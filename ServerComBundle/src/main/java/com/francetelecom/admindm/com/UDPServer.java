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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.stunclient.ISTUNCLient;

	/**
 * <p>
 * This class provides a UDP server used to receive the UDP Connection Request
 * sent by the ACS.
 * </p>
 * <p>
 * The UDP server is bound on the port 7547 by default.
 * </p>
 * 
 * @author mpcy8647
 */
public final class UDPServer implements Runnable {
	/** The Constant IPV6_LENGTH. */
	private static final int IPV6_LENGTH = 16;

	/** Default udp server port. */
	private static final int DEFAULT_PORT = 7547;

	/** Max datagram packet size. */
	private static final int MAX_DATAGRAM_SIZE = 8 * 1024;

	/** UDPConnectionRequestAddress parameter name */

	/** last authenticated and validated udp connection request. */
	private UDPConnectionRequest lastUdpConnectionRequest = null;

	/** Port on which the UDP server is bound. */
	private int randomPort = DEFAULT_PORT;

	/** Datagram socket (UDP socket). */
	private DatagramSocket datagramSocket;

	/** Data model. */
	private IParameterData parameterData;

	/** running ok ? */
	private boolean running = false;

	/** stun client. */
	private ISTUNCLient stunClient;

	/** UDPConnectionRequest queue. */
	private final UDPConnectionRequestQueue udpConnectionRequestQueue;

	/** UDPConnectionRequest processor. */
	private final UDPConnectionRequestProcessor udpConnectionRequestProcessor;

	/** udp server instance. */
	private static UDPServer udpServer = null;

	/** The best network interface selected by user. */
	private static String bestNI = "";

	/** The IP address selected by user. */
	private static String address = null;

