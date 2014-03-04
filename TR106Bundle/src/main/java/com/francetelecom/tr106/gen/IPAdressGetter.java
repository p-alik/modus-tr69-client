/**
 * Copyright France Telecom (Orange Labs R&D) 2008,  All Rights Reserved.
 *
 * This software is the confidential and proprietary information
 * of France Telecom (Orange Labs R&D). You shall not disclose
 * such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * France Telecom (Orange Labs R&D)
 *
 * Project     : Modus
 * Software    : Library
 *
 * Author : Orange Labs R&D O.Beyler
 * Generated :  by GenModel
 */

package com.francetelecom.tr106.gen;

// import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.com.HttpServer;

public class IPAdressGetter implements com.francetelecom.admindm.api.Getter {

	public Object get(String sessionId) {
		String url = HttpServer.getURL();
		// Log.debug("valeur url:"+url) ;
		// Log.debug("valeur IPAdress:"+url.split(":")[0].substring(2)) ;
		return url.split(":")[1].substring(2);
	}

}
