package integration.com.taulukko.ceu.cassandra.datastax;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Command;
import com.taulukko.ceu.handler.SingleObjectHandler;

public class SingleHandlerTest extends BaseTest {

	@Test
	public void beanSingleObjectHandlerNotExistTest() throws CEUException,
			ParseException {

		SingleObjectHandler<String> handler = new SingleObjectHandler<>(
				String.class, "email");

		Command command = new Command("SELECT email FROM \"" + TABLE_NAME
				+ "\" WHERE key = ?", "**notexist**");

		Assert.assertFalse(runner.query(command, handler).isPresent());
	}

	@Test
	public void beanSingleObjectHandlerTest() throws CEUException,
			ParseException {

		SingleObjectHandler<String> handler = new SingleObjectHandler<>(
				String.class, "email");
		Command command = new Command("SELECT email FROM \"" + TABLE_NAME
				+ "\" WHERE key = ?", "userTest1");

		String email = existOptional(runner.query(command, handler));

		Assert.assertEquals("userTest@gmail.com", email);
	}

}
