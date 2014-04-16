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

package com.francetelecom.admindm.sm_baseline1profile;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.sm_baseline1profile.exceptions.SM_Baseline1ProfileProfileException;
import com.francetelecom.admindm.sm_baseline1profile.model.SM_Baseline1ProfileDataModel;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author: Antonin Chazalet - Orange
 * @mail: antonin.chazalet@orange.com;antonin.chazale@gmail.com
 */
public final class Activator implements BundleActivator {

	private static final String SM_BASELINE_1_PROFILE_BUNDLE = "SM_Baseline1ProfileBundle";

	private DuAndEuEventListener duAndEuEventListener;

	/** The rpc mng service. */
	private IParameterData pmDataSvc;

	public void start(final BundleContext bundleContext) throws Exception {
		ServiceReference parameterDataServiceRef = bundleContext.getServiceReference(IParameterData.class.getName());

		if (parameterDataServiceRef != null) {
			this.pmDataSvc = (IParameterData) bundleContext.getService(parameterDataServiceRef);
			Log.info("Start " + SM_BASELINE_1_PROFILE_BUNDLE + ", after IParameterData");
		} else {
			throw new SM_Baseline1ProfileProfileException("Unable to start " + SM_BASELINE_1_PROFILE_BUNDLE
					+ ": IParameterData is missing", null);
		}

		Log.info("Start " + SM_BASELINE_1_PROFILE_BUNDLE + ", creation");

		try {
			Log.info(SM_BASELINE_1_PROFILE_BUNDLE + ", Modification du datamodel");
			// SM_Baseline1ProfileDataModel sm_Baseline1ProfileDataModel =
			new SM_Baseline1ProfileDataModel(bundleContext, this.pmDataSvc);
		} catch (Fault e) {
			Log.error("Execution error: " + e.getMessage(), e);
			throw new SM_Baseline1ProfileProfileException("Execution error: " + e.getMessage(), null);
		}

		this.duAndEuEventListener = new DuAndEuEventListener(bundleContext, this.pmDataSvc);
		bundleContext.addBundleListener(this.duAndEuEventListener);
	}

	public void stop(final BundleContext bundleContext) throws Exception {
		Log.info("Stop " + SM_BASELINE_1_PROFILE_BUNDLE);
		bundleContext.removeBundleListener(this.duAndEuEventListener);
		this.pmDataSvc = null;
	}
	
}
