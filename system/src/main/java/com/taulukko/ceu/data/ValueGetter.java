package com.taulukko.ceu.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.reflect.TypeToken;
import com.taulukko.ceu.CEUException;

public interface ValueGetter<R> {

	public abstract boolean isNull(int i) throws CEUException;

	public abstract boolean isNull(String name) throws CEUException;

	public abstract boolean getBool(String name) throws CEUException;

	public abstract byte getByte(String name) throws CEUException;

	public abstract short getShort(String name) throws CEUException;

	public abstract int getInt(String name) throws CEUException;

	public abstract long getLong(String name) throws CEUException;

	public abstract Date getTimestamp(String name) throws CEUException;

	public abstract LocalDate getDate(String name) throws CEUException;

	public abstract long getTime(String name) throws CEUException;

	public abstract boolean getBool(int i) throws CEUException;

	public abstract float getFloat(String name) throws CEUException;

	public abstract double getDouble(String name) throws CEUException;

	public abstract ByteBuffer getBytesUnsafe(String name) throws CEUException;

	public abstract byte getByte(int i) throws CEUException;

	public abstract ByteBuffer getBytes(String name) throws CEUException;

	public abstract String getString(String name) throws CEUException;

	public abstract BigInteger getVarint(String name) throws CEUException;

	public abstract short getShort(int i) throws CEUException;

	public abstract BigDecimal getDecimal(String name) throws CEUException;

	public abstract UUID getUUID(String name) throws CEUException;

	public abstract int getInt(int i) throws CEUException;

	public abstract InetAddress getInet(String name) throws CEUException;

	public abstract <T> List<T> getList(String name, Class<T> elementsClass)
			throws CEUException;

	public abstract <T> List<T> getList(String name, TypeToken<T> elementsType)
			throws CEUException;

	public abstract long getLong(int i) throws CEUException;

	public abstract <T> Set<T> getSet(String name, Class<T> elementsClass)
			throws CEUException;

	public abstract <T> Set<T> getSet(String name, TypeToken<T> elementsType)
			throws CEUException;

	public abstract Date getTimestamp(int i) throws CEUException;

	public abstract <K, V> Map<K, V> getMap(String name, Class<K> keysClass,
			Class<V> valuesClass) throws CEUException;

	public abstract LocalDate getDate(int i) throws CEUException;

	public abstract <K, V> Map<K, V> getMap(String name, TypeToken<K> keysType,
			TypeToken<V> valuesType) throws CEUException;

	public abstract long getTime(int i) throws CEUException;

	public abstract TupleValue getTupleValue(String name) throws CEUException;

	public abstract Object getObject(String name) throws CEUException;

	public abstract float getFloat(int i) throws CEUException;

	public abstract <T> T get(String name, Class<T> targetClass)
			throws CEUException;

	public abstract <T> T get(String name, TypeToken<T> targetType)
			throws CEUException;

	public abstract double getDouble(int i) throws CEUException;

	public abstract ByteBuffer getBytesUnsafe(int i) throws CEUException;

	public abstract ByteBuffer getBytes(int i) throws CEUException;

	public abstract String getString(int i) throws CEUException;

	public abstract BigInteger getVarint(int i) throws CEUException;

	public abstract BigDecimal getDecimal(int i) throws CEUException;

	public abstract UUID getUUID(int i) throws CEUException;

	public abstract InetAddress getInet(int i) throws CEUException;

	public abstract <T> List<T> getList(int i, Class<T> elementsClass)
			throws CEUException;

	public abstract <T> List<T> getList(int i, TypeToken<T> elementsType)
			throws CEUException;

	public abstract <T> Set<T> getSet(int i, Class<T> elementsClass)
			throws CEUException;

	public abstract <T> Set<T> getSet(int i, TypeToken<T> elementsType)
			throws CEUException;

	public abstract <K, V> Map<K, V> getMap(int i, Class<K> keysClass,
			Class<V> valuesClass) throws CEUException;

	public abstract <K, V> Map<K, V> getMap(int i, TypeToken<K> keysType,
			TypeToken<V> valuesType) throws CEUException;

	public abstract TupleValue getTupleValue(int i) throws CEUException;

	public abstract Object getObject(int i) throws CEUException;

	public abstract <T> T get(int i, Class<T> targetClass) throws CEUException;

	public abstract <T> T get(int i, TypeToken<T> targetType)
			throws CEUException;

}