package com.taulukko.ceu.cassandra.datastax;

import java.util.Collection;
import java.util.Map;

import com.datastax.driver.core.AggregateMetadata;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.FunctionMetadata;
import com.datastax.driver.core.MaterializedViewMetadata;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.core.UserType;
import com.taulukko.ceu.data.KeyspaceMetadata;

public class DSKeyspaceMetadata implements KeyspaceMetadata {
	private com.datastax.driver.core.KeyspaceMetadata keyspaceMetadata;

	public DSKeyspaceMetadata(
			com.datastax.driver.core.KeyspaceMetadata keyspaceMetadata) {
		this.keyspaceMetadata = keyspaceMetadata;
	}

	public String getName() {
		return keyspaceMetadata.getName();
	}

	public boolean isDurableWrites() {
		return keyspaceMetadata.isDurableWrites();
	}

	public Map<String, String> getReplication() {
		return keyspaceMetadata.getReplication();
	}

	public TableMetadata getTable(String name) {
		return keyspaceMetadata.getTable(name);
	}

	public Collection<TableMetadata> getTables() {
		return keyspaceMetadata.getTables();
	}

	public MaterializedViewMetadata getMaterializedView(String name) {
		return keyspaceMetadata.getMaterializedView(name);
	}

	public Collection<MaterializedViewMetadata> getMaterializedViews() {
		return keyspaceMetadata.getMaterializedViews();
	}

	public UserType getUserType(String name) {
		return keyspaceMetadata.getUserType(name);
	}

	public Collection<UserType> getUserTypes() {
		return keyspaceMetadata.getUserTypes();
	}

	public FunctionMetadata getFunction(String name,
			Collection<DataType> argumentTypes) {
		return keyspaceMetadata.getFunction(name, argumentTypes);
	}

	public FunctionMetadata getFunction(String name, DataType... argumentTypes) {
		return keyspaceMetadata.getFunction(name, argumentTypes);
	}

	public Collection<FunctionMetadata> getFunctions() {
		return keyspaceMetadata.getFunctions();
	}

	public AggregateMetadata getAggregate(String name,
			Collection<DataType> argumentTypes) {
		return keyspaceMetadata.getAggregate(name, argumentTypes);
	}

	public AggregateMetadata getAggregate(String name,
			DataType... argumentTypes) {
		return keyspaceMetadata.getAggregate(name, argumentTypes);
	}

	public Collection<AggregateMetadata> getAggregates() {
		return keyspaceMetadata.getAggregates();
	}

	public String exportAsString() {
		return keyspaceMetadata.exportAsString();
	}

	public String asCQLQuery() {
		return keyspaceMetadata.asCQLQuery();
	}

	public String toString() {
		return keyspaceMetadata.toString();
	}

	public boolean equals(Object o) {
		return keyspaceMetadata.equals(o);
	}

	public int hashCode() {
		return keyspaceMetadata.hashCode();
	}

}
