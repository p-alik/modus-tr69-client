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

package com.francetelecom.tr157.implem.periodicstatistic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;

/**
 * @author mpcy8647
 * 
 */
public class SampleParameterTask implements Observer {

	/** The Constant CURRENT. */
	private static final String CURRENT = "Current";

	/** The Constant CHANGE. */
	private static final String CHANGE = "Change";

	/** The Constant LATEST. */
	private static final String LATEST = "Latest";

	/** The Constant MINIMUM. */
	private static final String MINIMUM = "Minimum";

	/** The Constant MAXIMUM. */
	private static final String MAXIMUM = "Maximum";

	/** The Constant AVERAGE. */
	private static final String AVERAGE = "Average";

	/** The base path. */
	private final String basePath;

	/** The parameter data. */
	private final IParameterData parameterData;

	/** The observed parameter. */
	private final Parameter observedParameter;

	/** The calculation mode parameter. */
	private final Parameter calculationModeParameter;

	/** The sample mode parameter. */
	private final Parameter sampleModeParameter;

	/** The values. */
	private final List values;

	/**
	 * Instantiates a new sample parameter task.
	 * 
	 * @param pParameterData
	 *            the parameter data
	 * @param pBasePath
	 *            the base path
	 */
	public SampleParameterTask(final IParameterData pParameterData, final String pBasePath) {
		this.basePath = pBasePath;
		this.parameterData = pParameterData;
		this.values = new ArrayList();

		this.calculationModeParameter = this.parameterData.getParameter(this.basePath + "CalculationMode");
		this.sampleModeParameter = this.parameterData.getParameter(this.basePath + "SampleMode");

		Parameter referenceParameter = this.parameterData.getParameter(this.basePath + "Reference");
		String referenceValue = (String) referenceParameter.getValue();
		this.observedParameter = this.parameterData.getParameter(referenceValue);

	}

	/**
	 * Start.
	 */
	public void start() {
		// add observer
		String sampleMode = CURRENT;
		if (this.sampleModeParameter != null) {
			sampleMode = (String) this.sampleModeParameter.getValue();
		}

		if ((this.observedParameter != null) && (CHANGE.equals(sampleMode))) {
			this.observedParameter.addObserver(this);
		}

	}

	/**
	 * Stop.
	 */
	public void stop() {
		if (this.observedParameter != null) {
			// remove observer
			this.observedParameter.deleteObserver(this);
		}
	}

	/**
	 * <p>
	 * Gets the value.
	 * </p>
	 * <p>
	 * Two cases:
	 * <ul>
	 * <li>the values list is empty, the current value of the observed parameter
	 * is returned.</li>
	 * <li>the values list isn't empty, then the method returns the computed
	 * value according to the value of the CalculationMode parameter.</li>
	 * </ul>
	 * </p>
	 * 
	 * @return the value
	 */
	public Object getValue() {
		if (this.values.isEmpty()) {
			return this.observedParameter.getValue();
		} else {
			// depending on the value of CalculationMode
			String calculationMode = (String) this.calculationModeParameter.getValue();
			if (LATEST.equals(calculationMode)) {
				return this.values.get(this.values.size() - 1);
			} else if (MINIMUM.equals(calculationMode)) {
				return Collections.min(this.values);
			} else if (MAXIMUM.equals(calculationMode)) {
				return Collections.max(this.values);
			} else if (AVERAGE.equals(calculationMode)) {
				Class objectClass = this.values.get(0).getClass();
				if (Integer.class.equals(objectClass)) {
					int average = 0;
					for (Iterator it = this.values.iterator(); it.hasNext();) {
						average = average + ((Integer) it.next()).intValue();
					}
					return Integer.valueOf(String.valueOf(average / this.values.size()));
				} else if (Long.class.equals(objectClass)) {
					long average = 0;
					for (Iterator it = this.values.iterator(); it.hasNext();) {
						average = average + ((Long) it.next()).longValue();
					}
					return new Long(average / this.values.size());
				} else if (Float.class.equals(objectClass)) {
					float average = 0;
					for (Iterator it = this.values.iterator(); it.hasNext();) {
						average = average + ((Float) it.next()).floatValue();
					}
					return new Float(average / this.values.size());
				} else if (Double.class.equals(objectClass)) {
					double average = 0;
					for (Iterator it = this.values.iterator(); it.hasNext();) {
						average = average + ((Double) it.next()).doubleValue();
					}
					return new Double(average / this.values.size());
				}

			}
		}
		return null;
	}

	/**
	 * <p>
	 * Update.
	 * </p>
	 * <p>
	 * The new value is added in the values list.
	 * </p>
	 * 
	 * @param parameter
	 *            the parameter
	 * @param value
	 *            the value
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(final Observable observable, final Object value) {
		Parameter param = (Parameter) observable;
		this.values.add(param.getValue());
	}

	public String getBasePath() {
		return this.basePath;
	}

}
