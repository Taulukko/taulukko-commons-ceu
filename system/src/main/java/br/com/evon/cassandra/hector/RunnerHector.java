package br.com.evon.cassandra.hector;

import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.query.QueryResult;
import br.com.evon.cassandra.CEUException;
import br.com.evon.cassandra.Command;
import br.com.evon.cassandra.DataSource;
import br.com.evon.cassandra.Handler;
import br.com.evon.cassandra.Result;
import br.com.evon.cassandra.Runner;

public class RunnerHector extends
		Runner<QueryResult<CqlRows<String, String, byte[]>>> {

	DataSource<Keyspace> datasource = null;

	public RunnerHector(DataSource<Keyspace> datasource) {
		this.datasource = datasource;
	}

	public void exec(Command command) throws CEUException {
		Serializer<String> stringSerializer = StringSerializer.get();
		Serializer<byte[]> byteArraySerializer = BytesArraySerializer.get();

		CqlQuery<String, String, byte[]> cqlQuery = new CqlQuery<String, String, byte[]>(
				datasource.getKeyspace(), stringSerializer, stringSerializer,
				byteArraySerializer);

		String query = command.getQuery();
		Object parameters[] = command.getParameters();
		query = fillParameters(query, parameters);
		cqlQuery.setQuery(query);

		QueryResult<CqlRows<String, String, byte[]>> queryResult = null;

		try {
			queryResult = cqlQuery.execute();
		} catch (Exception e) {
			throw new CEUException("Invalid command : " + e.getMessage(), e);
		}

		Result result = new ResultHector(queryResult);
		command.setResult(result);
	}

	public <T> T query(Command command,
			Handler<T, QueryResult<CqlRows<String, String, byte[]>>> handler)
			throws CEUException {
		Serializer<String> stringSerializer = StringSerializer.get();
		Serializer<byte[]> byteArraySerializer = BytesArraySerializer.get();

		CqlQuery<String, String, byte[]> cqlQuery = new CqlQuery<String, String, byte[]>(
				datasource.getKeyspace(), stringSerializer, stringSerializer,
				byteArraySerializer);

		String query = command.getQuery();
		Object parameters[] = command.getParameters();
		query = fillParameters(query, parameters);
		cqlQuery.setQuery(query);
		QueryResult<CqlRows<String, String, byte[]>> queryResult = null;
		try {
			queryResult = cqlQuery.execute();
		} catch (Exception e) {
			throw new CEUException("Invalid query : " + e.getMessage(), e);
		}
		Result result = new ResultHector(queryResult);
		command.setResult(result);

		return handler.convert(queryResult);
	}

}