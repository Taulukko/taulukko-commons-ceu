package com.taulukko.ceu.cassandra.datastax;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.Cluster;
import com.taulukko.ceu.data.Connection;
import com.taulukko.ceu.data.Factory;
import com.taulukko.ceu.data.Host;
import com.taulukko.ceu.data.Metadata;

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
			String clusterName, String keyspaceName) throws CEUException {

		Factory factory = new DSDriver().getFactoryByContactPoint("localhost",null);
		Cluster cluster = factory.getCluster();
		Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n",
				metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.printf("Datacenter: %s; Host: %s", host.getDatacenter(),
					host.getAddress());
		}

		Connection session = cluster.connect();
		session.execute("CREATE KEYSPACE " + keyspaceName
				+ " WITH replication "
				+ "= {'class':'SimpleStrategy', 'replication_factor':3};");

		cluster.close();

		return getInstance();
	}

	public static CEUUtils removeKeyspace(String hostname, int port,
			String clusterName, String keyspaceName) throws CEUException {

		Factory factory = new DSDriver().getFactoryByContactPoint("localhost");
		Cluster cluster = factory.getCluster();

		Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n",
				metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.printf("Datacenter: %s; Host: %s", host.getDatacenter(),
					host.getAddress());
		}

		Connection session = cluster.connect();
		session.execute("DROP KEYSPACE " + keyspaceName);

		cluster.close(); 
		return getInstance();

	}

}
