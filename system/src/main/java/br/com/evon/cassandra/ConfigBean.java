package br.com.evon.cassandra;

import java.util.ArrayList;
import java.util.List;

public class ConfigBean {

	private boolean isAutoWrapItemName = false;

	private String sliceSeparator = null;
	private List<ServerInfo> servers = new ArrayList<>();

	public boolean isAutoWrapItemName() {
		return isAutoWrapItemName;
	}

	public void setAutoWrapItemName(boolean autoWrapItemName) {
		this.isAutoWrapItemName = autoWrapItemName;
	}

	public String getSliceSeparator() {
		return sliceSeparator;
	}

	public void setSliceSeparator(String sliceSeparator) {
		this.sliceSeparator = sliceSeparator;
	}

	public List<ServerInfo> getServers() {
		return servers;
	}

	public void setServers(List<ServerInfo> servers) {
		this.servers = servers;
	}
}
