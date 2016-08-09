package com.taulukko.ceu.data;

import java.util.Collection;

public interface State {

	public abstract Connection getSession();

	public abstract Collection<Host> getConnectedHosts();

	public abstract int getOpenConnections(Host host);

	public abstract int getTrashedConnections(Host host);

	public abstract int getInFlightQueries(Host host);

}