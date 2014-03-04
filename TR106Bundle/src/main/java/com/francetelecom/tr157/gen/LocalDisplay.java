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
import com.francetelecom.admindm.model.CheckMaximum;
import com.francetelecom.admindm.model.CheckMinimum;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * Class LocalDisplay.
 * 
 * @author OrangeLabs R&D
 */
public class LocalDisplay {
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
	public LocalDisplay(final IParameterData pData, final String pBasePath) {
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

		this.paramDisplayHeight = createDisplayHeight();
		this.paramPosY = createPosY();
		this.paramPosX = createPosX();
		this.paramHeight = createHeight();
		this.paramWidth = createWidth();
		this.paramResizable = createResizable();
		this.paramDisplayWidth = createDisplayWidth();
		this.paramMovable = createMovable();
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramDisplayHeight;

	/**
	 * Getter method of DisplayHeight.
	 * 
	 * @return _DisplayHeight
	 */
	public final com.francetelecom.admindm.model.Parameter getParamDisplayHeight() {
		return this.paramDisplayHeight;
	}

	/**
	 * Create the parameter DisplayHeight
	 * 
	 * @return DisplayHeight
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createDisplayHeight()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "DisplayHeight");
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
	private com.francetelecom.admindm.model.Parameter paramPosY;

	/**
	 * Getter method of PosY.
	 * 
	 * @return _PosY
	 */
	public final com.francetelecom.admindm.model.Parameter getParamPosY() {
		return this.paramPosY;
	}

	/**
	 * Create the parameter PosY
	 * 
	 * @return PosY
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createPosY()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath + "PosY");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.INT);
		param.setValue(Integer.valueOf("0"));
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramPosX;

	/**
	 * Getter method of PosX.
	 * 
	 * @return _PosX
	 */
	public final com.francetelecom.admindm.model.Parameter getParamPosX() {
		return this.paramPosX;
	}

	/**
	 * Create the parameter PosX
	 * 
	 * @return PosX
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createPosX()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath + "PosX");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.INT);
		param.setValue(Integer.valueOf("0"));
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramHeight;

	/**
	 * Getter method of Height.
	 * 
	 * @return _Height
	 */
	public final com.francetelecom.admindm.model.Parameter getParamHeight() {
		return this.paramHeight;
	}

	/**
	 * Create the parameter Height
	 * 
	 * @return Height
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createHeight()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath + "Height");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.UINT);
		param.addCheck(new CheckMinimum(0));
		param.addCheck(new CheckMaximum(4294967295L));
		param.setValue(new Long(0));
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramWidth;

	/**
	 * Getter method of Width.
	 * 
	 * @return _Width
	 */
	public final com.francetelecom.admindm.model.Parameter getParamWidth() {
		return this.paramWidth;
	}

	/**
	 * Create the parameter Width
	 * 
	 * @return Width
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createWidth()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath + "Width");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.UINT);
		param.addCheck(new CheckMinimum(0));
		param.addCheck(new CheckMaximum(4294967295L));
		param.setValue(new Long(0));
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramResizable;

	/**
	 * Getter method of Resizable.
	 * 
	 * @return _Resizable
	 */
	public final com.francetelecom.admindm.model.Parameter getParamResizable() {
		return this.paramResizable;
	}

	/**
	 * Create the parameter Resizable
	 * 
	 * @return Resizable
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createResizable()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data
				.createOrRetrieveParameter(this.basePath + "Resizable");
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
	private com.francetelecom.admindm.model.Parameter paramDisplayWidth;

	/**
	 * Getter method of DisplayWidth.
	 * 
	 * @return _DisplayWidth
	 */
	public final com.francetelecom.admindm.model.Parameter getParamDisplayWidth() {
		return this.paramDisplayWidth;
	}

	/**
	 * Create the parameter DisplayWidth
	 * 
	 * @return DisplayWidth
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createDisplayWidth()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "DisplayWidth");
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
	private com.francetelecom.admindm.model.Parameter paramMovable;

	/**
	 * Getter method of Movable.
	 * 
	 * @return _Movable
	 */
	public final com.francetelecom.admindm.model.Parameter getParamMovable() {
		return this.paramMovable;
	}

	/**
	 * Create the parameter Movable
	 * 
	 * @return Movable
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createMovable()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath + "Movable");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.BOOLEAN);
		param.addCheck(CheckBoolean.getInstance());
		param.setValue(Boolean.FALSE);
		param.setWritable(true);
		return param;
	}

}