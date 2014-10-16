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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.OpResultStruct;
import com.francetelecom.admindm.model.OperationStruct;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 * 
 *        OpStructExecutionManager must be a singleton.
 */
public class OpStructExecutionManager implements Runnable {

	private static OpStructExecutionManager instance;
	/** List of ChangeDUState */
	private final List listOfChangeDUStateToBeExecuted = new ArrayList();
	private final Object lock = new Object();
	private BundleContext bundleContext = null;
	private Thread thread = null;

	/** List of LocalData */
	private List localDataList = new ArrayList();

	/**
	 * There is only one way to know if a DU/bundle can be/is resolved: starting it... We don't want to do that, so
	 * resolved is set to false.
	 */
	boolean resolved = false;

	/** There is no way to get this info."; */
	String executionUnitRefList = "There is no way to get this info.";

	/** Must be private, use getSingletonInstance(). */
	private OpStructExecutionManager() {
		this.bundleContext = Activator.BUNDLE_CONTEXT;
		this.thread = new Thread(this);
		this.thread.start();
	}

	/**
	 * @return the singleton instance of OpStructExecutionManager
	 */
	public static OpStructExecutionManager getSingletonInstance() {
		if (instance == null) {
			instance = new OpStructExecutionManager();
		}
		return instance;
	}

	public void addChangeDUStateToBeExecuted(final ChangeDUState changeDUState) {
		synchronized (this.listOfChangeDUStateToBeExecuted) {
			this.listOfChangeDUStateToBeExecuted.add(changeDUState);
			this.listOfChangeDUStateToBeExecuted.notify();
		}
	}

	public void run() {

		while (true) {
			ChangeDUState changeDUStateToBeExecuted = null;
			synchronized (this.listOfChangeDUStateToBeExecuted) {
				if (listOfChangeDUStateToBeExecuted.isEmpty()) {
					try {
						listOfChangeDUStateToBeExecuted.wait();
					} catch (InterruptedException e) {
						// return thread
						return;
					}
				}
				// at this point, we are sure the list is not empty
				changeDUStateToBeExecuted = (ChangeDUState) listOfChangeDUStateToBeExecuted.remove(0);
			}

			if (changeDUStateToBeExecuted != null) {
				Log.debug("Start the execution of: " + changeDUStateToBeExecuted);
				// Synchronously execute the current ChangeDUState.
				// XXX AAA: The execution must be finished in a one hour time-slot.

				String commandKey = changeDUStateToBeExecuted.getCommandKey();
				Log.debug("The commandKey of the current changeDUStateToBeExecuted (changeDUStateToBeExecuted.getId(): "
						+ changeDUStateToBeExecuted.getId() + ") is: " + commandKey + ".");

				OperationStruct[] operationsToBeExecuted = changeDUStateToBeExecuted.getOperations();

				List opResultStructList = new ArrayList();

				if (operationsToBeExecuted == null) {
					// XXX AAA: handle this error. A 9001 fault?
					String errorMessage = "operationsToBeExecuted is null, here, but it must NOT.";
					Log.error(errorMessage);
					throw new RuntimeException(errorMessage);
				}

				for (int index = 0; index < operationsToBeExecuted.length; index = index + 1) {
					OperationStruct operationToBeExecuted = operationsToBeExecuted[index];
					if (OperationStruct.INSTALL.equals(operationToBeExecuted.getOperationStructType())) {
						opResultStructList.add(install(operationToBeExecuted));

					} else if (OperationStruct.UPDATE.equals(operationToBeExecuted.getOperationStructType())) {
						opResultStructList.add(update(operationToBeExecuted));


					} else if (OperationStruct.UNINSTALL.equals(operationToBeExecuted.getOperationStructType())) {
						opResultStructList.add(uninstall(operationToBeExecuted));

					} else {
						// XXX AAA: handle this error.
						String errorMessage = "The type (" + operationToBeExecuted.getOperationStructType()
								+ ") of operationToBeExecuted is UNKNOWN.";
						Log.error(errorMessage);
						throw new RuntimeException(errorMessage);
					}
				}

				// Emit an Inform(... DU STATE CHANGE COMPLETE, M ChangeDUState
				// ...); from CPE to ACS.
				
				ServiceReference parameterDataSr = bundleContext.getServiceReference(IParameterData.class.getName());
				IParameterData parameterData = null;
				if (parameterDataSr != null) {
					parameterData = (IParameterData) bundleContext.getService(parameterDataSr);
				}
				
				if (parameterData != null) {
					parameterData.addEvent(new EventStruct(
						EventCode.DU_STATE_CHANGE_COMPLETE, commandKey));
				
					DUStateChangeComplete duStateChangeComplete = new DUStateChangeComplete(
							opResultStructList, commandKey, "1");
					parameterData.addOutgoingRequest(duStateChangeComplete);
				}

			} else {
				// Log.debug("There is no ChangeDUState to execute.");
			}

//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				// XXX AAA: handle this error.
//				e.printStackTrace();
//				String errorMessage = e.getMessage();
//				Log.error(errorMessage, e);
//			}
		}
	}

