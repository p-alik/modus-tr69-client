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
 */

package com.francetelecom.tr106.implem;

import com.francetelecom.admindm.api.Getter;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr106.gen.DeviceInfo;

/**
 * The Class DeviceInfoImp.
 */
public final class DeviceInfoImp extends DeviceInfo {
	/** The Constant start. */
	static final long START = System.currentTimeMillis();

	/**
	 * Instantiates a new device info imp.
	 * 
	 * @param data
	 *            the data
	 * @param basePath
	 *            the base path
	 */
	public DeviceInfoImp(final IParameterData data, final String basePath) {
		super(data, basePath);
	}

	/**
	 * Initialize.
	 * 
	 * @throws Fault
	 *             the fault
	 * @see com.francetelecom.tr106.gen.DeviceInfo#initialize()
	 */
	public void initialize() throws Fault {
		super.initialize();
		getParamDescription().setStorageMode(StorageMode.DM_ONLY);
		getParamDescription().setValue("");
		getParamManufacturerOUI().setStorageMode(StorageMode.DM_ONLY);
		getParamManufacturer().setStorageMode(StorageMode.DM_ONLY);
		getParamUpTime().setGetter(new GetUpTime());
		getParamFirstUseDate().setValue(new Long(0));
	}

	/**
	 * The Class GetUpTime.
	 */
	final class GetUpTime implements Getter {
		/** The Constant NB_MS_PER_MINUTE. */
		private static final int NB_MS_PER_MINUTE = 1000;

		/**
		 * Gets the Value.
		 * 
		 * @param sessionId
		 *            the session id
		 * @return the object
		 * @see com.francetelecom.admindm.api.Getter#get(java.lang.String)
		 */
		public Object get(final String sessionId) {
			long now = System.currentTimeMillis();
			return new Long((now - START) / NB_MS_PER_MINUTE);
		}
	}
}
