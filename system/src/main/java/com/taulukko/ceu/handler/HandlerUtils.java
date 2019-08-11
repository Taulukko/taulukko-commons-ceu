package com.taulukko.ceu.handler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import com.taulukko.ceu.CEUConfig;
import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ColumnDefinitions;
import com.taulukko.ceu.data.Row;

public abstract class HandlerUtils {

	@SuppressWarnings("unchecked")
	public static <R> Optional<R> getValueFromObject(Object o, Class<R> clazzR,
			String fieldName) throws CEUException {
		Method methods[] = o.getClass().getMethods();

		String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);

		for (Method method : methods) {

			if (!method.getName().equals(methodName)) {
				continue;
			}
			if (method.getParameterTypes().length > 0) {
				continue;
			}

			if (!method.getReturnType().getComponentType()
					.equals(clazzR.getClass())) {
				continue;
			}

			try {
				return Optional.of((R) method.getReturnType().cast(
						method.invoke(o)));
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new CEUException(e);
			}

		}
		return Optional.empty();
	}

	public static <R> Optional<R> mapRowToObject(Row r, Class<R> clazz,
			Optional<Function<Exception, Boolean>> onSoftException) {

		clazz = Objects.requireNonNull(clazz);
		onSoftException = Objects.requireNonNull(onSoftException);

		try {
			return fillBean(r, clazz, onSoftException);
		} catch (Exception e) {
			if (!onSoftException.isPresent()) {
				e.printStackTrace();
			} else if (onSoftException.get().apply(e)) {
				return null;
			}
			return Optional.<R> empty();
		}

	}

	public static <T> Optional<T> fillBean(Row row, Class<T> clazz,
			Optional<Function<Exception, Boolean>> onSoftException)
			throws CEUException {

		row = Objects.requireNonNull(row);
		clazz = Objects.requireNonNull(clazz);
		onSoftException = Objects.requireNonNull(onSoftException);

		ColumnDefinitions columnsDefinition = row.getColumnDefinitions();

		T ret = null;

		try {
			ret = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			if (onSoftException.isPresent()) {
				boolean notQuit = onSoftException.get().apply(e);
				if (notQuit) {
					return Optional.empty();
				}
			}

			throw new RuntimeException("Error ", e);
		}

		Method methods[] = clazz.getMethods();

		for (Method method : methods) {

			if (!method.getName().startsWith("set")) {
				continue;
			}
			if (method.getParameterTypes().length > 1) {
				continue;
			}

			String parameterName = HandlerUtils.getParameterName(method
					.getName());

			if (!columnsDefinition.contains(parameterName)) {
				continue;
			}

			if (!CEUConfig.isAutoWrapItemName) {
				parameterName = parameterName.toLowerCase();
			}

			try {
				ret = saveValueIntoMethod(row, onSoftException,
						columnsDefinition, ret, method, parameterName);
			} catch (CEUException e) {
				if (onSoftException.isPresent()) {
					boolean quit = !onSoftException.get().apply(e);
					if (!quit) {
						return Optional.empty();
					}
				}

				throw e;
			}

		}
		return Optional.of(ret);

	}

	private static <T> T saveValueIntoMethod(Row row,
			Optional<Function<Exception, Boolean>> onSoftException,
			ColumnDefinitions columnsDefinition, T ret, Method method,
			String parameterName) throws CEUException {
		row = Objects.requireNonNull(row);
		onSoftException = Objects.requireNonNull(onSoftException);
		columnsDefinition = Objects.requireNonNull(columnsDefinition);
		ret = Objects.requireNonNull(ret);
		method = Objects.requireNonNull(method);
		parameterName = Objects.requireNonNull(parameterName);

		try {
			Object value = null;

			if (method.getParameterTypes()[0].getCanonicalName().equals(
					"java.util.List")) {
				// converte para uma forma menos especializada (List<String> =>
				// List<Object>), no futuro melhor e tentar converter para a
				// forma adequada
				String type = columnsDefinition.getType(parameterName)
						.getTypeArguments().get(0).getName().name(); // type
																		// pode
																		// ser
																		// varchar
																		// por
				// exemplo

				Class<?> clazzGeneric = resolveLangClassFromList(type);

				value = row.getList(parameterName, clazzGeneric);

			} else if (method.getParameterTypes()[0].getCanonicalName().equals(
					"java.util.Map")) {

				// converte para uma forma menos especializada (List<String> =>
				// List<Object>), no futuro melhor e tentar converter para a
				// forma adequada
				String typeA = columnsDefinition.getType(parameterName)
						.getTypeArguments().get(0).getName().name(); // type
																		// pode
																		// ser
																		// varchar
																		// por
																		// exemplo

				String typeB = columnsDefinition.getType(parameterName)
						.getTypeArguments().get(1).getName().name(); // type
																		// pode
																		// ser
																		// varchar
																		// por
																		// exemplo

				Class<?> clazzGenericA = resolveLangClassFromList(typeA);
				Class<?> clazzGenericB = resolveLangClassFromList(typeB);

				value = row.getMap(parameterName, clazzGenericA, clazzGenericB);

			} else if (method.getParameterTypes()[0].getCanonicalName().equals(
					"java.util.Set")) {

				// converte para uma forma menos especializada (List<String> =>
				// List<Object>), no futuro melhor e tentar converter para a
				// forma adequada
				String type = columnsDefinition.getType(parameterName)
						.getTypeArguments().get(0).getName().name(); // type
																		// pode
																		// ser
																		// varchar
																		// por
				// exemplo

				Class<?> clazzGeneric = resolveLangClassFromList(type);

				value = row.getSet(parameterName, clazzGeneric);

			} else {

				value = row.get(parameterName, method.getParameterTypes()[0]);

			}

			setValueIntoBean(ret, value, row, onSoftException, method,
					parameterName);
		} catch (Throwable e) {
			throw new CEUException(
					"Error in reflect in field " + parameterName, e);
		}
		return ret;
	}

	// isso tem que ir parar lá no ds
	private static Class<?> resolveLangClassFromList(String type)
			throws CEUException {

		if (type.equals("VARCHAR")) {
			return String.class;
		} else if (type.equals("INT")) {
			return Integer.class;
		} else {
			throw new CEUException("Not implemented to type " + type);
		}

	}

	private static <T> void setValueIntoBean(T object, Object parameter,
			Row row, Optional<Function<Exception, Boolean>> onSoftException,
			Method method, String parameterName) {
		object = Objects.requireNonNull(object);
		row = Objects.requireNonNull(row);
		onSoftException = Objects.requireNonNull(onSoftException);
		method = Objects.requireNonNull(method);
		parameterName = Objects.requireNonNull(parameterName);

		try {
			method.invoke(object, parameter);
		} catch (IllegalArgumentException | InvocationTargetException
				| IllegalAccessException e) {
			if (onSoftException.isPresent()) {
				boolean quit = !onSoftException.get().apply(e);
				if (quit) {
					throw new RuntimeException("Error ", e);
				}

			} else {
				new CEUException("Error in set parameter [" + parameterName
						+ "]", e).printStackTrace();
			}
		}
	}

	public static String getParameterName(String methodName) {
		String firstChar = methodName.substring(3, 4).toLowerCase();
		String othersChars = methodName.substring(4);

		String parameterName = firstChar + othersChars;
		return parameterName;

	}

	public static Field getFieldByName(Class<?> clazz, String name) {

		if (clazz.equals(Object.class)) {
			return null;
		}

		Field fields[] = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(name)) {
				return field;
			}
		}

		return getFieldByName(clazz.getSuperclass(), name);
	}

	public static Type[] getGenericParameters(Method method, Object instance)
			throws CEUException {
		String parameterName = getParameterName(method.getName());

		try {

			Field field = getFieldByName(instance.getClass(), parameterName);

			if (field == null) {
				field = getFieldByName(instance.getClass(),
						parameterName.toLowerCase());
				if (field == null) {
					return null;
				}
			}

			ParameterizedType types = (ParameterizedType) field
					.getGenericType();

			return types.getActualTypeArguments();

		} catch (SecurityException e) {
			throw new CEUException(e.getMessage(), e);
		}
	}

	public static <X> X getSilent(final Row row, String fieldName,
			Class<X> clazz) {
		try {
			return row.get(fieldName, clazz);
		} catch (CEUException e) {

			throw new RuntimeException(e);
		}
	}

	public static String getStringSilent(final Row row, String fieldName) {
		try {
			return row.getString(fieldName);
		} catch (CEUException e) {

			throw new RuntimeException(e);
		}
	}

	public static Integer getIntSilent(final Row row, String fieldName) {
		try {
			return row.getInt(fieldName);
		} catch (CEUException e) {

			throw new RuntimeException(e);
		}
	}

	public static <K, V> Map<K, V> getMapSilent(final Row row, Integer index,
			Class<K> classK, Class<V> classV) {

		try {
			return row.getMap(index, classK, classV);
		} catch (CEUException e) {

			throw new RuntimeException(e);
		}
	}

	public static <K, V> Map<K, V> getMapSilent(final Row row,
			String fieldName, Class<K> classK, Class<V> classV) {

		try {
			return row.getMap(fieldName, classK, classV);
		} catch (CEUException e) {

			throw new RuntimeException(e);
		}
	}
	
	public static <V> Set<V> getSetSilent(final Row row, String fieldName,
			Class<V> classV) {

		try {
			return row.getSet(fieldName, classV);
		} catch (CEUException e) {

			throw new RuntimeException(e);
		}
	} 
	public static <V> List<V> getListSilent(final Row row, String fieldName,
			Class<V> classV) {

		try {
			return row.getList(fieldName, classV);
		} catch (CEUException e) {

			throw new RuntimeException(e);
		}
	}

}