	public void shutdown() {
		// replace deprecated "this.thread.stop();" with the following lines of code.
		try {
			this.thread.interrupt();
		} catch (Exception e) {
			Log.debug("OpStructExecutionManager.shutdown() throw an Exception that should/can be ignored.");
		}
	}

	private OpResultStruct install(final OperationStruct operationToBeExecuted) {
		URL url = operationToBeExecuted.getUrl();
		// uuid is expected to be null, here.
		String uuid = operationToBeExecuted.getUuid();
		String username = operationToBeExecuted.getUsername();
		Log.debug("username: " + username);
		String password = operationToBeExecuted.getPassword();
		Log.debug("password: " + password);

		// Assumption, there is only one EE.
		String executionEnvRef = operationToBeExecuted.getExecutionEnvRef();
		Log.debug("Assumption, there is only one EE (executionEnvRef: " + executionEnvRef + ")");

		Log.debug("url: " + url);

		// Check that a bundle having url as URL is NOT already
		// deployed in the EE.
		Bundle[] bundles = this.bundleContext.getBundles();
		boolean bundleIsAlreadyDeployedInEE = false;
		for (int i = 0; i < bundles.length; i = i + 1) {
			Bundle bundle = bundles[i];
			// Log.debug("bundle: " + bundle.getBundleId()  + ", bundle.getLocation(): "
			// + bundle.getLocation());
			if (bundle.getLocation().equals(url.toString())) {
				bundleIsAlreadyDeployedInEE = true;
				break;
			}
		}

		/** I.e. the bundleId. */
		String deploymentUnitRef = "";
		String version = "";

		if (bundleIsAlreadyDeployedInEE) {
			String errorMessage = "Duplicate Deployment Unit: Bundle (located at url: " + url
					+ ") is already deployed in EE.";
			Log.error(errorMessage);
			OpStructExecutionManagerException opStructExecutionManagerException = 
					new OpStructExecutionManagerException(errorMessage, null);
			long startTime = System.currentTimeMillis();
			long completeTime = startTime;
			int pFaultcode = 9026;
			Fault fault = new Fault(pFaultcode, opStructExecutionManagerException.getMessage(),
					opStructExecutionManagerException);
			return new OpResultStruct(uuid, deploymentUnitRef, version, CurrentState.Failed,
					this.resolved, this.executionUnitRefList, startTime, completeTime, fault);
		}

		// Install the bundle located at url.
		long startTime = System.currentTimeMillis();
		try {
			Bundle justInstalledBundle = this.bundleContext.installBundle(url.toString());
			long completeTime = System.currentTimeMillis();

			// Store data locally: given uuid, version via
			// bundle.getheaders..., and bundleId.
			version = (String) justInstalledBundle.getHeaders().get("Bundle-Version");
			long justInstalledBundleId = justInstalledBundle.getBundleId();
			uuid = Long.toString(justInstalledBundleId);
			LocalData localDataOfTheJustInstalledBundle = 
					new LocalData(justInstalledBundleId, version);
			Log.info("Bundle has been installed (localDataOfTheJustInstalledBundle: "
					+ localDataOfTheJustInstalledBundle + ").");
			this.localDataList.add(localDataOfTheJustInstalledBundle);

			deploymentUnitRef = Long.toString(justInstalledBundleId);
			// see Fault in TR-069 Issue 1 Amendment 4 Table
			// 77 Page 118.
			Fault fault = new Fault(0, "");
			return new OpResultStruct(uuid, deploymentUnitRef, version,
					CurrentState.Installed, this.resolved, this.executionUnitRefList, startTime,
					completeTime, fault);
		} catch (BundleException e) {
			e.printStackTrace();
			String errorMessage = "Request Denied: Installation of bundle located at: " + url
					+ " failed.";
			Log.error(errorMessage, e);
			OpStructExecutionManagerException opStructExecutionManagerException = 
					new OpStructExecutionManagerException(errorMessage, e);
			long completeTime = System.currentTimeMillis();
			int pFaultcode = 9001;
			Fault fault = new Fault(pFaultcode, opStructExecutionManagerException.getMessage(),
					opStructExecutionManagerException);
			return new OpResultStruct(uuid, deploymentUnitRef, version,
					CurrentState.Failed, this.resolved, this.executionUnitRefList, startTime,
					completeTime, fault);
		}
	}

