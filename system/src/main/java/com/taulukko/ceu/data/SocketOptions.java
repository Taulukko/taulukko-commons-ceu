package com.taulukko.ceu.data;

import java.util.Optional;

public interface SocketOptions extends CEUOptions {

	public abstract Optional<Integer> getConnectTimeoutMillis();

	public abstract Optional<Integer> getReadTimeoutMillis();

	public abstract Optional<Integer> getReceiveBufferSize();

	public abstract Optional<Integer> getSendBufferSize();

	public abstract SocketOptions setReadTimeoutMillis(int i);

	public abstract SocketOptions setTcpNoDelay(boolean b);

}