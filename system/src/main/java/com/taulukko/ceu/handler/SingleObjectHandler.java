package com.taulukko.ceu.handler;

import java.util.Optional;

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

		return result.all().stream().map(r -> r.get(columnName, clazz))
				.findFirst();
	}

}
