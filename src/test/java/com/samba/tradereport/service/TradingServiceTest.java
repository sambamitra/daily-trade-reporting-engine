package com.samba.tradereport.service;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.samba.tradereport.TestFixture;
import com.samba.tradereport.model.Instruction;

public class TradingServiceTest {

	@Test
	public void testCalculateDailyIncomingRank() {
		final Map<LocalDate, List<Instruction>> dailyIncoming = TradingService
				.calculateDailyIncomingRank(TestFixture.createInstructions());
		assertNotNull("Daily incoming not expected to be null", dailyIncoming);
	}

	@Test
	public void testCalculateDailyIncomingSettlementAmount() {
		final Map<LocalDate, BigDecimal> dailyIncoming = TradingService
				.calculateDailyIncomingSettlement(TestFixture.createInstructions());
		assertNotNull("Daily incoming not expected to be null", dailyIncoming);
	}

	@Test
	public void testCalculateDailyOutgoingRank() {
		final Map<LocalDate, List<Instruction>> dailyOutgoing = TradingService
				.calculateDailyOutgoingRank(TestFixture.createInstructions());
		assertNotNull("Daily outgoing not expected to be null", dailyOutgoing);
	}

	@Test
	public void testCalculateDailyOutgoingSettlementAmount() {
		final Map<LocalDate, BigDecimal> dailyOutgoing = TradingService
				.calculateDailyOutgoingSettlement(TestFixture.createInstructions());
		assertNotNull("Daily outgoing not expected to be null", dailyOutgoing);
	}
}
