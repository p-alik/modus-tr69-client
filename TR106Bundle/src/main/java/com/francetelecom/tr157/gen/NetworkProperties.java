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
 * Class NetworkProperties.
 * 
 * @author OrangeLabs R&D
 */
public class NetworkProperties {
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
	public NetworkProperties(final IParameterData pData, final String pBasePath) {
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

		paramTCPImplementation = createTCPImplementation();
		paramMaxTCPWindowSize = createMaxTCPWindowSize();
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramTCPImplementation;

	/**
	 * Getter method of TCPImplementation.
	 * 
	 * @return _TCPImplementation
	 */
	public final com.francetelecom.admindm.model.Parameter getParamTCPImplementation() {
		return paramTCPImplementation;
	}

	/**
	 * Create the parameter TCPImplementation
	 * 
	 * @return TCPImplementation
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createTCPImplementation()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "TCPImplementation");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramMaxTCPWindowSize;

	/**
	 * Getter method of MaxTCPWindowSize.
	 * 
	 * @return _MaxTCPWindowSize
	 */
	public final com.francetelecom.admindm.model.Parameter getParamMaxTCPWindowSize() {
		return paramMaxTCPWindowSize;
	}

	/**
	 * Create the parameter MaxTCPWindowSize
	 * 
	 * @return MaxTCPWindowSize
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createMaxTCPWindowSize()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "MaxTCPWindowSize");
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