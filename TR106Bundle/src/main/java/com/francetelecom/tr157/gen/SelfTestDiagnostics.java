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
 * Generated : 21 oct. 2009 by GenModel
 */

package com.francetelecom.tr157.gen;

import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.CheckEnum;
import com.francetelecom.admindm.model.CheckLength;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * Class SelfTestDiagnostics.
 * 
 * @author OrangeLabs R&D
 */
public class SelfTestDiagnostics {
	/** The data. */
	private final IParameterData data;
	/** The base path. */
	private final String basePath;

	/**
	 * Default constructor.
	 * 
	 * @param pData
	 *            data model
	 * @param pBasePath
	 *            base path of attribute
	 * @param pPersist
	 *            persistence
	 */
	public SelfTestDiagnostics(final IParameterData pData,
			final String pBasePath) {
		super();
		this.data = pData;
		this.basePath = pBasePath;
	}

	/**
	 * Get the data.
	 * 
	 * @return the data
	 */
	public final IParameterData getData() {
		return data;
	}

	/**
	 * Get the basePath.
	 * 
	 * @return the basePath
	 */
	public final String getBasePath() {
		return basePath;
	}

	/**
	 * Initialiser.
	 */
	public void initialize() throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath);
		param.setType(ParameterType.ANY);

		paramDiagnosticsState = createDiagnosticsState();
		paramResults = createResults();
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramDiagnosticsState;

	/**
	 * Getter method of DiagnosticsState.
	 * 
	 * @return _DiagnosticsState
	 */
	public final com.francetelecom.admindm.model.Parameter getParamDiagnosticsState() {
		return paramDiagnosticsState;
	}

	/**
	 * Create the parameter DiagnosticsState
	 * 
	 * @return DiagnosticsState
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createDiagnosticsState()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "DiagnosticsState");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.STRING);
		param.setValue("");
		String[] values = { "None", "Requested", "Complete", "Error_Internal",
				"Error_Other", };
		param.addCheck(new CheckEnum(values));
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramResults;

	/**
	 * Getter method of Results.
	 * 
	 * @return _Results
	 */
	public final com.francetelecom.admindm.model.Parameter getParamResults() {
		return paramResults;
	}

	/**
	 * Create the parameter Results
	 * 
	 * @return Results
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createResults()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "Results");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(1024));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

}