	private OpResultStruct update(final OperationStruct operationToBeExecuted) {
		String uuid = operationToBeExecuted.getUuid();
		String version = operationToBeExecuted.getVersion();
		URL url = operationToBeExecuted.getUrl();
		String username = operationToBeExecuted.getUsername();
		Log.debug("username: " + username);
		String password = operationToBeExecuted.getPassword();
		Log.debug("password: " + password);

		LocalData localDataOfTheBundleToBeUpdated = null;
		Iterator localDataListIterator = this.localDataList.iterator();
		while (localDataListIterator.hasNext()) {
			LocalData localData = (LocalData) localDataListIterator.next();
			if (uuid.equals(Long.toString(localData.getUuid()))) {
				localDataOfTheBundleToBeUpdated = localData;
				break;
			}
		}

		/** I.e. the bundleId. */
		String deploymentUnitRef = "";
		/** I.e. not the version of the bundle once updated. */
		String currentVersionOfTheBundleToBeUpdated = "";

		Bundle bundle = null;
		if (localDataOfTheBundleToBeUpdated == null) {
			try {
				bundle = this.bundleContext.getBundle(Long.parseLong(uuid));
			} catch (NumberFormatException ignored) {
			}
		} else {
			bundle = this.bundleContext.getBundle(localDataOfTheBundleToBeUpdated.getUuid());
		}

		if (bundle == null) {
			String errorMessage = "Unknown Deployment Unit: Update of bundle(uuid: " + uuid
					+ "): failed. Can't find the targeted bundle in the EE.";
			Log.error(errorMessage);
			OpStructExecutionManagerException opStructExecutionManagerException = 
					new OpStructExecutionManagerException(errorMessage, null);
			long startTime = System.currentTimeMillis();
			long completeTime = startTime;
			int pFaultcode = 9028;
			Fault fault = new Fault(pFaultcode, opStructExecutionManagerException.getMessage(),
					opStructExecutionManagerException);
			return new OpResultStruct(uuid, deploymentUnitRef,
					currentVersionOfTheBundleToBeUpdated, CurrentState.Installed, this.resolved,
					this.executionUnitRefList, startTime, completeTime, fault);
		}

		long startTime = System.currentTimeMillis();
		try {
			InputStream iS = url.openStream();
			// Apache Felix bundle.update(iS) is equivalent to bundle.uninstall, and then
			// install of the bundle located at iS ; BUT the bundleId is kept, i.e. the newly
			// installed bundle has the same bundleId as the just uninstalled.
			// XXX Is there a bug?!. in Apache Felix, the information displayed after a bundle
			// update is updated, e.g.
			// 32|Installed|1|XYZ(1.0.0) is updated to 32|Installed|1|XYZ(2.0.0), and in
			// felix-cache, the bundle's .jar has been updated.
			// BUT the bundle's location remains the same:
			// 32|Installed|1|http://abc/XYZ-1.0.0.jar!
			bundle.update(iS);

			// Update the localData of the bundle that has been updated.
			String versionOfTheUpdatedBundle = (String) bundle.getHeaders().get(
					"Bundle-Version");
			// If inputs are trusted, it is possible to use version instead of
			// versionOfTheUpdatedBundle(that comes from
			// the version data coming packaged with the bundle itself.
			LocalData localDataOfTheUpdatedBundle = new LocalData(
					bundle.getBundleId(), versionOfTheUpdatedBundle);

			// Remove the "old" localData associated to the bundle that was updated.
			if (localDataOfTheBundleToBeUpdated != null) {
				this.localDataList.remove(localDataOfTheBundleToBeUpdated);
			}
			// Add "new" localData corresponding to the "new" bundle.
			this.localDataList.add(localDataOfTheUpdatedBundle);

			Log.info("Bundle has been updated (updated local data of the just updated bundle: "
					+ localDataOfTheUpdatedBundle + ")");

			deploymentUnitRef = Long.toString(bundle.getBundleId());

			long completeTime = System.currentTimeMillis();
			// see Fault in TR-069 Issue 1 Amendment 4
			// Table 77 Page 118.
			Fault fault = new Fault(0, "");
			return new OpResultStruct(uuid, deploymentUnitRef, version,
					CurrentState.Installed, this.resolved, this.executionUnitRefList,
					startTime, completeTime, fault);

		} catch (IOException e) {
			e.printStackTrace();
			String errorMessage = "File transfert failure: Unable to contact file server. "
					+ "Update of bundle (uuid: " + uuid + "): failed. IOException.";
			Log.error(errorMessage, e);
			OpStructExecutionManagerException opStructExecutionManagerException = 
					new OpStructExecutionManagerException(errorMessage, e);
			long completeTime = System.currentTimeMillis();
			int pFaultcode = 9015;
			Fault fault = new Fault(pFaultcode, opStructExecutionManagerException.getMessage(),
					opStructExecutionManagerException);
			return new OpResultStruct(uuid, deploymentUnitRef,
					currentVersionOfTheBundleToBeUpdated, CurrentState.Installed,
					this.resolved, this.executionUnitRefList, startTime, completeTime, fault);
		} catch (BundleException e) {
			// BundleException - If the provided stream
			// cannot be read or the update fails.
			e.printStackTrace();
			String errorMessage = "Request Denied: Update of bundle (uuid: " + uuid
					+ "): failed. BundleException.";
			Log.error(errorMessage, e);
			OpStructExecutionManagerException opStructExecutionManagerException = 
					new OpStructExecutionManagerException(errorMessage, e);
			long completeTime = System.currentTimeMillis();
			int pFaultcode = 9001;
			Fault fault = new Fault(pFaultcode, opStructExecutionManagerException.getMessage(),
					opStructExecutionManagerException);
			return new OpResultStruct(uuid, deploymentUnitRef,
					currentVersionOfTheBundleToBeUpdated, CurrentState.Installed,
					this.resolved, this.executionUnitRefList, startTime, completeTime, fault);
		}
	}

