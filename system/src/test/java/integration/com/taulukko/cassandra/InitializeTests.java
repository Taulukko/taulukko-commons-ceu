package integration.com.taulukko.cassandra;

import com.taulukko.cassandra.CEUConfig;
import com.taulukko.cassandra.CEUException;

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
