package com.taulukko.ceu.data;

import java.util.Optional;

import com.taulukko.ceu.CEUException;

public interface SocketOptions extends CEUOptions {

	public abstract Optional<Integer> getConnectTimeoutMillis()
			throws CEUException;

	public abstract Optional<Integer> getReadTimeoutMillis()
			throws CEUException;

	public abstract Optional<Integer> getReceiveBufferSize()
			throws CEUException;

	public abstract Optional<Integer> getSendBufferSize() throws CEUException;

	public abstract SocketOptions setReadTimeoutMillis(int i)
			throws CEUException;

	public abstract SocketOptions setTcpNoDelay(boolean b) throws CEUException;

}