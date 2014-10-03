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

package com.francetelecom.admindm.persist.file;

import java.io.Serializable;
import java.util.Arrays;

public class PersistElement implements Serializable {

	/** generated */
	private static final long serialVersionUID = -7843013882435346259L;

	private final String key;
	private final String[] subscribers;
	private final int notification;
	private final Object value;

	public String getKey() {
		return key;
	}

	public String[] getSubscribers() {
		return subscribers;
	}

	public int getNotification() {
		return notification;
	}

	public Object getValue() {
		return value;
	}

	public PersistElement(String key, String[] subscribers, int notification, Object value) {
		this.key = key;
		this.subscribers = subscribers;
		this.notification = notification;
		this.value = value;
	}
	
	public boolean equals(String pKey, String[] pSubscribers, int pNotification, Object pValue) {
		
		// check key field
		if ((key == null) && (pKey != null))  {
			return false;
		} else if ((key != null) && (!key.equals(pKey))) {
			return false;
		}
		
		// check notification field
		if (notification != pNotification)  {
			return false;
		}
		
		// check subscribers array field
		if ((subscribers == null) && (pSubscribers != null)) {
			return false;
		} else if ((subscribers != null) && (!Arrays.equals(subscribers, pSubscribers))) {
			return false;
		}
		
		// check value field
		if ((value == null) && (pValue != null)) {
			return false;
		} else if ((value != null) && (!value.equals(pValue))) {
			return false;
		}
			
		return true;
	}

	public String toString() {
		String result = "" + PersistElement.class.getName() + "[key: " + key + ", subscribers: " + subscribers
				+ ", notification: " + notification + ", value: " + value + "]";
		return result;
	}
}
