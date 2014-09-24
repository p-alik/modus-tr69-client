/**
 * Product Name : Modus TR-069 Orange
 *
 * Copyright c 2014 Orange
 *
 * This software is distributed under the Apache License, Version 2.0
 * (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 or see the "license.txt" file for
 * more details
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author: Olivier Beyler - Orange
 */

package com.francetelecom.admindm.com;

import java.net.BindException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

import com.francetelecom.admindm.api.ICom;
import com.francetelecom.admindm.api.Log;

/**
 * The Class HttpServer.
 */
public final class HttpServer implements Runnable {
	/** The Constant DEFAULT_PORT. */
	private static final int DEFAULT_PORT = 16001;
	/** The Constant IPV6_LENGTH. */
	private static final int IPV6_LENGTH = 16;
	/** The random port. */
	private static int randomPort = DEFAULT_PORT;
	/**
	 * The random path. note: <i>Can not use Math.random() with Mika JVM</i> should be :<br/>
	 */
	private static String randomPath = "/" + System.currentTimeMillis();

	/** The best network interface selected by user. */
	static private String bestNI = "";

	/** The IP address selected by user. */
	private static String address = null;

	/** The is running. */
	private boolean isRunning = true;
	/** The com. */
	private final ICom com;

	/**
	 * Instantiates a new HTTP server.
	 * 
	 * @param pCom
	 *            the com
	 */
	public HttpServer(final ICom pCom) {
		this.com = pCom;

	}

	/**
	 * ; Run the thread.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		ServerSocket server = null;
		try {
			boolean freeportFound = false;
			while (!freeportFound) {
				try {
					server = new ServerSocket(randomPort);
					freeportFound = true;
				} catch (BindException e) {
					Log.info("Change port because is already used" + getURL());
					randomPort = randomPort + 1;
					Log.info("Now it will be " + getURL());
				}
			}
			Log.info("TCPServer Waiting for client on+" + getURL());
			while (this.isRunning) {
				Socket connected = server.accept();
				connected.setSoTimeout(1000);
				Log.debug("accepted..");
				(new HttpServerConnection(connected, randomPath, this.com)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the uRL.
	 * 
	 * @return the uRL
	 */
	public static String getURL() {
		StringBuffer result = new StringBuffer("http://");
		String adress = "";
		if (HttpServer.address != null) {
			adress = HttpServer.address;
		} else {
			NetworkInterface interfaceN;
			try {
				Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
				Log.debug("BestNI=|" + bestNI + "|");
				while (interfaces.hasMoreElements()) { // carte reseau trouvee
					interfaceN = (NetworkInterface) interfaces.nextElement();
					Enumeration ienum = interfaceN.getInetAddresses();
					Log.debug("interface name = " + interfaceN.getName() + ".");
					if ((interfaceN.getName() != null) && (bestNI != null) && (interfaceN.getName().equals(bestNI))) {
						while (ienum.hasMoreElements()) {
							InetAddress ia = (InetAddress) ienum.nextElement();
							if (ia.getHostAddress().length() < IPV6_LENGTH) {
								adress = ia.getHostAddress().toString();
								result.append(adress);
								result.append(":");
								result.append(randomPort);
								result.append(randomPath);
								return result.toString();
							}
						}
					}
				}
				interfaces = NetworkInterface.getNetworkInterfaces();
				while (interfaces.hasMoreElements()) { // carte reseau trouvee
					interfaceN = (NetworkInterface) interfaces.nextElement();
					Enumeration ienum = interfaceN.getInetAddresses();
					while (ienum.hasMoreElements()) {
						// retourne l adresse IPv4et IPv6
						InetAddress ia = (InetAddress) ienum.nextElement();
						if (!(ia.isLoopbackAddress()) && (ia instanceof Inet4Address) && (adress.equals(""))) {
							Log.debug("ce n'est pas un loopback; interface name = " + interfaceN.getName() + ".");
							Log.debug("Adresse : " + ia.getHostAddress().toString());
							// System.out.println("C'est une IPV4") ;
							// Ce n'est pas l'adresse IP Local'
							adress = ia.getHostAddress().toString();
						}
					}
				}
			} catch (Exception e) {
				Log.error("no network card detected");
			}
		}
		// adress = "86.194.75.60";
		result.append(adress);
		result.append(":");
		result.append(randomPort);
		result.append(randomPath);
		return result.toString();
	}

	/**
	 * Sets the best ni.
	 * 
	 * @param property
	 *            the property
	 */
	public static void setBestNI(final String property) {
		bestNI = property;
	}

	public static void setIPAddress(String ipAddress) {
		address = ipAddress;
	}

}
