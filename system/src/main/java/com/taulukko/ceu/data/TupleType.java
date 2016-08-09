package com.taulukko.ceu.data;

import com.datastax.driver.core.exceptions.InvalidTypeException;
import com.taulukko.ceu.cassandra.datastax.DSTupleType;

public interface TupleType extends OptionalValue{

	/**
	 * Returns a new value for this tuple type that uses the provided values for
	 * the components.
	 * <p/>
	 * The numbers of values passed to this method must correspond to the number
	 * of components in this tuple type. The {@code i}th parameter value will
	 * then be assigned to the {@code i}th component of the resulting tuple
	 * value.
	 *
	 * @param values
	 *            the values to use for the component of the resulting tuple.
	 * @return a new tuple values based on the provided values.
	 * @throws IllegalArgumentException
	 *             if the number of {@code values} provided does not correspond
	 *             to the number of components in this tuple type.
	 * @throws InvalidTypeException
	 *             if any of the provided value is not of the correct type for
	 *             the component.
	 */
	public abstract TupleValue newValue(Object... values);

	public abstract boolean isFrozen();

	public abstract int hashCode();

	public abstract boolean equals(Object o);

	/**
	 * Return {@code true} if this tuple type contains the given tuple type, and
	 * {@code false} otherwise.
	 * <p/>
	 * A tuple type is said to contain another one if the latter has fewer
	 * components than the former, but all of them are of the same type. E.g.
	 * the type {@code tuple<int, text>} is contained by the type
	 * {@code tuple<int, text, double>}.
	 * <p/>
	 * A contained type can be seen as a "partial" view of a containing type,
	 * where the missing components are supposed to be {@code null}.
	 *
	 * @param other
	 *            the tuple type to compare against the current one
	 * @return {@code true} if this tuple type contains the given tuple type,
	 *         and {@code false} otherwise.
	 */
	public abstract boolean contains(DSTupleType other);

	public abstract String toString();

	public abstract String asFunctionParameterString();

}