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

	public DevicesServiceListener(final IParameterData pmDataService,
			final BundleContext bundleContext) {
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
		Log.info("event: " + event);

		// System.out.println("AC1982: 3 févr. 2014 12:06:50: event: " + event);
		// System.out.println("AC1982: 3 févr. 2014 12:07:05: event.getType(): "
		// + event.getType());
		// System.out.println("AC1982: 3 févr. 2014 12:07:07: event.toString(): "
		// + event.toString());
		// System.out.println("AC1982: 3 févr. 2014 12:07:08: event.getClass(): "
		// + event.getClass());
		// System.out
		// .println("AC1982: 3 févr. 2014 12:07:10: event.getServiceReference(): "
		// + event.getServiceReference());
		// System.out.println("AC1982: 3 févr. 2014 17:38:22: event.getServiceReference().getBundle(): "
		// + event.getServiceReference().getBundle());
		// System.out.println("AC1982: 3 févr. 2014 12:07:11: event.getSource(): "
		// + event.getSource());
		//
		// // Retrieve service from serviceReference, and thanks to
		// bundleContext.
		// Object o = BUNDLE_CONTEXT.getService(event.getServiceReference());
		// System.out.println("AC1982: 4 févr. 2014 11:39:18: o: " + o);
		// System.out.println("AC1982: 4 févr. 2014 11:39:25: o.getClass(): " +
		// o.getClass());

		updateHost(event);

	}

	private void updateHost(ServiceEvent serviceEvent) {

		// check if the service holds the DEVICE_CATEGORY property
		Object deviceCategoryObject = serviceEvent.getServiceReference()
				.getProperty(DeviceFromBaseDriver.DEVICE_CATEGORY_KEY);

		if (deviceCategoryObject != null) {

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
			String deviceDescription = (String) serviceEvent
					.getServiceReference().getProperty(
							DeviceFromBaseDriver.DEVICE_DESCRIPTION_KEY);
			String deviceSerial = (String) serviceEvent.getServiceReference()
					.getProperty(DeviceFromBaseDriver.DEVICE_SERIAL_KEY);
			String servicePid = (String) serviceEvent.getServiceReference()
					.getProperty(DeviceFromBaseDriver.SERVICE_PID_KEY);
			String deviceFriendlyName = (String) serviceEvent
					.getServiceReference().getProperty(
							DeviceFromBaseDriver.DEVICE_FRIENDLY_NAME_KEY);
			
			if (servicePid == null) {
				Log.error("service.pid is null. Nothing to do...");
				return;
			}

			DeviceFromBaseDriver deviceFromBaseDriver = null;

			switch (serviceEvent.getType()) {
			case ServiceEvent.REGISTERED:
				// new service
				// add a new Host into datamodel
				try {
					deviceFromBaseDriver = new DeviceFromBaseDriver(
							deviceCategory, deviceDescription, deviceSerial,
							servicePid, deviceFriendlyName);
					Hosts1ProfileDataModel
							.createDeviceFromBaseDriverNumberBranchAndRelatedLeafsDatamodel(
									this.pmDataService, deviceFromBaseDriver);
					Manager.getSingletonInstance().addADevice(
							deviceFromBaseDriver);
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
				deviceFromBaseDriver = Manager.getSingletonInstance().getADevice(servicePid);
				if (deviceFromBaseDriver != null) {
					try {
						 Hosts1ProfileDataModel
						 .removeDeviceFromBaseDriverNumberBranchAndRelatedLeafsDatamodel(
						 this.pmDataService, deviceFromBaseDriver);
						 Manager.getSingletonInstance().removeADevice(deviceFromBaseDriver);
					} catch (Fault e) {
						e.printStackTrace();
						Log.error(e.getMessage(), e);
						throw new RuntimeException(e.getMessage(), e);
					}
				}
				break;
			}
			
			// update number of entries
			
		} // else nothing
		
		
	}

	/**
	 * @throws NullPointerException
	 * @throws Fault
	 * @throws InvalidSyntaxException
	 */
	private void updateHosts() throws NullPointerException, Fault,
			InvalidSyntaxException {
		// Update the devices internal data, and the data model.
		List listOfDeviceFromBaseDriver = retrieveOsgiServicesDevicesRegisteredByTheBaseDrivers();

		List listOfDeviceFromManager = Manager.getSingletonInstance()
				.getDevices();

		// Publish devices base drivers' data to TR69 data model, if needed.
		for (int i = 0; i < listOfDeviceFromBaseDriver.size(); i = i + 1) {
			DeviceFromBaseDriver deviceFromBaseDriver = (DeviceFromBaseDriver) listOfDeviceFromBaseDriver
					.get(i);

			boolean deviceHasBeenFoundInManager = false;

			// Check that this device is NOT already present.
			for (int j = 0; j < listOfDeviceFromManager.size(); j = j + 1) {
				DeviceFromBaseDriver device = (DeviceFromBaseDriver) listOfDeviceFromManager
						.get(j);
				if (deviceFromBaseDriver.getServicePid().equals(
						device.getServicePid())) {
					break;
				}
			}

			if (!(deviceHasBeenFoundInManager)) {
				Hosts1ProfileDataModel
						.createDeviceFromBaseDriverNumberBranchAndRelatedLeafsDatamodel(
								this.pmDataService, deviceFromBaseDriver);
				Manager.getSingletonInstance().addADevice(deviceFromBaseDriver);
			}
		}

		// Let's search, and find the removed devices.
		Iterator devicesIterator = listOfDeviceFromManager.iterator();
		while (devicesIterator.hasNext()) {
			DeviceFromBaseDriver device = (DeviceFromBaseDriver) devicesIterator
					.next();
			boolean deviceHasBeenFoundInBundleContext = false;
			for (int i = 0; i < listOfDeviceFromBaseDriver.size(); i = i + 1) {
				DeviceFromBaseDriver d = (DeviceFromBaseDriver) listOfDeviceFromBaseDriver
						.get(i);
				if (device.getServicePid() == d.getServicePid()) {
					deviceHasBeenFoundInBundleContext = true;
					break;
				}
			}

			if (!(deviceHasBeenFoundInBundleContext)) {
				// Here, it means that the device is NO longer present.
				// Let's update the data model accordingly.
				try {
					Hosts1ProfileDataModel
							.removeDeviceFromBaseDriverNumberBranchAndRelatedLeafsDatamodel(
									this.pmDataService, device);
					Manager.getSingletonInstance().removeADevice(device);
				} catch (Fault e) {
					e.printStackTrace();
					Log.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			} // no "else" needed.
		}

		// As a consequence, the value of Device.Hosts.HostNumberOfEntries is
		// updated.
		Integer devicesNumberOfEntries = new Integer(Manager
				.getSingletonInstance().getDevices().size() + 1);
		Parameter devicesNumberOfEntriesLeaf = this.pmDataService
				.createOrRetrieveParameter(this.pmDataService.getRoot()
						+ Hosts1ProfileDataModel.HOSTS
						+ Hosts1ProfileDataModel.HOST_NUMBER_OF_ENTRIES);
		devicesNumberOfEntriesLeaf.setValue(devicesNumberOfEntries);
	}

	public static List retrieveOsgiServicesDevicesRegisteredByTheBaseDrivers() {
		List listOfDeviceFromBaseDriver = new ArrayList();

		try {
			ServiceReference[] srs = BUNDLE_CONTEXT.getAllServiceReferences(
					null, null);
			for (int i = 0; i < srs.length; i = i + 1) {
				ServiceReference sr = srs[i];
				Log.debug("Let's have a look on the serviceReference number: "
						+ i + " that is related to the bundleId: "
						+ sr.getBundle().getBundleId());

				String[] deviceCategory = null;
				try {
					deviceCategory = (String[]) sr
							.getProperty(DeviceFromBaseDriver.DEVICE_CATEGORY_KEY);
				} catch (ClassCastException e) {
					Log.debug("As debug info for the serviceReference number: "
							+ i
							+ ". Exception message: "
							+ e.getMessage()
							+ ". This service reference is non compliant. Now, let's try to cast this DEVICE_CATEGORY property to String in order to support current ZBBD implem.");
					try {
						String deviceCategoryAsAString = (String) sr
								.getProperty(DeviceFromBaseDriver.DEVICE_CATEGORY_KEY);
						deviceCategory = new String[1];
						deviceCategory[0] = deviceCategoryAsAString;
					} catch (ClassCastException e2) {
						Log.error(
								"The DEVICE_CATEGORY property (...) of this serviceReference (number:"
										+ i
										+ ") is neither a String[] (,  nor a String. This service reference is non compliant, and, as a consequence, it is rejected.",
								e2);
					}
				}

				String deviceDescription = (String) sr
						.getProperty(DeviceFromBaseDriver.DEVICE_DESCRIPTION_KEY);
				String deviceSerial = (String) sr
						.getProperty(DeviceFromBaseDriver.DEVICE_SERIAL_KEY);
				String servicePid = (String) sr
						.getProperty(DeviceFromBaseDriver.SERVICE_PID_KEY);
				String deviceFriendlyName = (String) sr
						.getProperty(DeviceFromBaseDriver.DEVICE_FRIENDLY_NAME_KEY);

				if (deviceCategory == null) {
					Log.debug("The serviceReference number: "
							+ i
							+ " does NOT have DEVICE_CATEGORY property. As a consequence, this serviceReference is ignored. Its service's properties are: "
							+ sr.getPropertyKeys());
				} else {
					Log.debug("The serviceReference number: "
							+ i
							+ " has DEVICE_CATEGORY property. As a consequence, let's try to take it into account.");
					try {
						listOfDeviceFromBaseDriver
								.add(new DeviceFromBaseDriver(deviceCategory,
										deviceDescription, deviceSerial,
										servicePid, deviceFriendlyName));
					} catch (Exception e) {
						Log.error(
								"This serviceReference is NOT compliant (so it is rejected).",
								e);
					}
				}
			}
		} catch (InvalidSyntaxException e) {
			Log.error(e.getMessage(), e);
		}

		return listOfDeviceFromBaseDriver;
	}
}
