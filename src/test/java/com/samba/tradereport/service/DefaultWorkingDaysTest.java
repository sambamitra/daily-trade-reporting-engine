package com.samba.tradereport.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.DayOfWeek;
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
	public void nextWorkingDayShouldReturnCurrentDayWhenCurrentDayIsWorkDay() {
		// Given - current day is Monday
		final LocalDate expected = LocalDate.of(2018, 8, 13);

		// When
		final LocalDate actual = this.workingDays.getNextWorkingDay(expected);

		// Then
		assertNotNull("Next working day not expected to be null", actual);
		assertEquals("Next working day should be the same as the current day", expected, actual);
	}

	@Test
	public void nextWorkingDayShouldReturnNextDayWhenCurrentDayIsWeekend() {
		// Given - current day is Sunday
		final LocalDate expected = LocalDate.of(2018, 8, 12);

		// When
		final LocalDate actual = this.workingDays.getNextWorkingDay(expected);

		// Then - next working day should be Monday
		assertNotNull("Next working day not expected to be null", actual);
		assertThat("Next working day should be the day after the current day", actual.getDayOfWeek(),
				is(DayOfWeek.MONDAY));
	}

}
