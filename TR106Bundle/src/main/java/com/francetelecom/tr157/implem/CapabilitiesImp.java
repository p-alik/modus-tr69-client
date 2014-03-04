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
import com.francetelecom.tr157.gen.Capabilities;

/**
 * The Class CapabilitiesImp.
 */
public class CapabilitiesImp extends Capabilities {

	/**
	 * Instantiates a new capabilities imp.
	 * 
	 * @param data
	 *            the data
	 * @param basePath
	 *            the base path
	 */
	public CapabilitiesImp(IParameterData data, String basePath) {
		super(data, basePath);
	}

	/**
	 * Initialize.
	 * 
	 * @throws Fault
	 *             the fault
	 * 
	 * @see com.francetelecom.tr157.gen.Capabilities#initialize()
	 */
	public void initialize() throws Fault {
		super.initialize();
		// set default values for Capabilities parameters.
		getParamUPnPArchitecture().setValue(new Long(0));
		getParamUPnPMediaServer().setValue(new Long(0));
		getParamUPnPMediaRenderer().setValue(new Long(0));
		getParamUPnPWLANAccessPoint().setValue(new Long(0));
		getParamUPnPBasicDevice().setValue(new Long(0));
		getParamUPnPQoSDevice().setValue(new Long(0));
		getParamUPnPQoSPolicyHolder().setValue(new Long(0));
		getParamUPnPIGD().setValue(new Long(0));
	}
}
