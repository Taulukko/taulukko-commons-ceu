package br.com.evon.cassandra.astyanax;

import java.math.BigInteger;
import java.text.ParseException;
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
import org.junit.Ignore;
import org.junit.Test;

import com.taulukko.common.ceu.test.InitializeTests;

import br.com.evon.cassandra.AccountTestBean;
import br.com.evon.cassandra.CEUConfig;
import br.com.evon.cassandra.CEUException;
import br.com.evon.cassandra.Command;
import br.com.evon.cassandra.astyanax.handler.BeanHandler;
import br.com.evon.cassandra.astyanax.handler.BeanListHandler;
import br.com.evon.cassandra.astyanax.handler.ListHandler;
import br.com.evon.cassandra.astyanax.handler.MapHandler;
import br.com.evon.cassandra.astyanax.handler.SetHandler;
import br.com.evon.cassandra.astyanax.handler.SingleObjectHandler;

public class HandlerTest {

	private static RunnerAstyanax runner = null;

	@BeforeClass
	public static void beforeClass() throws CEUException {
		
		InitializeTests.runOnce();
 
		
		runner = new RunnerAstyanax(
				FactoryDataSourceAstyanax.getDataSource("oauth"));
		Command command = null;
		try {

			command = new Command("DROP TABLE test");
			runner.exec(command);
		} catch (Exception e) {
			// fine, table test maybe not exist
		}
		command = new Command(
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
	}

	@AfterClass
	public static void afterClass() throws CEUException {

		Command command = new Command("DROP TABLE test");
		runner.exec(command);

	}

	@After
	public void end()
	{
		CEUConfig.isAutoWrapItemName = false;
	}
	
	
	@Test
	public void beanListHandlerTest() throws CEUException, ParseException {

		BeanListHandler<AccountTestBean> handler = new BeanListHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM test limit 10");

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

		Command command = new Command("SELECT * FROM test WHERE key = ?",
				"userTest");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertNotNull(account.getEmail());
		Assert.assertTrue(account.getEmail().contains("@"));
	}
	

	@Test
	public void ttlWithWrapItemName() throws CEUException, ParseException, InterruptedException {
		CEUConfig.isAutoWrapItemName = true;
		ttl();
	}

	@Test
	public void ttl() throws CEUException, ParseException, InterruptedException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		//para atualizar um TTL temque reinserir a linha 
		Command command = new Command("DELETE FROM test WHERE key = ?",
				"userTestTime2");
		runner.exec(command);
		 
		
		command = new Command(
				"INSERT INTO test (key, email) VALUES (?,?) USING TTL ?",
				"userTestTime2","teste3234@gmail.com",5);
		
		runner.exec(command);
		
		command = new Command("SELECT * FROM test WHERE key = ?",
				"userTestTime2");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertNotNull(account.getEmail());
		Assert.assertTrue(account.getEmail().contains("@"));

		Thread.sleep(10000);

		account = runner.query(command, handler);

		Assert.assertNull(account);

	}

	
	/**TODO: O erroocorre por conta do analisador lexico que sera trocado*/
	@Test @Ignore
	public void ttlNoInjectWithWrapItemName() throws CEUException, ParseException, InterruptedException {


		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);
		CEUConfig.isAutoWrapItemName = true;

		//para atualizar um TTL temque reinserir a linha 
		Command command = new Command("DELETE FROM test WHERE key = ?",
				"userTestTime2");
		runner.exec(command);
		 
		
		 command = new Command(
					"INSERT INTO test (key,email,age) VALUES ( 'userTestTime2', "
							+ "'temporario@gmail.com', 45) USING TTL 5");
		 
		runner.exec(command);
		
		command = new Command("SELECT * FROM test WHERE key = ?",
				"userTestTime2");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertNotNull(account.getEmail());
		Assert.assertTrue(account.getEmail().contains("@"));

		Thread.sleep(10000);

		account = runner.query(command, handler);

