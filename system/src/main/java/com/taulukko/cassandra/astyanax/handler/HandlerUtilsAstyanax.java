 
 
package com.taulukko.cassandra.astyanax.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cassandra.db.marshal.DateType;
import org.apache.cassandra.db.marshal.DoubleType;
import org.apache.cassandra.db.marshal.IntegerType;
import org.apache.cassandra.db.marshal.UTF8Type;

import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;
import com.netflix.astyanax.serializers.BooleanSerializer;
import com.netflix.astyanax.serializers.DateSerializer;
import com.netflix.astyanax.serializers.DoubleSerializer;
import com.netflix.astyanax.serializers.FloatSerializer;
import com.netflix.astyanax.serializers.IntegerSerializer;
import com.netflix.astyanax.serializers.ListSerializer;
import com.netflix.astyanax.serializers.LongSerializer;
import com.netflix.astyanax.serializers.MapSerializer;
import com.netflix.astyanax.serializers.SetSerializer;
import com.netflix.astyanax.serializers.ShortSerializer;
import com.netflix.astyanax.serializers.StringSerializer;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.handler.HandlerUtils;

public class HandlerUtilsAstyanax extends HandlerUtils {

	private HandlerUtilsAstyanax() {
	}

	public static <K, N, V> boolean isQueryEmpty(Rows<Integer, String> cqlRows) {

		boolean onlyOneRow = cqlRows.size() == 1;
		if (onlyOneRow) {

			Row<Integer, String> row = cqlRows.getRowByIndex(0);
			ColumnList<String> columns = row.getColumns();
			boolean noColumns = columns.size() == 0;
			boolean isOnlyKey = columns.size() == 1
					&& columns.getColumnByIndex(0).getName().toUpperCase()
							.equals("KEY");
			return noColumns || isOnlyKey;
		}
		return false;
	}

	public static <T> T bufferToObject(byte buffer[], Class<T> clazz)
			throws CEUException {

		boolean isMap = Map.class.equals(clazz);
		boolean isList = List.class.equals(clazz);

		if (isMap || isList) {
			throw new CEUException("K and V parameters are required");
		}
		return bufferToObject(buffer, clazz, null, null);

	}

