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
 * Class Interface.
 * 
 * @author OrangeLabs R&D
 */
public class Interface {
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
	public Interface(final IParameterData pData, final String pBasePath) {
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

		paramInterfaceClass = createInterfaceClass();
		paramInterfaceNumber = createInterfaceNumber();
		paramInterfaceSubClass = createInterfaceSubClass();
		paramInterfaceProtocol = createInterfaceProtocol();
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramInterfaceClass;

	/**
	 * Getter method of InterfaceClass.
	 * 
	 * @return _InterfaceClass
	 */
	public final com.francetelecom.admindm.model.Parameter getParamInterfaceClass() {
		return paramInterfaceClass;
	}

	/**
	 * Create the parameter InterfaceClass
	 * 
	 * @return InterfaceClass
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createInterfaceClass()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "InterfaceClass");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramInterfaceNumber;

	/**
	 * Getter method of InterfaceNumber.
	 * 
	 * @return _InterfaceNumber
	 */
	public final com.francetelecom.admindm.model.Parameter getParamInterfaceNumber() {
		return paramInterfaceNumber;
	}

	/**
	 * Create the parameter InterfaceNumber
	 * 
	 * @return InterfaceNumber
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createInterfaceNumber()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "InterfaceNumber");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.UINT);
		param.addCheck(new CheckMinimum(0));
		param.addCheck(new CheckMaximum(4294967295L));
		param.setValue(new Long(0));
		param.addCheck(new CheckMaximum(255));
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramInterfaceSubClass;

	/**
	 * Getter method of InterfaceSubClass.
	 * 
	 * @return _InterfaceSubClass
	 */
	public final com.francetelecom.admindm.model.Parameter getParamInterfaceSubClass() {
		return paramInterfaceSubClass;
	}

	/**
	 * Create the parameter InterfaceSubClass
	 * 
	 * @return InterfaceSubClass
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createInterfaceSubClass()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "InterfaceSubClass");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramInterfaceProtocol;

	/**
	 * Getter method of InterfaceProtocol.
	 * 
	 * @return _InterfaceProtocol
	 */
	public final com.francetelecom.admindm.model.Parameter getParamInterfaceProtocol() {
		return paramInterfaceProtocol;
	}

	/**
	 * Create the parameter InterfaceProtocol
	 * 
	 * @return InterfaceProtocol
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createInterfaceProtocol()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "InterfaceProtocol");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setWritable(false);
		return param;
	}

}