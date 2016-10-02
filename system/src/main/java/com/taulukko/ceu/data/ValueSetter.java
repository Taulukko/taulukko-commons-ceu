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

public interface ValueSetter<R> {

	public abstract R setBool(int i, boolean v) throws CEUException;

	public abstract R setBool(String name, boolean v) throws CEUException;

	public abstract R setByte(int i, byte v) throws CEUException;

	public abstract R setByte(String name, byte v) throws CEUException;

	public abstract R setShort(int i, short v) throws CEUException;

	public abstract R setShort(String name, short v) throws CEUException;

	public abstract R setInt(int i, int v) throws CEUException;

	public abstract R setInt(String name, int v) throws CEUException;

	public abstract R setLong(int i, long v) throws CEUException;

	public abstract R setLong(String name, long v) throws CEUException;

	public abstract R setTimestamp(int i, Date v) throws CEUException;

	public abstract R setTimestamp(String name, Date v) throws CEUException;

	public abstract R setDate(int i, LocalDate v) throws CEUException;

	public abstract R setDate(String name, LocalDate v) throws CEUException;

	public abstract R setTime(int i, long v) throws CEUException;

	public abstract R setTime(String name, long v) throws CEUException;

	public abstract R setFloat(int i, float v) throws CEUException;

	public abstract R setFloat(String name, float v) throws CEUException;

	public abstract R setDouble(int i, double v) throws CEUException;

	public abstract R setDouble(String name, double v) throws CEUException;

	public abstract R setString(int i, String v) throws CEUException;

	public abstract R setString(String name, String v) throws CEUException;

	public abstract R setBytes(int i, ByteBuffer v) throws CEUException;

	public abstract R setBytes(String name, ByteBuffer v) throws CEUException;

	public abstract R setBytesUnsafe(int i, ByteBuffer v) throws CEUException;

	public abstract R setBytesUnsafe(String name, ByteBuffer v)
			throws CEUException;

	public abstract R setVarint(int i, BigInteger v) throws CEUException;

	public abstract R setVarint(String name, BigInteger v) throws CEUException;

	public abstract R setDecimal(int i, BigDecimal v) throws CEUException;

	public abstract R setDecimal(String name, BigDecimal v) throws CEUException;

	public abstract R setUUID(int i, UUID v) throws CEUException;

	public abstract R setUUID(String name, UUID v) throws CEUException;

	public abstract R setInet(int i, InetAddress v) throws CEUException;

	public abstract R setInet(String name, InetAddress v) throws CEUException;

	public abstract <E> R setList(int i, List<E> v) throws CEUException;

	public abstract <E> R setList(int i, List<E> v, Class<E> elementsClass)
			throws CEUException;

	public abstract <E> R setList(int i, List<E> v, TypeToken<E> elementsType)
			throws CEUException;

	public abstract <E> R setList(String name, List<E> v) throws CEUException;

	public abstract <E> R setList(String name, List<E> v, Class<E> elementsClass)
			throws CEUException;

	public abstract <E> R setList(String name, List<E> v,
			TypeToken<E> elementsType) throws CEUException;

	public abstract <K, V> R setMap(int i, Map<K, V> v) throws CEUException;

	public abstract <K, V> R setMap(int i, Map<K, V> v, Class<K> keysClass,
			Class<V> valuesClass) throws CEUException;

	public abstract <K, V> R setMap(int i, Map<K, V> v, TypeToken<K> keysType,
			TypeToken<V> valuesType) throws CEUException;

	public abstract <K, V> R setMap(String name, Map<K, V> v)
			throws CEUException;

	public abstract <K, V> R setMap(String name, Map<K, V> v,
			Class<K> keysClass, Class<V> valuesClass) throws CEUException;

	public abstract <K, V> R setMap(String name, Map<K, V> v,
			TypeToken<K> keysType, TypeToken<V> valuesType) throws CEUException;

	public abstract <E> R setSet(int i, Set<E> v) throws CEUException;

	public abstract <E> R setSet(int i, Set<E> v, Class<E> elementsClass)
			throws CEUException;

	public abstract <E> R setSet(int i, Set<E> v, TypeToken<E> elementsType)
			throws CEUException;

	public abstract <E> R setSet(String name, Set<E> v) throws CEUException;

	public abstract <E> R setSet(String name, Set<E> v, Class<E> elementsClass)
			throws CEUException;

	public abstract <E> R setSet(String name, Set<E> v,
			TypeToken<E> elementsType) throws CEUException;

	public abstract R setTupleValue(int i, TupleValue v) throws CEUException;

	public abstract R setTupleValue(String name, TupleValue v)
			throws CEUException;

	public abstract <V> R set(int i, V v, Class<V> targetClass)
			throws CEUException;

	public abstract <V> R set(String name, V v, Class<V> targetClass)
			throws CEUException;

	public abstract <V> R set(int i, V v, TypeToken<V> targetType)
			throws CEUException;

	public abstract <V> R set(String name, V v, TypeToken<V> targetType)
			throws CEUException;

	public abstract R setToNull(int i) throws CEUException;

	public abstract R setToNull(String name) throws CEUException;

}