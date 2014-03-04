/**
 * Copyright France Telecom (Orange Labs R&D) 2008,  All Rights Reserved.
 *
 * This software is the confidential and proprietary information
 * of France Telecom (Orange Labs R&D). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * France Telecom (Orange Labs R&D)
 *
 * Project     : Modus
 * Software    : Library
 *
 * Author : Orange Labs R&D O.Beyler
 * Generated : 21 oct. 2009 by GenModel
 */

package com.francetelecom.tr106.gen;

import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.CheckEnum;
import com.francetelecom.admindm.model.CheckLength;
import com.francetelecom.admindm.model.CheckMaximum;
import com.francetelecom.admindm.model.CheckMinimum;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * Class DeviceInfo.
 * 
 * @author OrangeLabs R&D
 */
public class DeviceInfo {

	/**
	 * "SupportedDataModelNumberOfEntries" data model leaf.
	 * 
	 * The number of entries in the SupportedDataModel table..
	 */
	private static final String SUPPORTED_DATA_MODEL_NUMBER_OF_ENTRIES = "SupportedDataModelNumberOfEntries";

	// Hardcoded.
	private static final Integer SUPPORTED_DATA_MODEL_NUMBER_OF_ENTRIES_VALUE = Integer
			.valueOf("1");

	/**
	 * "SupportedDataModel." data model branch.
	 * 
	 * This table contains details of the device's Current Supported Data Model.
	 * 
	 * The table MUST describe the device's entire Supported Data Model.
	 * Therefore, if a device's Supported Data Model changes at run-time,
	 * entries will need to be added or removed as appropriate.
	 * 
	 * Each table entry MUST refer to only a single Root Object or Service
	 * Object. The device MAY choose to use more than one table entry for a
	 * given Root Object or Service Object.
	 * 
	 * Considering that every device has some form of a data model, this table
	 * MUST NOT be empty.
	 * 
	 * At most one entry in this table can exist with a given value for URL, or
	 * with a given value for Alias, or with a given value for UUID.
	 * */
	private static final String SUPPORTED_DATA_MODEL = "SupportedDataModel.";

	// Hardcoded.
	private static final String SUPPORTED_DATA_MODEL_NUMBER_ZERO = "0.";

	/**
	 * "URL" data model leaf.
	 * 
	 * URL ([RFC3986]) that describes some or all of the device's Current
	 * Supported Data Model.
	 * 
	 * The URL MUST reference an XML file which describes the appropriate part
	 * of the Supported Data Model.
	 * 
	 * The referenced XML file MUST be compliant with the DT (Device Type)
	 * Schema that is described in [Annex B/TR-106a3], including any additional
	 * normative requirements referenced within the Schema.
	 * 
	 * The XML file referenced by this URL MUST NOT change while the CPE is
	 * running, and SHOULD NOT change across a CPE reboot. Note that UUID is a
	 * unique key, so the XML file referenced by this URL will never change.
	 * 
	 * The XML file MAY be located within the CPE. In this scenario the CPE MAY
	 * use the value of "localhost" as URL host portion, When the "localhost"
	 * value is used, the ACS has the responsibility to substitute the
	 * "localhost" value with the host portion of the connection request URL.
	 * 
	 * Behavior in the event of an invalid URL, failure to access the referenced
	 * XML file, or an invalid XML file, is implementation-dependent.
	 * */
	private static final String URL = "URL";

	/**
	 * "URN" data model leaf.
	 * 
	 * URN ([RFC3986]) that is the value of the spec attribute in the DM (data
	 * model) Instance that defines the Root Object or Service Object referenced
	 * by this table entry.
	 * 
	 * For example, if this table entry references a DT Instance that refers to
	 * the Device:1.3 Root Object, the value of this parameter would be
	 * urn:broadband-forum-org:tr-157-1-0-0, because TR-157 defines Device:1.3.
	 * If the DT Instance instead referred to a vendor-specific Root Object,
	 * e.g. X_EXAMPLE_Device:1.0 (derived from Device:1.3), the value of this
	 * parameter would be something like urn:example-com:device-1-0-0.
	 */
	private static final String URN = "URN";

