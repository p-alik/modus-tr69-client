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

public class SampleTaskTest extends TestCase {

	/** The Constant ROOT. */
	public static final String ROOT = "Device.";

	/** The parameter data. */
	private IParameterData parameterData;

	/** The observed parameter. */
	private com.francetelecom.admindm.model.Parameter observedParameter;

	/** The parameter. */
	private Parameter parameter;

	/** The sample set. */
	private SampleSetImpl sampleSet;

	/** The sample task. */
	private SampleTask sampleTask;

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

		new PeriodicStatisticsImpl(this.parameterData, this.parameterData.getRoot() + "PeriodicStatistics.")
				.initialize();
		this.sampleSet = new SampleSetImpl(this.parameterData, this.parameterData.getRoot()
				+ "PeriodicStatistics.SampleSet.1.");
		this.sampleSet.initialize();
		this.parameter = this.sampleSet.addParameter();
		this.parameter.getParamReference().setValue(this.observedParameter.getName());
	}

	/**
	 * Test create.
	 */
	public void testCreate() {
		this.sampleTask = new SampleTask(this.parameterData, this.sampleSet.getBasePath(), 1);

		this.sampleTask.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		this.sampleTask.stop();
		assertTrue("1".equals(this.sampleSet.getParamSampleSeconds().getValue()));
		assertTrue("10".equals(this.parameter.getParamValues().getValue()));
		assertTrue("1".equals(this.parameter.getParamSampleSeconds().getValue()));
		assertTrue(!"".equals(this.sampleSet.getParamReportStartTime().getValue()));
		assertTrue(!"".equals(this.sampleSet.getParamReportEndTime().getValue()));
	}

	/**
	 * Test create_ with2 parameters.
	 */
	public void testCreate_With2Parameters() {
		com.francetelecom.admindm.model.Parameter observedParameter2 = null;
		try {
			observedParameter2 = this.parameterData.createOrRetrieveParameter("Device.Test2");
			observedParameter2.setType(ParameterType.INT);
			observedParameter2.setValue(Integer.valueOf("5"));
		} catch (Fault e) {
			e.printStackTrace();
		}

		Parameter parameter2 = this.sampleSet.addParameter();
		try {
			parameter2.getParamReference().setValue(observedParameter2.getName());
		} catch (Fault e) {
			e.printStackTrace();
		}

		this.sampleTask = new SampleTask(this.parameterData, this.sampleSet.getBasePath(), 1);

		this.sampleTask.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		this.sampleTask.stop();
		assertTrue("1".equals(this.sampleSet.getParamSampleSeconds().getValue()));
		assertTrue("10".equals(this.parameter.getParamValues().getValue()));
		assertTrue("1".equals(this.parameter.getParamSampleSeconds().getValue()));
		assertTrue("5".equals(parameter2.getParamValues().getValue()));
		assertTrue("1".equals(parameter2.getParamSampleSeconds().getValue()));
		assertTrue(!"".equals(this.sampleSet.getParamReportStartTime().getValue()));
		assertTrue(!"".equals(this.sampleSet.getParamReportEndTime().getValue()));

	}

	public void testCreate_Threshold() {
		try {
			this.parameter.getParamLowThreshold().setValue(Integer.valueOf("4"));
			this.parameter.getParamHighThreshold().setValue(Integer.valueOf("6"));
			this.parameter.getParamSampleMode().setValue("Change");

		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.sampleTask = new SampleTask(this.parameterData, this.sampleSet.getBasePath(), 1);

		this.sampleTask.start();
		try {
			this.observedParameter.setValue(Integer.valueOf("4"));
			this.observedParameter.setValue(Integer.valueOf("5"));
			this.observedParameter.setValue(Integer.valueOf("4"));
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		this.sampleTask.stop();
		assertTrue("1".equals(this.sampleSet.getParamSampleSeconds().getValue()));
		assertTrue("4".equals(this.parameter.getParamValues().getValue()));
		assertTrue("1".equals(this.parameter.getParamSampleSeconds().getValue()));
		assertTrue(!"".equals(this.sampleSet.getParamReportStartTime().getValue()));
		assertTrue(!"".equals(this.sampleSet.getParamReportEndTime().getValue()));
		assertTrue(new Long(1).equals(this.parameter.getParamFailures().getValue()));
	}

}
