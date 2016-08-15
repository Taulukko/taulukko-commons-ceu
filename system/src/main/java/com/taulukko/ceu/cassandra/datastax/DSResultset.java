package com.taulukko.ceu.cassandra.datastax;

import java.util.List;
import java.util.stream.Collectors;

import com.taulukko.ceu.data.ColumnDefinitions;
import com.taulukko.ceu.data.ExecutionInfo;
import com.taulukko.ceu.data.ResultSet;
import com.taulukko.ceu.data.Row;

public class DSResultset implements ResultSet {

	private com.datastax.driver.core.ResultSet coreResultSet = null;

	public DSResultset(com.datastax.driver.core.ResultSet resultSet) {
		this.coreResultSet = resultSet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Resultset#getColumnDefinitions()
	 */
	@Override
	public ColumnDefinitions getColumnDefinitions() {
		return new DSColumnDefinitions(coreResultSet.getColumnDefinitions());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Resultset#all()
	 */
	@Override
	public List<Row> all() {
		return coreResultSet.all().stream().map(r -> (Row) new DSRow(r))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.Resultset#getAvailableWithoutFetching
	 * ()
	 */
	@Override
	public int getAvailableWithoutFetching() {
		return coreResultSet.getAvailableWithoutFetching();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Resultset#isFullyFetched()
	 */
	@Override
	public boolean isFullyFetched() {
		return coreResultSet.isFullyFetched();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Resultset#getExecutionInfo()
	 */
	@Override
	public ExecutionInfo getExecutionInfo() {
		return new DSExecutionInfo(coreResultSet.getExecutionInfo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Resultset#getAllExecutionInfo()
	 */
	@Override
	public List<ExecutionInfo> getAllExecutionInfo() {
		return coreResultSet.getAllExecutionInfo().stream()
				.map(e -> new DSExecutionInfo(e)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Resultset#wasApplied()
	 */
	@Override
	public boolean wasApplied() {
		return coreResultSet.wasApplied();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Resultset#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return coreResultSet.isExhausted() && coreResultSet.all().size() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Resultset#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return coreResultSet.isExhausted();
	}
 
	@Override
	public Row next() {

		return new DSRow(coreResultSet.iterator().next());
	}

}
