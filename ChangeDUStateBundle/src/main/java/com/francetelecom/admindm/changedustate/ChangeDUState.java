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
import com.francetelecom.admindm.model.OperationStruct;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class ChangeDUState implements RPCMethod {

	/** The Constant NAME. */
	public static final String NAME = "ChangeDUState";

	/** The operations. */
	private OperationStruct[] operations = null;

	/** The command key. */
	private String commandKey = null;

	/** The current time. */
	private long currentTime = System.currentTimeMillis();

	/** id of the RPCMethod Request by ACS. */
	private String id = ChangeDUState.NAME + "_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 1000);

	// /** The changeDUState decoder. */
	// private static ChangeDUStateDecoder changeDUStateDecoder = new
	// ChangeDUStateDecoder();

	/**
	 * Instantiates a new changeDUState.
	 */
	public ChangeDUState() {
		Log.info("Constructor of " + ChangeDUState.NAME);
	}

	/**
	 * Gets the name.
	 * 
	 * @return RPCMethod's name
	 */
	public String getName() {
		return ChangeDUState.NAME;
	}

	public long getCurrentTime() {
		return this.currentTime;
	}

	// public void setCurrentTime(final long currentTime) {
	// this.currentTime = currentTime;
	// }

	public OperationStruct[] getOperations() {
		return this.operations;
	}

	public void setOperations(final OperationStruct[] operations) {
		this.operations = operations;
	}

	public String getCommandKey() {
		return this.commandKey;
	}

	public void setCommandKey(final String commandKey) {
		this.commandKey = commandKey;
	}

	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * Perform, i.e. launch asynchronously the install/update/uninstall, and respond by a ChangeDUStateResponse.
	 * 
	 * @see com.francetelecom.admindm.api.RPCMethod#perform(com.francetelecom.admindm.api.Session)
	 */
	public void perform(final Session session) throws Fault {
		RPCMethod response = null;

		response = new ChangeDUStateResponse();

		session.doASoapResponse(response);

		// XXX AAA: Check the ChangeDUState parameters ?
		//
		// RPCMethod response;
		// ParameterInfoStruct[] parameterList;
		// parameterList = getArrayOfParameterInfo(parameterPath, nextLevel,
		// session.getParameterData());
		// if (parameterList == null || parameterList.length == 0) {
		// StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9005);
		// error.append(": ");
		// error.append(parameterPath);
		// error.append(" doesn't correspond to any parameter.");
		// throw new Fault(FaultUtil.FAULT_9005, error.toString());
		// }
		// response = new GetParameterNamesResponse(parameterList);
		// session.doASoapResponse(response);

	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String operationsAsAString = null;
		if (this.operations != null) {
			for (int i = 0; i < this.operations.length; i = i + 1) {
				if (i == 0) {
					operationsAsAString = "[";
				}
				if (i == (this.operations.length - 1)) {
					operationsAsAString = operationsAsAString + this.operations[i] + "]";
				} else {
					operationsAsAString = operationsAsAString + this.operations[i] + ",";
				}
			}
		}
		String toString = this.getClass().getName() + "[" + "NAME=" + ChangeDUState.NAME + ",currentTime="
				+ this.currentTime + ",operations=" + operationsAsAString + ",commandKey=" + this.commandKey + ",id="
				+ this.id + "]";
		return toString;
	}
}
