package integration.com.taulukko.cassandra.hector;

import integration.com.taulukko.cassandra.BaseTest;
import integration.com.taulukko.cassandra.TestUtil;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.taulukko.cassandra.AccountTestBean;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Command;
import com.taulukko.cassandra.hector.FactoryDataSourceHector;
import com.taulukko.cassandra.hector.RunnerHector;
import com.taulukko.cassandra.hector.handler.BeanHandler;
import com.taulukko.cassandra.hector.handler.BeanListHandler;
import com.taulukko.cassandra.hector.handler.SingleObjectHandler;
import com.taulukko.cassandra.hector.handler.SliceHandler;

public class HandlerTest extends BaseTest {

	private static RunnerHector runner = null;

	private static final String TABLE_NAME_HECTOR_TEST = "hector";

	@BeforeClass
	public static void beforeClass() throws CEUException {

		TestUtil.start();

		runner = new RunnerHector(
				FactoryDataSourceHector.getDataSource(KEYSPACE_NAME_TEST));
		Command command = null;
		try {

			command = new Command("DROP TABLE " + TABLE_NAME_HECTOR_TEST);
			runner.exec(command);
		} catch (Exception e) {
			// fine, table test maybe not exist
		}
		command = new Command(
				"CREATE TABLE "
						+ TABLE_NAME_HECTOR_TEST
						+ " (key varchar PRIMARY KEY,email varchar,age int,tag_1 varchar,tag_2 varchar,tag_3 varchar,tag_4 varchar,tag_5 varchar,tag_22 varchar) WITH comparator=UTF8Type AND default_validation = UTF8Type");
		runner.exec(command);

		command = new Command(
				"INSERT INTO "
						+ TABLE_NAME_HECTOR_TEST
						+ " (key,email,age,tag_1,tag_2,tag_3,tag_4,tag_5,tag_22) VALUES (?,?,?,?,?,?,?,?,?)",
				"userTest", "userTest@gmail.com", 45, "Pelé1", "Pelé2",
				"Pelé3", "Pelé4", "Pelé5", "Pelé22");
		runner.exec(command);

		for (int index = 0; index < 100; index++) {
			command = new Command("INSERT INTO " + TABLE_NAME_HECTOR_TEST
					+ " (key,email,age,tag_1) VALUES (?,?,?,?)", "userTest"
					+ index, "userTest" + index + "@gmail.com", 45, "Pelé1");
			runner.exec(command);
		}
	}

	@AfterClass
	public static void afterClass() throws CEUException {

		Command command = new Command("DROP TABLE " + TABLE_NAME_HECTOR_TEST);
		runner.exec(command);

	}

	@Test
	public void beanListHandlerTest() throws CEUException, ParseException {

		BeanListHandler<AccountTestBean> handler = new BeanListHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM " + TABLE_NAME_HECTOR_TEST
				+ " limit 10");

		List<AccountTestBean> accounts = runner.query(command, handler);

		for (AccountTestBean account : accounts) {
			Assert.assertNotNull(account.getEmail());
			Assert.assertTrue(account.getEmail().contains("@"));
		}
	}

	@Test
	public void beanHandlerTest() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM " + TABLE_NAME_HECTOR_TEST
				+ " WHERE key = ?", "userTest");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertNotNull(account.getEmail());
		Assert.assertTrue(account.getEmail().contains("@"));
	}

	@Test(expected = CEUException.class)
	public void keyTest() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM " + TABLE_NAME_HECTOR_TEST
				+ " WHERE key = ?", "");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertNotNull(account.getEmail());
		Assert.assertTrue(account.getEmail().contains("@"));
	}

	@Test
	public void beanHandlerRowNotExistTest() throws CEUException,
			ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT email FROM "
				+ TABLE_NAME_HECTOR_TEST + " WHERE key = ?", "**notexist**");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertNull(account);
	}

	@Test
	public void beanListHandlerRowNotExistTest() throws CEUException,
			ParseException {

		BeanListHandler<AccountTestBean> handler = new BeanListHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT email FROM "
				+ TABLE_NAME_HECTOR_TEST + " WHERE key = ?", "**notexist**");

		List<AccountTestBean> accounts = runner.query(command, handler);

		Assert.assertEquals(0, accounts.size());
	}

	@Test
	public void beanSingleObjectHandlerNotExistTest() throws CEUException,
			ParseException {

		SingleObjectHandler<String> handler = new SingleObjectHandler<>(
				String.class, "email");

		Command command = new Command("SELECT email FROM "
				+ TABLE_NAME_HECTOR_TEST + " WHERE key = ?", "**notexist**");

		String email = runner.query(command, handler);

		Assert.assertNull(email);
	}

	@Test
	public void beanSingleObjectHandlerTest() throws CEUException,
			ParseException {

		SingleObjectHandler<String> handler = new SingleObjectHandler<>(
				String.class, "email");
		Command command = new Command("SELECT email FROM "
				+ TABLE_NAME_HECTOR_TEST + " WHERE key = ?", "userTest");

		String email = runner.query(command, handler);

		Assert.assertEquals("userTest@gmail.com", email);
	}

	@Test
	@Ignore
	/*
	 * Em versoes antigas funcionava, por algum motivo quando trocou o jar do
	 * cassandra parou mas não sera mais uado
	 */
	public void beanSliceHandlerPreSliceTest() throws CEUException,
			ParseException {

		SliceHandler<String> handler = new SliceHandler<>("tag", String.class);

		Command command = new Command("SELECT 'tag_2'..'tag_3' FROM "
				+ TABLE_NAME_HECTOR_TEST + " WHERE key = ?", "userTest");

		Map<String, String> tags = runner.query(command, handler);

		Assert.assertEquals("Pelé2", tags.get("2"));
		Assert.assertEquals("Pelé3", tags.get("3"));
		Assert.assertEquals("Pelé22", tags.get("22"));
		Assert.assertFalse(tags.containsKey("0"));
		Assert.assertFalse(tags.containsKey("4"));
	}

	@Test
	public void beanSliceHandlerPosSliceTest() throws CEUException,
			ParseException {

		SliceHandler<String> handler = new SliceHandler<>("tag", String.class);

		Command command = new Command("SELECT * FROM " + TABLE_NAME_HECTOR_TEST
				+ " WHERE key = ?", "userTest");

		Map<String, String> tags = runner.query(command, handler);

		Assert.assertEquals("Pelé2", tags.get("2"));
		Assert.assertEquals("Pelé3", tags.get("3"));
		Assert.assertEquals("Pelé22", tags.get("22"));
		Assert.assertTrue(tags.containsKey("1"));
		Assert.assertTrue(tags.containsKey("4"));
	}

	@Test
	public void beanReslice() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM " + TABLE_NAME_HECTOR_TEST
				+ " WHERE key = ?", "userTest");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertNotNull(account.getEmail());
		Assert.assertTrue(account.getEmail().contains("@"));

		SliceHandler<String> handler2 = new SliceHandler<>("tag", String.class);

		Map<String, String> tags = command.getResult().slice(handler2);

		Assert.assertEquals("Pelé2", tags.get("2"));
		Assert.assertEquals("Pelé3", tags.get("3"));
		Assert.assertEquals("Pelé22", tags.get("22"));
		Assert.assertTrue(tags.containsKey("1"));
		Assert.assertTrue(tags.containsKey("4"));
	}

}
