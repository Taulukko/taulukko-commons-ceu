package com.taulukko.ceu;

import java.util.ArrayList;
import java.util.List;

import com.taulukko.ceu.data.ServerInfo;

public class ConfigBean {

	private boolean isAutoWrapItemName = false;

	private List<ServerInfo> servers = new ArrayList<>();

	public boolean isAutoWrapItemName() {
		return isAutoWrapItemName;
	}

	public void setAutoWrapItemName(boolean autoWrapItemName) {
		this.isAutoWrapItemName = autoWrapItemName;
	}

	public List<ServerInfo> getServers() {
		return servers;
	}

	public void setServers(List<ServerInfo> servers) {
		this.servers = servers;
	}
}
