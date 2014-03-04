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
 * Author: Olivier Beyler - Orange
 */

package com.francetelecom.admindm.admincpe;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.model.IParameterData;

/**
 * The Class Activator.
 */
public final class Activator implements BundleActivator {
	/** The RPC method mng service ref. */
	private ServiceReference rpcMethodMngServiceRef = null;
	/** The rpc mng. */
	private RPCMethodMngService rpcMng;
	/** The RPC method mng service ref. */
	private ServiceReference parameterDataServiceRef = null;
	/** The rpc mng. */
	private IParameterData pmDataSvc;

	/**
	 * Start.
	 * 
	 * @param context
	 *            the context
	 * @throws Exception
	 *             the exception
	 */
	public void start(final BundleContext context) throws Exception {

		this.rpcMethodMngServiceRef = context.getServiceReference(RPCMethodMngService.class.getName());
		if (this.rpcMethodMngServiceRef != null) {
			this.rpcMng = (RPCMethodMngService) context.getService(this.rpcMethodMngServiceRef);
			Log.info("Start Admin CPE Bundle, apres RPCMethodMngService");
		} else {
			Log.error("Unable to start Admin CPE Bundle: RPCMethodMngService is missing");
		}
		this.parameterDataServiceRef = context.getServiceReference(IParameterData.class.getName());
		if (this.parameterDataServiceRef != null) {
			this.pmDataSvc = (IParameterData) context.getService(this.parameterDataServiceRef);
			Log.info("Start Admin CPE Bundle, apres IParameterData");
		} else {
			Log.error("Unable to start Admin CPE Bundle: IParameterData is missing");
		}
		AdminCpe adminCpe = new AdminCpe(this.rpcMng, this.pmDataSvc);
		context.registerService(IAdminCpe.class.getName(), adminCpe, null);
		Log.info("Start Admin CPE Bundle, creation AdminCpe");

	}

	/**
	 * Stop.
	 * 
	 * @param context
	 *            the context
	 * @throws Exception
	 *             the exception
	 */
	public void stop(final BundleContext context) throws Exception {
		context.ungetService(this.rpcMethodMngServiceRef);
	}
}
