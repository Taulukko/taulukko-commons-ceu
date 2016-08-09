package com.taulukko.ceu.cassandra.datastax;

import java.util.Optional;

import com.taulukko.ceu.CEUConfig;
import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.Cluster;
import com.taulukko.ceu.data.Factory;
import com.taulukko.ceu.data.ServerInfo;
import com.taulukko.ceu.data.SocketOptions;
import com.taulukko.commons.parsers.jsonParser.JSONParser;

public class DSFactory implements Factory {

	private String contactPoint = null;

	private Optional<String> keyspaceforce = null;

	DSFactory(String contactPoint, String keyspaceforce) {
		this(contactPoint);
		this.keyspaceforce = (keyspaceforce == null) ? Optional.empty()
				: Optional.of(keyspaceforce);

	}

	public DSFactory(String contactPoint) {
		this.contactPoint = contactPoint;
	}

	@Override
	public Cluster getCluster() throws CEUException {

		String serverinfoJSON = "{maxSchemaAgreementWaitSeconds:30000,socketTimeout:60000}";
		ServerInfo defaultServerInfo = JSONParser.convert(serverinfoJSON,
				ServerInfo.class);

		ServerInfo serverInfo = CEUConfig.getServerInfoByContactPoint(
				contactPoint).orElse(defaultServerInfo);

		com.datastax.driver.core.Cluster coreCluster = com.datastax.driver.core.Cluster
				.builder()
				.withMaxSchemaAgreementWaitSeconds(
						serverInfo.getMaxSchemaAgreementWaitSeconds())
				.addContactPoint(contactPoint).build();

		String keyspace = (keyspaceforce == null) ? serverInfo.getKeyspace()
				: keyspaceforce.orElse(null);

		Cluster cluster = new DSCluster(coreCluster, keyspace);

		SocketOptions options = cluster.getConfiguration().getSocketOptions();
		options.setReadTimeoutMillis(300000);
		options.setTcpNoDelay(true);

		return cluster;

	}
}
