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

import java.util.Iterator;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.sm_baseline1profile.model.SM_Baseline1ProfileDataModel;
import com.francetelecom.admindm.sm_baseline1profile.utils.Utils;
import com.francetelecom.admindm.soap.Fault;

/**
 * This Listener class manages the Data Model updates, thanks to the OSGi bundle
 * events.
 */
public class DuAndEuEventListener implements BundleListener {

	private BundleContext bundleContext;
	private IParameterData pmDataService;
	private Manager manager;

	/** */
	public DuAndEuEventListener(final BundleContext bundleContext,
			final IParameterData pmDataService) {
		this.bundleContext = bundleContext;
		this.pmDataService = pmDataService;
		manager = Manager.getSingletonInstance();
		try {
			updateDUs();
			updateEUs();
		} catch (NullPointerException e) {
			Log.error("Error init DuAndEuEventListener", e);
		} catch (Fault e) {
			Log.error("Fault init DuAndEuEventListener", e);
		}
		
	}

	public void bundleChanged(final BundleEvent event) {
		Log.info("DuAndEuEventListener.bundleChanged event: " + event);
		// int org.osgi.framework.BundleEvent.getType()
		// event.INSTALLED -> 1
		// event.STARTED -> 2
		// event.STOPPED -> 4
		// event.UPDATED -> 8
		// event.UNINSTALLED -> 16
		// event.RESOLVED -> 32
		// event.UNRESOLVED -> 64
		// event.STARTING -> 128
		// event.STOPPING -> 256
		// event.LAZY_ACTIVATION -> 512
		Log.info("event.getType(): " + event.getType());
		
		updateDU(event);
		updateEU(event);

//		try {
//			// Update data model corresponding to:
//			// updateDUs();
//			updateDU(event);
//			// Update data model corresponding to:
////			updateEUs();
//			updateEU(event);
//		} catch (NullPointerException e) {
//			Log.error(e.getMessage(), e);
//		} catch (Fault e) {
//			Log.error(e.getMessage(), e);
//		}
	}

	/**
	 * Update DeploymentUnit datamodel based on received bundle event
	 * 
	 * @param bundleEvent
	 *            bundle event
	 */
	private void updateDU(final BundleEvent bundleEvent) {
		Bundle bundle = bundleEvent.getBundle();
		boolean updateDUNumber = false;
		Log.debug("DuAndEuEventListener.updateDU " + bundleEvent);

		switch (bundleEvent.getType()) {
		case BundleEvent.INSTALLED: {
			// a new bundle has been installed
			// create a new DeploymentUnit and add it into the data model and the manager
			DeploymentUnit du = new DeploymentUnit(bundle);
			Log.info("created du " + du);
			try {
				Utils.createDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
						this.pmDataService, du);
			} catch (Fault e) {
				Log.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage(), e);
			}
			manager.addADeploymentUnit(du);
			updateDUNumber = true;
			break;
		}
		case BundleEvent.UPDATED:
		case BundleEvent.STARTED:
			// case BundleEvent.STARTING:
		case BundleEvent.STOPPED:
			// case BundleEvent.STOPPING:
		case BundleEvent.RESOLVED:
		case BundleEvent.UNRESOLVED: {
			// a bundle has been updated
			// find out its related DeploymentUnit and update it
			DeploymentUnit du = manager.getDeploymentUnit(bundle.getBundleId());
			if (du == null) {
				// something wrong as the du should exists
				// try to create the DU to recover to a valid situation
				du = new DeploymentUnit(bundle);
				try {
					Utils.createDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, du);
				} catch (Fault e) {
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
				manager.addADeploymentUnit(du);
				updateDUNumber = true;
			} else {
				du.updateDeploymentUnit(bundle);
				Log.info("updated du " + du);
				try {
					Utils.updateDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, du);
				} catch (Fault e) {
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		}
		case BundleEvent.UNINSTALLED: {
			// a bundle has been uninstalled
			// find out the DeploymentUnit and report uninstallation
			DeploymentUnit du = manager.getDeploymentUnit(bundle.getBundleId());
			Log.info("remove du " + du);
			if (du != null) {
				du.updateDeploymentUnitWhenTheBundleHasBeenUninstalled();
				try {
					Utils.updateDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, du);
					manager.removeADeploymentUnit(du);
				} catch (Fault e) {
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			updateDUNumber = true;
			break;
		}
		default:
			break;
		}

		if (updateDUNumber) {
			Long nbDUs = new Long(manager.getDeploymentUnits().size());
			try {
				Parameter deploymentUnitNumberOfEntriesLeaf = this.pmDataService
						.createOrRetrieveParameter(this.pmDataService.getRoot()
								+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES
								+ SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT_NUMBER_OF_ENTRIES);
				deploymentUnitNumberOfEntriesLeaf.setValue(nbDUs);
			} catch (Fault e) {
				Log.error(e.getMessage(), e);
			}
		}
	}

