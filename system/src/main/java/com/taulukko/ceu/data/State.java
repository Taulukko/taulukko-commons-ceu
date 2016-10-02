package com.taulukko.ceu.data;

import java.util.Collection;

import com.taulukko.ceu.CEUException;

public interface State {

	public abstract Connection getSession() throws CEUException;

	public abstract Collection<Host> getConnectedHosts() throws CEUException;

	public abstract int getOpenConnections(Host host) throws CEUException;

	public abstract int getTrashedConnections(Host host) throws CEUException;

	public abstract int getInFlightQueries(Host host) throws CEUException;

}