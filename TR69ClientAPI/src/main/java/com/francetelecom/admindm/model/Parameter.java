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

package com.francetelecom.admindm.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.francetelecom.admindm.api.CheckCallBack;
import com.francetelecom.admindm.api.Factory;
import com.francetelecom.admindm.api.Getter;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.Setter;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.persist.IPersist;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
import com.francetelecom.admindm.soap.Soap;

/**
 * The Class Parameter.
 * 
 * @author Olivier Beyler - OrangeLabs
 */
public final class Parameter extends Observable {
	/**
	 * The check call back list. Each call back will be invoked and can throw an
	 * Fault exception. Classic call back is check the minimum or the maximum
	 * value allowed.
	 */
	private List lsCheckCallBack = new ArrayList();

	/**
	 * Gets the list of check call back.
	 * 
	 * @return the list of check call back
	 */
	public List getLsCheckCallBack() {
		return this.lsCheckCallBack;
	}

	/**
	 * The setter call back witch be call instead of basic set if it's not null.
	 */
	private Setter setter = null;

	/**
	 * Sets the setter.
	 * 
	 * @param pSetter
	 *            the setter
	 */
	public void setSetter(final Setter pSetter) {
		this.setter = pSetter;
	}

	/**
	 * Sets the getter.
	 * 
	 * @param pGetter
	 *            the getter
	 */
	public void setGetter(final Getter pGetter) {
		this.getter = pGetter;
	}

	/**
	 * The getter call back witch be call instead of basic getter if it's not
	 * null.
	 */
	private Getter getter = null;
	/** The factory. */
	private Factory factory = null;
	/** The name. */
	private String name;
	/** The type. */
	private int type;
	/** The storage mode. */
	private int storageMode;
	/** The writable. */
	private boolean writable;
	/** The immediate changes. */
	private boolean immediateChanges;
	/** The access list. */
	private String[] accessList;
	/** The notification. */
	private int notification;
	/** The mandatory notification. */
	private boolean mandatoryNotification;
	/** The active notification denied. */
	private boolean activeNotificationDenied;
	/** The update mode. */
	private int updateMode;
	/** The state. */
	private int state;
	/** The back value. */
	private Object backValue;
	/** The value. */
	private Object value;
	/** hide the value in case of getTextValue. */
	private boolean hidden = false;
	/** The persist. */
	private IPersist persist = null;

	/**
	 * Gets the persist.
	 * 
	 * @return the persist
	 */
	public IPersist getPersist() {
		return this.persist;
	}

	/**
	 * Sets the persist.
	 * 
	 * @param pPersist
	 *            the new persist
	 */
	public void setPersist(final IPersist pPersist) {
		this.persist = pPersist;
		if (this.persist != null && getStorageMode() != StorageMode.COMPUTED) {
			this.persist.persist(this.name, this.accessList, this.notification, this.value, this.type);
		}
	}

	/**
	 * toString.
	 * 
	 * @return String
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer("name:" + this.name + ";");
		buffer.append("type:" + this.type + ";");
		// buffer.append(minValue).append(";");
		// buffer.append(maxValue).append(";");
		buffer.append("storageMode:" + this.storageMode + ";");
		buffer.append("writable:" + this.writable + ";");
		buffer.append("immediateChanges:" + this.immediateChanges + ";");
		buffer.append("accessList:" + this.accessList + ";");
		buffer.append("notification:" + this.notification + ";");
		buffer.append("mandatoryNotification:" + this.mandatoryNotification + ";");
		buffer.append("activeNotificationDenied:" + this.activeNotificationDenied + ";");
		buffer.append("updateMode:" + this.updateMode + ";");
		buffer.append("state:" + this.state + ";");
		buffer.append("backValue:" + this.backValue + ";");
		buffer.append("value:" + this.value + ";");
		return buffer.toString();
	}

	/**
	 * clear change tag.
	 * 
	 * @see java.util.Observable#clearChanged()
	 */
	public void clearChanged() {
		super.clearChanged();
	}

