package com.taulukko.cassandra;

import integration.com.taulukko.cassandra.InitializeTests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taulukko.cassandra.CEUConfig;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Command;
import com.taulukko.cassandra.Handler;
import com.taulukko.cassandra.Runner;

public class FillParametersTest {

	private static Runner<Object> runner = null;

	@BeforeClass
	public static void init() throws CEUException {
		InitializeTests.runOnce();
		 
		runner = new Runner<Object>() {
			@Override
			public void exec(Command command) throws CEUException {
			}

			@Override
			public <T> T query(Command command, Handler<T, Object> handler)
					throws CEUException {
				return null;
			}
		};
	}
	
	@Before
	public void reset()
	{
		CEUConfig.isAutoWrapItemName = false;
	}
	

	@Test(expected = CEUException.class)
	public void fillQueryFewParameters() throws CEUException {

		runner.fillParameters("teste ? ? ? ", new Object[] { "" });
	}

	@Test(expected = CEUException.class)
	public void fillQueryManyParameters() throws CEUException {
		runner.fillParameters("teste ? ? ?", new Object[] { "", "", "", "" });
	}

	@Test(expected = CEUException.class)
	public void fillQueryNullWhere() throws CEUException, ParseException {

		String sql = "UPDATE accounts "
				+ " SET email=?, password=?,lastAccess=?,name=?,lastIP=? "
				+ " WHERE key=? ";

		runner.fillParameters(sql, new Object[] { "1", "2",
				"2013-11-12 19:54:32+0000", null, null, null });

	}

	@Test
	public void fillQueryNull() throws CEUException, ParseException {

		String sql = "UPDATE accounts "
				+ " SET email=?, password=?,lastAccess=?,name=?,lastIP=? "
				+ " WHERE key=? ";

		String result = runner.fillParameters(sql, new Object[] { "1", "2",
				"2013-11-12 19:54:32+0000", null, null, "232" });

		Assert.assertEquals(
				"UPDATE accounts SET email='1', password='2',lastAccess='2013-11-12 19:54:32+0000',"
				+ "name=null,lastIP=null WHERE key='232'",
				result);

	}
	
	@Test
	public void fillBoolean() throws CEUException, ParseException {

		String sql = "UPDATE accounts "
				+ " SET isNewbie=? ";

		String result = runner.fillParameters(sql, new Object[] { true });

		Assert.assertEquals(
				"UPDATE accounts SET isNewbie=true",
				result);

	}

	@Test
	public void fillQueryQuestion() throws CEUException, ParseException {

		String sql = "UPDATE accounts SET email='gand?', password=?"
				+ " WHERE key=? AND show='teste?'";

		String result = runner.fillParameters(sql, new Object[] { 1, "2" });

		Assert.assertEquals(
				"UPDATE accounts SET email='gand?', password=1 WHERE key='2' AND show='teste?'",
				result);

	}

	@Test
	public void fillQuery() throws CEUException, ParseException {
		String result = runner.fillParameters("teste ? ? ?", new Object[] {
				"34", "23", "12" });
		Assert.assertEquals("teste '34' '23' '12'", result);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ssZ");

		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = sdf.parse("2013/01/02 04:05:06-0700");
		result = runner.fillParameters("teste ? ? ?", new Object[] { date, 34,
				"12" });
		Assert.assertEquals("teste '2013-01-02 11:05:06+0000' 34 '12'", result);

	}

	@Test
	public void fillQueryInject() throws CEUException, ParseException {
		String result = runner.fillParameters("teste ? ? ?", new Object[] {
				"a' ; INSERT INTO teste values(23)", "23", "12" });
		Assert.assertEquals(
				"teste 'a'' ; INSERT INTO teste values(23)' '23' '12'", result);
	}

	@Test
	public void fillWrap() throws CEUException, ParseException {
		CEUConfig.isAutoWrapItemName = true;
		String result = runner.fillParameters("INSERT INTO teste WHERE teste.a =  ? AND b = ?", new Object[] {
				"a",  12 });
		Assert.assertEquals(
				"INSERT INTO \"teste\" WHERE \"teste\".\"a\" = 'a' AND \"b\" = 12",
				result);
	}

	@Test
	public void fillQueryEndTest() throws CEUException, ParseException {

		String result = runner.fillParameters("teste ? ? ?", new Object[] {
				"34", "23", "12" });
		Assert.assertEquals("teste '34' '23' '12'", result);

		result = runner.fillParameters("teste ? ? ?", new Object[] { "34",
				"23", "12" });
		Assert.assertEquals("teste '34' '23' '12'", result);

	}

	@Test
	public void fillQueryIgnore() throws CEUException, ParseException {
		String result = runner.fillParameters("teste ? ? field='??'",
				new Object[] { "34", "23" });
		Assert.assertEquals("teste '34' '23' field='??'", result);

	}

	@Test(timeout = 2000)
	public void fillInfinityLoopInTheEnd() throws CEUException, ParseException {
		runner.fillParameters("teste ? ? field='??'",
				new Object[] { "34", "23" });

	}

	@Test(expected = CEUException.class)
	public void formatObjectNotSuported() throws CEUException {
		runner.format(this);
	}

}
