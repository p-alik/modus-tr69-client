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

package com.francetelecom.admindm.hosts1profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.hosts1profile.model.Hosts1ProfileDataModel;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;

/**
 * Service listener that updates data related to base drivers' devices.
 * 
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class DevicesServiceListener implements ServiceListener {

	private IParameterData pmDataService;
	private static BundleContext BUNDLE_CONTEXT;

	public DevicesServiceListener(final IParameterData pmDataService, final BundleContext bundleContext) {
		this.pmDataService = pmDataService;
		BUNDLE_CONTEXT = bundleContext;

		try {
			// Update data model corresponding to Hosts.
			updateHosts();
		} catch (NullPointerException e) {
			Log.error(e.getMessage(), e);
		} catch (Fault e) {
			Log.error(e.getMessage(), e);
		} catch (InvalidSyntaxException e) {
			Log.error(e.getMessage(), e);
		}
	}

	public void serviceChanged(final ServiceEvent event) {
		Log.info("DevicesServiceListener.serviceChanged event: " + event);
		updateHost(event);
	}

	private void updateHost(ServiceEvent serviceEvent) {
		Manager manager = Manager.getSingletonInstance();
		// check if the service holds the DEVICE_CATEGORY property
		Object deviceCategoryObject = serviceEvent.getServiceReference().getProperty(
				DeviceFromBaseDriver.DEVICE_CATEGORY_KEY);

		if (deviceCategoryObject == null) {
			Log.debug("No DEVICE_CATEGORY_KEY property. Ignore...");
			return; // do nothing
		}

		// cast device category
		String[] deviceCategory = null;
		if (deviceCategoryObject instanceof String[]) {
			deviceCategory = (String[]) deviceCategoryObject;
		} else if (deviceCategoryObject instanceof String) {
			deviceCategory = new String[] { (String) deviceCategoryObject };
		} else {
			Log.error("deviceCategory property is neither an array of String nor a String. Nothing to do...");
			return;
		}
		// retrieve other properties
		String servicePid = (String) serviceEvent.getServiceReference().getProperty(
				DeviceFromBaseDriver.SERVICE_PID_KEY);
		if (servicePid == null) {
			Log.error("service.pid is null. Nothing to do...");
			return;
		}

		switch (serviceEvent.getType()) {
		case ServiceEvent.REGISTERED:
			// new service
			// add a new Host into datamodel
			try {
				String deviceDescription = (String) serviceEvent.getServiceReference().getProperty(
						DeviceFromBaseDriver.DEVICE_DESCRIPTION_KEY);
				String deviceSerial = (String) serviceEvent.getServiceReference().getProperty(
						DeviceFromBaseDriver.DEVICE_SERIAL_KEY);
				String deviceFriendlyName = (String) serviceEvent.getServiceReference().getProperty(
						DeviceFromBaseDriver.DEVICE_FRIENDLY_NAME_KEY);
				DeviceFromBaseDriver deviceFromBaseDriver = new DeviceFromBaseDriver(deviceCategory, deviceDescription,
						deviceSerial, servicePid, deviceFriendlyName);
				Hosts1ProfileDataModel.createDeviceFromBaseDriverNumberBranchAndRelatedLeafsDatamodel(
						this.pmDataService, deviceFromBaseDriver);
				manager.addADevice(deviceFromBaseDriver);
			} catch (Exception e) {
				Log.error("unable to create a new Host", e);
			}
			break;
		case ServiceEvent.MODIFIED:
			// nothing to do
			break;
		case ServiceEvent.UNREGISTERING:
			// service gone
			// retrieve the related host and delete it
			DeviceFromBaseDriver deviceFromBaseDriver = manager.getADevice(servicePid);
			if (deviceFromBaseDriver != null) {
				try {
					Hosts1ProfileDataModel.removeDeviceFromBaseDriverNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, deviceFromBaseDriver);
					manager.removeADevice(deviceFromBaseDriver);
				} catch (Fault e) {
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			break;
		}
		// update number of entries
	}

	/**
	 * @throws NullPointerException
	 * @throws Fault
	 * @throws InvalidSyntaxException
	 */
	private void updateHosts() throws NullPointerException, Fault, InvalidSyntaxException {
		Manager manager = Manager.getSingletonInstance();
		// Update the devices internal data, and the data model.
		List devicesFromBaseDriver = retrieveOsgiServicesDevicesRegisteredByTheBaseDrivers();
		List devicesFromManager = manager.getDevices();

		// Publish devices base drivers' data to TR69 data model, if needed.
		for (Iterator itBD = devicesFromBaseDriver.iterator(); itBD.hasNext();) {
			DeviceFromBaseDriver deviceFromBaseDriver = (DeviceFromBaseDriver) itBD.next();
			// Check that this device is NOT already present.
			boolean foundInManager = false;
			for (Iterator itM = devicesFromManager.iterator(); itM.hasNext();) {
				DeviceFromBaseDriver device = (DeviceFromBaseDriver) itM.next();
				if (deviceFromBaseDriver.getServicePid().equals(device.getServicePid())) {
					foundInManager = true;
					break;
				}
			}
			if (!foundInManager) {
				// new device! Create it in data model
				Hosts1ProfileDataModel.createDeviceFromBaseDriverNumberBranchAndRelatedLeafsDatamodel(
						this.pmDataService, deviceFromBaseDriver);
				manager.addADevice(deviceFromBaseDriver);
			}
		}

		// Let's search, and find the removed devices.
		for (Iterator itM = devicesFromManager.iterator(); itM.hasNext();) {
			DeviceFromBaseDriver device = (DeviceFromBaseDriver) itM.next();
			boolean foundInBundleContext = false;
			for (Iterator itBD = devicesFromBaseDriver.iterator(); itBD.hasNext();) {
				DeviceFromBaseDriver d = (DeviceFromBaseDriver) itBD.next();
				if (device.getServicePid().equals(d.getServicePid())) {
					foundInBundleContext = true;
					break;
				}
			}

			if (!foundInBundleContext) {
				// Here, it means that the device is NO longer present.
				// Let's update the data model accordingly.
				try {
					Hosts1ProfileDataModel.removeDeviceFromBaseDriverNumberBranchAndRelatedLeafsDatamodel(
							this.pmDataService, device);
					manager.removeADevice(device);
				} catch (Fault e) {
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			} // no "else" needed.
		}

		// As a consequence, the value of Device.Hosts.HostNumberOfEntries is updated.
		Integer devicesNumberOfEntries = new Integer(manager.getDevices().size() + 1);
		Parameter devicesNumberOfEntriesLeaf = this.pmDataService.createOrRetrieveParameter(this.pmDataService
				.getRoot() + Hosts1ProfileDataModel.HOSTS + Hosts1ProfileDataModel.HOST_NUMBER_OF_ENTRIES);
		devicesNumberOfEntriesLeaf.setValue(devicesNumberOfEntries);
	}

	public static List retrieveOsgiServicesDevicesRegisteredByTheBaseDrivers() {
		List listOfDeviceFromBaseDriver = new ArrayList();
		try {
			ServiceReference[] srs = BUNDLE_CONTEXT.getAllServiceReferences(null, null);
			for (int idx = 0; idx < srs.length; idx++) {
				ServiceReference sr = srs[idx];
				Log.debug("Let's have a look on the serviceReference number: " + idx
						+ " that is related to the bundleId: " + sr.getBundle().getBundleId());

				Object devCategory = sr.getProperty(DeviceFromBaseDriver.DEVICE_CATEGORY_KEY);
				if (devCategory == null) {
					Log.debug("The serviceReference number: "
							+ idx
							+ " does NOT have DEVICE_CATEGORY property. As a consequence, this serviceReference is ignored. Its service's properties are: "
							+ sr.getPropertyKeys());
					return listOfDeviceFromBaseDriver;
				}
				String[] deviceCategory = null;
				if (devCategory instanceof String[]) {
					deviceCategory = (String[]) devCategory;
				} else if (devCategory instanceof String) {
					Log.debug("As debug info for the serviceReference number: "
							+ idx
							+ ". This service reference is non compliant. Now, however we can cast cast this DEVICE_CATEGORY property to String in order to support current ZBBD implem.");
					deviceCategory = new String[1];
					deviceCategory[0] = (String) devCategory;
				} else {
					Log.error("The DEVICE_CATEGORY property (...) of this serviceReference (number:"
							+ idx
							+ ") is neither a String[] (,  nor a String. This service reference is non compliant, and, as a consequence, it is rejected.");
					return listOfDeviceFromBaseDriver;
				}

				String deviceDescription = (String) sr.getProperty(DeviceFromBaseDriver.DEVICE_DESCRIPTION_KEY);
				String deviceSerial = (String) sr.getProperty(DeviceFromBaseDriver.DEVICE_SERIAL_KEY);
				String servicePid = (String) sr.getProperty(DeviceFromBaseDriver.SERVICE_PID_KEY);
				String deviceFriendlyName = (String) sr.getProperty(DeviceFromBaseDriver.DEVICE_FRIENDLY_NAME_KEY);

				Log.debug("The serviceReference number: " + idx
						+ " has DEVICE_CATEGORY property. As a consequence, let's try to take it into account.");
				try {
					listOfDeviceFromBaseDriver.add(new DeviceFromBaseDriver(deviceCategory, deviceDescription,
							deviceSerial, servicePid, deviceFriendlyName));
				} catch (Exception e) {
					Log.error("This serviceReference is NOT compliant (so it is rejected).", e);
				}
			}
		} catch (InvalidSyntaxException e) {
			Log.error(e.getMessage(), e);
		}
		return listOfDeviceFromBaseDriver;
	}

}
