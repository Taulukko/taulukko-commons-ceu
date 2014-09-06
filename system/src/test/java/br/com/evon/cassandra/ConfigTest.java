package br.com.evon.cassandra;
 
import org.junit.Assert;
import org.junit.Test;

import br.com.evon.cassandra.ConfigBean;
import br.com.evon.cassandra.ServerInfo;

import com.evon.jsonParser.JSONParser;

public class ConfigTest {

	@Test
	public void parseTest() {

		String json = "{sliceSeparator:\"_\",servers:[{"
				+ "clusterName:\"Evon Cluster 01\",keyspace:\"oauth\","
				+ "url:\"localhost:9160\",primary:true}]}";
		
		ConfigBean config = JSONParser.convert(json, ConfigBean.class);
		Assert.assertNotNull(config);
		ServerInfo serverInfo = config.getServers().get(0);
		Assert.assertNotNull(serverInfo);
		Assert.assertEquals("Evon Cluster 01", serverInfo.getClusterName());
		Assert.assertEquals(true, serverInfo.getPrimary());
	}
}