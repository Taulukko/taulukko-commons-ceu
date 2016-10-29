package com.taulukko.ceu.handler;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;
import com.taulukko.ceu.data.Row;

public class BeanListHandler<T> implements Handler<List<T>> {

	private Class<T> clazz = null;
	private Optional<Function<Exception, Boolean>> onError = Optional.empty();

	public BeanListHandler(Class<T> clazz,
			Optional<Function<Exception, Boolean>> onerror) {
		this.clazz = Objects.requireNonNull(clazz);
		this.onError = Objects.requireNonNull(onError);
	}

	public BeanListHandler(Class<T> clazz) {
		this(clazz, Optional.empty());
	}

	private static <U> Optional<U> converterFillBean(Row row, Class<U> clazz,
			Optional<Function<Exception, Boolean>> onSoftException) {
		try {
			return HandlerUtils.fillBean(row, clazz, onSoftException);
		} catch (CEUException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<List<T>> convert(ResultSet result) throws CEUException {
		if (result.isEmpty()) {
			return Optional.empty();
		}

		try {

			Optional<List<T>> ret = Optional.of(result.all().stream()
					.map(r -> converterFillBean(r, clazz, onError))
					.filter(o -> o.isPresent()).map(o -> o.get())
					.collect(Collectors.toList()));

			return ret;
		} catch (RuntimeException e) {
			throw new CEUException(e);
		}

	}
}
