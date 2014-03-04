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

package com.francetelecom.admindm.inform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.francetelecom.admindm.RPCMethodMng;
import com.francetelecom.admindm.api.EventBehavior;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.DeviceIdStruct;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterValueStruct;
import com.francetelecom.admindm.soap.Fault;

/**
 * The Class Inform.
 * 
 * @author Olivier Beyler - OrangeLabs
 */
public final class Inform implements RPCMethod {
	/** The name. */
	private static final String NAME = "Inform";
	/** The device id. */
	private DeviceIdStruct deviceId;
	/** The event. */
	private List event = new ArrayList();
	/** The max envelopes. */
	private long maxEnvelopes = 1;
	/** The inform encoder. */
	private static InformEncoder informEncoder = new InformEncoder();

	/**
	 * Gets the device id.
	 * 
	 * @return the device id
	 */
	public DeviceIdStruct getDeviceId() {
		return this.deviceId;
	}

	/**
	 * Sets the device id.
	 * 
	 * @param pDeviceId
	 *            the new device id
	 */
	public void setDeviceId(final DeviceIdStruct pDeviceId) {
		this.deviceId = pDeviceId;
	}

	/**
	 * Gets the event.
	 * 
	 * @return the event
	 */
	public List getEvent() {
		return this.event;
	}

	/**
	 * Sets the event.
	 * 
	 * @param pEvent
	 *            the new event
	 */
	public void setEvent(final List pEvent) {
		this.event = pEvent;
	}

	/**
	 * Gets the max envelopes.
	 * 
	 * @return the max envelopes
	 */
	public long getMaxEnvelopes() {
		return this.maxEnvelopes;
	}

	/**
	 * Sets the max envelopes.
	 * 
	 * @param pMaxEnvelopes
	 *            the new max envelopes
	 */
	public void setMaxEnvelopes(final long pMaxEnvelopes) {
		this.maxEnvelopes = pMaxEnvelopes;
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
	 * Sets the current time.
	 * 
	 * @param pCurrentTime
	 *            the new current time
	 */
	public void setCurrentTime(final long pCurrentTime) {
		this.currentTime = pCurrentTime;
	}

	/**
	 * Gets the retry count.
	 * 
	 * @return the retry count
	 */
	public long getRetryCount() {
		return this.retryCount;
	}

	/**
	 * Gets the parameter list.
	 * 
	 * @return the parameter list
	 */
	public List getParameterList() {
		return this.parameterList;
	}

	/**
	 * Sets the parameter list.
	 * 
	 * @param pParameterList
	 *            the new parameter list
	 */
	public void setParameterList(final List pParameterList) {
		this.parameterList = pParameterList;
	}

	/** The current time. */
	private long currentTime;
	/** The retry count. */
	private final long retryCount;
	/** The parameter list. */
	private List parameterList = new ArrayList();
	/** The parameter data. */
	private final IParameterData parameterData;

	/**
	 * Instantiates a new inform.
	 * 
	 * @param pParameterData
	 *            the parameter data
	 * @param retry
	 *            the retry
	 */
	public Inform(final IParameterData pParameterData, final long retry) {
		this.parameterData = pParameterData;
		this.deviceId = new DeviceIdStruct(pParameterData);
		this.retryCount = retry;
	}

	/**
	 * construct the events list for an inform. As some Event code must be
	 * unique the duplicate some event are remove.
	 */
	private void constructEvents() {
		Map mapEventnameEventBehavior = RPCMethodMng.getMapEventnameEventBehavior();
		List eventToBeRemove = new ArrayList();
		Object[] events = this.parameterData.getEventsArray();
		EventStruct evt;
		EventBehavior eb;
		for (int i = 0; i < events.length; i++) {
			evt = (EventStruct) events[i];
			eb = (EventBehavior) mapEventnameEventBehavior.get(evt.getEventCode());
			if (eb == null || (eb.isMustBeSingle() && eb.getCount() == 0) || !eb.isMustBeSingle()) {
				this.event.add(evt);
				if (eb != null) {
					eb.setCount(eb.getCount() + 1);
				}
			} else {
				eventToBeRemove.add(evt);
			}
		}
		// auto remove the duplicate event.
		Iterator it = eventToBeRemove.iterator();
		while (it.hasNext()) {
			this.parameterData.deleteEvent((EventStruct) it.next());
		}
	}

	/**
	 * Construct parameter.
	 * 
	 * @param sessionId
	 *            the session id
	 */
	private void constructParameter(final String sessionId) {
		Object[] obs = this.parameterData.getParametersArray();
		for (int i = 0; i < obs.length; i++) {
			Parameter param = (Parameter) obs[i];
			if (param.isMandatoryNotification()) {
				this.parameterData.getSetParamChanged().add(param);
			}
		}
		obs = this.parameterData.getSetParamChanged().toArray();
		for (int i = 0; i < obs.length; i++) {
			Parameter param = (Parameter) obs[i];
			param.getType();
			ParameterValueStruct pvs = new ParameterValueStruct(param.getName(), param.getTextValue(sessionId),
					param.getType());
			this.parameterList.add(pvs);
		}
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
	 * Perform.
	 * 
	 * @param session
	 *            the session
	 * @throws Fault
	 *             the fault
	 * @see RPCMethod#perform(Session)
	 */
	public void perform(final Session session) throws Fault {
		constructEvents();
		constructParameter(session.getSessionId());
		session.doSoapRequest(informEncoder.encode(this), this.id);
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

		// if (this.operations != null) {
		// for (int i = 0; i < this.operations.length; i = i + 1) {
		// if (i == 0) {
		// operationsAsAString = "[";
		// }
		// if (i == (this.operations.length - 1)) {
		// operationsAsAString = operationsAsAString
		// + this.operations[i] + "]";
		// } else {
		// operationsAsAString = operationsAsAString
		// + this.operations[i] + ",";
		// }
		// }
		// }
		String toString = this.getClass().getName() + "[" + "NAME=" + NAME + ",id=" + this.id + ",deviceId="
				+ this.deviceId + ",event=" + this.event + ",maxEnvelopes=" + this.maxEnvelopes + ",informEncoder="
				+ Inform.informEncoder + ",currentTime=" + this.currentTime + ",retryCount=" + this.retryCount
				+ ",parameterList=" + this.parameterList + ",parameterData=" + this.parameterData + "]";
		return toString;
	}

}
