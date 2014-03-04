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
import com.francetelecom.admindm.model.CheckBoolean;
import com.francetelecom.admindm.model.CheckEnum;
import com.francetelecom.admindm.model.CheckLength;
import com.francetelecom.admindm.model.CheckMaximum;
import com.francetelecom.admindm.model.CheckMinimum;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * Class Parameter.
 * 
 * @author OrangeLabs R&D
 */
public class Parameter {
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
	public Parameter(final IParameterData pData, final String pBasePath) {
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
		return this.data;
	}

	/**
	 * Get the basePath.
	 * 
	 * @return the basePath
	 */
	public final String getBasePath() {
		return this.basePath;
	}

	/**
	 * Initialiser.
	 */
	public void initialize() throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath);
		param.setType(ParameterType.ANY);

		this.paramValues = createValues();
		this.paramHighThreshold = createHighThreshold();
		this.paramSuspectData = createSuspectData();
		this.paramEnable = createEnable();
		this.paramCalculationMode = createCalculationMode();
		this.paramFailures = createFailures();
		this.paramSampleMode = createSampleMode();
		this.paramReference = createReference();
		this.paramLowThreshold = createLowThreshold();
		this.paramSampleSeconds = createSampleSeconds();
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramValues;

	/**
	 * Getter method of Values.
	 * 
	 * @return _Values
	 */
	public final com.francetelecom.admindm.model.Parameter getParamValues() {
		return this.paramValues;
	}

	/**
	 * Create the parameter Values
	 * 
	 * @return Values
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createValues()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath + "Values");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.STRING);
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramHighThreshold;

	/**
	 * Getter method of HighThreshold.
	 * 
	 * @return _HighThreshold
	 */
	public final com.francetelecom.admindm.model.Parameter getParamHighThreshold() {
		return this.paramHighThreshold;
	}

	/**
	 * Create the parameter HighThreshold
	 * 
	 * @return HighThreshold
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createHighThreshold()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "HighThreshold");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.INT);
		param.setValue(Integer.valueOf("0"));
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramSuspectData;

	/**
	 * Getter method of SuspectData.
	 * 
	 * @return _SuspectData
	 */
	public final com.francetelecom.admindm.model.Parameter getParamSuspectData() {
		return this.paramSuspectData;
	}

	/**
	 * Create the parameter SuspectData
	 * 
	 * @return SuspectData
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createSuspectData()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "SuspectData");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.STRING);
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramEnable;

	/**
	 * Getter method of Enable.
	 * 
	 * @return _Enable
	 */
	public final com.francetelecom.admindm.model.Parameter getParamEnable() {
		return this.paramEnable;
	}

	/**
	 * Create the parameter Enable
	 * 
	 * @return Enable
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createEnable()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath + "Enable");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.BOOLEAN);
		param.addCheck(CheckBoolean.getInstance());
		param.setValue(Boolean.FALSE);
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramCalculationMode;

	/**
	 * Getter method of CalculationMode.
	 * 
	 * @return _CalculationMode
	 */
	public final com.francetelecom.admindm.model.Parameter getParamCalculationMode() {
		return this.paramCalculationMode;
	}

	/**
	 * Create the parameter CalculationMode
	 * 
	 * @return CalculationMode
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createCalculationMode()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "CalculationMode");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.setValue("");
		String[] values = { "Latest", "Minimum", "Maximum", "Average", };
		param.addCheck(new CheckEnum(values));
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramFailures;

	/**
	 * Getter method of Failures.
	 * 
	 * @return _Failures
	 */
	public final com.francetelecom.admindm.model.Parameter getParamFailures() {
		return this.paramFailures;
	}

	/**
	 * Create the parameter Failures
	 * 
	 * @return Failures
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createFailures()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath + "Failures");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.UINT);
		param.addCheck(new CheckMinimum(0));
		param.addCheck(new CheckMaximum(4294967295L));
		param.setValue(new Long(0));
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramSampleMode;

	/**
	 * Getter method of SampleMode.
	 * 
	 * @return _SampleMode
	 */
	public final com.francetelecom.admindm.model.Parameter getParamSampleMode() {
		return this.paramSampleMode;
	}

	/**
	 * Create the parameter SampleMode
	 * 
	 * @return SampleMode
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createSampleMode()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "SampleMode");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.setValue("");
		String[] values = { "Current", "Change", };
		param.addCheck(new CheckEnum(values));
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramReference;

	/**
	 * Getter method of Reference.
	 * 
	 * @return _Reference
	 */
	public final com.francetelecom.admindm.model.Parameter getParamReference() {
		return this.paramReference;
	}

	/**
	 * Create the parameter Reference
	 * 
	 * @return Reference
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createReference()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data
				.createOrRetrieveParameter(this.basePath + "Reference");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(256));
		param.setValue("");
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramLowThreshold;

	/**
	 * Getter method of LowThreshold.
	 * 
	 * @return _LowThreshold
	 */
	public final com.francetelecom.admindm.model.Parameter getParamLowThreshold() {
		return this.paramLowThreshold;
	}

	/**
	 * Create the parameter LowThreshold
	 * 
	 * @return LowThreshold
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createLowThreshold()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "LowThreshold");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.INT);
		param.setValue(Integer.valueOf("0"));
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramSampleSeconds;

	/**
	 * Getter method of SampleSeconds.
	 * 
	 * @return _SampleSeconds
	 */
	public final com.francetelecom.admindm.model.Parameter getParamSampleSeconds() {
		return this.paramSampleSeconds;
	}

	/**
	 * Create the parameter SampleSeconds
	 * 
	 * @return SampleSeconds
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createSampleSeconds()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "SampleSeconds");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.STRING);
		param.setValue("");
		param.setWritable(false);
		return param;
	}

}