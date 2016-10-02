package com.taulukko.ceu.handler;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;
import com.taulukko.ceu.data.Row;

public class SetHandlerBuilder {

	private SetHandlerBuilder() {
	}

	public static FilterSet build() {
		return new SetHandlerBuilder().new FilterSet();
	}

	public class FilterSet {
		private Function<Stream<Row>, Stream<Row>> decorator = null;

		private FilterSet() {

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

		public FilterSet filter(Predicate<Row> condition) {
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

		public <T> SetHandler<T> byFunction(
				Function<Stream<Row>, Optional<Set<T>>> function) {
			return new SetHandler<T>(resultset -> function.apply(resultset
					.stream()));
		}

		public <T> SetHandler<T> byField(String fieldName, Class<T> clazz)
				throws CEUException {
			return new SetHandler<T>(setByFieldName(fieldName, clazz));
		}

		private <T> Function<ResultSet, Optional<Set<T>>> setByFieldName(
				final String fieldName, final Class<T> targetClass)
				throws CEUException {

			return resultset -> (resultset.isEmpty()) ? Optional.empty()
					: Optional
							.of(resultset
									.stream()
									.map(row -> HandlerUtils
											.getSilent(row,
													fieldName, targetClass))
									.filter(v -> v != null)
									.collect(Collectors.toSet()));

		}
	}

	public class CollectorOneRow {

		private Function<Stream<Row>, Optional<Row>> filterbyRow;

		private CollectorOneRow(Function<Stream<Row>, Optional<Row>> filterbyRow) {
			this.filterbyRow = filterbyRow;
		}

		public <T> SetHandler<T> byFunction(
				Function<Optional<Row>, Optional<Set<T>>> function) {
			return new SetHandler<T>(resultset -> filterbyRow.andThen(function)
					.apply(resultset.stream()));
		}

		public <T> SetHandler<T> byFieldSet(String fieldName, Class<T> clazz)
				throws CEUException {
			return new SetHandler<T>(setByFieldCollection(fieldName, clazz));
		}

		public <T> SetHandler<T> byFieldList(String fieldName, Class<T> clazz)
				throws CEUException {
			return new SetHandler<T>(setByFieldCollection(fieldName, clazz));
		}

		private <T> Function<ResultSet, Optional<Set<T>>> setByFieldCollection(
				String fieldName, Class<T> clazz) {

			final Function<Stream<Row>, Optional<Row>> filter = filterbyRow;
			Function<ResultSet, Optional<Set<T>>> ret = resultset -> {
				Optional<Row> row = filter.apply(resultset.stream());
				if (!row.isPresent()) {
					return Optional.empty();
				}

				return Optional.of(HandlerUtils
						.getListSilent(row.get(), fieldName,
								clazz).stream().distinct()
						.collect(Collectors.toSet()));

			};
			return ret;

		}
	}
}
