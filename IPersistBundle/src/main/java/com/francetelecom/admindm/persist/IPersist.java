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

package com.francetelecom.admindm.persist;

/**
 * The Interface IPersist.
 */
public interface IPersist {

	/**
	 * Persist.
	 * 
	 * @param key
	 *            the key
	 * @param subscribers
	 *            the subscribers
	 * @param notification
	 *            the notification
	 * @param value
	 *            the value
	 * @param type
	 *            the type
	 */
	void persist(String key, String[] subscribers, int notification, Object value, int type);

	/**
	 * Restore parameter value.
	 * 
	 * @param name
	 *            the name
	 * @param type
	 *            the type
	 * @return the object
	 */
	Object restoreParameterValue(String name, int type);

	/**
	 * Restore parameter subscriber.
	 * 
	 * @param name
	 *            the name
	 * @return the string[]
	 */
	String[] restoreParameterSubscriber(String name);

	/**
	 * Restore parameter notification.
	 * 
	 * @param name
	 *            the name
	 * @return the int
	 */
	int restoreParameterNotification(String name);
}
