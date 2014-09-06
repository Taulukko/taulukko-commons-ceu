package br.com.evon.cassandra.hector;

import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import br.com.evon.cassandra.DataSource;

public class DataSourceCommon implements DataSource<Keyspace> {

	private Cluster cluster = null;
	private Keyspace keyspace = null;

	public DataSourceCommon(String clusterName, String keyspaceName,
			String hostname, int port) {

		// "Evon Cluster 01","localhost:9160","oauth"
		cluster = HFactory.getOrCreateCluster(clusterName, hostname + ":"
				+ port);
		this.keyspace = HFactory.createKeyspace(keyspaceName, cluster);

	}

	@Override
	public Keyspace getKeyspace() {
		return keyspace;
	}

}
