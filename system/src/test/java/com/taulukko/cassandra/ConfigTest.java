package com.taulukko.cassandra;

import org.junit.Assert;
import org.junit.Test;

import com.taulukko.ceu.CEUConfig;
import com.taulukko.ceu.CEUException;
import com.taulukko.ceu.ConfigBean;
import com.taulukko.ceu.data.ServerInfo;
import com.taulukko.commons.parsers.jsonParser.JSONParser;
import com.taulukko.commons.util.io.EFile;

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
		Assert.assertEquals(true, serverInfo.isPrimary());
	}
	
	@Test
	public void loadedProperty() throws CEUException {

		 assert !CEUConfig.loaded;
		 CEUConfig.load(EFile.getWorkPath()+"/src/test/resources/config/ceu.json");
		 assert CEUConfig.loaded;
	}
}
