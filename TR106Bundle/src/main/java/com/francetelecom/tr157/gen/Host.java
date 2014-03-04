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
 * Class Host.
 * 
 * @author OrangeLabs R&D
 */
public class Host {
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
	public Host(final IParameterData pData, final String pBasePath) {
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

		paramName = createName();
		paramEnable = createEnable();
		paramType = createType();
		paramDeviceNumberOfEntries = createDeviceNumberOfEntries();
		paramUSBVersion = createUSBVersion();
		paramPowerManagementEnable = createPowerManagementEnable();
		paramReset = createReset();
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramName;

	/**
	 * Getter method of Name.
	 * 
	 * @return _Name
	 */
	public final com.francetelecom.admindm.model.Parameter getParamName() {
		return paramName;
	}

	/**
	 * Create the parameter Name
	 * 
	 * @return Name
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createName()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "Name");
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
	private com.francetelecom.admindm.model.Parameter paramType;

	/**
	 * Getter method of Type.
	 * 
	 * @return _Type
	 */
	public final com.francetelecom.admindm.model.Parameter getParamType() {
		return paramType;
	}

	/**
	 * Create the parameter Type
	 * 
	 * @return Type
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createType()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "Type");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.setValue("");
		String[] values = { "OHCI", "EHCI", "UHCI", "xHCI", };
		param.addCheck(new CheckEnum(values));
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramDeviceNumberOfEntries;

	/**
	 * Getter method of DeviceNumberOfEntries.
	 * 
	 * @return _DeviceNumberOfEntries
	 */
	public final com.francetelecom.admindm.model.Parameter getParamDeviceNumberOfEntries() {
		return paramDeviceNumberOfEntries;
	}

	/**
	 * Create the parameter DeviceNumberOfEntries
	 * 
	 * @return DeviceNumberOfEntries
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createDeviceNumberOfEntries()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath
				+ "DeviceNumberOfEntries");
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
	private com.francetelecom.admindm.model.Parameter paramUSBVersion;

	/**
	 * Getter method of USBVersion.
	 * 
	 * @return _USBVersion
	 */
	public final com.francetelecom.admindm.model.Parameter getParamUSBVersion() {
		return paramUSBVersion;
	}

	/**
	 * Create the parameter USBVersion
	 * 
	 * @return USBVersion
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createUSBVersion()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "USBVersion");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(4));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramPowerManagementEnable;

	/**
	 * Getter method of PowerManagementEnable.
	 * 
	 * @return _PowerManagementEnable
	 */
	public final com.francetelecom.admindm.model.Parameter getParamPowerManagementEnable() {
		return paramPowerManagementEnable;
	}

	/**
	 * Create the parameter PowerManagementEnable
	 * 
	 * @return PowerManagementEnable
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createPowerManagementEnable()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath
				+ "PowerManagementEnable");
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
	private com.francetelecom.admindm.model.Parameter paramReset;

	/**
	 * Getter method of Reset.
	 * 
	 * @return _Reset
	 */
	public final com.francetelecom.admindm.model.Parameter getParamReset() {
		return paramReset;
	}

	/**
	 * Create the parameter Reset
	 * 
	 * @return Reset
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createReset()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "Reset");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setHidden(true);
		param.setType(ParameterType.BOOLEAN);
		param.addCheck(CheckBoolean.getInstance());
		param.setValue(Boolean.FALSE);
		param.setWritable(true);
		return param;
	}

}