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

	/** */
	public DuAndEuEventListener(final BundleContext bundleContext,
			final IParameterData pmDataService) {
		this.bundleContext = bundleContext;
		this.pmDataService = pmDataService;
		
		try {
			updateDUs();
			updateEUs();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void bundleChanged(final BundleEvent event) {
		Log.info("event: " + event);
		Log.info("event.getBundle(): " + event.getBundle());
		Log.info("event.getSource(): " + event.getSource());
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
	private void updateDU(BundleEvent bundleEvent) {

		Bundle bundle = bundleEvent.getBundle();
		DeploymentUnit du = null;
		boolean updateDUNumber = false;

		switch (bundleEvent.getType()) {
		case BundleEvent.INSTALLED:
			// a new bundle has been installed
			// create a new DeploymentUnit and add it into the data model and
			// the manager
			du = new DeploymentUnit(bundle);
			try {
				Utils.createDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
						this.pmDataService, du);
			} catch (Fault e) {
				e.printStackTrace();
				Log.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage(), e);
			}
			Manager.getSingletonInstance().getDeploymentUnits().add(du);
			updateDUNumber = true;
			break;
		case BundleEvent.UPDATED:
		case BundleEvent.STARTED:
			// case BundleEvent.STARTING:
		case BundleEvent.STOPPED:
			// case BundleEvent.STOPPING:
		case BundleEvent.RESOLVED:
		case BundleEvent.UNRESOLVED:
			// a bundle has been updated
			// find out its related DeploymentUnit and update it
			updateDUNumber = false;
			du = Manager.getSingletonInstance().getDeploymentUnit(
					bundle.getBundleId());
			if (du != null) {
				du.updateDeploymentUnit(bundle);
				try {
					Utils.updateDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, du);
				} catch (Fault e) {
					e.printStackTrace();
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			} else {
				// something wrong as the du should exists
				// try to create the DU to recover to a valid situation
				du = new DeploymentUnit(bundle);
				try {
					Utils.createDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, du);
				} catch (Fault e) {
					e.printStackTrace();
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
				Manager.getSingletonInstance().getDeploymentUnits().add(du);
				updateDUNumber = true;
			}

			break;
		case BundleEvent.UNINSTALLED:
			// a bundle has been uninstalled
			// find out the DeploymentUnit and report uninstallation
			du = Manager.getSingletonInstance().getDeploymentUnit(
					bundle.getBundleId());
			if (du != null) {
				du.updateDeploymentUnitWhenTheBundleHasBeenUninstalled();
				try {
					Utils.updateDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, du);
					Manager.getSingletonInstance().removeADeploymentUnit(du);
				} catch (Fault e) {
					e.printStackTrace();
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			updateDUNumber = true;
			break;
		default:
			break;
		}

		if (updateDUNumber) {
			Long deploymentUnitsNumberOfEntries = Long.valueOf(Integer
					.toString(Manager.getSingletonInstance()
							.getDeploymentUnits().size()));
			Parameter deploymentUnitNumberOfEntriesLeaf;
			try {
				deploymentUnitNumberOfEntriesLeaf = this.pmDataService
						.createOrRetrieveParameter(this.pmDataService.getRoot()
								+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES
								+ SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT_NUMBER_OF_ENTRIES);
				deploymentUnitNumberOfEntriesLeaf
						.setValue(deploymentUnitsNumberOfEntries);
			} catch (Fault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void updateDUs() throws NullPointerException, Fault {
		// The value associated to Device.OSGI.BundleNumberOfEntries is
		// bugged (once at least one bundle uninstallation has occured).
		// Long bundleNumberOfEntries = (Long) this.pmDataService
		// .getParameter("Device.OSGI.BundleNumberOfEntries")
		// .getValue();

		// Update the deployment units internal data, and the data model.
		Bundle[] bundles = this.bundleContext.getBundles();
		for (int i = 0; i < bundles.length; i = i + 1) {
			Bundle b = bundles[i];
			boolean bundleHasBeenFoundInManager = false;
			Iterator duIterator = Manager.getSingletonInstance()
					.getDeploymentUnits().iterator();
			while (duIterator.hasNext()) {
				DeploymentUnit du = (DeploymentUnit) duIterator.next();
				if (b.getBundleId() == du.getUuid()) {
					bundleHasBeenFoundInManager = true;
					// Update du's data.
					du.updateDeploymentUnit(b);
					try {
						Utils.updateDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
								this.pmDataService, du);
					} catch (Fault e) {
						e.printStackTrace();
						Log.error(e.getMessage(), e);
						throw new RuntimeException(e.getMessage(), e);
					}
					break;
				}
			}

			if (!(bundleHasBeenFoundInManager)) {
				DeploymentUnit du = new DeploymentUnit(b);
				try {
					Utils.createDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, du);
				} catch (Fault e) {
					e.printStackTrace();
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
				Manager.getSingletonInstance().getDeploymentUnits().add(du);
			} // no "else" needed.
		}

		// Let's search, and find the uninstalled bundles.
		Iterator duIterator = Manager.getSingletonInstance()
				.getDeploymentUnits().iterator();
		while (duIterator.hasNext()) {
			DeploymentUnit du = (DeploymentUnit) duIterator.next();
			boolean duHasBeenFoundInBundleContext = false;
			for (int i = 0; i < bundles.length; i = i + 1) {
				Bundle b = bundles[i];
				if (du.getUuid() == b.getBundleId()) {
					duHasBeenFoundInBundleContext = true;
					break;
				}
			}

			if (!(duHasBeenFoundInBundleContext)) {
				// Here, it means that the bundle has been uninstalled.
				// Let's update the du accordingly.
				du.updateDeploymentUnitWhenTheBundleHasBeenUninstalled();
				try {
					Utils.updateDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, du);
					Manager.getSingletonInstance().removeADeploymentUnit(du);
				} catch (Fault e) {
					e.printStackTrace();
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			} // no "else" needed.
		}

		// As a consequence, the "number" of du in
		// Manager.getSingletonInstance().getDeploymentUnits() is updated.
		Long deploymentUnitsNumberOfEntries = Long.valueOf(Integer
				.toString(Manager.getSingletonInstance().getDeploymentUnits()
						.size()));
		Parameter deploymentUnitNumberOfEntriesLeaf = this.pmDataService
				.createOrRetrieveParameter(this.pmDataService.getRoot()
						+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES
						+ SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT_NUMBER_OF_ENTRIES);
		deploymentUnitNumberOfEntriesLeaf
				.setValue(deploymentUnitsNumberOfEntries);
	}

	private void updateEU(BundleEvent bundleEvent) {

		Bundle bundle = bundleEvent.getBundle();
		ExecutionUnit eu = null;
		boolean updateEUNumber = false;

		switch (bundleEvent.getType()) {
		case BundleEvent.INSTALLED:
			// create a new ExecutionUnit and add it into the datamodel
			eu = new ExecutionUnit(bundle);
			try {
				Utils.createExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
						this.pmDataService, this.bundleContext, eu);
			} catch (Fault e) {
				e.printStackTrace();
				Log.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage(), e);
			}
			Manager.getSingletonInstance().getExecutionUnits().add(eu);
			updateEUNumber = true;
			break;
		case BundleEvent.RESOLVED:
		case BundleEvent.STARTED:
		case BundleEvent.STARTING:
		case BundleEvent.STOPPED:
		case BundleEvent.STOPPING:
		case BundleEvent.UNRESOLVED:
		case BundleEvent.UPDATED:
			// find out the ExecutionUnit and update it
			eu = Manager.getSingletonInstance().getExecutionUnit(
					bundle.getBundleId());
			if (eu != null) {
				// Update eu's data.
				eu.updateExecutionUnit(bundle);
				try {
					Utils.updateExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, eu);
				} catch (Fault e) {
					e.printStackTrace();
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}

			} else {
				// should not occur
				// try to create it to come back to a stable situation
				eu = new ExecutionUnit(bundle);
				try {
					Utils.createExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, this.bundleContext, eu);
				} catch (Fault e) {
					e.printStackTrace();
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
				Manager.getSingletonInstance().getExecutionUnits().add(eu);
				updateEUNumber = true;
			}

			break;
		case BundleEvent.UNINSTALLED:
			eu = Manager.getSingletonInstance().getExecutionUnit(
					bundle.getBundleId());
			if (eu != null) {
				updateEUNumber = true;
				eu.updateExecutionUnitWhenTheBundleHasBeenUninstalled();
				try {
					Utils.updateExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, eu);
					Manager.getSingletonInstance().removeAnExecutionUnit(eu);
				} catch (Fault e) {
					e.printStackTrace();
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		default:
			break;
		}
		
		if (updateEUNumber) {
			Long executionUnitsNumberOfEntries = Long.valueOf(Integer
					.toString(Manager.getSingletonInstance().getExecutionUnits()
							.size()));
			Parameter executionUnitNumberOfEntriesLeaf;
			try {
				executionUnitNumberOfEntriesLeaf = this.pmDataService
						.createOrRetrieveParameter(this.pmDataService.getRoot()
								+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES
								+ SM_Baseline1ProfileDataModel.EXECUTION_UNIT_NUMBER_OF_ENTRIES);
				executionUnitNumberOfEntriesLeaf
				.setValue(executionUnitsNumberOfEntries);
			} catch (Fault e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	private void updateEUs() throws NullPointerException, Fault {
		// Update the execution units internal data, and the datamodel.
		Bundle[] bundles = this.bundleContext.getBundles();
		for (int i = 0; i < bundles.length; i = i + 1) {
			Bundle b = bundles[i];
			boolean bundleHasBeenFoundInManager = false;
			Iterator euIterator = Manager.getSingletonInstance()
					.getExecutionUnits().iterator();
			while (euIterator.hasNext()) {
				ExecutionUnit eu = (ExecutionUnit) euIterator.next();
				if (b.getBundleId() == eu.getEuid()) {
					bundleHasBeenFoundInManager = true;
					// Update eu's data.
					eu.updateExecutionUnit(b);
					try {
						Utils.updateExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
								this.pmDataService, eu);
					} catch (Fault e) {
						e.printStackTrace();
						Log.error(e.getMessage(), e);
						throw new RuntimeException(e.getMessage(), e);
					}
					break;
				}
			}

			if (!(bundleHasBeenFoundInManager)) {
				ExecutionUnit eu = new ExecutionUnit(b);
				try {
					Utils.createExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, this.bundleContext, eu);
				} catch (Fault e) {
					e.printStackTrace();
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
				Manager.getSingletonInstance().getExecutionUnits().add(eu);
			} // no "else" needed.
		}

		// Let's search, and find the uninstalled bundles (an active bundle can
		// be uninstalled directly in OSGi).
		Iterator euIterator = Manager.getSingletonInstance()
				.getExecutionUnits().iterator();
		while (euIterator.hasNext()) {
			ExecutionUnit eu = (ExecutionUnit) euIterator.next();
			boolean euHasBeenFoundInBundleContext = false;
			for (int i = 0; i < bundles.length; i = i + 1) {
				Bundle b = bundles[i];
				if (eu.getEuid() == b.getBundleId()) {
					euHasBeenFoundInBundleContext = true;
					break;
				}
			}

			if (!(euHasBeenFoundInBundleContext)) {
				// Here, it means that the bundle has been uninstalled.
				// Let's update the eu accordingly.
				eu.updateExecutionUnitWhenTheBundleHasBeenUninstalled();
				try {
					Utils.updateExecutionUnitNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, eu);
					Manager.getSingletonInstance().removeAnExecutionUnit(eu);
				} catch (Fault e) {
					e.printStackTrace();
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			} // no "else" needed.
		}

		// As a consequence, the "number" of eu in
		// Manager.getSingletonInstance().getExecutionUnits() is updated.
		Long executionUnitsNumberOfEntries = Long.valueOf(Integer
				.toString(Manager.getSingletonInstance().getExecutionUnits()
						.size()));
		Parameter executionUnitNumberOfEntriesLeaf = this.pmDataService
				.createOrRetrieveParameter(this.pmDataService.getRoot()
						+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES
						+ SM_Baseline1ProfileDataModel.EXECUTION_UNIT_NUMBER_OF_ENTRIES);
		executionUnitNumberOfEntriesLeaf
				.setValue(executionUnitsNumberOfEntries);
	}
}
