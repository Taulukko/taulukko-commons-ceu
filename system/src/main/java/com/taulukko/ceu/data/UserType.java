package com.taulukko.ceu.data;

import java.util.Collection;
import java.util.Iterator;

import com.datastax.driver.core.UserType.Field;

public interface UserType extends DataType {

	public abstract String getKeyspace();

	public abstract String getTypeName();

	public abstract int size();

	public abstract boolean contains(String name);

	public abstract Iterator<Field> iterator();

	public abstract Collection<String> getFieldNames();

	public abstract DataType getFieldType(String name);

	public abstract boolean isFrozen();

	public abstract int hashCode();

	public abstract boolean equals(Object o);

	public abstract String exportAsString();

	public abstract String asCQLQuery();

	public abstract String toString();

	public abstract String asFunctionParameterString();

}