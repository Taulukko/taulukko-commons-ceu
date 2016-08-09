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

public interface UDTValue {

	public abstract UserType getType();

	public abstract boolean equals(Object o);

	public abstract boolean isNull(String name);

	public abstract int hashCode();

	public abstract String toString();

	public abstract boolean getBool(String name);

	public abstract byte getByte(String name);

	public abstract UDTValue setBool(int i, boolean v);

	public abstract short getShort(String name);

	public abstract int getInt(String name);

	public abstract long getLong(String name);

	public abstract UDTValue setBool(String name, boolean v);

	public abstract Date getTimestamp(String name);

	public abstract UDTValue setByte(int i, byte v);

	public abstract LocalDate getDate(String name);

	public abstract boolean isNull(int i);

	public abstract long getTime(String name);

	public abstract boolean getBool(int i);

	public abstract float getFloat(String name);

	public abstract UDTValue setByte(String name, byte v);

	public abstract double getDouble(String name);

	public abstract UDTValue setShort(int i, short v);

	public abstract ByteBuffer getBytesUnsafe(String name);

	public abstract byte getByte(int i);

	public abstract ByteBuffer getBytes(String name);

	public abstract String getString(String name);

	public abstract UDTValue setShort(String name, short v);

	public abstract BigInteger getVarint(String name);

	public abstract short getShort(int i);

	public abstract UDTValue setInt(int i, int v);

	public abstract BigDecimal getDecimal(String name);

	public abstract UUID getUUID(String name);

	public abstract int getInt(int i);

	public abstract InetAddress getInet(String name);

	public abstract UDTValue setInt(String name, int v);

	public abstract <T> List<T> getList(String name, Class<T> elementsClass);

	public abstract UDTValue setLong(int i, long v);

	public abstract <T> List<T> getList(String name, TypeToken<T> elementsType);

	public abstract long getLong(int i);

	public abstract <T> Set<T> getSet(String name, Class<T> elementsClass);

	public abstract UDTValue setLong(String name, long v);

	public abstract <T> Set<T> getSet(String name, TypeToken<T> elementsType);

	public abstract UDTValue setTimestamp(int i, Date v);

	public abstract Date getTimestamp(int i);

	public abstract <K, V> Map<K, V> getMap(String name, Class<K> keysClass,
			Class<V> valuesClass);

	public abstract UDTValue setTimestamp(String name, Date v);

	public abstract LocalDate getDate(int i);

	public abstract UDTValue setDate(int i, LocalDate v);

	public abstract <K, V> Map<K, V> getMap(String name, TypeToken<K> keysType,
			TypeToken<V> valuesType);

	public abstract UDTValue setDate(String name, LocalDate v);

	public abstract long getTime(int i);

	public abstract UDTValue getUDTValue(String name);

	public abstract UDTValue setTime(int i, long v);

	public abstract TupleValue getTupleValue(String name);

	public abstract Object getObject(String name);

	public abstract float getFloat(int i);

	public abstract <T> T get(String name, Class<T> targetClass);

	public abstract UDTValue setTime(String name, long v);

	public abstract <T> T get(String name, TypeToken<T> targetType);

	public abstract UDTValue setFloat(int i, float v);

	public abstract double getDouble(int i);

	public abstract UDTValue setFloat(String name, float v);

	public abstract UDTValue setDouble(int i, double v);

	public abstract ByteBuffer getBytesUnsafe(int i);

	public abstract ByteBuffer getBytes(int i);

	public abstract UDTValue setDouble(String name, double v);

	public abstract String getString(int i);

	public abstract UDTValue setString(int i, String v);

	public abstract BigInteger getVarint(int i);

	public abstract UDTValue setString(String name, String v);

	public abstract UDTValue setBytes(int i, ByteBuffer v);

	public abstract BigDecimal getDecimal(int i);

	public abstract UDTValue setBytes(String name, ByteBuffer v);

	public abstract UUID getUUID(int i);

	public abstract UDTValue setBytesUnsafe(int i, ByteBuffer v);

	public abstract InetAddress getInet(int i);

