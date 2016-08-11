package com.taulukko.ceu.cassandra.datastax;

import java.util.Map;

import com.taulukko.ceu.CEUException;
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

	public com.datastax.driver.core.Session getCoreSession()
			throws CEUException {
		return coreSession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#getLoggedKeyspace()
	 */
	@Override
	public String getLoggedKeyspace() throws CEUException {
		return coreSession.getLoggedKeyspace();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#init()
	 */
	@Override
	public Connection init() throws CEUException {
		return new DSConnection(cluster, coreSession.init());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#execute(java.lang.String)
	 */
	@Override
	public ResultSet execute(String query) throws CEUException {
		try {
			return new DSResultset(coreSession.execute(query));
		} catch (RuntimeException re) {
			throw new CEUException(re);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#execute(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public ResultSet execute(String query, Object... values)
			throws CEUException {
		try {
			return new DSResultset(coreSession.execute(query, values));
		} catch (RuntimeException re) {
			throw new CEUException(re);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#execute(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public ResultSet execute(String query, Map<String, Object> values)
			throws CEUException {
		try {
			return new DSResultset(coreSession.execute(query, values));
		} catch (RuntimeException re) {
			throw new CEUException(re);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#close()
	 */
	@Override
	public void close() throws CEUException {
		coreSession.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#isClosed()
	 */
	@Override
	public boolean isClosed() throws CEUException {
		return coreSession.isClosed();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#getCluster()
	 */
	@Override
	public Cluster getCluster() throws CEUException {
		return cluster;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Session#getState()
	 */
	@Override
	public State getState() throws CEUException {
		return new DSState(cluster, coreSession.getState());
	}

}
