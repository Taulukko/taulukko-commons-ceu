package com.taulukko.ceu.data;

import com.taulukko.ceu.CEUException;

public interface Value<R> extends ValueGetter<R>, ValueSetter<R> {

	public abstract UDTValue getUDTValue(String name) throws CEUException;

}
