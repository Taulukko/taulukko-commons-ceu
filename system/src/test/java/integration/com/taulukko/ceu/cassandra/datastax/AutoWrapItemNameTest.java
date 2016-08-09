package integration.com.taulukko.ceu.cassandra.datastax;

import java.text.ParseException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taulukko.cassandra.AccountTestBean;
import com.taulukko.ceu.CEUConfig;
import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.Command;
import com.taulukko.ceu.handler.BeanHandler;

public class AutoWrapItemNameTest extends BaseTest {

	private static boolean originalAutoWrapItemName = false;

	@BeforeClass
	public static void beforeClass() throws CEUException {

		BaseTest.beforeClass();
		originalAutoWrapItemName = CEUConfig.isAutoWrapItemName;
	}

	@AfterClass
	public static void afterClass() throws CEUException {
		BaseTest.afterClass();
		CEUConfig.isAutoWrapItemName = originalAutoWrapItemName;
	}

	@Test
	public void captureFieldsAutoWrapItemName() throws CEUException,
			ParseException {

		CEUConfig.isAutoWrapItemName = true;

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		String cql = "SELECT * FROM accounts WHERE oldId = ? ALLOW FILTERING";

		Command command = new Command(cql, 6);

		AccountTestBean accountBean = existOptional(runner.query(command,
				handler));

		Assert.assertEquals(6, accountBean.getOldId());
		Assert.assertEquals(6, accountBean.getOldid());

	}

	@Test
	public void captureFieldsAutoNotWrapItemName() throws CEUException,
			ParseException {

		CEUConfig.isAutoWrapItemName = false;

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		String cql = "SELECT * FROM accounts WHERE \"oldId\" = ?";

		Command command = new Command(cql, 6);

		AccountTestBean accountBean = existOptional(runner.query(command,
				handler));

		Assert.assertEquals(6, accountBean.getOldId());

		Assert.assertEquals(6, accountBean.getOldid());
	}

	
	@Test
	public void captureFieldsAutoNotWrapItemName2() throws CEUException,
			ParseException {

		CEUConfig.isAutoWrapItemName = false;

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		String cql = "SELECT * FROM accounts WHERE oldId = ?";

		Command command = new Command(cql, 6);

		AccountTestBean accountBean = existOptional(runner.query(command,
				handler));

		Assert.assertEquals(6, accountBean.getOldId());

		Assert.assertEquals(6, accountBean.getOldid());
	}

}
