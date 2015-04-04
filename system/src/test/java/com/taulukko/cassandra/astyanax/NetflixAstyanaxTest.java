package com.taulukko.cassandra.astyanax;

import integration.com.taulukko.cassandra.InitializeTests;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.cassandra.db.marshal.IntegerType;
import org.apache.cassandra.db.marshal.UTF8Type;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.ddl.ColumnDefinition;
import com.netflix.astyanax.ddl.ColumnFamilyDefinition;
import com.netflix.astyanax.ddl.FieldMetadata;
import com.netflix.astyanax.ddl.KeyspaceDefinition;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.model.CqlResult;
import com.netflix.astyanax.model.Row;
import com.netflix.astyanax.model.Rows;
import com.netflix.astyanax.serializers.IntegerSerializer;
import com.netflix.astyanax.serializers.MapSerializer;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;

public class NetflixAstyanaxTest {

	private static String TABLE_TEST_NAME = "employees";
	public static AstyanaxContext<Keyspace> context = null;
	public static Keyspace keyspace = null;

	@BeforeClass
	public static void init() throws Exception {
		InitializeTests.runOnce();
		 
		context = new AstyanaxContext.Builder()
				.forCluster("Evon Cluster 01")
				.forKeyspace("oauth")
				.withAstyanaxConfiguration(
						new AstyanaxConfigurationImpl()
								.setCqlVersion("3.0.0")
								.setTargetCassandraVersion("1.2")
								.setDiscoveryType(
										NodeDiscoveryType.RING_DESCRIBE))
				.withConnectionPoolConfiguration(
						new ConnectionPoolConfigurationImpl("MyConnectionPool")
								.setPort(9160).setMaxConnsPerHost(1)
								.setSeeds("localhost"))
				.withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
				.buildKeyspace(ThriftFamilyFactory.getInstance());

		context.start();
		keyspace = context.getClient();

		ColumnFamily<Integer, String> CQL3_CF = ColumnFamily.newColumnFamily(
				"Cql3CF", IntegerSerializer.get(), StringSerializer.get());

		try {
			keyspace.prepareQuery(CQL3_CF)
					.withCql("DROP TABLE " + TABLE_TEST_NAME + ";").execute();

		} catch (Exception e) {
			e.printStackTrace();

		}
		try {
			keyspace.prepareQuery(CQL3_CF)
					.withCql("DROP TABLE " + TABLE_TEST_NAME + "MAP;")
					.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		keyspace.prepareQuery(CQL3_CF)
				.withCql(
						"CREATE TABLE "
								+ TABLE_TEST_NAME
								+ " (empID int, deptID int, first_name varchar, "
								+ "	              last_name varchar, mark list<varchar>, PRIMARY KEY (empID));")
				.execute();

		keyspace.prepareQuery(CQL3_CF)
				.withCql(
						"CREATE TABLE "
								+ TABLE_TEST_NAME
								+ "MAP"
								+ " (empID int, deptID int, first_name varchar, "
								+ "	              last_name varchar, mark map<int,text>, PRIMARY KEY (empID));")
				.execute();

	}

	@AfterClass
	public static void shutdown() {
		if (context != null) {
			context.shutdown();
		}
	}

	@Test
	public void list() throws ConnectionException {

		OperationResult<CqlResult<Integer, String>> result;

		ColumnFamily<Integer, String> CQL3_CF = ColumnFamily.newColumnFamily(
				"Cql3CF", IntegerSerializer.get(), StringSerializer.get());

		result = keyspace
				.prepareQuery(CQL3_CF)
				.withCql(
						"INSERT INTO "
								+ TABLE_TEST_NAME
								+ " (empID, deptID, first_name, last_name, mark)  "
								+ " VALUES (111, 222, 'eran', 'landau',['1é','1ó','1ú']);")
				.execute();

		result = keyspace
				.prepareQuery(CQL3_CF)
				.withCql(
						"SELECT * FROM " + TABLE_TEST_NAME
								+ " WHERE empID = 111;").execute();

		System.out.println("AttemptsCount: " + result.getAttemptsCount());

		System.out.println("Latency in ns: " + result.getLatency());

		System.out.println("Latency in mcs: "
				+ result.getLatency(TimeUnit.MICROSECONDS));

		System.out.println("Latency in ms: "
				+ result.getLatency(TimeUnit.MILLISECONDS));

		System.out.println("Latency in s: "
				+ result.getLatency(TimeUnit.SECONDS));

		Rows<Integer, String> rows = result.getResult().getRows();

		for (Row<Integer, String> row : rows) {

			ColumnList<String> columns = row.getColumns();

			for (String columnName : columns.getColumnNames()) {
				System.out.println("ColumnName:" + columnName);
			}

			Assert.assertEquals(new Integer(111),
					columns.getIntegerValue("empid", null));
			Assert.assertEquals(new Integer(222),
					columns.getIntegerValue("deptid", null));
			Assert.assertEquals("eran",
					columns.getStringValue("first_name", null));
			Assert.assertEquals("landau",
					columns.getStringValue("last_name", null));

		}

	}

	@Test
	public void map() throws ConnectionException {

		OperationResult<CqlResult<Integer, String>> result;

		ColumnFamily<Integer, String> CQL3_CF = ColumnFamily.newColumnFamily(
				"Cql3CF", IntegerSerializer.get(), StringSerializer.get());

		result = keyspace
				.prepareQuery(CQL3_CF)
				.withCql(
						"INSERT INTO "
								+ TABLE_TEST_NAME
								+ "MAP"
								+ " (empID, deptID, first_name, last_name, mark)  "
								+ " VALUES (111, 222, 'eran', 'landau',{1:'é',2:'ú',3:'ó'});")
				.execute();

		result = keyspace
				.prepareQuery(CQL3_CF)
				.withCql(
						"SELECT * FROM " + TABLE_TEST_NAME + "MAP"
								+ " WHERE empID = 111;").execute();

		Rows<Integer, String> rows = result.getResult().getRows();

		for (Row<Integer, String> row : rows) {

			ColumnList<String> columns = row.getColumns();

			for (String columnName : columns.getColumnNames()) {
				System.out.println("ColumnName:" + columnName);
			}
			Assert.assertEquals(new Integer(111),
					columns.getIntegerValue("empid", null));
			Assert.assertEquals(new Integer(222),
					columns.getIntegerValue("deptid", null));
			Assert.assertEquals("eran",
					columns.getStringValue("first_name", null));
			Assert.assertEquals("landau",
					columns.getStringValue("last_name", null));

			MapSerializer<BigInteger, String> serializer = new MapSerializer<>(
					IntegerType.instance, UTF8Type.instance);

			byte bytes[] = columns.getByteArrayValue("mark", new byte[] {});

			Map<BigInteger, String> map = serializer.fromBytes(bytes);

			Assert.assertEquals("é", map.get(BigInteger.valueOf(1)));
			Assert.assertEquals("ú", map.get(BigInteger.valueOf(2)));
			Assert.assertEquals("ó", map.get(BigInteger.valueOf(3)));

		}

	}

	@Test
	public void serializerIntStringMapTest() throws ConnectionException {
		MapSerializer<BigInteger, String> serializer = new MapSerializer<>(
				IntegerType.instance, UTF8Type.instance);
		// byte bytes[] = columns.getByteArrayValue("mark", new byte[] {});
		// 000X positivo
		// -1-1-1X negativo

		// 0,3 (3 elementos)
		// 04 (inicio de chave)
		// 0000 (chave)
		// 02 (início de texto)

		// [0, 3, 0, 4, 0, 0, 0, 1, 0, 2, -61, -87, 0, 4, 0, 0, 0, 2, 0, 2,
		// -61, -70, 0, 4, 0, 0, 0, 3, 0, 2, -61, -77]

		byte bytes[] = new byte[] { 0, 3, 0, 4, 0, 0, 0, 1, 0, 2, -61, -87, 0,
				4, 0, 0, 0, 2, 0, 2, -61, -70, 0, 4, 0, 0, 0, 3, 0, 2, -61, -77 };

		Map<BigInteger, String> map = serializer.fromBytes(bytes);

		Assert.assertEquals("é", map.get(BigInteger.valueOf(1)));

	}

	@Test
	public void metadata() throws ConnectionException {

		Assert.assertEquals("Evon Cluster 01", context.getClusterName());

		Assert.assertEquals("oauth", context.getKeyspaceName());
		KeyspaceDefinition kd = keyspace.describeKeyspace();

		for (ColumnFamilyDefinition cf : kd.getColumnFamilyList()) {
			System.out.println(cf.getName() + ";");
			for (ColumnDefinition cd : cf.getColumnDefinitionList()) {
				System.out
						.println(cd.getName() + ";" + cd.getIndexName() + ";");

				for (FieldMetadata fm : cd.getFieldsMetadata()) {
					System.out.println(fm.getName() + ";" + fm.getType() + ";");

				}

			}
		}
	}

}
