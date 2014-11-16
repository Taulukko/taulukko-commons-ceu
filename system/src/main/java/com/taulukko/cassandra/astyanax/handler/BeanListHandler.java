package com.taulukko.cassandra.astyanax.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Handler;

public class BeanListHandler<T> implements
		Handler<List<T>, OperationResult<CqlResult<Integer, String>>> {

	private Class<T> clazz = null;

	public BeanListHandler(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public List<T> convert(OperationResult<CqlResult<Integer, String>> result)
			throws CEUException {

		Rows<Integer, String> rows = result.getResult().getRows();

		List<T> ret = new ArrayList<>();

		if (HandlerUtilsAstyanax.isQueryEmpty(rows)) {
			return ret;
		}
		try {

			for (Row<Integer, String> row : rows) {

				ColumnList<String> columns = row.getColumns();

				T item = null;

				item = clazz.newInstance();

				Method methods[] = clazz.getMethods();

				for (Method method : methods) {

					if (!method.getName().startsWith("set")) {
						continue;
					}
					if (method.getParameterTypes().length > 1) {
						continue;
					}

					String parameterName = HandlerUtilsAstyanax
							.getParameterName(method.getName());
					Class<?> parameterTypes[] = method.getParameterTypes();
					Class<?> parameter = parameterTypes[0];

					Column<String> column = columns
							.getColumnByName(parameterName);
					if (column == null) {
						continue;
					}
					byte buffer[] = valueOrNull(column);

					if (buffer == null) {
						continue;
					}

					HandlerUtilsAstyanax.beanInvokeWriteParameterByByteBuffer(
							item, method, parameter, buffer);
				}

				ret.add(item);
			}

			return ret;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new CEUException(
					"Can't parse result in BeanListHandler, reason  : "
							+ e.getMessage(), e);
		}
	}

	private byte[] valueOrNull(Column<String> column) {
		try {
			return column.getByteArrayValue();
		} catch (NullPointerException ne) {
			return null;
		}
	}
}
