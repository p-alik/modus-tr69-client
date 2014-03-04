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
import com.francetelecom.admindm.model.CheckBoolean;
import com.francetelecom.admindm.model.CheckMaximum;
import com.francetelecom.admindm.model.CheckMinimum;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * Class DHCPOption.
 * 
 * @author OrangeLabs R&D
 */
public class DHCPOption {
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
	public DHCPOption(final IParameterData pData, final String pBasePath) {
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

		paramValue = createValue();
		paramTag = createTag();
		paramRequest = createRequest();
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramValue;

	/**
	 * Getter method of Value.
	 * 
	 * @return _Value
	 */
	public final com.francetelecom.admindm.model.Parameter getParamValue() {
		return paramValue;
	}

	/**
	 * Create the parameter Value
	 * 
	 * @return Value
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createValue()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "Value");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramTag;

	/**
	 * Getter method of Tag.
	 * 
	 * @return _Tag
	 */
	public final com.francetelecom.admindm.model.Parameter getParamTag() {
		return paramTag;
	}

	/**
	 * Create the parameter Tag
	 * 
	 * @return Tag
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createTag()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "Tag");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.UINT);
		param.addCheck(new CheckMinimum(0));
		param.addCheck(new CheckMaximum(4294967295L));
		param.setValue(new Long(0));
		param.addCheck(new CheckMinimum(1));
		param.addCheck(new CheckMaximum(254));
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramRequest;

	/**
	 * Getter method of Request.
	 * 
	 * @return _Request
	 */
	public final com.francetelecom.admindm.model.Parameter getParamRequest() {
		return paramRequest;
	}

	/**
	 * Create the parameter Request
	 * 
	 * @return Request
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createRequest()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = data.createOrRetrieveParameter(basePath + "Request");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.BOOLEAN);
		param.addCheck(CheckBoolean.getInstance());
		param.setValue(Boolean.FALSE);
		param.setWritable(true);
		return param;
	}

}