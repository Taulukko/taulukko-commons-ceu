package com.taulukko.ceu.data;

import com.taulukko.ceu.CEUException;

public interface UDTValue extends Value<UDTValue> {
	

	public abstract UserType getType() throws CEUException;

	public abstract boolean equals(Object o);

	
	public abstract int hashCode();

	public abstract String toString();

	public abstract UDTValue getUDTValue(int i) throws CEUException;

	public abstract UDTValue setUDTValue(int i, UDTValue v) throws CEUException;

	public abstract UDTValue setUDTValue(String name, UDTValue v)
			throws CEUException;

	public abstract com.datastax.driver.core.UDTValue getCoreUdtValue();

}