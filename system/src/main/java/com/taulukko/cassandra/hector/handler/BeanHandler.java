package com.taulukko.cassandra.hector.handler;

import java.lang.reflect.Method;
import java.util.List;

import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Handler;

import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.query.QueryResult;

public class BeanHandler<T> implements
		Handler<T, QueryResult<CqlRows<String, String, byte[]>>> {

	private Class<T> clazz = null;

	public BeanHandler(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T convert(QueryResult<CqlRows<String, String, byte[]>> result)
			throws CEUException {

		CqlRows<String, String, byte[]> cqlRows = result.get();

		if (HandlerUtilsHector.isQueryEmpty(cqlRows)) {
			return null;
		}

		List<Row<String, String, byte[]>> listRows = cqlRows.getList();

		if (listRows.size() == 0) {
			throw new CEUException("Resultset is empty");
		}

		if (listRows.size() > 1) {
			throw new CEUException("To many rows");
		}

		T ret = null;
		try {
			ret = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new CEUException(e.getMessage(), e);
		}

		Row<String, String, byte[]> row = listRows.get(0);

		Method methods[] = clazz.getMethods();

		for (Method method : methods) {
 
			if (!method.getName().startsWith("set")) {
				continue;
			}
			if (method.getParameterTypes().length > 1) {
				continue;
			}

			String parameterName = HandlerUtilsHector.getParameterName(method
					.getName());
			Class<?> parameterTypes[] = method.getParameterTypes();
			Class<?> parameter = parameterTypes[0];

			HColumn<String, byte[]> column = row.getColumnSlice()
					.getColumnByName(parameterName);
			if (column == null) {
				continue;
			}
			byte buffer[] = column.getValue();

			HandlerUtilsHector.beanInvokeWriteParameterByByteBuffer(ret,
					method, parameter, buffer);

		}

		return ret;
	}

}
