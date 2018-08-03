package com.samba.tradereport.service;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class SpecialWorkingDays implements WorkingDays {

	/**
	 * Special working days - Friday/Saturday are off
	 */
	@Override
	public List<DayOfWeek> getWorkingDays() {
		List<DayOfWeek> workingDays = new ArrayList<>();
		workingDays.add(SUNDAY);
		workingDays.add(MONDAY);
		workingDays.add(TUESDAY);
		workingDays.add(WEDNESDAY);
		workingDays.add(THURSDAY);
		return workingDays;
	}

}
