package com.taulukko.cassandra.hector;

import java.util.HashMap;
import java.util.Map;

import com.taulukko.cassandra.CEUConfig;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.DataSource;
import com.taulukko.cassandra.ServerInfo;

import me.prettyprint.hector.api.Keyspace;

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
