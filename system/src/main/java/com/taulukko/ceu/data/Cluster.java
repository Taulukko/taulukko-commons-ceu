package com.taulukko.ceu.data;

import com.taulukko.ceu.CEUException;

public interface Cluster extends OptionalValue {

	public abstract int hashCode();

	public abstract boolean equals(Object obj);

	public abstract Cluster init() throws CEUException;

	public abstract Connection newSession() throws CEUException;

	public abstract String toString();

	public abstract Connection connect() throws CEUException;

	public abstract Connection connect(String keyspace) throws CEUException;

	public abstract String getClusterName() throws CEUException;

	public abstract Metadata getMetadata() throws CEUException;

	public abstract Configuration getConfiguration() throws CEUException;

	public abstract Metrics getMetrics() throws CEUException;

	public abstract void close() throws CEUException;

	public abstract boolean isClosed()  throws CEUException;

}