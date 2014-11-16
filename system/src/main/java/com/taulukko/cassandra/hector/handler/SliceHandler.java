package br.com.evon.cassandra.hector.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.query.QueryResult;
import br.com.evon.cassandra.CEUConfig;
import br.com.evon.cassandra.CEUException;
import br.com.evon.cassandra.Handler;

public class SliceHandler<T> implements
		Handler<Map<String, T>, QueryResult<CqlRows<String, String, byte[]>>> {

	private Class<T> clazz = null;
	private String columnName = null;

	public SliceHandler(String columnName, Class<T> clazz) {
		this.clazz = clazz;
		this.columnName = columnName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, T> convert(
			QueryResult<CqlRows<String, String, byte[]>> cqlQueryGeneric)
			throws CEUException {

		QueryResult<CqlRows<String, String, byte[]>> result = (QueryResult<CqlRows<String, String, byte[]>>) cqlQueryGeneric;

		CqlRows<String, String, byte[]> cqlRows = result.get();

		List<Row<String, String, byte[]>> listRows = cqlRows.getList();

		Map<String, T> ret = new HashMap<>();

		if (HandlerUtilsHector.isQueryEmpty(cqlRows)) {
			return ret;
		}

		for (Row<String, String, byte[]> row : listRows) {

			List<HColumn<String, byte[]>> collumns = row.getColumnSlice()
					.getColumns();
			for (HColumn<String, byte[]> collumn : collumns) {
				// TODO: _ separator from config file
				String collumnNameParts[] = collumn.getName().split(
						"[" + CEUConfig.sliceSeparator + "]");

				if (collumnNameParts.length != 2) {
					continue;
				}

				String sliceColumnName = collumnNameParts[0];
				String sliceColumIndex = collumnNameParts[1];

				if (!columnName.equals(sliceColumnName)) {
					continue;
				}

				byte buffer[] = collumn.getValue();

				Object value = HandlerUtilsHector.bufferToObject(buffer, clazz);

				ret.put(sliceColumIndex, (T) value);

			}

		}

		return ret;
	}

}
