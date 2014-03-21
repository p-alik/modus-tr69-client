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

import java.util.Iterator;
import java.util.List;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.OpResultStruct;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 * 
 * @see Table 76, 77, and 78 in TR-069 Issue 1 Amendment 4 (page 117-...).
 */
public final class DUStateChangeComplete implements RPCMethod {

	/** The Constant NAME. */
	public static final String NAME = "DUStateChangeComplete";

	/** OpResultStruct[] */
	private List results = null;

	/** The command key */
	private String commandKey = null;

	/** The current time. */
	private long currentTime = System.currentTimeMillis();

	/** id of the RPCMethod Request by ACS. */
	private String id = null;
	// private String id = DUStateChangeComplete.NAME + "_"
	// + System.currentTimeMillis() + "_" + (int) (Math.random() * 1000);

	/** The duStateChangeComplete encoder. */
	private static DUStateChangeCompleteEncoder duStateChangeCompleteEncoder = new DUStateChangeCompleteEncoder();

	/**
	 * Instantiates a new DUStateChangeComplete.
	 * 
	 * @param results
	 *            , i.e. List of OpResultStruct.
	 * @param commandKey
	 * @param id
	 *            , of the RPCMethod Request by ACS.
	 */
	public DUStateChangeComplete(final List results, final String commandKey, final String id) {
		Log.info("Constructor of " + DUStateChangeComplete.NAME);
		this.results = results;
		this.commandKey = commandKey;
		this.id = id;
	}

	/**
	 * Gets the name.
	 * 
	 * @return RPCMethod's name
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * Gets the results.
	 * 
	 * @return the results
	 */
	public List getResults() {
		return this.results;
	}

	/**
	 * Gets the commandKey.
	 * 
	 * @return the commandKey
	 */
	public String getCommandKey() {
		return this.commandKey;
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
		// Complete DUStateChangeComplete if needed: see Inform.
		// constructEvents();
		// constructParameter(session.getSessionId());
		session.doSoapRequest(duStateChangeCompleteEncoder.encode(this), this.id);
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
		String resultsAsAString = null;
		if (this.results != null) {
			Iterator resultsIterator = this.results.iterator();
			while (resultsIterator.hasNext()) {
				OpResultStruct result = (OpResultStruct) resultsIterator.next();
				if (resultsAsAString == null) {
					resultsAsAString = "[";
				}
				if (resultsIterator.hasNext()) {
					resultsAsAString = resultsAsAString + result + ",";
				} else {
					resultsAsAString = resultsAsAString + result + "]";
				}
			}
		}

		String toString = this.getClass().getName() + "[" + "NAME=" + NAME + ",results=" + resultsAsAString
				+ ",commandKey=" + this.commandKey + ",currentTime=" + this.currentTime + ",id=" + this.id + "]";
		return toString;
	}

}
