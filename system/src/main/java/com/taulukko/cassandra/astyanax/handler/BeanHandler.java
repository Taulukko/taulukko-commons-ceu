package com.taulukko.cassandra.astyanax.handler;

import java.lang.reflect.Method;

import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;
import com.taulukko.cassandra.CEUConfig;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Handler;
import com.taulukko.cassandra.handler.HandlerUtils;

public class BeanHandler<T> implements
		Handler<T, OperationResult<CqlResult<Integer, String>>> {

	private Class<T> clazz = null;

	public BeanHandler(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T convert(OperationResult<CqlResult<Integer, String>> result)
			throws CEUException {
		Rows<Integer, String> rows = result.getResult().getRows();

		T ret = null;

		if (HandlerUtilsAstyanax.isQueryEmpty(rows)) {
			return ret;
		}
		try {

			for (Row<Integer, String> row : rows) {

				ColumnList<String> columns = row.getColumns();

				ret = clazz.newInstance();

				Method methods[] = clazz.getMethods();

				for (Method method : methods) {

					if (!method.getName().startsWith("set")) {
						continue;
					}
					if (method.getParameterTypes().length > 1) {
						continue;
					}

					Class<?> parameterTypes[] = method.getParameterTypes();
					Class<?> parameter = parameterTypes[0];

					String parameterName = HandlerUtils.getParameterName(method
							.getName());

					if (!CEUConfig.isAutoWrapItemName) {
					//	parameterName = parameterName.toLowerCase();
					}

					byte buffer[] = columns.getByteArrayValue(parameterName,
							new byte[] {});

					if (buffer == null || buffer.length == 0) {
						continue;
					}

					HandlerUtilsAstyanax.beanInvokeWriteParameterByByteBuffer(
							ret, method, parameter, buffer);

				}

				return ret;
			}

			return ret;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new CEUException(
					"Can't parse result in BeanHandler, reason  : "
							+ e.getMessage(), e);
		}
	}
}
