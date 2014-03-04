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

import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.tr106.gen.Config;

/**
 * The Class ConfigImp.
 */
public class ConfigImp extends Config {
	/**
	 * Instantiates a new config imp.
	 * 
	 * @param data
	 *            the data
	 * @param basePath
	 *            the base path
	 */
	public ConfigImp(IParameterData data, String basePath) {
		super(data, basePath);
	}
}
