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

package com.francetelecom.tr157.implem;

import org.osgi.service.upnp.UPnPService;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.Service;

public class ServiceImp extends Service {

	/** The Constant LEASE_ACTIVE. */
	public static final String LEASE_ACTIVE = "LeaseActive";

	/** The Constant BYE_BYE. */
	public static final String BYE_BYE = "ByeByeReceived";

	/** The upnp service. */
	private final UPnPService upnpService;

	/** The device uuid. */
	private final String deviceUuid;

	/**
	 * Instantiates a new service imp.
	 * 
	 * @param data
	 *            the data
	 * @param basePath
	 *            the base path
	 * @param pUPnPService
	 *            the u pn p service
	 * @param pDeviceUuid
	 *            the device uuid
	 */
	public ServiceImp(IParameterData data, String basePath, UPnPService pUPnPService, String pDeviceUuid) {
		super(data, basePath);
		this.upnpService = pUPnPService;
		this.deviceUuid = pDeviceUuid;
	}

	/**
	 * Initialize.
	 * 
	 * @throws Fault
	 *             the fault
	 * 
	 * @see com.francetelecom.tr157.gen.Service#initialize()
	 */
	public void initialize() throws Fault {
		super.initialize();

		// status
		getParamStatus().setValue(LEASE_ACTIVE);
		getParamStatus().setStorageMode(StorageMode.COMPUTED);

		// usn
		String usn = this.deviceUuid + "::" + upnpService.getType();
		getParamUSN().setValue(usn);
		getParamUSN().setStorageMode(StorageMode.COMPUTED);
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		try {
			getParamStatus().setValue(status);
		} catch (Fault e) {
			Log.error("unable to set the status parameter of " + getParamUSN().getValue() + " to " + status, e);
		}
	}

}