	/**
	 * Gets the text value.
	 * 
	 * @param sessionId
	 *            the session id
	 * @return the text value
	 */
	public String getTextValue(final String sessionId) {
		String result = null;
		if (isHidden()) {
			result = "";
		} else {
			if (this.getter != null) {
				this.value = this.getter.get(sessionId);
			}
			if (this.value != null) {
				if (this.type == ParameterType.DATE) {
					result = Soap.convertDate2String(((Long) this.value).longValue());
				} else {
					result = this.value.toString();
				}
			}
		}
		return result;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/** The Constant MAX_LENGTH_NAME. */
	private static final int MAX_LENGTH_NAME = 256;

	/**
	 * Sets the name.
	 * 
	 * @param pName
	 *            the new name
	 * @throws Fault
	 *             the fault
	 */
	public void setName(final String pName) throws Fault {
		if (pName.length() > MAX_LENGTH_NAME) {
			StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9007);
			error.append(" exceed max lenght 256");
			throw new Fault(FaultUtil.FAULT_9007, error.toString());
		}
		this.name = pName;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param pType
	 *            the new type
	 */
	public void setType(final int pType) {
		this.type = pType;
		switch (pType) {
		case ParameterType.INT:
			addCheck(new CheckMaximum(Integer.MAX_VALUE));
			addCheck(new CheckMinimum(Integer.MIN_VALUE));
			break;
		case ParameterType.UINT:
			addCheck(new CheckMinimum(0));
			break;
		case ParameterType.BOOLEAN:
			addCheck(CheckBoolean.getInstance());
			break;
		case ParameterType.DATE:
			addCheck(CheckDate.getInstance());
			break;
		// case ParameterType.LONG:
		default:
			break;
		}
	}

	/**
	 * Gets the storage mode.
	 * 
	 * @return the storage mode
	 */
	public int getStorageMode() {
		return this.storageMode;
	}

	/**
	 * Sets the storage mode.
	 * 
	 * @param pStorageMode
	 *            the new storage mode
	 */
	public void setStorageMode(final int pStorageMode) {
		this.storageMode = pStorageMode;
	}

	/**
	 * Checks if is writable.
	 * 
	 * @return true, if is writable
	 */
	public boolean isWritable() {
		return this.writable;
	}

	/**
	 * Sets the writable.
	 * 
	 * @param pWritable
	 *            the new writable
	 */
	public void setWritable(final boolean pWritable) {
		if (this.writable != pWritable) {
			this.writable = pWritable;
		}
	}

	/**
	 * Checks if is immediate changes.
	 * 
	 * @return true, if is immediate changes
	 */
	public boolean isImmediateChanges() {
		return this.immediateChanges;
	}

	/**
	 * Sets the immediate changes.
	 * 
	 * @param pImmediateChanges
	 *            the new immediate changes
	 */
	public void setImmediateChanges(final boolean pImmediateChanges) {
		if (this.immediateChanges != pImmediateChanges) {
			this.immediateChanges = pImmediateChanges;
		}
	}

	/**
	 * Gets the access list.
	 * 
	 * @return the access list
	 */
	public String[] getAccessList() {
		return this.accessList;
	}

	/**
	 * Sets the access list.
	 * 
	 * @param pAccessList
	 *            the new access list
	 */
	public void setAccessList(final String[] pAccessList) {
		if ((this.accessList == null) || (!this.accessList.equals(pAccessList))) {
			this.accessList = pAccessList;
			this.setChanged();
			if (this.persist != null) {
				this.persist.persist(this.name, this.accessList, this.notification, this.value, this.type);
			}
		}
	}

	/**
	 * Gets the notification.
	 * 
	 * @return the notification
	 */
	public int getNotification() {
		return this.notification;
	}

	/**
	 * Sets the notification.
	 * 
	 * @param pNotification
	 *            the new notification
	 */
	public void setNotification(final int pNotification) {
		if (this.notification != pNotification) {
			this.notification = pNotification;
			this.setChanged();
			if (this.persist != null) {
				this.persist.persist(this.name, this.accessList, this.notification, this.value, this.type);
			}
		}
	}

	/**
	 * Checks if is mandatory notification.
	 * 
	 * @return true, if is mandatory notification
	 */
	public boolean isMandatoryNotification() {
		return this.mandatoryNotification;
	}

	/**
	 * Sets the mandatory notification.
	 * 
	 * @param pMandatoryNotification
	 *            the new mandatory notification
	 */
	public void setMandatoryNotification(final boolean pMandatoryNotification) {
		if (this.mandatoryNotification != pMandatoryNotification) {
			this.mandatoryNotification = pMandatoryNotification;
		}
	}

	/**
	 * Checks if is active notification denied.
	 * 
	 * @return true, if is active notification denied
	 */
	public boolean isActiveNotificationDenied() {
		return this.activeNotificationDenied;
	}

	/**
	 * Sets the active notification denied.
	 * 
	 * @param pActiveNotificationDenied
	 *            the active notification denied
	 */
	public void setActiveNotificationDenied(final boolean pActiveNotificationDenied) {
		if (this.activeNotificationDenied != pActiveNotificationDenied) {
			this.activeNotificationDenied = pActiveNotificationDenied;
		}
	}

	/**
	 * Gets the update mode.
	 * 
	 * @return the update mode
	 */
	public int getUpdateMode() {
		return this.updateMode;
	}

	/**
	 * Sets the update mode.
	 * 
	 * @param pUpdateMode
	 *            the new update mode
	 */
	public void setUpdateMode(final int pUpdateMode) {
		if (this.updateMode != pUpdateMode) {
			this.updateMode = pUpdateMode;
			this.setChanged();
		}
	}

	/**
	 * Gets the state.
	 * 
	 * @return the state
	 */
	public int getState() {
		return this.state;
	}

	/**
	 * Sets the state.
	 * 
	 * @param pState
	 *            the new state
	 */
	public void setState(final int pState) {
		if (this.state != pState) {
			this.state = pState;
			this.setChanged();
		}
	}

	/**
	 * Gets the back value.
	 * 
	 * @return the back value
	 */
	public Object getBackValue() {
		return this.backValue;
	}

	/**
	 * Sets the back value.
	 * 
	 * @param pBackValue
	 *            the new back value
	 */
	public void setBackValue(final Object pBackValue) {
		if (this.backValue == null || !this.value.equals(pBackValue)) {
			this.backValue = pBackValue;
			this.setChanged();
		}
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public Object getValue() {
		return getValue("");
	}

	/**
	 * Gets the value.
	 * 
	 * @param sessionId
	 *            the session id
	 * @return the value
	 */
	public Object getValue(final String sessionId) {
		Object result;
		if (this.getter != null) {
			result = this.getter.get(sessionId);
		} else {
			result = this.value;
		}
		return result;
	}

	/**
	 * Sets the value.
	 * 
	 * @param pValue
	 *            the new value
	 * @throws Fault
	 *             the fault
	 */
	public void setValue(final Object pValue) throws Fault {
		if (this.setter == null) {
			setDirectValue(pValue);
		} else {
			this.setter.set(this, pValue);
		}
	}

	/**
	 * Sets the value without use of setter.
	 * 
	 * @param pValue
	 *            the new value
	 */
	public void setDirectValue(final Object pValue) {
		if (this.value == null || !this.value.equals(pValue)) {
			this.value = pValue;
			this.setChanged();
			if (this.persist != null && getStorageMode() != StorageMode.COMPUTED) {
				this.persist.persist(this.name, this.accessList, this.notification, this.value, this.type);
			}
			this.notifyObservers();
		}
	}

	/**
	 * Sets the value without saving.
	 * 
	 * @param pValue
	 *            the new value
	 */
	public void setValueWithout(final Object pValue) {
		if (this.value == null || !this.value.equals(pValue)) {
			this.value = pValue;
		}
	}

	/**
	 * Gets the value.
	 * 
	 * @param value
	 *            the value
	 * @param type
	 *            the type
	 * @param key
	 *            the key
	 * @return the value
	 * @throws Fault
	 *             the fault
	 */
	public static Object getValue(final String key, final String value, final int type) throws Fault {
		Object result = null;
		switch (type) {
		case ParameterType.STRING:
			result = value;
			break;
		case ParameterType.INT:
			result = getINT(key, value);
			break;
		case ParameterType.LONG:
			result = getLONG(key, value);
			break;
		case ParameterType.UINT:
			result = getUINT(key, value);
			break;
		case ParameterType.BOOLEAN:
			result = getBOOLEAN(key, value);
			break;
		case ParameterType.DATE:
			result = getDATE(key, value);
			break;
		default:
			result = value;
			break;
		}
		return result;
	}

	/**
	 * Gets the dATE.
	 * 
	 * @param value
	 *            the value
	 * @param key
	 *            the key
	 * @return the dATE
	 * @throws ParseException
	 */
	protected static Long getDATE(final String key, final String value) {
		Long result = null;
		Pattern timeNoMilli = Pattern.compile(
		// Absolute time 2009-01-01T00:00:00Z
		// time zoned Z is optional
				"(" + "[2][0-9][0-9][0-9]" + // 2000 to 2999
						"-" + // -
						"([0][1-9]|[1][0-2])" + // 01 - alias January ... 12
						// alias December
						"-" + // -
						"([0][1-9]|[1-2][0-9]|[3][0-1])" + // from 01 to 31
						"T" + // T
						"([0-1][0-9]|[2][0-3])" + // 00 hours
						":" + // :
						"[0-5][0-9]" + // 00 minutes
						":" + // :
						"[0-5][0-9]" + // 00 seconds
						"([Z]|([/+|/-]([0][0-9]|[1][0-9])))?" + // 00 TIME ZONE
						")|("
						// relative time 0001-01-01T00:00 (0001 is
						+ "[0-1][0-9][0-9][0-9]" + // 0000
						"-" + // -
						"([0][1-9]|[1][0-2])" + // 01 - alias January ... 12
						// alias December
						"-" + // -
						"([0][1-9]|[1-2][0-9]|[3][0-1])" + // from 01 to 31
						"T" + // T
						"([0-1][0-9]|[2][0-3])" + // 00 hours
						":" + // :
						"[0-5][0-9]" + // 00 minutes
						":" + // :
						"[0-5][0-9]" + // 00 seconds
						")" + // 00 seconds
						"([Z]|([/+|/-]([0][0-9]|[1][0-9])))?" + // 00 TIME ZONE
						"");
		Pattern timeMilli = Pattern.compile(
		// Absolute time 2009-01-01T00:00:00.000Z
		// time zoned Z is optional
				"(" + "[2][0-9][0-9][0-9]" + // 2000 to 2999
						"-" + // -
						"([0][1-9]|[1][0-2])" + // 01 - alias January ... 12
						// alias December
						"-" + // -
						"([0][1-9]|[1-2][0-9]|[3][0-1])" + // from 01 to 31
						"T" + // T
						"([0-1][0-9]|[2][0-3])" + // 00 hours
						":" + // :
						"[0-5][0-9]" + // 00 minutes
						":" + // :
						"[0-5][0-9]" + // 00 seconds
						"(([.])([0-9]+))" + // 000 fractional seconds
						"([Z]|([/+|/-]([0][0-9]|[1][0-9])))?" + // 00 TIME ZONE
						")|("
						// relative time 0001-01-01T00:00 (0001 is
						+ "[0-1][0-9][0-9][0-9]" + // 0000
						"-" + // -
						"([0][1-9]|[1][0-2])" + // 01 - alias January ... 12
						// alias December
						"-" + // -
						"([0][1-9]|[1-2][0-9]|[3][0-1])" + // from 01 to 31
						"T" + // T
						"([0-1][0-9]|[2][0-3])" + // 00 hours
						":" + // :
						"[0-5][0-9]" + // 00 minutes
						":" + // :
						"[0-5][0-9]" + // 00 seconds
						"(([.])([0-9]+))" + // 000 fractional seconds
						")" + // 00 seconds
						"([Z]|([/+|/-]([0][0-9]|[1][0-9])))?" + // 00 TIME ZONE
						"");

		try {
			if ("".equals(value) || value == null || "0001-01-01T00:00:00Z".equals(value)
					|| "0001-01-01T00:00:00.000Z".equals(value)) {
				result = new Long(-1);
			} else {
				DateFormat format;
				Date date = null;
				Matcher m = timeNoMilli.matcher(value.toString());
				Matcher m2 = timeMilli.matcher(value.toString());
				if (m.matches()) { // Si on n'a pas les millisecondes
					format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
					date = format.parse(value);
				} else if (m2.matches()) { // si on les a
					format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'.'S'Z'");
					date = format.parse(value);
				} else
					Log.error("Erreur Parsing :" + value);
				result = new Long(date.getTime());
			}
		} catch (NumberFormatException e) {
			// TODO Cest une chaine a parser et non un long!!!!
			Log.error(e.getMessage());
		} catch (ParseException e) {
			Log.error(e.getMessage());
		}
		return result;
	}

	/**
	 * Gets the bOOLEAN.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the bOOLEAN
	 * @throws Fault
	 *             the fault
	 */
	protected static Object getBOOLEAN(final String key, final String value) throws Fault {
		Boolean result = null;
		if ("true".equals(value) || "1".equals(value) || "TRUE".equals(value)) {
			result = Boolean.TRUE;
		} else if ("false".equals(value) || "0".equals(value) || "FALSE".equals(value)) {
			result = Boolean.FALSE;
		} else {
			StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9007);
			error.append(": only valid values for ");
			error.append(key);
			error.append(" are:\"true\", \"1\"");
			error.append(" \"false\", \"0\" and not :");
			error.append(value);
			throw new Fault(FaultUtil.FAULT_9007, error.toString());
		}
		return result;
	}

	/**
	 * Gets the iNT.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the iNT
	 * @throws Fault
	 *             the fault
	 */
	protected static Object getINT(final String key, final String value) throws Fault {
		Integer result = null;
		try {
			result = Integer.valueOf(String.valueOf(value));
		} catch (NumberFormatException e) {
			StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9007);
			error.append(": only valid values for ");
			error.append(key);
			error.append(" are integer");
			error.append(" and not :");
			error.append(value);
			throw new Fault(FaultUtil.FAULT_9007, error.toString(), e);
		}
		return result;
	}

