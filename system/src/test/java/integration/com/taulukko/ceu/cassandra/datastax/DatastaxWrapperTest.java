package integration.com.taulukko.ceu.cassandra.datastax;

import integration.com.taulukko.cassandra.TestUtil;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.cassandra.datastax.DSDriver;
import com.taulukko.ceu.data.Cluster;
import com.taulukko.ceu.data.Connection;
import com.taulukko.ceu.data.Factory;
import com.taulukko.ceu.data.Host;
import com.taulukko.ceu.data.Metadata;
import com.taulukko.ceu.data.ResultSet;
import com.taulukko.ceu.data.Row;

public class DatastaxWrapperTest {

	private static Cluster cluster;

	@BeforeClass
	public static void init() throws CEUException {
		TestUtil.start();

		Factory factory = new DSDriver().getFactoryByContactPoint("localhost");
		cluster = factory.getCluster();

		Metadata metadata = cluster.getMetadata();
		Assert.assertNotNull(metadata.getClusterName());
		System.out.printf("Connected to cluster: %s\n",
				metadata.getClusterName());

		Assert.assertTrue(metadata.getAllHosts().size() > 0);
		for (Host host : metadata.getAllHosts()) {
			System.out.printf("Datacenter: %s; Host: %s\n",
					host.getDatacenter(), host.getAddress());
		}

		Connection session = cluster.connect();

		try {
			session.execute("DROP KEYSPACE simplex ;");
		} catch (Exception e) {
			if (!e.getMessage().contains(
					"Cannot drop non existing keyspace 'simplex'")) {
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}

		session.execute("CREATE KEYSPACE simplex WITH replication "
				+ "= {'class':'SimpleStrategy', 'replication_factor':3};");

		session.execute("CREATE TABLE simplex.songs (" + "id uuid PRIMARY KEY,"
				+ "title text," + "album text," + "artist text,"
				+ "tags set<text>," + "data blob" + ");");

		session.execute("CREATE TABLE simplex.playlists (" + "id uuid,"
				+ "title text," + "album text, " + "artist text,"
				+ "song_id uuid," + "PRIMARY KEY (id, title, album, artist)"
				+ ");");

		session.execute("INSERT INTO simplex.songs (id, title, album, artist, tags) "
				+ "VALUES ("
				+ "756716f7-2e54-4715-9f00-91dcbea6cf50,"
				+ "'La Petite Tonkinoise',"
				+ "'Bye Bye Blackbird',"
				+ "'Joséphine Baker'," + "{'jazz', '2013'})" + ";");
		session.execute("INSERT INTO simplex.playlists (id, song_id, title, album, artist) "
				+ "VALUES ("
				+ "2cc9ccb7-6221-4ccb-8387-f22b6a1b354d,"
				+ "756716f7-2e54-4715-9f00-91dcbea6cf50,"
				+ "'La Petite Tonkinoise',"
				+ "'Bye Bye Blackbird',"
				+ "'Joséphine Baker'" + ");");

		session.close();
	}

	@AfterClass
	public static void terminate() throws CEUException {

		cluster.close();
	}

	@Test
	public void selectTest() throws CEUException {
		try {
			Connection session = cluster.connect();

			ResultSet results = session
					.execute("SELECT * FROM simplex.playlists "
							+ "WHERE id = 2cc9ccb7-6221-4ccb-8387-f22b6a1b354d;");

			Row row = results.next();

			Assert.assertEquals("La Petite Tonkinoise", row.getString("title"));

			session.close();
		} catch (RuntimeException re) {
			throw new CEUException(re);
		}
	}
}
