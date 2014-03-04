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

import junit.framework.TestCase;

import org.ow2.odis.test.TestUtil;

public class ComTest extends TestCase {
	public void testTestUrl() {
		TestUtil.TRACE(this);
		// IParameterData data = new ParameterData();
		// System.out.println("test");
		//
		// Properties prop = System.getProperties();
		//
		// prop.put("socksProxyHost", "p-freeway");
		// prop.put("socksProxyPort", "1080");
		// prop.put("http.proxySet", "true");
		// prop.put("http.proxyHost", "goodway");
		// prop.put("http.proxyPort", "3128");
		// String address = "admindm.rd.francetelecom.com";
		// try {
		// InetSocketAddress ads = new InetSocketAddress("161.105.161.34",80);
		//
		// Socket socket = new Socket();
		// System.out.println("fi");
		//
		// socket.connect(ads, 20);
		// System.out.println("fin");
		// System.out.println(socket.getLocalAddress().getHostAddress());
		// } catch (UnknownHostException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public void testRetry(int retry, long min, long max) {
		long oldValue = 0;
		for (int i = 0; i < 5000; i++) {
			long value = Com.getSleepingTimeBeforeRetry(retry);
			assertTrue("" + i + "retry delay" + retry + "  " + value + " " + oldValue, value != oldValue);
			assertTrue("retry delay " + retry + "  " + value, value >= min);
			assertTrue("retry delay " + retry + "  " + value, value <= max);
			oldValue = value;
		}
	}

	public void testRetry() {
		TestUtil.TRACE(this);
		testRetry(1, 5000, 10000);
		testRetry(2, 10000, 20000);
		testRetry(3, 20000, 40000);
		testRetry(4, 40000, 80000);
		testRetry(5, 80000, 160000);
		testRetry(6, 160000, 320000);
		testRetry(7, 320000, 640000);
		testRetry(8, 640000, 1280000);
		testRetry(9, 1280000, 2560000);
		testRetry(10, 2560000, 5120000);
	}

}
