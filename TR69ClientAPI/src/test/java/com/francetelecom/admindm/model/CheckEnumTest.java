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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.ow2.odis.test.TestUtil;

import com.francetelecom.admindm.soap.Fault;

public class CheckEnumTest extends TestCase {
	public void testCheckWithList() {
		TestUtil.TRACE(this);
		List lsautorise = new ArrayList();
		lsautorise.add("test");
		CheckEnum cd = new CheckEnum(lsautorise);
		try {
			cd.check(null);
			fail("should throw a fault");
		} catch (Fault e) {
			System.out.println("Ok");
			System.out.println(e.getFaultcode());
			System.out.println(e.getFaultstring());
		}
		try {
			cd.check("not test");
			fail("should throw a fault");
		} catch (Fault e) {
			System.out.println("Ok");
			System.out.println(e.getFaultcode());
			System.out.println(e.getFaultstring());
		}
		try {
			cd.check("test");
		} catch (Fault e) {
			System.out.println(e.getFaultcode());
			System.out.println(e.getFaultstring());
			fail("should not throw a fault");
		}
	}

	public void testCheckWithArray() {
		TestUtil.TRACE(this);
		String[] lsautorise = { "test" };
		CheckEnum cd = new CheckEnum(lsautorise);
		try {
			cd.check(null);
			fail("should throw a fault");
		} catch (Fault e) {
			System.out.println("Ok");
			System.out.println(e.getFaultcode());
			System.out.println(e.getFaultstring());
		}
		try {
			cd.check("not test");
			fail("should throw a fault");
		} catch (Fault e) {
			System.out.println("Ok");
			System.out.println(e.getFaultcode());
			System.out.println(e.getFaultstring());
		}
		try {
			cd.check("test");
		} catch (Fault e) {
			System.out.println(e.getFaultcode());
			System.out.println(e.getFaultstring());
			fail("should not throw a fault");
		}
	}

	public void testCheckWithArray2Value() {
		TestUtil.TRACE(this);
		String[] lsautorise = { "test", "toto" };
		CheckEnum cd = new CheckEnum(lsautorise);
		try {
			cd.check(null);
			fail("should throw a fault");
		} catch (Fault e) {
			System.out.println("Ok");
			System.out.println(e.getFaultcode());
			System.out.println(e.getFaultstring());
		}
		try {
			cd.check("not test");
			fail("should throw a fault");
		} catch (Fault e) {
			System.out.println("Ok");
			System.out.println(e.getFaultcode());
			System.out.println(e.getFaultstring());
		}
		try {
			cd.check("test");
			cd.check("toto");
		} catch (Fault e) {
			System.out.println(e.getFaultcode());
			System.out.println(e.getFaultstring());
			fail("should not throw a fault");
		}
	}
}