	/**
	 * "Features" data model leaf.
	 * 
	 * Comma-separated list of strings. This parameter MUST list exactly the
	 * features that are defined using the top-level feature element in the DT
	 * Instance referenced by URL.
	 * 
	 * For example, if the DT instance specified the following:
	 * 
	 * <feature name="DNSServer"/> <feature name="Router"/> <feature
	 * name="X_MyDeviceFeature"/>
	 * 
	 * then the value of this parameter might be
	 * DNSServer,Router,X_MyDeviceFeature. The order in which the features are
	 * listed is not significant.
	 */
	private static final String FEATURES = "Features";

	/** The data. */
	private final IParameterData data;
	/** The base path. */
	private final String basePath;

	/**
	 * Default constructor.
	 * 
	 * @param pData
	 *            data model
	 * @param pBasePath
	 *            base path of attribute
	 * @param pPersist
	 *            persistence
	 */
	public DeviceInfo(final IParameterData pData, final String pBasePath) {
		super();
		this.data = pData;
		this.basePath = pBasePath;
	}

	/**
	 * Get the data.
	 * 
	 * @return the data
	 */
	public final IParameterData getData() {
		return this.data;
	}

	/**
	 * Get the basePath.
	 * 
	 * @return the basePath
	 */
	public final String getBasePath() {
		return this.basePath;
	}