	private void updateDUs() throws NullPointerException, Fault {
		Log.debug("DuAndEuEventListener.updateDUs");
		// The value associated to Device.OSGI.BundleNumberOfEntries is
		// bugged (once at least one bundle uninstallation has occured).
		// Long bundleNumberOfEntries = (Long) this.pmDataService
		// .getParameter("Device.OSGI.BundleNumberOfEntries")
		// .getValue();

		// Update the deployment units internal data, and the data model.
		Bundle[] bundles = this.bundleContext.getBundles();
		for (int i = 0; i < bundles.length; i++) {
			Bundle bundle = bundles[i];
			boolean bundleFoundInManager = false;
			for (Iterator it = manager.getDeploymentUnits().iterator(); it.hasNext(); ) {
				DeploymentUnit du = (DeploymentUnit) it.next();
				if (bundle.getBundleId() == du.getUuid()) {
					bundleFoundInManager = true;
					// Update du's data.
					du.updateDeploymentUnit(bundle);
					try {
						Utils.updateDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
								this.pmDataService, du);
					} catch (Fault e) {
						Log.error(e.getMessage(), e);
						throw new RuntimeException(e.getMessage(), e);
					}
					break;
				}
			}
			if (! bundleFoundInManager) {
				DeploymentUnit du = new DeploymentUnit(bundle);
				try {
					Utils.createDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, du);
				} catch (Fault e) {
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
				manager.addADeploymentUnit(du);
			} // no "else" needed.
		}

		// Let's search, and find the uninstalled bundles.
		for (Iterator it = manager.getDeploymentUnits().iterator(); it.hasNext(); ) {
			DeploymentUnit du = (DeploymentUnit) it.next();
			boolean duFoundInBundleContext = false;
			for (int i = 0; i < bundles.length; i++) {
				if (du.getUuid() == bundles[i].getBundleId()) {
					duFoundInBundleContext = true;
					break;
				}
			}
			if (! duFoundInBundleContext) {
				// Here, it means that the bundle has been uninstalled.
				// Let's update the du accordingly.
				du.updateDeploymentUnitWhenTheBundleHasBeenUninstalled();
				try {
					Utils.updateDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, du);
					manager.removeADeploymentUnit(du);
				} catch (Fault e) {
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			} // no "else" needed.
		}

