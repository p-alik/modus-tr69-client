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

package com.francetelecom.admindm.changedustate;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethodMngService;

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public final class Activator implements BundleActivator {

	private ServiceReference rpcMethodManagementServiceRef = null;
	static RPCMethodMngService RPC_METHOD_MANAGEMENT_SERVICE = null;
	private OpStructExecutionManager opStructExecutionManager = null;

	static BundleContext BUNDLE_CONTEXT;

	/**
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(final BundleContext context) {
		BUNDLE_CONTEXT = context;
		this.rpcMethodManagementServiceRef = context.getServiceReference(RPCMethodMngService.class.getName());
		if (this.rpcMethodManagementServiceRef != null) {
			Activator.RPC_METHOD_MANAGEMENT_SERVICE = (RPCMethodMngService) context
					.getService(this.rpcMethodManagementServiceRef);

			// ChangeDUState
			Activator.RPC_METHOD_MANAGEMENT_SERVICE.registerRPCMethod(ChangeDUState.NAME);
			Activator.RPC_METHOD_MANAGEMENT_SERVICE.registerRPCDecoder(ChangeDUState.NAME, new ChangeDUStateDecoder());
			Activator.RPC_METHOD_MANAGEMENT_SERVICE.registerRPCEncoder(ChangeDUStateResponse.NAME,
					new ChangeDUStateResponseEncoder());

			// DUStateChangeComplete
			Activator.RPC_METHOD_MANAGEMENT_SERVICE.registerRPCMethod(DUStateChangeCompleteResponse.NAME);
			Activator.RPC_METHOD_MANAGEMENT_SERVICE.registerRPCDecoder(DUStateChangeCompleteResponse.NAME,
					new DUStateChangeCompleteResponseDecoder());

			this.opStructExecutionManager = OpStructExecutionManager.getSingletonInstance();
			Log.info("Start RPC Method " + ChangeDUState.NAME);
		} else {
			Log.error("Unable to start " + ChangeDUState.NAME + ": " + "RPCMethodMngService is missing");
		}
	}

	/**
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(final BundleContext context) {
		if (Activator.RPC_METHOD_MANAGEMENT_SERVICE != null) {
			Activator.RPC_METHOD_MANAGEMENT_SERVICE.unregisterRPCMethod(ChangeDUState.NAME);
			Activator.RPC_METHOD_MANAGEMENT_SERVICE.unregisterRPCDecoder(ChangeDUState.NAME);
			Activator.RPC_METHOD_MANAGEMENT_SERVICE.unregisterRPCEncoder(ChangeDUStateResponse.NAME);
			this.opStructExecutionManager.shutdown();
			this.opStructExecutionManager = null;
			BUNDLE_CONTEXT = null;
		}
		context.ungetService(this.rpcMethodManagementServiceRef);
		Log.info("Stop RPC Method " + ChangeDUState.NAME);
	}
}
