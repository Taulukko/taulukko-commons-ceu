package com.taulukko.ceu.cassandra.datastax;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.Driver;
import com.taulukko.ceu.data.Factory;

public class DSDriver implements Driver {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.Driver#getFactoryByContactPoint(java
	 * .lang.String)
	 */
	@Override
	public Factory getFactoryByContactPoint(String contactPoint)
			throws CEUException {
		return new DSFactory(contactPoint);
	}

	Factory getFactoryByContactPoint( String contactPoint,String forceKeyspace)
			throws CEUException {
		return new DSFactory( contactPoint,forceKeyspace);
	}
}
