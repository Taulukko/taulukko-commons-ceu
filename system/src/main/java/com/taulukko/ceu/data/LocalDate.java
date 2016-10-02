package com.taulukko.ceu.data;

import com.taulukko.ceu.CEUException;

public interface LocalDate {

	public abstract int getDaysSinceEpoch() throws CEUException;

	public abstract long getMillisSinceEpoch() throws CEUException;

	public abstract int getYear() throws CEUException;

	public abstract int getMonth() throws CEUException;

	public abstract int getDay() throws CEUException;

	public abstract LocalDate add(int field, int amount) throws CEUException;

}