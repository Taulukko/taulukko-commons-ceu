package com.taulukko.cassandra.astyanax;

import com.netflix.astyanax.connectionpool.Host;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.CqlResult;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Result;
import com.taulukko.cassandra.ServerInfo;

public class ResultAstyanax implements Result {

	private long executionTimeNano = 0;
	private int rowCount = 0;
	ServerInfo serverinfo = new ServerInfo();

	public ResultAstyanax(OperationResult<CqlResult<Integer, String>> result) {
		executionTimeNano = result.getLatency();
		rowCount = result.getAttemptsCount();
		Host host = result.getHost();
		serverinfo.setHostName(host.getHostName());
		serverinfo.setIP(host.getIpAddress());
		serverinfo.setUrl(host.getUrl());
		serverinfo.setPort(host.getPort());
	}

	@Override
	public long getExecutionTimeNano() {
		return executionTimeNano;
	}

	@Override
	public long getExecutionTimeMili() {
		return getExecutionTimeMicro() / 1000;
	}

	@Override
	public long getExecutionTimeMicro() {
		return executionTimeNano / 1000;
	}

	@Override
	public int getRowCount() {
		return rowCount;
	}

	@Override
	public <T> T slice(Object handler) throws CEUException {
		throw new CEUException(
				"ResultsetAstyanax not support slice. Use list or ResultsetHector");
	}

	@Override
	public ServerInfo getServerInfo() {
		return serverinfo;
	}

}
