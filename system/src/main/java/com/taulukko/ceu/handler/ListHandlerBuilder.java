package com.taulukko.ceu.handler;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.taulukko.ceu.data.ResultSet;
import com.taulukko.ceu.data.Row;

public class ListHandlerBuilder {

	private ListHandlerBuilder() {
	}

	public static FilterList build() {
		return new ListHandlerBuilder().new FilterList();
	}

	public class FilterList {
		private Function<Stream<Row>, Stream<Row>> decorator = null;

		private FilterList() {

		}

		public PreparementCollectorManyRow byAllRows() {
			return new PreparementCollectorManyRow();
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

		public FilterList filter(Predicate<Row> condition) {
			decorator = stream -> stream.filter(condition);
			return this;
		}

	}

	public class PreparementCollectorManyRow {

		private PreparementCollectorManyRow() {

		}

		public CollectorFromManyRows collect() {
			return new CollectorFromManyRows();
		}

	}

	public class PreparementCollectorOneRow {
		private Function<Stream<Row>, Optional<Row>> filterbyRow;

		private PreparementCollectorOneRow(
				Function<Stream<Row>, Optional<Row>> filterbyRow) {
			this.filterbyRow = filterbyRow;
		}

		public CollectorOneRow collect() {
			return new CollectorOneRow(filterbyRow);
		}

	}

	public class CollectorFromManyRows {

		private CollectorFromManyRows() {

		}

		public <T> ListHandler<T> byFunction(
				Function<Stream<Row>, Optional<List<T>>> function) {
			return new ListHandler<T>(resultset -> function.apply(resultset
					.stream()));
		}

		public <T> ListHandler<T> byField(String fieldName, Class<T> clazz) {
			return new ListHandler<T>(listByFieldName(fieldName, clazz));
		}

		private <T> Function<ResultSet, Optional<List<T>>> listByFieldName(
				final String fieldName, final Class<T> targetClass) {

			return resultset -> (resultset.isEmpty()) ? Optional.empty()
					: Optional.of(resultset.stream()
							.map(row -> row.get(fieldName, targetClass))
							.collect(Collectors.toList()));
		}
	}

	public class CollectorOneRow {

		private Function<Stream<Row>, Optional<Row>> filterbyRow;

		private CollectorOneRow(Function<Stream<Row>, Optional<Row>> filterbyRow) {
			this.filterbyRow = filterbyRow;
		}

		public <T> ListHandler<T> byFunction(
				Function<Optional<Row>, Optional<List<T>>> function) {
			return new ListHandler<T>(resultset -> filterbyRow.andThen(function)
					.apply(resultset.stream()));
		}

		public <T> ListHandler<T> byFieldSet(String fieldName, Class<T> clazz) {
			return new ListHandler<T>(setByFieldCollection(fieldName, clazz));
		}

		public <T> ListHandler<T> byFieldList(String fieldName, Class<T> clazz) {
			return new ListHandler<T>(setByFieldCollection(fieldName, clazz));
		}

		private <T> Function<ResultSet, Optional<List<T>>> setByFieldCollection(
				String fieldName, Class<T> clazz) {

			final Function<Stream<Row>, Optional<Row>> filter = filterbyRow;
			return resultset -> {
				Optional<Row> row = filter.apply(resultset.stream());
				if (!row.isPresent()) {
					return Optional.empty();
				}

				return Optional.of(row.get().getList(fieldName, clazz));
			};
		}

	}
}
