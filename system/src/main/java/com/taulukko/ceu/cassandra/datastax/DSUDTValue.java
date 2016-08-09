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

import com.google.common.reflect.TypeToken;
import com.taulukko.ceu.data.LocalDate;
import com.taulukko.ceu.data.TupleValue;
import com.taulukko.ceu.data.UDTValue;
import com.taulukko.ceu.data.UserType;

public class DSUDTValue implements UDTValue {

	private com.datastax.driver.core.UDTValue coreUdtValue = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getType()
	 */
	@Override
	public UserType getType() {
		return new DSUserType(coreUdtValue.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return coreUdtValue.equals(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#isNull(java.lang.String)
	 */
	@Override
	public boolean isNull(String name) {
		return coreUdtValue.isNull(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#hashCode()
	 */
	@Override
	public int hashCode() {
		return coreUdtValue.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#toString()
	 */
	@Override
	public String toString() {
		return coreUdtValue.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getBool(java.lang.String)
	 */
	@Override
	public boolean getBool(String name) {
		return coreUdtValue.getBool(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getByte(java.lang.String)
	 */
	@Override
	public byte getByte(String name) {
		return coreUdtValue.getByte(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setBool(int, boolean)
	 */
	@Override
	public UDTValue setBool(int i, boolean v) {
		return new DSUDTValue(coreUdtValue.setBool(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getShort(java.lang.String)
	 */
	@Override
	public short getShort(String name) {
		return coreUdtValue.getShort(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getInt(java.lang.String)
	 */
	@Override
	public int getInt(String name) {
		return coreUdtValue.getInt(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getLong(java.lang.String)
	 */
	@Override
	public long getLong(String name) {
		return coreUdtValue.getLong(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setBool(java.lang.String,
	 * boolean)
	 */
	@Override
	public UDTValue setBool(String name, boolean v) {
		return new DSUDTValue(coreUdtValue.setBool(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.UDTValue#getTimestamp(java.lang.String)
	 */
	@Override
	public Date getTimestamp(String name) {
		return coreUdtValue.getTimestamp(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setByte(int, byte)
	 */
	@Override
	public UDTValue setByte(int i, byte v) {
		return new DSUDTValue(coreUdtValue.setByte(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getDate(java.lang.String)
	 */
	@Override
	public LocalDate getDate(String name) {
		return new DSLocalDate(coreUdtValue.getDate(name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#isNull(int)
	 */
	@Override
	public boolean isNull(int i) {
		return coreUdtValue.isNull(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getTime(java.lang.String)
	 */
	@Override
	public long getTime(String name) {
		return coreUdtValue.getTime(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getBool(int)
	 */
	@Override
	public boolean getBool(int i) {
		return coreUdtValue.getBool(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getFloat(java.lang.String)
	 */
	@Override
	public float getFloat(String name) {
		return coreUdtValue.getFloat(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setByte(java.lang.String,
	 * byte)
	 */
	@Override
	public UDTValue setByte(String name, byte v) {
		return new DSUDTValue(coreUdtValue.setByte(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getDouble(java.lang.String)
	 */
	@Override
	public double getDouble(String name) {
		return coreUdtValue.getDouble(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setShort(int, short)
	 */
	@Override
	public UDTValue setShort(int i, short v) {
		return new DSUDTValue(coreUdtValue.setShort(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.UDTValue#getBytesUnsafe(java.lang.String)
	 */
	@Override
	public ByteBuffer getBytesUnsafe(String name) {
		return coreUdtValue.getBytesUnsafe(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getByte(int)
	 */
	@Override
	public byte getByte(int i) {
		return coreUdtValue.getByte(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getBytes(java.lang.String)
	 */
	@Override
	public ByteBuffer getBytes(String name) {
		return coreUdtValue.getBytes(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getString(java.lang.String)
	 */
	@Override
	public String getString(String name) {
		return coreUdtValue.getString(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setShort(java.lang.String,
	 * short)
	 */
	@Override
	public UDTValue setShort(String name, short v) {
		return new DSUDTValue(coreUdtValue.setShort(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getVarint(java.lang.String)
	 */
	@Override
	public BigInteger getVarint(String name) {
		return coreUdtValue.getVarint(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getShort(int)
	 */
	@Override
	public short getShort(int i) {
		return coreUdtValue.getShort(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setInt(int, int)
	 */
	@Override
	public UDTValue setInt(int i, int v) {
		return new DSUDTValue(coreUdtValue.setInt(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.UDTValue#getDecimal(java.lang.String)
	 */
	@Override
	public BigDecimal getDecimal(String name) {
		return coreUdtValue.getDecimal(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getUUID(java.lang.String)
	 */
	@Override
	public UUID getUUID(String name) {
		return coreUdtValue.getUUID(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getInt(int)
	 */
	@Override
	public int getInt(int i) {
		return coreUdtValue.getInt(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getInet(java.lang.String)
	 */
	@Override
	public InetAddress getInet(String name) {
		return coreUdtValue.getInet(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setInt(java.lang.String,
	 * int)
	 */
	@Override
	public UDTValue setInt(String name, int v) {
		return new DSUDTValue(coreUdtValue.setInt(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getList(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> List<T> getList(String name, Class<T> elementsClass) {
		return coreUdtValue.getList(name, elementsClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setLong(int, long)
	 */
	@Override
	public UDTValue setLong(int i, long v) {
		return new DSUDTValue(coreUdtValue.setLong(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getList(java.lang.String,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> List<T> getList(String name, TypeToken<T> elementsType) {
		return coreUdtValue.getList(name, elementsType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getLong(int)
	 */
	@Override
	public long getLong(int i) {
		return coreUdtValue.getLong(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getSet(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> Set<T> getSet(String name, Class<T> elementsClass) {
		return coreUdtValue.getSet(name, elementsClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setLong(java.lang.String,
	 * long)
	 */
	@Override
	public UDTValue setLong(String name, long v) {
		return new DSUDTValue(coreUdtValue.setLong(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getSet(java.lang.String,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> Set<T> getSet(String name, TypeToken<T> elementsType) {
		return coreUdtValue.getSet(name, elementsType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setTimestamp(int,
	 * java.util.Date)
	 */
	@Override
	public UDTValue setTimestamp(int i, Date v) {
		return new DSUDTValue(coreUdtValue.setTimestamp(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getTimestamp(int)
	 */
	@Override
	public Date getTimestamp(int i) {
		return coreUdtValue.getTimestamp(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getMap(java.lang.String,
	 * java.lang.Class, java.lang.Class)
	 */
	@Override
	public <K, V> Map<K, V> getMap(String name, Class<K> keysClass,
			Class<V> valuesClass) {
		return coreUdtValue.getMap(name, keysClass, valuesClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.UDTValue#setTimestamp(java.lang.String,
	 * java.util.Date)
	 */
	@Override
	public UDTValue setTimestamp(String name, Date v) {
		return new DSUDTValue(coreUdtValue.setTimestamp(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getDate(int)
	 */
	@Override
	public LocalDate getDate(int i) {
		return new DSLocalDate(coreUdtValue.getDate(i));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setDate(int,
	 * com.taulukko.cassandra.data.LocalDate)
	 */
	@Override
	public UDTValue setDate(int i, LocalDate v) {
		return new DSUDTValue(coreUdtValue.setDate(i,
				((DSLocalDate) v).getCoreLocalDate()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getMap(java.lang.String,
	 * com.google.common.reflect.TypeToken, com.google.common.reflect.TypeToken)
	 */
	@Override
	public <K, V> Map<K, V> getMap(String name, TypeToken<K> keysType,
			TypeToken<V> valuesType) {
		return coreUdtValue.getMap(name, keysType, valuesType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setDate(java.lang.String,
	 * com.taulukko.cassandra.data.LocalDate)
	 */
	@Override
	public UDTValue setDate(String name, LocalDate v) {
		return new DSUDTValue(coreUdtValue.setDate(name,
				((DSLocalDate) v).getCoreLocalDate()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getTime(int)
	 */
	@Override
	public long getTime(int i) {
		return coreUdtValue.getTime(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.UDTValue#getUDTValue(java.lang.String)
	 */
	@Override
	public UDTValue getUDTValue(String name) {
		return new DSUDTValue(coreUdtValue.getUDTValue(name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setTime(int, long)
	 */
	@Override
	public UDTValue setTime(int i, long v) {
		return new DSUDTValue(coreUdtValue.setTime(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.UDTValue#getTupleValue(java.lang.String)
	 */
	@Override
	public TupleValue getTupleValue(String name) {
		return new DSTupleValue(coreUdtValue.getTupleValue(name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getObject(java.lang.String)
	 */
	@Override
	public Object getObject(String name) {
		return coreUdtValue.getObject(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getFloat(int)
	 */
	@Override
	public float getFloat(int i) {
		return coreUdtValue.getFloat(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#get(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> T get(String name, Class<T> targetClass) {
		return coreUdtValue.get(name, targetClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setTime(java.lang.String,
	 * long)
	 */
	@Override
	public UDTValue setTime(String name, long v) {
		return new DSUDTValue(coreUdtValue.setTime(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#get(java.lang.String,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> T get(String name, TypeToken<T> targetType) {
		return coreUdtValue.get(name, targetType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setFloat(int, float)
	 */
	@Override
	public UDTValue setFloat(int i, float v) {
		return new DSUDTValue(coreUdtValue.setFloat(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getDouble(int)
	 */
	@Override
	public double getDouble(int i) {
		return coreUdtValue.getDouble(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setFloat(java.lang.String,
	 * float)
	 */
	@Override
	public UDTValue setFloat(String name, float v) {
		return new DSUDTValue(coreUdtValue.setFloat(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setDouble(int, double)
	 */
	@Override
	public UDTValue setDouble(int i, double v) {
		return new DSUDTValue(coreUdtValue.setDouble(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getBytesUnsafe(int)
	 */
	@Override
	public ByteBuffer getBytesUnsafe(int i) {
		return coreUdtValue.getBytesUnsafe(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getBytes(int)
	 */
	@Override
	public ByteBuffer getBytes(int i) {
		return coreUdtValue.getBytes(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setDouble(java.lang.String,
	 * double)
	 */
	@Override
	public UDTValue setDouble(String name, double v) {
		return new DSUDTValue(coreUdtValue.setDouble(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getString(int)
	 */
	@Override
	public String getString(int i) {
		return coreUdtValue.getString(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setString(int,
	 * java.lang.String)
	 */
	@Override
	public UDTValue setString(int i, String v) {
		return new DSUDTValue(coreUdtValue.setString(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getVarint(int)
	 */
	@Override
	public BigInteger getVarint(int i) {
		return coreUdtValue.getVarint(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setString(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public UDTValue setString(String name, String v) {
		return new DSUDTValue(coreUdtValue.setString(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setBytes(int,
	 * java.nio.ByteBuffer)
	 */
	@Override
	public UDTValue setBytes(int i, ByteBuffer v) {
		return new DSUDTValue(coreUdtValue.setBytes(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getDecimal(int)
	 */
	@Override
	public BigDecimal getDecimal(int i) {
		return coreUdtValue.getDecimal(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setBytes(java.lang.String,
	 * java.nio.ByteBuffer)
	 */
	@Override
	public UDTValue setBytes(String name, ByteBuffer v) {
		return new DSUDTValue(coreUdtValue.setBytes(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getUUID(int)
	 */
	@Override
	public UUID getUUID(int i) {
		return coreUdtValue.getUUID(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setBytesUnsafe(int,
	 * java.nio.ByteBuffer)
	 */
	@Override
	public UDTValue setBytesUnsafe(int i, ByteBuffer v) {
		return new DSUDTValue(coreUdtValue.setBytesUnsafe(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getInet(int)
	 */
	@Override
	public InetAddress getInet(int i) {
		return coreUdtValue.getInet(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.UDTValue#setBytesUnsafe(java.lang.String,
	 * java.nio.ByteBuffer)
	 */
	@Override
	public UDTValue setBytesUnsafe(String name, ByteBuffer v) {
		return new DSUDTValue(coreUdtValue.setBytesUnsafe(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getList(int,
	 * java.lang.Class)
	 */
	@Override
	public <T> List<T> getList(int i, Class<T> elementsClass) {
		return coreUdtValue.getList(i, elementsClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setVarint(int,
	 * java.math.BigInteger)
	 */
	@Override
	public UDTValue setVarint(int i, BigInteger v) {
		return new DSUDTValue(coreUdtValue.setVarint(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getList(int,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> List<T> getList(int i, TypeToken<T> elementsType) {
		return coreUdtValue.getList(i, elementsType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setVarint(java.lang.String,
	 * java.math.BigInteger)
	 */
	@Override
	public UDTValue setVarint(String name, BigInteger v) {
		return new DSUDTValue(coreUdtValue.setVarint(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setDecimal(int,
	 * java.math.BigDecimal)
	 */
	@Override
	public UDTValue setDecimal(int i, BigDecimal v) {
		return new DSUDTValue(coreUdtValue.setDecimal(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getSet(int,
	 * java.lang.Class)
	 */
	@Override
	public <T> Set<T> getSet(int i, Class<T> elementsClass) {
		return coreUdtValue.getSet(i, elementsClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.UDTValue#setDecimal(java.lang.String,
	 * java.math.BigDecimal)
	 */
	@Override
	public UDTValue setDecimal(String name, BigDecimal v) {
		return new DSUDTValue(coreUdtValue.setDecimal(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setUUID(int,
	 * java.util.UUID)
	 */
	@Override
	public UDTValue setUUID(int i, UUID v) {
		return new DSUDTValue(coreUdtValue.setUUID(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getSet(int,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> Set<T> getSet(int i, TypeToken<T> elementsType) {
		return coreUdtValue.getSet(i, elementsType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setUUID(java.lang.String,
	 * java.util.UUID)
	 */
	@Override
	public UDTValue setUUID(String name, UUID v) {
		return new DSUDTValue(coreUdtValue.setUUID(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setInet(int,
	 * java.net.InetAddress)
	 */
	@Override
	public UDTValue setInet(int i, InetAddress v) {
		return new DSUDTValue(coreUdtValue.setInet(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getMap(int,
	 * java.lang.Class, java.lang.Class)
	 */
	@Override
	public <K, V> Map<K, V> getMap(int i, Class<K> keysClass,
			Class<V> valuesClass) {
		return coreUdtValue.getMap(i, keysClass, valuesClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setInet(java.lang.String,
	 * java.net.InetAddress)
	 */
	@Override
	public UDTValue setInet(String name, InetAddress v) {
		return new DSUDTValue(coreUdtValue.setInet(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getMap(int,
	 * com.google.common.reflect.TypeToken, com.google.common.reflect.TypeToken)
	 */
	@Override
	public <K, V> Map<K, V> getMap(int i, TypeToken<K> keysType,
			TypeToken<V> valuesType) {
		return coreUdtValue.getMap(i, keysType, valuesType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getUDTValue(int)
	 */
	@Override
	public UDTValue getUDTValue(int i) {
		return new DSUDTValue(coreUdtValue.getUDTValue(i));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setList(int,
	 * java.util.List)
	 */
	@Override
	public <E> UDTValue setList(int i, List<E> v) {
		return new DSUDTValue(coreUdtValue.setList(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getTupleValue(int)
	 */
	@Override
	public TupleValue getTupleValue(int i) {
		return new DSTupleValue(coreUdtValue.getTupleValue(i));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setList(int,
	 * java.util.List, java.lang.Class)
	 */
	@Override
	public <E> UDTValue setList(int i, List<E> v, Class<E> elementsClass) {
		return new DSUDTValue(coreUdtValue.setList(i, v, elementsClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getObject(int)
	 */
	@Override
	public Object getObject(int i) {
		return coreUdtValue.getObject(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setList(int,
	 * java.util.List, com.google.common.reflect.TypeToken)
	 */
	@Override
	public <E> UDTValue setList(int i, List<E> v, TypeToken<E> elementsType) {
		return new DSUDTValue(coreUdtValue.setList(i, v, elementsType));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#get(int, java.lang.Class)
	 */
	@Override
	public <T> T get(int i, Class<T> targetClass) {
		return coreUdtValue.get(i, targetClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#get(int,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <T> T get(int i, TypeToken<T> targetType) {
		return coreUdtValue.get(i, targetType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setList(java.lang.String,
	 * java.util.List)
	 */
	@Override
	public <E> UDTValue setList(String name, List<E> v) {
		return new DSUDTValue(coreUdtValue.setList(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setList(java.lang.String,
	 * java.util.List, java.lang.Class)
	 */
	@Override
	public <E> UDTValue setList(String name, List<E> v, Class<E> elementsClass) {
		return new DSUDTValue(coreUdtValue.setList(name, v, elementsClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setList(java.lang.String,
	 * java.util.List, com.google.common.reflect.TypeToken)
	 */
	@Override
	public <E> UDTValue setList(String name, List<E> v,
			TypeToken<E> elementsType) {
		return new DSUDTValue(coreUdtValue.setList(name, v, elementsType));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setMap(int, java.util.Map)
	 */
	@Override
	public <K, V> UDTValue setMap(int i, Map<K, V> v) {
		return new DSUDTValue(coreUdtValue.setMap(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setMap(int, java.util.Map,
	 * java.lang.Class, java.lang.Class)
	 */
	@Override
	public <K, V> UDTValue setMap(int i, Map<K, V> v, Class<K> keysClass,
			Class<V> valuesClass) {
		return new DSUDTValue(coreUdtValue.setMap(i, v, keysClass, valuesClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setMap(int, java.util.Map,
	 * com.google.common.reflect.TypeToken, com.google.common.reflect.TypeToken)
	 */
	@Override
	public <K, V> UDTValue setMap(int i, Map<K, V> v, TypeToken<K> keysType,
			TypeToken<V> valuesType) {
		return new DSUDTValue(coreUdtValue.setMap(i, v, keysType, valuesType));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setMap(java.lang.String,
	 * java.util.Map)
	 */
	@Override
	public <K, V> UDTValue setMap(String name, Map<K, V> v) {
		return new DSUDTValue(coreUdtValue.setMap(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setMap(java.lang.String,
	 * java.util.Map, java.lang.Class, java.lang.Class)
	 */
	@Override
	public <K, V> UDTValue setMap(String name, Map<K, V> v, Class<K> keysClass,
			Class<V> valuesClass) {
		return new DSUDTValue(coreUdtValue.setMap(name, v, keysClass,
				valuesClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setMap(java.lang.String,
	 * java.util.Map, com.google.common.reflect.TypeToken,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <K, V> UDTValue setMap(String name, Map<K, V> v,
			TypeToken<K> keysType, TypeToken<V> valuesType) {
		return new DSUDTValue(
				coreUdtValue.setMap(name, v, keysType, valuesType));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setSet(int, java.util.Set)
	 */
	@Override
	public <E> UDTValue setSet(int i, Set<E> v) {
		return new DSUDTValue(coreUdtValue.setSet(i, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setSet(int, java.util.Set,
	 * java.lang.Class)
	 */
	@Override
	public <E> UDTValue setSet(int i, Set<E> v, Class<E> elementsClass) {
		return new DSUDTValue(coreUdtValue.setSet(i, v, elementsClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setSet(int, java.util.Set,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <E> UDTValue setSet(int i, Set<E> v, TypeToken<E> elementsType) {
		return new DSUDTValue(coreUdtValue.setSet(i, v, elementsType));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setSet(java.lang.String,
	 * java.util.Set)
	 */
	@Override
	public <E> UDTValue setSet(String name, Set<E> v) {
		return new DSUDTValue(coreUdtValue.setSet(name, v));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setSet(java.lang.String,
	 * java.util.Set, java.lang.Class)
	 */
	@Override
	public <E> UDTValue setSet(String name, Set<E> v, Class<E> elementsClass) {
		return new DSUDTValue(coreUdtValue.setSet(name, v, elementsClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setSet(java.lang.String,
	 * java.util.Set, com.google.common.reflect.TypeToken)
	 */
	@Override
	public <E> UDTValue setSet(String name, Set<E> v, TypeToken<E> elementsType) {
		return new DSUDTValue(coreUdtValue.setSet(name, v, elementsType));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setUDTValue(int,
	 * com.taulukko.cassandra.datastax.UDTValue)
	 */
	@Override
	public UDTValue setUDTValue(int i, UDTValue v) {
		return new DSUDTValue(coreUdtValue.setUDTValue(i,
				((UDTValue) v).getCoreUdtValue()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.UDTValue#setUDTValue(java.lang.String,
	 * com.taulukko.cassandra.datastax.UDTValue)
	 */
	@Override
	public UDTValue setUDTValue(String name, UDTValue v) {
		return new DSUDTValue(coreUdtValue.setUDTValue(name,
				((UDTValue) v).getCoreUdtValue()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setTupleValue(int,
	 * com.taulukko.cassandra.data.TupleValue)
	 */
	@Override
	public UDTValue setTupleValue(int i, TupleValue v) {
		return new DSUDTValue(coreUdtValue.setTupleValue(i,
				((DSTupleValue) v).getCoreTupleValue()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.UDTValue#setTupleValue(java.lang.String,
	 * com.taulukko.cassandra.data.TupleValue)
	 */
	@Override
	public UDTValue setTupleValue(String name, TupleValue v) {
		return new DSUDTValue(coreUdtValue.setTupleValue(name,
				((DSTupleValue) v).getCoreTupleValue()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#set(int, V,
	 * java.lang.Class)
	 */
	@Override
	public <V> UDTValue set(int i, V v, Class<V> targetClass) {
		return new DSUDTValue(coreUdtValue.set(i, v, targetClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#set(java.lang.String, V,
	 * java.lang.Class)
	 */
	@Override
	public <V> UDTValue set(String name, V v, Class<V> targetClass) {
		return new DSUDTValue(coreUdtValue.set(name, v, targetClass));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#set(int, V,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <V> UDTValue set(int i, V v, TypeToken<V> targetType) {
		return new DSUDTValue(coreUdtValue.set(i, v, targetType));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#set(java.lang.String, V,
	 * com.google.common.reflect.TypeToken)
	 */
	@Override
	public <V> UDTValue set(String name, V v, TypeToken<V> targetType) {
		return new DSUDTValue(coreUdtValue.set(name, v, targetType));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setToNull(int)
	 */
	@Override
	public UDTValue setToNull(int i) {
		return new DSUDTValue(coreUdtValue.setToNull(i));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#setToNull(java.lang.String)
	 */
	@Override
	public UDTValue setToNull(String name) {
		return new DSUDTValue(coreUdtValue.setToNull(name));
	}

	public DSUDTValue(com.datastax.driver.core.UDTValue udtValue) {
		this.coreUdtValue = udtValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.UDTValue#getCoreUdtValue()
	 */
	@Override
	public com.datastax.driver.core.UDTValue getCoreUdtValue() {
		return coreUdtValue;
	}

}
