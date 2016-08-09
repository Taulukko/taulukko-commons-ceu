package integration.com.taulukko.ceu.cassandra.datastax;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class DatastaxTest {

	private static Cluster cluster;

	@BeforeClass
	public static void init() {
		cluster = Cluster.builder().addContactPoint("localhost").build();

		Metadata metadata = cluster.getMetadata();
		Assert.assertNotNull(metadata.getClusterName());
		System.out.printf("Connected to cluster: %s\n",
				metadata.getClusterName());

		Assert.assertTrue(metadata.getAllHosts().size() > 0);
		for (Host host : metadata.getAllHosts()) {
			System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
					host.getDatacenter(), host.getAddress(), host.getRack());
		}

		Session session = cluster.connect();

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
	public static void terminate() {

		cluster.close();
	}

	@Test
	public void selectTest() {
		Session session = cluster.connect();

		ResultSet results = session.execute("SELECT * FROM simplex.playlists "
				+ "WHERE id = 2cc9ccb7-6221-4ccb-8387-f22b6a1b354d;");

		Row row = results.one();

		Assert.assertEquals("La Petite Tonkinoise", row.getString("title"));

		session.close();
	}
}
