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

import org.apache.commons.lang.NotImplementedException;

import com.google.common.reflect.TypeToken;
import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.data.LocalDate;
import com.taulukko.ceu.data.TupleType;
import com.taulukko.ceu.data.TupleValue;
import com.taulukko.ceu.data.UDTValue;
 
public class DSTupleValue implements TupleValue {

	 

	private com.datastax.driver.core.TupleValue coreTupleValue = null;

	public DSTupleValue(com.datastax.driver.core.TupleValue tupleValue) {
		this.coreTupleValue = tupleValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getType()
	 */
	@Override
	public TupleType getType() {
		return new DSTupleType(coreTupleValue.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setBool(int, boolean)
	 */
	@Override
	public TupleValue setBool(int i, boolean v) {
		coreTupleValue.setBool(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return coreTupleValue.equals(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#hashCode()
	 */
	@Override
	public int hashCode() {
		return coreTupleValue.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setByte(int, byte)
	 */
	@Override
	public TupleValue setByte(int i, byte v) {
		coreTupleValue.setByte(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#toString()
	 */
	@Override
	public String toString() {
		return coreTupleValue.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setShort(int, short)
	 */
	@Override
	public TupleValue setShort(int i, short v) {
		coreTupleValue.setShort(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setInt(int, int)
	 */
	@Override
	public TupleValue setInt(int i, int v) {
		coreTupleValue.setInt(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#isNull(int)
	 */
	@Override
	public boolean isNull(int i) {
		return coreTupleValue.isNull(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setLong(int, long)
	 */
	@Override
	public TupleValue setLong(int i, long v) {
		coreTupleValue.setLong(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getBool(int)
	 */
	@Override
	public boolean getBool(int i) {
		return coreTupleValue.getBool(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setTimestamp(int,
	 * java.util.Date)
	 */
	@Override
	public TupleValue setTimestamp(int i, Date v) {
		coreTupleValue.setTimestamp(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getByte(int)
	 */
	@Override
	public byte getByte(int i) {
		return coreTupleValue.getByte(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setDate(int,
	 * com.datastax.driver.core.LocalDate)
	 */
	@Override
	public TupleValue setDate(int i, LocalDate v) {
		DSLocalDate dsLocalDate = (DSLocalDate) v;
		coreTupleValue.setDate(i, dsLocalDate.getCoreLocalDate());
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setTime(int, long)
	 */
	@Override
	public TupleValue setTime(int i, long v) {
		coreTupleValue.setTime(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getShort(int)
	 */
	@Override
	public short getShort(int i) {
		return coreTupleValue.getShort(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setFloat(int, float)
	 */
	@Override
	public TupleValue setFloat(int i, float v) {
		coreTupleValue.setFloat(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getInt(int)
	 */
	@Override
	public int getInt(int i) {
		return coreTupleValue.getInt(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setDouble(int, double)
	 */
	@Override
	public TupleValue setDouble(int i, double v) {
		coreTupleValue.setDouble(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getLong(int)
	 */
	@Override
	public long getLong(int i) {
		return coreTupleValue.getLong(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setString(int,
	 * java.lang.String)
	 */
	@Override
	public TupleValue setString(int i, String v) {
		coreTupleValue.setString(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setBytes(int,
	 * java.nio.ByteBuffer)
	 */
	@Override
	public TupleValue setBytes(int i, ByteBuffer v) {
		coreTupleValue.setBytes(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getTimestamp(int)
	 */
	@Override
	public Date getTimestamp(int i) {
		return coreTupleValue.getTimestamp(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setBytesUnsafe(int,
	 * java.nio.ByteBuffer)
	 */
	@Override
	public TupleValue setBytesUnsafe(int i, ByteBuffer v) {
		coreTupleValue.setBytesUnsafe(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setVarint(int,
	 * java.math.BigInteger)
	 */
	@Override
	public TupleValue setVarint(int i, BigInteger v) {
		coreTupleValue.setVarint(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getDate(int)
	 */
	@Override
	public LocalDate getDate(int i) {
		return new DSLocalDate(coreTupleValue.getDate(i));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setDecimal(int,
	 * java.math.BigDecimal)
	 */
	@Override
	public TupleValue setDecimal(int i, BigDecimal v) {
		coreTupleValue.setDecimal(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getTime(int)
	 */
	@Override
	public long getTime(int i) {
		return coreTupleValue.getTime(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setUUID(int,
	 * java.util.UUID)
	 */
	@Override
	public TupleValue setUUID(int i, UUID v) {
		coreTupleValue.setUUID(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setInet(int,
	 * java.net.InetAddress)
	 */
	@Override
	public TupleValue setInet(int i, InetAddress v) {
		coreTupleValue.setInet(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setList(int,
	 * java.util.List)
	 */
	@Override
	public <E> TupleValue setList(int i, List<E> v) {
		coreTupleValue.setList(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getFloat(int)
	 */
	@Override
	public float getFloat(int i) {
		return coreTupleValue.getFloat(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setList(int,
	 * java.util.List, java.lang.Class)
	 */
	@Override
	public <E> TupleValue setList(int i, List<E> v, Class<E> elementsClass) {
		coreTupleValue.setList(i, v, elementsClass);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setList(int,
	 * java.util.List, com.google.common.reflect.TypeToken)
	 */
	@Override
	public <E> TupleValue setList(int i, List<E> v, TypeToken<E> elementsType) {
		coreTupleValue.setList(i, v, elementsType);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getDouble(int)
	 */
	@Override
	public double getDouble(int i) {
		return coreTupleValue.getDouble(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setMap(int,
	 * java.util.Map)
	 */
	@Override
	public <K, V> TupleValue setMap(int i, Map<K, V> v) {
		coreTupleValue.setMap(i, v);
		return this;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setMap(int,
	 * java.util.Map, java.lang.Class, java.lang.Class)
	 */
	@Override
	public <K, V> TupleValue setMap(int i, Map<K, V> v, Class<K> keysClass,
			Class<V> valuesClass) {
		coreTupleValue.setMap(i, v, keysClass, valuesClass);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setMap(int,
	 * java.util.Map, com.google.common.reflect.TypeToken,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <K, V> TupleValue setMap(int i, Map<K, V> v, TypeToken<K> keysType,
			TypeToken<V> valuesType) {
		coreTupleValue.setMap(i, v, keysType, valuesType);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getBytesUnsafe(int)
	 */
	@Override
	public ByteBuffer getBytesUnsafe(int i) {
		return coreTupleValue.getBytesUnsafe(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setSet(int,
	 * java.util.Set)
	 */
	@Override
	public <E> TupleValue setSet(int i, Set<E> v) {
		coreTupleValue.setSet(i, v);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getBytes(int)
	 */
	@Override
	public ByteBuffer getBytes(int i) {
		return coreTupleValue.getBytes(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setSet(int,
	 * java.util.Set, java.lang.Class)
	 */
	@Override
	public <E> TupleValue setSet(int i, Set<E> v, Class<E> elementsClass) {
		coreTupleValue.setSet(i, v, elementsClass);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getString(int)
	 */
	@Override
	public String getString(int i) {
		return coreTupleValue.getString(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setSet(int,
	 * java.util.Set, com.google.common.reflect.TypeToken)
	 */
	@Override
	public <E> TupleValue setSet(int i, Set<E> v, TypeToken<E> elementsType) {
		coreTupleValue.setSet(i, v, elementsType);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getVarint(int)
	 */
	@Override
	public BigInteger getVarint(int i) {
		return coreTupleValue.getVarint(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setUDTValue(int,
	 * com.datastax.driver.core.UDTValue)
	 */
	@Override
	public TupleValue setUDTValue(int i, UDTValue v) {
		coreTupleValue.setUDTValue(i, ((DSUDTValue) v).getCoreUdtValue());
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getDecimal(int)
	 */
	@Override
	public BigDecimal getDecimal(int i) {
		return coreTupleValue.getDecimal(i);
	}

	public com.datastax.driver.core.TupleValue getCoreTupleValue() {
		return coreTupleValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setTupleValue(int,
	 * com.datastax.driver.core.TupleValue)
	 */
	@Override
	public TupleValue setTupleValue(int i, TupleValue v) {
		DSTupleValue dupleValue = (DSTupleValue) v;
		this.coreTupleValue.setTupleValue(i, dupleValue.getCoreTupleValue());
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#set(int, V,
	 * java.lang.Class)
	 */
	@Override
	public <V> TupleValue set(int i, V v, Class<V> targetClass) {
		coreTupleValue.set(i, v, targetClass);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getUUID(int)
	 */
	@Override
	public UUID getUUID(int i) {
		return coreTupleValue.getUUID(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#set(int, V,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <V> TupleValue set(int i, V v, TypeToken<V> targetType) {
		coreTupleValue.set(i, v, targetType);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getInet(int)
	 */
	@Override
	public InetAddress getInet(int i) {
		return coreTupleValue.getInet(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getList(int,
	 * java.lang.Class)
	 */
	@Override
	public <T> List<T> getList(int i, Class<T> elementsClass) {
		return coreTupleValue.getList(i, elementsClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#setToNull(int)
	 */
	@Override
	public TupleValue setToNull(int i) {
		coreTupleValue.setToNull(i);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getList(int,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> List<T> getList(int i, TypeToken<T> elementsType) {
		return coreTupleValue.getList(i, elementsType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getSet(int,
	 * java.lang.Class)
	 */
	@Override
	public <T> Set<T> getSet(int i, Class<T> elementsClass) {
		return coreTupleValue.getSet(i, elementsClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getSet(int,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> Set<T> getSet(int i, TypeToken<T> elementsType) {
		return coreTupleValue.getSet(i, elementsType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getMap(int,
	 * java.lang.Class, java.lang.Class)
	 */
	@Override
	public <K, V> Map<K, V> getMap(int i, Class<K> keysClass,
			Class<V> valuesClass) {
		return coreTupleValue.getMap(i, keysClass, valuesClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getMap(int,
	 * com.google.common.reflect.TypeToken, com.google.common.reflect.TypeToken)
	 */
	@Override
	public <K, V> Map<K, V> getMap(int i, TypeToken<K> keysType,
			TypeToken<V> valuesType) {
		return coreTupleValue.getMap(i, keysType, valuesType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getUDTValue(int)
	 */
	@Override
	public UDTValue getUDTValue(int i) {
		return new DSUDTValue(coreTupleValue.getUDTValue(i));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getTupleValue(int)
	 */
	@Override
	public TupleValue getTupleValue(int i) {
		coreTupleValue.getTupleValue(i);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#getObject(int)
	 */
	@Override
	public Object getObject(int i) {
		return coreTupleValue.getObject(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#get(int, java.lang.Class)
	 */
	@Override
	public <T> T get(int i, Class<T> targetClass) {
		return coreTupleValue.get(i, targetClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleValue#get(int,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> T get(int i, TypeToken<T> targetType) {
		return coreTupleValue.get(i, targetType);
	}

	@Override
	public boolean isNull(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public boolean getBool(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public byte getByte(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public short getShort(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public int getInt(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public long getLong(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setBool(String name, boolean v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public Date getTimestamp(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public LocalDate getDate(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public long getTime(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public float getFloat(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setByte(String name, byte v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public double getDouble(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public ByteBuffer getBytesUnsafe(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public ByteBuffer getBytes(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public String getString(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setShort(String name, short v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public BigInteger getVarint(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public BigDecimal getDecimal(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public UUID getUUID(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public InetAddress getInet(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setInt(String name, int v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <T> List<T> getList(String name, Class<T> elementsClass)
			throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <T> List<T> getList(String name, TypeToken<T> elementsType)
			throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <T> Set<T> getSet(String name, Class<T> elementsClass)
			throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setLong(String name, long v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <T> Set<T> getSet(String name, TypeToken<T> elementsType)
			throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <K, V> Map<K, V> getMap(String name, Class<K> keysClass,
			Class<V> valuesClass) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setTimestamp(String name, Date v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <K, V> Map<K, V> getMap(String name, TypeToken<K> keysType,
			TypeToken<V> valuesType) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setDate(String name, LocalDate v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public UDTValue getUDTValue(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue getTupleValue(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public Object getObject(String name) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <T> T get(String name, Class<T> targetClass) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setTime(String name, long v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <T> T get(String name, TypeToken<T> targetType) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setFloat(String name, float v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setDouble(String name, double v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setString(String name, String v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setBytes(String name, ByteBuffer v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setBytesUnsafe(String name, ByteBuffer v)
			throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setVarint(String name, BigInteger v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setDecimal(String name, BigDecimal v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setUUID(String name, UUID v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setInet(String name, InetAddress v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <E> TupleValue setList(String name, List<E> v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <E> TupleValue setList(String name, List<E> v, Class<E> elementsClass)
			throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <E> TupleValue setList(String name, List<E> v,
			TypeToken<E> elementsType) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <K, V> TupleValue setMap(String name, Map<K, V> v)
			throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <K, V> TupleValue setMap(String name, Map<K, V> v,
			Class<K> keysClass, Class<V> valuesClass) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <K, V> TupleValue setMap(String name, Map<K, V> v,
			TypeToken<K> keysType, TypeToken<V> valuesType) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <E> TupleValue setSet(String name, Set<E> v) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <E> TupleValue setSet(String name, Set<E> v, Class<E> elementsClass)
			throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <E> TupleValue setSet(String name, Set<E> v,
			TypeToken<E> elementsType) throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setTupleValue(String name, TupleValue v)
			throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <V> TupleValue set(String name, V v, Class<V> targetClass)
			throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public <V> TupleValue set(String name, V v, TypeToken<V> targetType)
			throws CEUException {
		throw new NotImplementedException();
	}

	@Override
	public TupleValue setToNull(String name) throws CEUException {
		throw new NotImplementedException();
	}

}
