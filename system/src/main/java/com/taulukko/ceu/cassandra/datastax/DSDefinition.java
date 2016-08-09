package com.taulukko.ceu.cassandra.datastax;

import com.taulukko.ceu.data.DataType;
import com.taulukko.ceu.data.Definition;

public class DSDefinition implements Definition {
	private com.datastax.driver.core.ColumnDefinitions.Definition coreDefinition = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Definition#toString()
	 */
	@Override
	public String toString() {
		return coreDefinition.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Definition#getKeyspace()
	 */
	@Override
	public String getKeyspace() {
		return coreDefinition.getKeyspace();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Definition#getTable()
	 */
	@Override
	public String getTable() {
		return coreDefinition.getTable();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Definition#getName()
	 */
	@Override
	public String getName() {
		return coreDefinition.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Definition#getType()
	 */
	@Override
	public DataType getType() {
		return new DSDataType<>(coreDefinition.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Definition#hashCode()
	 */
	@Override
	public final int hashCode() {
		return coreDefinition.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.Definition#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals(Object o) {
		return coreDefinition.equals(o);
	}

	public DSDefinition(
			com.datastax.driver.core.ColumnDefinitions.Definition definition) {
		this.coreDefinition = definition;
	}
}
