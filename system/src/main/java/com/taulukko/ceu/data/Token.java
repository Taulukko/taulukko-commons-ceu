package com.taulukko.ceu.data;


public interface Token {

	public abstract DataType getType();

	public abstract Object getValue();

	public abstract int hashCode();

	public abstract boolean equals(Object obj);

	public abstract int compareTo(Token o);

	public abstract String toString();

}