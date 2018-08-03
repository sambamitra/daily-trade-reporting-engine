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
		final InstructionTrade trade1 = InstructionTrade.builder().currency(Currency.getInstance("GBP"))
				.agreedFx(BigDecimal.valueOf(0.50)).pricePerUnit(BigDecimal.valueOf(100.25)).units(200)
				.tradeAction(TradeAction.BUY).build();
		final InstructionTrade trade2 = InstructionTrade.builder().currency(Currency.getInstance("AED"))
				.agreedFx(BigDecimal.valueOf(0.22)).pricePerUnit(BigDecimal.valueOf(150.5)).units(450)
				.tradeAction(TradeAction.SELL).build();

		final Instruction instruction1 = Instruction.builder().entity("foo").instructionDate(LocalDate.of(2016, 1, 1))
				.settlementDate(LocalDate.of(2016, 1, 2)).instructionTrade(trade1).build();
		final Instruction instruction2 = Instruction.builder().entity("bar").instructionDate(LocalDate.of(2016, 1, 5))
				.settlementDate(LocalDate.of(2016, 1, 7)).instructionTrade(trade2).build();

		final List<Instruction> instructions = Arrays.asList(instruction1, instruction2);

		ReportingService reportingService = new ReportingService();
		System.out.println(reportingService.generateDailyReport(instructions));
	}
}
