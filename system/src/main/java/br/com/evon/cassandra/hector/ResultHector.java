package br.com.evon.cassandra.hector;

import br.com.evon.cassandra.CEUException;
import br.com.evon.cassandra.Handler;
import br.com.evon.cassandra.Result;
import br.com.evon.cassandra.ServerInfo;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.cassandra.service.CassandraHost;
import me.prettyprint.hector.api.query.QueryResult;

public class ResultHector implements Result {

	private long executionTimeNano = 0;
	private int rowCount = 0;
	private QueryResult<CqlRows<String, String, byte[]>> result = null;
	ServerInfo serverinfo = new ServerInfo();

	public ResultHector(QueryResult<CqlRows<String, String, byte[]>> result) {
		executionTimeNano = result.getExecutionTimeNano();
		rowCount = result.get().getCount();
		CassandraHost host = result.getHostUsed();
		serverinfo.setHostName(host.getHost());
		serverinfo.setIP(host.getIp());
		serverinfo.setUrl(host.getUrl());
		if (serverinfo.getUrl() != null) {
			if (serverinfo.getUrl().contains(":")) {
				String urlParts[] = serverinfo.getUrl().split(":");
				serverinfo.setPort(Integer.valueOf(urlParts[1]));
			} else {
				serverinfo.setPort(80);
			}
		}
		this.result = result;
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

	@SuppressWarnings("unchecked")
	@Override
	public <T> T slice(Object handlerObj) throws CEUException {
		Handler<T, QueryResult<CqlRows<String, String, byte[]>>> handler = (Handler<T, QueryResult<CqlRows<String, String, byte[]>>>) handlerObj;
		return handler.convert(result);
	}

	@Override
	public ServerInfo getServerInfo() {
		return serverinfo;
	}

}
