package com.taulukko.cassandra.hector.handler;

import java.util.List;

import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Handler;

import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.query.QueryResult;

public class SingleObjectHandler<T> implements
		Handler<T, QueryResult<CqlRows<String, String, byte[]>>> {

	private Class<T> clazz = null;
	private String columnName = null;

	public SingleObjectHandler(Class<T> clazz, String columnName) {
		this.clazz = clazz;
		this.columnName = columnName;
	}

	@Override
	public T convert(QueryResult<CqlRows<String, String, byte[]>> result)
			throws CEUException {

		CqlRows<String, String, byte[]> cqlRows = result.get();

		if (HandlerUtilsHector.isQueryEmpty(cqlRows)) {
			return null;
		}

		List<Row<String, String, byte[]>> listRows = cqlRows.getList();

		if (listRows.size() > 1) {
			throw new CEUException("To many rows");
		}

		Row<String, String, byte[]> row = listRows.get(0);

		HColumn<String, byte[]> column = row.getColumnSlice().getColumnByName(
				columnName);

		if (column == null) {
			return null;
		}

		byte buffer[] = column.getValue();

		return HandlerUtilsHector.bufferToObject(buffer, clazz);
	}

}
