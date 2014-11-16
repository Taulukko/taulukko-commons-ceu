package br.com.evon.cassandra.astyanax;

import br.com.evon.cassandra.DataSource;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

public class DataSourceCommon implements DataSource<Keyspace> {

	public AstyanaxContext<Keyspace> context = null;

	public DataSourceCommon(String clusterName, String keyspaceName,
			String hostname, int port, int maxConsPerHost, int maxTimeoutWhenExhausted, int socketTimeout, int connectionTimeout) {

		context = new AstyanaxContext.Builder()
				.forCluster(clusterName)
				.forKeyspace(keyspaceName)

				.withAstyanaxConfiguration(
						new AstyanaxConfigurationImpl()
								.setCqlVersion("3.0.0")
								.setTargetCassandraVersion("1.2")
								.setDiscoveryType(
										NodeDiscoveryType.RING_DESCRIBE))

				.withConnectionPoolConfiguration(
						new ConnectionPoolConfigurationImpl("MyConnectionPool")
								.setPort(port)
								/*
								 * maxConnsPerHost ~= <Number of cores per
								 * host>/<Replication factor> + 1 That is, for a
								 * cluster of 8-core boxes with replication
								 * factor 3, maxConnsPerHost should be around 4.
								 * This value is also a good starting point for
								 * experiments in heavy-load scenarios.
								 * 
								 * The motivation: a cluster of N nodes each
								 * having C cores has N * C cores total. To
								 * process request with replication factor R, R
								 * cores (of different nodes) are required. So,
								 * at every given moment the cluster can process
								 * up to N * C / R requests. It's a good idea to
								 * keep the amount of concurrent connections
								 * around this number. Divide it by N to
								 * calculate the number of connections per host.
								 * Add 1 spare connection per host for network
								 * latencies, etc. That's it.
								 * 
								 * Start with some maxConnsPerHost value
								 * 
								 * Simulate load and observe CPU usage and
								 * org.apache
								 * .cassandra.request->***Stage->pendingTasks
								 * JXM attributes
								 * 
								 * Increase maxConnsPerHost until pendingTasks
								 * starts to increase rapidly. This is probably
								 * the optimal value.
								 * 
								 * CPU load on cluster nodes should be around
								 * 50-70%. If it's much less - there's probably
								 * something wrong with server configuration.
								 */
								.setMaxConnsPerHost(maxConsPerHost)
								.setInitConnsPerHost(maxConsPerHost / 2)
								.setMaxTimeoutWhenExhausted(maxTimeoutWhenExhausted)
								.setSeeds(hostname)
								.setConnectTimeout(connectionTimeout)
								.setSocketTimeout(socketTimeout) 
						)
							
				.withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
				.buildKeyspace(ThriftFamilyFactory.getInstance());

		context.start();

	}
	
	@Override
	public Keyspace getKeyspace() {
		return context.getClient();
	}

	public void release() {
		context.shutdown();
	}
}