	/**
	 * Return the singleton instance.
	 * 
	 * @return instance
	 */
	public static UDPServer getInstance() {
		if (udpServer == null) {
			udpServer = new UDPServer();
		}
		return udpServer;
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

	/**
	 * Set the parameterData in use.
	 * 
	 * @param pParameterData
	 *            the parameter data
	 */
	public void setParameterData(final IParameterData pParameterData) {
		this.parameterData = pParameterData;
	}

	/**
	 * Run.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		Log.info("starting udp server on port " + this.randomPort);
		UDPServerConnection udpServerConnection;
		DatagramPacket datagramPacket;
		while (this.running) {
			try {
				// create a new datagram packet
				int bufsize = this.datagramSocket.getReceiveBufferSize();
				byte[] message = new byte[bufsize];
				datagramPacket = new DatagramPacket(message, bufsize);
				// wait for datagram packet receiving
				Log.info("waiting for incoming UDP packet (datagram packet)");
				this.datagramSocket.receive(datagramPacket);
				Log.info("new incoming packet");
				udpServerConnection = new UDPServerConnection(datagramPacket);
				udpServerConnection.start();
			} catch (SocketException e) {
				StringBuffer error = new StringBuffer("unable to get");
				error.append(" the datagram socket receive buffer size");
				Log.error(error.toString(), e);
			} catch (IOException e) {
				Log.error("unable to receive datagram packet");
			}
		}
	}

	/**
	 * <p>
	 * Start the UDP server. The following actions are performed:
	 * <ul>
	 * <li>check if the paramterData attribute is set.</li>
	 * <li>if so, create a new Thread.</li>
	 * </ul>
	 * 
	 * @throws Exception
	 *             if the setParameterData method wasn't previously called.
	 */
	public void startUDPServer() throws Exception {
		this.running = true;
		if (this.parameterData == null) {
			throw new Exception("the parameterData must be set !!");
		}
		this.udpConnectionRequestProcessor.setParameterData(this.parameterData);
		this.udpConnectionRequestProcessor.start();
		new Thread(this).start();

		// start the stun client if available
		if (this.stunClient != null) {
			this.stunClient.start(this.datagramSocket);
		}
	}

	/**
	 * Set the port number on which the UDP server is bound. This method has to
	 * be invoked prior to start the UDP server thread.
	 * 
	 * @param port
	 *            port
	 */
	public void setPort(final int port) {
		this.randomPort = port;
	}

	/**
	 * Get the port of the datagram socket.
	 * 
	 * @return port
	 */
	public int getPort() {
		return this.randomPort;
	}

	/**
	 * <p>
	 * Get the address of the datagram socket.
	 * </p>
	 * 
	 * @return address (ip form)
	 */
	public String getAddress() {
		Log.debug("datagramsocket = " + this.datagramSocket);
		Log.debug("datagramsocket.getinetAddress = " + this.datagramSocket.getLocalAddress());
		Log.debug("datagramsocket.getinetAddress.gethostaddress = "
				+ this.datagramSocket.getLocalAddress().getHostAddress());
		return this.datagramSocket.getLocalAddress().getHostAddress();
	}

	/**
	 * <p>
	 * Get the STUN client.
	 * </p>
	 * 
	 * @return stun client
	 */
	public ISTUNCLient getSTUNClient() {
		return this.stunClient;
	}

	/**
	 * Set the STUN client to use. It also invokes the start method provided by
	 * the STUN client.
	 * 
	 * @param pStunClient
	 *            stun client, null to unset
	 */
	public void setSTUNClient(final ISTUNCLient pStunClient) {
		this.stunClient = pStunClient;
		if ((this.stunClient != null) && (this.running)) {
			this.stunClient.start(this.datagramSocket);
		}
	}

	/**
	 * Get the queue of UDPConnectionRequest.
	 * 
	 * @return queue
	 */
	public UDPConnectionRequestQueue getUDPConnectionRequestQueue() {
		return this.udpConnectionRequestQueue;
	}

	/**
	 * Stop the server thread and close the socket.
	 */
	public void stopUDPServer() {
		this.running = false;
		this.datagramSocket.close();
	}

	/**
	 * <p>
	 * Get the last validated and authenticated UDP Connection request.
	 * </p>
	 * 
	 * @return the last validated and authenticated udp connection request
	 */
	public UDPConnectionRequest getLastUDPConnectionRequest() {
		return this.lastUdpConnectionRequest;
	}

	/**
	 * <p>
	 * Set the last validated and authenticated UDP connection request.
	 * </p>
	 * 
	 * @param udpConnectionRequest
	 *            udp connection request.
	 */
	public void setLastUDPConnectionRequest(final UDPConnectionRequest udpConnectionRequest) {
		this.lastUdpConnectionRequest = udpConnectionRequest;
	}

	/**
	 * <p>
	 * Private constructor.
	 * </p>
	 * <p>
	 * The following actions are performed:
	 * <ul>
	 * <li>initializes the datagram socket</li>
	 * <li>initializes the STUN client</li>
	 * </ul>
	 * </p>
	 */
	private UDPServer() {
		this.udpConnectionRequestQueue = new UDPConnectionRequestQueue();
		this.udpConnectionRequestProcessor = new UDPConnectionRequestProcessor();
		try {
			InetAddress inetAdress = getExternalInetAddress();
			Log.debug("chosen inetadress for UDPServer = " + inetAdress);
			this.datagramSocket = new DatagramSocket(this.randomPort, inetAdress);
			this.datagramSocket.setReceiveBufferSize(MAX_DATAGRAM_SIZE);
		} catch (SocketException e) {
			Log.error("unable to create a datagram socket on the port " + this.randomPort, e);
		}
	}

	/**
	 * <p>
	 * Return the InetAddress object corresponding to the first network
	 * interface different of the loopback interface.
	 * </p>
	 * 
	 * @return an InetAddress or null
	 */
	public static InetAddress getExternalInetAddress() {
			try {
			if (UDPServer.address != null) {
				try {
					return InetAddress.getByName(UDPServer.address);
				} catch (Exception e) {
					Log.debug("Internet address " + UDPServer.address + " not found. Try otherwise");
				}
			}
			NetworkInterface interfaceN;
			Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) { // carte reseau trouvee
				interfaceN = (NetworkInterface) interfaces.nextElement();
				Enumeration ienum = interfaceN.getInetAddresses();
				Log.debug("interface name = " + interfaceN.getName() + ".");
				if ((interfaceN.getName() != null) && (bestNI != null) && (interfaceN.getName().equals(bestNI))) {
					while (ienum.hasMoreElements()) {
						InetAddress ia = (InetAddress) ienum.nextElement();
						if (ia.getHostAddress().length() < IPV6_LENGTH) {
							Log.debug("bestInterface " + interfaceN.getName() + " found !");
							return ia;
						}
					}
				}
			}

			interfaces = NetworkInterface.getNetworkInterfaces();
			Enumeration inetAddressEnumeration;
			InetAddress inetAddress;
			for (; interfaces.hasMoreElements();) {
				NetworkInterface ni = (NetworkInterface) interfaces.nextElement();
				inetAddressEnumeration = ni.getInetAddresses();
				for (; inetAddressEnumeration.hasMoreElements();) {
					inetAddress = (InetAddress) inetAddressEnumeration.nextElement();
					if ((inetAddress.getHostAddress().length() < IPV6_LENGTH) && (!inetAddress.isLoopbackAddress())) {
						Log.debug("select inetAddress " + inetAddress.getHostAddress());
						return inetAddress;
					}
				}
			}
		} catch (SocketException e) {
			Log.error("unable to get network interfaces");
		}
		return null;
	}

