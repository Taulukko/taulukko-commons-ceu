package com.taulukko.ceu.handler;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.taulukko.ceu.data.ResultSet;
import com.taulukko.ceu.data.Row;

public class BuilderSetHandler {

	private BuilderSetHandler() {
	}

	public static FilterSet build() {
		return new BuilderSetHandler().new FilterSet();
	}

	public class FilterSet {
		private Function<Stream<Row>, Stream<Row>> decorator = null;

		private FilterSet() {

		}

		public FilterByRow byFirstRow() {
			if (decorator != null) {
				return new FilterByRow(stream -> decorator.apply(stream)
						.findFirst());
			} else {
				return new FilterByRow(Stream::findFirst);
			}
		}

		public FilterByRow byAnyRow() {
			if (decorator != null) {
				return new FilterByRow(stream -> decorator.apply(stream)
						.findAny());
			} else {
				return new FilterByRow(Stream::findAny);
			}

		}

		public FilterSet filter(Predicate<Row> condition) {
			decorator = stream -> stream.filter(condition);
			return this;
		}
	}

	public class FilterByRow {
		private Function<Stream<Row>, Optional<Row>> filterbyRow;

		private FilterByRow(Function<Stream<Row>, Optional<Row>> filterbyRow) {
			this.filterbyRow = filterbyRow;
		}

		public Collector collect() {
			return new Collector(filterbyRow);
		}

	}

	public class Collector {

		private Function<Stream<Row>, Optional<Row>> filterbyRow;

		private Collector(Function<Stream<Row>, Optional<Row>> filterbyRow) {
			this.filterbyRow = filterbyRow;
		}

		public <T> SetHandler<T> byFieldName(String fieldName, Class<T> clazz) {
			return new SetHandler<T>(setByFieldname(fieldName, clazz));
		}

		private <T> Function<ResultSet, Optional<Set<T>>> setByFieldname(
				String fieldName, Class<T> clazz) {

			final Function<Stream<Row>, Optional<Row>> filter = filterbyRow;
			return resultset -> {
				Optional<Row> row = filter.apply(resultset.stream());
				if (!row.isPresent()) {
					return Optional.empty();
				}

				return Optional.of(row.get().getList(fieldName, clazz).stream()
						.distinct().collect(Collectors.toSet()));
			};
		}

	}
}
