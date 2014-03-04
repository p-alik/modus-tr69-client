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

/**
 * Very light HTTP connection server class.
 * Valid status (HTTP/1.1 200) can only be reach in case of
 * the HTTPRequest use:<br/>
 * <li>method GET</li>
 * <li>on specific path</li>
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import com.francetelecom.admindm.api.ICom;
import com.francetelecom.admindm.api.Log;

/**
 * The Class HttpServerConnection.
 */
public class HttpServerConnection extends Thread {
	/** The random path. */
	private String randomPath;
	/** The connected client. */
	private Socket connectedClient = null;
	/** The com. */
	private final ICom com;

	/**
	 * Instantiates a new http server connection.
	 * 
	 * @param pClient
	 *            the client
	 * @param pRandomPath
	 *            the random path
	 */
	public HttpServerConnection(final Socket pClient, final String pRandomPath, final ICom pCom) {
		connectedClient = pClient;
		this.randomPath = pRandomPath;
		this.com = pCom;
	}

	/** The msg ok. */
	private static String msgOK = "" + "HTTP/1.1 200 OK\r\n" + "Content-Type: text/plain\r\n"
			+ "Server: CPE_HTTPServer\r\n" + "\r\n";

	// The following constant is not used.
	// /** The msg ko. */
	// private static String msgKO = ""
	// + "HTTP/1.1 401 Unauthorized\r\n"
	// +
	// "WWW-Authenticate: Digest realm=\"DSL Forum\", algorithm=\"MD5\", qop=\"auth\", nonce=\"549ad3dc48bd7ffe\"\r\n"
	// + "Content-Type: text/html\r\n" +
	// "<HTML><HEAD><TITLE>401 Unauthorized</TITLE></HEAD>"
	// + "<BODY>401 Unauthorized</BODY></HTML>\r\n";

	/**
	 * Run.
	 * 
	 * @see java.lang.Thread#run()
	 */
	public final void run() {
		BufferedReader inFromClient = null;
		try {
			Log.info("The Client " + connectedClient.getInetAddress() + ":" + connectedClient.getPort()
					+ " is connected");
			inFromClient = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));
			String requestString = inFromClient.readLine();
			Log.debug("Lu : " + requestString);
			String headerLine = requestString;
			StringTokenizer tokenizer = new StringTokenizer(headerLine);
			String httpMethod = tokenizer.nextToken();
			String httpQueryString = tokenizer.nextToken();
			String userAgent = inFromClient.readLine();
			Log.debug("userAgent:" + userAgent);
			String userMdp = inFromClient.readLine();
			Log.debug("userMdp:" + userMdp);
			StringBuffer responseBuffer = new StringBuffer();
			while (inFromClient.ready()) {
				// Read the HTTP complete HTTP Query
				responseBuffer.append(requestString);
				responseBuffer.append("<BR>");
				requestString = inFromClient.readLine();
				Log.debug("Lu : " + requestString);
			}
			if (httpMethod.equals("GET")) {
				Log.debug("GET trouve");
				if (httpQueryString.equals(randomPath)) {
					Log.debug("randomPath OK : " + randomPath);
					OutputStream out = connectedClient.getOutputStream();
					/*
					 * if (userMdp.contains("username")) {
					 * Log.debug("userMdp OK : "+userMdp) ;
					 * out.write(msgOK.getBytes()); out.close();
					 * com.requestNewSessionByHTTPConnection(); } else {
					 * out.write(msgKO.getBytes()); out.close(); }
					 */
					out.write(msgOK.getBytes());
					out.close();
					com.requestNewSessionByHTTPConnection();
				}
				inFromClient.close();
			}

		} catch (Exception e) {
			Log.error(e.getLocalizedMessage());
		} finally {
			try {
				connectedClient.close();
			} catch (IOException e) {
				Log.error(e.getLocalizedMessage());
			}
		}
	}
}
