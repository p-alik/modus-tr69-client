/**
 * Copyright France Telecom (Orange Labs R&D) 2008,  All Rights Reserved.
 *
 * This software is the confidential and proprietary information
 * of France Telecom (Orange Labs R&D). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * France Telecom (Orange Labs R&D)
 *
 * Project     : Modus
 * Software    : Library
 *
 * Author : Orange Labs R&D O.Beyler
 */

package com.francetelecom.tr157.implem;

import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.Device;

/**
 * The Class DeviceImp.
 */
public class DeviceImp extends Device {

	/**
	 * Instantiates a new device imp.
	 * 
	 * @param data
	 *            the data
	 * @param basePath
	 *            the base path
	 */
	public DeviceImp(IParameterData data, String basePath) {
		super(data, basePath);
	}

	/**
	 * Initialize.
	 * 
	 * @throws Fault
	 *             the fault
	 * 
	 * @see com.francetelecom.tr157.gen.Device#initialize()
	 */
	public void initialize() throws Fault {
		super.initialize();

		// default settings
		// Enable = false
		// UPnPMediaServer = false
		// UPnPMediaRenderer = false
		// UPnPWLANAccessPoint = false
		// UPnPQoSDevice = false
		// UPnPQoSPolicyHolder = false
		// UPnPIGD = false
		getParamEnable().setValue(Boolean.FALSE);
		getParamUPnPMediaServer().setValue(Boolean.FALSE);
		getParamUPnPMediaRenderer().setValue(Boolean.FALSE);
		getParamUPnPWLANAccessPoint().setValue(Boolean.FALSE);
		getParamUPnPQoSDevice().setValue(Boolean.FALSE);
		getParamUPnPQoSPolicyHolder().setValue(Boolean.FALSE);
		getParamUPnPIGD().setValue(Boolean.FALSE);
	}
}
