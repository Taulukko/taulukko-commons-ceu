package com.taulukko.ceu.cassandra.datastax;

import java.util.Optional;

import com.taulukko.ceu.data.SocketOptions;

public class DSSocketOptions implements SocketOptions {

	private com.datastax.driver.core.SocketOptions coreSocketOptions;

	public DSSocketOptions(
			com.datastax.driver.core.SocketOptions coreSocketOptions) {
		this.coreSocketOptions = coreSocketOptions;
	}

	public int hashCode() {
		return coreSocketOptions.hashCode();
	}

	public boolean equals(Object obj) {
		return coreSocketOptions.equals(obj);
	}

	public SocketOptions setReadTimeoutMillis(int readTimeoutMillis) {

		coreSocketOptions.setReadTimeoutMillis(readTimeoutMillis);
		return this;
	}

	public Boolean getKeepAlive() {
		return coreSocketOptions.getKeepAlive();
	}

	public SocketOptions setKeepAlive(boolean keepAlive) {
		coreSocketOptions.setKeepAlive(keepAlive);
		return this;
	}

	public Boolean getReuseAddress() {
		return coreSocketOptions.getReuseAddress();
	}

	public SocketOptions setReuseAddress(boolean reuseAddress) {

		coreSocketOptions.setReuseAddress(reuseAddress);
		return this;
	}

	public Integer getSoLinger() {
		return coreSocketOptions.getSoLinger();
	}

	public SocketOptions setSoLinger(int soLinger) {
		coreSocketOptions.setSoLinger(soLinger);
		return this;
	}

	public Boolean getTcpNoDelay() {
		return coreSocketOptions.getTcpNoDelay();
	}

	public SocketOptions setTcpNoDelay(Boolean tcpNoDelay) {
		coreSocketOptions.setTcpNoDelay(tcpNoDelay);
		return this;
	}

	public SocketOptions setReceiveBufferSize(int receiveBufferSize) {
		coreSocketOptions.setReceiveBufferSize(receiveBufferSize);
		return this;
	}

	public SocketOptions setSendBufferSize(int sendBufferSize) {
		coreSocketOptions.setSendBufferSize(sendBufferSize);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.SocketOptions#getConnectTimeoutMillis()
	 */
	@Override
	public Optional<Integer> getConnectTimeoutMillis() {
		return Optional.of(coreSocketOptions.getConnectTimeoutMillis());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.SocketOptions#getReadTimeoutMillis()
	 */
	@Override
	public Optional<Integer> getReadTimeoutMillis() {
		return Optional.of(coreSocketOptions.getReadTimeoutMillis());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.SocketOptions#getReceiveBufferSize()
	 */
	@Override
	public Optional<Integer> getReceiveBufferSize() {
		return Optional.of(coreSocketOptions.getReceiveBufferSize());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.SocketOptions#getSendBufferSize()
	 */
	@Override
	public Optional<Integer> getSendBufferSize() {
		return Optional.of(coreSocketOptions.getSendBufferSize());
	}

	@Override
	public SocketOptions setTcpNoDelay(boolean b) {
		coreSocketOptions.setTcpNoDelay(b);
		return this;
	}

}
