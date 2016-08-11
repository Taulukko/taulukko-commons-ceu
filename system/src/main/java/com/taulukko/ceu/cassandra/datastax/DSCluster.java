package com.taulukko.ceu.cassandra.datastax;

import com.datastax.driver.mapping.MappingManager;
import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.Cluster;
import com.taulukko.ceu.data.Configuration;
import com.taulukko.ceu.data.Connection;
import com.taulukko.ceu.data.Metadata;
import com.taulukko.ceu.data.Metrics;

public class DSCluster implements Cluster {
	private com.datastax.driver.core.Cluster coreCluster;
	private String keyspaceDefault = null;

	public DSCluster(com.datastax.driver.core.Cluster cluster,
			String keyspaceDefault) {
		this.coreCluster = cluster;
		this.keyspaceDefault = keyspaceDefault;
	}

	public com.datastax.driver.core.Cluster getCoreCluster() {
		return coreCluster;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Cluster#hashCode()
	 */
	@Override
	public int hashCode() {
		return coreCluster.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Cluster#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return coreCluster.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Cluster#init()
	 */
	@Override
	public Cluster init() {
		return new DSCluster(coreCluster.init(), keyspaceDefault);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Cluster#newSession()
	 */
	@Override
	public Connection newSession() throws CEUException {
		DSConnection dsConnection = new DSConnection(this,
				coreCluster.newSession());
		if (keyspaceDefault != null) {
			dsConnection.execute("USE " + keyspaceDefault + ";");
		}
		// não precisa guardar pra manipular posteriormente ?
		new MappingManager(dsConnection.getCoreSession());

		return dsConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Cluster#toString()
	 */
	@Override
	public String toString() {
		return coreCluster.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Cluster#connect()
	 */
	@Override
	public Connection connect() throws CEUException {
		DSConnection dsConnection = null;

		if (keyspaceDefault != null) {
			dsConnection = new DSConnection(this,
					coreCluster.connect(keyspaceDefault));

			// não precisa guardar pra manipular posteriormente ?
			new MappingManager(dsConnection.getCoreSession());

		} else {
			dsConnection = new DSConnection(this, coreCluster.connect());
		}
		return dsConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Cluster#connect(java.lang.String)
	 */
	@Override
	public Connection connect(String keyspace) throws CEUException {
		DSConnection dsConnection = new DSConnection(this,
				coreCluster.connect(keyspace));

		// não precisa guardar pra manipular posteriormente ?
		new MappingManager(dsConnection.getCoreSession());

		return dsConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Cluster#getClusterName()
	 */
	@Override
	public String getClusterName() {
		return coreCluster.getClusterName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Cluster#getMetadata()
	 */
	@Override
	public Metadata getMetadata() {
		return new DSMetadata(coreCluster.getMetadata());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Cluster#getConfiguration()
	 */
	@Override
	public Configuration getConfiguration() {
		return new DSConfiguration(coreCluster.getConfiguration());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Cluster#getMetrics()
	 */
	@Override
	public Metrics getMetrics() {
		return new DSMetrics(coreCluster.getMetrics());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Cluster#close()
	 */
	@Override
	public void close() {
		coreCluster.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Cluster#isClosed()
	 */
	@Override
	public boolean isClosed() {
		return coreCluster.isClosed();
	}

}
