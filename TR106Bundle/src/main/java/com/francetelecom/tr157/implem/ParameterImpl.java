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
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.Parameter;

/**
 * @author mpcy8647
 * 
 */
public class ParameterImpl extends Parameter implements Observer {

	/** The Constant IN_RANGE. */
	private static final int IN_RANGE = 0;

	/** The Constant OUT_OF_RANGE. */
	private static final int OUT_OF_RANGE = 1;

	/** The Constant OUT_OF_RANGE_LOW. */
	private static final int OUT_OF_RANGE_LOW = 2;

	/** The Constant OUT_OF_RANGE_HIGH. */
	private static final int OUT_OF_RANGE_HIGH = 3;

	/** The observed parameter. */
	private com.francetelecom.admindm.model.Parameter observedParameter = null;

	public ParameterImpl(final IParameterData pData, final String pBasePath) {
		super(pData, pBasePath);

	}

	public void initialize() throws Fault {
		super.initialize();

		getParamCalculationMode().setValue("Latest");
		getParamSampleMode().setValue("Current");
		getParamValues().addObserver(this);
	}

	public void update(final Observable arg0, final Object arg1) {
		if (arg0.equals(getParamValues())) {
			processThreshold();
		}

	}

	private com.francetelecom.admindm.model.Parameter getObserverParameter() {
		if ((!"".equals(getParamReference().getValue()) && (this.observedParameter == null))) {
			this.observedParameter = getData().getParameter((String) getParamReference().getValue());
		}

		return this.observedParameter;
	}

	private void processThreshold() {
		if (isThresholdApplicable()) {
			// check the last value
			String value = (String) getParamValues().getValue();
			String[] splittedString = value.split(";");
			String lastValue = null;
			String previousLastValue = null;
			if (splittedString != null) {
				if (splittedString.length > 0) {
					lastValue = splittedString[splittedString.length - 1];
				}
				if (splittedString.length > 1) {
					previousLastValue = splittedString[splittedString.length - 2];
				}
			}
			if (!checkThreshold(lastValue, previousLastValue)) {
				long failure = ((Long) getParamFailures().getValue()).longValue();
				failure++;
				try {
					getParamFailures().setValue(new Long(failure));
				} catch (Fault e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	private boolean isThresholdApplicable() {
		if (getParamLowThreshold().getValue().equals(getParamHighThreshold().getValue())) {
			return false;
		}

		if (getObserverParameter() == null) {
			return false;
		}
		switch (getObserverParameter().getType()) {
		case ParameterType.INT:
		case ParameterType.UINT:
		case ParameterType.BASE64:
		case ParameterType.LONG:
			return true;
		default:
			return false;
		}

	}

	/**
	 * Check threslhold.
	 * 
	 * @return true, if the value isn't is a suspect value (that is to say that
	 *         this value is greater than the high threshold or is less than the
	 *         low threshold).
	 */
	private boolean checkThreshold(final String lastValue, final String previousLastValue) {
		if (lastValue != null) {
			int lastValueState = -1;
			int previousLastValueState = -1;
			switch (this.observedParameter.getType()) {
			case ParameterType.INT:
				Integer lastInt = Integer.valueOf(lastValue);
				Integer previouslastInt = null;
				if (previousLastValue != null) {
					previouslastInt = Integer.valueOf(previousLastValue);
				}
				Integer lowThreshold = (Integer) getParamLowThreshold().getValue();
				Integer highThreshold = (Integer) getParamHighThreshold().getValue();

				// compute state of the last value
				if ((lastInt.compareTo(lowThreshold) > 0) && (lastInt.compareTo(highThreshold) < 0)) {
					lastValueState = IN_RANGE;
				} else if (lastInt.compareTo(lowThreshold) <= 0) {
					lastValueState = OUT_OF_RANGE_LOW;
				} else if (lastInt.compareTo(highThreshold) >= 0) {
					lastValueState = OUT_OF_RANGE_HIGH;
				}

				// compute the state of the previous last value
				if (previouslastInt != null) {
					if ((previouslastInt.compareTo(lowThreshold) > 0) && (previouslastInt.compareTo(highThreshold) < 0)) {
						previousLastValueState = IN_RANGE;
					} else if (previouslastInt.compareTo(lowThreshold) <= 0) {
						previousLastValueState = OUT_OF_RANGE_LOW;
					} else if (previouslastInt.compareTo(highThreshold) >= 0) {
						previousLastValueState = OUT_OF_RANGE_HIGH;
					}
				} else {
					previousLastValueState = IN_RANGE;
				}
				break;
			case ParameterType.UINT:
			case ParameterType.LONG:
				Long lastLong = new Long(lastValue);
				Long previouslastLong = null;
				if (previousLastValue != null) {
					previouslastLong = new Long(previousLastValue);
				}
				Long lowThresholdLong = (Long) getParamLowThreshold().getValue();
				Long highThresholdLong = (Long) getParamHighThreshold().getValue();

				// compute state of the last value
				if ((lastLong.compareTo(lowThresholdLong) > 0) && (lastLong.compareTo(highThresholdLong) < 0)) {
					lastValueState = IN_RANGE;
				} else if (lastLong.compareTo(lowThresholdLong) <= 0) {
					lastValueState = OUT_OF_RANGE_LOW;
				} else if (lastLong.compareTo(highThresholdLong) >= 0) {
					lastValueState = OUT_OF_RANGE_HIGH;
				}

				// compute the state of the previous last value
				if (previouslastLong != null) {
					if ((previouslastLong.compareTo(lowThresholdLong) > 0)
							&& (previouslastLong.compareTo(highThresholdLong) < 0)) {
						previousLastValueState = IN_RANGE;
					} else if (previouslastLong.compareTo(lowThresholdLong) <= 0) {
						previousLastValueState = OUT_OF_RANGE_LOW;
					} else if (previouslastLong.compareTo(highThresholdLong) >= 0) {
						previousLastValueState = OUT_OF_RANGE_HIGH;
					}
				} else {
					previousLastValueState = IN_RANGE;
				}

				break;
			case ParameterType.BASE64:
				break;

			}

			if ((previousLastValueState == IN_RANGE) && (lastValueState > previousLastValueState)) {
				return false;
			}
			if ((previousLastValueState > OUT_OF_RANGE) && (lastValueState > OUT_OF_RANGE)
					&& (previousLastValueState != lastValueState)) {
				return false;
			}
		}

		return true;
	}
}
