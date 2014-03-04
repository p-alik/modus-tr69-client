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
 */

package com.francetelecom.admindm.model;

import org.kxml2.kdom.Element;

import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.Soap;

/**
 * @SoftwareName:
 * @Version: 1.1.0-SNAPSHOT
 * 
 * @Copyright: c 2013 France Telecom
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class OpResultStruct {
	/**
	 * That's not the bundle id, this uuid can be defined bu the ACS, or by the
	 * CPE.
	 */
	String uuid = null;
	String deploymentUnitRef = null;
	String version = null;
	String currentState = null;
	boolean resolved = false;
	String executionUnitRefList = null;
	/**
	 * Should be TR-069 dateTime type instead of long.
	 */
	long startTime = -1;
	/**
	 * Should be TR-069 dateTime type instead of long.
	 */
	long completeTime = -1;
	/**
	 * Legacy: Fault should be named FaultStruct.
	 */
	Fault fault = null;

	/**
	 * OpResultStruct
	 * 
	 * @param uuid
	 *            , that's not the bundle id, this uuid can be defined bu the
	 *            ACS, or by the CPE. Assume that uuid is defined by the ACS:
	 *            uuid can NOT be null, or "".
	 * @param deploymentUnitRef
	 *            , i.e. the bundleId in this TR-157 for OSGi implementation.
	 * @param version
	 * @param currentState
	 * @param resolved
	 * @param executionUnitRefList
	 *            , a comma-separated list of the execution Units related to the
	 *            affected DU (see TR-069 Issue 1 Amendment 4 Table 77 Page
	 *            117).
	 * @param startTime
	 *            , must be inline with System.currentTimeMillis() format.
	 * @param completeTime
	 *            , must be inline with System.currentTimeMillis() format.
	 * @param fault
	 * 
	 */
	public OpResultStruct(final String uuid, final String deploymentUnitRef, final String version,
			final String currentState, final boolean resolved, final String executionUnitRefList, final long startTime,
			final long completeTime, final Fault fault) {
		super();
		// if (uuid == null || uuid.equals("")) {
		// throw new OpResultStructException(
		// "The given uuid can NOT be null, or \"\".", null);
		// }
		this.uuid = uuid;
		this.deploymentUnitRef = deploymentUnitRef;
		this.version = version;
		this.currentState = currentState;
		this.resolved = resolved;
		this.executionUnitRefList = executionUnitRefList;
		this.startTime = startTime;
		this.completeTime = completeTime;
		this.fault = fault;
	}

	/**
	 * @return the DU's UUID, that's not the bundle, this uuid can be defined by
	 *         the ACS, or by the CPE.
	 */
	public String getUuid() {
		return this.uuid;
	}

	public String getDeploymentUnitRef() {
		return this.deploymentUnitRef;
	}

	public String getVersion() {
		return this.version;
	}

	public String getCurrentState() {
		return this.currentState;
	}

	public boolean isResolved() {
		return this.resolved;
	}

	public String getExecutionUnitRefList() {
		return this.executionUnitRefList;
	}

	public long getStartTime() {
		return this.startTime;
	}

	public long getCompleteTime() {
		return this.completeTime;
	}

	public Fault getFault() {
		return this.fault;
	}

	/**
	 * Encoded.
	 * 
	 * @return the element
	 * @throws OpResultStructException
	 */
	public Element encoded() throws OpResultStructException {
		Element result = new Element();
		result.setName("OpResultStruct");

		// UUID
		Element eUUID = new Element();
		eUUID.setName("UUID");
		result.addChild(Element.ELEMENT, eUUID);
		if (this.uuid == null) {
			throw new OpResultStructException("uuid is null, but it can NOT be.", null);
			// eUUID.addChild(Element.TEXT, "");
		} else {
			eUUID.addChild(Element.TEXT, this.uuid);
		}

		// DeploymentUnitRef
		Element eDeploymentUnitRef = new Element();
		eDeploymentUnitRef.setName("DeploymentUnitRef");
		result.addChild(Element.ELEMENT, eDeploymentUnitRef);
		eDeploymentUnitRef.addChild(Element.TEXT, this.deploymentUnitRef);

		// Version
		Element eVersion = new Element();
		eVersion.setName("Version");
		result.addChild(Element.ELEMENT, eVersion);
		eVersion.addChild(Element.TEXT, this.version);

		// CurrentState
		Element eCurrentState = new Element();
		eCurrentState.setName("CurrentState");
		result.addChild(Element.ELEMENT, eCurrentState);
		eCurrentState.addChild(Element.TEXT, this.currentState);

		// Resolved
		Element eResolved = new Element();
		eResolved.setName("Resolved");
		if (this.resolved) {
			eResolved.addChild(Element.TEXT, "true");
		} else {
			eResolved.addChild(Element.TEXT, "false");
		}
		result.addChild(Element.ELEMENT, eResolved);

		// ExecutionUnitRefList
		Element eExecutionUnitRefList = new Element();
		eExecutionUnitRefList.setName("ExecutionUnitRefList");
		result.addChild(Element.ELEMENT, eExecutionUnitRefList);
		eExecutionUnitRefList.addChild(Element.TEXT, this.executionUnitRefList);

		// StartTime
		Element eStartTime = new Element();
		eStartTime.setName("StartTime");
		result.addChild(Element.ELEMENT, eStartTime);
		eStartTime.addChild(Element.TEXT, Soap.convertDate2String(this.startTime));

		// CompleteTime
		Element eCompleteTime = new Element();
		eCompleteTime.setName("CompleteTime");
		result.addChild(Element.ELEMENT, eCompleteTime);
		eCompleteTime.addChild(Element.TEXT, Soap.convertDate2String(this.completeTime));

		// Fault
		Element eFault = new Element();
		eFault.setName("Fault");
		result.addChild(Element.ELEMENT, eFault);

		// Can NOT use FaultEncoder here.
		// try {
		// eFault.addChild(Element.ELEMENT,
		// new FaultEncoder().encode(this.fault));
		// } catch (Fault e) {
		// throw new OpResultStructException(e.getMessage(), e);
		// }
		// Because it produces:
		// <SOAP-ENV:Fault>
		// <faultcode>Server</faultcode>
		// <faultstring>CWMP Fault</faultstring>
		// <detail>
		// <cwmp:Fault>
		// <FaultCode>90XY</FaultCode>
		// <FaultString>...</FaultString>
		// </cwmp:Fault>
		// </detail>
		// </SOAP-ENV:Fault>
		// And something like the following is expected instead:
		// <Fault>
		// <FaultCode>90XY</FaultCode>
		// <FaultString>...</FaultString>
		// </Fault>

		Element eFaultcode = new Element();
		eFaultcode.setName("FaultCode");
		// eFaultcode.addChild(Element.TEXT,
		// FaultUtil.getType(this.fault.getFaultcode()));
		eFaultcode.addChild(Element.TEXT, Integer.toString(this.fault.getFaultcode()));
		eFault.addChild(Element.ELEMENT, eFaultcode);

		Element eFaultstring = new Element();
		eFaultstring.setName("FaultString");
		eFaultstring.addChild(Element.TEXT, this.fault.getFaultstring());
		eFault.addChild(Element.ELEMENT, eFaultstring);

		return result;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String toString = this.getClass().getName() + "[" + "uuid=" + this.uuid + ",deploymentUnitRef="
				+ this.deploymentUnitRef + ",version=" + this.version + ",currentState=" + this.currentState
				+ ",resolved=" + this.resolved + ",executionUnitRefList=" + this.executionUnitRefList + ",startTime="
				+ this.startTime + ",completeTime=" + this.completeTime + ",fault=" + this.fault + "]";
		return toString;
	}
}
