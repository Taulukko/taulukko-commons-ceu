package com.taulukko.ceu.handler;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;

public class ListHandler<T> implements Handler<List<T>> {
	private Function<ResultSet, Optional<List<T>>> identitys = null;

	public ListHandler(Function<ResultSet, Optional<List<T>>> identitys) {

		this.identitys = identitys;

	}

	@Override
	public Optional<List<T>> convert(ResultSet result) throws CEUException {
		return identitys.apply(result);
	}
}
