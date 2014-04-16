/**
 * Product Name : Modus TR-069 Orange
 *
 * Copyright c 2014 Orange
 *
 * This software is distributed under the Apache License, Version 2.0
 * (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 or see the "license.txt" file for
 * more details
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author: Antonin Chazalet - Orange
 * Mail: antonin.chazalet@orange.com;antonin.chazale@gmail.com
 */

package com.francetelecom.admindm.hosts1profile;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.hosts1profile.exceptions.Hosts1ProfileException;
import com.francetelecom.admindm.hosts1profile.model.Hosts1ProfileDataModel;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public final class Activator implements BundleActivator {

	private static final String HOSTS_1_PROFILE_BUNDLE = "Hosts1ProfileBundle";

	private BundleContext bundleContext;

	private DevicesServiceListener devicesServiceListener;

	/** The rpc mng service. */
	private IParameterData pmDataSvc;

	public void start(final BundleContext bundleContext) throws Exception {
		this.bundleContext = bundleContext;
		ServiceReference parameterDataServiceRef = bundleContext.getServiceReference(IParameterData.class.getName());
		if (parameterDataServiceRef != null) {
			this.pmDataSvc = (IParameterData) bundleContext.getService(parameterDataServiceRef);
			Log.info("Start " + HOSTS_1_PROFILE_BUNDLE + ", after IParameterData");
		} else {
			throw new Hosts1ProfileException("Unable to start " + HOSTS_1_PROFILE_BUNDLE
					+ ": IParameterData is missing", null);
		}

		Log.info("Start " + HOSTS_1_PROFILE_BUNDLE + ", creation");
		this.devicesServiceListener = new DevicesServiceListener(this.pmDataSvc, this.bundleContext);
		this.bundleContext.addServiceListener(this.devicesServiceListener);
		try {
			Log.info(HOSTS_1_PROFILE_BUNDLE + ", Modification du datamodel");
			// Hosts1ProfileDataModel hosts1ProfileDataModel =
			new Hosts1ProfileDataModel(this.pmDataSvc);
		} catch (Fault e) {
			Log.error("Execution error: " + e.getMessage(), e);
			throw new Hosts1ProfileException("Execution error: " + e.getMessage(), null);
		}
	}

	public void stop(final BundleContext bundleContext) throws Exception {
		Log.info("Stop " + HOSTS_1_PROFILE_BUNDLE);
		bundleContext.removeServiceListener(this.devicesServiceListener);
		this.pmDataSvc = null;
	}

}
