package integration.com.taulukko.ceu.cassandra.datastax;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Command;
import com.taulukko.ceu.data.Row;
import com.taulukko.ceu.handler.SetHandlerBuilder;
import com.taulukko.ceu.handler.SetHandler;

public class SetHandlerTest extends BaseTest {

	@Test
	public void byAnyRowCollectbyFieldName() throws CEUException,
			ParseException {

		SetHandler<String> handlerSet = SetHandlerBuilder.build().byAnyRow()
				.collect().byFieldSet("tags", String.class);
		Command command = new Command("SELECT tags FROM " + TABLE_NAME
				+ " where key= ? ", "userTest3");

		Set<String> emailsSet = existOptional(runner.query(command, handlerSet));

		Assert.assertEquals(3, emailsSet.size());

		Assert.assertEquals(true, emailsSet.contains("Tag1-3"));
		Assert.assertEquals(true, emailsSet.contains("Tag2-3"));
		Assert.assertEquals(true, emailsSet.contains("Tag3-3"));

		runner.exec(command);

	}

	@Test
	public void byFirstRowCollectbyFieldName() throws CEUException,
			ParseException {

		SetHandler<String> handlerSet = SetHandlerBuilder.build().byAnyRow()
				.collect().byFieldSet("tags", String.class);
		Command command = new Command("SELECT tags FROM " + TABLE_NAME
				+ " where key= ? ", "userTest3");

		Set<String> emailsSet = existOptional(runner.query(command, handlerSet));

		Assert.assertEquals(3, emailsSet.size());

		Assert.assertEquals(true, emailsSet.contains("Tag1-3"));
		Assert.assertEquals(true, emailsSet.contains("Tag2-3"));
		Assert.assertEquals(true, emailsSet.contains("Tag3-3"));

		runner.exec(command);

	}

	@Test
	public void byRowFunction() throws CEUException, ParseException {

		SetHandler<String> handlerSet = SetHandlerBuilder
				.build()
				.byAnyRow()
				.collect()
				.byFunction(
						row -> Optional.of(new HashSet<String>(row.get()
								.getList("tags", String.class))));

		Command command = new Command("SELECT tags FROM " + TABLE_NAME
				+ " where key= ? ", "userTest3");

		Set<String> emailsSet = existOptional(runner.query(command, handlerSet));

		Assert.assertEquals(3, emailsSet.size());

		Assert.assertEquals(true, emailsSet.contains("Tag1-3"));
		Assert.assertEquals(true, emailsSet.contains("Tag2-3"));
		Assert.assertEquals(true, emailsSet.contains("Tag3-3"));

		runner.exec(command);

	}

	@Test
	public void byRowsFunction() throws CEUException, ParseException {

		Function<Stream<Row>, Optional<Set<String>>> converter = stream -> Optional
				.of(new HashSet<String>(
						stream.filter(
								row -> row.getString("key").equals("userTest3"))
								.findFirst().get()
								.getList("tags", String.class)));

		SetHandler<String> handlerSet = SetHandlerBuilder.build().byAllRows()
				.collect().byFunction(converter);

		Command command = new Command("SELECT key,tags FROM " + TABLE_NAME
				+ " ALLOW FILTERING ");

		Set<String> emailsSet = existOptional(runner.query(command, handlerSet));

		Assert.assertEquals(3, emailsSet.size());

		Assert.assertEquals(true, emailsSet.contains("Tag1-3"));
		Assert.assertEquals(true, emailsSet.contains("Tag2-3"));
		Assert.assertEquals(true, emailsSet.contains("Tag3-3"));

		runner.exec(command);

	}

	@Test
	public void usingFilter() throws CEUException, ParseException {

		SetHandler<String> handlerSet = SetHandlerBuilder.build()
				.filter(r -> r.getInt("age") == 45)
				.filter(r -> "userTestTime".equals(r.getString("key")))
				.byAnyRow().collect().byFieldSet("tags", String.class);

		Command command = new Command("SELECT age,key,tags FROM " + TABLE_NAME
				+ " ALLOW FILTERING");

		Set<String> emailsSet = existOptional(runner.query(command, handlerSet));

		Assert.assertEquals(3, emailsSet.size());

		Assert.assertEquals(true, emailsSet.contains("Player1"));
		Assert.assertEquals(true, emailsSet.contains("Player2"));
		Assert.assertEquals(true, emailsSet.contains("Player3"));

		runner.exec(command);

		command = new Command("SELECT email,tags FROM " + TABLE_NAME
				+ " ALLOW FILTERING");

		handlerSet = SetHandlerBuilder
				.build()
				.filter(r -> r.getString("email").equals("userTesta@gmail.com"))
				.byAnyRow().collect().byFieldSet("tags", String.class);

		command = new Command("SELECT key,email,tags FROM " + TABLE_NAME
				+ " ALLOW FILTERING");

		emailsSet = existOptional(runner.query(command, handlerSet));

		Assert.assertEquals(3, emailsSet.size());

		Assert.assertEquals(true, emailsSet.contains("testa1"));
		Assert.assertEquals(true, emailsSet.contains("testa2"));
		Assert.assertEquals(true, emailsSet.contains("testa3"));

		runner.exec(command);

	}

	@After
	public void afterMethod() throws CEUException {
		Command command = new Command("DELETE FROM " + TABLE_NAME
				+ " WHERE key = ? ", "userTestset");
		runner.exec(command);
	}

	/*
	 * @Test public void setFromRow() throws CEUException, ParseException {
	 * 
	 * Command command = new Command( "INSERT INTO " + TABLE_NAME +
	 * " (key,email,age,tags,\"friendsByName\",cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?})"
	 * , "userTestset", "userTesta@gmail.com", 45, "Pelé1", "Pelé2", "Pelé3",
	 * "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55); runner.exec(command);
	 * 
	 * ListHandler<String> handler = new ListHandler<String>("email",
	 * String.class); SetHandler<String> handlerSet = new
	 * SetHandler<String>("email", String.class); command = new
	 * Command("SELECT email FROM " + TABLE_NAME +
	 * " where email= ? ALLOW FILTERING", "userTesta@gmail.com");
	 * 
	 * List<String> emails = existOptional(runner.query(command, handler));
	 * Set<String> emailsSet = existOptional(runner.query(command, handlerSet));
	 * 
	 * Assert.assertEquals(emails.size() - 1, emailsSet.size());
	 * 
	 * }
	 * 
	 * @Test public void setFromFunction() throws CEUException, ParseException {
	 * 
	 * Command command = new Command( "INSERT INTO " + TABLE_NAME +
	 * " (key,email,age,tags,\"friendsByName\",cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?})"
	 * , "userTestset", "userTesta@gmail.com", 45, "Pelé1", "Pelé2", "Pelé3",
	 * "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55); runner.exec(command);
	 * 
	 * SetHandler<String> handlerSet = new SetHandler<String>( r ->
	 * Optional.of(r.getString("email")));
	 * 
	 * ListHandler<String> handler = new ListHandler<String>("email",
	 * String.class);
	 * 
	 * command = new Command("SELECT email FROM " + TABLE_NAME +
	 * " where email= ? ALLOW FILTERING", "userTesta@gmail.com");
	 * 
	 * List<String> emailsList = existOptional(runner.query(command, handler));
	 * 
	 * Set<String> emailsSet = existOptional(runner.query(command, handlerSet));
	 * 
	 * Assert.assertEquals(emailsList.size() - 1, emailsSet.size());
	 * 
	 * }
	 */

}
