package com.taulukko.ceu.cassandra.datastax;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.commons.lang.NotImplementedException;

import com.taulukko.ceu.data.ColumnDefinitions;
import com.taulukko.ceu.data.DataType;
import com.taulukko.ceu.data.Definition;

public class DSColumnDefinitions implements ColumnDefinitions {
	private com.datastax.driver.core.ColumnDefinitions coreColumnDefinitions = null;

	public DSColumnDefinitions(
			com.datastax.driver.core.ColumnDefinitions columnDefinition) {
		this.coreColumnDefinitions = columnDefinition;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#hashCode()
	 */
	@Override
	public int hashCode() {
		return coreColumnDefinitions.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#equals(java.lang
	 * .Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return coreColumnDefinitions.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#size()
	 */
	@Override
	public int size() {
		return coreColumnDefinitions.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#contains(java.lang
	 * .String)
	 */
	@Override
	public boolean contains(String name) {
		return coreColumnDefinitions.contains(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#getIndexOf(java
	 * .lang.String)
	 */
	@Override
	public int getIndexOf(String name) {
		return coreColumnDefinitions.getIndexOf(name);
	}

	@Override
	public Iterator<Definition> iterator() {

		return asList().stream().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#asList()
	 */
	@Override
	public List<Definition> asList() {
		return coreColumnDefinitions.asList().stream()
				.map(d -> new DSDefinition(d)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#getName(int)
	 */
	@Override
	public String getName(int i) {
		return coreColumnDefinitions.getName(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#getType(int)
	 */
	@Override
	public DataType getType(int i) {
		return new DSDataType<>(coreColumnDefinitions.getType(i));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#getType(java.lang
	 * .String)
	 */
	@Override
	public DataType getType(String name) {
		return new DSDataType<>(coreColumnDefinitions.getType(name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#getKeyspace(int)
	 */
	@Override
	public String getKeyspace(int i) {
		return coreColumnDefinitions.getKeyspace(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#getKeyspace(java
	 * .lang.String)
	 */
	@Override
	public String getKeyspace(String name) {
		return coreColumnDefinitions.getKeyspace(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#getTable(int)
	 */
	@Override
	public String getTable(int i) {
		return coreColumnDefinitions.getTable(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#getTable(java.lang
	 * .String)
	 */
	@Override
	public String getTable(String name) {
		return coreColumnDefinitions.getTable(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.ColumnDefinitions#toString()
	 */
	@Override
	public String toString() {
		return coreColumnDefinitions.toString();
	}

	@Override
	public void forEach(
			Consumer<? super com.taulukko.ceu.data.Definition> action) {
		throw new NotImplementedException();

	}

}
