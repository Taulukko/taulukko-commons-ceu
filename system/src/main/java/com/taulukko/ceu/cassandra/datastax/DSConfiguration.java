package com.taulukko.ceu.cassandra.datastax;

import com.taulukko.ceu.data.Configuration;
import com.taulukko.ceu.data.PoolingOptions;
import com.taulukko.ceu.data.SocketOptions;

public class DSConfiguration implements Configuration {

	private com.datastax.driver.core.Configuration configuration = null;

	public DSConfiguration(com.datastax.driver.core.Configuration configuration) {

		this.configuration = configuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Configuration#getPoolingOptions()
	 */
	@Override
	public PoolingOptions getPoolingOptions() {
		return new DSPoolingOptions(configuration.getPoolingOptions());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Configuration#getSocketOptions()
	 */
	@Override
	public SocketOptions getSocketOptions() {
		return new DSSocketOptions(configuration.getSocketOptions());
	}
}
