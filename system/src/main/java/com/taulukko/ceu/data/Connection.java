package com.taulukko.ceu.data;

import java.util.Map;

import com.taulukko.ceu.CEUException;

public interface Connection {

	public abstract String getLoggedKeyspace() throws CEUException;

	public abstract Connection init() throws CEUException;

	public abstract ResultSet execute(String query) throws CEUException;

	public abstract ResultSet execute(String query, Object... values)
			throws CEUException;

	public abstract ResultSet execute(String query, Map<String, Object> values)
			throws CEUException;

	public abstract void close() throws CEUException;

	public abstract boolean isClosed() throws CEUException;

	public abstract Cluster getCluster() throws CEUException;

	public abstract State getState() throws CEUException;

}