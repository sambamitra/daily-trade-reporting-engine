package com.samba.tradereport.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.samba.tradereport.model.Instruction;

public class ReportingService {

	private static final String NEW_LINE = "\n";

	private static LocalDate getActualSettlementDate(final Instruction instruction) {
		final WorkingDays workDays = getWorkDays(instruction);
		final List<DayOfWeek> workingDays = workDays.getWorkingDays();
		final LocalDate settlementDate = instruction.getSettlementDate();
		final DayOfWeek settlementDay = settlementDate.getDayOfWeek();
		if (!workingDays.contains(settlementDay)) {
			return workDays.getNextWorkingDay(settlementDate);
		}
		return settlementDate;
	}

	private static WorkingDays getWorkDays(final Instruction instruction) {
		switch (instruction.getInstructionTrade().getCurrency().getCurrencyCode()) {
		case "AED":
		case "SAR":
			return new SpecialWorkingDays();
		default:
			return new DefaultWorkingDays();
		}
	}

	public String generateDailyReport(final List<Instruction> instructions) {

		instructions.forEach(instruction -> instruction.setSettlementDate(getActualSettlementDate(instruction)));

		final Map<LocalDate, BigDecimal> dailyIncoming = TradingService.calculateDailyIncomingSettlement(instructions);
		final Map<LocalDate, BigDecimal> dailyOutgoing = TradingService.calculateDailyOutgoingSettlement(instructions);
		final Map<LocalDate, List<Instruction>> dailyIncomingRank = TradingService
				.calculateDailyIncomingRank(instructions);
		final Map<LocalDate, List<Instruction>> dailyOutgoingRank = TradingService
				.calculateDailyOutgoingRank(instructions);

		final StringBuilder report = new StringBuilder();
		report.append("Daily Incoming Report").append(NEW_LINE);
		report.append("Date       | Incoming Amount").append(NEW_LINE);
		report.append("----------------------------").append(NEW_LINE);
		for (Entry<LocalDate, BigDecimal> incoming : dailyIncoming.entrySet()) {
			report.append(incoming.getKey()).append(" | ").append(incoming.getValue()).append(NEW_LINE);
		}

		report.append(NEW_LINE).append(NEW_LINE);

		report.append("Daily Outgoing Report").append(NEW_LINE);
		report.append("Date       | Outgoing Amount").append(NEW_LINE);
		report.append("----------------------------").append(NEW_LINE);
		for (Entry<LocalDate, BigDecimal> outgoing : dailyOutgoing.entrySet()) {
			report.append(outgoing.getKey()).append(" | ").append(outgoing.getValue()).append(NEW_LINE);
		}

		report.append(NEW_LINE).append(NEW_LINE);

		report.append("Daily Incoming Rank Report").append(NEW_LINE);
		report.append("Date       | Entity").append(NEW_LINE);
		report.append("-------------------").append(NEW_LINE);
		for (Entry<LocalDate, List<Instruction>> incoming : dailyIncomingRank.entrySet()) {
			report.append(incoming.getKey()).append(" | ");
			incoming.getValue().forEach(val -> report.append(val.getEntity()).append(NEW_LINE));
		}

		report.append(NEW_LINE).append(NEW_LINE);

		report.append("Daily Outgoing Rank Report").append(NEW_LINE);
		report.append("Date       | Entity").append(NEW_LINE);
		report.append("-------------------").append(NEW_LINE);
		for (Entry<LocalDate, List<Instruction>> outgoing : dailyOutgoingRank.entrySet()) {
			report.append(outgoing.getKey()).append(" | ");
			outgoing.getValue().forEach(val -> report.append(val.getEntity()).append(NEW_LINE));
		}

		return report.toString();
	}

}
