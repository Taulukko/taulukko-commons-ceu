package com.taulukko.ceu.data;

public interface LocalDate {

	public abstract int getDaysSinceEpoch();

	public abstract long getMillisSinceEpoch();

	public abstract int getYear();

	public abstract int getMonth();

	public abstract int getDay();

	public abstract LocalDate add(int field, int amount);

	public abstract boolean equals(Object o);

	public abstract int hashCode();

	public abstract String toString();

}