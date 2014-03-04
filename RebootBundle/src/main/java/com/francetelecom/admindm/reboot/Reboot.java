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

package com.francetelecom.admindm.reboot;

import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;

/**
 * The Class Reboot.
 */
public final class Reboot implements RPCMethod {
	/** The Constant name. */
	private static final String NAME = "Reboot";
	/** The command key. */
	private final String commandKey;

	/**
	 * Gets the command key.
	 * 
	 * @return the command key
	 */
	public String getCommandKey() {
		return commandKey;
	}

	/**
	 * Instantiates a new reboot.
	 * 
	 * @param pCommandKey
	 *            the command key
	 */
	public Reboot(final String pCommandKey) {
		super();
		this.commandKey = pCommandKey;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 * @see com.francetelecom.admindm.api.RPCMethod#getName()
	 */
	public String getName() {
		return NAME;
	}

	/**
	 * Perform.
	 * 
	 * @param session
	 *            the session
	 * @throws Fault
	 *             the exception
	 */
	public void perform(final Session session) throws Fault {
		RebootResponse rebootResponse = new RebootResponse();
		IParameterData parameterData = session.getParameterData();
		parameterData.addEvent(new EventStruct(EventCode.M_REBOOT, commandKey));
		session.doASoapResponse(rebootResponse);
		System.exit(-1);
	}

	/**
	 * id of the RPCMethod Request by ACS.
	 */
	private String id = null;

	/**
	 * Gets the id.
	 * 
	 * @return the Id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter the Id.
	 */
	public void setId(String id) {
		this.id = id;
	}

}
