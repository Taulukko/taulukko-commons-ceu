package com.taulukko.ceu.data;


public interface Cluster extends OptionalValue {
 
	public abstract int hashCode();

	public abstract boolean equals(Object obj);

	public abstract Cluster init();

	public abstract Connection newSession();

	public abstract String toString();

	public abstract Connection connect();

	public abstract Connection connect(String keyspace);

	public abstract String getClusterName();

	public abstract Metadata getMetadata();

	public abstract Configuration getConfiguration();

	public abstract Metrics getMetrics();

	public abstract void close();

	public abstract boolean isClosed();

}