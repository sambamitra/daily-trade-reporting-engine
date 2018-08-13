package com.samba.tradereport.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class SpecialWorkingDaysTest {

	private WorkingDays workingDays;

	@Before
	public void setup() {
		this.workingDays = new SpecialWorkingDays();
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
		// Given - current day is Friday
		final LocalDate expected = LocalDate.of(2018, 8, 10);

		// When
		final LocalDate actual = this.workingDays.getNextWorkingDay(expected);

		// Then - next working day should be Sunday
		assertNotNull("Next working day not expected to be null", actual);
		assertThat("Next working day should be the day after the current day", actual.getDayOfWeek(),
				is(DayOfWeek.SUNDAY));
	}

}
