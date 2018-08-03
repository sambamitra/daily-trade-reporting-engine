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

/**
 * Service responsible for performing all the trading actions
 * 
 * @author samba.mitra
 *
 */
public class TradingService {

	/**
	 * Calculates the the daily incoming rank of entities
	 * 
	 * @param instructions
	 * @return
	 */
	public static Map<LocalDate, List<Instruction>> calculateDailyIncomingRank(final List<Instruction> instructions) {
		return calculateDailyRank(instructions,
				instruction -> instruction.getInstructionTrade().getTradeAction() == TradeAction.SELL);
	}

	/**
	 * Calculates the daily incoming settlement amount
	 * 
	 * @param instructions
	 * @return
	 */
	public static Map<LocalDate, BigDecimal> calculateDailyIncomingSettlement(final List<Instruction> instructions) {
		return calculateDailySettlement(instructions,
				instruction -> instruction.getInstructionTrade().getTradeAction() == TradeAction.SELL);
	}

	/**
	 * Calculates the daily outgoing rank of entities
	 * 
	 * @param instructions
	 * @return
	 */
	public static Map<LocalDate, List<Instruction>> calculateDailyOutgoingRank(final List<Instruction> instructions) {
		return calculateDailyRank(instructions,
				instruction -> instruction.getInstructionTrade().getTradeAction() == TradeAction.BUY);
	}

	/**
	 * Calculates the daily outgoing settlement amount
	 * 
	 * @param instructions
	 * @return
	 */
	public static Map<LocalDate, BigDecimal> calculateDailyOutgoingSettlement(final List<Instruction> instructions) {
		return calculateDailySettlement(instructions,
				instruction -> instruction.getInstructionTrade().getTradeAction() == TradeAction.BUY);
	}

	/**
	 * Calculates the daily rank - returns a sorted list of entities (sorted by rank
	 * highest) per settlement date
	 * 
	 * @param instructions
	 * @param tradeActionPredicate
	 * @return
	 */
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

	/**
	 * Calculates the daily settlement amount - returns the sum of all transactions
	 * of a particular type grouped by settlement date
	 * 
	 * @param instructions
	 * @param tradeActionPredicate
	 * @return
	 */
	private static Map<LocalDate, BigDecimal> calculateDailySettlement(final List<Instruction> instructions,
			final Predicate<Instruction> tradeActionPredicate) {
		return instructions.stream().filter(tradeActionPredicate)
				.collect(Collectors.groupingBy(Instruction::getSettlementDate,
						mapping(Instruction::getTradeAmount, reducing(BigDecimal.ZERO, BigDecimal::add))));
	}

}
