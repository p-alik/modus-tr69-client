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

package com.francetelecom.admindm.sm_baseline1profile.model;

import java.util.Iterator;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.CheckLength;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.sm_baseline1profile.DeploymentUnit;
import com.francetelecom.admindm.sm_baseline1profile.ExecutionUnit;
import com.francetelecom.admindm.sm_baseline1profile.Manager;
import com.francetelecom.admindm.sm_baseline1profile.utils.Utils;
import com.francetelecom.admindm.soap.Fault;

public class SM_Baseline1ProfileDataModel {

	/** */
	private BundleContext bundleContext;

	/**
	 * "SoftwareModules." data model branch.
	 * 
	 * Top level object for dynamically managed software applications.
	 * */
	public static final String SOFTWAREMODULES = "SoftwareModules.";

	/**
	 * "ExecEnvNumberOfEntries" data model leaf.
	 * 
	 * The number of entries in the ExecEnv table.
	 */
	static final String EXEC_ENV_NUMBER_OF_ENTRIES = "ExecEnvNumberOfEntries";

	/**
	 * Hardcoded.
	 */
	static final Integer EXEC_ENV_NUMBER_OF_ENTRIES_VALUE = Integer.valueOf("1");

	/**
	 * "DeploymentUnitNumberOfEntries" data model leaf.
	 * 
	 * The number of entries in the DeploymentUnit table.
	 */
	public static final String DEPLOYMENT_UNIT_NUMBER_OF_ENTRIES = "DeploymentUnitNumberOfEntries";

	/**
	 * "ExecutionUnitNumberOfEntries" data model leaf.
	 * 
	 * The number of entries in the ExecutionUnit table.
	 */
	public static final String EXECUTION_UNIT_NUMBER_OF_ENTRIES = "ExecutionUnitNumberOfEntries";

	/**
	 * "ExecEnv." data model branch.
	 * 
	 * The Execution Environments that are available on the device, along with their properties and configurable
	 * settings.
	 * 
	 * At most one entry in this table can exist with a given value for Alias, or with a given value for Name.
	 * */
	static final String EXECENV = "ExecEnv.";

	/**
	 * Hardcoded.
	 */
	static final String EXECENV_NUMBER_ZERO = "0.";

	/**
	 * "Enable" data model leaf.
	 * 
	 * Indicates whether or not this ExecEnv is enabled.
	 * 
	 * Disabling an enabled Execution Environment stops it, while enabling a disabled Execution Environment starts it.
	 * 
	 * When an Execution Environment is disabled, Deployment Units installed to that Execution Environment will be
	 * unaffected, but any Execution Units currently running on that Execution Environment will automatically transition
	 * to Idle.
	 * 
	 * If a ChangeDUState is attempted on a DeploymentUnit that is to be applied against a disabled ExecEnv, that
	 * ChangeDUState operation fails and the associated DUStateChangeComplete RPC will contain a FaultStruct for that
	 * operation.
	 * 
	 * If a SetParameterValues is attempted against the ExecutionUnit.{i}.RequestedState for an ExecutionUnit that is
	 * associated with a disabled ExecEnv a CWMP Fault will be issued in response.
	 * 
	 * Disabling an Execution Environment could place the device in a non-manageable state. For example, if the
	 * operating system itself was modeled as an ExecEnv and the ACS disabled it, the CWMP management agent might be
	 * terminated leaving the device unmanageable.
	 * */
	static final String ENABLE = "Enable";

	/**
	 * "Status" data model leaf.
	 * 
	 * Indicates the status of this ExecEnv. Enumeration of:
	 * 
	 * Up Error (OPTIONAL) Disabled
	 * */
	static final String STATUS = "Status";

	/**
	 * "Name" data model leaf.
	 * 
	 * A Name provided by the CPE that adequately distinguishes this ExecEnv from all other ExecEnv instances.
	 * */
	static final String NAME = "Name";

	/**
	 * "Type" data model leaf.
	 * 
	 * Indicates the complete type and specification version of this ExecEnv.
	 * */
	static final String TYPE = "Type";

	/**
	 * "Vendor" data model leaf.
	 * 
	 * The vendor that produced this ExecEnv.
	 * */
	static final String VENDOR = "Vendor";