	/**
	 * Initialiser.
	 */
	public void initialize() throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath);
		param.setType(ParameterType.ANY);

		this.paramDescription = createDescription();
		this.paramManufacturerOUI = createManufacturerOUI();
		this.paramProvisioningCode = createProvisioningCode();
		this.paramHardwareVersion = createHardwareVersion();
		this.paramUpTime = createUpTime();
		this.paramEnabledOptions = createEnabledOptions();
		this.paramSerialNumber = createSerialNumber();
		this.paramAdditionalHardwareVersion = createAdditionalHardwareVersion();
		this.paramSoftwareVersion = createSoftwareVersion();
		this.paramManufacturer = createManufacturer();
		this.paramDeviceLog = createDeviceLog();
		this.paramModelName = createModelName();
		this.paramFirstUseDate = createFirstUseDate();
		this.paramProductClass = createProductClass();
		this.paramAdditionalSoftwareVersion = createAdditionalSoftwareVersion();
		this.paramDeviceStatus = createDeviceStatus();

		// SupportedDataModel:1 Profile.

		// Here, this.basePath is "Device.DeviceInfo.".

		Parameter supportedDataModelNumberOfEntriesLeaf = this.data
				.createOrRetrieveParameter(this.basePath
						+ SUPPORTED_DATA_MODEL_NUMBER_OF_ENTRIES);
		supportedDataModelNumberOfEntriesLeaf.setType(ParameterType.UINT);
		supportedDataModelNumberOfEntriesLeaf
				.setStorageMode(StorageMode.COMPUTED);
		supportedDataModelNumberOfEntriesLeaf.setWritable(false);
		supportedDataModelNumberOfEntriesLeaf.setNotification(0);
		supportedDataModelNumberOfEntriesLeaf
				.setActiveNotificationDenied(false);
		supportedDataModelNumberOfEntriesLeaf
				.setValue(SUPPORTED_DATA_MODEL_NUMBER_OF_ENTRIES_VALUE);

		Parameter supportedDataModelBranch = this.data
				.createOrRetrieveParameter(this.basePath + SUPPORTED_DATA_MODEL);
		supportedDataModelBranch.setType(ParameterType.ANY);

		Parameter supportedDataModelNumberZeroBranch = this.data
				.createOrRetrieveParameter(this.basePath + SUPPORTED_DATA_MODEL
						+ SUPPORTED_DATA_MODEL_NUMBER_ZERO);
		supportedDataModelNumberZeroBranch.setType(ParameterType.ANY);

		Parameter urlLeaf = this.data
				.createOrRetrieveParameter(this.basePath + SUPPORTED_DATA_MODEL
						+ SUPPORTED_DATA_MODEL_NUMBER_ZERO + URL);
		urlLeaf.setType(ParameterType.STRING);
		urlLeaf.setStorageMode(StorageMode.COMPUTED);
		urlLeaf.setWritable(false);
		urlLeaf.setNotification(0);
		urlLeaf.setActiveNotificationDenied(false);
		urlLeaf.addCheck(new CheckLength(256));
		urlLeaf.setValue("http://www.broadband-forum.org/cwmp/tr-181-2-6-0.xml");

		Parameter urnLeaf = this.data
				.createOrRetrieveParameter(this.basePath + SUPPORTED_DATA_MODEL
						+ SUPPORTED_DATA_MODEL_NUMBER_ZERO + URN);
		urnLeaf.setType(ParameterType.STRING);
		urnLeaf.setStorageMode(StorageMode.COMPUTED);
		urnLeaf.setWritable(false);
		urnLeaf.setNotification(0);
		urnLeaf.setActiveNotificationDenied(false);
		urnLeaf.addCheck(new CheckLength(256));
		urnLeaf.setValue("urn:broadband-forum-org:tr-181-2-6-0");

		Parameter featuresLeaf = this.data
				.createOrRetrieveParameter(this.basePath + SUPPORTED_DATA_MODEL
						+ SUPPORTED_DATA_MODEL_NUMBER_ZERO + FEATURES);
		featuresLeaf.setType(ParameterType.STRING);
		featuresLeaf.setStorageMode(StorageMode.COMPUTED);
		featuresLeaf.setWritable(false);
		featuresLeaf.setNotification(0);
		featuresLeaf.setActiveNotificationDenied(false);
		// XXX AAA: Implement Features.
		featuresLeaf.setValue("TODO AAA: Implement Features.");

	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramDescription;

	/**
	 * Getter method of Description.
	 * 
	 * @return _Description
	 */
	public final com.francetelecom.admindm.model.Parameter getParamDescription() {
		return this.paramDescription;
	}

	/**
	 * Create the parameter Description
	 * 
	 * @return Description
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createDescription()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "Description");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(256));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
	 * nullThis value MUST remain fixed over the lifetime of the device, across
	 * firmware updates.
	 */
	private com.francetelecom.admindm.model.Parameter paramManufacturerOUI;

	/**
	 * Getter method of ManufacturerOUI.
	 * 
	 * @return _ManufacturerOUI
	 */
	public final com.francetelecom.admindm.model.Parameter getParamManufacturerOUI() {
		return this.paramManufacturerOUI;
	}

	/**
	 * Create the parameter ManufacturerOUI
	 * 
	 * @return ManufacturerOUI
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createManufacturerOUI()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "ManufacturerOUI");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(6));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramProvisioningCode;

	/**
	 * Getter method of ProvisioningCode.
	 * 
	 * @return _ProvisioningCode
	 */
	public final com.francetelecom.admindm.model.Parameter getParamProvisioningCode() {
		return this.paramProvisioningCode;
	}

	/**
	 * Create the parameter ProvisioningCode
	 * 
	 * @return ProvisioningCode
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createProvisioningCode()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "ProvisioningCode");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(64));
		param.setValue("");
		param.setWritable(true);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramHardwareVersion;

	/**
	 * Getter method of HardwareVersion.
	 * 
	 * @return _HardwareVersion
	 */
	public final com.francetelecom.admindm.model.Parameter getParamHardwareVersion() {
		return this.paramHardwareVersion;
	}

	/**
	 * Create the parameter HardwareVersion
	 * 
	 * @return HardwareVersion
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createHardwareVersion()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "HardwareVersion");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setMandatoryNotification(true);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(64));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramUpTime;

	/**
	 * Getter method of UpTime.
	 * 
	 * @return _UpTime
	 */
	public final com.francetelecom.admindm.model.Parameter getParamUpTime() {
		return this.paramUpTime;
	}

	/**
	 * Create the parameter UpTime
	 * 
	 * @return UpTime
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createUpTime()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath + "UpTime");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.UINT);
		param.addCheck(new CheckMinimum(0));
		param.addCheck(new CheckMaximum(4294967295L));
		param.setValue(new Long(0));
		param.setWritable(false);
		return param;
	}

	/**
	 * Comma-separated list of the OptionName of each Option that is enabled in
	 * the CPE. The OptionName of each is identical to the element of the
	 * OptionStruct described in {{bibref|TR-069a2}}. Only options are listed
	 * whose State indicates the option is enabled.
	 */
	private com.francetelecom.admindm.model.Parameter paramEnabledOptions;

	/**
	 * Getter method of EnabledOptions.
	 * 
	 * @return _EnabledOptions
	 */
	public final com.francetelecom.admindm.model.Parameter getParamEnabledOptions() {
		return this.paramEnabledOptions;
	}

	/**
	 * Create the parameter EnabledOptions
	 * 
	 * @return EnabledOptions
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createEnabledOptions()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "EnabledOptions");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(1024));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
	 * nullThis value MUST remain fixed over the lifetime of the device, across
	 * firmware updates.
	 */
	private com.francetelecom.admindm.model.Parameter paramSerialNumber;

	/**
	 * Getter method of SerialNumber.
	 * 
	 * @return _SerialNumber
	 */
	public final com.francetelecom.admindm.model.Parameter getParamSerialNumber() {
		return this.paramSerialNumber;
	}

	/**
	 * Create the parameter SerialNumber
	 * 
	 * @return SerialNumber
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createSerialNumber()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "SerialNumber");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(64));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
	 * A comma-separated list of any additional versions. Represents any
	 * hardware version information the vendor might wish to supply.
	 */
	private com.francetelecom.admindm.model.Parameter paramAdditionalHardwareVersion;

	/**
	 * Getter method of AdditionalHardwareVersion.
	 * 
	 * @return _AdditionalHardwareVersion
	 */
	public final com.francetelecom.admindm.model.Parameter getParamAdditionalHardwareVersion() {
		return this.paramAdditionalHardwareVersion;
	}

	/**
	 * Create the parameter AdditionalHardwareVersion
	 * 
	 * @return AdditionalHardwareVersion
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createAdditionalHardwareVersion()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "AdditionalHardwareVersion");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(64));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramSoftwareVersion;

	/**
	 * Getter method of SoftwareVersion.
	 * 
	 * @return _SoftwareVersion
	 */
	public final com.francetelecom.admindm.model.Parameter getParamSoftwareVersion() {
		return this.paramSoftwareVersion;
	}

	/**
	 * Create the parameter SoftwareVersion
	 * 
	 * @return SoftwareVersion
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createSoftwareVersion()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "SoftwareVersion");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setMandatoryNotification(true);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(64));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramManufacturer;

	/**
	 * Getter method of Manufacturer.
	 * 
	 * @return _Manufacturer
	 */
	public final com.francetelecom.admindm.model.Parameter getParamManufacturer() {
		return this.paramManufacturer;
	}

	/**
	 * Create the parameter Manufacturer
	 * 
	 * @return Manufacturer
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createManufacturer()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "Manufacturer");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(64));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramDeviceLog;

	/**
	 * Getter method of DeviceLog.
	 * 
	 * @return _DeviceLog
	 */
	public final com.francetelecom.admindm.model.Parameter getParamDeviceLog() {
		return this.paramDeviceLog;
	}

	/**
	 * Create the parameter DeviceLog
	 * 
	 * @return DeviceLog
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createDeviceLog()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data
				.createOrRetrieveParameter(this.basePath + "DeviceLog");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(32768));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramModelName;

	/**
	 * Getter method of ModelName.
	 * 
	 * @return _ModelName
	 */
	public final com.francetelecom.admindm.model.Parameter getParamModelName() {
		return this.paramModelName;
	}

	/**
	 * Create the parameter ModelName
	 * 
	 * @return ModelName
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createModelName()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data
				.createOrRetrieveParameter(this.basePath + "ModelName");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(64));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
	 * nullIf NTP or equivalent is not available, this parameter, if SHOULD be
	 * set to the Unknown Time value.
	 */
	private com.francetelecom.admindm.model.Parameter paramFirstUseDate;

	/**
	 * Getter method of FirstUseDate.
	 * 
	 * @return _FirstUseDate
	 */
	public final com.francetelecom.admindm.model.Parameter getParamFirstUseDate() {
		return this.paramFirstUseDate;
	}

	/**
	 * Create the parameter FirstUseDate
	 * 
	 * @return FirstUseDate
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createFirstUseDate()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "FirstUseDate");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setActiveNotificationDenied(true);
		param.setType(ParameterType.DATE);
		param.setValue(new Long(0));
		param.setWritable(false);
		return param;
	}

	/**
	 * nullThis value MUST remain fixed over the lifetime of the device, across
	 * firmware updates.
	 */
	private com.francetelecom.admindm.model.Parameter paramProductClass;

	/**
	 * Getter method of ProductClass.
	 * 
	 * @return _ProductClass
	 */
	public final com.francetelecom.admindm.model.Parameter getParamProductClass() {
		return this.paramProductClass;
	}

	/**
	 * Create the parameter ProductClass
	 * 
	 * @return ProductClass
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createProductClass()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "ProductClass");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(64));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
	 * A comma-separated list of any additional versions. Represents any
	 * software version information the vendor might wish to supply.
	 */
	private com.francetelecom.admindm.model.Parameter paramAdditionalSoftwareVersion;

	/**
	 * Getter method of AdditionalSoftwareVersion.
	 * 
	 * @return _AdditionalSoftwareVersion
	 */
	public final com.francetelecom.admindm.model.Parameter getParamAdditionalSoftwareVersion() {
		return this.paramAdditionalSoftwareVersion;
	}

	/**
	 * Create the parameter AdditionalSoftwareVersion
	 * 
	 * @return AdditionalSoftwareVersion
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createAdditionalSoftwareVersion()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "AdditionalSoftwareVersion");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.addCheck(new CheckLength(64));
		param.setValue("");
		param.setWritable(false);
		return param;
	}

	/**
    *  
    */
	private com.francetelecom.admindm.model.Parameter paramDeviceStatus;

	/**
	 * Getter method of DeviceStatus.
	 * 
	 * @return _DeviceStatus
	 */
	public final com.francetelecom.admindm.model.Parameter getParamDeviceStatus() {
		return this.paramDeviceStatus;
	}

	/**
	 * Create the parameter DeviceStatus
	 * 
	 * @return DeviceStatus
	 * @throws Fault
	 *             exception
	 */
	public final com.francetelecom.admindm.model.Parameter createDeviceStatus()
			throws Fault {
		com.francetelecom.admindm.model.Parameter param;
		param = this.data.createOrRetrieveParameter(this.basePath
				+ "DeviceStatus");
		param.setNotification(0);
		param.setStorageMode(StorageMode.DM_ONLY);
		param.setType(ParameterType.STRING);
		param.setValue("");
		String[] values = { "Up", "Initializing", "Error", "Disabled", };
		param.addCheck(new CheckEnum(values));
		param.setWritable(false);
		return param;
	}

}