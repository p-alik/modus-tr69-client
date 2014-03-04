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
 * Author: Olivier Beyler - Orange
 */

package com.francetelecom.admindm.getRPCMethods;

import java.util.ArrayList;
import java.util.List;

import com.francetelecom.admindm.api.EventBehavior;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.RPCMethodMngService;

/**
 * The Class MockRPCMethodsManagement.
 */
public class MockRPCMethodsManagement implements RPCMethodMngService {

	/**
	 * Gets the rpc method.
	 * 
	 * @return the RPC method
	 */
	public List getRPCMethod() {
		List lsMethods = new ArrayList();
		lsMethods.add("test1");
		lsMethods.add("test2");
		return lsMethods;
	}

	/**
	 * Register event behavior.
	 * 
	 * @param name
	 *            the name
	 * @param eventBehavior
	 *            the event behavior
	 */
	public void registerEventBehavior(String name, EventBehavior eventBehavior) {
	}

	/**
	 * Register rpc decoder.
	 * 
	 * @param name
	 *            the name
	 * @param decoder
	 *            the decoder
	 */
	public void registerRPCDecoder(String name, RPCDecoder decoder) {
	}

	/**
	 * Register rpc encoder.
	 * 
	 * @param name
	 *            the name
	 * @param encoder
	 *            the encoder
	 */
	public void registerRPCEncoder(String name, RPCEncoder encoder) {
	}

	/**
	 * Register rpc method.
	 * 
	 * @param name
	 *            the name
	 */
	public void registerRPCMethod(String name) {
	}

	/**
	 * Unregister event behavior.
	 * 
	 * @param name
	 *            the name
	 */
	public void unregisterEventBehavior(String name) {
	}

	/**
	 * Unregister rpc decoder.
	 * 
	 * @param name
	 *            the name
	 */
	public void unregisterRPCDecoder(String name) {
	}

	/**
	 * Unregister rpc encoder.
	 * 
	 * @param name
	 *            the name
	 */
	public void unregisterRPCEncoder(String name) {
	}

	/**
	 * Unregister rpc method.
	 * 
	 * @param name
	 *            the name
	 */
	public void unregisterRPCMethod(String name) {
	}

	/**
	 * Find rpc method decoder.
	 * 
	 * @param value
	 *            the value
	 * @return the RPC decoder
	 */
	public RPCDecoder findRPCMethodDecoder(String value) {
		return null;
	}

	/**
	 * Find rpc method encoder.
	 * 
	 * @param method
	 *            the method
	 * @return the RPC encoder
	 */
	public RPCEncoder findRPCMethodEncoder(RPCMethod method) {
		return null;
	}

	/**
	 * Find rpc method encoder.
	 * 
	 * @param name
	 *            the name
	 * @return the RPC encoder
	 */
	public RPCEncoder findRPCMethodEncoder(String name) {
		return null;
	}
}
