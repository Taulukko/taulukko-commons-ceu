package com.taulukko.ceu.data;

import java.util.Iterator;
import java.util.List;
 

public interface ResultSet extends Iterator<Row> {

	public abstract ColumnDefinitions getColumnDefinitions();

	public abstract Row one();

	public abstract List<Row> all();

	public abstract Iterator<Row> iterator();

	public abstract int getAvailableWithoutFetching();

	public abstract boolean isFullyFetched();

	public abstract ExecutionInfo getExecutionInfo();

	public abstract List<ExecutionInfo> getAllExecutionInfo();

	public abstract boolean wasApplied();

	public abstract boolean isEmpty();

	public abstract boolean hasNext();

}