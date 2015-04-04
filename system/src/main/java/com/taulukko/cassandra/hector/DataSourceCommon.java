package com.taulukko.cassandra.hector;

import com.taulukko.cassandra.DataSource;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;

public class DataSourceCommon implements DataSource<Keyspace> {

	private Cluster cluster = null;
	private Keyspace keyspace = null;

	public DataSourceCommon(String clusterName, String keyspaceName,
			String hostname, int port) {

		cluster = HFactory.getOrCreateCluster(clusterName, hostname + ":"
				+ port);
		this.keyspace = HFactory.createKeyspace(keyspaceName, cluster);

	}

	@Override
	public Keyspace getKeyspace() {
		return keyspace;
	}

	@Override
	public void release() {
	}
	
	

}
