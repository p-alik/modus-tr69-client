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
 */

package com.francetelecom.admindm.model;

import java.net.URL;

/**
 * @SoftwareName:
 * @Version: 1.1.0-SNAPSHOT
 * 
 * @Copyright: c 2013 France Telecom
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class OperationStruct {

	public static final String INSTALL = "INSTALL";
	/**
	 * In the BBF/TR world, update soft(foo, V1) by soft(foo, V2) means replace
	 * soft(foo, V1) by soft(foo, V2), i.e. before the update, there is
	 * soft(foo, V1) in the EE, and after the update there is only soft(foo,
	 * V2), soft(foo, V1) has been removed.
	 */
	public static final String UPDATE = "UPDATE";
	public static final String UNINSTALL = "UNINSTALL";

	String operationStructType = null;

	URL url = null;
	/**
	 * That's not the bundle id, this uuid can be defined by the ACS, or by the
	 * CPE.
	 */
	String uuid = null;
	String username = null;
	String password = null;
	String executionEnvRef = null;
	String version = null;

	/**
	 * InstallOpStruct
	 * 
	 * @param url
	 * @param uuid
	 *            , that's not the bundle id, this uuid can be defined by the
	 *            ACS, or by the CPE. Assume that uuid is defined by the ACS:
	 *            uuid can NOT be null, or "".
	 * @param username
	 * @param password
	 * @param executionEnvRef
	 * @throws OperationStructException
	 *             if uuid is null, or "".
	 */
	public OperationStruct(final URL url, final String uuid, final String username, final String password,
			final String executionEnvRef) throws OperationStructException {
		this.operationStructType = OperationStruct.INSTALL;
		this.url = url;
		if (uuid == null || uuid.equals("")) {
			throw new OperationStructException("The given uuid can NOT be null, or \"\".", null);
		}
		this.uuid = uuid;
		this.username = username;
		this.password = password;
		this.executionEnvRef = executionEnvRef;

	}

	/**
	 * UpdateOpStruct
	 * 
	 * @param uuid
	 *            , that's not the bundle, this uuid can be defined bu the ACS,
	 *            or by the CPE. Assume that uuid is defined by the ACS: uuid
	 *            can NOT be null, or "".
	 * @param version
	 * @param url
	 * @param username
	 * @param password
	 * @throws OperationStructException
	 *             if uuid is null, or "".
	 */
	public OperationStruct(final String uuid, final String version, final URL url, final String username,
			final String password) throws OperationStructException {
		this.operationStructType = OperationStruct.UPDATE;
		this.uuid = uuid;
		this.version = version;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * UninstallOpStruct
	 * 
	 * @param uuid
	 *            , that's not the bundle, this uuid can be defined bu the ACS,
	 *            or by the CPE. Assume that uuid is defined by the ACS: uuid
	 *            can NOT be null, or "".
	 * @param version
	 * @param executionEnvRef
	 * @throws OperationStructException
	 *             if uuid is null, or "".
	 */
	public OperationStruct(final String uuid, final String version, final String executionEnvRef)
			throws OperationStructException {
		this.operationStructType = OperationStruct.UNINSTALL;
		this.uuid = uuid;
		this.version = version;
		this.executionEnvRef = executionEnvRef;
	}

	/**
	 * @return
	 */
	public String getOperationStructType() {
		return this.operationStructType;
	}

	/**
	 * @return
	 */
	public URL getUrl() {
		return this.url;
	}

	/**
	 * @return the DU's UUID, that's not the bundle, this uuid can be defined bu
	 *         the ACS, or by the CPE.
	 */
	public String getUuid() {
		return this.uuid;
	}

	/**
	 * @return
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @return
	 */
	public String getExecutionEnvRef() {
		return this.executionEnvRef;
	}

	/**
	 * @return
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String toString = this.getClass().getName() + "[" + "operationStructType=" + this.operationStructType + ",url="
				+ this.url + ",uuid=" + this.uuid + ",username=" + this.username + ",password=" + this.password
				+ ",executionEnvRef=" + this.executionEnvRef + ",version=" + this.version + "]";
		return toString;
	}

}
