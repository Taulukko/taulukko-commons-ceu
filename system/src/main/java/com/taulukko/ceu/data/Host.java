package com.taulukko.ceu.data;

import java.net.InetAddress;
import java.net.SocketAddress;

import com.taulukko.ceu.CEUException;

public interface Host {
	public String getDatacenter() throws CEUException;

	public InetAddress getAddress() throws CEUException;

	public SocketAddress getSocketAddress() throws CEUException;
}