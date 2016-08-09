package com.taulukko.ceu.data;

import java.util.Collection;
import java.util.Map;

import com.datastax.driver.core.AggregateMetadata;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.FunctionMetadata;
import com.datastax.driver.core.MaterializedViewMetadata;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.core.UserType;

public interface KeyspaceMetadata {

	public String getName();

	public boolean isDurableWrites();

	public Map<String, String> getReplication();

	public TableMetadata getTable(String name);

	public Collection<TableMetadata> getTables();

	public MaterializedViewMetadata getMaterializedView(String name);

	public Collection<MaterializedViewMetadata> getMaterializedViews();

	public UserType getUserType(String name);

	public Collection<UserType> getUserTypes();

	public FunctionMetadata getFunction(String name,
			Collection<DataType> argumentTypes);

	public FunctionMetadata getFunction(String name, DataType... argumentTypes);

	public Collection<FunctionMetadata> getFunctions();

	public AggregateMetadata getAggregate(String name,
			Collection<DataType> argumentTypes);

	public AggregateMetadata getAggregate(String name,
			DataType... argumentTypes);

	public Collection<AggregateMetadata> getAggregates();

	public String exportAsString();

	public String asCQLQuery();

	public String toString();

	public boolean equals(Object o);

	public int hashCode();

}
