package integration.com.taulukko.ceu.cassandra.datastax;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.cassandra.AccountTestBean;
import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Command;
import com.taulukko.ceu.handler.BeanHandler;

public class TTLTest extends BaseTest {

	// error in 2.0 or oldest cassandra versions
	@Test
	public void ttl() throws CEUException, ParseException, InterruptedException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		// para atualizar um TTL temque reinserir a linha
		Command command = new Command("DELETE FROM \"" + TABLE_NAME
				+ "\" WHERE key = ?", "userTestTime2");
		runner.exec(command);

		// 30s is the min to be security that test
		command = new Command("INSERT INTO \"" + TABLE_NAME
				+ "\" (key, email) VALUES (?,?) USING TTL ?", "userTestTime2",
				"teste3234@gmail.com", 10);

		runner.exec(command);

		Thread.sleep(5000);

		command = new Command("SELECT * FROM \"" + TABLE_NAME
				+ "\" WHERE key = ?", "userTestTime2");

		AccountTestBean account = existOptional(runner.query(command, handler));

		Assert.assertNotNull(account.getEmail());
		Assert.assertTrue(account.getEmail().contains("@"));

		Thread.sleep(10000);

		Assert.assertFalse(runner.query(command, handler).isPresent());

	}
	
	@Test
	public void ttlTest() throws CEUException, InterruptedException {

		//static ttl
		Command command = new Command("INSERT INTO " + TABLE_NAME
				+ " (key,email,age) VALUES ( 'temporario1234', "
				+ "'temporario@gmail.com', 45) USING TTL 2");

		runner.exec(command);

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		command = new Command("SELECT * FROM " + TABLE_NAME
				+ " WHERE key = 'temporario1234' ");

		AccountTestBean account = existOptional(runner.query(command, handler));
		Assert.assertEquals("temporario@gmail.com", account.getEmail());

		Thread.sleep(5000);


		//dynamic ttl
		Assert.assertFalse(runner.query(command, handler).isPresent());

		command = new Command("INSERT INTO " + TABLE_NAME
				+ " (key,email,age) VALUES (?,?,?) USING TTL ?",
				"temporario1234", "temporario@gmail.com", 45, 2);
		runner.exec(command);

		handler = new BeanHandler<>(AccountTestBean.class);

		command = new Command("SELECT * FROM " + TABLE_NAME
				+ " WHERE key = 'temporario1234' ");

		account = existOptional(runner.query(command, handler));
		Assert.assertEquals("temporario@gmail.com", account.getEmail());

		Thread.sleep(5000);
 
		Assert.assertFalse(runner.query(command, handler).isPresent());
	}
}
