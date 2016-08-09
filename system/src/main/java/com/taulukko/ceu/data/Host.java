package com.taulukko.ceu.data;

import java.net.InetAddress;
import java.net.SocketAddress;

public interface Host {
	public String getDatacenter();
	public InetAddress getAddress();
	public SocketAddress getSocketAddress();
}