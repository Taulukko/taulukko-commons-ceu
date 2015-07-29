package com.taulukko.cassandra.astyanax;

import com.google.common.collect.ImmutableMap;
import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

public class CEUUtils {

	private static CEUUtils instance = null;
 

	private CEUUtils() {
	};

	private static CEUUtils getInstance() {
		if (instance == null) {
			instance = new CEUUtils();
		}
		return instance;
	}

	public static CEUUtils createKeyspace(String hostname, int port,
			String clusterName, String keyspaceName) throws ConnectionException {

		AstyanaxContext<Keyspace> context = createAstyanaxContext(hostname,
				port, clusterName, keyspaceName, 30, 120000, 120000, 120000);
		context.start();
		Keyspace keyspace = context.getClient();

		// Using simple strategy
		keyspace.createKeyspace(ImmutableMap
				.<String, Object> builder()
				.put("strategy_options",
						ImmutableMap.<String, Object> builder()
								.put("replication_factor", "1").build())
				.put("strategy_class", "SimpleStrategy").build());
		context.shutdown();

		return getInstance();
	}

	public static CEUUtils removeKeyspace(String hostname, int port,
			String clusterName, String keyspaceName) throws ConnectionException {

		AstyanaxContext<Keyspace> context = createAstyanaxContext(hostname,
				port, clusterName, keyspaceName, 30, 120000, 120000, 120000);
		context.start();
		Keyspace keyspace = context.getClient();

		// Using simple strategy
		keyspace.dropKeyspace();
		context.shutdown();

		return getInstance(); 
	}

	public static AstyanaxContext<Keyspace> createAstyanaxContext(
			String hostname, int port, String clusterName, String keyspaceName,
			int maxConnsPerHost, int maxTimeoutWhenExhausted,
			int connectTimeout, int socketTimeout) {
		ThriftFamilyFactory factoryFamily = ThriftFamilyFactory.getInstance();
		
		AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
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
								.setMaxConnsPerHost(maxConnsPerHost)
								.setInitConnsPerHost(maxConnsPerHost / 2)
								.setMaxTimeoutWhenExhausted(
										maxTimeoutWhenExhausted)
								.setSeeds(hostname)
								.setConnectTimeout(connectTimeout)
								.setSocketTimeout(socketTimeout))

				.withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
				.buildKeyspace(factoryFamily);
		return context;
	}
}
