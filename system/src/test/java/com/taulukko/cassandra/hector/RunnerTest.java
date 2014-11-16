package com.taulukko.cassandra.hector;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taulukko.cassandra.AccountTestBean;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Command;
import com.taulukko.cassandra.hector.FactoryDataSourceHector;
import com.taulukko.cassandra.hector.RunnerHector;
import com.taulukko.cassandra.hector.handler.BeanHandler;
import com.taulukko.cassandra.hector.handler.BeanListHandler;
import com.taulukko.common.ceu.test.InitializeTests;

public class RunnerTest {

	private static RunnerHector runner = null;

	@BeforeClass
	public static void beforeClass() throws CEUException {
		InitializeTests.runOnce();
		 
		runner = new RunnerHector(FactoryDataSourceHector.getDataSource("oauth"));

		Command command = null;
		try {

			command = new Command("DROP TABLE test");
			runner.exec(command);
		} catch (Exception e) {
			// fine, table test maybe not exist
		}
		command = new Command(
				"CREATE TABLE test (key varchar PRIMARY KEY,email varchar,age int,tag_1 varchar,tag_2 varchar,tag_3 varchar,tag_4 varchar,tag_5 varchar,tag_22 varchar) WITH comparator=UTF8Type AND default_validation = UTF8Type");
		runner.exec(command);

		command = new Command(
				"INSERT INTO test (key,email,age,tag_1,tag_2,tag_3,tag_4,tag_5,tag_22) VALUES (?,?,?,?,?,?,?,?,?)",
				"userTest", "userTest@gmail.com", 45, "Pelé1", "Pelé2",
				"Pelé3", "Pelé4", "Pelé5", "Pelé22");
		runner.exec(command);

		for (int index = 0; index < 100; index++) {
			command = new Command(
					"INSERT INTO test (key,email,age,tag_1) VALUES (?,?,?,?)",
					"userTest" + index, "userTest" + index + "@gmail.com", 45,
					"Pelé1");
			runner.exec(command);
		}
	}

	@AfterClass
	public static void afterClass() throws CEUException {

		Command command = new Command("DROP TABLE test");
		runner.exec(command);

	}

	@Test
	public void execInsert() throws CEUException {
		Command command = new Command(
				"INSERT INTO test (key,email,age,tag_1) VALUES (?,?,?,?)",
				"userTest11", "userTest11@gmail.com", 45, "Pelé1");
		runner.exec(command);

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		command = new Command("SELECT * FROM test WHERE key = ?", "userTest11");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertNotNull(account);
		Assert.assertEquals("userTest11@gmail.com", account.getEmail());
	}

	@Test
	public void execUpdate() throws CEUException {
		Command command = new Command(
				"UPDATE test set email =  ? WHERE key = ?",
				"userTest3_new@gmail.com", "userTest3");
		runner.exec(command);

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		command = new Command("SELECT * FROM test WHERE key = ?", "userTest3");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertNotNull(account);
		Assert.assertEquals("userTest3_new@gmail.com", account.getEmail());
	}

	@Test
	public void resultCount() throws CEUException {
		BeanListHandler<AccountTestBean> handler = new BeanListHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM test limit 3");

		List<AccountTestBean> accounts = runner.query(command, handler);

		Assert.assertEquals(3, accounts.size());
		Assert.assertEquals(3, command.getResult().getRowCount());

	}

	@Test
	public void resultTime() throws CEUException {
		BeanListHandler<AccountTestBean> handler = new BeanListHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM test limit 3");

		long timeMs = System.currentTimeMillis();
		long timeNs = System.nanoTime();
		runner.query(command, handler);

		System.out.println(command.getResult().getExecutionTimeMili());
		System.out.println(timeMs);
		Assert.assertTrue(command.getResult().getExecutionTimeMili() <= timeMs);
		Assert.assertTrue(command.getResult().getExecutionTimeMili() > 0);

		Assert.assertTrue(command.getResult().getExecutionTimeNano() <= timeNs);
		Assert.assertTrue(command.getResult().getExecutionTimeNano() > 0);

	}

	@Test
	public void ttlTest() throws CEUException, InterruptedException {

		int time = 1;// seconds
		Command command = new Command(
				"INSERT INTO test (key,email,age,tag_1) VALUES (?,?,?,?) USING TTL ?",
				"userTest12", "userTest15@gmail.com", 45, "Pelé1", time);
		runner.exec(command);

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		command = new Command("SELECT * FROM test WHERE key = ?", "userTest12");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertNotNull(account);
		Assert.assertEquals("userTest15@gmail.com", account.getEmail());

		Thread.sleep(time * 3000);

		account = runner.query(command, handler);
		Assert.assertNull(account);
	}

	@Test
	public void resultServerInfo() throws CEUException {
		BeanListHandler<AccountTestBean> handler = new BeanListHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM test limit 3");

		runner.query(command, handler);

		Assert.assertEquals("localhost", command.getResult().getServerInfo()
				.getHostName());
		Assert.assertEquals("127.0.0.1", command.getResult().getServerInfo()
				.getIP());
		Assert.assertEquals("localhost:9160", command.getResult()
				.getServerInfo().getUrl());
		Assert.assertEquals(new Integer(9160), command.getResult()
				.getServerInfo().getPort());
	}
}
