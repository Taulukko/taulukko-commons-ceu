package com.taulukko.cassandra;


import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taulukko.cassandra.CEUConfig;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Command;
import com.taulukko.cassandra.astyanax.FactoryDataSourceAstyanax;
import com.taulukko.cassandra.astyanax.RunnerAstyanax;
import com.taulukko.cassandra.astyanax.handler.BeanHandler;

public class CEUTest {

	private static RunnerAstyanax runner = null;

	@BeforeClass
	public static void beforeClass() throws CEUException {

		CEUConfig.load("/server/config/taulukko/ceu.json");
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
				"CREATE TABLE test (key varchar PRIMARY KEY,email varchar,age int,tag_1 varchar,tag_2 varchar,tag_3 varchar,tag_4 varchar,tag_5 varchar,tag_22 varchar)");
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
	public void ttlTest() throws CEUException, InterruptedException {

		// nao funciona por conta do analisador lexico que sera trocado
		Command command = new Command(
				"INSERT INTO test (key,email,age) VALUES ( 'temporario1234', "
						+ "'temporario@gmail.com', 45) USING TTL 5");
 
		// funciona
		command = new Command(
				"INSERT INTO test (key,email,age) VALUES (?,?,?) USING TTL ?",
				"temporario1234", "temporario@gmail.com", 45, 1);
		runner.exec(command);

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		command = new Command(
				"SELECT * FROM test WHERE key = 'temporario1234' ");

		AccountTestBean account = runner.query(command, handler);
		Assert.assertEquals("temporario@gmail.com", account.getEmail());

		Thread.sleep(5000);

		account = runner.query(command, handler);
		Assert.assertNull(account);
	}

}
