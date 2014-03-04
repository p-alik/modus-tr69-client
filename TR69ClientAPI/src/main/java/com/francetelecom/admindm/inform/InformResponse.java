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

import java.util.Map;

import com.francetelecom.admindm.RPCMethodMng;
import com.francetelecom.admindm.api.EventBehavior;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterValueStruct;

/**
 * The Class InformResponse.
 */
public final class InformResponse implements RPCMethod {
	/** The Constant NAME. */
	public static final String NAME = "InformResponse";

	/**
	 * Get the name of RPCMethod.
	 * 
	 * @return String
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
	 * @see RPCMethod#perform(Session)
	 */
	public void perform(final Session session) {
		IParameterData data = session.getParameterData();
		Inform inform = (Inform) session.getLastRPCMethod();
		Map map = RPCMethodMng.getMapEventnameEventBehavior();
		while (!inform.getEvent().isEmpty()) {
			EventStruct evt = (EventStruct) (inform.getEvent().remove(0));
			EventBehavior behavior = (EventBehavior) map.get(evt.getEventCode());
			if (behavior == null) {
				Log.error("Unknown EventCode use:" + evt.getEventCode());
			} else if (NAME.equals(behavior.getSuccessfulDelivery())) {
				data.deleteEvent(evt);
			}
		}
		ParameterValueStruct pvs;
		while (!inform.getParameterList().isEmpty()) {
			pvs = (ParameterValueStruct) inform.getParameterList().remove(0);
			Parameter param = data.getParameter(pvs.getName());
			data.getSetParamChanged().remove(param);
		}
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

}