	/**
	 * Gets the lONG.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the lONG
	 * @throws Fault
	 *             the fault
	 */
	protected static Object getLONG(final String key, final String value) throws Fault {
		Long result = null;
		try {
			long l = Long.parseLong(value);
			result = new Long(l);
		} catch (NumberFormatException e) {
			StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9007);
			error.append(": only valid values for ");
			error.append(key);
			error.append(" are Long");
			error.append(" and not :");
			error.append(value);
			throw new Fault(FaultUtil.FAULT_9007, error.toString(), e);
		}
		return result;
	}

	/**
	 * Gets the uINT.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the uINT
	 * @throws Fault
	 *             the fault
	 */
	protected static Object getUINT(final String key, final String value) throws Fault {
		Long result = null;
		try {
			long l = Long.parseLong(value);
			if (l < 0) {
				throw new NumberFormatException();
			}
			result = new Long(l);
		} catch (NumberFormatException e) {
			StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9007);
			error.append(": only valid values for ");
			error.append(key);
			error.append("are unsigned integer");
			error.append(" and not :");
			error.append(value);
			throw new Fault(FaultUtil.FAULT_9007, error.toString(), e);
		}
		return result;
	}

	/**
	 * Check.
	 * 
	 * @param pValue
	 *            the value
	 * @throws Fault
	 *             the fault
	 */
	public void check(final Object pValue) throws Fault {
		Iterator it = this.lsCheckCallBack.iterator();
		while (it.hasNext()) {
			CheckCallBack cb = (CheckCallBack) it.next();
			cb.check(pValue);
		}
	}

	/**
	 * Adds the check.
	 * 
	 * @param check
	 *            the check
	 */
	public void addCheck(final CheckCallBack check) {
		this.lsCheckCallBack.add(check);
	}

	/**
	 * Gets the factory.
	 * 
	 * @return the factory
	 */
	public Factory getFactory() {
		return this.factory;
	}

	/**
	 * Sets the factory.
	 * 
	 * @param pFactory
	 *            the new factory
	 */
	public void setFactory(final Factory pFactory) {
		this.factory = pFactory;
	}

	/**
	 * Sets the hidden.
	 * 
	 * @param hidden
	 *            the new hidden
	 */
	public void setHidden(final boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * Checks if is hidden.
	 * 
	 * @return true, if is hidden
	 */
	public boolean isHidden() {
		return this.hidden;
	}
}
