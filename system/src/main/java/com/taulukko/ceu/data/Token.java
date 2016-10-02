package com.taulukko.ceu.data;

import com.taulukko.ceu.CEUException;

public interface Token {

	public abstract DataType getType() throws CEUException;

	public abstract Object getValue() throws CEUException;

	public abstract int hashCode();

	public abstract boolean equals(Object obj);

	public abstract int compareTo(Token o);

	public abstract String toString();

}