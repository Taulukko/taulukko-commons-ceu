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

	public MapHandler(Function<ResultSet, Optional<Map<K, V>>> identitys) {

		this.identitys = identitys;
	}

	public MapHandler(Function<Row, K> keyBuilder, Function<Row, V> valueBuilder) {

		this.identitys = rs -> Optional.of(rs.all().stream()
				.collect(Collectors.toMap(keyBuilder, valueBuilder)));

	}

	@Override
	public Optional<Map<K, V>> convert(ResultSet result) throws CEUException {
		return identitys.apply(result);
	}
}
