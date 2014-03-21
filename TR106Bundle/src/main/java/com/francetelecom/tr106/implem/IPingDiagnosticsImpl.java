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
 */

package com.francetelecom.tr106.implem;

import java.util.Observable;
import java.util.Observer;

import org.apache.regexp.RE;

import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.GetterSystem;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr106.gen.IPPingDiagnostics;

/**
 * The Class IPingDiagnosticsImpl.
 */
public final class IPingDiagnosticsImpl extends IPPingDiagnostics implements Observer {
	
	/**
	 * LINE SEPARATOR REGEX
	 */
	private static final RE LINE_SEPARATOR_REGEX = new RE(System.getProperty("line.separator"));
	
	/**
	 * SPACE REGEX
	 */
	private static final RE SPACE_REGEX = new RE(" ");

	// The following attribute is not used.
	// /** The last session id. */
	// private String lastSessionId;

	/**
	 * Instantiates a new i ping diagnostics impl.
	 * 
	 * @param data
	 *            the data
	 * @param basePath
	 *            the base path
	 */
	public IPingDiagnosticsImpl(final IParameterData data, final String basePath) {
		super(data, basePath);
	}

	/**
	 * @see com.francetelecom.tr106.gen.IPPingDiagnostics#initialize()
	 */
	public void initialize() throws Fault {
		super.initialize();
		this.getter = new GetterSystem("ping", "", ParameterType.STRING);
		getParamDiagnosticsState().addObserver(this);
	}

	/** The result. */
	private String result;
	/** The getter. */
	private GetterSystem getter;

	/**
	 * Creates the cmd.
	 * 
	 * @param sessionId
	 *            the session id
	 * @return the string
	 */
	String createCmd(final String sessionId) {
		StringBuffer cmd = new StringBuffer("ping -c ");
		cmd.append(getParamNumberOfRepetitions().getTextValue(sessionId));
		cmd.append(" -w ");
		cmd.append(getParamTimeout().getTextValue(sessionId));
		cmd.append(" -s ");
		cmd.append(getParamDataBlockSize().getTextValue(sessionId));
		cmd.append(" ");
		cmd.append(getParamHost().getTextValue(sessionId));
		return cmd.toString();
	}

	/**
	 * Parses the result.
	 */
	protected void parseResult() {
		if (this.result != null) {
//			String[] tokens = this.result.split(System.getProperty("line.separator"));
			String[] tokens = LINE_SEPARATOR_REGEX.split(this.result);
			int nbtokens = tokens.length;
			String resume = (tokens[nbtokens - 2]);
			System.out.println("resume" + resume);
			System.out.println("avant derniere ligne" + tokens[nbtokens - 1]);
			// System.out.println("derni�re ligne"+tokens[nbtokens]);
			// TODO full parsingof result
//			String[] line1 = (tokens[nbtokens - 1]).split(" ");
			String[] line1 = SPACE_REGEX.split(tokens[nbtokens - 1]);
			getParamSuccessCount().setDirectValue(Integer.valueOf(line1[0]));
			if (line1.length > 10) {
				getParamFailureCount().setDirectValue(Integer.valueOf(line1[5].substring(1)));
			} else
				getParamFailureCount().setDirectValue(Integer.valueOf("0"));
			getParamMaximumResponseTime().setDirectValue(Integer.valueOf("0"));
			getParamMinimumResponseTime().setDirectValue(Integer.valueOf("0"));
			getParamAverageResponseTime().setDirectValue(Integer.valueOf("0"));

			/*
			 * else {
			 * getParamFailureCount().setDirectValue(Integer.valueOf("0"));
			 * line1 = (tokens[nbtokens-1]).split(" ") ; String[] temps =
			 * line1[3].split("/") ;
			 * getParamMaximumResponseTime().setDirectValue(new
			 * Integer(temps[2]));
			 * getParamMinimumResponseTime().setDirectValue(new
			 * Integer(temps[0]));
			 * getParamAverageResponseTime().setDirectValue(new
			 * Integer(temps[1])); }
			 */
		}
	}

	/**
	 * Update.
	 * 
	 * @param arg0
	 *            the arg0
	 * @param arg1
	 *            the arg1
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(final Observable arg0, final Object arg1) {
		if (arg0 == getParamDiagnosticsState() && "Requested".equals(getParamDiagnosticsState().getTextValue(""))) {
			this.getter.setCmd(createCmd(""));
			this.result = (String) this.getter.get("");
			parseResult();
			getParamDiagnosticsState().setDirectValue("Complete");
			this.getData().addEvent(new EventStruct(EventCode.DIAGNOSTICS_COMPLETE, ""));
			this.getData().getCom().requestNewSession();
		}
	}
}
