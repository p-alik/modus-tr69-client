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

package com.francetelecom.admindm.download;

import java.io.File;
import java.io.FileReader;

import junit.framework.TestCase;

import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;

import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.download.api.Upload;

/**
 * The Class DownloadDecoderTest.
 */
public final class UploadDecoderTest extends TestCase {
	/**
	 * Test decode.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void testDecode() throws Exception {
		TestUtil.COMMENT("Each field is complete");
		TestUtil.TRACE(this);
		RPCDecoder decoder = new UploadDecoder(new MockDownloadEngine());
		File file = new File("./src/test/resources/Upload_1.xml");
		FileReader reader = new FileReader(file);
		KXmlParser parser = new KXmlParser();
		parser.setInput(reader);
		Document doc = new Document();
		doc.parse(parser);
		Upload method = (Upload) decoder.decode(doc.getRootElement());
		assertEquals("CommandKey", method.getCommandKey());
		assertEquals("FileType", method.getFileType());
		assertEquals("URL", method.getUrl());
		assertEquals("Username", method.getUsername());
		assertEquals("Password", method.getPassword());
		assertEquals(0, method.getDelaySeconds());

	}
}
