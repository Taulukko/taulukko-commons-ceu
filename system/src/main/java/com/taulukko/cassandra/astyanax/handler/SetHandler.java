package com.taulukko.cassandra.astyanax.handler;

import java.util.Set;

import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Handler;

public class SetHandler<T> implements
		Handler<Set<T>, OperationResult<CqlResult<Integer, String>>> {

	private Class<T> clazz = null;
	private String columnName = null;

	public SetHandler(Class<T> clazz, String columnName) {
		this.clazz = clazz;
		this.columnName = columnName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<T> convert(OperationResult<CqlResult<Integer, String>> result)
			throws CEUException {
		Rows<Integer, String> rows = result.getResult().getRows();

		if (HandlerUtilsAstyanax.isQueryEmpty(rows)) {
			return null;
		}
		for (Row<Integer, String> row : rows) {

			ColumnList<String> columns = row.getColumns();

			byte buffer[] = columns.getByteArrayValue(this.columnName.toLowerCase(), null);

			if(buffer==null) 
			{
				throw new CEUException("Column not found");
			}

			return HandlerUtilsAstyanax.bufferToObject(buffer, Set.class,
					null, clazz);

		}

		return null;
	}
}
