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

package com.francetelecom.admindm.changedustate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.kxml2.kdom.Element;

import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.model.OperationStruct;
import com.francetelecom.admindm.model.OperationStructException;
import com.francetelecom.admindm.soap.Fault;

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class ChangeDUStateDecoder implements RPCDecoder {
	/**
	 * @see com.francetelecom.admindm.api.RPCDecoder#decode(org.kxml2.kdom.Element)
	 */
	public final RPCMethod decode(final Element element) throws Fault {
		ChangeDUState result = new ChangeDUState();

		// There are, at least, 2 childs: 1 commandKey, and 1..* operation(s)
		if (element.getChildCount() <= 1) {
			Fault fault = new Fault(-1, "TODO: 30 janv. 2013 17:07:39");
			Log.error(fault.getMessage(), fault);
			throw fault;
		}

		// List<OperationStruct>
		List operationsStructList = new ArrayList();
		for (int index = 0; index < element.getChildCount(); index = index + 1) {
			// Extract operations, there is at least one operation.
			// XMLUtil.extractValue can not be used here.
			// String operations = XMLUtil.extractValue(element, "Operations");
			// Element operationsAsAnElement = ((Element) element.getChild(0))
			// Element operationsAsAnElement = element.getElement("",
			// "Operations");
			// Log.info("operationsAsAnElement: " + operationsAsAnElement);

			Element operationsOrCommandKeyAsAnElement = element.getElement(index);
			Log.info("operationsOrCommandKeyAsAnElement: " + operationsOrCommandKeyAsAnElement);

			if ("Operations".equals(operationsOrCommandKeyAsAnElement.getName())) {
				Element operationsAsAnElement = operationsOrCommandKeyAsAnElement;
				// Extract the data contained in this operation.
				if (operationsAsAnElement.getAttributeCount() != 1) {
					Fault fault = new Fault(-1, "TODO: 30 janv. 2013 14:15:20");
					Log.error(fault.getMessage(), fault);
					throw fault;
				}
				if (!"xsi:type".equals(operationsAsAnElement.getAttributeName(0))) {
					Fault fault = new Fault(-1, "TODO: 30 janv. 2013 14:16:45");
					Log.error(fault.getMessage(), fault);
					throw fault;
				}

				// cwmp:InstallOpStruct, or ...
				String opStructOfTheOperation = operationsAsAnElement.getAttributeValue(0);
				Log.info("opStructOfTheOperation: " + opStructOfTheOperation);

				if ("cwmp:InstallOpStruct".equals(opStructOfTheOperation)) {
					// Handle InstallOpStruct
					Element urlAsAnElement = operationsAsAnElement.getElement("", "URL");
					Log.info("urlAsAnElement: " + urlAsAnElement);
					Log.info("urlAsAnElement.getChild(0): " + urlAsAnElement.getChild(0));
					URL url = null;
					try {
						url = new URL((String) urlAsAnElement.getChild(0));
					} catch (MalformedURLException e) {
						e.printStackTrace();
						Fault fault = new Fault(-1, "TODO: 30 janv. 2013 17:44:11");
						Log.error(fault.getMessage(), fault);
						throw fault;
					}

					Element uuidAsAnElement = operationsAsAnElement.getElement("", "UUID");
					Log.info("uuidAsAnElement: " + uuidAsAnElement);
					Log.info("uuidAsAnElement.getChild(0): " + uuidAsAnElement.getChild(0));
					String uuid = (String) uuidAsAnElement.getChild(0);

					Element usernameAsAnElement = operationsAsAnElement.getElement("", "Username");
					Log.info("usernameAsAnElement: " + usernameAsAnElement);
					Log.info("usernameAsAnElement.getChild(0): " + usernameAsAnElement.getChild(0));
					String username = (String) usernameAsAnElement.getChild(0);

					Element passwordAsAnElement = operationsAsAnElement.getElement("", "Password");
					Log.info("passwordAsAnElement: " + passwordAsAnElement);
					Log.info("passwordAsAnElement.getChild(0): " + passwordAsAnElement.getChild(0));
					String password = (String) passwordAsAnElement.getChild(0);

					Element executionEnvRefAsAnElement = operationsAsAnElement.getElement("", "ExecutionEnvRef");
					Log.info("executionEnvRefAsAnElement: " + executionEnvRefAsAnElement);
					Log.info("executionEnvRefAsAnElement.getChild(0): " + executionEnvRefAsAnElement.getChild(0));
					String executionEnvRef = (String) executionEnvRefAsAnElement.getChild(0);

					try {
						OperationStruct operationStruct = new OperationStruct(url, uuid, username, password,
								executionEnvRef);
						operationsStructList.add(operationStruct);
					} catch (OperationStructException e) {
						e.printStackTrace();
						Fault fault = new Fault(-1, e.getMessage(), e);
						Log.error(fault.getMessage(), fault);
						throw fault;
					}
				} else if ("cwmp:UpdateOpStruct".equals(opStructOfTheOperation)) {
					// Handle UpdateOpStruct
					Element uuidAsAnElement = operationsAsAnElement.getElement("", "UUID");
					Log.info("uuidAsAnElement: " + uuidAsAnElement);
					Log.info("uuidAsAnElement.getChild(0): " + uuidAsAnElement.getChild(0));
					String uuid = (String) uuidAsAnElement.getChild(0);

					Element versionAsAnElement = operationsAsAnElement.getElement("", "Version");
					Log.info("versionAsAnElement: " + versionAsAnElement);
					Log.info("versionAsAnElement.getChild(0): " + versionAsAnElement.getChild(0));
					String version = (String) versionAsAnElement.getChild(0);

					Element urlAsAnElement = operationsAsAnElement.getElement("", "URL");
					Log.info("urlAsAnElement: " + urlAsAnElement);
					Log.info("urlAsAnElement.getChild(0): " + urlAsAnElement.getChild(0));
					URL url = null;
					try {
						url = new URL((String) urlAsAnElement.getChild(0));
					} catch (MalformedURLException e) {
						e.printStackTrace();
						Fault fault = new Fault(-1, "TODO: 31 janv. 2013 16:05:50");
						Log.error(fault.getMessage(), fault);
						throw fault;
					}

					Element usernameAsAnElement = operationsAsAnElement.getElement("", "Username");
					Log.info("usernameAsAnElement: " + usernameAsAnElement);
					Log.info("usernameAsAnElement.getChild(0): " + usernameAsAnElement.getChild(0));
					String username = (String) usernameAsAnElement.getChild(0);

					Element passwordAsAnElement = operationsAsAnElement.getElement("", "Password");
					Log.info("passwordAsAnElement: " + passwordAsAnElement);
					Log.info("passwordAsAnElement.getChild(0): " + passwordAsAnElement.getChild(0));
					String password = (String) passwordAsAnElement.getChild(0);
					try {
						OperationStruct operationStruct = new OperationStruct(uuid, version, url, username, password);
						operationsStructList.add(operationStruct);
					} catch (OperationStructException e) {
						e.printStackTrace();
						Fault fault = new Fault(-1, e.getMessage(), e);
						Log.error(fault.getMessage(), fault);
						throw fault;
					}
				} else if ("cwmp:UninstallOpStruct".equals(opStructOfTheOperation)) {
					// Handle UninstallOpStruct
					Element uuidAsAnElement = operationsAsAnElement.getElement("", "UUID");
					Log.info("uuidAsAnElement: " + uuidAsAnElement);
					Log.info("uuidAsAnElement.getChild(0): " + uuidAsAnElement.getChild(0));
					String uuid = (String) uuidAsAnElement.getChild(0);

					Element versionAsAnElement = operationsAsAnElement.getElement("", "Version");
					Log.info("versionAsAnElement: " + versionAsAnElement);
					Log.info("versionAsAnElement.getChild(0): " + versionAsAnElement.getChild(0));
					String version = (String) versionAsAnElement.getChild(0);

					Element executionEnvRefAsAnElement = operationsAsAnElement.getElement("", "ExecutionEnvRef");
					Log.info("executionEnvRefAsAnElement: " + executionEnvRefAsAnElement);
					Log.info("executionEnvRefAsAnElement.getChild(0): " + executionEnvRefAsAnElement.getChild(0));
					String executionEnvRef = (String) executionEnvRefAsAnElement.getChild(0);
					try {
						OperationStruct operationStruct = new OperationStruct(uuid, version, executionEnvRef);
						operationsStructList.add(operationStruct);
					} catch (OperationStructException e) {
						e.printStackTrace();
						Fault fault = new Fault(-1, e.getMessage(), e);
						Log.error(fault.getMessage(), fault);
						throw fault;
					}
				} else {
					// Only InstallOpStruct, UpdateOpStruct, and
					// DeinstallOpStruct are handled.
					Fault fault = new Fault(-1, "TODO: 31 janv. 2013 16:00:12");
					Log.error(fault.getMessage(), fault);
					throw fault;
				}
			} else if ("CommandKey".equals(operationsOrCommandKeyAsAnElement.getName())) {
				Element commandKeyAsAnElement = operationsOrCommandKeyAsAnElement;
				// String commandKey = XMLUtil.extractValue(element,
				// "CommandKey");
				String commandKey = (String) commandKeyAsAnElement.getChild(0);
				result.setCommandKey(commandKey);
			} else {
				// Something else than operations or commandKey is rejected.
				Fault fault = new Fault(-1, "TODO: 30 janv. 2013 17:21:53");
				Log.error(fault.getMessage(), fault);
				throw fault;
			}

		}

		OperationStruct operationsStruct[] = new OperationStruct[operationsStructList.size()];
		for (int i = 0; i < operationsStructList.size(); i = i + 1) {
			operationsStruct[i] = (OperationStruct) operationsStructList.get(i);
		}

		result.setOperations(operationsStruct);

		Log.debug("result: " + result);

		OpStructExecutionManager.getSingletonInstance().addChangeDUStateToBeExecuted(result);

		return result;
	}
}
