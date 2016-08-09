package integration.com.taulukko.ceu.cassandra.datastax;

import java.math.BigInteger;
import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.cassandra.AccountTestBean;
import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Command;
import com.taulukko.ceu.handler.BeanHandler;

public class BeanHandlerTest extends BaseTest {

	@Test
	public void simpleSelect() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM \"" + TABLE_NAME
				+ "\" WHERE key = ?", "userTest");

		AccountTestBean account = existOptional(runner.query(command, handler));

		Assert.assertNotNull(account.getEmail());
		Assert.assertTrue(account.getEmail().contains("@"));
	}

	@Test
	public void keyTest() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM \"" + TABLE_NAME
				+ "\" WHERE key = ?", "error");

		Assert.assertFalse(runner.query(command, handler).isPresent());
	}

	@Test(expected = RuntimeException.class)
	public void keyEmptyTest() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM \"" + TABLE_NAME
				+ "\" WHERE key = ?", "");

		existOptional(runner.query(command, handler));

	}

	@Test
	public void beanHandlerRowNotExistTest() throws CEUException,
			ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT email FROM \"" + TABLE_NAME
				+ "\" WHERE key = ?", "**notexist**");

		Assert.assertFalse(runner.query(command, handler).isPresent());
	}

	@Test
	public void fieldList() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT tags FROM \"" + TABLE_NAME
				+ "\" WHERE key = ?", "userTest2");

		AccountTestBean account = existOptional(runner.query(command, handler));

		Assert.assertEquals("Tag1-2", account.getTags().get(0));
		Assert.assertEquals("Tag2-2", account.getTags().get(1));
		Assert.assertEquals("Tag3-2", account.getTags().get(2));
	}

	@Test
	public void fieldMap() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT \"friendsByName\" FROM \""
				+ TABLE_NAME + "\" WHERE key = ?", "userTest1");

		AccountTestBean account = existOptional(runner.query(command, handler));

		BigInteger expected =BigInteger.valueOf(1); 
		Assert.assertEquals(expected.longValue(), account
				.getFriendsByName().get("Amigo1-1").longValue());
		expected =BigInteger.valueOf(2); 
		Assert.assertEquals(expected.longValue(), account
				.getFriendsByName().get("Amigo2-1").longValue());
	}

	public void fieldAll() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM \"" + TABLE_NAME
				+ "\" WHERE key = ?", "userTest1");

		AccountTestBean account = existOptional(runner.query(command, handler));

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
