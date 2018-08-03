package com.samba.tradereport.service;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.samba.tradereport.model.Instruction;
import com.samba.tradereport.model.TradeAction;

public class TradingService {

	public static Map<LocalDate, BigDecimal> calculateDailyIncomingSettlement(final List<Instruction> instructions) {
		return calculateDailySettlement(instructions,
				instruction -> instruction.getInstructionTrade().getTradeAction() == TradeAction.SELL);
	}

	public static Map<LocalDate, BigDecimal> calculateDailyOutgoingSettlement(final List<Instruction> instructions) {
		return calculateDailySettlement(instructions,
				instruction -> instruction.getInstructionTrade().getTradeAction() == TradeAction.BUY);
	}

	public static Map<LocalDate, List<Instruction>> calculateDailyIncomingRank(final List<Instruction> instructions) {
		return calculateDailyRank(instructions,
				instruction -> instruction.getInstructionTrade().getTradeAction() == TradeAction.SELL);
	}

	public static Map<LocalDate, List<Instruction>> calculateDailyOutgoingRank(final List<Instruction> instructions) {
		return calculateDailyRank(instructions,
				instruction -> instruction.getInstructionTrade().getTradeAction() == TradeAction.BUY);
	}

	private static Map<LocalDate, BigDecimal> calculateDailySettlement(final List<Instruction> instructions,
			final Predicate<Instruction> tradeActionPredicate) {
		return instructions.stream().filter(tradeActionPredicate)
				.collect(Collectors.groupingBy(Instruction::getSettlementDate,
						mapping(Instruction::getTradeAmount, reducing(BigDecimal.ZERO, BigDecimal::add))));
	}

	private static Map<LocalDate, List<Instruction>> calculateDailyRank(final List<Instruction> instructions,
			final Predicate<Instruction> tradeActionPredicate) {
		final Map<LocalDate, List<Instruction>> dailyRank = new HashMap<>();

		final Map<LocalDate, List<Instruction>> dailyInstructions = instructions.stream().filter(tradeActionPredicate)
				.collect(Collectors.groupingBy(Instruction::getSettlementDate, Collectors.toList()));

		final Set<Entry<LocalDate, List<Instruction>>> entrySet = dailyInstructions.entrySet();
		for (Entry<LocalDate, List<Instruction>> entry : entrySet) {
			final LocalDate date = entry.getKey();
			final List<Instruction> ins = entry.getValue();
			Collections.sort(ins, (ins1, ins2) -> ins2.getTradeAmount().compareTo(ins1.getTradeAmount()));
			dailyRank.put(date, ins);
		}

		return dailyRank;
	}

}
