package com.taulukko.ceu.handler;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;
import com.taulukko.ceu.data.Row;

public class BeanListHandler<T> implements Handler<List<T>> {

	private Class<T> clazz = null;
	private Function<Exception, Boolean> onError = null;

	public BeanListHandler(Class<T> clazz, Function<Exception, Boolean> onerror) {
		this.clazz = clazz;
		this.onError = onerror;
	}

	public BeanListHandler(Class<T> clazz) {
		this(clazz, null);
	}

	private static <U> Optional<U> converterFillBean(Row row, Class<U> clazz,
			Function<Exception, Boolean> onError, final List<Exception> errors) {
		try {
			return HandlerUtils.fillBean(row, clazz, onError);
		} catch (RuntimeException e) {
			errors.add(e);
			return Optional.empty();
		}
	}

	@Override
	public Optional<List<T>> convert(ResultSet result) throws CEUException {
		if (result.isEmpty()) {
			return Optional.empty();
		}

		final List<Exception> errors = new CopyOnWriteArrayList<Exception>();

		try {

			Optional<List<T>> ret = Optional.of(result.all().stream()
					.map(r -> converterFillBean(r, clazz, onError, errors))
					.filter(o -> o.isPresent()).map(o -> o.get())
					.collect(Collectors.toList()));
			if (!errors.isEmpty()) {
				throw new CEUException(errors.get(0));
			}
			return ret;
		} catch (RuntimeException e) {
			throw new CEUException(e);
		}

	}
}
