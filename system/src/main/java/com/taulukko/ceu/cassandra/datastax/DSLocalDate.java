package com.taulukko.ceu.cassandra.datastax;

import com.taulukko.ceu.data.LocalDate;

public class DSLocalDate implements LocalDate {
	private com.datastax.driver.core.LocalDate coreLocalDate = null;

	public DSLocalDate(com.datastax.driver.core.LocalDate coreLocalDate) {
		this.coreLocalDate = coreLocalDate;
	}

	public com.datastax.driver.core.LocalDate getCoreLocalDate() {
		return coreLocalDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.LocalDate#getDaysSinceEpoch()
	 */
	@Override
	public int getDaysSinceEpoch() {
		return coreLocalDate.getDaysSinceEpoch();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.LocalDate#getMillisSinceEpoch()
	 */
	@Override
	public long getMillisSinceEpoch() {
		return coreLocalDate.getMillisSinceEpoch();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.LocalDate#getYear()
	 */
	@Override
	public int getYear() {
		return coreLocalDate.getYear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.LocalDate#getMonth()
	 */
	@Override
	public int getMonth() {
		return coreLocalDate.getMonth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.LocalDate#getDay()
	 */
	@Override
	public int getDay() {
		return coreLocalDate.getDay();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.LocalDate#add(int, int)
	 */
	@Override
	public LocalDate add(int field, int amount) {
		return new DSLocalDate(coreLocalDate.add(field, amount));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.LocalDate#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return coreLocalDate.equals(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.LocalDate#hashCode()
	 */
	@Override
	public int hashCode() {
		return coreLocalDate.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.LocalDate#toString()
	 */
	@Override
	public String toString() {
		return coreLocalDate.toString();
	}

}
