package integration.com.taulukko.cassandra.astyanax;

import integration.com.taulukko.cassandra.TestUtil;

import java.math.BigInteger;
import java.text.ParseException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.taulukko.cassandra.AccountTestBean;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Command;
import com.taulukko.cassandra.astyanax.FactoryDataSourceAstyanax;
import com.taulukko.cassandra.astyanax.RunnerAstyanax;
import com.taulukko.cassandra.astyanax.handler.BeanHandler;

public class IgnoreCaseSensitive {

	private static RunnerAstyanax runner = null;

	@BeforeClass
	public static void beforeClass() throws CEUException {
		TestUtil.start();
		 
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
						+ " friendsByName map<text,int>, cmps set<int>)");
		runner.exec(command);

		command = new Command(
				"INSERT INTO test (key,email,age,tags,friendsByName,cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?})",
				"userTest", "userTest@gmail.com", 45, "Pelé1", "Pelé2",
				"Pelé3", "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55);
		runner.exec(command);

	 
	}

	@AfterClass
	public static void afterClass() throws CEUException {

		Command command = new Command("DROP TABLE test");
		runner.exec(command);

	}
 

	/**@TODO:Para resolver esse erro, deve-se separar em tokens de ITEM-NAME-CASE-SENSITIVE e ITEM-NAME-CASE-INSENSITIVE, onde:
	 * 
	 * ITEM-NAME-CASE-SENSITIVE  ::= "<ITEM-NAME-CASE-INSENSITIVE>"
	 * 
	 * Dependendo da configuração de autowrap, converter o ITEM-NAME-CASE-INSENSITIVE em:
	 * se true transformar em ITEM-NAME-CASE-SENSITIVE 
	 * se false transformar tudo para minusculas
	 * 
	 * Corrigir quando trocar o analisador lexico.

	TODO: O erroocorre por conta do analisador lexico que sera trocado
	 * */ 
	@Test @Ignore
	public void fieldMap() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command(
				"SELECT friendsByName FROM test WHERE key = ?", "userTest");

		AccountTestBean account = runner.query(command, handler);

		Assert.assertEquals(new BigInteger("1"), account.getFriendsByName()
				.get("Amigo1-1"));
		Assert.assertEquals(new BigInteger("2"), account.getFriendsByName()
				.get("Amigo2-1"));
	}
 

}
