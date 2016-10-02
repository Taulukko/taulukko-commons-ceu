package com.taulukko.ceu.data;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.cassandra.datastax.DSToken;

public interface Row extends ValueGetter<Row> {

	public abstract ColumnDefinitions getColumnDefinitions()
			throws CEUException;

	public abstract DSToken getToken(int i) throws CEUException;

	public abstract Token getToken(String name) throws CEUException;

	public abstract Token getPartitionKeyToken() throws CEUException;

	public abstract boolean isEmpty();

}