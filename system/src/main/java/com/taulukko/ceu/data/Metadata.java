package com.taulukko.ceu.data;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;

import com.taulukko.ceu.CEUException;

public interface Metadata {
 
	public abstract Set<Host> getReplicas(String keyspace,
			ByteBuffer partitionKey)  throws CEUException;
 

	public abstract String getClusterName()  throws CEUException;

	public abstract String getPartitioner()  throws CEUException;

	public abstract Set<Host> getAllHosts()  throws CEUException;

	public abstract boolean checkSchemaAgreement()  throws CEUException;

	public abstract KeyspaceMetadata getKeyspace(String keyspace)  throws CEUException;

	public abstract List<KeyspaceMetadata> getKeyspaces()  throws CEUException;

	public abstract String exportSchemaAsString()  throws CEUException;

	public abstract TupleType newTupleType(DataType... types) throws CEUException;

	public abstract TupleType newTupleType(List<DataType> types)  throws CEUException;

	public abstract Token newToken(String tokenStr)  throws CEUException;
 
}