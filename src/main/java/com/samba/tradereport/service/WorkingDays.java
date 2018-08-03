package com.samba.tradereport.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface for working days
 * 
 * @author samba.mitra
 *
 */
public interface WorkingDays {

	/**
	 * Returns the next working day
	 * 
	 * @param currentDate
	 * @return
	 */
	default LocalDate getNextWorkingDay(final LocalDate currentDate) {
		if (getWorkingDays().contains(currentDate.getDayOfWeek())) {
			return currentDate;
		}
		return getNextWorkingDay(currentDate.plusDays(1));
	}

	/**
	 * Gets all working days
	 * 
	 * @return
	 */
	List<DayOfWeek> getWorkingDays();

}
