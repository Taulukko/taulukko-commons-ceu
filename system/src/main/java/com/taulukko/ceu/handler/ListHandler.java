package com.taulukko.ceu.handler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;
import com.taulukko.ceu.data.Row;

public class ListHandler<T> implements Handler<List<T>> {

	private Class<T> clazz = null;
	private String columnName = null;

	public ListHandler(String columnName, Class<T> clazz) {
		this.clazz = clazz;

		this.columnName = columnName;
	}

	@Override
	public Optional<List<T>> convert(ResultSet result) throws CEUException {
		if (result.isEmpty()) {
			return Optional.empty();
		}

		List<Row> all = result.all();

		if (all.size() == 1) {
			return Optional.of(all.stream().findFirst().get()
					.getList(columnName, clazz));
		} else {
			return Optional.of(all.stream()
					.map(row -> row.get(columnName, clazz))
					.collect(Collectors.toList()));
		}

	}
}
