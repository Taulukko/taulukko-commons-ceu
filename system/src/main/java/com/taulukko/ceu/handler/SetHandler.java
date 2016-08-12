package com.taulukko.ceu.handler;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;
import com.taulukko.ceu.data.Row;

public class SetHandler<T> implements Handler<Set<T>> {

	private Function<ResultSet, Optional<Set<T>>> identitys = null;

	private static <V> Optional<Set<V>> builderSet(final String columnName,
			final Class<V> clazz, final ResultSet rs) {
		List<Row> all = rs.all();
		if (all.size() == 0) {
			return Optional.empty();
		} else if (all.size() == 1) {
			return Optional.of(all.stream().findAny().get()
					.getList(columnName, clazz).stream().distinct()
					.collect(Collectors.toSet()));
		} else {
			return Optional.of(all.stream().map(r -> r.get(columnName, clazz))
					.distinct().collect(Collectors.toSet()));
		}
	}

	public SetHandler(Function<Row, Optional<T>> builder) {

		this.identitys = rs -> Optional.of(rs.all().stream()
				.map(row -> builder.apply(row))
				.filter(optional -> optional.isPresent())
				.map(optional -> optional.get()).collect(Collectors.toSet()));

	}

	public SetHandler(final String columnName, final Class<T> clazz) {

		identitys = rs -> builderSet(columnName, clazz, rs);

	}

	@Override
	public Optional<Set<T>> convert(ResultSet result) throws CEUException {
		return identitys.apply(result);
	}
}
