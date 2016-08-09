package com.taulukko.ceu.cassandra.datastax;

import java.net.InetAddress;
import java.net.SocketAddress;

import com.taulukko.ceu.data.Host;

public class DSHost implements Host {

	private com.datastax.driver.core.Host coreHost;

	public DSHost(com.datastax.driver.core.Host coreHost) {
		this.coreHost = coreHost;
	}

	public com.datastax.driver.core.Host getCoreHost() {
		return coreHost;
	}

	@Override
	public String getDatacenter() {
		return coreHost.getDatacenter();
	}

	@Override
	public InetAddress getAddress() {
		return coreHost.getAddress();
	}

	@Override
	public SocketAddress getSocketAddress() {
		return coreHost.getSocketAddress();
	}
}
