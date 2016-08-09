package com.taulukko.ceu.cassandra.datastax;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.DataType;
import com.taulukko.ceu.data.Host;
import com.taulukko.ceu.data.KeyspaceMetadata;
import com.taulukko.ceu.data.Metadata;
import com.taulukko.ceu.data.Token;
import com.taulukko.ceu.data.TupleType;

public class DSMetadata implements Metadata {
	com.datastax.driver.core.Metadata coreMetaData = null;

	public DSMetadata(com.datastax.driver.core.Metadata dsmetadata) {
		this.coreMetaData = dsmetadata;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metadata2#hashCode()
	 */
	@Override
	public int hashCode() {
		return coreMetaData.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metadata2#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return coreMetaData.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metadata2#toString()
	 */
	@Override
	public String toString() {
		return coreMetaData.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.Metadata2#getReplicas(java.lang.String,
	 * java.nio.ByteBuffer)
	 */
	@Override
	public Set<Host> getReplicas(String keyspace, ByteBuffer partitionKey) {
		return convert(coreMetaData.getReplicas(keyspace, partitionKey));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metadata2#getClusterName()
	 */
	@Override
	public String getClusterName() {
		return coreMetaData.getClusterName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metadata2#getPartitioner()
	 */
	@Override
	public String getPartitioner() {
		return coreMetaData.getPartitioner();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metadata2#getAllHosts()
	 */
	@Override
	public Set<Host> getAllHosts() {
		return convert(coreMetaData.getAllHosts());
	}

	private Set<Host> convert(Set<com.datastax.driver.core.Host> hosts) {
		final Set<Host> ret = new HashSet<Host>();

		hosts.stream().map(h -> new DSHost(h)).forEach(h -> ret.add(h));
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metadata2#checkSchemaAgreement()
	 */
	@Override
	public boolean checkSchemaAgreement() {
		return coreMetaData.checkSchemaAgreement();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.Metadata2#getKeyspace(java.lang.String)
	 */
	@Override
	public KeyspaceMetadata getKeyspace(String keyspace) {
		return new DSKeyspaceMetadata(coreMetaData.getKeyspace(keyspace));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metadata2#getKeyspaces()
	 */
	@Override
	public List<KeyspaceMetadata> getKeyspaces() {
		final List<KeyspaceMetadata> ret = new ArrayList<KeyspaceMetadata>();
		coreMetaData.getKeyspaces().stream()
				.map(k -> new DSKeyspaceMetadata(k)).forEach(t -> ret.add(t));
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metadata2#exportSchemaAsString()
	 */
	@Override
	public String exportSchemaAsString() {
		return coreMetaData.exportSchemaAsString();
	}

	private DSDataType<?> castToDS(final DataType type,
			final List<CEUException> errors) {
		if (type instanceof DSDataType) {
			return (DSDataType<?>) type;
		}
		errors.add(new CEUException(
				"cant receive Datatype from other driver, use Datastax driver"));
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.Metadata2#newTupleType(com.datastax.driver
	 * .core.DataType)
	 */
	@Override
	public TupleType newTupleType(DataType... arrayTypes) throws CEUException {
		List<DataType> types = Arrays.asList(arrayTypes);

		return this.newTupleType(types);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.Metadata2#newTupleType(java.util.List)
	 */
	@Override
	public TupleType newTupleType(List<DataType> types) throws CEUException {
		final List<CEUException> errors = new ArrayList<CEUException>();

		List<com.datastax.driver.core.DataType> coreDataTypes = types.stream()
				.map(t -> castToDS(t, errors)).map(t -> t.getCoreDataType())
				.collect(Collectors.toList());

		if (errors.size() > 0) {
			throw errors.get(0);
		}

		return new DSTupleType(coreMetaData.newTupleType(coreDataTypes));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metadata2#newToken(java.lang.String)
	 */
	@Override
	public Token newToken(String tokenStr) {
		return new DSToken(coreMetaData.newToken(tokenStr));
	}

}