	// /**
	// * Return the properly formatted Mac Address corresponding to the first
	// * network interface different of the loopback interface.
	// *
	// * @return, e.g. 08:00:27:00:98:67, or null.
	// */
	// public static String getProperlyFormattedMacAddress() {
	// /** the properly formatted mac address. */
	// String result = null;
	// try {
	// if (UDPServer.address != null) {
	// try {
	// InetAddress ia = InetAddress.getByName(UDPServer.address);
	// NetworkInterface ni = NetworkInterface.getByInetAddress(ia);
	// return properlyFormatAMacAddress(ni.getHardwareAddress());
	// } catch(Exception e) {
	// Log.debug("Internet address " + UDPServer.address +
	// " not found. Try otherwise");
	// }
	// }
	//
	// Enumeration nis;
	// // First, let's take bestNI into account.
	// nis = NetworkInterface.getNetworkInterfaces();
	// while (nis.hasMoreElements()) { // carte reseau trouvee
	// NetworkInterface ni = (NetworkInterface) nis.nextElement();
	// Enumeration inetAddresses = ni.getInetAddresses();
	// if ((ni.getName() != null) && (bestNI != null)
	// && (ni.getName().equals(bestNI))) {
	// while (inetAddresses.hasMoreElements()) {
	// InetAddress inetAddress = (InetAddress) inetAddresses
	// .nextElement();
	// if (inetAddress.getHostAddress().length() < IPV6_LENGTH) {
	// byte[] mac = ni.getHardwareAddress();
	// result = properlyFormatAMacAddress(mac);
	// return result;
	// }
	// }
	// }
	// }
	//
	// // Second, if taking bestNI into account was NOT successful, then
	// // let's ignore bestNI.
	// nis = NetworkInterface.getNetworkInterfaces();
	// while (nis.hasMoreElements()) { // carte reseau trouvee
	// NetworkInterface ni = (NetworkInterface) nis.nextElement();
	// Enumeration inetAddresses = ni.getInetAddresses();
	// while (inetAddresses.hasMoreElements()) {
	// InetAddress inetAddress = (InetAddress) inetAddresses
	// .nextElement();
	// if ((inetAddress.getHostAddress().length() < IPV6_LENGTH)
	// && (!inetAddress.isLoopbackAddress())) {
	// byte[] mac = ni.getHardwareAddress();
	// result = properlyFormatAMacAddress(mac);
	// return result;
	// }
	// }
	// }
	//
	// return result;
	//
	// } catch (SocketException e) {
	// Log.error("unable to get network interfaces");
	// return result;
	// }
	// }
	//
	// private static String properlyFormatAMacAddress(final byte[] mac) {
	// String macProperlyFormatted = null;
	// if (mac != null) {
	// BigInteger bi = new BigInteger(1, mac);
	// Object[] o = { bi };
	// String macAddressAsAString = String.format("%0" + (mac.length << 1)
	// + "X", o);
	//
	// Log.debug("macAddressAsAString: " + macAddressAsAString);
	//
	// char[] c = macAddressAsAString.toCharArray();
	// macProperlyFormatted = "";
	// if (c.length == 12) {
	// for (int i = 0; i < c.length; i = i + 1) {
	// if (i == 2) {
	// macProperlyFormatted = macProperlyFormatted + ":"
	// + c[i];
	// } else if (i == 4) {
	// macProperlyFormatted = macProperlyFormatted + ":"
	// + c[i];
	// } else if (i == 6) {
	// macProperlyFormatted = macProperlyFormatted + ":"
	// + c[i];
	// } else if (i == 8) {
	// macProperlyFormatted = macProperlyFormatted + ":"
	// + c[i];
	// } else if (i == 10) {
	// macProperlyFormatted = macProperlyFormatted + ":"
	// + c[i];
	// } else {
	// macProperlyFormatted = macProperlyFormatted + c[i];
	// }
	// }
	// }
	//
	// Log.debug("macProperlyFormatted: " + macProperlyFormatted);
	//
	// }
	// return macProperlyFormatted;
	// }

}
