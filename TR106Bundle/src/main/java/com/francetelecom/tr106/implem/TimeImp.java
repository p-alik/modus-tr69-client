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

import java.util.TimeZone;

import com.francetelecom.admindm.api.Getter;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr106.gen.Time;

/**
 * The Class TimeImp.
 */
public class TimeImp extends Time {
	/**
	 * Instantiates a new time imp.
	 * 
	 * @param data
	 *            the data
	 * @param basePath
	 *            the base path
	 */
	public TimeImp(IParameterData data, String basePath) {
		super(data, basePath);
	}

	/**
	 * Initialize.
	 * 
	 * @throws Fault
	 *             the fault
	 * @see com.francetelecom.tr106.gen.Time#initialize()
	 */
	public void initialize() throws Fault {
		super.initialize();
		getParamCurrentLocalTime().setGetter(new GetCurrentLocalTime());
		getParamCurrentLocalTime().setStorageMode(StorageMode.COMPUTED);
		getParamLocalTimeZone().setGetter(new GetLocalTimeZone());
		getParamLocalTimeZone().setStorageMode(StorageMode.COMPUTED);
	}

	/**
	 * The Class GetTime.
	 */
	class GetCurrentLocalTime implements Getter {
		/**
		 * Gets the.
		 * 
		 * @param sessionId
		 *            the session id
		 * @return the object
		 * @see com.francetelecom.admindm.api.Getter#get(java.lang.String)
		 */
		public Object get(final String sessionId) {
			return new Long(System.currentTimeMillis());
		}
	}

	/**
	 * The Class GetTimeZone.
	 */
	class GetLocalTimeZone implements Getter {
		/**
		 * Gets the.
		 * 
		 * @param sessionId
		 *            the session id
		 * @return the object
		 * @see com.francetelecom.admindm.api.Getter#get(java.lang.String)
		 */
		public Object get(final String sessionId) {
			TimeZone zone = TimeZone.getDefault();
			return zone.getDisplayName();
		}
	}
}
