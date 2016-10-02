package com.taulukko.ceu.data;

import com.taulukko.ceu.CEUException;

public interface Result {

	public ResultSet getResultset() throws CEUException;

	public default boolean isEmpty() throws CEUException {
		return this.getResultset().isEmpty();
	}

}
