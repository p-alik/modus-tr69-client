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

package com.francetelecom.admindm.hosts1profile.model;

import java.net.InetAddress;
import java.util.Iterator;
import java.util.List;

import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.com.UDPServer;
import com.francetelecom.admindm.hosts1profile.DeviceFromBaseDriver;
import com.francetelecom.admindm.hosts1profile.DevicesServiceListener;
import com.francetelecom.admindm.hosts1profile.Manager;
import com.francetelecom.admindm.model.CheckLength;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class Hosts1ProfileDataModel {

	/**
	 * "Hosts." data model branch.
	 * 
	 * This object provides information about each of the hosts on the LAN, including those whose IP address was
	 * allocated by the CPE using DHCP as well as hosts with statically allocated IP addresses. It can also include
	 * non-IP hosts.
	 * */
	public static final String HOSTS = "Hosts.";

	/**
	 * "HostNumberOfEntries" data model leaf.
	 * 
	 * The number of entries in the Host table.
	 */
	public static final String HOST_NUMBER_OF_ENTRIES = "HostNumberOfEntries";

	/**
	 * "Host." data model branch.
	 * 
	 * Host table.
	 * 
	 * At most one entry in this table can exist with a given value for Alias, or with a given value for PhysAddress.
	 * */
	private static final String HOST = "Host.";

	// Hardcoded.
	private static final String HOST_NUMBER_ZERO = "0.";

	/**
	 * "IPAddress" data model leaf.
	 * 
	 * [IPAddress] Current IP Address of the host. An empty string if no address is available.
	 * 
	 * If more than one IP address for this host is known, the CPE will choose a primary address. All known IP addresses
	 * can be listed in the IPv4Address and IPv6Address tables.
	 * */
	private static final String IP_ADDRESS = "IPAddress";

	/**
	 * "AddressSource" data model leaf.
	 * 
	 * Indicates whether the IP address of the host was allocated by the CPE using DHCP, was assigned to the host
	 * statically, or was assigned using automatic IP address allocation. Enumeration of:
	 * 
	 * DHCP Static AutoIP None
	 * 
	 * This parameter is DEPRECATED because only None and DHCP made sense (the CPE doesn't know whether the address is
	 * Static or AutoIP). The DHCP case is now handled via the DHCPClient reference.
	 */
	private static final String ADDRESS_SOURCE = "AddressSource";

	/**
	 * "LeaseTimeRemaining" data model leaf.
	 * 
	 * DHCP lease time remaining in seconds. A value of -1 indicates an infinite lease. The value MUST be 0 (zero) if
	 * the AddressSource is not DHCP.
	 * 
	 * This parameter is DEPRECATED because DHCP lease/lifetime information can be accessed via the DHCPClient
	 * reference.
	 */
	private static final String LEASE_TIME_REMAINING = "LeaseTimeRemaining";

	/**
	 * "PhysAddress" data model leaf.
	 * 
	 * Unique physical identifier of the host. For many layer 2 technologies this is typically a MAC address.
	 */
	private static final String PHYS_ADDRESS = "PhysAddress";

	/**
	 * "HostName" data model leaf.
	 * 
	 * The device's host name or an empty string if unknown.
	 */
	private static final String HOST_NAME = "HostName";

	/**
	 * "Active" data model leaf.
	 * 
	 * Whether or not the host is currently present on the LAN. The method of presence detection is a local matter to
	 * the CPE.
	 * 
	 * The ability to list inactive hosts is OPTIONAL. If the CPE includes inactive hosts in this table, Active MUST be
	 * set to false for each inactive host. The length of time an inactive host remains listed in this table is a local
	 * matter to the CPE.
	 */
	private static final String ACTIVE = "Active";

	// Hardcoded.
	private static final Integer ACTIVE_VALUE = Integer.valueOf("1");

	// private static ZigbeeDevices zigbeeDevices;

	/**
	 * @param pmDataService
	 * @throws Fault
	 */
	public Hosts1ProfileDataModel(final IParameterData pmDataService) throws Fault {
		super();

		// Initialize internal data for Hosts:1 Profile implementation.
		initImplementationInternalData();

		this.createDatamodel(pmDataService);
		// this.createGetters(pmDataService);
	}

	/**
	 * This method inits this Hosts:1 Profile implementation internal data.
	 */
	/**
	 * 
	 */
	/**
	 * 
	 */
	private void initImplementationInternalData() {
		// // Call base driver implem here, instead of the following line of
		// // mock.
		// zigbeeDevices = new ZigbeeDevices();

		// Update the deployment units internal data, and the data model.
		List listOfDeviceFromBaseDriver = DevicesServiceListener
				.retrieveOsgiServicesDevicesRegisteredByTheBaseDrivers();

		// Publish device base driver's data to TR69 data model.
		Iterator it = listOfDeviceFromBaseDriver.iterator();
		while (it.hasNext()) {
			DeviceFromBaseDriver deviceFromBaseDriver = (DeviceFromBaseDriver) it.next();
			Manager.getSingletonInstance().addADevice(deviceFromBaseDriver);
		}
	}

	/**
	 * @param pmDataService
	 * @throws Fault
	 */
	private void createDatamodel(final IParameterData pmDataService) throws Fault {
		Parameter hostsBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS);
		hostsBranch.setType(ParameterType.ANY);

		Parameter hostNumberOfEntriesLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS
				+ HOST_NUMBER_OF_ENTRIES);
		hostNumberOfEntriesLeaf.setType(ParameterType.UINT);
		hostNumberOfEntriesLeaf.setStorageMode(StorageMode.COMPUTED);
		hostNumberOfEntriesLeaf.setWritable(false);
		hostNumberOfEntriesLeaf.setNotification(0);
		hostNumberOfEntriesLeaf.setActiveNotificationDenied(false);

		Parameter hostBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST);
		hostBranch.setType(ParameterType.ANY);

		// This CPE is itself a "leaf".
		Parameter hostNumberZeroBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ HOST_NUMBER_ZERO);
		hostNumberZeroBranch.setType(ParameterType.ANY);

		Parameter ipAddressLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ HOST_NUMBER_ZERO + IP_ADDRESS);
		ipAddressLeaf.setType(ParameterType.STRING);
		ipAddressLeaf.setStorageMode(StorageMode.COMPUTED);
		ipAddressLeaf.setWritable(false);
		ipAddressLeaf.setNotification(0);
		ipAddressLeaf.setActiveNotificationDenied(false);
		ipAddressLeaf.addCheck(new CheckLength(45));
		String IPAddress = ""; // default value as specified in TR181 i2 a6.
		InetAddress primaryInetAddress = UDPServer.getExternalInetAddress();
		if (primaryInetAddress != null) {
			IPAddress = primaryInetAddress.getHostAddress();
		} // no "else" needed.
		ipAddressLeaf.setValue(IPAddress);

		// Deprecated.
		Parameter addressSourceLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ HOST_NUMBER_ZERO + ADDRESS_SOURCE);
		addressSourceLeaf.setType(ParameterType.STRING);
		addressSourceLeaf.setStorageMode(StorageMode.COMPUTED);
		addressSourceLeaf.setWritable(false);
		addressSourceLeaf.setNotification(0);
		addressSourceLeaf.setActiveNotificationDenied(false);
		addressSourceLeaf.setValue("DEPRECATED cf. TR181-i2-a6.");

		Parameter leaseTimeRemainingLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS
				+ HOST + HOST_NUMBER_ZERO + LEASE_TIME_REMAINING);
		leaseTimeRemainingLeaf.setType(ParameterType.STRING);
		leaseTimeRemainingLeaf.setStorageMode(StorageMode.COMPUTED);
		leaseTimeRemainingLeaf.setWritable(false);
		leaseTimeRemainingLeaf.setNotification(0);
		leaseTimeRemainingLeaf.setActiveNotificationDenied(false);
		leaseTimeRemainingLeaf.setValue("DEPRECATED cf. TR181-i2-a6.");

		Parameter physAddressLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ HOST_NUMBER_ZERO + PHYS_ADDRESS);
		physAddressLeaf.setType(ParameterType.STRING);
		physAddressLeaf.setStorageMode(StorageMode.COMPUTED);
		physAddressLeaf.setWritable(false);
		physAddressLeaf.setNotification(0);
		physAddressLeaf.setActiveNotificationDenied(false);
		physAddressLeaf.addCheck(new CheckLength(64));

		String properlyFormattedMacAddress = "TODO: TO BE IMPLEMENTED FOR JAVA 1.4";
		// TODO: reintroduce Mac Address code.
		// String properlyFormattedMacAddress = UDPServer
		// .getProperlyFormattedMacAddress();
		physAddressLeaf.setValue(properlyFormattedMacAddress);

		Parameter hostNameLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ HOST_NUMBER_ZERO + HOST_NAME);
		hostNameLeaf.setType(ParameterType.STRING);
		hostNameLeaf.setStorageMode(StorageMode.COMPUTED);
		hostNameLeaf.setWritable(false);
		hostNameLeaf.setNotification(0);
		hostNameLeaf.setActiveNotificationDenied(false);
		hostNameLeaf.addCheck(new CheckLength(64));
		String hostName = ""; // default value as specified in TR181 i2 a6.
		if (primaryInetAddress != null) {
			hostName = primaryInetAddress.getHostName();
		} // no "else" needed.
		hostNameLeaf.setValue(hostName);

		Parameter activeLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ HOST_NUMBER_ZERO + ACTIVE);
		activeLeaf.setType(ParameterType.BOOLEAN);
		activeLeaf.setStorageMode(StorageMode.COMPUTED);
		activeLeaf.setWritable(false);
		activeLeaf.setNotification(0);
		activeLeaf.setActiveNotificationDenied(false);
		activeLeaf.setValue(ACTIVE_VALUE);

		List listOfDeviceFromBaseDriver = Manager.getSingletonInstance().getDevices();

		// Publish device base driver's data to TR69 data model.
		Iterator it = listOfDeviceFromBaseDriver.iterator();
		while (it.hasNext()) {
			DeviceFromBaseDriver deviceFromBaseDriver = (DeviceFromBaseDriver) it.next();
			createDeviceFromBaseDriverNumberBranchAndRelatedLeafsDatamodel(pmDataService, deviceFromBaseDriver);
		}

		// Device.Hosts.HostNumberOfEntries's value is equal to the number of
		// Devices + the CPE that is itself a host here.
		Integer devicesNumberOfEntries = new Integer(Manager.getSingletonInstance().getDevices().size() + 1);
		hostNumberOfEntriesLeaf.setValue(devicesNumberOfEntries);
	}

	/**
	 * @param pmDataService
	 * @param deviceZigbee
	 * @param deviceNumberBranch
	 * @throws Fault
	 */
	public static void createDeviceFromBaseDriverNumberBranchAndRelatedLeafsDatamodel(
			final IParameterData pmDataService, final DeviceFromBaseDriver deviceFromBaseDriver) throws Fault {
		String deviceNumberBranchIsTheServicePid = deviceFromBaseDriver.getServicePid();

		Parameter hostNumberZeroBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + ".");
		hostNumberZeroBranch.setType(ParameterType.ANY);

		Parameter ipAddressLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + IP_ADDRESS);
		ipAddressLeaf.setType(ParameterType.STRING);
		ipAddressLeaf.setStorageMode(StorageMode.COMPUTED);
		ipAddressLeaf.setWritable(false);
		ipAddressLeaf.setNotification(0);
		ipAddressLeaf.setActiveNotificationDenied(false);
		ipAddressLeaf.addCheck(new CheckLength(45));
		String IPAddress = ""; // default value as specified in TR181 i2 a6.
		// There is no IPAddress in ZigBee
		// TR indicates that the default empty value must be kept.
		ipAddressLeaf.setValue(IPAddress);

		// Deprecated.
		Parameter addressSourceLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + ADDRESS_SOURCE);
		addressSourceLeaf.setType(ParameterType.STRING);
		addressSourceLeaf.setStorageMode(StorageMode.COMPUTED);
		addressSourceLeaf.setWritable(false);
		addressSourceLeaf.setNotification(0);
		addressSourceLeaf.setActiveNotificationDenied(false);
		addressSourceLeaf.setValue("DEPRECATED cf. TR181-i2-a6.");

		Parameter leaseTimeRemainingLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS
				+ HOST + deviceNumberBranchIsTheServicePid + "." + LEASE_TIME_REMAINING);
		leaseTimeRemainingLeaf.setType(ParameterType.STRING);
		leaseTimeRemainingLeaf.setStorageMode(StorageMode.COMPUTED);
		leaseTimeRemainingLeaf.setWritable(false);
		leaseTimeRemainingLeaf.setNotification(0);
		leaseTimeRemainingLeaf.setActiveNotificationDenied(false);
		leaseTimeRemainingLeaf.setValue("DEPRECATED cf. TR181-i2-a6.");

		Parameter physAddressLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + PHYS_ADDRESS);
		physAddressLeaf.setType(ParameterType.STRING);
		physAddressLeaf.setStorageMode(StorageMode.COMPUTED);
		physAddressLeaf.setWritable(false);
		physAddressLeaf.setNotification(0);
		physAddressLeaf.setActiveNotificationDenied(false);
		physAddressLeaf.addCheck(new CheckLength(64));
		String ieeeaddress = "PhysAddress CAN NOT BE IMPLEMENTED";
		physAddressLeaf.setValue(ieeeaddress);

		Parameter hostNameLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + HOST_NAME);
		hostNameLeaf.setType(ParameterType.STRING);
		hostNameLeaf.setStorageMode(StorageMode.COMPUTED);
		hostNameLeaf.setWritable(false);
		hostNameLeaf.setNotification(0);
		hostNameLeaf.setActiveNotificationDenied(false);
		hostNameLeaf.addCheck(new CheckLength(64));
		String name = "HostName CAN NOT BE IMPLEMENTED";
		hostNameLeaf.setValue(name);

		Parameter activeLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + ACTIVE);
		activeLeaf.setType(ParameterType.BOOLEAN);
		activeLeaf.setStorageMode(StorageMode.COMPUTED);
		activeLeaf.setWritable(false);
		activeLeaf.setNotification(0);
		activeLeaf.setActiveNotificationDenied(false);
		activeLeaf.setValue(Integer.valueOf("1"));

		// The following implements the part of the data model related to:
		// NonMockedDeviceFromBaseDriver.DEVICE_CATEGORY_KEY

		Parameter deviceCategoryLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + DeviceFromBaseDriver.DEVICE_CATEGORY_KEY);
		deviceCategoryLeaf.setType(ParameterType.STRING);
		deviceCategoryLeaf.setStorageMode(StorageMode.COMPUTED);
		deviceCategoryLeaf.setWritable(false);
		deviceCategoryLeaf.setNotification(0);
		deviceCategoryLeaf.setActiveNotificationDenied(false);
		String deviceCategory = null;
		for (int i = 0; i < deviceFromBaseDriver.getDeviceCategory().length; i = i + 1) {
			if (deviceCategory == null) {
				deviceCategory = deviceFromBaseDriver.getDeviceCategory()[i];
			} else {
				deviceCategory = deviceCategory + ";;" + deviceFromBaseDriver.getDeviceCategory()[i];
			}
		}
		deviceCategoryLeaf.setValue(deviceCategory);

		Parameter deviceDescriptionLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS
				+ HOST + deviceNumberBranchIsTheServicePid + "." + DeviceFromBaseDriver.DEVICE_DESCRIPTION_KEY);
		deviceDescriptionLeaf.setType(ParameterType.STRING);
		deviceDescriptionLeaf.setStorageMode(StorageMode.COMPUTED);
		deviceDescriptionLeaf.setWritable(false);
		deviceDescriptionLeaf.setNotification(0);
		deviceDescriptionLeaf.setActiveNotificationDenied(false);
		String deviceDescription = deviceFromBaseDriver.getDeviceDescription();
		deviceDescriptionLeaf.setValue(deviceDescription);

		Parameter deviceSerialLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + DeviceFromBaseDriver.DEVICE_SERIAL_KEY);
		deviceSerialLeaf.setType(ParameterType.STRING);
		deviceSerialLeaf.setStorageMode(StorageMode.COMPUTED);
		deviceSerialLeaf.setWritable(false);
		deviceSerialLeaf.setNotification(0);
		deviceSerialLeaf.setActiveNotificationDenied(false);
		String deviceSerial = deviceFromBaseDriver.getDeviceSerial();
		deviceSerialLeaf.setValue(deviceSerial);

		// SERVICE_PID_KEY is equal to service.pid, but Modus/TR69 does NOT
		// support at dot "." in a leaf's name.
		// service.pid already appears in the branch, it is used as
		// deviceNumberBranch.
		// The following line of code can NOT be displayed by Modus in the data
		// model.
		Parameter servicePidLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + DeviceFromBaseDriver.SERVICE_PID_KEY);
		servicePidLeaf.setType(ParameterType.STRING);
		servicePidLeaf.setStorageMode(StorageMode.COMPUTED);
		servicePidLeaf.setWritable(false);
		servicePidLeaf.setNotification(0);
		servicePidLeaf.setActiveNotificationDenied(false);
		String servicePid = deviceFromBaseDriver.getServicePid();
		servicePidLeaf.setValue(servicePid);

		Parameter deviceFriendlyNameLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS
				+ HOST + deviceNumberBranchIsTheServicePid + "." + DeviceFromBaseDriver.DEVICE_FRIENDLY_NAME_KEY);
		deviceFriendlyNameLeaf.setType(ParameterType.STRING);
		deviceFriendlyNameLeaf.setStorageMode(StorageMode.COMPUTED);
		deviceFriendlyNameLeaf.setWritable(false);
		deviceFriendlyNameLeaf.setNotification(0);
		deviceFriendlyNameLeaf.setActiveNotificationDenied(false);
		String deviceFriendlyName = deviceFromBaseDriver.getDeviceFriendlyName();
		deviceFriendlyNameLeaf.setValue(deviceFriendlyName);
	}

	/**
	 * @param pmDataService
	 * @param deviceFromBaseDriver
	 * @throws Fault
	 */
	public static void removeDeviceFromBaseDriverNumberBranchAndRelatedLeafsDatamodel(
			final IParameterData pmDataService, final DeviceFromBaseDriver deviceFromBaseDriver) throws Fault {
		String deviceNumberBranchIsTheServicePid = deviceFromBaseDriver.getServicePid();

		Parameter hostNumberServicePidBranch = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS
				+ HOST + deviceNumberBranchIsTheServicePid + ".");
		hostNumberServicePidBranch.setType(ParameterType.ANY);

		Parameter ipAddressLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + IP_ADDRESS);

		// Deprecated.
		Parameter addressSourceLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + ADDRESS_SOURCE);

		Parameter leaseTimeRemainingLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS
				+ HOST + deviceNumberBranchIsTheServicePid + "." + LEASE_TIME_REMAINING);

		Parameter physAddressLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + PHYS_ADDRESS);

		Parameter hostNameLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + HOST_NAME);

		Parameter activeLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + ACTIVE);

		// The following implements the part of the data model related to:
		// NonMockedDeviceFromBaseDriver.DEVICE_CATEGORY_KEY

		Parameter deviceCategoryLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + DeviceFromBaseDriver.DEVICE_CATEGORY_KEY);

		Parameter deviceDescriptionLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS
				+ HOST + deviceNumberBranchIsTheServicePid + "." + DeviceFromBaseDriver.DEVICE_DESCRIPTION_KEY);

		Parameter deviceSerialLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + DeviceFromBaseDriver.DEVICE_SERIAL_KEY);

		// SERVICE_PID_KEY is equal to service.pid, but Modus/TR69 does NOT
		// support at dot "." in a leaf's name.
		// service.pid already appears in the branch, it is used as
		// deviceNumberBranch.
		// The following line of code can NOT be displayed by Modus in the data
		// model.
		Parameter servicePidLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS + HOST
				+ deviceNumberBranchIsTheServicePid + "." + DeviceFromBaseDriver.SERVICE_PID_KEY);

		Parameter deviceFriendlyNameLeaf = pmDataService.createOrRetrieveParameter(pmDataService.getRoot() + HOSTS
				+ HOST + deviceNumberBranchIsTheServicePid + "." + DeviceFromBaseDriver.DEVICE_FRIENDLY_NAME_KEY);

		pmDataService.deleteParam(hostNumberServicePidBranch);
		pmDataService.deleteParam(ipAddressLeaf);
		pmDataService.deleteParam(addressSourceLeaf);
		pmDataService.deleteParam(leaseTimeRemainingLeaf);
		pmDataService.deleteParam(physAddressLeaf);
		pmDataService.deleteParam(hostNameLeaf);
		pmDataService.deleteParam(activeLeaf);
		pmDataService.deleteParam(deviceCategoryLeaf);
		pmDataService.deleteParam(deviceDescriptionLeaf);
		pmDataService.deleteParam(deviceSerialLeaf);
		pmDataService.deleteParam(servicePidLeaf);
		pmDataService.deleteParam(deviceFriendlyNameLeaf);
	}

}
