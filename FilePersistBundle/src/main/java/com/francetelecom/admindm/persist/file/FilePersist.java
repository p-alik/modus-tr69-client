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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.francetelecom.admindm.api.FileUtil;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.persist.IPersist;

/**
 * The Class FilePersist.
 */
public final class FilePersist implements IPersist {
	/** The Constant INT. */
	public static final int INT = 0;
	/** The Constant UINT. */
	public static final int UINT = 1;
	/** The Constant LONG. */
	public static final int LONG = 2;
	/** The Constant BOOLEAN. */
	public static final int BOOLEAN = 3;
	/** The Constant DATE. */
	public static final int DATE = 4;
	/** The Constant STRING. */
	public static final int STRING = 5;
	/** The Constant ANY. */
	public static final int ANY = 6;
	/** The Constant UNDEFINED. */
	public static final int UNDEFINED = 7;
	/** The Constant BASE64. */
	public static final int BASE64 = 8;
	/** The data save file. */
	private File dataSaveFile;

	/** The map key. */
	private Map mapKey = new HashMap();
	/** The last line. */
	int lastLine = 0;

	/**
	 * The Constructor use a file indirection.
	 * 
	 * @param filename
	 *            the filename
	 * 
	 * @throws Exception
	 *             , if any pb occurs.
	 */
	public FilePersist() throws Exception {
		dataSaveFile = FileUtil.getFileFromShortName(FileUtil.SAVE);
		initializeData();
	}

	/**
	 * Initialize data.
	 * 
	 * Note that this method is "public" just because the tests require it.
	 * 
	 * @throws Exception
	 *             , if any pb occurs.
	 */
	public void initializeData() throws Exception {
		try {
			if (dataSaveFile == null) {
				StringBuffer error = new StringBuffer("\"");
				error.append(FileUtil.SAVE);
				error.append("=\" is not defined into ");
				error.append(FileUtil.CONFIG_FILE);
				Log.error(error.toString());
				throw new RuntimeException(error.toString());
			}
			if (dataSaveFile.exists()) {
				FileInputStream istream = null;
				try {
					istream = new FileInputStream(dataSaveFile);
					ObjectInputStream p = new ObjectInputStream(istream);
					mapKey = (HashMap) p.readObject();

					System.out.println("AC1982: 24 sept. 2014 14:28:27: mapkey: " + mapKey);

				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (istream != null) {
							istream.close();
						}
					} catch (IOException e) {
						Log.error("ShouldNotOccured", e);
					}
				}
			} else {
				String path = dataSaveFile.getAbsolutePath();
				int pos = path.lastIndexOf(File.separator);
				if (pos >= 0) {
					File dir = new File(path.substring(0, pos));
					dir.mkdirs();
				}
				dataSaveFile.createNewFile();
			}
		} catch (Exception e) {
			throw new Exception("Persist File not defined, see log.", e);
		}
	}

	/**
	 * Persist parameter attribute.
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
	 * @see com.francetelecom.admindm.persist.IPersist#persistParameterAttribute(java.lang.String, java.lang.String[],
	 *      int)
	 */
	public void persist(final String key, final String[] subscribers, final int notification, final Object value,
			final int type) {

		System.out.println("AC1982: 24 sept. 2014 11:05:34: FilePersist.persist(key: " + key + ", subscribers: "
				+ subscribers + ", notification: " + notification + ", value: " + value + ", type: " + type);

		mapKey.put(key, new PersistElement(key, subscribers, notification, value));
		try {
			// Log.debug("dataSaveFile.createNewFile(): " + dataSaveFile.createNewFile());
			// dataSaveFile.createNewFile(); - there is no need to execute this line; the file has been initialized in
			// FilePersist constructor.
			FileOutputStream ostream = new FileOutputStream(dataSaveFile);
			ObjectOutputStream p = new ObjectOutputStream(ostream);
			// System.out.println("AC1982: 24 sept. 2014 11:34:45: mapKey: " + mapKey);
			// Iterator ki = mapKey.keySet().iterator();
			// while (ki.hasNext()) {
			// Object k = ki.next();
			// System.out.println("AC1982: 24 sept. 2014 11:36:01: write: k: " + k + ", mapKey.get(k): "
			// + mapKey.get(k));
			// }
			p.writeObject(mapKey);
			ostream.close();
		} catch (IOException e) {
			Log.error("ShouldNotOccured", e);
		}
	}

	/**
	 * Restore parameter notification.
	 * 
	 * @param key
	 *            the key
	 * @return the int
	 */
	public int restoreParameterNotification(String key) {
		int result = 0;
		PersistElement element = (PersistElement) mapKey.get(key);
		if (element != null) {
			result = element.getNotification();
		}
		return result;
	}

	/**
	 * Restore parameter subscriber.
	 * 
	 * @param key
	 *            the key
	 * @return the string[]
	 */
	public String[] restoreParameterSubscriber(String key) {
		String[] result = new String[0];
		PersistElement element = (PersistElement) mapKey.get(key);
		if (element != null) {
			result = element.getSubscribers();
		}
		return result;
	}

	/**
	 * Restore parameter value.
	 * 
	 * @param key
	 *            the key
	 * @param type
	 *            the type
	 * @return the object
	 */
	public Object restoreParameterValue(String key, int type) {
		Object result = null;
		PersistElement element = (PersistElement) mapKey.get(key);
		if (element != null) {
			result = element.getValue();
		}
		return result;
	}
}