	public abstract UDTValue setBytesUnsafe(String name, ByteBuffer v);

	public abstract <T> List<T> getList(int i, Class<T> elementsClass);

	public abstract UDTValue setVarint(int i, BigInteger v);

	public abstract <T> List<T> getList(int i, TypeToken<T> elementsType);

	public abstract UDTValue setVarint(String name, BigInteger v);

	public abstract UDTValue setDecimal(int i, BigDecimal v);

	public abstract <T> Set<T> getSet(int i, Class<T> elementsClass);

	public abstract UDTValue setDecimal(String name, BigDecimal v);

	public abstract UDTValue setUUID(int i, UUID v);

	public abstract <T> Set<T> getSet(int i, TypeToken<T> elementsType);

	public abstract UDTValue setUUID(String name, UUID v);

	public abstract UDTValue setInet(int i, InetAddress v);

	public abstract <K, V> Map<K, V> getMap(int i, Class<K> keysClass,
			Class<V> valuesClass);

	public abstract UDTValue setInet(String name, InetAddress v);

	public abstract <K, V> Map<K, V> getMap(int i, TypeToken<K> keysType,
			TypeToken<V> valuesType);

	public abstract UDTValue getUDTValue(int i);

	public abstract <E> UDTValue setList(int i, List<E> v);

	public abstract TupleValue getTupleValue(int i);

	public abstract <E> UDTValue setList(int i, List<E> v,
			Class<E> elementsClass);

	public abstract Object getObject(int i);

	public abstract <E> UDTValue setList(int i, List<E> v,
			TypeToken<E> elementsType);

	public abstract <T> T get(int i, Class<T> targetClass);

	public abstract <T> T get(int i, TypeToken<T> targetType);

	public abstract <E> UDTValue setList(String name, List<E> v);

	public abstract <E> UDTValue setList(String name, List<E> v,
			Class<E> elementsClass);

	public abstract <E> UDTValue setList(String name, List<E> v,
			TypeToken<E> elementsType);

	public abstract <K, V> UDTValue setMap(int i, Map<K, V> v);

	public abstract <K, V> UDTValue setMap(int i, Map<K, V> v,
			Class<K> keysClass, Class<V> valuesClass);

	public abstract <K, V> UDTValue setMap(int i, Map<K, V> v,
			TypeToken<K> keysType, TypeToken<V> valuesType);

	public abstract <K, V> UDTValue setMap(String name, Map<K, V> v);

	public abstract <K, V> UDTValue setMap(String name, Map<K, V> v,
			Class<K> keysClass, Class<V> valuesClass);

	public abstract <K, V> UDTValue setMap(String name, Map<K, V> v,
			TypeToken<K> keysType, TypeToken<V> valuesType);

	public abstract <E> UDTValue setSet(int i, Set<E> v);

	public abstract <E> UDTValue setSet(int i, Set<E> v,
			Class<E> elementsClass);

	public abstract <E> UDTValue setSet(int i, Set<E> v,
			TypeToken<E> elementsType);

	public abstract <E> UDTValue setSet(String name, Set<E> v);

	public abstract <E> UDTValue setSet(String name, Set<E> v,
			Class<E> elementsClass);

	public abstract <E> UDTValue setSet(String name, Set<E> v,
			TypeToken<E> elementsType);

	public abstract UDTValue setUDTValue(int i, UDTValue v);

	public abstract UDTValue setUDTValue(String name, UDTValue v);

	public abstract UDTValue setTupleValue(int i, TupleValue v);

	public abstract UDTValue setTupleValue(String name, TupleValue v);

	public abstract <V> UDTValue set(int i, V v, Class<V> targetClass);

	public abstract <V> UDTValue set(String name, V v, Class<V> targetClass);

	public abstract <V> UDTValue set(int i, V v, TypeToken<V> targetType);

	public abstract <V> UDTValue set(String name, V v, TypeToken<V> targetType);

	public abstract UDTValue setToNull(int i);

	public abstract UDTValue setToNull(String name);

	public abstract com.datastax.driver.core.UDTValue getCoreUdtValue();

}