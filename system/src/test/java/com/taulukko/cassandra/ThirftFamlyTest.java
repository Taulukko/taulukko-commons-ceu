package com.taulukko.cassandra;
import org.junit.Assert;
import org.junit.Test;

import com.netflix.astyanax.thrift.ThriftFamilyFactory;


public class ThirftFamlyTest {
	
	@Test
	public void testInstance()
	{
		ThriftFamilyFactory factoryFamily = ThriftFamilyFactory.getInstance();
		Assert.assertNotNull(factoryFamily);
	}

}
