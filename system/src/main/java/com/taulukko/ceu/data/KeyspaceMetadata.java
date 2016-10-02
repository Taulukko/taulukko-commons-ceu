package com.taulukko.ceu.data;

import java.util.Collection;
import java.util.Map;

import com.datastax.driver.core.AggregateMetadata;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.FunctionMetadata;
import com.datastax.driver.core.MaterializedViewMetadata;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.core.UserType;
import com.taulukko.ceu.CEUException;

public interface KeyspaceMetadata {

	public String getName() throws CEUException;

	public boolean isDurableWrites() throws CEUException;

	public Map<String, String> getReplication() throws CEUException;

	public TableMetadata getTable(String name) throws CEUException;

	public Collection<TableMetadata> getTables() throws CEUException;

	public MaterializedViewMetadata getMaterializedView(String name)
			throws CEUException;

	public Collection<MaterializedViewMetadata> getMaterializedViews()
			throws CEUException;

	public UserType getUserType(String name) throws CEUException;

	public Collection<UserType> getUserTypes() throws CEUException;

	public FunctionMetadata getFunction(String name,
			Collection<DataType> argumentTypes) throws CEUException;

	public FunctionMetadata getFunction(String name, DataType... argumentTypes)
			throws CEUException;

	public Collection<FunctionMetadata> getFunctions() throws CEUException;

	public AggregateMetadata getAggregate(String name,
			Collection<DataType> argumentTypes) throws CEUException;

	public AggregateMetadata getAggregate(String name,
			DataType... argumentTypes) throws CEUException;

	public Collection<AggregateMetadata> getAggregates() throws CEUException;

	public String exportAsString() throws CEUException;

	public String asCQLQuery() throws CEUException;

}
