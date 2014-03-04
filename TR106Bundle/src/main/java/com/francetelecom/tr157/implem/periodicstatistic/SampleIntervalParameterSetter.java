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
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author mpcy8647
 * 
 */
public class SampleIntervalParameterSetter implements Setter {

	// The following constant is not used.
	// /** The Constant REPORT_START_TIME. */
	// private static final String REPORT_START_TIME = "ReportStartTime";

	/** The parameter data. */
	private final IParameterData parameterData;

	/** The base path. */
	private final String basePath;

	public SampleIntervalParameterSetter(IParameterData pParameterData, String pBasePath) {
		parameterData = pParameterData;
		basePath = pBasePath;
	}

	/**
	 * <p>
	 * Sets the parameter.
	 * </p>
	 * <p>
	 * The following actions are performed:
	 * <ul>
	 * <li>discards any stored samples if the Enable parameter equals true.</li>
	 * <li>starts the first sample interval if the Enable parameter equals true.
	 * </li>
	 * </ul>
	 * </p>
	 * 
	 * 
	 * 
	 * @param parameter
	 *            the parameter
	 * @param value
	 *            the value
	 * 
	 * @throws Fault
	 *             the fault
	 * 
	 * @see com.francetelecom.admindm.api.Setter#set(com.francetelecom.admindm.model.Parameter,
	 *      java.lang.Object)
	 */
	public void set(Parameter parameter, Object value) throws Fault {
		Parameter enableParameter = parameterData.getParameter(basePath + "Enable");
		if (enableParameter != null) {
			Boolean enableValue = (Boolean) enableParameter.getValue();
			if (Boolean.TRUE.equals(enableValue)) {
				// discard any stored samples
				// ReportStartTime parameter

				// start the first sample
			}
		}
	}

}