	/**
	 * "Version" data model leaf.
	 * 
	 * The Version of this ExecEnv as specified by the Vendor that implemented this ExecEnv, not the version of the
	 * specification.
	 * */
	static final String VERSION = "Version";

	/**
	 * "ActiveExecutionUnits" data model leaf.
	 * 
	 * Comma-separated list of strings. Each list item MUST be the path name of a row in the ExecutionUnit table. If the
	 * referenced object is deleted, the corresponding item MUST be removed from the list. Represents the ExecutionUnit
	 * instances currently running on this ExecEnv. This parameter only contains ExecutionUnit instances that currently
	 * have a ExecutionUnit.{i}.Status of Active.
	 * */
	static final String ACTIVE_EXECUTION_UNITS = "ActiveExecutionUnits";

	/**
	 * "DeploymentUnit." data model branch.
	 * 
	 * This table serves as the Deployment Unit inventory and contains status information about each Deployment Unit.
	 * 
	 * A new instance of this table gets created during the installation of a Software Module.
	 * 
	 * At most one entry in this table can exist with all the same values for UUID, Version and ExecutionEnvRef, or with
	 * a given value for Alias.
	 * */
	public static final String DEPLOYMENT_UNIT = "DeploymentUnit.";

	/**
	 * "UUID" data model leaf.
	 * 
	 * string­(36)
	 * 
	 * A Universally Unique Identifier either provided by the ACS, or generated by the CPE, at the time of Deployment
	 * Unit Installation. The format of this value is defined by [RFC4122] Version 3 (Name-Based) and [Annex
	 * H/TR-069a3].
	 * 
	 * This value MUST NOT be altered when the DeploymentUnit is updated.
	 */
	public static final String UUID = "UUID";

	/**
	 * "DUID" data model leaf.
	 * 
	 * string­(64)
	 * 
	 * Deployment Unit Identifier chosen by the targeted ExecEnv. The format of this value is Execution Environment
	 * specific.
	 */
	public static final String DUID = "DUID";

	/**
	 * "Name" data model leaf.
	 * 
	 * string­(64)
	 * 
	 * Indicates the Name of this DeploymentUnit, which is chosen by the author of the Deployment Unit.
	 * 
	 * The value of this parameter is used in the generation of the UUID based on the rules defined in [Annex
	 * H/TR-069a3].
	 */
	public static final String NAME_IN_DEPLOYMENT_UNIT = "Name";

	/**
	 * "Status" data model leaf.
	 * 
	 * string
	 * 
	 * Indicates the status of this DeploymentUnit. Enumeration of:
	 * 
	 * Installing (This instance is in the process of being Installed and SHOULD transition to the Installed state)
	 * Installed (This instance has been successfully Installed. The Resolved flag SHOULD also be referenced for
	 * dependency resolution) Updating (This instance is in the process of being Updated and SHOULD transition to the
	 * Installed state) Uninstalling (This instance is in the process of being Uninstalled and SHOULD transition to the
	 * Uninstalled state) Uninstalled (This instance has been successfully Uninstalled. This status will typically not
	 * be seen within a DeploymentUnit instance)
	 */
	public static final String STATUS_IN_DEPLOYMENT_UNIT = "Status";

	/**
	 * "Resolved" data model leaf.
	 * 
	 * boolean
	 * 
	 * Indicates whether or not this DeploymentUnit has resolved all of its dependencies.
	 */
	public static final String RESOLVED = "Resolved";

	/**
	 * "URL" data model leaf.
	 * 
	 * string­(1024)
	 * 
	 * Contains the URL used by the most recent ChangeDUState RPC to either Install or Update this DeploymentUnit.
	 */
	public static final String URL = "URL";

	/**
	 * "Vendor" data model leaf.
	 * 
	 * string­(128)
	 * 
	 * The author of this DeploymentUnit formatted as a domain name.
	 * 
	 * The value of this parameter is used in the generation of the UUID based on the rules defined in [Annex
	 * H/TR-069a3].
	 */
	public static final String VENDOR_IN_DEPLOYMENT_UNIT = "Vendor";

	/**
	 * "Version" data model leaf.
	 * 
	 * string­(32)
	 * 
	 * Version of this DeploymentUnit. The format of this value is Execution Environment specific.
	 */
	public static final String VERSION_IN_DEPLOYMENT_UNIT = "Version";

