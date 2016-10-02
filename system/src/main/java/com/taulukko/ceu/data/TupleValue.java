package com.taulukko.ceu.data;

import com.taulukko.ceu.CEUException;

public interface TupleValue extends Value<TupleValue> {

	public abstract TupleType getType() throws CEUException;

	public abstract boolean equals(Object o);

	public abstract int hashCode();

	public abstract String toString();

	public abstract TupleValue setUDTValue(int i, UDTValue v)
			throws CEUException;

	public abstract TupleValue setTupleValue(int i, TupleValue v)
			throws CEUException;

	public abstract UDTValue getUDTValue(int i) throws CEUException;

	public abstract TupleValue getTupleValue(int i) throws CEUException;

}