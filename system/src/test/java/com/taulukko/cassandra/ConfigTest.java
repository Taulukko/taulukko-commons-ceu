package com.taulukko.cassandra;
 
import org.junit.Assert;
import org.junit.Test;

import com.taulukko.commons.parsers.jsonParser.JSONParser;

public class ConfigTest {

	@Test
	public void parseTest() {

		String json = "{sliceSeparator:\"_\",servers:[{"
				+ "clusterName:\"Taulukko Cluster 01\",keyspace:\"infos\","
				+ "url:\"localhost:9160\",primary:true}]}";
		
		ConfigBean config = JSONParser.convert(json, ConfigBean.class);
		Assert.assertNotNull(config);
		ServerInfo serverInfo = config.getServers().get(0);
		Assert.assertNotNull(serverInfo);
		Assert.assertEquals("Taulukko Cluster 01", serverInfo.getClusterName());
		Assert.assertEquals(true, serverInfo.getPrimary());
	}
}
