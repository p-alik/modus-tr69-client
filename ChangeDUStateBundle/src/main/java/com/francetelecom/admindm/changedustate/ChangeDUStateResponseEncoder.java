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

import org.kxml2.kdom.Element;

import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
import com.francetelecom.admindm.soap.Soap;

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class ChangeDUStateResponseEncoder implements RPCEncoder {

	/**
	 * Cf. Table 66 – ChangeDUStateResponse Arguments, TR-069_Amendment-4.pdf.
	 * 
	 * @see com.francetelecom.admindm.api.RPCEncoder#encode(com.francetelecom.admindm.api.RPCMethod)
	 */
	public final Element encode(final RPCMethod method) throws Fault {
		if (method == null) {
			throw new Fault(FaultUtil.FAULT_9002, "encode null pointer");
		}
		ChangeDUStateResponse changeDUStateResponse = (ChangeDUStateResponse) method;
		Element result = new Element();
		result.setName(changeDUStateResponse.getName());
		result.setNamespace(Soap.getCWMPNameSpace());
		return result;
	}

}
