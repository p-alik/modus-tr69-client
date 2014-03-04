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
 * Author: Anne Gerodolle - Orange
 * Mail: anne.gerodolle@orange.com
 * Author: Antonin Chazalet - Orange
 * Mail: antonin.chazalet@orange.com;antonin.chazale@gmail.com
 */

package com.francetelecom.admindm.changedustate.callviaacs;

import java.io.IOException;
import java.security.InvalidParameterException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.simple.JSONObject;

/**
 * @author: Anne Gerodolle
 * @mail: anne.gerodolle@orange.com
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class CallChangeDUStateUpdateCapabilityOnEdgeAcs {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		HttpClient client = new HttpClient();
		// client.getHostConfiguration().setProxy("", );

		String host = null;
		String port = null;
		String address = "http://" + host + ":" + port + "/edge/api/";

		String acsUsername = null;
		String acsPassword = null;

		if (host == null || port == null || acsUsername == null || acsPassword == null) {
			throw new InvalidParameterException("Fill host: " + host + ", port: " + port + ", acsUsername: "
					+ acsUsername + ", and acsPassword: " + acsPassword + ".");
		}

		String realm = "NBBS_API_Realm";

		AuthScope authscope = new AuthScope(host, Integer.parseInt(port), realm);

		// client.getState().setCredentials(realm, host,
		// new UsernamePasswordCredentials(acsUsername, acsPassword));

		client.getState().setCredentials(authscope, new UsernamePasswordCredentials(acsUsername, acsPassword));

		PostMethod post = null;

		// -----
		// ----- Execution de la capability : changeDUStateUpdate
		// -----

		post = new PostMethod(address + "capability/execute");

		post.addParameter(new NameValuePair("deviceId", "10003"));
		post.addParameter(new NameValuePair("timeoutMs", "60000"));
		post.addParameter(new NameValuePair("capability", "\"changeDUStateUpdate\""));

		// UUID: string
		// Version: string
		// URL: string
		// Username: string
		// Password: string

		JSONObject object = new JSONObject();
		object.put("UUID", "45");
		object.put("Version", "1.0.0");
		object.put("URL", "http://127.0.0.1:8085/a/org.apache.felix.http.jetty-2.2.0.jar");
		object.put("Username", "Username_value");
		object.put("Password", "Password_value");
		post.addParameter(new NameValuePair("input", object.toString()));

		post.setDoAuthentication(true);

		// post.addParameter(new NameValuePair("deviceId", "60001"));

		// -----
		// ----- Partie commune : Execution du post
		// -----

		try {
			int status = client.executeMethod(post);
			System.out.println("status: " + status);
			String resp = post.getResponseBodyAsString();
			System.out.println("resp: " + resp);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// release any connection resources used by the method
			post.releaseConnection();
		}
	}
}
