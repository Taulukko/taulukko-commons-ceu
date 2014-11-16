package com.taulukko.cassandra.astyanax;

import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.serializers.IntegerSerializer;
import com.netflix.astyanax.serializers.StringSerializer;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Command;
import com.taulukko.cassandra.DataSource;
import com.taulukko.cassandra.Handler;
import com.taulukko.cassandra.Result;
import com.taulukko.cassandra.Runner;

public class RunnerAstyanax extends
		Runner<OperationResult<CqlResult<Integer, String>>> {

	DataSource<Keyspace> datasource = null;

	public RunnerAstyanax(DataSource<Keyspace> datasource) {
		this.datasource = datasource;
	}

	public void exec(Command command) throws CEUException {
		Keyspace keyspace = datasource.getKeyspace();

		OperationResult<CqlResult<Integer, String>> queryRequltset;

		String query = command.getQuery();
		Object parameters[] = command.getParameters();
		query = fillParameters(query, parameters);

		try {
			ColumnFamily<Integer, String> CQL3_CF = ColumnFamily
					.newColumnFamily("Cql3CF", IntegerSerializer.get(),
							StringSerializer.get());

			queryRequltset = keyspace.prepareQuery(CQL3_CF).withCql(query)
					.execute();

		} catch (Exception e) {
			throw new CEUException("Invalid update : " + query + " - \n "
					+ e.getMessage(), e);

		}

		Result resultset = new ResultAstyanax(queryRequltset);
		command.setResult(resultset);
	}

	public <T> T query(Command command,
			Handler<T, OperationResult<CqlResult<Integer, String>>> handler)
			throws CEUException {

		OperationResult<CqlResult<Integer, String>> result;

		ColumnFamily<Integer, String> CQL3_CF = ColumnFamily.newColumnFamily(
				"Cql3CF", IntegerSerializer.get(), StringSerializer.get());

		String query = command.getQuery();
		Object parameters[] = command.getParameters();
		query = fillParameters(query, parameters);

		Keyspace keyspace = datasource.getKeyspace();
		try {
			result = keyspace.prepareQuery(CQL3_CF).withCql(query).execute();
		} catch (Exception e) {
			throw new CEUException("Invalid query : " + query + " - \n "
					+ e.getMessage(), e);
		}
		Result rs = new ResultAstyanax(result);
		command.setResult(rs);

		return handler.convert(result);
	}

}
