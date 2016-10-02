package com.taulukko.ceu.handler;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;

public class BeanHandler<T> implements Handler<T> {

	private Class<T> clazz = null;
	private Function<Exception, Boolean> onSoftException = null;

	public BeanHandler(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * @param clazz
	 * @param onSoftException use to resolve non SQL Exceptions, return true to ignore exception
	 */
	public BeanHandler(Class<T> clazz, Function<Exception, Boolean> onSoftException) {
		this.clazz = clazz;
		this.onSoftException = onSoftException;
	}

	@Override
	public Optional<T> convert(ResultSet result) throws CEUException {
		if (result.isEmpty()) {
			return Optional.empty();
		}

		final AtomicInteger fail = new AtomicInteger();

		try {
			return result
					.all()
					.stream()
					.map(r -> HandlerUtils.mapRowToObject(fail, r, clazz,
							onSoftException).get()).filter(r -> r != null).findFirst();
		} catch (NoSuchElementException e) {
			throw new CEUException(e);
		}
	}

}
