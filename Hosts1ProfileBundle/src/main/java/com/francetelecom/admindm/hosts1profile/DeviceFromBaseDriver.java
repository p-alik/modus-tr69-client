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

/**
 * @see L2 2 2b-AccesAuxEquipements-BaseDrivers-v02.docx specification.
 * 
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class DeviceFromBaseDriver {

	/**
	 * Property MUST String[]. Le premier element du tableau prenant comme valeurs : X3D,ZiigBee,USB,UPnP… Connectivity
	 * technology [1], could be completed as needed.
	 */
	public static final String DEVICE_CATEGORY_KEY = "DEVICE_CATEGORY";

	/**
	 * Property SHOULD String. Description d equipement selon la specification OSGi Device Access [1]
	 */
	public static final String DEVICE_DESCRIPTION_KEY = "DEVICE_DESCRIPTION";

	/**
	 * Property SHOULD String. Numero de serie de l equipement selon la specification OSGi Device Access [1] OSGi Device
	 * Access [1]
	 */
	public static final String DEVICE_SERIAL_KEY = "DEVICE_SERIAL";

	/**
	 * Property MUST String. Un identifiant unique selon la specification OSGi Device Access [1] service Persistent
	 * ID[1]
	 */
	public static final String SERVICE_PID_KEY = "service.pid";

	/**
	 * Property MUST String. Un nom d equipement en anglais d un ou plusieurs mots et de moins de 25 caracteres. si
	 * cette proprieté n est pas presente, les 25 premiers caracteres de DEVICE_DESCRIPTION seront utilises pour
	 * afficher l equipement dans l interface utilisateur
	 */
	public static final String DEVICE_FRIENDLY_NAME_KEY = "DEVICE_FRIENDLY_NAME";

	private String[] deviceCategory;
	private String deviceDescription;
	private String deviceSerial;
	private String servicePid;
	private String deviceFriendlyName;

	public DeviceFromBaseDriver(final String[] deviceCategory, final String deviceDescription,
			final String deviceSerial, final String servicePid, final String deviceFriendlyName) throws Exception {
		if (deviceCategory == null) {
			throw new Exception("deviceCategory can NOT be null");
		}
		if (deviceDescription == null) {
			throw new Exception("deviceDescription can NOT be null");
		}
		if (deviceSerial == null) {
			throw new Exception("deviceSerial can NOT be null");
		}
		if (deviceFriendlyName == null) {
			throw new Exception("deviceFriendlyName can NOT be null");
		}
		if (servicePid == null) {
			throw new Exception("servicePid can NOT be null");
		} else if (servicePid.indexOf(".") >= 0) {
			throw new Exception("servicePid can NOT contain \".\" due to TR069/Modus constraints.");
		}

		this.deviceCategory = deviceCategory;
		this.deviceDescription = deviceDescription;
		this.deviceSerial = deviceSerial;
		this.servicePid = servicePid;
		this.deviceFriendlyName = deviceFriendlyName;
	}

	public String[] getDeviceCategory() {
		return this.deviceCategory;
	}

	public String getDeviceDescription() {
		return this.deviceDescription;
	}

	public String getDeviceSerial() {
		return this.deviceSerial;
	}

	public String getServicePid() {
		return this.servicePid;
	}

	public String getDeviceFriendlyName() {
		return this.deviceFriendlyName;
	}

	public String toString() {
		String deviceCategoryAsAString = null;
		if ((deviceCategory != null) && (deviceCategory.length > 0)) {
			deviceCategoryAsAString = deviceCategory[0];
			for (int i = 1; i < deviceCategory.length; i = i + 1) {
				deviceCategoryAsAString += ";;" + deviceCategory[i];
			}
		}
		return DeviceFromBaseDriver.class.getName() + "[deviceCategory=" + deviceCategoryAsAString
				+ ",deviceDescription=" + this.deviceDescription + ",deviceSerial=" + this.deviceSerial
				+ ",servicePid=" + this.servicePid + ",deviceFriendlyName=" + this.deviceFriendlyName + "]";
	}
	
}