	private OpResultStruct uninstall(final OperationStruct operationToBeExecuted) {
		String uuid = operationToBeExecuted.getUuid();
		String version = operationToBeExecuted.getVersion();

		// Assumption, there is only one EE.
		String executionEnvRef = operationToBeExecuted.getExecutionEnvRef();
		Log.debug("Assumption, there is only one EE (executionEnvRef: " + executionEnvRef + ")");

		LocalData localDataOfTheBundleToBeUninstalled = null;
		Iterator localDataListIterator = this.localDataList.iterator();
		while (localDataListIterator.hasNext()) {
			LocalData localData = (LocalData) localDataListIterator.next();
			// Check UUID
			if (uuid.equals(Long.toString(localData.getUuid())) 
					&& version.equals(localData.getVersion())) {
				localDataOfTheBundleToBeUninstalled = localData;
				break;
			}
		}

		/** I.e. the bundleId. */
		String deploymentUnitRef = "";
		/** I.e. not the version of the bundle once updated. */
		String currentVersionOfTheBundleToBeUpdated = "";

		Bundle bundle = null;
		if (localDataOfTheBundleToBeUninstalled == null) {
			try {
				bundle = this.bundleContext.getBundle(Long.parseLong(uuid));
				// check version
				if (! bundle.getHeaders().get("Bundle-Version").equals(version)) {
					Log.error("Installed bundle has same ID but different versions "
							+ "with bundle to be uninstalled");
					bundle = null;
				}
			} catch (Exception ignored) {
			}
		} else {
			bundle = this.bundleContext.getBundle(localDataOfTheBundleToBeUninstalled.getUuid());
		}

		if (bundle == null) {
			String errorMessage = "Unknown Deployment Unit: Uninstall of bundle (uuid: " + uuid
					+ " ): failed. Can't find the targeted bundle in the EE.";
			Log.error(errorMessage);
			OpStructExecutionManagerException opStructExecutionManagerException = 
					new OpStructExecutionManagerException(errorMessage, null);
			long startTime = System.currentTimeMillis();
			long completeTime = startTime;
			int pFaultcode = 9028;
			Fault fault = new Fault(pFaultcode, opStructExecutionManagerException.getMessage(),
					opStructExecutionManagerException);
			return new OpResultStruct(uuid, deploymentUnitRef,
					currentVersionOfTheBundleToBeUpdated, CurrentState.Installed, this.resolved,
					this.executionUnitRefList, startTime, completeTime, fault);
		}

		long startTime = System.currentTimeMillis();
		try {
			deploymentUnitRef = Long.toString(bundle.getBundleId());
			bundle.uninstall();
			if (localDataOfTheBundleToBeUninstalled != null) {
				// Remove the localData associated to the just uninstalled bundle.
				this.localDataList.remove(localDataOfTheBundleToBeUninstalled);
			}
			Log.info("Bundle has been uninstalled.");

			long completeTime = System.currentTimeMillis();
			// see Fault in TR-069 Issue 1 Amendment 4
			// Table 77 Page 118.
			Fault fault = new Fault(0, "");
			return new OpResultStruct(uuid, deploymentUnitRef, version,
					CurrentState.Uninstalled, this.resolved, this.executionUnitRefList,
					startTime, completeTime, fault);
		} catch (BundleException e) {
			e.printStackTrace();
			String errorMessage = "Request Denied: Uninstall of bundle (uuid: " + uuid
					+ " ): failed. BundleException.";
			Log.error(errorMessage, e);
			OpStructExecutionManagerException opStructExecutionManagerException = 
					new OpStructExecutionManagerException(errorMessage, null);
			long completeTime = startTime;
			int pFaultcode = 9001;
			Fault fault = new Fault(pFaultcode, opStructExecutionManagerException.getMessage(),
					opStructExecutionManagerException);
			return new OpResultStruct(uuid, deploymentUnitRef,
					currentVersionOfTheBundleToBeUpdated, CurrentState.Installed,
					this.resolved, this.executionUnitRefList, startTime, completeTime, fault);
		}
	}

}
