package com.taulukko.ceu.data;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import com.taulukko.ceu.CEUException;

public interface ResultSet extends Iterator<Row> {

	public abstract ColumnDefinitions getColumnDefinitions()
			throws CEUException;

	public abstract List<Row> all();

	public abstract int getAvailableWithoutFetching() throws CEUException;

	public abstract boolean isFullyFetched() throws CEUException;

	public abstract ExecutionInfo getExecutionInfo() throws CEUException;

	public abstract List<ExecutionInfo> getAllExecutionInfo()
			throws CEUException;

	public abstract boolean wasApplied() throws CEUException;

	public abstract boolean hasNext();

	public default Stream<Row> stream() {
		return this.all().stream();
	}

	public default boolean isEmpty() {
		return this.all().size() == 0;
	}

}