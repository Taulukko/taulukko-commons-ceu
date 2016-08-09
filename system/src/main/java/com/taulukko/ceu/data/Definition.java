package com.taulukko.ceu.data;


public interface Definition {

	public abstract String toString();

	public abstract String getKeyspace();

	public abstract String getTable();

	public abstract String getName();

	public abstract DataType getType();

	public abstract int hashCode();

	public abstract boolean equals(Object o);

}