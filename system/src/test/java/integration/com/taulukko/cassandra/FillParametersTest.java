package integration.com.taulukko.cassandra;

import integration.com.taulukko.ceu.cassandra.datastax.BaseTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.taulukko.ceu.CEUConfig;
import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Runner;

public class FillParametersTest extends BaseTest {

	 
	@Before
	public void reset() {
		CEUConfig.isAutoWrapItemName = false;
	}

	@Test(expected = CEUException.class)
	public void fillQueryFewParameters() throws CEUException {

		Runner.fillParameters("teste ? ? ? ", new Object[] { "" });
	}

	@Test(expected = CEUException.class)
	public void fillQueryManyParameters() throws CEUException {
		Runner.fillParameters("teste ? ? ?", new Object[] { "", "", "", "" });
	}

	public void fillQueryNullWhere() throws CEUException, ParseException {

		String sql = "UPDATE accounts "
				+ " SET email=?, password=?,lastAccess=?,name=?,lastIP=? "
				+ " WHERE key=? ";

		String result = Runner.fillParameters(sql, new Object[] { "1", "2",
				"2013-11-12 19:54:32+0000", null, null, null });

		Assert.assertEquals(
				"UPDATE accounts "
						+ " SET email=1, password=2,lastAccess='2013-11-12 19:54:32+0000',name=NULL,lastIP=NULL "
						+ " WHERE key=NULL ", result);

	}

	@Test
	public void fillQueryNull() throws CEUException, ParseException {

		String sql = "UPDATE accounts "
				+ "SET email=?, password=?,lastAccess=?,name=?,lastIP=? "
				+ "WHERE key=? ";

		String result = Runner.fillParameters(
				sql,
				new Object[] { "1", "2", "2013-11-12 19:54:32+0000", null,
						null, "232" }).trim();

		Assert.assertEquals(
				"UPDATE accounts SET email='1', password='2',lastaccess='2013-11-12 19:54:32+0000',"
						+ "name=NULL,lastip=NULL WHERE key='232'", result);

	}

	@Test
	public void fillBoolean() throws CEUException, ParseException {

		String sql = "UPDATE accounts SET isNewbie=?";

		String result = Runner.fillParameters(sql, new Object[] { true });

		Assert.assertEquals("UPDATE accounts SET isnewbie=true", result);

	}

	@Test
	public void fillQueryQuestion() throws CEUException, ParseException {

		String sql = "UPDATE accounts SET email='gand?', password=?"
				+ " WHERE key=? AND show='teste?'";

		String result = Runner.fillParameters(sql, new Object[] { 1, "2" });

		Assert.assertEquals(
				"UPDATE accounts SET email='gand?', password=1 WHERE key='2' AND show='teste?'",
				result);

	}

	@Test
	public void fillQuery() throws CEUException, ParseException {
		String result = Runner.fillParameters(
				"SELECT a FROM X WHERE a= ? and b= ? and c= ?", new Object[] {
						"34", "23", "12" });
		Assert.assertEquals(
				"SELECT a FROM x WHERE a= '34' and b= '23' and c= '12'", result);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ssZ");

		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = sdf.parse("2013/01/02 04:05:06-0700");
		result = Runner.fillParameters(
				"SELECT a FROM X WHERE a= ? and b= ? and c = ?", new Object[] {
						34, "12", date });
		Assert.assertEquals(
				"SELECT a FROM x WHERE a= 34 and b= '12' and c = '2013-01-02 11:05:06+0000'",
				result);

	}

	@Test
	public void fillQueryInject() throws CEUException, ParseException {
		String result = Runner
				.fillParameters("SELECT a from b where c = ? and d=?  and e=?",
						new Object[] {
								"a' ; INSERT INTO teste (age) values(23)",
								"23", "12" });
		Assert.assertEquals(
				"SELECT a from b where c = 'a'' ; INSERT INTO teste (age) values(23)' and d='23'  and e='12'",
				result);
	}

	@Test
	public void fillWrap() throws CEUException, ParseException {
		CEUConfig.isAutoWrapItemName = true;
		String result = Runner.fillParameters(
				"UPDATE teste set teste.a=? WHERE b = ?", new Object[] { "a",
						12 });

		Assert.assertEquals(
				"UPDATE \"teste\" set \"teste\".\"a\"='a' WHERE \"b\" = 12",
				result);

	}

	@Test
	public void fillQueryEndTest() throws CEUException, ParseException {

		String result = Runner.fillParameters(
				"select * from teste where ? = ? using ttl ?", new Object[] {
						34, 23, 12 });
		Assert.assertEquals("select * from teste where 34 = 23 using ttl 12",
				result);

	}

	@Test
	public void fillQueryIgnore() throws CEUException, ParseException {
		String result = Runner.fillParameters(
				"select ?, ? from testeE where field='??'", new Object[] {
						"34", "23" });
		Assert.assertEquals("select '34', '23' from testee where field='??'",
				result);

	}

	@Test(timeout = 2000)
	public void fillInfinityLoopInTheEnd() throws CEUException, ParseException {
		Runner.fillParameters("select testee from a where ? = ? or fa='??'",
				new Object[] { "34", "23" });

	}

	@Test(expected = CEUException.class)
	public void formatObjectNotSuported() throws CEUException {
		Runner.format(this);
	}

}
