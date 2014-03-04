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

import java.util.Observable;
import java.util.Observer;

import junit.framework.TestCase;

import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr157.gen.Parameter;
import com.francetelecom.tr157.implem.PeriodicStatisticsImpl;
import com.francetelecom.tr157.implem.SampleSetImpl;

public class SampleSchedulerTest extends TestCase implements Observer {

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
	private SampleScheduler sampleScheduler;

	/** The count. */
	private int count = 0;

	protected void setUp() throws Exception {
		super.setUp();

		this.count = 0;
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
		this.sampleSet.getParamSampleInterval().setValue(new Long(1));
		this.sampleSet.getParamReportSamples().setValue(new Long(10));

		this.parameter = this.sampleSet.addParameter();
		this.parameter.getParamReference().setValue(this.observedParameter.getName());
	}

	public void testCreate() {
		this.sampleScheduler = new SampleScheduler(this.parameterData, this.sampleSet.getBasePath());
		this.sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(this.sampleSet.getParamStatus().getValue()));

		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Disabled".equals(this.sampleSet.getParamStatus().getValue()));
		assertTrue("10;10;10;10;10;10;10;10;10;10".equals(this.parameter.getParamValues().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(this.parameter.getParamSampleSeconds().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(this.sampleSet.getParamSampleSeconds().getValue()));

	}

	public void testCreate_ForceSample() {
		try {
			this.sampleSet.getParamReportSamples().setValue(new Long(1));
			this.sampleSet.getParamSampleInterval().setValue(new Long(10));
		} catch (Fault e2) {
			e2.printStackTrace();
		}

		this.sampleScheduler = new SampleScheduler(this.parameterData, this.sampleSet.getBasePath());
		this.sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Enabled".equals(this.sampleSet.getParamStatus().getValue()));

		try {
			this.observedParameter.setValue(Integer.valueOf("0"));
			this.sampleSet.getParamForceSample().setValue(Boolean.TRUE);

		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("0".equals(this.parameter.getParamValues().getValue()));
		try {
			this.observedParameter.setValue(Integer.valueOf("10"));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Disabled".equals(this.sampleSet.getParamStatus().getValue()));
		assertTrue("10".equals(this.parameter.getParamValues().getValue()));
		assertTrue("10".equals(this.parameter.getParamSampleSeconds().getValue()));
		assertTrue("10".equals(this.sampleSet.getParamSampleSeconds().getValue()));

	}

	public void testCreate_Threshold_WithForceSample() {
		try {
			this.parameter.getParamLowThreshold().setValue(Integer.valueOf("4"));
			this.parameter.getParamHighThreshold().setValue(Integer.valueOf("6"));
			// parameter.getParamSampleMode().setValue("Change");

		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.sampleScheduler = new SampleScheduler(this.parameterData, this.sampleSet.getBasePath());
		this.sampleScheduler.start(-1);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		try {
			this.observedParameter.setValue(Integer.valueOf("4"));
			this.sampleSet.getParamForceSample().setValue(Boolean.TRUE);
			System.out.println(this.parameter.getParamValues().getValue());
			Thread.sleep(1000);

			this.observedParameter.setValue(Integer.valueOf("5"));
			this.sampleSet.getParamForceSample().setValue(Boolean.TRUE);
			System.out.println(this.parameter.getParamValues().getValue());
			Thread.sleep(1000);

			this.observedParameter.setValue(Integer.valueOf("4"));
			this.sampleSet.getParamForceSample().setValue(Boolean.TRUE);
			System.out.println(this.parameter.getParamValues().getValue());
			Thread.sleep(1000);
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
		}
		this.sampleScheduler.stop();
		// assertTrue("1".equals(sampleSet.getParamSampleSeconds().getValue()));
		// assertTrue("4".equals(parameter.getParamValues().getValue()));
		// assertTrue("1".equals(parameter.getParamSampleSeconds().getValue()));
		// assertTrue(!"".equals(sampleSet.getParamReportStartTime().getValue()));
		// assertTrue(!"".equals(sampleSet.getParamReportEndTime().getValue()));
		System.out.println(this.parameter.getParamFailures().getValue());
		System.out.println(this.parameter.getParamValues().getValue());
		assertTrue(new Long(3).equals(this.parameter.getParamFailures().getValue()));
	}

	/**
	 * Test create_ fetch sample_5.
	 */
	public void testCreate_FetchSample_5() {

		try {
			this.sampleSet.getParamFetchSamples().setValue(new Long(5));
			this.sampleSet.getParamStatus().addObserver(this);
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.sampleScheduler = new SampleScheduler(this.parameterData, this.sampleSet.getBasePath());
		this.sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(this.sampleSet.getParamStatus().getValue()));

		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Disabled".equals(this.sampleSet.getParamStatus().getValue()));
		assertTrue("10;10;10;10;10;10;10;10;10;10".equals(this.parameter.getParamValues().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(this.parameter.getParamSampleSeconds().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(this.sampleSet.getParamSampleSeconds().getValue()));
		assertTrue(this.count == 4);

	}

	/**
	 * Test create_ fetch sample_0.
	 */
	public void testCreate_FetchSample_0() {

		try {
			this.sampleSet.getParamFetchSamples().setValue(new Long(0));
			this.sampleSet.getParamStatus().addObserver(this);
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.sampleScheduler = new SampleScheduler(this.parameterData, this.sampleSet.getBasePath());
		this.sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(this.sampleSet.getParamStatus().getValue()));

		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Disabled".equals(this.sampleSet.getParamStatus().getValue()));
		assertTrue("10;10;10;10;10;10;10;10;10;10".equals(this.parameter.getParamValues().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(this.parameter.getParamSampleSeconds().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(this.sampleSet.getParamSampleSeconds().getValue()));
		assertTrue(this.count == 2);

	}

	/**
	 * Test create_ fetch sample_15.
	 */
	public void testCreate_FetchSample_15() {

		try {
			this.sampleSet.getParamFetchSamples().setValue(new Long(15));
			this.sampleSet.getParamStatus().addObserver(this);
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.sampleScheduler = new SampleScheduler(this.parameterData, this.sampleSet.getBasePath());
		this.sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(this.sampleSet.getParamStatus().getValue()));

		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Disabled".equals(this.sampleSet.getParamStatus().getValue()));
		assertTrue("10;10;10;10;10;10;10;10;10;10".equals(this.parameter.getParamValues().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(this.parameter.getParamSampleSeconds().getValue()));
		assertTrue("1;1;1;1;1;1;1;1;1;1".equals(this.sampleSet.getParamSampleSeconds().getValue()));
		assertTrue(this.count == 2);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(final Observable o, final Object arg) {
		com.francetelecom.admindm.model.Parameter parameter = (com.francetelecom.admindm.model.Parameter) o;
		System.out.println("parameter name = " + parameter.getName() + ", value = " + parameter.getValue());
		this.count++;

	}

	/**
	 * Test change sample interval.
	 */
	public void testChangeSampleInterval() {
		try {
			this.sampleSet.getParamReportSamples().setValue(new Long(5));
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.sampleScheduler = new SampleScheduler(this.parameterData, this.sampleSet.getBasePath());
		this.sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(this.sampleSet.getParamStatus().getValue()));

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue("Enabled".equals(this.sampleSet.getParamStatus().getValue()));
		System.out.println(this.parameter.getParamValues().getValue());

		try {
			this.sampleSet.getParamSampleInterval().setValue(new Long(2));
			this.observedParameter.setValue(Integer.valueOf("2"));
		} catch (Fault e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Status = " + this.sampleSet.getParamStatus().getValue());
		System.out.println("Values = " + this.parameter.getParamValues().getValue());

		assertTrue("Disabled".equals(this.sampleSet.getParamStatus().getValue()));
		assertTrue("2;2;2;2;2".equals(this.parameter.getParamValues().getValue()));
		assertTrue("2;2;2;2;2".equals(this.parameter.getParamSampleSeconds().getValue()));
		assertTrue("2;2;2;2;2".equals(this.sampleSet.getParamSampleSeconds().getValue()));

	}

	public void testChangeReportSample_GreaterValue() {
		try {
			this.sampleSet.getParamReportSamples().setValue(new Long(3));
			this.observedParameter.setValue(Integer.valueOf("2"));
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.sampleScheduler = new SampleScheduler(this.parameterData, this.sampleSet.getBasePath());
		this.sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(this.sampleSet.getParamStatus().getValue()));

		try {
			this.sampleSet.getParamReportSamples().setValue(new Long(5));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Disabled".equals(this.sampleSet.getParamStatus().getValue()));
		assertTrue("2;2;2;2;2".equals(this.parameter.getParamValues().getValue()));
		assertTrue("1;1;1;1;1".equals(this.parameter.getParamSampleSeconds().getValue()));
		assertTrue("1;1;1;1;1".equals(this.sampleSet.getParamSampleSeconds().getValue()));

	}

	public void testChangeReportSample_LessValue() {
		try {
			this.sampleSet.getParamReportSamples().setValue(new Long(5));
			this.observedParameter.setValue(Integer.valueOf("2"));
		} catch (Fault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.sampleScheduler = new SampleScheduler(this.parameterData, this.sampleSet.getBasePath());
		this.sampleScheduler.start(-1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Enabled".equals(this.sampleSet.getParamStatus().getValue()));

		try {
			this.sampleSet.getParamReportSamples().setValue(new Long(3));
		} catch (Fault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue("Disabled".equals(this.sampleSet.getParamStatus().getValue()));
		assertTrue("2;2;2".equals(this.parameter.getParamValues().getValue()));
		assertTrue("1;1;1".equals(this.parameter.getParamSampleSeconds().getValue()));
		assertTrue("1;1;1".equals(this.sampleSet.getParamSampleSeconds().getValue()));

	}

}
