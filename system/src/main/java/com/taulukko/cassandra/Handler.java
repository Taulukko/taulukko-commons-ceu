package com.taulukko.cassandra;


public interface Handler<T,K> {

	public T convert(K cqlQuery) throws CEUException;
}
