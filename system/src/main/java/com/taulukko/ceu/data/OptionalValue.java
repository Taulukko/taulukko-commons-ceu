package com.taulukko.ceu.data;

import java.util.Optional;

public interface OptionalValue {

	public default Optional<?> toOptional()
	{
		return Optional.of(this);
	}
}
