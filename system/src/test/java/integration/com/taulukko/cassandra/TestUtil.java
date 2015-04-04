package integration.com.taulukko.cassandra;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;
import com.taulukko.cassandra.CEUConfig;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Command;
import com.taulukko.cassandra.astyanax.CEUUtils;
import com.taulukko.cassandra.astyanax.FactoryDataSourceAstyanax;
import com.taulukko.cassandra.astyanax.RunnerAstyanax;

public class TestUtil extends BaseTest{

	private static boolean started = false;

	private static RunnerAstyanax runner = null;



	public static synchronized void start() throws CEUException {
		if (!started) {
			CEUConfig.load(TestUtil.class
					.getResourceAsStream("/config/ceu.json"));
			started = true;
		}

		try {
			CEUUtils.removeKeyspace(HOST_NAME_TEST, PORT_TEST,
					CLUSTER_NAME_TEST, KEYSPACE_NAME_TEST);
		} catch (ConnectionException e1) {
			// fine not exists
		}

		try {

			CEUUtils.createKeyspace(HOST_NAME_TEST, PORT_TEST,
					CLUSTER_NAME_TEST, KEYSPACE_NAME_TEST);

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
					"userTestTime2", "userTest@gmail.com", 45, "Pelé1",
					"Pelé2", "Pelé3", "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33,
					44, 55);
			runner.exec(command);

			for (int index = 0; index < 100; index++) {
				List<String> tags = Arrays.asList("Tag1-" + index, "Tag2-"
						+ index, "Tag3-" + index);
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

			command = new Command("CREATE TABLE accounts "
					+ " (key varchar PRIMARY KEY,name text,\"oldId\" int )");
			runner.exec(command);

			command = new Command("CREATE INDEX accounts_userid " +
			  " ON accounts (\"oldId\")");
			runner.exec(command);

			
			for (int cont = 0; cont < 20; cont++) {
				command = new Command(
						"INSERT INTO accounts (key,name,\"oldId\" ) VALUES (?,?,?)",
						"userTest" + cont, "Phillip " + cont, cont);
				runner.exec(command);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new CEUException(e.getMessage());
		}
	}

	public static synchronized void stop() throws CEUException {

		try {
			CEUUtils.createKeyspace(HOST_NAME_TEST, PORT_TEST,
					CLUSTER_NAME_TEST, KEYSPACE_NAME_TEST);
		} catch (ConnectionException e1) {
			// fine already exists
		}
		try {
			CEUUtils.removeKeyspace(HOST_NAME_TEST, PORT_TEST,
					CLUSTER_NAME_TEST, KEYSPACE_NAME_TEST);
		} catch (ConnectionException e) {
			e.printStackTrace();
			throw new CEUException(e.getMessage(), e);
		}
	}
}
