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
 * Author: Antonin Chazalet - Orange
 * Mail: antonin.chazalet@orange.com;antonin.chazale@gmail.com
 */

package com.francetelecom.admindm.changedustate;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 * 
 * @see Table 79 in TR-069 Issue 1 Amendment 4 (page 119).
 */
public final class DUStateChangeCompleteResponse implements RPCMethod {

	/** The Constant NAME. */
	public static final String NAME = "DUStateChangeCompleteResponse";

	/** The current time. */
	private long currentTime = System.currentTimeMillis();

	/** id of the RPCMethod Request by ACS. */
	private String id = null;

	// private String id = DUStateChangeCompleteResponse.NAME + "_"
	// + System.currentTimeMillis() + "_" + (int) (Math.random() * 1000);

	/**
	 * Instantiates a new DUStateChangeCompleteResponse.
	 */
	public DUStateChangeCompleteResponse() {
		Log.info("Constructor of " + DUStateChangeCompleteResponse.NAME);
	}

	/**
	 * Gets the name.
	 * 
	 * @return RPCMethod's name
	 */
	public String getName() {
		return DUStateChangeCompleteResponse.NAME;
	}

	/**
	 * Gets the current time.
	 * 
	 * @return the current time
	 */
	public long getCurrentTime() {
		return this.currentTime;
	}

	/**
	 * Perform.
	 * 
	 * @param session
	 *            the session
	 * @throws Fault
	 *             the fault
	 * @see RPCMethod#perform(Session)
	 */
	public void perform(final Session session) throws Fault {

		// Send an empty post as requested: see fig. 5 of TR-157_Amendment-5.pdf
		// file.
		// Note that session.doSoapRequest(...) implementation ignores the
		// second arg if the first one is null, that's why the second arg is
		// null here. We may try with session.doSoapRequest(null, this.id)???
		Log.debug("DUStateChangeCompleteResponse now sends an empty post, as requested in TR-157.");
		session.doSoapRequest(null, null);
		Log.debug("DUStateChangeCompleteResponse has just sent an empty post, as requested in TR-157.");

	}

	/**
	 * Gets the id.
	 * 
	 * @return the Id.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Setter the Id.
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String toString = this.getClass().getName() + "[" + "NAME=" + NAME + ",currentTime=" + this.currentTime
				+ ",id=" + this.id + "]";
		return toString;
	}

}
