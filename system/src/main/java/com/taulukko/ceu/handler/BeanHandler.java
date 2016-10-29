package com.taulukko.ceu.handler;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;

public class BeanHandler<T> implements Handler<T> {

	private Class<T> clazz = null;
	private Optional<Function<Exception, Boolean>> onSoftException = Optional
			.empty();

	public BeanHandler(Class<T> clazz) {
		this.clazz = Objects.requireNonNull(clazz);
	}

	/**
	 * @param clazz
	 * @param onSoftException
	 *            use to resolve non SQL Exceptions, return true to ignore
	 *            exception
	 */
	public BeanHandler(Class<T> clazz,
			Optional<Function<Exception, Boolean>> onSoftException) {
		this.clazz = Objects.requireNonNull(clazz);

		this.onSoftException = onSoftException;
	}

	@Override
	public Optional<T> convert(ResultSet result) throws CEUException {
		if (result.isEmpty()) {
			return Optional.empty();
		}

		try {
			return result
					.all()
					.stream()
					.map(r -> HandlerUtils.mapRowToObject(r, clazz,
							onSoftException).get()).filter(r -> r != null)
					.findFirst();
		} catch (NoSuchElementException e) {
			throw new CEUException(e);
		}
	}

}
