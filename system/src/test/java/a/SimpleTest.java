package a;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.taulukko.cassandra.CEUConfig;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Command;
import com.taulukko.cassandra.astyanax.CEUUtils;
import com.taulukko.cassandra.astyanax.FactoryDataSourceAstyanax;
import com.taulukko.cassandra.astyanax.RunnerAstyanax;
import com.taulukko.common.ceu.test.InitializeTests;

public class SimpleTest {

	private static final String HOST_NAME_TEST = "localhost";

	private static final int PORT_TEST = 9160;

	private static final String CLUSTER_NAME_TEST = "Test Cluster";

	private static final String KEYSPACE_NAME_TEST = "keySpaceTest";

	private static RunnerAstyanax runner = null;

	private static boolean loaded = false;

	@BeforeClass
	public static void beforeClass() throws CEUException, ConnectionException {

		InitializeTests.runOnce();

		CEUUtils.createKeyspace(HOST_NAME_TEST, PORT_TEST, CLUSTER_NAME_TEST,
				KEYSPACE_NAME_TEST);

		runner = new RunnerAstyanax(
				FactoryDataSourceAstyanax.getDataSource(KEYSPACE_NAME_TEST));

		// is need to not put double ""in friendsByName
		CEUConfig.isAutoWrapItemName = false;

		Command command = new Command(
				"CREATE TABLE test "
						+ " (key varchar PRIMARY KEY,email text,age int,tags list<text>,"
						+ " \"friendsByName\" map<text,int>, cmps set<int>)");
		runner.exec(command);
		 

		command = new Command(
				"INSERT INTO test (key,email,age,tags,\"friendsByName\",cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?})",
				"userTest", "userTest@gmail.com", 45, "Pelé1", "Pelé2",
				"Pelé3", "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55);
		runner.exec(command);

		command = new Command(
				"INSERT INTO test (key,email,age,tags,\"friendsByName\",cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?}) USING TTL 10",
				"userTestTime", "userTest@gmail.com", 45, "Pelé1", "Pelé2",
				"Pelé3", "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55);
		runner.exec(command);

		command = new Command(
				"INSERT INTO test (key,email,age,tags,\"friendsByName\",cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?})",
				"userTestTime2", "userTest@gmail.com", 45, "Pelé1", "Pelé2",
				"Pelé3", "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55);
		runner.exec(command);

		for (int index = 0; index < 100; index++) {
			List<String> tags = Arrays.asList("Tag1-" + index, "Tag2-" + index,
					"Tag3-" + index);
			Map<String, Integer> friendsByName = new HashMap<>();
			friendsByName.put("Amigo1-" + index, 1);
			friendsByName.put("Amigo2-" + index, 2);
			friendsByName.put("Amigo3-" + index, 3);

			Set<Integer> cmps = new HashSet<>();

			cmps.add(100 + index);
			cmps.add(110 + index);
			cmps.add(120 + index);

			command = new Command(
					"INSERT INTO test (key,email,age,tags,\"friendsByName\",cmps) VALUES (?,?,?,?,?,?)",
					"userTest" + index, "userTest@gmail.com", 45, tags,
					friendsByName, cmps);
			runner.exec(command);
		}

		loaded = true;
	}

	@AfterClass
	public static void afterClass() throws CEUException, ConnectionException {

		CEUUtils.removeKeyspace(HOST_NAME_TEST, PORT_TEST, CLUSTER_NAME_TEST,
				KEYSPACE_NAME_TEST);
	}

	@After
	public void end() {

	}

	@Test
	public void iAmAlive() {
		Assert.assertTrue(loaded);

	}

}
