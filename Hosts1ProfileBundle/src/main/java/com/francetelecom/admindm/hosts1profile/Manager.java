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

package com.francetelecom.admindm.hosts1profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.francetelecom.admindm.api.Log;

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class Manager {

	private static Manager instance;

	/**
	 * Ordered list of Devices (from base drivers).
	 */
	private List devices = new ArrayList();

	private static final Object lock = new Object();

	/** Must be private, use getSingletonInstance(). */
	private Manager() {

	}

	/**
	 * @return the singleton instance of the class.
	 */
	public static Manager getSingletonInstance() {
		if (instance == null) {
			instance = new Manager();
		}
		return instance;
	}

	/**
	 * @return a copy of the List of Devices (from base drivers)
	 */
	public List getDevices() {
		synchronized (lock) {
			List copy = new ArrayList();
			for (int i = 0; i < this.devices.size(); i = i + 1) {
				copy.add(this.devices.get(i));
			}
			return copy;
		}
	}

	public void addADevice(final DeviceFromBaseDriver d) {
		synchronized (lock) {
			Log.debug("hosts1profile.Manager.addADevice(" + d + ")");
			boolean deviceAlreadyAppearInDevices = false;
			for (int i = 0; i < this.devices.size(); i = i + 1) {
				if (((DeviceFromBaseDriver) this.devices.get(i)).getServicePid().equals(d.getServicePid())) {
					deviceAlreadyAppearInDevices = true;
					break;
				}
			}
			if (!deviceAlreadyAppearInDevices) {
				this.devices.add(d);
			} // no "else" needed.
		}
	}

	public void removeADevice(final DeviceFromBaseDriver d) {
		synchronized (lock) {
			Log.debug("hosts1profile.Manager.removeADevice(" + d + ")");
			this.devices.remove(d);
		}
	}
	
	public DeviceFromBaseDriver getADevice(String servicePid) {
		for(Iterator it = getDevices().iterator(); it.hasNext();) {
			DeviceFromBaseDriver d = (DeviceFromBaseDriver) it.next();
			if (d.getServicePid().equals(servicePid)) {
				return d;
			}
		}
		return null;
	}

}
