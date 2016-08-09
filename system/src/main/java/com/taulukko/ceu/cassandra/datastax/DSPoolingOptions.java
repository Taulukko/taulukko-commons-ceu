package com.taulukko.ceu.cassandra.datastax;

import com.taulukko.ceu.data.PoolingOptions;

public class DSPoolingOptions implements PoolingOptions {
	private com.datastax.driver.core.PoolingOptions poolingOptions = null;

	public DSPoolingOptions(
			com.datastax.driver.core.PoolingOptions poolingOptions) {
		this.poolingOptions = poolingOptions;
	}

	public int getIdleTimeoutSeconds() {
		return poolingOptions.getIdleTimeoutSeconds();
	}

	public int getPoolTimeoutMillis() {
		return poolingOptions.getPoolTimeoutMillis();
	}

	public int getHeartbeatIntervalSeconds() {
		return poolingOptions.getHeartbeatIntervalSeconds();
	}

}