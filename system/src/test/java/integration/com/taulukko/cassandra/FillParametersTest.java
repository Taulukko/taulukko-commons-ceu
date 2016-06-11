package integration.com.taulukko.cassandra;

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
		TestUtil.start();

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
	public void reset() {
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
						+ "name=null,lastIP=null WHERE key='232'", result);

	}

	@Test
	public void fillBoolean() throws CEUException, ParseException {

		String sql = "UPDATE accounts " + " SET isNewbie=? ";

		String result = runner.fillParameters(sql, new Object[] { true });

		Assert.assertEquals("UPDATE accounts SET isNewbie=true", result);

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
		String result = runner.fillParameters(
				"SELECT a FROM X WHERE a= ? and b= ? and c= ?", new Object[] {
						"34", "23", "12" });
		Assert.assertEquals(
				"SELECT a FROM X WHERE a= '34' and b= '23' and c= '12'", result);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ssZ");

		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = sdf.parse("2013/01/02 04:05:06-0700");
		result = runner.fillParameters(
				"SELECT a FROM X WHERE a= ? and b= ? and c = ?", new Object[] {
						34, "12", date });
		Assert.assertEquals(
				"SELECT a FROM X WHERE a= 34 and b= '12' and c = '2013-01-02 11:05:06+0000'",
				result);

	}


	@Test
	public void fillQueryInject() throws CEUException, ParseException {
		String result = runner.fillParameters("2611", new Object[] {
				"a' ; INSERT INTO teste values(23)", "23", "12" });
		Assert.assertEquals(
				"teste 'a'' ; INSERT INTO teste values(23)' '23' '12'", result);
	}

	@Test	
	public void fillWrap() throws CEUException, ParseException {
		CEUConfig.isAutoWrapItemName = true;
		String result = runner.fillParameters(
				"UPDATE teste set teste.a=? WHERE b = ?", new Object[] { "a",
						12 });

		Assert.assertEquals(
				"UPDATE \"teste\" set \"teste\".\"a\" = 'a' WHERE \"b\" = 12",
				result);

	}

	@Test
	public void fillQueryEndTest() throws CEUException, ParseException {

		String result = runner.fillParameters(
				"select * from teste where ? = ? using ttl ?", new Object[] {
						34, 23, 12 });
		Assert.assertEquals("select * from teste where 34 = 23 using ttl 12",
				result);

	}

	@Test
	public void fillQueryIgnore() throws CEUException, ParseException {
		String result = runner.fillParameters("select ?, ? from testeE where  field='??'",
				new Object[] { "34", "23" });
		Assert.assertEquals("select '34', '23' from testeE where field='??'", result);

	}

	@Test(timeout = 2000)
	public void fillInfinityLoopInTheEnd() throws CEUException, ParseException {
		runner.fillParameters("select testE from a where ? = ? or fa='??'",
				new Object[] { "34", "23" });

	}

	@Test(expected = CEUException.class)
	public void formatObjectNotSuported() throws CEUException {
		runner.format(this);
	}

}
