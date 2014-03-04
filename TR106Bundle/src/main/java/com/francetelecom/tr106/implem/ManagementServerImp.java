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

import java.util.Observable;
import java.util.Observer;

import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr106.gen.ManagementServer;

/**
 * The Class ManagementServerImp.
 */
public class ManagementServerImp extends ManagementServer implements Observer {
	/**
	 * Instantiates a new management server imp.
	 * 
	 * @param data
	 *            the data
	 * @param basePath
	 *            the base path
	 */
	public ManagementServerImp(final IParameterData data, final String basePath) {
		super(data, basePath);
	}

	/**
	 * Initialize.
	 * 
	 * @throws Fault
	 *             the fault
	 * @see com.francetelecom.tr106.gen.ManagementServer#initialize()
	 */
	public void initialize() throws Fault {
		super.initialize();
		getParamPeriodicInformTime().setValueWithout(new Long(0));
		getParamPeriodicInformInterval().setValueWithout(new Long(30));
		getParamPeriodicInformEnable().setValueWithout(Boolean.FALSE);
		getParamNATDetected().setValueWithout(Boolean.FALSE);
		getParamNATDetected().setMandatoryNotification(true);
		getParamNATDetected().setNotification(2);
		getParamSTUNEnable().setValueWithout(Boolean.FALSE);
		getParamSTUNEnable().setMandatoryNotification(true);
		getParamUDPConnectionRequestAddress().setMandatoryNotification(true);
		getParamUDPConnectionRequestAddress().setNotification(2);
		Log.debug("Entree initialize Observer :");
		getParamURL().addObserver(this);

		// getParamConnectionRequestUsername().setMandatoryNotification(true);
		// getParamConnectionRequestPassword().setMandatoryNotification(true);
	}

	public void update(final Observable arg0, final Object arg1) {
		if (arg0 == getParamURL()) {
			this.getData().addEvent(new EventStruct(EventCode.BOOTSTRAP, ""));
			this.getData().getCom().requestNewSession();
		}
	}
}
