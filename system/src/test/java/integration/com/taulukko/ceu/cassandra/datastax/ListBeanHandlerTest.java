package integration.com.taulukko.ceu.cassandra.datastax;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.cassandra.AccountTestBean;
import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Command;
import com.taulukko.ceu.handler.BeanListHandler;
import com.taulukko.ceu.handler.ListHandler;

public class ListBeanHandlerTest extends BaseTest {

	@Test
	public void beanListHandlerTest() throws CEUException, ParseException {

		BeanListHandler<AccountTestBean> handler = new BeanListHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT * FROM \"" + TABLE_NAME
				+ "\" limit 10");

		List<AccountTestBean> accounts = existOptional(runner.query(command,
				handler));

		for (AccountTestBean account : accounts) {
			Assert.assertNotNull(account.getEmail());
			Assert.assertTrue(account.getEmail().contains("@"));
		}
	}

	@Test
	public void beanListHandlerRowNotExistTest() throws CEUException,
			ParseException {

		BeanListHandler<AccountTestBean> handler = new BeanListHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT email FROM \"" + TABLE_NAME
				+ "\" WHERE key = ?", "**notexist**");

		Optional<List<AccountTestBean>> ret = runner.query(command, handler);
		Assert.assertFalse(ret.isPresent());
	}

	@Test
	public void listHandlerTest() throws CEUException, ParseException {

		ListHandler<String> handler = new ListHandler<String>("tags",
				String.class);
		Command command = new Command("SELECT tags FROM \"" + TABLE_NAME
				+ "\" WHERE key = ?", "userTest3");

		List<String> tags = existOptional(runner.query(command, handler));

		Assert.assertEquals("Tag1-3", tags.get(0));
		Assert.assertEquals("Tag2-3", tags.get(1));
		Assert.assertEquals("Tag3-3", tags.get(2));
	}
}
