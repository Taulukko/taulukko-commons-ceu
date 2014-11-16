package com.taulukko.cassandra.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.taulukko.cassandra.CEUException;

public abstract class HandlerUtils {

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

			ParameterizedType types = (ParameterizedType) field
					.getGenericType();

			return types.getActualTypeArguments();

		} catch (SecurityException e) {
			throw new CEUException(e.getMessage(), e);
		}
	}

}
