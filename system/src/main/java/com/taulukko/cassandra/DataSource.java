package com.taulukko.cassandra;


public interface DataSource<K> {

	public K getKeyspace();
	public void release();
}
