package br.com.evon.cassandra.hector.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.query.QueryResult;
import br.com.evon.cassandra.CEUException;
import br.com.evon.cassandra.Handler;

public class BeanListHandler<T> implements
		Handler<List<T>, QueryResult<CqlRows<String, String, byte[]>>> {

	private Class<T> v = null;

	public BeanListHandler(Class<T> clazz) {
		this.v = clazz;
	}

	@Override
	public List<T> convert(QueryResult<CqlRows<String, String, byte[]>> result)
			throws CEUException {

		CqlRows<String, String, byte[]> cqlRows = result.get();

		List<Row<String, String, byte[]>> listRows = cqlRows.getList();

		List<T> ret = new ArrayList<>();

		if (HandlerUtilsHector.isQueryEmpty(cqlRows)) {
			return ret;
		}
		try {
			for (Row<String, String, byte[]> row : listRows) {

				T item = null;

				item = v.newInstance();

				Method methods[] = v.getMethods();

				for (Method method : methods) {
 
					if (!method.getName().startsWith("set")) {
						continue;
					}
					if (method.getParameterTypes().length > 1) {
						continue;
					}
					String parameterName = HandlerUtilsHector
							.getParameterName(method.getName());
					Class<?> parameterTypes[] = method.getParameterTypes();
					Class<?> parameter = parameterTypes[0];

					HColumn<String, byte[]> column = row.getColumnSlice()
							.getColumnByName(parameterName);
					if (column == null) {
						continue;
					}
					byte buffer[] = column.getValue();

					HandlerUtilsHector.beanInvokeWriteParameterByByteBuffer(
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

}
