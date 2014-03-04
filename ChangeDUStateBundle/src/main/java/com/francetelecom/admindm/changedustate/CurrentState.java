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

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 * 
 *        TR-069 Issue 1 Amendment 4 Table 77 Page 117.
 * 
 *        CurrentState string The current state of the affected DU. This state was attained either by completing a
 *        requested Operation in the ChangeDUState method or reflects the state of the DU after a failed attempt to
 *        change its state.
 * 
 *        The following values are defined:
 */
public class CurrentState {

	/**
	 * Installed: The DU is in an Installed state due to one of the following: successful Install, successful Update,
	 * failed Update, or failed Uninstall. In the case of a failed Update or failed Uninstall the Fault argument will
	 * contain an explanation of the failure.
	 */
	public static final String Installed = "Installed";

	/**
	 * Uninstalled: The DU was successfully Uninstalled from the device.
	 */
	public static final String Uninstalled = "Uninstalled";

	/**
	 * Failed: The DU could not be installed in which case a DU instance MUST NOT be created in the Data Model.
	 */
	public static final String Failed = "Failed";

}
