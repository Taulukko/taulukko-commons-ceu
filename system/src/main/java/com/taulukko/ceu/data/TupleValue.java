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

public interface TupleValue {

	public abstract TupleType getType();

	public abstract TupleValue setBool(int i, boolean v);

	public abstract boolean equals(Object o);

	public abstract int hashCode();

	public abstract TupleValue setByte(int i, byte v);

	public abstract String toString();

	public abstract TupleValue setShort(int i, short v);

	public abstract TupleValue setInt(int i, int v);

	public abstract boolean isNull(int i);

	public abstract TupleValue setLong(int i, long v);

	public abstract boolean getBool(int i);

	public abstract TupleValue setTimestamp(int i, Date v);

	public abstract byte getByte(int i);

	public abstract TupleValue setDate(int i, LocalDate v);

	public abstract TupleValue setTime(int i, long v);

	public abstract short getShort(int i);

	public abstract TupleValue setFloat(int i, float v);

	public abstract int getInt(int i);

	public abstract TupleValue setDouble(int i, double v);

	public abstract long getLong(int i);

	public abstract TupleValue setString(int i, String v);

	public abstract TupleValue setBytes(int i, ByteBuffer v);

	public abstract Date getTimestamp(int i);

	public abstract TupleValue setBytesUnsafe(int i, ByteBuffer v);

	public abstract TupleValue setVarint(int i, BigInteger v);

	public abstract LocalDate getDate(int i);

	public abstract TupleValue setDecimal(int i, BigDecimal v);

	public abstract long getTime(int i);

	public abstract TupleValue setUUID(int i, UUID v);

	public abstract TupleValue setInet(int i, InetAddress v);

	public abstract <E> TupleValue setList(int i, List<E> v);

	public abstract float getFloat(int i);

	public abstract <E> TupleValue setList(int i, List<E> v,
			Class<E> elementsClass);

	public abstract <E> TupleValue setList(int i, List<E> v,
			TypeToken<E> elementsType);

	public abstract double getDouble(int i);

	public abstract <K, V> TupleValue setMap(int i, Map<K, V> v);

	public abstract <K, V> TupleValue setMap(int i, Map<K, V> v,
			Class<K> keysClass, Class<V> valuesClass);

	public abstract <K, V> TupleValue setMap(int i, Map<K, V> v,
			TypeToken<K> keysType, TypeToken<V> valuesType);

	public abstract ByteBuffer getBytesUnsafe(int i);

	public abstract <E> TupleValue setSet(int i, Set<E> v);

	public abstract ByteBuffer getBytes(int i);

	public abstract <E> TupleValue setSet(int i, Set<E> v,
			Class<E> elementsClass);

	public abstract String getString(int i);

	public abstract <E> TupleValue setSet(int i, Set<E> v,
			TypeToken<E> elementsType);

	public abstract BigInteger getVarint(int i);

	public abstract TupleValue setUDTValue(int i, UDTValue v);

	public abstract BigDecimal getDecimal(int i);

	public abstract TupleValue setTupleValue(int i, TupleValue v);

	public abstract <V> TupleValue set(int i, V v, Class<V> targetClass);

	public abstract UUID getUUID(int i);

	public abstract <V> TupleValue set(int i, V v, TypeToken<V> targetType);

	public abstract InetAddress getInet(int i);

	public abstract <T> List<T> getList(int i, Class<T> elementsClass);

	public abstract TupleValue setToNull(int i);

	public abstract <T> List<T> getList(int i, TypeToken<T> elementsType);

	public abstract <T> Set<T> getSet(int i, Class<T> elementsClass);

	public abstract <T> Set<T> getSet(int i, TypeToken<T> elementsType);

	public abstract <K, V> Map<K, V> getMap(int i, Class<K> keysClass,
			Class<V> valuesClass);

	public abstract <K, V> Map<K, V> getMap(int i, TypeToken<K> keysType,
			TypeToken<V> valuesType);

	public abstract UDTValue getUDTValue(int i);

	public abstract TupleValue getTupleValue(int i);

	public abstract Object getObject(int i);

	public abstract <T> T get(int i, Class<T> targetClass);

	public abstract <T> T get(int i, TypeToken<T> targetType);

}