package com.taulukko.ceu.handler;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;

public class BeanHandler<T> implements Handler<T> {

	private Class<T> clazz = null;
	private Function<Exception, Boolean> onError = null;

	public BeanHandler(Class<T> clazz) {
		this.clazz = clazz;
	}

	public BeanHandler(Class<T> clazz, Function<Exception, Boolean> onError) {
		this.clazz = clazz;
		this.onError = onError;
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
							onError).get()).filter(r -> r != null).findFirst();
		} catch (NoSuchElementException e) {
			throw new CEUException(e);
		}
	}

}
