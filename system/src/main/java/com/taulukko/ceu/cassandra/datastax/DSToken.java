package com.taulukko.ceu.cassandra.datastax;

import com.taulukko.ceu.data.DataType;
import com.taulukko.ceu.data.Token;

public class DSToken implements Token {
	private com.datastax.driver.core.Token coreToken = null;

	public DSToken(com.datastax.driver.core.Token coreToken) {
		this.coreToken = coreToken;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Token#getType()
	 */
	@Override
	public DataType getType() {
		return new DSDataType<com.datastax.driver.core.DataType>(
				coreToken.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Token#getValue()
	 */
	@Override
	public Object getValue() {
		return coreToken.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Token#hashCode()
	 */
	@Override
	public int hashCode() {
		return coreToken.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Token#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return coreToken.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.Token#compareTo(com.datastax.driver
	 * .core.Token)
	 */
	@Override
	public int compareTo(Token o) {
		if (!(o instanceof DSToken)) {
			return Integer.MIN_VALUE;
		}
		DSToken dsToken = (DSToken) o;
		return coreToken.compareTo(dsToken.coreToken);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Token#toString()
	 */
	@Override
	public String toString() {
		return coreToken.toString();
	}

}
