package com.samba.tradereport.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public interface WorkingDays {

	default LocalDate getNextWorkingDay(final LocalDate currentDate) {
		if (getWorkingDays().contains(currentDate.getDayOfWeek())) {
			return currentDate;
		}
		return getNextWorkingDay(currentDate.plusDays(1));
	}

	List<DayOfWeek> getWorkingDays();

}
