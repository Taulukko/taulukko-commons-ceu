package com.taulukko.ceu.cassandra.datastax;

import java.util.Map;

import com.taulukko.ceu.data.Cluster;
import com.taulukko.ceu.data.Connection;
import com.taulukko.ceu.data.ResultSet;
import com.taulukko.ceu.data.State;

public class DSConnection implements Connection {

	private Cluster cluster;
	private com.datastax.driver.core.Session coreSession = null;

	public DSConnection(Cluster cluster,
			com.datastax.driver.core.Session session) {
		this.cluster = cluster;
		this.coreSession = session;
	}

	public com.datastax.driver.core.Session getCoreSession() {
		return coreSession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#getLoggedKeyspace()
	 */
	@Override
	public String getLoggedKeyspace() {
		return coreSession.getLoggedKeyspace();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#init()
	 */
	@Override
	public Connection init() {
		return new DSConnection(cluster, coreSession.init());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#execute(java.lang.String)
	 */
	@Override
	public ResultSet execute(String query) {
		return new DSResultset(coreSession.execute(query));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#execute(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public ResultSet execute(String query, Object... values) {
		return new DSResultset(coreSession.execute(query, values));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#execute(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public ResultSet execute(String query, Map<String, Object> values) {
		return new DSResultset(coreSession.execute(query, values));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#close()
	 */
	@Override
	public void close() {
		coreSession.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#isClosed()
	 */
	@Override
	public boolean isClosed() {
		return coreSession.isClosed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#getCluster()
	 */
	@Override
	public Cluster getCluster() {
		return cluster;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#getState()
	 */
	@Override
	public State getState() {
		return new DSState(cluster,coreSession.getState());
	}

}
