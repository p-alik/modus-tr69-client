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

package com.francetelecom.tr157.implem.periodicstatistic;

import com.francetelecom.admindm.api.Setter;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author mpcy8647
 * 
 */
public class EnableParameterSetter implements Setter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.francetelecom.admindm.api.Setter#set(com.francetelecom.admindm.model
	 * .Parameter, java.lang.Object)
	 */
	public void set(Parameter parameter, Object value) throws Fault {
		// set the new value
		parameter.setDirectValue(value);

		// discard any stored sample values
	}

}
