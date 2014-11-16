package com.taulukko.cassandra.astyanax.handler;

import java.util.Map;

import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Handler;

public class MapHandler<K, V> implements
		Handler<Map<K, V>, OperationResult<CqlResult<Integer, String>>> {

	private Class<K> k = null;
	private Class<V> v = null;
	private String columnName = null;

	public MapHandler(Class<K> k, Class<V> v, String columnName) {
		this.k = k;
		this.v = v;
		this.columnName = columnName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<K, V> convert(OperationResult<CqlResult<Integer, String>> result)
			throws CEUException {
		Rows<Integer, String> rows = result.getResult().getRows();

		if (HandlerUtilsAstyanax.isQueryEmpty(rows)) {
			return null;
		}
		for (Row<Integer, String> row : rows) {

			ColumnList<String> columns = row.getColumns();

			byte buffer[] = columns.getByteArrayValue(this.columnName, null);
			
			if(buffer==null)
			{
				throw new CEUException("Column not found");
			}

			return HandlerUtilsAstyanax.bufferToObject(buffer, Map.class, k, v);

		}

		return null;
	}
}
