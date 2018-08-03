package com.samba.tradereport.model;

import java.math.BigDecimal;
import java.util.Currency;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstructionTrade {

	private final TradeAction tradeAction;

	private final BigDecimal agreedFx;

	private final Currency currency;

	private final int units;

	private final BigDecimal pricePerUnit;

}
