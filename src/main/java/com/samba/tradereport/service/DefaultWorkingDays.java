package com.samba.tradereport.service;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class DefaultWorkingDays implements WorkingDays {

	@Override
	public List<DayOfWeek> getWorkingDays() {
		List<DayOfWeek> workingDays = new ArrayList<>();
		workingDays.add(MONDAY);
		workingDays.add(TUESDAY);
		workingDays.add(WEDNESDAY);
		workingDays.add(THURSDAY);
		workingDays.add(FRIDAY);
		return workingDays;
	}

}
