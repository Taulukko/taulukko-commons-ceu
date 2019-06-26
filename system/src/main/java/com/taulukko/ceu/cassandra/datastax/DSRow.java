package com.taulukko.ceu.cassandra.datastax;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.NotImplementedException;

import com.google.common.reflect.TypeToken;
import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.ColumnDefinitions;
import com.taulukko.ceu.data.LocalDate;
import com.taulukko.ceu.data.Row;
import com.taulukko.ceu.data.Token;
import com.taulukko.ceu.data.TupleValue;

public class DSRow implements Row {
	private com.datastax.driver.core.Row coreRow = null; 

	public DSRow(com.datastax.driver.core.Row coreRow) {
		this.coreRow = coreRow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getColumnDefinitions()
	 */
	@Override
	public ColumnDefinitions getColumnDefinitions() {
		return new DSColumnDefinitions(coreRow.getColumnDefinitions());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#isNull(java.lang.String)
	 */
	@Override
	public boolean isNull(String name) {
		return coreRow.isNull(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#isNull(int)
	 */
	@Override
	public boolean isNull(int i) {
		return coreRow.isNull(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getToken(int)
	 */
	@Override
	public DSToken getToken(int i) {
		return new DSToken(coreRow.getToken(i));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getBool(java.lang.String)
	 */
	@Override
	public boolean getBool(String name) {
		return coreRow.getBool(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getBool(int)
	 */
	@Override
	public boolean getBool(int i) {
		return coreRow.getBool(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getToken(java.lang.String)
	 */
	@Override
	public Token getToken(String name) {
		return new DSToken(coreRow.getToken(name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getByte(java.lang.String)
	 */
	@Override
	public byte getByte(String name) {
		return coreRow.getByte(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getByte(int)
	 */
	@Override
	public byte getByte(int i) {
		return coreRow.getByte(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getPartitionKeyToken()
	 */
	@Override
	public Token getPartitionKeyToken() {
		return new DSToken(coreRow.getPartitionKeyToken());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getShort(java.lang.String)
	 */
	@Override
	public short getShort(String name) {
		return coreRow.getShort(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getShort(int)
	 */
	@Override
	public short getShort(int i) {
		return coreRow.getShort(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getInt(java.lang.String)
	 */
	@Override
	public int getInt(String name) {
		return coreRow.getInt(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getInt(int)
	 */
	@Override
	public int getInt(int i) {
		return coreRow.getInt(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getLong(java.lang.String)
	 */
	@Override
	public long getLong(String name) {
		return coreRow.getLong(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getLong(int)
	 */
	@Override
	public long getLong(int i) {
		return coreRow.getLong(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.Row#getTimestamp(java.lang.String)
	 */
	@Override
	public Date getTimestamp(String name) {
		return coreRow.getTimestamp(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getTimestamp(int)
	 */
	@Override
	public Date getTimestamp(int i) {
		return coreRow.getTimestamp(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getDate(java.lang.String)
	 */
	@Override
	public LocalDate getDate(String name) {
		return new DSLocalDate(coreRow.getDate(name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getTime(java.lang.String)
	 */
	@Override
	public long getTime(String name) {
		return coreRow.getTime(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getTime(int)
	 */
	@Override
	public long getTime(int i) {
		return coreRow.getTime(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getFloat(java.lang.String)
	 */
	@Override
	public float getFloat(String name) {
		return coreRow.getFloat(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getFloat(int)
	 */
	@Override
	public float getFloat(int i) {
		return coreRow.getFloat(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getDouble(java.lang.String)
	 */
	@Override
	public double getDouble(String name) {
		return coreRow.getDouble(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getDouble(int)
	 */
	@Override
	public double getDouble(int i) {
		return coreRow.getDouble(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.ceu.cassandra.datastax.Row#getBytesUnsafe(java.lang.String)
	 */
	@Override
	public ByteBuffer getBytesUnsafe(String name) {
		return coreRow.getBytesUnsafe(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getBytesUnsafe(int)
	 */
	@Override
	public ByteBuffer getBytesUnsafe(int i) {
		return coreRow.getBytesUnsafe(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getBytes(int)
	 */
	@Override
	public ByteBuffer getBytes(int i) {
		return coreRow.getBytes(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getBytes(java.lang.String)
	 */
	@Override
	public ByteBuffer getBytes(String name) {
		return coreRow.getBytes(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getString(int)
	 */
	@Override
	public String getString(int i) {
		return coreRow.getString(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getString(java.lang.String)
	 */
	@Override
	public String getString(String name) {
		return coreRow.getString(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getVarint(int)
	 */
	@Override
	public BigInteger getVarint(int i) {
		return coreRow.getVarint(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getVarint(java.lang.String)
	 */
	@Override
	public BigInteger getVarint(String name) {
		return coreRow.getVarint(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getDecimal(java.lang.String)
	 */
	@Override
	public BigDecimal getDecimal(String name) {
		return coreRow.getDecimal(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getDecimal(int)
	 */
	@Override
	public BigDecimal getDecimal(int i) {
		return coreRow.getDecimal(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getUUID(java.lang.String)
	 */
	@Override
	public UUID getUUID(String name) {
		return coreRow.getUUID(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getUUID(int)
	 */
	@Override
	public UUID getUUID(int i) {
		return coreRow.getUUID(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getInet(java.lang.String)
	 */
	@Override
	public InetAddress getInet(String name) {
		return coreRow.getInet(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getInet(int)
	 */
	@Override
	public InetAddress getInet(int i) {
		return coreRow.getInet(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getList(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> List<T> getList(String name, Class<T> elementsClass) {
		return coreRow.getList(name, elementsClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getList(int,
	 * java.lang.Class)
	 */
	@Override
	public <T> List<T> getList(int i, Class<T> elementsClass) {
		return coreRow.getList(i, elementsClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getList(java.lang.String,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> List<T> getList(String name, TypeToken<T> elementsType) {
		return coreRow.getList(name, elementsType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getList(int,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> List<T> getList(int i, TypeToken<T> elementsType) {
		return coreRow.getList(i, elementsType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getSet(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> Set<T> getSet(String name, Class<T> elementsClass) {
		return coreRow.getSet(name, elementsClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getSet(int, java.lang.Class)
	 */
	@Override
	public <T> Set<T> getSet(int i, Class<T> elementsClass) {
		return coreRow.getSet(i, elementsClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getSet(java.lang.String,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> Set<T> getSet(String name, TypeToken<T> elementsType) {
		return coreRow.getSet(name, elementsType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getSet(int,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> Set<T> getSet(int i, TypeToken<T> elementsType) {
		return coreRow.getSet(i, elementsType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getMap(java.lang.String,
	 * java.lang.Class, java.lang.Class)
	 */
	@Override
	public <K, V> Map<K, V> getMap(String name, Class<K> keysClass,
			Class<V> valuesClass) {
		return coreRow.getMap(name, keysClass, valuesClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getMap(int, java.lang.Class,
	 * java.lang.Class)
	 */
	@Override
	public <K, V> Map<K, V> getMap(int i, Class<K> keysClass,
			Class<V> valuesClass) {
		return coreRow.getMap(i, keysClass, valuesClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getMap(java.lang.String,
	 * com.google.common.reflect.TypeToken, com.google.common.reflect.TypeToken)
	 */
	@Override
	public <K, V> Map<K, V> getMap(String name, TypeToken<K> keysType,
			TypeToken<V> valuesType) {
		return coreRow.getMap(name, keysType, valuesType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getMap(int,
	 * com.google.common.reflect.TypeToken, com.google.common.reflect.TypeToken)
	 */
	@Override
	public <K, V> Map<K, V> getMap(int i, TypeToken<K> keysType,
			TypeToken<V> valuesType) {
		return coreRow.getMap(i, keysType, valuesType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getObject(java.lang.String)
	 */
	@Override
	public Object getObject(String name) {
		return coreRow.getObject(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#getObject(int)
	 */
	@Override
	public Object getObject(int i) {
		return coreRow.getObject(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#get(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> T get(String name, Class<T> targetClass) {
		return coreRow.get(name, targetClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#get(int, java.lang.Class)
	 */
	@Override
	public <T> T get(int i, Class<T> targetClass) {
		return coreRow.get(i, targetClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#get(java.lang.String,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> T get(String name, TypeToken<T> targetType) {
		return coreRow.get(name, targetType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#get(int,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> T get(int i, TypeToken<T> targetType) {
		return coreRow.get(i, targetType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.ceu.cassandra.datastax.Row#isEmpty()
	 */
	@Override
	public boolean isEmpty() {

		ColumnDefinitions columnDefinitions = getColumnDefinitions();
		boolean noColumns = columnDefinitions.size() == 0;
		if (noColumns) {
			return true;
		}
		boolean onlyKey = (columnDefinitions.size() == 1 && columnDefinitions
				.contains("key"));
		if (onlyKey) {
			return true;
		}
		return false;
	}

	@Override
	public LocalDate getDate(int i) throws CEUException {
		throw new NotImplementedException("CEU");
	}

	@Override
	public TupleValue getTupleValue(String name) throws CEUException {
		throw new NotImplementedException("CEU");
	}

	@Override
	public TupleValue getTupleValue(int i) throws CEUException {
		throw new NotImplementedException("CEU");
	}
}