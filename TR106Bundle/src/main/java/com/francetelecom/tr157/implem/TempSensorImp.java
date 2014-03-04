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

package com.francetelecom.tr157.implem;

import java.util.Observable;
import java.util.Observer;

import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.TemperatureSensor;

public class TempSensorImp extends TemperatureSensor implements Observer {
	static final int UNDEFINED_TEMP = -274;

	public TempSensorImp(final String name, final IParameterData data, final String basePath) {
		super(data, basePath);
	}

	public void initialize() throws Fault {
		super.initialize();
		getParamReset().addObserver(this);
		getParamValue().addObserver(this);
		getParamStatus().addObserver(this);
	}

	public void update(final Observable parameter, final Object arg) {
		if (parameter == getParamValue()) {
			onValueChange((Parameter) parameter);
		} else if (parameter == getParamReset()) {
			onReset((Parameter) parameter);
		} else if (parameter == getParamStatus()) {
			onStatusChange((Parameter) parameter);
		}
	}

	private void onReset(final Parameter parameter) {
		if (Boolean.TRUE.equals(parameter.getValue())) {
			long time = System.currentTimeMillis();
			getParamLowAlarmTime().setDirectValue(Integer.valueOf("-257"));
			getParamHighAlarmTime().setDirectValue(Integer.valueOf("-257"));
			getParamResetTime().setDirectValue(new Long(time));
			parameter.setDirectValue(Boolean.FALSE);
		}
	}

	private void onValueChange(final Parameter parameter) {
		int value = ((Integer) parameter.getValue()).intValue();
		int lowAlarmValue = ((Integer) getParamLowAlarmValue().getValue()).intValue();
		int highAlarmValue = ((Integer) getParamHighAlarmValue().getValue()).intValue();
		int minValue = ((Integer) getParamMinValue().getValue()).intValue();
		int maxValue = ((Integer) getParamMaxValue().getValue()).intValue();
		Long zero = new Long(0);
		Long time = new Long(System.currentTimeMillis());
		getParamLastUpdate().setDirectValue(time);
		//
		if (value < minValue) {
			getParamMinValue().setDirectValue(Integer.valueOf(String.valueOf(value)));
			getParamMinTime().setDirectValue(time);
		}
		//
		if (value > maxValue) {
			getParamMaxValue().setDirectValue(Integer.valueOf(String.valueOf(value)));
			getParamMaxTime().setDirectValue(time);
		}
		//
		if ((highAlarmValue != UNDEFINED_TEMP) && (value > highAlarmValue)
				&& zero.equals(getParamHighAlarmTime().getValue())) {
			getParamHighAlarmTime().setDirectValue(time);
		}
		//
		if ((lowAlarmValue != UNDEFINED_TEMP) && (value < lowAlarmValue)
				&& zero.equals(getParamLowAlarmTime().getValue())) {
			getParamLowAlarmTime().setDirectValue(time);
		}
		//
		if ((highAlarmValue != UNDEFINED_TEMP) && (value > highAlarmValue)
				&& zero.equals(getParamHighAlarmTime().getValue())) {
			getParamHighAlarmTime().setDirectValue(time);
		}
	}

	private void onStatusChange(final Parameter parameter) {
		long time = System.currentTimeMillis();
		getParamResetTime().setDirectValue(new Long(time));
		parameter.setDirectValue(Boolean.FALSE);
	}
}
