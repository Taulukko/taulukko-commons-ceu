package com.taulukko.ceu.handler;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;

public class SingleObjectHandler<T> implements Handler<T> {

	private Class<T> clazz = null;
	private String columnName = null;

	public SingleObjectHandler(Class<T> clazz, String columnName) {
		this.clazz = clazz;
		this.columnName = columnName;
	}

	@Override
	public Optional<T> convert(ResultSet result) throws CEUException {

		if (result.isEmpty()) {
			return Optional.empty();
		}

		final List<CEUException> exceptions = new CopyOnWriteArrayList<>();

		Optional<T> ret = result.all().stream().map(r -> {
			try {
				return r.get(columnName, clazz);
			} catch (CEUException e) {
				exceptions.add(e);
				return null;
			}
		}).filter(v -> v != null).findFirst();

		if (exceptions.isEmpty()) {
			return ret;
		}
		throw exceptions.get(0);
	}

}
