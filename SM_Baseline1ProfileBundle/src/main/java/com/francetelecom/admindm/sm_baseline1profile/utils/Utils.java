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

package com.francetelecom.admindm.sm_baseline1profile.utils;

import org.osgi.framework.BundleContext;

import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.CheckLength;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.sm_baseline1profile.DeploymentUnit;
import com.francetelecom.admindm.sm_baseline1profile.ExecutionUnit;
import com.francetelecom.admindm.sm_baseline1profile.model.ExecutionUnitResquestedStateSetter;
import com.francetelecom.admindm.sm_baseline1profile.model.SM_Baseline1ProfileDataModel;
import com.francetelecom.admindm.soap.Fault;

public class Utils {

	/**
	 * @param pmDataService
	 * @param du
	 * @throws Fault
	 */
	public static void createDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(final IParameterData pmDataService,
			final DeploymentUnit du) throws Fault {
		Parameter deploymentUnitNumberBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + ".");
		deploymentUnitNumberBranch.setType(ParameterType.ANY);

		// uuid
		Parameter uuidLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.UUID);
		uuidLeaf.setType(ParameterType.STRING);
		uuidLeaf.setStorageMode(StorageMode.COMPUTED);
		uuidLeaf.setWritable(false);
		uuidLeaf.setNotification(0);
		uuidLeaf.setActiveNotificationDenied(false);
		uuidLeaf.addCheck(new CheckLength(36));
		uuidLeaf.setValue(Long.toString(du.getUuid()));

		// duid
		Parameter duidLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.DUID);
		duidLeaf.setType(ParameterType.STRING);
		duidLeaf.setStorageMode(StorageMode.COMPUTED);
		duidLeaf.setWritable(false);
		duidLeaf.setNotification(0);
		duidLeaf.setActiveNotificationDenied(false);
		duidLeaf.addCheck(new CheckLength(64));
		duidLeaf.setValue(Long.toString(du.getDuid()));

		// name
		Parameter nameLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.NAME_IN_DEPLOYMENT_UNIT);
		nameLeaf.setType(ParameterType.STRING);
		nameLeaf.setStorageMode(StorageMode.COMPUTED);
		nameLeaf.setWritable(false);
		nameLeaf.setNotification(0);
		nameLeaf.setActiveNotificationDenied(false);
		nameLeaf.addCheck(new CheckLength(64));
		nameLeaf.setValue(du.getName());

		// status
		Parameter statusLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.STATUS_IN_DEPLOYMENT_UNIT);
		statusLeaf.setType(ParameterType.STRING);
		statusLeaf.setStorageMode(StorageMode.COMPUTED);
		statusLeaf.setWritable(false);
		statusLeaf.setNotification(0);
		statusLeaf.setActiveNotificationDenied(false);
		statusLeaf.setValue(du.getStatusAsAString());

		// resolved
		Parameter resolvedLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.RESOLVED);
		resolvedLeaf.setType(ParameterType.BOOLEAN);
		resolvedLeaf.setStorageMode(StorageMode.COMPUTED);
		resolvedLeaf.setWritable(false);
		resolvedLeaf.setNotification(0);
		resolvedLeaf.setActiveNotificationDenied(false);
		resolvedLeaf.setValue(du.getResolved());

		// url
		Parameter urlLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.URL);
		urlLeaf.setType(ParameterType.STRING);
		urlLeaf.setStorageMode(StorageMode.COMPUTED);
		urlLeaf.setWritable(false);
		urlLeaf.setNotification(0);
		urlLeaf.setActiveNotificationDenied(false);
		urlLeaf.addCheck(new CheckLength(1024));
		urlLeaf.setValue(du.getUrl());

		// vendor
		Parameter vendorLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.VENDOR_IN_DEPLOYMENT_UNIT);
		vendorLeaf.setType(ParameterType.STRING);
		vendorLeaf.setStorageMode(StorageMode.COMPUTED);
		vendorLeaf.setWritable(false);
		vendorLeaf.setNotification(0);
		vendorLeaf.setActiveNotificationDenied(false);
		vendorLeaf.addCheck(new CheckLength(128));
		vendorLeaf.setValue(du.getVendor());

		// version
		Parameter versionLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.VERSION_IN_DEPLOYMENT_UNIT);
		versionLeaf.setType(ParameterType.STRING);
		versionLeaf.setStorageMode(StorageMode.COMPUTED);
		versionLeaf.setWritable(false);
		versionLeaf.setNotification(0);
		versionLeaf.setActiveNotificationDenied(false);
		versionLeaf.addCheck(new CheckLength(32));
		versionLeaf.setValue(du.getVersion());

		// executionUnitList
		Parameter executionUnitListLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.EXECUTION_UNIT_LIST);
		executionUnitListLeaf.setType(ParameterType.STRING);
		executionUnitListLeaf.setStorageMode(StorageMode.COMPUTED);
		executionUnitListLeaf.setWritable(false);
		executionUnitListLeaf.setNotification(0);
		executionUnitListLeaf.setActiveNotificationDenied(false);
		executionUnitListLeaf.setValue(du.getExecutionUnitList());

		// executionEnvRef
		Parameter executionEnvRefLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.EXECUTION_ENV_REF);
		executionEnvRefLeaf.setType(ParameterType.STRING);
		executionEnvRefLeaf.setStorageMode(StorageMode.COMPUTED);
		executionEnvRefLeaf.setWritable(false);
		executionEnvRefLeaf.setNotification(0);
		executionEnvRefLeaf.setActiveNotificationDenied(false);
		executionEnvRefLeaf.setValue(du.getExecutionEnvRef());
	}

	/**
	 * There is no need to update the deployment unit number branch. The related leafs must be updated.
	 * 
	 * @param pmDataService
	 * @param du
	 * @throws Fault
	 */
	public static void updateDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(final IParameterData pmDataService,
			final DeploymentUnit du) throws Fault {
		// uuid
		Parameter uuidLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.UUID);

		// duid
		Parameter duidLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.DUID);

		// name
		Parameter nameLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.NAME_IN_DEPLOYMENT_UNIT);

		// status
		Parameter statusLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.STATUS_IN_DEPLOYMENT_UNIT);

		// resolved
		Parameter resolvedLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.RESOLVED);

		// url
		Parameter urlLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.URL);

		// vendor
		Parameter vendorLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.VENDOR_IN_DEPLOYMENT_UNIT);

		// version
		Parameter versionLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.VERSION_IN_DEPLOYMENT_UNIT);

		// executionUnitList
		Parameter executionUnitListLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.EXECUTION_UNIT_LIST);

		// executionEnvRef
		Parameter executionEnvRefLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
				+ du.getUuid() + "." + SM_Baseline1ProfileDataModel.EXECUTION_ENV_REF);

		if ("Uninstalled".equals(du.getStatusAsAString())) {

			Parameter deploymentUnitNumberBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
					+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.DEPLOYMENT_UNIT
					+ du.getUuid() + ".");
			pmDataService.deleteParam(deploymentUnitNumberBranch);

			pmDataService.deleteParam(uuidLeaf);
			pmDataService.deleteParam(duidLeaf);
			pmDataService.deleteParam(nameLeaf);
			pmDataService.deleteParam(statusLeaf);
			pmDataService.deleteParam(resolvedLeaf);
			pmDataService.deleteParam(urlLeaf);
			pmDataService.deleteParam(vendorLeaf);
			pmDataService.deleteParam(versionLeaf);
			pmDataService.deleteParam(executionUnitListLeaf);
			pmDataService.deleteParam(executionEnvRefLeaf);
		} else {
			// uuid: (Long) pmDataService
			// .getParameter("Device.OSGI.BundleNumberOfEntries")
			// .getValue();
			uuidLeaf.setValue(Long.toString(du.getUuid()));
			duidLeaf.setValue(Long.toString(du.getDuid()));
			nameLeaf.setValue(du.getName());
			statusLeaf.setValue(du.getStatusAsAString());
			resolvedLeaf.setValue(du.getResolved());
			urlLeaf.setValue(du.getUrl());
			vendorLeaf.setValue(du.getVendor());
			versionLeaf.setValue(du.getVersion());
			executionUnitListLeaf.setValue(du.getExecutionUnitList());
			executionEnvRefLeaf.setValue(du.getExecutionEnvRef());
		}
	}

	/**
	 * @param pmDataService
	 * @param bundleContext
	 * @param eu
	 * @throws Fault
	 */
	public static void createExecutionUnitNumberBranchAndRelatedLeafsDatamodel(final IParameterData pmDataService,
			final BundleContext bundleContext, final ExecutionUnit eu) throws Fault {
		Parameter executionUnitNumberBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + ".");
		executionUnitNumberBranch.setType(ParameterType.ANY);

		// EUID
		Parameter euidLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.EUID);
		euidLeaf.setType(ParameterType.STRING);
		euidLeaf.setStorageMode(StorageMode.COMPUTED);
		euidLeaf.setWritable(false);
		euidLeaf.setNotification(0);
		euidLeaf.setActiveNotificationDenied(false);
		euidLeaf.addCheck(new CheckLength(64));
		euidLeaf.setValue(Long.toString(eu.getEuid()));

		// Name
		Parameter nameLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.NAME_IN_EXECUTION_UNIT);
		nameLeaf.setType(ParameterType.STRING);
		nameLeaf.setStorageMode(StorageMode.COMPUTED);
		nameLeaf.setWritable(false);
		nameLeaf.setNotification(0);
		nameLeaf.setActiveNotificationDenied(false);
		nameLeaf.addCheck(new CheckLength(32));
		nameLeaf.setValue(eu.getName());

		// ExecEnvLabel
		Parameter execEnvLabelLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.EXEC_ENV_LABEL);
		execEnvLabelLeaf.setType(ParameterType.STRING);
		execEnvLabelLeaf.setStorageMode(StorageMode.COMPUTED);
		execEnvLabelLeaf.setWritable(false);
		execEnvLabelLeaf.setNotification(0);
		execEnvLabelLeaf.setActiveNotificationDenied(false);
		execEnvLabelLeaf.addCheck(new CheckLength(64));
		execEnvLabelLeaf.setValue(eu.getExecEnvLabel());

		// Status
		Parameter statusLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.STATUS_IN_EXECUTION_UNIT);
		statusLeaf.setType(ParameterType.STRING);
		statusLeaf.setStorageMode(StorageMode.COMPUTED);
		statusLeaf.setWritable(false);
		statusLeaf.setNotification(0);
		statusLeaf.setActiveNotificationDenied(false);
		statusLeaf.setValue(eu.getStatusAsAString());

		// RequestedState
		Parameter requestedStateLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.REQUESTED_STATE);
		requestedStateLeaf.setType(ParameterType.STRING);
		requestedStateLeaf.setStorageMode(StorageMode.COMPUTED);
		requestedStateLeaf.setNotification(0);
		requestedStateLeaf.setActiveNotificationDenied(false);
		requestedStateLeaf.setImmediateChanges(true);
		requestedStateLeaf.setValue(eu.getRequestedState());
		ExecutionUnitResquestedStateSetter executionUnitResquestedStateSetter = new ExecutionUnitResquestedStateSetter(
				eu.getEuid(), bundleContext);
		requestedStateLeaf.setSetter(executionUnitResquestedStateSetter);
		requestedStateLeaf.setWritable(true);

		// ExecutionFaultCode
		Parameter executionFaultCodeLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.EXECUTION_FAULT_CODE);
		executionFaultCodeLeaf.setType(ParameterType.STRING);
		executionFaultCodeLeaf.setStorageMode(StorageMode.COMPUTED);
		executionFaultCodeLeaf.setWritable(false);
		executionFaultCodeLeaf.setNotification(0);
		executionFaultCodeLeaf.setActiveNotificationDenied(false);
		executionFaultCodeLeaf.setValue(eu.getExecutionFaultCode());

		// ExecutionFaultMessage
		Parameter executionFaultMessageLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.EXECUTION_FAULT_MESSAGE);
		executionFaultMessageLeaf.setType(ParameterType.STRING);
		executionFaultMessageLeaf.setStorageMode(StorageMode.COMPUTED);
		executionFaultMessageLeaf.setWritable(false);
		executionFaultMessageLeaf.setNotification(0);
		executionFaultMessageLeaf.setActiveNotificationDenied(false);
		executionFaultMessageLeaf.addCheck(new CheckLength(256));
		executionFaultMessageLeaf.setValue(eu.getExecutionFaultMessage());

		// Vendor
		Parameter vendorLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.VENDOR_IN_EXECUTION_UNIT);
		vendorLeaf.setType(ParameterType.STRING);
		vendorLeaf.setStorageMode(StorageMode.COMPUTED);
		vendorLeaf.setWritable(false);
		vendorLeaf.setNotification(0);
		vendorLeaf.setActiveNotificationDenied(false);
		vendorLeaf.addCheck(new CheckLength(128));
		vendorLeaf.setValue(eu.getVendor());

		// Version
		Parameter versionLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.VERSION_IN_EXECUTION_UNIT);
		versionLeaf.setType(ParameterType.STRING);
		versionLeaf.setStorageMode(StorageMode.COMPUTED);
		versionLeaf.setWritable(false);
		versionLeaf.setNotification(0);
		versionLeaf.setActiveNotificationDenied(false);
		versionLeaf.addCheck(new CheckLength(32));
		versionLeaf.setValue(eu.getVersion());

		// References
		Parameter referencesLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.REFERENCES);
		referencesLeaf.setType(ParameterType.STRING);
		referencesLeaf.setStorageMode(StorageMode.COMPUTED);
		referencesLeaf.setWritable(false);
		referencesLeaf.setNotification(0);
		referencesLeaf.setActiveNotificationDenied(false);
		referencesLeaf.setValue(eu.getReferences());

		// SupportedDataModelList
		Parameter supportedDataModelListLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.SUPPORTED_DATA_MODEL_LIST);
		supportedDataModelListLeaf.setType(ParameterType.STRING);
		supportedDataModelListLeaf.setStorageMode(StorageMode.COMPUTED);
		supportedDataModelListLeaf.setWritable(false);
		supportedDataModelListLeaf.setNotification(0);
		supportedDataModelListLeaf.setActiveNotificationDenied(false);
		supportedDataModelListLeaf.setValue(eu.getSupportedDataModelList());

		Parameter extentionsBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.EXTENSIONS);
		extentionsBranch.setType(ParameterType.ANY);

	}

	/**
	 * There is no need to update the execution unit number branch. The related leafs must be updated.
	 * 
	 * @param pmDataService
	 * @param eu
	 * @throws Fault
	 */
	public static void updateExecutionUnitNumberBranchAndRelatedLeafsDatamodel(final IParameterData pmDataService,
			final ExecutionUnit eu) throws Fault {

		// EUID
		Parameter euidLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.EUID);

		// Name
		Parameter nameLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.NAME_IN_EXECUTION_UNIT);

		// ExecEnvLabel
		Parameter execEnvLabelLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.EXEC_ENV_LABEL);
		// Status
		Parameter statusLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.STATUS_IN_EXECUTION_UNIT);

		// RequestedState
		Parameter requestedStateLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.REQUESTED_STATE);

		// ExecutionFaultCode
		Parameter executionFaultCodeLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.EXECUTION_FAULT_CODE);

		// ExecutionFaultMessage
		Parameter executionFaultMessageLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.EXECUTION_FAULT_MESSAGE);

		// Vendor
		Parameter vendorLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.VENDOR_IN_EXECUTION_UNIT);

		// Version
		Parameter versionLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.VERSION_IN_EXECUTION_UNIT);

		// References
		Parameter referencesLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.REFERENCES);

		// SupportedDataModelList
		Parameter supportedDataModelListLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.SUPPORTED_DATA_MODEL_LIST);

		Parameter extentionsBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
				+ eu.getEuid() + "." + SM_Baseline1ProfileDataModel.EXTENSIONS);

		if ("Uninstalled".equals(eu.getStatusAsAString())) {

			Parameter executionUnitNumberBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
					+ SM_Baseline1ProfileDataModel.SOFTWAREMODULES + SM_Baseline1ProfileDataModel.EXECUTION_UNIT
					+ eu.getEuid() + ".");
			pmDataService.deleteParam(executionUnitNumberBranch);

			pmDataService.deleteParam(euidLeaf);
			pmDataService.deleteParam(nameLeaf);
			pmDataService.deleteParam(execEnvLabelLeaf);

			// statusLeaf.setValue("Uninstalled");
			pmDataService.deleteParam(statusLeaf);

			pmDataService.deleteParam(requestedStateLeaf);
			pmDataService.deleteParam(executionFaultCodeLeaf);
			pmDataService.deleteParam(executionFaultMessageLeaf);
			pmDataService.deleteParam(vendorLeaf);
			pmDataService.deleteParam(versionLeaf);
			pmDataService.deleteParam(referencesLeaf);
			pmDataService.deleteParam(supportedDataModelListLeaf);
			pmDataService.deleteParam(extentionsBranch);

		} else {
			// Euid
			euidLeaf.setValue(Long.toString(eu.getEuid()));
			// Name
			nameLeaf.setValue(eu.getName());
			// ExecEnvLabel
			execEnvLabelLeaf.setValue(eu.getExecEnvLabel());
			// Status
			statusLeaf.setValue(eu.getStatusAsAString());
			// RequestedState
			requestedStateLeaf.setValue(eu.getRequestedState());
			// ExecutionFaultCode
			executionFaultCodeLeaf.setValue(eu.getExecutionFaultCode());
			// ExecutionFaultMessage
			executionFaultMessageLeaf.setValue(eu.getExecutionFaultMessage());
			// Vendor
			vendorLeaf.setValue(eu.getVendor());
			// Version
			versionLeaf.setValue(eu.getVersion());
			// References
			referencesLeaf.setValue(eu.getReferences());
			// SupportedDataModelList
			supportedDataModelListLeaf.setValue(eu.getSupportedDataModelList());
		}
	}
}
