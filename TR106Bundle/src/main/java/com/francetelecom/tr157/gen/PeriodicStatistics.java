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
import com.francetelecom.admindm.model.CheckMaximum;
import com.francetelecom.admindm.model.CheckMinimum;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * Class PeriodicStatistics.
 * 
 * @author OrangeLabs R&D
 */
public class PeriodicStatistics {
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
	public PeriodicStatistics(final IParameterData pData, final String pBasePath) {
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

		paramSampleSet = createSampleSet();
		paramMinSampleInterval = createMinSampleInterval();
		paramMaxReportSamples = createMaxReportSamples();
		paramSampleSetNumberOfEntries = createSampleSetNumberOfEntries();
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramSampleSet;

	/**
	 * Getter method of SampleSet.
	 * 
	 * @return _SampleSet
	 */
	public final com.francetelecom.admindm.model.Parameter getParamSampleSet() {
		return paramSampleSet;
	}

	/**
	 * Create the parameter SampleSet
	 * 
	 * @return SampleSet
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createSampleSet()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "SampleSet");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.ANY);
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramMinSampleInterval;

	/**
	 * Getter method of MinSampleInterval.
	 * 
	 * @return _MinSampleInterval
	 */
	public final com.francetelecom.admindm.model.Parameter getParamMinSampleInterval() {
		return paramMinSampleInterval;
	}

	/**
	 * Create the parameter MinSampleInterval
	 * 
	 * @return MinSampleInterval
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createMinSampleInterval()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "MinSampleInterval");
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
	private com.francetelecom.admindm.model.Parameter paramMaxReportSamples;

	/**
	 * Getter method of MaxReportSamples.
	 * 
	 * @return _MaxReportSamples
	 */
	public final com.francetelecom.admindm.model.Parameter getParamMaxReportSamples() {
		return paramMaxReportSamples;
	}

	/**
	 * Create the parameter MaxReportSamples
	 * 
	 * @return MaxReportSamples
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createMaxReportSamples()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "MaxReportSamples");
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
	private com.francetelecom.admindm.model.Parameter paramSampleSetNumberOfEntries;

	/**
	 * Getter method of SampleSetNumberOfEntries.
	 * 
	 * @return _SampleSetNumberOfEntries
	 */
	public final com.francetelecom.admindm.model.Parameter getParamSampleSetNumberOfEntries() {
		return paramSampleSetNumberOfEntries;
	}

	/**
	 * Create the parameter SampleSetNumberOfEntries
	 * 
	 * @return SampleSetNumberOfEntries
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createSampleSetNumberOfEntries()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath
				+ "SampleSetNumberOfEntries");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.UINT);
		param.addCheck(new CheckMinimum(0));
		param.addCheck(new CheckMaximum(4294967295L));
		param.setValue(new Long(0));
		param.setWritable(false);
		return param;
	}

}