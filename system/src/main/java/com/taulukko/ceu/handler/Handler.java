package com.taulukko.ceu.handler;

import java.util.Optional;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ResultSet;
 
public interface Handler<T> {

	public Optional<T> convert(ResultSet result) throws CEUException;
}
