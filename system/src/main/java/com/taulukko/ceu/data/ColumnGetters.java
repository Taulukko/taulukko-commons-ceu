package com.taulukko.ceu.data;

import java.lang.reflect.Method;

import com.taulukko.ceu.CEUException;

public interface ColumnGetters {

	public abstract Method getColumnByIndex(int i) throws CEUException;

}