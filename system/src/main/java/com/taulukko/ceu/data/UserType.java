package com.taulukko.ceu.data;

import java.util.Collection;
import java.util.Iterator;

import com.datastax.driver.core.UserType.Field;
import com.taulukko.ceu.CEUException;

public interface UserType extends DataType {

	public abstract String getKeyspace() throws CEUException;

	public abstract String getTypeName() throws CEUException;

	public abstract int size() throws CEUException;

	public abstract boolean contains(String name) throws CEUException;

	public abstract Iterator<Field> iterator() throws CEUException;

	public abstract Collection<String> getFieldNames() throws CEUException;

	public abstract DataType getFieldType(String name) throws CEUException;

	public abstract boolean isFrozen() throws CEUException;

	public abstract int hashCode();

	public abstract boolean equals(Object o);

	public abstract String exportAsString() throws CEUException;

	public abstract String asCQLQuery() throws CEUException;

	public abstract String toString();

	public abstract String asFunctionParameterString() throws CEUException;

}