	/**
	 * "ExecutionUnitList" data model leaf.
	 * 
	 * string
	 * 
	 * Comma-separated list of strings. Each list item MUST be the path name of a row in the ExecutionUnit table. If the
	 * referenced object is deleted, the corresponding item MUST be removed from the list. Represents the ExecutionUnit
	 * instances that are associated with this DeploymentUnit instance.
	 */
	public static final String EXECUTION_UNIT_LIST = "ExecutionUnitList";

	/**
	 * "ExecutionEnvRef" data model leaf.
	 * 
	 * string
	 * 
	 * The value MUST be the path name of a row in the ExecEnv table. If the referenced object is deleted, the parameter
	 * value MUST be set to an empty string. Represents the ExecEnv instance where this DeploymentUnit instance is
	 * installed.
	 */
	public static final String EXECUTION_ENV_REF = "ExecutionEnvRef";

	/**
	 * "ExecutionUnit." data model branch.
	 * 
	 * This table serves as the Execution Unit inventory and contains both status information about each Execution Unit
	 * as well as configurable parameters for each Execution Unit.
	 * 
	 * Each DeploymentUnit that is installed can have zero or more Execution Units.
	 * 
	 * Once a Deployment Unit is installed it populates this table with its contained Execution Units.
	 * 
	 * When the Deployment Unit (that caused this ExecutionUnit to come into existence) is updated, this instance MAY be
	 * removed and new instances MAY come into existence. While the Deployment Unit (that caused this ExecutionUnit to
	 * come into existence) is being updated, all ExecutionUnit instances associated with the Deployment Unit will be
	 * stopped until the update is complete at which time they will be restored to the state that they were in before
	 * the update started.
	 * 
	 * When the Deployment Unit (that caused this ExecutionUnit to come into existence) is uninstalled, this instance is
	 * removed.
	 * 
	 * Each ExecutionUnit MAY also contain a set of vendor specific parameters displaying status and maintaining
	 * configuration that reside under the Extensions object.
	 * 
	 * At most one entry in this table can exist with a given value for EUID, or with a given value for Alias.
	 * */
	public static final String EXECUTION_UNIT = "ExecutionUnit.";

	/**
	 * "EUID" data model leaf.
	 * 
	 * string­(64)
	 * 
	 * Execution Unit Identifier chosen by the ExecEnv during installation of the associated DeploymentUnit.
	 * 
	 * The format of this value is Execution Environment specific, but it MUST be unique across ExecEnv instances. Thus,
	 * it is recommended that this be a combination of the ExecEnv.{i}.Name and an Execution Environment local unique
	 * value.
	 */
	public static final String EUID = "EUID";

	/**
	 * "Name" data model leaf.
	 * 
	 * string­(32)
	 * 
	 * The name of this ExecutionUnit as it pertains to its associated DeploymentUnit, which SHOULD be unique across all
	 * ExecutionUnit instances contained within its associated DeploymentUnit.
	 */
	public static final String NAME_IN_EXECUTION_UNIT = "Name";

	/**
	 * "ExecEnvLabel" data model leaf.
	 * 
	 * string­(64)
	 * 
	 * The name of this ExecutionUnit as provided by the ExecEnv, which SHOULD be unique across all ExecutionUnit
	 * instances contained within a specific ExecEnv.
	 */
	public static final String EXEC_ENV_LABEL = "ExecEnvLabel";

	/**
	 * "Status" data model leaf.
	 * 
	 * string
	 * 
	 * Indicates the status of this ExecutionUnit. Enumeration of:
	 * 
	 * Idle (This instance is in an Idle state and not running) Starting (This instance is in the process of Starting
	 * and SHOULD transition to the Active state) Active (This instance is currently running) Stopping (This instance is
	 * in the process of Stopping and SHOULD transition to the Idle state)
	 */
	public static final String STATUS_IN_EXECUTION_UNIT = "Status";

