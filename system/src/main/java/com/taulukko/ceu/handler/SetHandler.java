package com.taulukko.ceu.handler;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;

public class SetHandler<T> implements Handler<Set<T>> {

	private Function<ResultSet, Optional<Set<T>>> identitys = null;

	public SetHandler(Function<ResultSet, Optional<Set<T>>> identitys) {

		this.identitys = identitys;

	}

	@Override
	public Optional<Set<T>> convert(ResultSet result) throws CEUException {
		try {
			return identitys.apply(result);
		} catch (RuntimeException re) {
			throw new CEUException(re);
		}
	}
}
