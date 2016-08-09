package integration.com.taulukko.ceu.cassandra.datastax;

import integration.com.taulukko.cassandra.TestUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import com.taulukko.ceu.CEUConfig;
import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Command;
import com.taulukko.ceu.Runner;
import com.taulukko.ceu.cassandra.datastax.DSDriver;
import com.taulukko.ceu.data.Cluster;
import com.taulukko.ceu.data.Connection;
import com.taulukko.ceu.data.Factory;

public class BaseTest {

	protected static Runner runner = null;

	protected static String TABLE_NAME = "handlerTest";
	protected static String TABLE_ICST = "IgnoreCaseSensitiveTest"
			.toLowerCase();

	public static final String HOST_NAME_TEST = "localhost";

	public static final int PORT_TEST = 9160;

	public static final String CLUSTER_NAME_TEST = "Test Cluster";

	public static final String KEYSPACE_NAME_TEST = "KeySpaceTest";// "KeySpaceTest";

	@BeforeClass
	public static void beforeClass() throws CEUException {

		TestUtil.start();

		Factory factory = new DSDriver().getFactoryByContactPoint("localhost");
		Cluster cluster = factory.getCluster();

		Connection con = cluster.connect();

		runner = new Runner(con);

		Command command = null;
		try {

			command = new Command("DROP TABLE \"" + TABLE_NAME + "\"");
			runner.exec(command);
		} catch (Exception e) {
			// fine, table test maybe not exist
		}
		command = new Command(
				"CREATE TABLE \""
						+ TABLE_NAME
						+ "\" (key varchar PRIMARY KEY,email text,age int,tags list<text>,"
						+ " \"friendsByName\" map<text,int>, cmps set<int>)");
		runner.exec(command);

		command = new Command(
				"INSERT INTO \""
						+ TABLE_NAME
						+ "\" (key,email,age,tags,\"friendsByName\",cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?})",
				"userTest", "userTesta@gmail.com", 45, "Pelé1", "Pelé2",
				"Pelé3", "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55);
		runner.exec(command);

		command = new Command(
				"INSERT INTO \""
						+ TABLE_NAME
						+ "\" (key,email,age,tags,\"friendsByName\",cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?}) USING TTL 10",
				"userTestTime", "userTestb@gmail.com", 45, "Pelé1", "Pelé2",
				"Pelé3", "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55);
		runner.exec(command);

		command = new Command(
				"INSERT INTO \""
						+ TABLE_NAME
						+ "\" (key,email,age,tags,\"friendsByName\",cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?})",
				"userTestTime2", "userTestc@gmail.com", 45, "Pelé1", "Pelé2",
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
					"INSERT INTO \""
							+ TABLE_NAME
							+ "\" (key,email,age,tags,\"friendsByName\",cmps) VALUES (?,?,?,?,?,?)",
					"userTest" + index, "userTest" + index + "@gmail.com", 45, tags,
					friendsByName, cmps);
			runner.exec(command);

		}

		try {

			command = new Command("DROP TABLE " + TABLE_ICST);
			runner.exec(command);
		} catch (Exception e) {
			// fine, table test maybe not exist
		}
		command = new Command(
				"CREATE TABLE  "
						+ TABLE_ICST
						+ " (key varchar PRIMARY KEY,email text,age int,tags list<text>,"
						+ " friendsByName map<text,int>, cmps set<int>)");
		runner.exec(command);

		command = new Command(
				"INSERT INTO "
						+ TABLE_ICST
						+ " (key,email,age,tags,friendsByName,cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?})",
				"userTest", "userTest@gmail.com", 45, "Pelé1", "Pelé2",
				"Pelé3", "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55);
		runner.exec(command);
	}

	@AfterClass
	public static void afterClass() throws CEUException {

		Command command = new Command("DROP TABLE \"" + TABLE_NAME + "\"");
		runner.exec(command);

		command = new Command("DROP TABLE " + TABLE_ICST);
		runner.exec(command);

	}

	@After
	public final void afterMethod() {
		CEUConfig.isAutoWrapItemName = false;
	}

	public <T> T existOptional(Optional<T> o) {
		Assert.assertTrue(o.isPresent());
		return o.get();
	}

}