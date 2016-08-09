package com.taulukko.ceu.cassandra.datastax;

import java.util.Collection;
import java.util.Iterator;

import com.datastax.driver.core.UserType.Field;
import com.taulukko.ceu.data.DataType;
import com.taulukko.ceu.data.UserType;

public class DSUserType extends DSDataType<com.datastax.driver.core.UserType>
		implements UserType {

	public DSUserType(com.datastax.driver.core.UserType usertype) {
		super(usertype);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UserType#getKeyspace()
	 */
	@Override
	public String getKeyspace() {
		return super.getCoreDataType().getKeyspace();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UserType#getTypeName()
	 */
	@Override
	public String getTypeName() {
		return super.getCoreDataType().getTypeName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UserType#size()
	 */
	@Override
	public int size() {
		return super.getCoreDataType().size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UserType#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String name) {
		return super.getCoreDataType().contains(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UserType#iterator()
	 */
	@Override
	public Iterator<Field> iterator() {
		return super.getCoreDataType().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UserType#getFieldNames()
	 */
	@Override
	public Collection<String> getFieldNames() {
		return super.getCoreDataType().getFieldNames();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.UserType#getFieldType(java.lang.String)
	 */
	@Override
	public DataType getFieldType(String name) {
		return new DSDataType<>(super.getCoreDataType().getFieldType(name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UserType#isFrozen()
	 */
	@Override
	public boolean isFrozen() {
		return super.getCoreDataType().isFrozen();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UserType#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.getCoreDataType().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UserType#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return super.getCoreDataType().equals(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UserType#exportAsString()
	 */
	@Override
	public String exportAsString() {
		return super.getCoreDataType().exportAsString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UserType#asCQLQuery()
	 */
	@Override
	public String asCQLQuery() {
		return super.getCoreDataType().asCQLQuery();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UserType#toString()
	 */
	@Override
	public String toString() {
		return super.getCoreDataType().toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UserType#asFunctionParameterString()
	 */
	@Override
	public String asFunctionParameterString() {
		return super.getCoreDataType().asFunctionParameterString();
	}

}
