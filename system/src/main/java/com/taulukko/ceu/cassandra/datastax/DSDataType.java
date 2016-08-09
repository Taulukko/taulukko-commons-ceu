package com.taulukko.ceu.cassandra.datastax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.taulukko.ceu.data.DataType;

public class DSDataType<T extends com.datastax.driver.core.DataType> implements
		DataType {
	private T coreDataType;

	private com.taulukko.ceu.data.DataType.Name name = null;

	public DSDataType(T dataType) {

		this.coreDataType = dataType;

		initName(dataType);
	}

	private void initName(T dataType) {
		List<com.taulukko.ceu.data.DataType.Name> names = Arrays
				.asList(com.taulukko.ceu.data.DataType.Name.values());

		Optional<com.taulukko.ceu.data.DataType.Name> name = names
				.stream()
				.filter(n -> n.name().equals(dataType.getName().name()))
				.findFirst();
		if (name.isPresent()) {
			this.name = name.get();
		}
	}

	public T getCoreDataType() {
		return coreDataType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.DataType#hashCode()
	 */
	@Override
	public int hashCode() {
		return coreDataType.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.DataType#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return coreDataType.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.DataType#toString()
	 */
	@Override
	public String toString() {
		return coreDataType.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.DataType#getName()
	 */
	@Override
	public com.taulukko.ceu.data.DataType.Name getName() {
		return this.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.DataType#isFrozen()
	 */
	@Override
	public boolean isFrozen() {
		return coreDataType.isFrozen();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.DataType#isCollection()
	 */
	@Override
	public boolean isCollection() {
		return coreDataType.isCollection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.DataType#getTypeArguments()
	 */
	@Override
	public List<DataType> getTypeArguments() {
		final List<DataType> ret = new ArrayList<DataType>();
		List<com.datastax.driver.core.DataType> arguments = coreDataType
				.getTypeArguments();
		Arrays.asList(
				arguments
						.toArray(new com.datastax.driver.core.DataType[arguments
								.size()])).stream()
				.map(ds -> new DSDataType<com.datastax.driver.core.DataType>(ds))
				.forEach(dataType -> ret.add(dataType));

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.DataType#asFunctionParameterString()
	 */
	@Override
	public String asFunctionParameterString() {
		return coreDataType.asFunctionParameterString();
	}

}
