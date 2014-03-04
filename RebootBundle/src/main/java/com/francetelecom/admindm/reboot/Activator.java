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

package com.francetelecom.admindm.reboot;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethodMngService;

/**
 * The Class Activator.
 */
public final class Activator implements BundleActivator {
	/** The RPC method mng service ref. */
	private ServiceReference rpcMethodMngServiceRef = null;
	/** The rpc mng. */
	private RPCMethodMngService rpcMng;

	/**
	 * Start.
	 * 
	 * @param context
	 *            the context
	 * @throws Exception
	 *             the exception
	 */
	public void start(final BundleContext context) throws Exception {
		rpcMethodMngServiceRef = context.getServiceReference(RPCMethodMngService.class.getName());
		if (rpcMethodMngServiceRef != null) {
			rpcMng = (RPCMethodMngService) context.getService(rpcMethodMngServiceRef);
			rpcMng.registerRPCMethod("Reboot");
			rpcMng.registerRPCEncoder("RebootResponse", new RebootResponseEncoder());
			rpcMng.registerRPCDecoder("Reboot", new RebootDecoder());
			Log.info("Start RPC Method Reboot");
		} else {
			Log.error("Unable tp start Reboot: " + "RPCMethodMngService is missing");
		}
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
		if (rpcMng != null) {
			rpcMng.unregisterRPCMethod("Reboot");
			rpcMng.unregisterRPCEncoder("RebootResponse");
			rpcMng.unregisterRPCDecoder("Reboot");
		}
		context.ungetService(rpcMethodMngServiceRef);
	}
}