	/**
	 * "RequestedState" data model leaf.
	 * 
	 * string
	 * 
	 * Indicates the state transition that the ACS is requesting for this ExecutionUnit. Enumeration of:
	 * 
	 * Idle (If this ExecutionUnit is currently in Starting or Active the CPE will attempt to Stop the Execution Unit;
	 * otherwise this requested state is ignored) Active (If this ExecutionUnit is currently in Idle the CPE will
	 * attempt to Start the Execution Unit. If this ExecutionUnit is in Stopping the request is rejected and a fault
	 * raised. Otherwise this requested state is ignored)
	 * 
	 * If this ExecutionUnit is associated with an Execution Environment that is disabled and an attempt is made to
	 * alter this value, then a CWMP Fault MUST be generated.
	 * 
	 * The value of this parameter is not part of the device configuration and is always an empty string when read.
	 */
	public static final String REQUESTED_STATE = "RequestedState";

	/**
	 * "ExecutionFaultCode" data model leaf.
	 * 
	 * string
	 * 
	 * If while running or transitioning between states this ExecutionUnit identifies a fault this parameter embodies
	 * the problem. The value of NoFault MUST be used when everything is working as intended. Enumeration of:
	 * 
	 * NoFault FailureOnStart FailureOnAutoStart FailureOnStop FailureWhileActive DependencyFailure UnStartable
	 * 
	 * For fault codes not included in this list, the vendor MAY include vendor-specific values, which MUST use the
	 * format defined in [Section 3.3/TR-106a4].
	 */
	public static final String EXECUTION_FAULT_CODE = "ExecutionFaultCode";

	/**
	 * "ExecutionFaultMessage" data model leaf.
	 * 
	 * string­(256)
	 * 
	 * If while running or transitioning between states this ExecutionUnit identifies a fault this parameter provides a
	 * more detailed explanation of the problem.
	 * 
	 * If ExecutionFaultCode has the value of NoFault then the value of this parameter MUST an empty string and ignored
	 * by the ACS.
	 */
	public static final String EXECUTION_FAULT_MESSAGE = "ExecutionFaultMessage";

	/**
	 * "Vendor" data model leaf.
	 * 
	 * string­(128)
	 * 
	 * Vendor of this ExecutionUnit.
	 */
	public static final String VENDOR_IN_EXECUTION_UNIT = "Vendor";

	/**
	 * "Version" data model leaf.
	 * 
	 * string­(32)
	 * 
	 * Version of the ExecutionUnit. The format of this value is Execution Environment specific.
	 */
	public static final String VERSION_IN_EXECUTION_UNIT = "Version";

	/**
	 * "References" data model leaf.
	 * 
	 * string
	 * 
	 * Comma-separated list of strings. Each list item MUST be the path name of a table row. If the referenced object is
	 * deleted, the corresponding item MUST be removed from the list. Represents the instances of multi-instanced
	 * objects that are directly controlled by, and have come into existence because of, this ExecutionUnit. See
	 * [Appendix II.3.2/TR-157a3] for more description and some examples.
	 * 
	 * NOTE: All other objects and parameters (i.e. not multi-instanced objects) that this ExecutionUnit has caused to
	 * come into existence can be discovered via the DeviceInfo.SupportedDataModel.{i}. table.
	 */
	public static final String REFERENCES = "References";

	/**
	 * "SupportedDataModelList" data model leaf.
	 * 
	 * string
	 * 
	 * Comma-separated list of strings. Each list item MUST be the path name of a row in the
	 * DeviceInfo.SupportedDataModel table. If the referenced object is deleted, the corresponding item MUST be removed
	 * from the list. Represents the CWMP-DT schema instances that have been introduced to this device because of the
	 * existence of this ExecutionUnit.
	 */
	public static final String SUPPORTED_DATA_MODEL_LIST = "SupportedDataModelList";

	/**
	 * "Extensions." data model branch.
	 * 
	 * This object proposes a general location for vendor extensions specific to this Execution Unit, which allows
	 * multiple Execution Units to expose parameters without the concern of conflicting parameter names. These vendor
	 * extensions are related to displaying status and maintaining configuration for this Execution Unit.
	 * 
	 * It is also possible for the Execution Unit to expose status and configuration parameters within Service objects
	 * or as embedded objects and parameters directly within the root data model, in which case the combination of
	 * References and SupportedDataModelList will be used to determine their locations.
	 * 
	 * See [Appendix II.3.2/TR-157a3] for more description and some examples.
	 */
	public static final String EXTENSIONS = "Extensions.";

