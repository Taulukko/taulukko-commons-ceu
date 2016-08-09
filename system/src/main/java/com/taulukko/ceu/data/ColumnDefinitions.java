package com.taulukko.ceu.data;

import java.util.List;

public interface ColumnDefinitions extends Iterable<Definition> {

	public abstract int hashCode();

	public abstract boolean equals(Object obj);

	public abstract int size();

	public abstract boolean contains(String name);

	public abstract int getIndexOf(String name);

	public abstract List<Definition> asList();

	public abstract String getName(int i);

	public abstract DataType getType(int i);

	public abstract DataType getType(String name);

	public abstract String getKeyspace(int i);

	public abstract String getKeyspace(String name);

	public abstract String getTable(int i);

	public abstract String getTable(String name);

	public abstract String toString();

}