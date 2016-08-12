package integration.com.taulukko.ceu.cassandra.datastax;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Command;
import com.taulukko.ceu.handler.ListHandler;
import com.taulukko.ceu.handler.SetHandler;

public class SetHandlerTest extends BaseTest {

	@Test
	public void setFromRow() throws CEUException, ParseException {

		Command command = new Command(
				"INSERT INTO "
						+ TABLE_NAME
						+ " (key,email,age,tags,\"friendsByName\",cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?})",
				"userTestset", "userTesta@gmail.com", 45, "Pelé1", "Pelé2",
				"Pelé3", "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55);
		runner.exec(command);

		ListHandler<String> handler = new ListHandler<String>("email",
				String.class);
		SetHandler<String> handlerSet = new SetHandler<String>("email",
				String.class);
		command = new Command("SELECT email FROM " + TABLE_NAME
				+ " where email= ? ALLOW FILTERING", "userTesta@gmail.com");

		List<String> emails = existOptional(runner.query(command, handler));
		Set<String> emailsSet = existOptional(runner.query(command, handlerSet));

		Assert.assertEquals(emails.size() - 1, emailsSet.size());

	}

	@Test
	public void setFromList() throws CEUException, ParseException {

		SetHandler<String> handlerSet = new SetHandler<String>("tags",
				String.class);
		Command command = new Command("SELECT tags FROM " + TABLE_NAME
				+ " where key= ? ", "userTest3");

		Set<String> emailsSet = existOptional(runner.query(command, handlerSet));

		Assert.assertEquals(3, emailsSet.size());

		Assert.assertEquals(true, emailsSet.contains("Tag1-3"));
		Assert.assertEquals(true, emailsSet.contains("Tag2-3"));
		Assert.assertEquals(true, emailsSet.contains("Tag3-3"));

		runner.exec(command);

	}

	@After
	public void afterMethod() throws CEUException {
		Command command = new Command("DELETE FROM " + TABLE_NAME
				+ " WHERE key = ? ", "userTestset");
		runner.exec(command);
	}

	@Test
	public void setFromFunction() throws CEUException, ParseException {

		Command command = new Command(
				"INSERT INTO "
						+ TABLE_NAME
						+ " (key,email,age,tags,\"friendsByName\",cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?})",
				"userTestset", "userTesta@gmail.com", 45, "Pelé1", "Pelé2",
				"Pelé3", "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55);
		runner.exec(command);

		SetHandler<String> handlerSet = new SetHandler<String>(
				r -> Optional.of(r.getString("email")));

		ListHandler<String> handler = new ListHandler<String>("email",
				String.class);

		command = new Command("SELECT email FROM " + TABLE_NAME
				+ " where email= ? ALLOW FILTERING", "userTesta@gmail.com");

		List<String> emailsList = existOptional(runner.query(command, handler));

		Set<String> emailsSet = existOptional(runner.query(command, handlerSet));

		Assert.assertEquals(emailsList.size() - 1, emailsSet.size());

	}

}
