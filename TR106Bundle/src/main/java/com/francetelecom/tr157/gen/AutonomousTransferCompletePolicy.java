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
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * Class AutonomousTransferCompletePolicy.
 * 
 * @author OrangeLabs R&D
 */
public class AutonomousTransferCompletePolicy {

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
	public AutonomousTransferCompletePolicy(final IParameterData pData,
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

		paramTransferTypeFilter = createTransferTypeFilter();
		paramEnable = createEnable();
		paramFileTypeFilter = createFileTypeFilter();
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramTransferTypeFilter;

	/**
	 * Getter method of TransferTypeFilter.
	 * 
	 * @return _TransferTypeFilter
	 */
	public final com.francetelecom.admindm.model.Parameter getParamTransferTypeFilter() {
		return paramTransferTypeFilter;
	}

	/**
	 * Create the parameter TransferTypeFilter
	 * 
	 * @return TransferTypeFilter
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createTransferTypeFilter()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "TransferTypeFilter");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.setValue("");
		String[] values = { "Upload", "Download", "Both", };
		param.addCheck(new CheckEnum(values));
		param.setWritable(true);
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
		return paramEnable;
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
		param = data.createOrRetrieveParameter(basePath + "Enable");
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
	private com.francetelecom.admindm.model.Parameter paramFileTypeFilter;

	/**
	 * Getter method of FileTypeFilter.
	 * 
	 * @return _FileTypeFilter
	 */
	public final com.francetelecom.admindm.model.Parameter getParamFileTypeFilter() {
		return paramFileTypeFilter;
	}

	/**
	 * Create the parameter FileTypeFilter
	 * 
	 * @return FileTypeFilter
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createFileTypeFilter()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "FileTypeFilter");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(1024));
		param.setValue("");
		param.setWritable(true);
		return param;
	}

}