		// As a consequence, the "number" of du in
		// Manager.getSingletonInstance().getDeploymentUnits() is updated.
		Long nbDUs = new Long(manager.getDeploymentUnits().size());
		Parameter deploymentUnitNumberOfEntriesLeaf = this.pmDataService
				.createOrRetrieveParameter(this.pmDataService.getRoot()
						+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES
						+ SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT_NUMBER_OF_ENTRIES);
		deploymentUnitNumberOfEntriesLeaf.setValue(nbDUs);
	}

	private void updateEU(final BundleEvent bundleEvent) {
		Bundle bundle = bundleEvent.getBundle();
		boolean updateEUNumber = false;

		switch (bundleEvent.getType()) {
		case BundleEvent.INSTALLED: {
			// create a new ExecutionUnit and add it into the datamodel
			ExecutionUnit eu = new ExecutionUnit(bundle);
			try {
				Utils.createExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
						this.pmDataService, this.bundleContext, eu);
			} catch (Fault e) {
				Log.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage(), e);
			}
			manager.addAnExecutionUnit(eu);
			updateEUNumber = true;
			break;
		}
		case BundleEvent.RESOLVED:
		case BundleEvent.STARTED:
		case BundleEvent.STARTING:
		case BundleEvent.STOPPED:
		case BundleEvent.STOPPING:
		case BundleEvent.UNRESOLVED:
		case BundleEvent.UPDATED: {
			// find out the ExecutionUnit and update it
			ExecutionUnit eu = manager.getExecutionUnit(bundle.getBundleId());
			if (eu == null) {
				// should not occur
				// try to create it to come back to a stable situation
				eu = new ExecutionUnit(bundle);
				try {
					Utils.createExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, this.bundleContext, eu);
				} catch (Fault e) {
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
				manager.addAnExecutionUnit(eu);
				updateEUNumber = true;
			} else {
				// Update eu's data.
				eu.updateExecutionUnit(bundle);
				try {
					Utils.updateExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, eu);
				} catch (Fault e) {
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		}
		case BundleEvent.UNINSTALLED: {
			ExecutionUnit eu = manager.getExecutionUnit(bundle.getBundleId());
			if (eu != null) {
				updateEUNumber = true;
				eu.updateExecutionUnitWhenTheBundleHasBeenUninstalled();
				try {
					Utils.updateExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, eu);
					manager.removeAnExecutionUnit(eu);
				} catch (Fault e) {
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		}
		default:
			break;
		}
		
		if (updateEUNumber) {
			Long nbEUs = new Long(manager.getExecutionUnits().size());
			try {
				Parameter executionUnitNumberOfEntriesLeaf = this.pmDataService
						.createOrRetrieveParameter(this.pmDataService.getRoot()
								+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES
								+ SM_Baseline1ProfileDataModel.EXECUTION_UNIT_NUMBER_OF_ENTRIES);
				executionUnitNumberOfEntriesLeaf.setValue(nbEUs);
			} catch (Fault e) {
				Log.error(e.getMessage(), e);
			}	
		}
	}

	private void updateEUs() throws NullPointerException, Fault {
		// Update the execution units internal data, and the datamodel.
		Bundle[] bundles = this.bundleContext.getBundles();
		for (int i = 0; i < bundles.length; i++) {
			Bundle bundle = bundles[i];
			boolean bundleFoundInManager = false;
			for (Iterator it = manager.getExecutionUnits().iterator(); it.hasNext(); ) {
				ExecutionUnit eu = (ExecutionUnit) it.next();
				if (bundle.getBundleId() == eu.getEuid()) {
					bundleFoundInManager = true;
					// Update eu's data.
					eu.updateExecutionUnit(bundle);
					try {
						Utils.updateExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
								this.pmDataService, eu);
					} catch (Fault e) {
						Log.error(e.getMessage(), e);
						throw new RuntimeException(e.getMessage(), e);
					}
					break;
				}
			}
			if (! bundleFoundInManager) {
				ExecutionUnit eu = new ExecutionUnit(bundle);
				try {
					Utils.createExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, this.bundleContext, eu);
				} catch (Fault e) {
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
				manager.addAnExecutionUnit(eu);
			} // no "else" needed.
		}

		// Let's search, and find the uninstalled bundles (an active bundle can
		// be uninstalled directly in OSGi).
		for (Iterator it = manager.getExecutionUnits().iterator(); it.hasNext(); ) {
			ExecutionUnit eu = (ExecutionUnit) it.next();
			boolean euFoundInBundleContext = false;
			for (int i = 0; i < bundles.length; i++) {
				if (eu.getEuid() == bundles[i].getBundleId()) {
					euFoundInBundleContext = true;
					break;
				}
			}
			if (! euFoundInBundleContext) {
				// Here, it means that the bundle has been uninstalled.
				// Let's update the eu accordingly.
				eu.updateExecutionUnitWhenTheBundleHasBeenUninstalled();
				try {
					Utils.updateExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, eu);
					manager.removeAnExecutionUnit(eu);
				} catch (Fault e) {
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			} // no "else" needed.
		}

		// As a consequence, the "number" of eu in
		// Manager.getSingletonInstance().getExecutionUnits() is updated.
		Long nbEUs = new Long(manager.getExecutionUnits().size());
		Parameter executionUnitNumberOfEntriesLeaf = this.pmDataService
				.createOrRetrieveParameter(this.pmDataService.getRoot()
						+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES
						+ SM_Baseline1ProfileDataModel.EXECUTION_UNIT_NUMBER_OF_ENTRIES);
		executionUnitNumberOfEntriesLeaf.setValue(nbEUs);
	}
	
}
