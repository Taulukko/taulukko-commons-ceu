package com.taulukko.ceu.handler;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;
import com.taulukko.ceu.data.Row;

public class ListHandlerBuilder {

	final List<CEUException> exceptions = new CopyOnWriteArrayList<CEUException>();

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
				Function<Stream<Row>, Optional<List<T>>> function)
				throws CEUException {
			return new ListHandler<T>(resultset -> function.apply(resultset
					.stream()));
		}

		public <T> ListHandler<T> byField(String fieldName, Class<T> clazz)
				throws CEUException {
			return new ListHandler<T>(listByFieldName(fieldName, clazz));
		}

		private <T> Function<ResultSet, Optional<List<T>>> listByFieldName(
				final String fieldName, final Class<T> targetClass)
				throws CEUException {

			Function<ResultSet, Optional<List<T>>> ret = resultset -> (resultset
					.isEmpty()) ? Optional.empty() : Optional.of(resultset
					.stream().map(row -> {
						try {
							return row.get(fieldName, targetClass);
						} catch (CEUException e) {
							exceptions.add(e);
							return null;
						}
					}).collect(Collectors.toList()));

			return ret;

		}
	}

	public class CollectorOneRow {

		private Function<Stream<Row>, Optional<Row>> filterbyRow;

		private CollectorOneRow(Function<Stream<Row>, Optional<Row>> filterbyRow) {
			this.filterbyRow = filterbyRow;
		}

		public <T> ListHandler<T> byFunction(
				Function<Optional<Row>, Optional<List<T>>> function) {
			return new ListHandler<T>(resultset -> filterbyRow
					.andThen(function).apply(resultset.stream()));
		}

		public <T> ListHandler<T> byFieldSet(String fieldName, Class<T> clazz)
				throws CEUException {
			return new ListHandler<T>(setByFieldCollection(fieldName, clazz));
		}

		public <T> ListHandler<T> byFieldList(String fieldName, Class<T> clazz)
				throws CEUException {
			return new ListHandler<T>(setByFieldCollection(fieldName, clazz));
		}

		private <T> Function<ResultSet, Optional<List<T>>> setByFieldCollection(
				String fieldName, Class<T> clazz) throws CEUException {

			final Function<Stream<Row>, Optional<Row>> filter = filterbyRow;
			Function<ResultSet, Optional<List<T>>> ret = resultset -> {
				Optional<Row> row = filter.apply(resultset.stream());
				if (!row.isPresent()) {
					return Optional.empty();
				}

				try {
					return Optional.of(row.get().getList(fieldName, clazz));
				} catch (CEUException e) {
					exceptions.add(e);
					return Optional.empty();
				}
			};

			return ret;

		}
	}
}
