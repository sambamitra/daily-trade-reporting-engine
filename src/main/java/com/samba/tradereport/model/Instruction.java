package com.samba.tradereport.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class Instruction {

	public static class InstructionTradeBuilder {
		@SuppressWarnings("unused")
		private InstructionTradeBuilder tradeAmount(final BigDecimal tradeAmount) {
			return this;
		}
	}

	private final String entity;

	private final LocalDate instructionDate;

	private LocalDate settlementDate;

	private final InstructionTrade instructionTrade;

	@Getter(value = AccessLevel.NONE)
	private final BigDecimal tradeAmount;

	public BigDecimal getTradeAmount() {
		return this.instructionTrade.getPricePerUnit().multiply(BigDecimal.valueOf(this.instructionTrade.getUnits()))
				.multiply(this.instructionTrade.getAgreedFx());
	}

}
