package com.taulukko.common.ceu.test;

import br.com.evon.cassandra.CEUConfig;
import br.com.evon.cassandra.CEUException;

public class InitializeTests {

	private static boolean started = false;

	public static synchronized void runOnce() throws CEUException {
		if (started) {
			return;

		}

		CEUConfig.load(InitializeTests.class
				.getResourceAsStream("/config/ceu.json"));

		started = true;
	}
}
