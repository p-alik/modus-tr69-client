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

import java.util.Iterator;

import org.kxml2.kdom.Element;

import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.model.OpResultStruct;
import com.francetelecom.admindm.model.OpResultStructException;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.Soap;

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public final class DUStateChangeCompleteEncoder implements RPCEncoder {
	/**
	 * encode the RPCMethod.
	 * 
	 * @param method
	 *            the method
	 * @see RPCEncoder#encode(RPCMethod)
	 * @return Element
	 * @throws Fault
	 */
	public Element encode(final RPCMethod method) throws Fault {
		Element eDUStateChangeComplete = null;
		if (method instanceof DUStateChangeComplete) {
			DUStateChangeComplete duStateChangeComplete = (DUStateChangeComplete) method;
			eDUStateChangeComplete = new Element();
			eDUStateChangeComplete.setName(DUStateChangeComplete.NAME);
			eDUStateChangeComplete.setNamespace(Soap.getCWMPNameSpace());

			// // DUStateChangeComplete
			// public static final String NAME = "DUStateChangeComplete";
			// private List results = null;
			// private String commandKey = null;
			// private long currentTime = System.currentTimeMillis();
			// private String id = null;

			Element eParameterList = eDUStateChangeComplete.createElement("", "Results");
			eDUStateChangeComplete.addChild(Element.ELEMENT, eParameterList);
			int size = duStateChangeComplete.getResults().size();
			eParameterList.setAttribute(Soap.getSoapEncNameSpace(), "arrayType", "cwmp:OpResultStruct[" + size + "]");
			Iterator it = duStateChangeComplete.getResults().iterator();
			while (it.hasNext()) {
				OpResultStruct opResultStruct = (OpResultStruct) it.next();
				try {
					eParameterList.addChild(Element.ELEMENT, opResultStruct.encoded());
				} catch (OpResultStructException e) {
					throw new Fault(-1, e.getMessage(), e);
				}
			}

			Element eCommandKey = eDUStateChangeComplete.createElement("", "CommandKey");
			eDUStateChangeComplete.addChild(Element.ELEMENT, eCommandKey);
			eCommandKey.addChild(Element.TEXT, duStateChangeComplete.getCommandKey());
			return eDUStateChangeComplete;
		} else {
			throw new Fault(-1, "The given method: " + method + " is NOT instanceof DUStateChangeComplete.");
		}
	}
}
