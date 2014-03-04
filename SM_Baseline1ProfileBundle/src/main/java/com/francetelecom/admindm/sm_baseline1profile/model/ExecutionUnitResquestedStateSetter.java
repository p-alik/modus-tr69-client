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

package com.francetelecom.admindm.sm_baseline1profile.model;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.Setter;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;

public class ExecutionUnitResquestedStateSetter implements Setter {

	/** @see TR181 i2 a6 */
	private static final String IDLE = "Idle";
	/** @see TR181 i2 a6 */
	private static final String ACTIVE = "Active";

	private long euid;
	private BundleContext bundleContext;

	public ExecutionUnitResquestedStateSetter(final long euid, final BundleContext bundleContext) {
		Log.debug("ExecutionUnitResquestedStateSetter's constructor: euid: " + euid + ", bundleContext: "
				+ bundleContext + ".");
		this.euid = euid;
		this.bundleContext = bundleContext;
	}

	/**
	 * @see com.francetelecom.admindm.api.Setter#set(com.francetelecom.admindm.model.Parameter, java.lang.Object)
	 */
	public void set(final Parameter param, final Object obj) throws Fault {
		// Log.debug("ExecutionUnitResquestedStateSetter's set: param: " + param
		// + ", obj: " + obj + ".");
		try {
			if (IDLE.equals(obj)) {
				Bundle b = this.bundleContext.getBundle(this.euid);
				if (b == null) {
					StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9001);
					error.append(": The bundle associated to the given EUID can NOT be found.");
					throw new Fault(FaultUtil.FAULT_9001, error.toString());
				} else {
					b.stop();
				}
			} else if (ACTIVE.equals(obj)) {
				Bundle b = this.bundleContext.getBundle(this.euid);
				if (b == null) {
					StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9001);
					error.append(": The bundle associated to the given EUID can NOT be found.");
					throw new Fault(FaultUtil.FAULT_9001, error.toString());
				} else {
					b.start();
				}
			} else if ("".equals(obj)) {
				// Log.debug("obj is: \"\". There is nothing to do, here.");
			} else {
				StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
				error.append(": " + IDLE + ", or " + ACTIVE + " are only allowed values.");
				throw new Fault(FaultUtil.FAULT_9003, error.toString());
			}
		} catch (BundleException e) {
			StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9001);
			error.append(": " + e.getLocalizedMessage());
			throw new Fault(FaultUtil.FAULT_9001, error.toString(), e);
		}
	}
}
