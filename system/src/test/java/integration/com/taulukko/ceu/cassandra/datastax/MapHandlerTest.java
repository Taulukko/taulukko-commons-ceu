package integration.com.taulukko.ceu.cassandra.datastax;

import java.text.ParseException;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Command;
import com.taulukko.ceu.handler.MapHandler;
import com.taulukko.ceu.handler.MapHandlerBuilder;

public class MapHandlerTest extends BaseTest {

	@Test
	public void mapFromField() throws CEUException, ParseException {

		MapHandler<String, Integer> handlerSet = MapHandlerBuilder
				.<String, Integer> build().byAnyRow().collect()
				.byFieldMap("friendsByName", String.class, Integer.class);

 
		Command command = new Command("SELECT friendsByName FROM "
				+ TABLE_NAME + " WHERE key = ? ALLOW FILTERING", "userTest");

		Map<String, Integer> emailsMap = existOptional(runner.query(command,
				handlerSet));

		Assert.assertEquals(3, emailsMap.size());
		Assert.assertEquals(Integer.valueOf(1), emailsMap.get("Eduardo"));
		Assert.assertEquals(Integer.valueOf(2), emailsMap.get("Rafael"));

	}

	/*
	@Test
	public void mapFromRow() throws CEUException, ParseException {

		Command command = new Command(
				"INSERT INTO "
						+ TABLE_NAME
						+ " (key,email,age,tags,friendsByName,cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?})",
				"userTestmap", "userTesta@gmail.com", 33, "Pelé1", "Pelé2",
				"Pelé3", "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55);
		runner.exec(command);

		MapHandler<String, Integer> handlerSet = new MapHandler<String, Integer>(
				"key", String.class, "age", Integer.class);
		command = new Command("SELECT key,age,email FROM " + TABLE_NAME
				+ " where email= ? ALLOW FILTERING", "userTesta@gmail.com");

		Map<String, Integer> emailsMap = existOptional(runner.query(command,
				handlerSet));

		Assert.assertEquals(2, emailsMap.size());
		Assert.assertEquals(Integer.valueOf(33), emailsMap.get("userTestmap"));
		Assert.assertEquals(Integer.valueOf(45), emailsMap.get("userTest"));

	}

	@After
	public void afterMethod() throws CEUException {
		Command command = new Command("DELETE FROM \"" + TABLE_NAME
				+ "\" WHERE key = ? ", "userTestset");
		runner.exec(command);
	}

	@Test
	public void mapFromFunction() throws CEUException, ParseException {

		Command command = new Command(
				"INSERT INTO "
						+ TABLE_NAME
						+ " (key,email,age,tags,friendsByName,cmps) VALUES (?,?,?,[?,?,?],{?:?,?:? ,?:? },{?,?,?})",
				"userTestmap", "userTesta@gmail.com", 33, "Pelé1", "Pelé2",
				"Pelé3", "Eduardo", 1, "Rafael", 2, "Gabi", 3, 33, 44, 55);
		runner.exec(command);

		MapHandler<String, Integer> handlerSet = new MapHandler<String, Integer>(
				row -> "AAA" + row.getString("key"),
				row -> row.getInt("age") + 1000);
		command = new Command("SELECT key,age,email FROM " + TABLE_NAME
				+ " where email= ? ALLOW FILTERING", "userTesta@gmail.com");

		Map<String, Integer> emailsMap = existOptional(runner.query(command,
				handlerSet));

		Assert.assertEquals(2, emailsMap.size());
		Assert.assertEquals(Integer.valueOf(1033),
				emailsMap.get("AAAuserTestmap"));
		Assert.assertEquals(Integer.valueOf(1045), emailsMap.get("AAAuserTest"));

	}
*/
}
