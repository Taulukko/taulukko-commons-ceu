package com.taulukko.ceu.data;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.taulukko.ceu.NotImplementedFunction;

public class SimpleColumnList extends ArrayList<Column> implements ColumnList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -299895117531244879L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.data.ColumnGetters#getColumnByIndex(int)
	 */
	@Override
	public Method getColumnByIndex(int i) {
		throw new NotImplementedFunction();
	}

}
