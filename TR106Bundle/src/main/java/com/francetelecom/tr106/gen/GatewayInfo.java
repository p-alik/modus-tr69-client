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

package com.francetelecom.tr106.gen;

import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.CheckLength;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * Class GatewayInfo.
 * 
 * @author OrangeLabs R&D
 */
public class GatewayInfo {
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
	public GatewayInfo(final IParameterData pData, final String pBasePath) {
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

		paramSerialNumber = createSerialNumber();
		paramManufacturerOUI = createManufacturerOUI();
		paramProductClass = createProductClass();
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramSerialNumber;

	/**
	 * Getter method of SerialNumber.
	 * 
	 * @return _SerialNumber
	 */
	public final com.francetelecom.admindm.model.Parameter getParamSerialNumber() {
		return paramSerialNumber;
	}

	/**
	 * Create the parameter SerialNumber
	 * 
	 * @return SerialNumber
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createSerialNumber()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "SerialNumber");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(64));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramManufacturerOUI;

	/**
	 * Getter method of ManufacturerOUI.
	 * 
	 * @return _ManufacturerOUI
	 */
	public final com.francetelecom.admindm.model.Parameter getParamManufacturerOUI() {
		return paramManufacturerOUI;
	}

	/**
	 * Create the parameter ManufacturerOUI
	 * 
	 * @return ManufacturerOUI
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createManufacturerOUI()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "ManufacturerOUI");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(6));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramProductClass;

	/**
	 * Getter method of ProductClass.
	 * 
	 * @return _ProductClass
	 */
	public final com.francetelecom.admindm.model.Parameter getParamProductClass() {
		return paramProductClass;
	}

	/**
	 * Create the parameter ProductClass
	 * 
	 * @return ProductClass
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createProductClass()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "ProductClass");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(64));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

}