		Assert.assertNull(account);

	}

	@Test(expected = CEUException.class)
	public void keyTest() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM test WHERE key = ?", "");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertNotNull(account.getEmail());
		Assert.assertTrue(account.getEmail().contains("@"));
	}

	@Test
	public void beanHandlerRowNotExistTest() throws CEUException,
			ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT email FROM test WHERE key = ?",
				"**notexist**");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertNull(account);
	}

	@Test
	public void beanListHandlerRowNotExistTest() throws CEUException,
			ParseException {

		BeanListHandler<AccountTestBean> handler = new BeanListHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT email FROM test WHERE key = ?",
				"**notexist**");

		List<AccountTestBean> accounts = runner.query(command, handler);

		Assert.assertEquals(0, accounts.size());
	}

	@Test
	public void beanSingleObjectHandlerNotExistTest() throws CEUException,
			ParseException {

		SingleObjectHandler<String> handler = new SingleObjectHandler<>(
				String.class, "email");

		Command command = new Command("SELECT email FROM test WHERE key = ?",
				"**notexist**");

		String email = runner.query(command, handler);

		Assert.assertNull(email);
	}

	@Test
	public void beanSingleObjectHandlerTest() throws CEUException,
			ParseException {

		SingleObjectHandler<String> handler = new SingleObjectHandler<>(
				String.class, "email");
		Command command = new Command("SELECT email FROM test WHERE key = ?",
				"userTest1");

		String email = runner.query(command, handler);

		Assert.assertEquals("userTest@gmail.com", email);
	}

	@Test
	public void listHandlerTest() throws CEUException, ParseException {

		ListHandler<String> handler = new ListHandler<>(String.class, "tags");
		Command command = new Command("SELECT tags FROM test WHERE key = ?",
				"userTest3");

		List<String> tags = runner.query(command, handler);

		Assert.assertEquals("Tag1-3", tags.get(0));
		Assert.assertEquals("Tag2-3", tags.get(1));
		Assert.assertEquals("Tag3-3", tags.get(2));
	}

	@Test
	public void setHandlerTest() throws CEUException, ParseException {

		SetHandler<BigInteger> handler = new SetHandler<>(BigInteger.class,
				"cmps");
		Command command = new Command("SELECT cmps FROM test WHERE key = ?",
				"userTest3");

		Set<BigInteger> cmps = runner.query(command, handler);

		Assert.assertTrue(cmps.contains(BigInteger.valueOf(103)));
		Assert.assertTrue(cmps.contains(BigInteger.valueOf(113)));
		Assert.assertTrue(cmps.contains(BigInteger.valueOf(123)));

	}

	@Test
	public void listMapHandlerTest() throws CEUException, ParseException {

		MapHandler<String, BigInteger> handler = new MapHandler<>(String.class,
				BigInteger.class, "friendsByName");
		Command command = new Command(
				"SELECT \"friendsByName\" FROM test WHERE key = ?", "userTest3");

		Map<String, BigInteger> tags = runner.query(command, handler);

		Assert.assertEquals(BigInteger.valueOf(1), tags.get("Amigo1-3"));
		Assert.assertEquals(BigInteger.valueOf(2), tags.get("Amigo2-3"));
		Assert.assertEquals(BigInteger.valueOf(3), tags.get("Amigo3-3"));
	}

	@Test
	public void fieldList() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT tags FROM test WHERE key = ?",
				"userTest2");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertEquals("Tag1-2", account.getTags().get(0));
		Assert.assertEquals("Tag2-2", account.getTags().get(1));
		Assert.assertEquals("Tag3-2", account.getTags().get(2));
	}

	@Test
	public void fieldMap() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command(
				"SELECT \"friendsByName\" FROM test WHERE key = ?", "userTest1");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertEquals(new BigInteger("1"), account.getFriendsByName()
				.get("Amigo1-1"));
		Assert.assertEquals(new BigInteger("2"), account.getFriendsByName()
				.get("Amigo2-1"));
	}

	public void fieldAll() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM test WHERE key = ?",
				"userTest1");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertEquals("userTest@gmail.com", account.getEmail());

		Assert.assertEquals("Pelé2", account.getTags().get(0));
		Assert.assertEquals("Pelé2", account.getTags().get(1));
		Assert.assertEquals("Pelé2", account.getTags().get(2));

		Assert.assertEquals(new BigInteger("1"), account.getFriendsByName()
				.get("Amigo1-1"));
		Assert.assertEquals(new BigInteger("2"), account.getFriendsByName()
				.get("Amigo2-1"));

		Assert.assertTrue(account.getCmps().contains(BigInteger.valueOf(101)));
		Assert.assertTrue(account.getCmps().contains(BigInteger.valueOf(111)));
		Assert.assertTrue(account.getCmps().contains(BigInteger.valueOf(121)));

	}

}
