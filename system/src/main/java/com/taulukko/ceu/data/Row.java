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
import com.taulukko.ceu.cassandra.datastax.DSToken;

public interface Row {

	public abstract ColumnDefinitions getColumnDefinitions();

	public abstract boolean isNull(String name);

	public abstract boolean isNull(int i);

	public abstract DSToken getToken(int i);

	public abstract boolean getBool(String name);

	public abstract boolean getBool(int i);

	public abstract Token getToken(String name);

	public abstract byte getByte(String name);

	public abstract byte getByte(int i);

	public abstract Token getPartitionKeyToken();

	public abstract short getShort(String name);

	public abstract short getShort(int i);

	public abstract int getInt(String name);

	public abstract int getInt(int i);

	public abstract long getLong(String name);

	public abstract long getLong(int i);

	public abstract Date getTimestamp(String name);

	public abstract Date getTimestamp(int i);

	public abstract LocalDate getDate(String name);

	public abstract long getTime(String name);

	public abstract long getTime(int i);

	public abstract float getFloat(String name);

	public abstract float getFloat(int i);

	public abstract double getDouble(String name);

	public abstract double getDouble(int i);

	public abstract ByteBuffer getBytesUnsafe(String name);

	public abstract ByteBuffer getBytesUnsafe(int i);

	public abstract ByteBuffer getBytes(int i);

	public abstract ByteBuffer getBytes(String name);

	public abstract String getString(int i);

	public abstract String getString(String name);

	public abstract BigInteger getVarint(int i);

	public abstract BigInteger getVarint(String name);

	public abstract BigDecimal getDecimal(String name);

	public abstract BigDecimal getDecimal(int i);

	public abstract UUID getUUID(String name);

	public abstract UUID getUUID(int i);

	public abstract InetAddress getInet(String name);

	public abstract InetAddress getInet(int i);

	public abstract <T> List<T> getList(String name, Class<T> elementsClass);

	public abstract <T> List<T> getList(int i, Class<T> elementsClass);

	public abstract <T> List<T> getList(String name, TypeToken<T> elementsType);

	public abstract <T> List<T> getList(int i, TypeToken<T> elementsType);

	public abstract <T> Set<T> getSet(String name, Class<T> elementsClass);

	public abstract <T> Set<T> getSet(int i, Class<T> elementsClass);

	public abstract <T> Set<T> getSet(String name, TypeToken<T> elementsType);

	public abstract <T> Set<T> getSet(int i, TypeToken<T> elementsType);

	public abstract <K, V> Map<K, V> getMap(String name, Class<K> keysClass,
			Class<V> valuesClass);

	public abstract <K, V> Map<K, V> getMap(int i, Class<K> keysClass,
			Class<V> valuesClass);

	public abstract <K, V> Map<K, V> getMap(String name, TypeToken<K> keysType,
			TypeToken<V> valuesType);

	public abstract <K, V> Map<K, V> getMap(int i, TypeToken<K> keysType,
			TypeToken<V> valuesType);

	public abstract Object getObject(String name);

	public abstract Object getObject(int i);

	public abstract <T> T get(String name, Class<T> targetClass);

	public abstract <T> T get(int i, Class<T> targetClass);

	public abstract <T> T get(String name, TypeToken<T> targetType);

	public abstract <T> T get(int i, TypeToken<T> targetType);

	public abstract boolean isEmpty();

}