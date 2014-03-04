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

package com.francetelecom.tr157.implem.periodicstatistic;

import junit.framework.TestCase;

import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.Parameter;
import com.francetelecom.tr157.implem.PeriodicStatisticsImpl;
import com.francetelecom.tr157.implem.SampleSetImpl;

public class SampleParameterTaskTest extends TestCase {

	/** The Constant ROOT. */
	public static final String ROOT = "Device.";

	/** The parameter data. */
	private IParameterData parameterData;

	/** The sample parameter task. */
	private SampleParameterTask sampleParameterTask;

	/** The observed parameter. */
	private com.francetelecom.admindm.model.Parameter observedParameter;

	/** The parameter. */
	private Parameter parameter;

	/**
	 * Sets the up.
	 * 
	 * @throws Exception
	 *             the exception
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		this.parameterData = new ParameterData();
		this.parameterData.setRoot(ROOT);
		this.observedParameter = this.parameterData.createOrRetrieveParameter("Device.Test");
		this.observedParameter.setType(ParameterType.INT);
		this.observedParameter.setValue(Integer.valueOf("10"));
		System.out.println("observed parameter = " + this.observedParameter.getName());

		new PeriodicStatisticsImpl(this.parameterData, this.parameterData.getRoot() + "PeriodicStatistics.")
				.initialize();
		SampleSetImpl sampleSet = new SampleSetImpl(this.parameterData, this.parameterData.getRoot()
				+ "PeriodicStatistics.SampleSet.1.");
		sampleSet.initialize();
		this.parameter = sampleSet.addParameter();
		// parameter = new ParameterImpl(parameterData, parameterData.getRoot()
		// + "PeriodicStatistics.SampleSet.1.Parameter.1.");
		// parameter.initialize();
		System.out.println(this.parameter.getParamReference());
		this.parameter.getParamReference().setValue(this.observedParameter.getName());

	}

	/**
	 * Test create_ simple.
	 */
	public void testCreate_Simple_Current_Latest() {
		this.sampleParameterTask = new SampleParameterTask(this.parameterData, this.parameterData.getRoot()
				+ "PeriodicStatistics.SampleSet.1.Parameter.1.");
		assertTrue(this.parameter.getParamFailures().getValue().equals(new Long(0)));
		this.sampleParameterTask.start();
		this.sampleParameterTask.stop();
		assertTrue(Integer.valueOf("10").equals(this.sampleParameterTask.getValue()));
		assertTrue(this.parameter.getParamFailures().getValue().equals(new Long(0)));
	}

	/**
	 * Test create_ advanced_ change_ maximum.
	 */
	public void testCreate_Advanced_Change_Maximum() {
		try {
			this.parameter.getParamSampleMode().setValue("Change");
			this.parameter.getParamCalculationMode().setValue("Maximum");
		} catch (Fault e) {
		}
		assertTrue(this.parameter.getParamFailures().getValue().equals(new Long(0)));
		this.sampleParameterTask = new SampleParameterTask(this.parameterData, this.parameterData.getRoot()
				+ "PeriodicStatistics.SampleSet.1.Parameter.1.");
		this.sampleParameterTask.start();

		try {
			this.observedParameter.setValue(Integer.valueOf("9"));
			this.observedParameter.setValue(Integer.valueOf("8"));
			this.observedParameter.setValue(Integer.valueOf("7"));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.sampleParameterTask.stop();
		assertTrue(Integer.valueOf("9").equals(this.sampleParameterTask.getValue()));
		assertTrue(this.parameter.getParamFailures().getValue().equals(Long.valueOf("0")));
		System.out.println(this.sampleParameterTask.getValue());
	}

	public void testCreate_Current_Maximum() {
		try {
			this.parameter.getParamSampleMode().setValue("Current");
			this.parameter.getParamCalculationMode().setValue("Maximum");
		} catch (Fault e) {
		}
		this.sampleParameterTask = new SampleParameterTask(this.parameterData, this.parameterData.getRoot()
				+ "PeriodicStatistics.SampleSet.1.Parameter.1.");
		this.sampleParameterTask.start();

		try {
			this.observedParameter.setValue(Integer.valueOf("9"));
			this.observedParameter.setValue(Integer.valueOf("8"));
			this.observedParameter.setValue(Integer.valueOf("7"));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.sampleParameterTask.stop();
		assertTrue(Integer.valueOf("7").equals(this.sampleParameterTask.getValue()));
		System.out.println(this.sampleParameterTask.getValue());
	}

	/**
	 * Test create_ advanced_ change_ minimum.
	 */
	public void testCreate_Advanced_Change_Minimum() {
		try {
			this.parameter.getParamSampleMode().setValue("Change");
			this.parameter.getParamCalculationMode().setValue("Minimum");
		} catch (Fault e) {
		}
		this.sampleParameterTask = new SampleParameterTask(this.parameterData, this.parameterData.getRoot()
				+ "PeriodicStatistics.SampleSet.1.Parameter.1.");
		this.sampleParameterTask.start();

		try {
			this.observedParameter.setValue(Integer.valueOf("9"));
			this.observedParameter.setValue(Integer.valueOf("8"));
			this.observedParameter.setValue(Integer.valueOf("7"));
			this.observedParameter.setValue(Integer.valueOf("9"));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.sampleParameterTask.stop();
		assertTrue(Integer.valueOf("7").equals(this.sampleParameterTask.getValue()));
		System.out.println(this.sampleParameterTask.getValue());
	}

	/**
	 * Test create_ advanced_ change_ average.
	 */
	public void testCreate_Advanced_Change_Average() {
		try {
			this.parameter.getParamSampleMode().setValue("Change");
			this.parameter.getParamCalculationMode().setValue("Average");
		} catch (Fault e) {
		}
		this.sampleParameterTask = new SampleParameterTask(this.parameterData, this.parameterData.getRoot()
				+ "PeriodicStatistics.SampleSet.1.Parameter.1.");
		this.sampleParameterTask.start();

		try {
			this.observedParameter.setValue(Integer.valueOf("2"));
			this.observedParameter.setValue(Integer.valueOf("6"));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.sampleParameterTask.stop();
		assertTrue(Integer.valueOf("4").equals(this.sampleParameterTask.getValue()));
		System.out.println(this.sampleParameterTask.getValue());
	}

}
