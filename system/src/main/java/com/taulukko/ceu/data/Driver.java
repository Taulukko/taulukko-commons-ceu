package com.taulukko.ceu.data;

import com.taulukko.ceu.CEUException;

public interface Driver {

	public abstract Factory getFactoryByContactPoint(String contactPoint)
			throws CEUException;

}