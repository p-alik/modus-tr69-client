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
import java.util.Date;

import junit.framework.TestCase;

import org.ow2.odis.test.TestUtil;

import com.francetelecom.admindm.soap.Fault;

public class ParameterTest extends TestCase {
	public void testToString() {
		TestUtil.TRACE(this, "toString and getTextValue must never throw any exception");
		Parameter param;
		// ----------
		param = new Parameter();
		param.setType(ParameterType.INT);
		param.setValueWithout(Integer.valueOf("10"));
		System.out.println(param.toString());
		System.out.println(param.getTextValue(""));
		// ----------
		param = new Parameter();
		param.setType(ParameterType.UINT);
		param.setValueWithout(Integer.valueOf("10"));
		System.out.println(param.toString());
		System.out.println(param.getTextValue(""));
		// ----------
		param = new Parameter();
		param.setType(ParameterType.BOOLEAN);
		param.setValueWithout(Boolean.TRUE);
		System.out.println(param.toString());
		System.out.println(param.getTextValue(""));
		// ----------
		param = new Parameter();
		param.setType(ParameterType.DATE);
		param.setValueWithout(new Long(0l));
		System.out.println(param.toString());
		System.out.println(param.getTextValue(""));
		// ----------
		param = new Parameter();
		param.setType(ParameterType.LONG);
		param.setValueWithout(new Long(4l));
		System.out.println(param.toString());
		System.out.println(param.getTextValue(""));
		// ----------
		param = new Parameter();
		param.setType(ParameterType.DATE);
		param.setValueWithout(new Long(45l));
		System.out.println(param.toString());
		System.out.println(param.getTextValue(""));
		// ----------
		param = new Parameter();
		param.setType(ParameterType.STRING);
		param.setValueWithout(new Long(0l));
		System.out.println(param.toString());
		System.out.println(param.getTextValue(""));
		// ----------
		param = new Parameter();
		param.setType(ParameterType.DATE);
		param.setValueWithout(null);
		System.out.println(param.toString());
		System.out.println(param.getTextValue(""));
		// ----------
		param = new Parameter();
		param.setType(ParameterType.LONG);
		param.setValueWithout(null);
		System.out.println(param.toString());
		System.out.println(param.getTextValue(""));
	}

	public void testGetBOOLEAN() throws Fault {
		TestUtil.TRACE(this);
		Object result;
		result = Parameter.getBOOLEAN("test", "true");
		assertEquals(Boolean.TRUE, result);
		result = Parameter.getBOOLEAN("test", "false");
		assertEquals(Boolean.FALSE, result);

	}

	public void testGetDate() throws Fault {
		TestUtil.TRACE(this);
		Long result;
		result = Parameter.getDATE("test", "");
		assertEquals(new Long(-1), result);
		result = Parameter.getDATE("test", "0001-01-01T00:00:00Z");
		assertEquals(new Long(-1), result);

		result = Parameter.getDATE("test", "2001-01-01T00:00:00Z");
		Date date = new Date(result.longValue());

		// assertEquals("compare " + date.toLocaleString(),
		// "1 janv. 2001 00:00:00", date.toLocaleString());
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		assertEquals("compare " + dateFormat.format(date), "1 janv. 2001 00:00:00", dateFormat.format(date));

		/*
		 * DateFormat formater = SimpleDateFormat.getTimeInstance() ; DateFormat
		 * formater2 = SimpleDateFormat.getDateInstance() ; String result2 = new
		 * String(formater.format(date)) ;
		 * assertEquals("compare "+result2,"1 janv. 2001 00:00:00",
		 * formater2.format(date)+" "+formater.format(date));
		 */

		result = Parameter.getDATE("test", "1001-01-01T00:00:00Z");
		date = new Date(result.longValue());

		// assertEquals("compare " + date.toLocaleString(),
		// "1 janv. 1001 00:00:00", date.toLocaleString());
		assertEquals("compare " + dateFormat.format(date), "1 janv. 1001 00:00:00", dateFormat.format(date));

	}
	// public void testSetType() {
	// fail("Not yet implemented");
	// }
	// public void testSetAccessList() {
	// fail("Not yet implemented");
	// }
	// public void testSetNotification() {
	// fail("Not yet implemented");
	// }
	// public void testSetDirectValue() {
	// fail("Not yet implemented");
	// }
	// public void testSetValueWithout() {
	// fail("Not yet implemented");
	// }
	// public void testGetValueStringStringInt() {
	// fail("Not yet implemented");
	// }
}
