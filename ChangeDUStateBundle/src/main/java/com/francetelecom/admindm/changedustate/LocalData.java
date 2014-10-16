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

/**
 * @author: JZBV7415
 * @mail: antonin.chazalet@orange.com,antonin.chazalet@gmail.com
 */
public class LocalData {

	/**
	 * uuid, i.e. the bundleId.
	 */
	private long uuid;
	private String version;

	/**
	 * @param uuid
	 *            , i.e. the bundleId.
	 * @param version
	 */
	public LocalData(final long uuid, final String version) {
		this.uuid = uuid;
		this.version = version;
	}

	/**
	 * @return uuid, i.e. the bundleId
	 */
	public long getUuid() {
		return this.uuid;
	}

	public String getVersion() {
		return this.version;
	}

	public int hashCode() {
		int result = 31 + (int) (uuid ^ (uuid >>> 32));
		return 31 * result + ((version == null) ? 0 : version.hashCode());
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		LocalData other = (LocalData) obj;
		return (uuid == other.uuid) 
			&& ((version == null) ? (other.version == null) : version.equals(other.version));
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String toString = this.getClass().getName() + "[" + "uuid=" + this.uuid + ",version=" + this.version + "]";
		return toString;
	}

}
