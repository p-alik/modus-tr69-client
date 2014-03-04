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

package com.francetelecom.admindm.admincpe;

import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.SetParamValuesFault;

/**
 * @author umdo6622 Cette interface est utilisee pour une administration minimale du device
 */
public interface IAdminCpe {

	/**
	 * addEvent : insert a new event in davece Queue
	 * 
	 * @param myEventCode
	 */
	void addEvent(String myEventCode);

	/**
	 * updateValue : updtae a Parameter
	 * 
	 * @param myParameter
	 * @param myValue
	 * @throws Fault
	 */
	void updateValue(String myParameterName, String myValue) throws AdminCpeException;

	/**
	 * getValue : geive the Value of a dedicated parameter
	 * 
	 * @param myParameter
	 * @return
	 */
	String getValue(String myParameter) throws AdminCpeException;

	/** Force reboot device */
	void rebootCpe() throws AdminCpeException;

	/** Force stop device : the device stops sending inform */
	void stopCpe() throws AdminCpeException;

	/** Force start device : the device restart sending inform */
	void startCpe() throws AdminCpeException;

	/** Force device to initialise a new session */
	void forceInform() throws AdminCpeException;

	/**
	 * Give a notification level to a Parameter
	 * 
	 * @throws SetParamValuesFault
	 */
	void addNotification(String Param, int level) throws AdminCpeException;

	/** Give a notification level to a Parameter */
	int getNotification(String Param) throws AdminCpeException;

	/** Force File Transfert Download */
	void forceTransfer(String monFichier) throws AdminCpeException;

	/** Add Parameter to DataModel */
	void addParameter(String parent, String paramName, int type, int strorageMode, boolean isWritable,
			int notification, boolean setActNotDen) throws AdminCpeException;

	/** Operate internaaly a resetFactory for the device */
	void resetFactory() throws AdminCpeException;

	/** send a connection Request to the device */
	void sendConnectionRequest() throws AdminCpeException;

	/** From a datamodel file descriptor, initialise a part of datamodel */
	void loadModelFromFile(String myFileName) throws AdminCpeException;

	/** fonctionnalite de logs entre CPE et ACS : a reflechir */

}