	@SuppressWarnings("unchecked")
	public static <T> T bufferToObject(byte buffer[], Class<T> clazz,
			Class<?> K, Class<?> V) throws CEUException {

		try {
			boolean isMap = Map.class.equals(clazz);
			boolean isList = List.class.equals(clazz);
			boolean isSet = Set.class.equals(clazz);

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
			} else if (isMap) {

				if (K == null || V == null) {
					throw new CEUException(
							"K and V vlaues is required to map object");
				}

				if (K.equals(Integer.class) || V.equals(Integer.class)) {
					throw new CEUException(
							"Change Integer to BigInteger in Map.");
				}

				if (K.equals(Float.class) || V.equals(Float.class)) {
					throw new CEUException("Change Float to Double in Map.");
				}
				if (K.equals(BigInteger.class)) {
					return (T) bufferToMapKeyInteger(buffer, V);
				} else if (K.equals(String.class)) {
					return (T) bufferToMapKeyString(buffer, V);
				} else if (K.equals(Double.class)) {
					return (T) bufferToMapKeyDouble(buffer, V);
				} else if (K.equals(Date.class)) {
					return (T) bufferToMapKeyDate(buffer, V);
				}

				throw new CEUException("Format Map<" + K.getName() + ","
						+ V.getClass().getName() + ">");

			} else if (isList) {

				if (V == null) {
					throw new CEUException("V vlaue is required to list object");
				}

				if (V.equals(Integer.class)) {
					throw new CEUException(
							"Change Integer to BigInteger in List.");
				}

				if (V.equals(Float.class)) {
					throw new CEUException("Change Float to Double in List.");
				}

				return (T) bufferToList(buffer, V);

			} else if (isSet) {

				if (V == null) {
					throw new CEUException("V vlaue is required to set object");
				}

				if (V.equals(Integer.class)) {
					throw new CEUException(
							"Change Integer to BigInteger in List.");
				}

				if (V.equals(Float.class)) {
					throw new CEUException("Change Float to Double in List.");
				}

				return (T) bufferToSet(buffer, V);

			} else {
				throw new CEUException("Type unknown " + clazz.getName());
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new CEUException(
					"CEU Cannot be parse resultset, check if your bean have correct parameter types "
							+ clazz.getName());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object bufferToList(byte[] buffer, Class<?> V) {
		if (V.equals(BigInteger.class)) {
			return new ListSerializer(IntegerType.instance).fromBytes(buffer);
		} else if (V.equals(Date.class)) {
			return new ListSerializer(DateType.instance).fromBytes(buffer);
		} else if (V.equals(Double.class)) {
			return new ListSerializer(DoubleType.instance).fromBytes(buffer);
		} else if (V.equals(String.class)) {
			return new ListSerializer(UTF8Type.instance).fromBytes(buffer);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object bufferToSet(byte[] buffer, Class<?> V) {
		if (V.equals(BigInteger.class)) {
			return new SetSerializer(IntegerType.instance).fromBytes(buffer);
		} else if (V.equals(Date.class)) {
			return new SetSerializer(DateType.instance).fromBytes(buffer);
		} else if (V.equals(Double.class)) {
			return new SetSerializer(DoubleType.instance).fromBytes(buffer);
		} else if (V.equals(String.class)) {
			return new SetSerializer(UTF8Type.instance).fromBytes(buffer);
		}
		return null;
	}

	private static Object bufferToMapKeyDate(byte[] buffer, Class<?> V) {
		if (V.equals(BigInteger.class)) {
			return new MapSerializer<Date, BigInteger>(DateType.instance,
					IntegerType.instance).fromBytes(buffer);
		} else if (V.equals(Date.class)) {
			return new MapSerializer<Date, Date>(DateType.instance,
					DateType.instance).fromBytes(buffer);
		} else if (V.equals(Double.class)) {
			return new MapSerializer<Date, Double>(DateType.instance,
					DoubleType.instance).fromBytes(buffer);
		} else if (V.equals(String.class)) {
			return new MapSerializer<Date, String>(DateType.instance,
					UTF8Type.instance).fromBytes(buffer);
		}
		return null;
	}

	private static Object bufferToMapKeyDouble(byte[] buffer, Class<?> V) {
		if (V.equals(BigInteger.class)) {
			return new MapSerializer<Double, BigInteger>(DoubleType.instance,
					IntegerType.instance).fromBytes(buffer);
		} else if (V.equals(Date.class)) {
			return new MapSerializer<Double, Date>(DoubleType.instance,
					DateType.instance).fromBytes(buffer);
		} else if (V.equals(Double.class)) {
			return new MapSerializer<Double, Double>(DoubleType.instance,
					DoubleType.instance).fromBytes(buffer);
		} else if (V.equals(String.class)) {
			return new MapSerializer<Double, String>(DoubleType.instance,
					UTF8Type.instance).fromBytes(buffer);
		}
		return null;
	}

	private static Object bufferToMapKeyString(byte[] buffer, Class<?> V) {
		if (V.equals(BigInteger.class)) {
			return new MapSerializer<String, BigInteger>(UTF8Type.instance,
					IntegerType.instance).fromBytes(buffer);
		} else if (V.equals(Date.class)) {
			return new MapSerializer<String, Date>(UTF8Type.instance,
					DateType.instance).fromBytes(buffer);
		} else if (V.equals(Double.class)) {
			return new MapSerializer<String, Double>(UTF8Type.instance,
					DoubleType.instance).fromBytes(buffer);
		} else if (V.equals(String.class)) {
			return new MapSerializer<String, String>(UTF8Type.instance,
					UTF8Type.instance).fromBytes(buffer);
		}
		return null;
	}

	private static Object bufferToMapKeyInteger(byte[] buffer, Class<?> V) {
		if (V.equals(BigInteger.class)) {
			return new MapSerializer<BigInteger, BigInteger>(
					IntegerType.instance, IntegerType.instance)
					.fromBytes(buffer);
		} else if (V.equals(Date.class)) {
			return new MapSerializer<BigInteger, Date>(IntegerType.instance,
					DateType.instance).fromBytes(buffer);
		} else if (V.equals(Double.class)) {
			return new MapSerializer<BigInteger, Double>(IntegerType.instance,
					DoubleType.instance).fromBytes(buffer);
		} else if (V.equals(String.class)) {
			return new MapSerializer<BigInteger, String>(IntegerType.instance,
					UTF8Type.instance).fromBytes(buffer);
		}
		return null;
	}

	public static void beanInvokeWriteParameterByByteBuffer(Object instance,
			Method method, Class<?> clazz, byte[] buffer) throws CEUException {
		try {

			boolean isMap = Map.class.equals(clazz);
			boolean isList = List.class.equals(clazz);
			boolean isSet = Set.class.equals(clazz);

			Class<?> K = null;
			Class<?> V = null;

			if (isList || isMap || isSet) {

				Type genericParameters[] = getGenericParameters(method,
						instance);

				if (isList | isSet) {
					V = (Class<?>) genericParameters[0];
				} else if (isMap) {
					K = (Class<?>) genericParameters[0];
					V = (Class<?>) genericParameters[1];
				}

			}

			Object value = HandlerUtilsAstyanax.bufferToObject(buffer, clazz,
					K, V);
			method.invoke(instance, value);

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new CEUException(e.getLocalizedMessage(), e);
		}
	}

}  
