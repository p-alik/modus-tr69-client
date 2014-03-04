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

package com.francetelecom.admindm.admincpe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.francetelecom.admindm.api.ICom;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethodMngService;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
import com.francetelecom.admindm.soap.SetParamValuesFault;

/**
 * @author umdo6622
 * 
 */
public class AdminCpe implements IAdminCpe {

	/** The rpc mng. */
	private RPCMethodMngService rpcMng;
	/** The rpc mng. */
	private IParameterData pmDataSvc;

	public AdminCpe(final RPCMethodMngService rpcMng, final IParameterData pmDataSvc) {
		// TODO Auto-generated constructor stub
		this.pmDataSvc = pmDataSvc;
		this.rpcMng = rpcMng;
		Log.debug("AdminCpe(pmDataSvc: " + this.pmDataSvc + ", rpcMng: " + this.rpcMng + ")");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.francetelecom.admindm.admincpe.IAdminCpe#addEvent(com.francetelecom .admindm.api.EventCode)
	 */
	public void addEvent(final String myEventCode) {
		// TODO Auto-generated method stub
		EventStruct myEvent = new EventStruct(myEventCode, "Test Admin");
		this.pmDataSvc.addEvent(myEvent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.francetelecom.admindm.admincpe.IAdminCpe#updateValue(java.lang.String , java.lang.String)
	 */
	public void updateValue(final String myParameterName, final String myValue) throws AdminCpeException {
		// TODO Auto-generated method stub
		List lsFault = new ArrayList();
		IParameterData parameterData = this.pmDataSvc;

		try {

			Parameter param = parameterData.getParameter(myParameterName);
			// unable to find the parameter
			if (param == null) {
				StringBuffer error;
				error = new StringBuffer(FaultUtil.STR_FAULT_9005);
				error.append(": Unable to find this parameter " + "in the data model");
				throw new SetParamValuesFault(myParameterName, FaultUtil.FAULT_9005, error.toString());
			}
			checkIfSetIsPossible(param);
			checkIfSetIsApplicable(param, myValue);
		} catch (SetParamValuesFault fault) {
			lsFault.add(fault);
		}

		if (!lsFault.isEmpty()) {
			// create a global fault with all detected fault
			throw new AdminCpeException("Multiple Fautes" + lsFault.get(0).toString());
		}
		boolean isAllApplied = true;
		String value;
		Parameter param;

		value = myValue;
		param = parameterData.getParameter(myParameterName);
		try {
			param.setValue(Parameter.getValue(param.getName(), value, param.getType()));
		} catch (Fault e) {
			StringBuffer error;
			error = new StringBuffer(FaultUtil.STR_FAULT_9003);
			error.append(": Unable to find this parameter " + "in the data model");
			lsFault.add(new SetParamValuesFault(param.getName(), e.getFaultcode(), e.getFaultstring()));
		}
		param.notifyObservers("AdminCpe");
		isAllApplied &= param.isImmediateChanges();
		Log.debug("isAllApplied: " + isAllApplied);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.francetelecom.admindm.admincpe.IAdminCpe#getValue(java.lang.String)
	 */
	public String getValue(final String myParameter) throws AdminCpeException {
		// TODO Auto-generated method stub
		Parameter param = this.pmDataSvc.getParameter(myParameter);
		return (String) param.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.francetelecom.admindm.admincpe.IAdminCpe#rebootCpe()
	 */
	public void rebootCpe() throws AdminCpeException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.francetelecom.admindm.admincpe.IAdminCpe#forceInform()
	 */
	public void forceInform() throws AdminCpeException {
		// TODO Auto-generated method stub
		ICom com = this.pmDataSvc.getCom();
		com.requestNewSession();
	}

	/**
	 * Check if set is possible.
	 * 
	 * @param param
	 *            the parameter
	 * @throws SetParamValuesFault
	 *             the set parameter values fault
	 */
	private void checkIfSetIsPossible(final Parameter param) throws SetParamValuesFault {
		if (!param.isWritable()) {
			throw new SetParamValuesFault(param.getName(), FaultUtil.FAULT_9008, FaultUtil.STR_FAULT_9008);
		}
	}

	/**
	 * Check if the parameter is compliance with the request.
	 * 
	 * @param param
	 *            parameter
	 * @param value
	 *            new value
	 * @throws SetParamValuesFault
	 *             in case of error.
	 */
	private void checkIfSetIsApplicable(final Parameter param, final String value) throws SetParamValuesFault {
		try {
			param.check(value);
		} catch (Fault exp) {
			throw new SetParamValuesFault(param.getName(), exp.getFaultcode(), exp.getFaultstring(), exp);
		}
	}

	public void addNotification(final String Param, final int level) throws AdminCpeException {
		try {
			Parameter paramToModify = this.pmDataSvc.createOrRetrieveParameter(Param);
			paramToModify.setNotification(level);
		} catch (Fault exp) {
			throw new AdminCpeException("Erreur Notification " + Param + exp.getFaultstring(), exp.getStackTrace());
		}
	}

	public int getNotification(final String Param) throws AdminCpeException {
		try {
			Parameter paramToQuery = this.pmDataSvc.createOrRetrieveParameter(Param);
			return paramToQuery.getNotification();
		} catch (Fault exp) {
			throw new AdminCpeException("Erreur Notification " + Param + exp.getFaultstring(), exp.getStackTrace());
		}
	}

	public void forceTransfer(final String monFichier) throws AdminCpeException {
		// TODO Auto-generated method stub

	}

	public void addParameter(final String parent, final String paramName, final int type, final int storageMode,
			final boolean isWritable, final int notification, final boolean setActNotDen) throws AdminCpeException {
		try {
			Parameter servicesToInstall = this.pmDataSvc.createOrRetrieveParameter(parent + paramName);
			servicesToInstall.setType(type);
			switch (type) {
			case ParameterType.STRING:
				servicesToInstall.setValue("");
				break;
			default:
			}
			servicesToInstall.setStorageMode(storageMode);
			servicesToInstall.setWritable(isWritable);
			servicesToInstall.setNotification(notification);
			servicesToInstall.setActiveNotificationDenied(setActNotDen);
		} catch (Fault exp) {
			throw new AdminCpeException("Erreur AddParameter " + parent + paramName + " " + exp.getFaultstring(),
					exp.getStackTrace());
		}

	}

	public void resetFactory() throws AdminCpeException {

	}

	public void sendConnectionRequest() throws AdminCpeException {
		try {
			/* recuperation de la valeur de la connexionRequestURL */
			String racine = this.pmDataSvc.getRoot();
			String cnxUrl = (String) this.pmDataSvc.getParameter(racine + "ManagementServer.ConnectionRequestURL")
					.getValue();

			/* envoi de la requete de connextion Request au device */
			URL groupURL = new URL(cnxUrl);
			Log.debug("Envoi requete GET");
			HttpURLConnection cnx = (HttpURLConnection) groupURL.openConnection();
			cnx.setRequestMethod("GET");

			InputStreamReader myInput = new InputStreamReader(cnx.getInputStream());
			BufferedReader inGroup = new BufferedReader(myInput);
			String inputLine;
			while ((inputLine = inGroup.readLine()) != null)
				Log.debug("reponse CPE : " + inputLine);
			inGroup.close();
		} catch (Exception exp) {
			throw new AdminCpeException("Erreur sendConnectionRequest " + exp.getMessage(), exp.getStackTrace());
		}
	}

	public void loadModelFromFile(final String myFileName) throws AdminCpeException {
		// TODO Auto-generated method stub

	}

	public void stopCpe() throws AdminCpeException {
		// TODO Auto-generated method stub
		System.exit(-1);

	}

	public void startCpe() throws AdminCpeException {
		// TODO Auto-generated method stub

	}

}
