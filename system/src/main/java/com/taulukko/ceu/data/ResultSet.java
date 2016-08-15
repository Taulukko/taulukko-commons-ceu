package com.taulukko.ceu.data;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public interface ResultSet extends Iterator<Row> {

	public abstract ColumnDefinitions getColumnDefinitions();

	public abstract List<Row> all();

	public abstract int getAvailableWithoutFetching();

	public abstract boolean isFullyFetched();

	public abstract ExecutionInfo getExecutionInfo();

	public abstract List<ExecutionInfo> getAllExecutionInfo();

	public abstract boolean wasApplied();

	public abstract boolean hasNext();

	public default Stream<Row> stream() {
		return this.all().stream();
	}

	public default boolean isEmpty() {
		return this.all().size() == 0;
	}

}