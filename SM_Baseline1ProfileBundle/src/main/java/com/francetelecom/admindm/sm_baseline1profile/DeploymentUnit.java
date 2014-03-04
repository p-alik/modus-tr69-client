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

import org.osgi.framework.Bundle;

public class DeploymentUnit {

	/** The bundle id. */
	private long uuid;
	/** The bundle id. */
	private long duid;
	private String name = "undefined";
	private String statusAsAString;
	private Boolean resolved;
	private String url;
	private String vendor = "undefined";
	private String version;

	private String executionUnitList;
	// hardcoded.
	private String executionEnvRef = "Device.SoftwareModules.ExecEnv.0.";

	/**
	 * Create a DeploymentUnit initialized thanks to the info coming from bundle b.
	 * 
	 * @param b
	 */
	public DeploymentUnit(final Bundle b) {
		this.updateDeploymentUnit(b);
	}

	/**
	 * Update current DeploymentUnit thanks to the info coming from bundle b.
	 * 
	 * @param b
	 */
	public void updateDeploymentUnit(final Bundle b) {
		this.uuid = b.getBundleId();
		this.duid = this.uuid;
		// this.name = b.getSymbolicName();
		if (b.getHeaders().get("Bundle-Name") != null) {
			this.name = (String) b.getHeaders().get("Bundle-Name");
		}

		int status = b.getState();
		this.statusAsAString = checkStatusAndMoveItToAString(status);
		this.resolved = isThisBundleResolved(status);
		this.url = b.getLocation();
		if (b.getHeaders().get("Bundle-Vendor") != null) {
			this.vendor = (String) b.getHeaders().get("Bundle-Vendor");
		}
		this.version = (String) b.getHeaders().get("Bundle-Version");
		// In OSGi, a bundle is both a deployment unit, and an execution unit,
		// so uuid == duid == euid.
		this.executionUnitList = Long.toString(this.uuid);
		// this.executionEnvRef = ;
	}

	/**
	 * Update current DeploymentUnit when the corresponding bundle has been uninstalled.
	 */
	public void updateDeploymentUnitWhenTheBundleHasBeenUninstalled() {
		int status = Bundle.UNINSTALLED;
		this.statusAsAString = checkStatusAndMoveItToAString(status);
		this.resolved = isThisBundleResolved(status);
	}

	public long getUuid() {
		return this.uuid;
	}

	public long getDuid() {
		return this.duid;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * @param bundleStatus
	 * @see org.osgi.framework.Bundle
	 * 
	 *      Bundle.UNINSTALLED = 1, Bundle.INSTALLED = 2.
	 * 
	 *      For info, the others OSGi states are: RESOLVED = 4, STARTING = 8, STOPPING = 16, ACTIVE = 32.
	 * @return the deployment unit status as a String (i.e. Installing, Installed, Updating, Uninstalling, or
	 *         Uninstalled).
	 */
	private String checkStatusAndMoveItToAString(final int bundleStatus) {
		String result;
		if (bundleStatus == Bundle.UNINSTALLED) {
			result = "Uninstalled";
		} else if (bundleStatus == Bundle.INSTALLED) {
			result = "Installed";
		} else if (bundleStatus == Bundle.RESOLVED) {
			result = "Installed";
		} else if (bundleStatus == Bundle.STARTING) {
			result = "Installed";
		} else if (bundleStatus == Bundle.STOPPING) {
			result = "Installed";
		} else if (bundleStatus == Bundle.ACTIVE) {
			result = "Installed";
		} else {
			throw new RuntimeException("This bundle state (" + bundleStatus + ") is UNKNOWN.");
		}
		return result;
	}

	public String getStatusAsAString() {
		return this.statusAsAString;
	}

	public Boolean getResolved() {
		return this.resolved;
	}

	public String getUrl() {
		return this.url;
	}

	public String getVendor() {
		return this.vendor;
	}

	public String getVersion() {
		return this.version;
	}

	public String getExecutionUnitList() {
		return this.executionUnitList;
	}

	public String getExecutionEnvRef() {
		return this.executionEnvRef;
	}

	private Boolean isThisBundleResolved(final int bundleStatus) {
		Boolean result;
		if (bundleStatus == Bundle.UNINSTALLED) {
			result = Boolean.FALSE;
		} else if (bundleStatus == Bundle.INSTALLED) {
			result = Boolean.FALSE;
		} else if (bundleStatus == Bundle.RESOLVED) {
			result = Boolean.TRUE;
		} else if (bundleStatus == Bundle.STARTING) {
			result = Boolean.TRUE;
		} else if (bundleStatus == Bundle.STOPPING) {
			result = Boolean.TRUE;
		} else if (bundleStatus == Bundle.ACTIVE) {
			result = Boolean.TRUE;
		} else {
			throw new RuntimeException("This deployment unit state (" + bundleStatus + ") is UNKNOWN.");
		}
		return result;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String toString = this.getClass().getName() + "[" + "uuid=" + this.uuid + ",duid=" + this.duid + ",name="
				+ this.name + ",statusAsAString=" + this.statusAsAString + ",resolved=" + this.resolved + ",url="
				+ this.url + ",vendor=" + this.vendor + ",version=" + this.version + ",executionUnitList="
				+ this.executionUnitList + ",executionEnvRef=" + this.executionEnvRef + "]";
		return toString;
	}

}
