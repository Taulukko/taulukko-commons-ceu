package br.com.evon.cassandra.hector.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.DoubleSerializer;
import me.prettyprint.cassandra.serializers.FloatSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.ShortSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.Row;
import br.com.evon.cassandra.CEUException;
import br.com.evon.cassandra.handler.HandlerUtils;

public class HandlerUtilsHector extends HandlerUtils{

	private HandlerUtilsHector() {
	}

	public static <K,N,V>  boolean isQueryEmpty(CqlRows<K, N, V> cqlRows) {

		boolean onlyOneRow = cqlRows.getCount() == 1;
		if (onlyOneRow) {
			Row<K, N, V> row = cqlRows.getList().get(0);
			List<HColumn<N, V>> columns = row.getColumnSlice().getColumns();
			boolean noColumns = columns.size() == 0;
			boolean isOnlyKey = columns.size()==1 && columns.get(0).getName().toString().toUpperCase().equals("KEY");
			return noColumns || isOnlyKey;
		}

		return false;
	}

	public static boolean isRowEmpty(Row<?, ?, ?> row) {

		boolean isOnlyKey = row.getColumnSlice().getColumns().size() == 0;
		return isOnlyKey;

	}

	@SuppressWarnings("unchecked")
	public static <T> T bufferToObject(byte buffer[], Class<T> clazz)
			throws CEUException {
		if (clazz.equals(String.class)) {
			return (T) StringSerializer.get().fromBytes(buffer);
		} else if (clazz.equals(byte[].class)) {
			return (T) buffer;
		} else if (clazz.equals(int.class)) {
			return (T) IntegerSerializer.get().fromBytes(buffer);
		} else if (clazz.equals(short.class)) {
			return (T) ShortSerializer.get().fromBytes(buffer);
		} else if (clazz.equals(long.class)) {
			return (T) LongSerializer.get().fromBytes(buffer);
		} else if (clazz.equals(float.class)) {
			return (T) FloatSerializer.get().fromBytes(buffer);
		} else if (clazz.equals(double.class)) {
			return (T) DoubleSerializer.get().fromBytes(buffer);
		} else if (clazz.equals(boolean.class)) {
			return (T) BooleanSerializer.get().fromBytes(buffer);
		} else if (clazz.equals(Integer.class)) {
			return (T) IntegerSerializer.get().fromBytes(buffer);
		} else if (clazz.equals(Short.class)) {
			return (T) ShortSerializer.get().fromBytes(buffer);
		} else if (clazz.equals(Long.class)) {
			return (T) LongSerializer.get().fromBytes(buffer);
		} else if (clazz.equals(Date.class)) {
			return (T) DateSerializer.get().fromBytes(buffer);
		} else if (clazz.equals(Boolean.class)) {
			return (T) BooleanSerializer.get().fromBytes(buffer);
		} else if (clazz.equals(Float.class)) {
			return (T) FloatSerializer.get().fromBytes(buffer);
		} else if (clazz.equals(Double.class)) {
			return (T) DoubleSerializer.get().fromBytes(buffer);
		} else {
			throw new CEUException("Type unknown " + clazz.getName());
		}

	}

	public static void beanInvokeWriteParameterByByteBuffer(Object instance,
			Method method, Class<?> clazz, byte[] buffer) throws CEUException {
		try {
			Object value = HandlerUtilsHector.bufferToObject(buffer, clazz);
			method.invoke(instance, value);

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new CEUException(e.getLocalizedMessage(), e);
		}
	}
}
