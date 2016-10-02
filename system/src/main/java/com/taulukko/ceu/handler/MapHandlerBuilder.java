package com.taulukko.ceu.handler;

import static com.taulukko.ceu.handler.HandlerUtils.getSilent;
import static com.taulukko.ceu.handler.HandlerUtils.getMapSilent;
import static com.taulukko.ceu.handler.HandlerUtils.getStringSilent;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.taulukko.ceu.data.Row;

public class MapHandlerBuilder {

	private MapHandlerBuilder() {
	}


	public static <K, V> FilterMap<K, V> build() {
		return new FilterMap<K, V>();
	}

	public static <R, F> Function<Row, String> byKey() {

		return row -> getStringSilent(row, "key");

	}

	public static <R, V> Function<Row, V> byField(String fieldName,
			Class<V> clazz) {
		return row -> getSilent(row, fieldName, clazz);

	}

	public static class FilterMap<K, V> {
		private Function<Stream<Row>, Stream<Row>> decorator = null;

		private FilterMap() {

		}

		public PreparementCollectorManyRow<K, V> byAllRows() {
			return new PreparementCollectorManyRow<K, V>();
		}

		public PreparementCollectorOneRow byFirstRow() {
			if (decorator != null) {
				return new PreparementCollectorOneRow(stream -> decorator
						.apply(stream).findFirst());
			} else {
				return new PreparementCollectorOneRow(Stream::findFirst);
			}
		}

		public PreparementCollectorOneRow byAnyRow() {
			if (decorator != null) {
				return new PreparementCollectorOneRow(stream -> decorator
						.apply(stream).findAny());
			} else {
				return new PreparementCollectorOneRow(Stream::findAny);
			}

		}

		public FilterMap<K, V> filter(Predicate<Row> condition) {
			decorator = stream -> stream.filter(condition);
			return this;
		}

	}

	public static class PreparementCollectorManyRow<K, V> {

		private PreparementCollectorManyRow() {

		}

		public CollectorFromManyRows<K, V> collect() {
			return new CollectorFromManyRows<K, V>();
		}

	}

	public static class CollectorOneRow {

		private Function<Stream<Row>, Optional<Row>> filterbyRow;

		CollectorOneRow(Function<Stream<Row>, Optional<Row>> filterbyRow) {
			this.filterbyRow = filterbyRow;
		}

		public <K, V> MapHandler<K, V> byFunction(
				Function<Optional<Row>, Optional<Map<K, V>>> function) {
			return new MapHandler<K, V>(resultset -> filterbyRow.andThen(
					function).apply(resultset.stream()));
		}

		public <K, V> MapHandler<K, V> byFieldMap(String fieldName,
				Class<K> keysClass, Class<V> valuesClass) {
			return new MapHandler<K, V>(resultset -> Optional.of(
					filterbyRow
							.apply(resultset.stream())
							.filter(row -> !row.isEmpty())
							.map(row -> getMapSilent(row,
									fieldName, keysClass, valuesClass))

			).get());
		}
	}

	public static class PreparementCollectorOneRow {
		private Function<Stream<Row>, Optional<Row>> filterbyRow;

		PreparementCollectorOneRow(
				Function<Stream<Row>, Optional<Row>> filterbyRow) {
			this.filterbyRow = filterbyRow;
		}

		public CollectorOneRow collect() {
			return new CollectorOneRow(filterbyRow);
		}

	}

	public static class CollectorFromManyRows<K, V> {

		private Function<Row, K> keyBuilder = null;
		private Function<Row, V> valueBuilder = null;

		CollectorFromManyRows() {

		}

		public CollectorFromManyRows<K, V> prepareKey(
				Function<Row, K> keyBuilder) {
			this.keyBuilder = keyBuilder;
			return this;
		}

		public CollectorFromManyRows<K, V> prepareValue(
				Function<Row, V> valueBuilder) {
			this.valueBuilder = valueBuilder;
			return this;
		}

		public MapHandler<K, V> collect() {
			Objects.requireNonNull(keyBuilder);
			Objects.requireNonNull(valueBuilder);
			return new MapHandler<K, V>(keyBuilder, valueBuilder);
		}

	}

	public static class CollectorFromManyRowsByFields {
		CollectorFromManyRowsByFields() {
		}

		public <K, V> MapHandler<K, V> byFunction(Function<Row, K> keyBuilder,
				Function<Row, V> valueBuilder) {
			return new MapHandler<K, V>(resultset -> Optional.of(resultset
					.stream().collect(
							Collectors.<Row, K, V> toMap(keyBuilder,
									valueBuilder))));

		}

		public <K, V> MapHandler<K, V> byFunctionAndField(
				Function<Row, K> keyBuilder, String fieldNameValue,
				Class<V> classFieldValue) {
			return byFunction(
					keyBuilder,
					row -> getSilent(row, fieldNameValue,
							classFieldValue));
		}

		public <K, V> MapHandler<K, V> byFieldAndFunction(String fieldNameKey,
				Class<K> classFieldKey, Function<Row, V> valueBuilder) {
			return byFunction(
					row -> getSilent(row, fieldNameKey,
							classFieldKey), valueBuilder);

		}
	}

	public static class CollectorFromManyRowsByKey {

		CollectorFromManyRowsByKey() {

		}

		public <K, V> MapHandler<String, V> byFunction(Function<Row, V> function) {
			return new MapHandler<String, V>(resultset -> Optional.of(resultset
					.stream().collect(
							Collectors.<Row, String, V> toMap(
									row -> getStringSilent(
											row, "key"), row -> function
											.apply(row)))));

		}

		public <K, V> MapHandler<String, V> byField(String fieldName,
				Class<V> classField) {
			return byFunction(row -> getSilent(row,
					fieldName, classField));
		}

	}
}
