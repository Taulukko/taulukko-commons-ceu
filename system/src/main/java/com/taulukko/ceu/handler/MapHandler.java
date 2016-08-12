package com.taulukko.ceu.handler;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;
import com.taulukko.ceu.data.Row;

public class MapHandler<K, V> implements Handler<Map<K, V>> {

	private Function<ResultSet, Optional<Map<K, V>>> identitys = null;

	public MapHandler(Function<Row, K> keyMapper, Function<Row, V> valueMapper) {

		this.identitys = rs -> Optional.of(rs.all().stream()
				.collect(Collectors.toMap(keyMapper, valueMapper)));

	}

	public MapHandler(final String fieldname, final Class<K> classK,
			final Class<V> classV) {

		identitys = r -> {
			Optional<Row> row = r.all().stream().findAny();
			return (row.isPresent()) ? Optional.of(row.get().getMap(fieldname,
					classK, classV)) : Optional.empty();
		};
	}

	public MapHandler(final String columnNameKey, final Class<K> clazzK,
			final String columnNameValue, final Class<V> clazzV) {

		this(r -> r.get(columnNameKey, clazzK), r -> r.get(columnNameValue,
				clazzV));

	}

	public MapHandler(final String columnNameKey, final Class<K> clazzK,
			Function<Row, V> valueMapper) {

		this(r -> r.get(columnNameKey, clazzK), valueMapper);

	}

	public MapHandler(Function<Row, K> keyMapper, final String columnNameValue,
			final Class<V> clazzV) {

		this.identitys = rs -> Optional.of(rs
				.all()
				.stream()
				.collect(
						Collectors.toMap(keyMapper,
								r -> r.get(columnNameValue, clazzV))));

	}

	@Override
	public Optional<Map<K, V>> convert(ResultSet result) throws CEUException {
		return identitys.apply(result);
	}
}
