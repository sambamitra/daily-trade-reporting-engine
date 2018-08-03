package com.samba.tradereport.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.samba.tradereport.TestFixture;

public class ResportingServiceTest {

	private ReportingService service;

	@Before
	public void setup() {
		this.service = new ReportingService();
	}

	@Test
	public void testGenerateDailyReport() {
		final String report = this.service.generateDailyReport(TestFixture.createInstructions());
		assertNotNull("Report not expected to be null", report);
		assertTrue("Report expected to contain daily incoming report", report.contains("Daily Incoming Report"));
	}

}
