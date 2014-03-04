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

/**
 * @SoftwareName:
 * @Version: 1.1.0-SNAPSHOT
 * 
 * @Copyright: c 2013 France Telecom
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class OperationStructException extends Exception {

	/** Generated */
	private static final long serialVersionUID = -1411877319510114495L;

	/**
	 * @param message
	 * @param cause
	 */
	public OperationStructException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
