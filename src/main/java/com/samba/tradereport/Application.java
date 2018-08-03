package com.samba.tradereport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import com.samba.tradereport.model.Instruction;
import com.samba.tradereport.model.InstructionTrade;
import com.samba.tradereport.model.TradeAction;
import com.samba.tradereport.service.ReportingService;

public class Application {

	public static void main(String[] args) {
		final List<Instruction> instructions = createInstructions();

		ReportingService reportingService = new ReportingService();
		System.out.println(reportingService.generateDailyReport(instructions));
	}

	private static List<Instruction> createInstructions() {
		final InstructionTrade trade1 = InstructionTrade.builder().currency(Currency.getInstance("GBP"))
				.agreedFx(BigDecimal.valueOf(0.50)).pricePerUnit(BigDecimal.valueOf(100.25)).units(200)
				.tradeAction(TradeAction.BUY).build();
		final InstructionTrade trade2 = InstructionTrade.builder().currency(Currency.getInstance("AED"))
				.agreedFx(BigDecimal.valueOf(0.22)).pricePerUnit(BigDecimal.valueOf(150.5)).units(450)
				.tradeAction(TradeAction.SELL).build();
		final InstructionTrade trade3 = InstructionTrade.builder().currency(Currency.getInstance("EUR"))
				.agreedFx(BigDecimal.valueOf(0.70)).pricePerUnit(BigDecimal.valueOf(180.30)).units(450)
				.tradeAction(TradeAction.BUY).build();
		final InstructionTrade trade4 = InstructionTrade.builder().currency(Currency.getInstance("USD"))
				.agreedFx(BigDecimal.valueOf(0.65)).pricePerUnit(BigDecimal.valueOf(200.75)).units(450)
				.tradeAction(TradeAction.BUY).build();
		final InstructionTrade trade5 = InstructionTrade.builder().currency(Currency.getInstance("SAR"))
				.agreedFx(BigDecimal.valueOf(0.30)).pricePerUnit(BigDecimal.valueOf(90.75)).units(450)
				.tradeAction(TradeAction.SELL).build();

		final Instruction instruction1 = Instruction.builder().entity("foo").instructionDate(LocalDate.of(2018, 7, 10))
				.settlementDate(LocalDate.of(2018, 7, 16)).instructionTrade(trade1).build();
		final Instruction instruction2 = Instruction.builder().entity("bar").instructionDate(LocalDate.of(2016, 7, 15))
				.settlementDate(LocalDate.of(2018, 7, 16)).instructionTrade(trade2).build();
		final Instruction instruction3 = Instruction.builder().entity("abc").instructionDate(LocalDate.of(2018, 7, 25))
				.settlementDate(LocalDate.of(2018, 7, 31)).instructionTrade(trade3).build();
		final Instruction instruction4 = Instruction.builder().entity("def").instructionDate(LocalDate.of(2018, 8, 1))
				.settlementDate(LocalDate.of(2018, 8, 3)).instructionTrade(trade4).build();
		final Instruction instruction5 = Instruction.builder().entity("xyz").instructionDate(LocalDate.of(2018, 8, 1))
				.settlementDate(LocalDate.of(2018, 8, 4)).instructionTrade(trade5).build();

		return Arrays.asList(instruction1, instruction2, instruction3, instruction4, instruction5);
	}
}
