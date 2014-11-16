package br.com.evon.cassandra;


public interface DataSource<K> {

	public K getKeyspace();
}
