package com.samba.tradereport.service;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class DefaultWorkingDaysTest {

	private WorkingDays workingDays;

	@Before
	public void setup() {
		this.workingDays = new DefaultWorkingDays();
	}

	@Test
	public void testGetNextWorkingDay() {
		final LocalDate nextWorkingDay = this.workingDays.getNextWorkingDay(LocalDate.now());
		assertNotNull("Next working day not expected to be null", nextWorkingDay);
	}

	@Test
	public void testGetNextWorkingDayForWeekend() {
		final LocalDate nextWorkingDay = this.workingDays.getNextWorkingDay(LocalDate.of(2018, 8, 4));
		assertNotNull("Next working day not expected to be null", nextWorkingDay);
	}

}
