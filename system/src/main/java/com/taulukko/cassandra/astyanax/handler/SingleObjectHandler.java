package br.com.evon.cassandra.astyanax.handler;

import br.com.evon.cassandra.CEUException;
import br.com.evon.cassandra.Handler;

import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;

public class SingleObjectHandler<T> implements
		Handler<T, OperationResult<CqlResult<Integer, String>>> {

	private Class<T> clazz = null;
	private String columnName = null; 
	public SingleObjectHandler(Class<T> clazz, String columnName) {
		this.clazz = clazz;
		this.columnName = columnName;
	}
	 

	@Override
	public T convert(OperationResult<CqlResult<Integer, String>> result)
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
 
			return HandlerUtilsAstyanax.bufferToObject(buffer, clazz);

		}

		return null;
	}
}
