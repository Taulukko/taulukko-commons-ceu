package com.taulukko.ceu.data;


public interface Configuration {

	public abstract PoolingOptions getPoolingOptions();

	public abstract SocketOptions getSocketOptions();

}