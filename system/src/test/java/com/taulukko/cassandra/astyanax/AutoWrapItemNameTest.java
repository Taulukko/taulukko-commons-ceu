package com.taulukko.cassandra.astyanax;

import integration.com.taulukko.cassandra.InitializeTests;

import java.text.ParseException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.taulukko.cassandra.AccountTestBean;
import com.taulukko.cassandra.CEUConfig;
import com.taulukko.cassandra.CEUException;
import com.taulukko.cassandra.Command;
import com.taulukko.cassandra.astyanax.FactoryDataSourceAstyanax;
import com.taulukko.cassandra.astyanax.RunnerAstyanax;
import com.taulukko.cassandra.astyanax.handler.BeanHandler;

public class AutoWrapItemNameTest {

	private static RunnerAstyanax runner = null;
	private static boolean originalAutoWrapItemName = false;

	@BeforeClass
	public static void beforeClass() throws CEUException {

		InitializeTests.runOnce();
		 
		runner = new RunnerAstyanax(
				FactoryDataSourceAstyanax.getDataSource("oauth"));
		originalAutoWrapItemName = CEUConfig.isAutoWrapItemName;
	}

	@AfterClass
	public static void afterClass() throws CEUException {
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

		AccountTestBean accountBean = runner.query(command, handler);

		Assert.assertEquals(0, accountBean.getOldid());
		Assert.assertEquals(6, accountBean.getOldId());
	}

	@Test
	public void captureFieldsAutoNotWrapItemName() throws CEUException,
			ParseException {

		CEUConfig.isAutoWrapItemName = false;

		BeanHandler<AccountTestBean> handler = new BeanHandler<>(
				AccountTestBean.class);

		String cql = "SELECT * FROM accounts WHERE \"oldId\" = ?";

		Command command = new Command(cql, 6);

		AccountTestBean accountBean = runner.query(command, handler);

		Assert.assertEquals(0, accountBean.getOldid());
		Assert.assertEquals(6, accountBean.getOldId());
	}

}
