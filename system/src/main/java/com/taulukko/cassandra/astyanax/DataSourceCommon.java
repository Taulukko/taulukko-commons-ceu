package com.taulukko.cassandra.astyanax;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.taulukko.cassandra.DataSource;

public class DataSourceCommon implements DataSource<Keyspace> {
 
	public AstyanaxContext<Keyspace> context = null;

	public DataSourceCommon(String clusterName, String keyspaceName,
			String hostname, int port, int maxConsPerHost,
			int maxTimeoutWhenExhausted, int socketTimeout,
			int connectionTimeout) {

		context = CEUUtils.createAstyanaxContext(hostname, port, clusterName,
				keyspaceName, maxConsPerHost, maxTimeoutWhenExhausted,
				connectionTimeout, socketTimeout);

		context.start();

	}

	@Override
	public Keyspace getKeyspace() {
		return context.getClient();
	}

	@Override
	public void release() {
		context.shutdown();
	}
}
