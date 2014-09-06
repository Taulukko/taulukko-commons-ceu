package br.com.evon.cassandra.hector;

import java.util.HashMap;
import java.util.Map;

import me.prettyprint.hector.api.Keyspace;
import br.com.evon.cassandra.CEUConfig;
import br.com.evon.cassandra.CEUException;
import br.com.evon.cassandra.DataSource;
import br.com.evon.cassandra.ServerInfo;

public class FactoryDataSourceHector {

	private static Map<String, DataSource<Keyspace>> dataSources = new HashMap<>();

	private static DataSource<Keyspace> defaultDataSource = null;

	public static DataSource<Keyspace> getDataSource() throws CEUException {
		if (defaultDataSource != null) {
			return defaultDataSource;
		}
		defaultDataSource = loadDataSource(null, true);
		return defaultDataSource;
	}

	public static DataSource<Keyspace> getDataSource(String keyspace) throws CEUException {
		if (dataSources.containsKey(keyspace)) {
			return dataSources.get(keyspace);
		}
		return loadDataSource(keyspace, false);
	}

	private synchronized static DataSource<Keyspace> loadDataSource(String keyspace,
			boolean defaultRequired) throws CEUException {
		if (dataSources.containsKey(keyspace)) {
			return dataSources.get(keyspace);
		}

		for (ServerInfo serverInfo : CEUConfig.serverInfos) {
			boolean sameName = serverInfo.getKeyspace().equals(keyspace);

			boolean valid = sameName
					|| (defaultRequired && serverInfo.getPrimary());
			if (valid) {
				DataSource<Keyspace> dataSource = new DataSourceCommon(
						serverInfo.getClusterName(), serverInfo.getKeyspace(),
						serverInfo.getHostName(),serverInfo.getPort());
				dataSources.put(keyspace, dataSource);
				return dataSource;
			}
		}
		throw new CEUException("Keyspace " + keyspace + " not found!");
	}
}
