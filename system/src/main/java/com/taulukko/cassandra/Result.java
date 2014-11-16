package com.taulukko.cassandra;


public interface Result {

	public long getExecutionTimeNano();

	public long getExecutionTimeMili();

	public long getExecutionTimeMicro();

	public int getRowCount();

	public <T> T slice(Object handler) throws CEUException;

	public ServerInfo getServerInfo();

}
