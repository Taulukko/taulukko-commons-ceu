package integration.com.taulukko.ceu.cassandra.datastax;

import io.netty.util.internal.ConcurrentSet;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Command;
import com.taulukko.ceu.handler.ListHandler;
import com.taulukko.ceu.handler.SetHandler;

public class SetHandlerTest extends BaseTest {

	@Test
	public void listToSet() throws CEUException, ParseException {

		Command command = new Command(
				"INSERT INTO \""
						+ TABLE_NAME
						+ "\" (key,email,age,tags,\"friendsByName\",cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?})",
				"userTestset", "userTesta@gmail.com", 45, "Pelé1", "Pelé2",
				"Pelé3", "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55);
		runner.exec(command);

		ListHandler<String> handler = new ListHandler<String>("email",
				String.class);
		SetHandler<String> handlerSet = new SetHandler<String>("email",
				String.class);
		command = new Command("SELECT email FROM \"" + TABLE_NAME
				+ "\" where email= ? ALLOW FILTERING", "userTesta@gmail.com");

		List<String> emails = existOptional(runner.query(command, handler));
		Set<String> emailsSet = existOptional(runner.query(command, handlerSet));

		Assert.assertEquals(emails.size() - 1, emailsSet.size());

		command = new Command("DELETE FROM \"" + TABLE_NAME
				+ "\" WHERE key = ? ", "userTestset");
		runner.exec(command);

	}

	@Test
	public void listFromRows() throws CEUException, ParseException {

		ListHandler<String> handler = new ListHandler<String>("email",
				String.class);
		Command command = new Command("SELECT email FROM \"" + TABLE_NAME
				+ "\" allow filtering ");

		List<String> emails = existOptional(runner.query(command, handler));
		final Set<String> setEmails = new ConcurrentSet<String>();

		emails.stream().forEach(email -> {
			Assert.assertTrue(emails.get(0).startsWith("userTest"));
			Assert.assertTrue(emails.get(0).endsWith("@gmail.com"));
			setEmails.add(email);
		});

		Assert.assertEquals(emails.size(), setEmails.size());

	}

	@Test
	public void listFromFieldSet() throws CEUException, ParseException {

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