	/**
	 * @param pmDataService
	 * @throws Fault
	 */
	public SM_Baseline1ProfileDataModel(final BundleContext bundleContext, final IParameterData pmDataService)
			throws Fault {
		super();
		this.bundleContext = bundleContext;

		// Initialize internal data for SM_Baseline implementation.
		initImplementationInternalData();

		this.createDatamodel(pmDataService);
		// this.createGetters(pmDataService);
	}

	/**
	 * This method inits this SM_Baseline:1 Profile implementation internal data.
	 */
	private void initImplementationInternalData() {
		Bundle[] bundles = this.bundleContext.getBundles();
		for (int i = 0; i < bundles.length; i = i + 1) {
			Bundle b = bundles[i];
			// Initialize the internal data for the deployment units.
			DeploymentUnit du = new DeploymentUnit(b);
			Manager.getSingletonInstance().addADeploymentUnit(du);
			// Initialize the internal data for the execution units.
			ExecutionUnit eu = new ExecutionUnit(b);
			Manager.getSingletonInstance().addAnExecutionUnit(eu);
		}
	}

	/**
	 * @param pmDataService
	 * @throws Fault
	 */
	private void createDatamodel(final IParameterData pmDataService) throws Fault {
		Parameter softwaremodulesBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SOFTWAREMODULES);
		softwaremodulesBranch.setType(ParameterType.ANY);

		Parameter execEnvNumberOfEntriesLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SOFTWAREMODULES + EXEC_ENV_NUMBER_OF_ENTRIES);
		execEnvNumberOfEntriesLeaf.setType(ParameterType.UINT);
		execEnvNumberOfEntriesLeaf.setStorageMode(StorageMode.COMPUTED);
		execEnvNumberOfEntriesLeaf.setWritable(false);
		execEnvNumberOfEntriesLeaf.setNotification(0);
		execEnvNumberOfEntriesLeaf.setActiveNotificationDenied(false);
		execEnvNumberOfEntriesLeaf.setValue(EXEC_ENV_NUMBER_OF_ENTRIES_VALUE);

		Parameter deploymentUnitNumberOfEntriesLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SOFTWAREMODULES + DEPLOYMENT_UNIT_NUMBER_OF_ENTRIES);
		deploymentUnitNumberOfEntriesLeaf.setType(ParameterType.UINT);
		deploymentUnitNumberOfEntriesLeaf.setStorageMode(StorageMode.COMPUTED);
		deploymentUnitNumberOfEntriesLeaf.setWritable(false);
		deploymentUnitNumberOfEntriesLeaf.setNotification(0);
		deploymentUnitNumberOfEntriesLeaf.setActiveNotificationDenied(false);

		Parameter executionUnitNumberOfEntriesLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SOFTWAREMODULES + EXECUTION_UNIT_NUMBER_OF_ENTRIES);
		executionUnitNumberOfEntriesLeaf.setType(ParameterType.UINT);
		executionUnitNumberOfEntriesLeaf.setStorageMode(StorageMode.COMPUTED);
		executionUnitNumberOfEntriesLeaf.setWritable(false);
		executionUnitNumberOfEntriesLeaf.setNotification(0);
		executionUnitNumberOfEntriesLeaf.setActiveNotificationDenied(false);

		Parameter execEnvBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + SOFTWAREMODULES
				+ EXECENV);
		execEnvBranch.setType(ParameterType.ANY);

		Parameter execEnvNumberZeroBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SOFTWAREMODULES + EXECENV + EXECENV_NUMBER_ZERO);
		execEnvNumberZeroBranch.setType(ParameterType.ANY);

		// XXX AAA: Enable should be WRITABLE.
		Parameter enableLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + SOFTWAREMODULES
				+ EXECENV + EXECENV_NUMBER_ZERO + ENABLE);
		enableLeaf.setType(ParameterType.BOOLEAN);
		enableLeaf.setStorageMode(StorageMode.COMPUTED);
		enableLeaf.setWritable(false);
		enableLeaf.setNotification(0);
		enableLeaf.setActiveNotificationDenied(false);
		enableLeaf.setValue(Boolean.TRUE);

		Parameter statusLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + SOFTWAREMODULES
				+ EXECENV + EXECENV_NUMBER_ZERO + STATUS);
		statusLeaf.setType(ParameterType.STRING);
		statusLeaf.setStorageMode(StorageMode.COMPUTED);
		statusLeaf.setWritable(false);
		statusLeaf.setNotification(0);
		statusLeaf.setActiveNotificationDenied(false);
		statusLeaf.addCheck(new CheckLength(45));
		statusLeaf.setValue("Up");

		Parameter nameLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + SOFTWAREMODULES
				+ EXECENV + EXECENV_NUMBER_ZERO + NAME);
		nameLeaf.setType(ParameterType.STRING);
		nameLeaf.setStorageMode(StorageMode.COMPUTED);
		nameLeaf.setWritable(false);
		nameLeaf.setNotification(0);
		nameLeaf.setActiveNotificationDenied(false);
		nameLeaf.addCheck(new CheckLength(32));
		nameLeaf.setValue("ExecEnvName:OSGi");

		Parameter typeLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + SOFTWAREMODULES
				+ EXECENV + EXECENV_NUMBER_ZERO + TYPE);
		typeLeaf.setType(ParameterType.STRING);
		typeLeaf.setStorageMode(StorageMode.COMPUTED);
		typeLeaf.setWritable(false);
		typeLeaf.setNotification(0);
		typeLeaf.setActiveNotificationDenied(false);
		typeLeaf.addCheck(new CheckLength(64));
		typeLeaf.setValue("OSGiR4.3");

		Parameter vendorLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + SOFTWAREMODULES
				+ EXECENV + EXECENV_NUMBER_ZERO + VENDOR);
		vendorLeaf.setType(ParameterType.STRING);
		vendorLeaf.setStorageMode(StorageMode.COMPUTED);
		vendorLeaf.setWritable(false);
		vendorLeaf.setNotification(0);
		vendorLeaf.setActiveNotificationDenied(false);
		vendorLeaf.addCheck(new CheckLength(128));
		vendorLeaf.setValue("ApacheFelix");

		Parameter versionLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + SOFTWAREMODULES
				+ EXECENV + EXECENV_NUMBER_ZERO + VERSION);
		versionLeaf.setType(ParameterType.STRING);
		versionLeaf.setStorageMode(StorageMode.COMPUTED);
		versionLeaf.setWritable(false);
		versionLeaf.setNotification(0);
		versionLeaf.setActiveNotificationDenied(false);
		versionLeaf.addCheck(new CheckLength(32));
		versionLeaf.setValue("4.0.3");

		Parameter activeExecutionUnitsLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SOFTWAREMODULES + EXECENV + EXECENV_NUMBER_ZERO + ACTIVE_EXECUTION_UNITS);
		activeExecutionUnitsLeaf.setType(ParameterType.STRING);
		activeExecutionUnitsLeaf.setStorageMode(StorageMode.COMPUTED);
		activeExecutionUnitsLeaf.setWritable(false);
		activeExecutionUnitsLeaf.setNotification(0);
		activeExecutionUnitsLeaf.setActiveNotificationDenied(false);
		// XXX AAA: Implement activeExecutionUnitsLeaf.
		activeExecutionUnitsLeaf.setValue("TODO: Implement this.");

		createDeploymentUnitBranchDatamodel(pmDataService);

		createExecutionUnitBranchDatamodel(pmDataService);
	}

	private void createDeploymentUnitBranchDatamodel(final IParameterData pmDataService) throws Fault {
		Parameter deploymentUnitBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SOFTWAREMODULES + DEPLOYMENT_UNIT);
		deploymentUnitBranch.setType(ParameterType.ANY);

		Iterator duIterator = Manager.getSingletonInstance().getDeploymentUnits().iterator();
		while (duIterator.hasNext()) {
			DeploymentUnit du = (DeploymentUnit) duIterator.next();
			Utils.createDeploymentUnitNumberBranchAndRelatedLeafsDatamodel(pmDataService, du);
		}
	}

	private void createExecutionUnitBranchDatamodel(final IParameterData pmDataService) throws Fault {
		Parameter executionUnitBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot()
				+ SOFTWAREMODULES + EXECUTION_UNIT);
		executionUnitBranch.setType(ParameterType.ANY);

		Iterator euIterator = Manager.getSingletonInstance().getExecutionUnits().iterator();
		while (euIterator.hasNext()) {
			ExecutionUnit eu = (ExecutionUnit) euIterator.next();
			Utils.createExecutionUnitNumberBranchAndRelatedLeafsDatamodel(pmDataService, this.bundleContext, eu);
		}
	}

}
