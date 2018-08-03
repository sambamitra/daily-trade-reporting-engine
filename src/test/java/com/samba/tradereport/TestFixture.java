package com.samba.tradereport;

import java.util.List;

import com.samba.tradereport.model.Instruction;

import io.github.benas.randombeans.api.EnhancedRandom;

public class TestFixture {

	public static List<Instruction> createInstructions() {
		return EnhancedRandom.randomListOf(10, Instruction.class);
	}

}
