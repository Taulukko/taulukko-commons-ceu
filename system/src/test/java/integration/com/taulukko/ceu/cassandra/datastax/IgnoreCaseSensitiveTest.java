package integration.com.taulukko.ceu.cassandra.datastax;

import java.math.BigInteger;
import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.cassandra.AccountTestBean;
import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Command;
import com.taulukko.ceu.handler.BeanHandler;

public class IgnoreCaseSensitiveTest extends BaseTest {

	@Test
	public void fieldMap() throws CEUException, ParseException {

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		Command command = new Command("SELECT friendsByName FROM " + TABLE_ICST
				+ " WHERE key = ?", "userTest");

		AccountTestBean account = existOptional(runner.query(command, handler));

		Assert.assertEquals(new BigInteger("1"), account.getFriendsbyname()
				.get("Eduardo"));
		Assert.assertNull(account.getFriendsbyname().get("FriendElse"));
	}

}
