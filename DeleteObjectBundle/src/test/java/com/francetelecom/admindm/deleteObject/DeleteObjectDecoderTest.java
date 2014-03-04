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

package com.francetelecom.admindm.deleteObject;

import java.io.File;
import java.io.FileReader;

import junit.framework.TestCase;

import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;

import com.francetelecom.admindm.soap.Fault;

public class DeleteObjectDecoderTest extends TestCase {

	/**
	 * Test decode empty.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testDecodeEmpty() throws Exception {
		TestUtil.TRACE(this);
		DeleteObjectDecoder decoder = new DeleteObjectDecoder();

		File file = new File("./src/test/resources/DeleteObject_empty.xml");
		FileReader reader = new FileReader(file);
		KXmlParser parser = new KXmlParser();
		parser.setInput(reader);
		Document doc = new Document();
		doc.parse(parser);
		try {
			decoder.decode(doc.getRootElement());
			fail("Should throw a Fault ObjectName musn't be empty");
		} catch (Fault e) {
			System.out.println("OK" + e.getFaultstring());
		}
	}

	/**
	 * Test decode empty.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testDecodeBad() throws Exception {
		TestUtil.TRACE(this);
		DeleteObjectDecoder decoder = new DeleteObjectDecoder();

		File file = new File("./src/test/resources/DeleteObject_Bad.xml");
		FileReader reader = new FileReader(file);
		KXmlParser parser = new KXmlParser();
		parser.setInput(reader);
		Document doc = new Document();
		doc.parse(parser);
		try {
			decoder.decode(doc.getRootElement());
			fail("Should throw a Fault ObjectName musnt be present");
		} catch (Fault e) {
			System.out.println("OK" + e.getFaultstring());
		}
	}

	/**
	 * Test decode empty.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testDecode() throws Exception {
		TestUtil.TRACE(this);
		DeleteObjectDecoder decoder = new DeleteObjectDecoder();

		File file = new File("./src/test/resources/DeleteObject.xml");
		FileReader reader = new FileReader(file);
		KXmlParser parser = new KXmlParser();
		parser.setInput(reader);
		Document doc = new Document();
		doc.parse(parser);
		try {
			decoder.decode(doc.getRootElement());
		} catch (Fault e) {
			fail("Should not throw a Fault");
		}
	}
}
