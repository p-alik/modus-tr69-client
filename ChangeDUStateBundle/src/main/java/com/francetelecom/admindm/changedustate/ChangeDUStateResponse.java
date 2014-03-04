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

import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class ChangeDUStateResponse implements RPCMethod {

	/** The Constant NAME. */
	public static final String NAME = "ChangeDUStateResponse";

	/**
	 * id of the RPCMethod Request by ACS.
	 */
	private String id = null;

	/**
	 * Instantiates a ChangeDUStateResponse.
	 */
	public ChangeDUStateResponse() {
		super();
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 * @see com.francetelecom.admindm.api.RPCMethod#getName()
	 */
	public String getName() {
		return ChangeDUStateResponse.NAME;
	}

	/**
	 * @see com.francetelecom.admindm.api.RPCMethod#perform(com.francetelecom.admindm.api.Session)
	 */
	public void perform(final Session session) {
		// NOTHING TO DO
	}

	/**
	 * Gets the id.
	 * 
	 * @return the Id.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Setter the Id.
	 */
	public void setId(final String id) {
		this.id = id;
	}

}
