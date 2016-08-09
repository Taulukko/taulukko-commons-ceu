package com.taulukko.ceu.cassandra.datastax;

import java.util.Collection;
import java.util.stream.Collectors;

import com.taulukko.ceu.data.Cluster;
import com.taulukko.ceu.data.Connection;
import com.taulukko.ceu.data.Host;
import com.taulukko.ceu.data.State;

public class DSState implements State {

	private com.datastax.driver.core.Session.State coreState = null;
	private Cluster cluster = null;

	public DSState(Cluster cluster,
			com.datastax.driver.core.Session.State coreState) {
		this.coreState = coreState;
		this.cluster = cluster;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.State#getSession()
	 */
	@Override
	public Connection getSession() {
		com.datastax.driver.core.Session session = coreState.getSession();
		return new DSConnection(cluster, session);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.State#getConnectedHosts()
	 */
	@Override
	public Collection<Host> getConnectedHosts() {
		return coreState.getConnectedHosts().stream()
				.map(h -> (Host) new DSHost(h)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.State#getOpenConnections(com.taulukko
	 * .ceu.data.Host)
	 */
	@Override
	public int getOpenConnections(Host host) {
		return coreState.getOpenConnections(((DSHost) host).getCoreHost());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.State#getTrashedConnections(com.taulukko
	 * .ceu.data.Host)
	 */
	@Override
	public int getTrashedConnections(Host host) {
		return coreState.getTrashedConnections(((DSHost) host).getCoreHost());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.State#getInFlightQueries(com.taulukko
	 * .ceu.data.Host)
	 */
	@Override
	public int getInFlightQueries(Host host) {
		return coreState.getInFlightQueries(((DSHost) host).getCoreHost());
	}

}
