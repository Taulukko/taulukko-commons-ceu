package integration.com.taulukko.ceu.cassandra.datastax;

import java.text.ParseException;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Command;
import com.taulukko.ceu.data.Row;
import com.taulukko.ceu.handler.MapHandler;
import com.taulukko.ceu.handler.MapHandlerBuilder;

public class MapHandlerTest extends BaseTest {

	@Test
	public void mapFromField() throws CEUException, ParseException {

		MapHandler<String, Integer> handlerSet = MapHandlerBuilder
				.<String, Integer> build().byAnyRow().collect()
				.byFieldMap("friendsByName", String.class, Integer.class);

		Command command = new Command("SELECT friendsByName FROM " + TABLE_NAME
				+ " WHERE key = ? ALLOW FILTERING", "userTest");

		Map<String, Integer> emailsMap = existOptional(runner.query(command,
				handlerSet));

		Assert.assertEquals(3, emailsMap.size());
		Assert.assertEquals(Integer.valueOf(1), emailsMap.get("Eduardo"));
		Assert.assertEquals(Integer.valueOf(2), emailsMap.get("Rafael"));

	}

	@Test
	public void mapFromFunction() throws CEUException, ParseException {

		MapHandler<String, Integer> handlerSet = MapHandlerBuilder
				.<String, Integer> build()
				.byAnyRow()
				.collect()
				.<String, Integer> byFunction(
						row -> Optional.of(row.get().getMap(0, String.class,
								Integer.class)));

		Command command = new Command("SELECT friendsByName FROM " + TABLE_NAME
				+ " WHERE key = ? ALLOW FILTERING", "userTest");

		Map<String, Integer> emailsMap = existOptional(runner.query(command,
				handlerSet));

		Assert.assertEquals(3, emailsMap.size());
		Assert.assertEquals(Integer.valueOf(1), emailsMap.get("Eduardo"));
		Assert.assertEquals(Integer.valueOf(2), emailsMap.get("Rafael"));

	}

	private static Optional<Map<String, Integer>> getTest(Optional<Row> orow) {
		Row row = orow.get();
		Map<String, Integer> ret = row.getMap(0, String.class, Integer.class);
		return Optional.of(ret);
	}

	@Test
	public void mapFromFunctionByFirstFunction() throws CEUException,
			ParseException {

		MapHandler<String, Integer> handlerSet = MapHandlerBuilder
				.<String, Integer> build().byFirstRow().collect()
				.<String, Integer> byFunction(MapHandlerTest::getTest);

		Command command = new Command("SELECT friendsByName FROM " + TABLE_NAME
				+ " WHERE email = ? ALLOW FILTERING", "mapTest@gmail.com");

		Map<String, Integer> emailsMap = existOptional(runner.query(command,
				handlerSet));

		Assert.assertEquals(3, emailsMap.size());
		Assert.assertEquals(Integer.valueOf(1), emailsMap.get("Eduardo"));
		Assert.assertEquals(Integer.valueOf(2), emailsMap.get("Rafael"));

	}

	@Test
	public void mapFromRowsKeyAndField() throws CEUException, ParseException {

		MapHandler<String, Integer> handlerSet = MapHandlerBuilder
				.<String, Integer> build().byAllRows().collect()
				.prepareKey(MapHandlerBuilder.byKey())
				.prepareValue(MapHandlerBuilder.byField("age", Integer.class))
				.collect();

		Command command = new Command("SELECT age,key FROM " + TABLE_NAME
				+ " WHERE email = ? ALLOW FILTERING", "mapTest@gmail.com");

		Map<String, Integer> emailsMap = existOptional(runner.query(command,
				handlerSet));

		Assert.assertEquals(Integer.valueOf(45), emailsMap.get("mapuserTest1"));
		Assert.assertEquals(Integer.valueOf(46), emailsMap.get("mapuserTest2"));

	}

}
