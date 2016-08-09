package com.taulukko.ceu.data;

public class ServerInfo {
	private String hostName;
	private String port;
	private String url;
	private String keyspace;

	private String clusterName = "Test Cluster";
	private int maxConsPerHost = 30;
	private int maxTimeoutWhenExhausted = 120000;
	private int maxSchemaAgreementWaitSeconds = 30000;
	
	private boolean primary=false;

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public int getMaxConsPerHost() {
		return maxConsPerHost;
	}

	public void setMaxConsPerHost(int maxConsPerHost) {
		this.maxConsPerHost = maxConsPerHost;
	}

	public int getMaxTimeoutWhenExhausted() {
		return maxTimeoutWhenExhausted;
	}

	public void setMaxTimeoutWhenExhausted(int maxTimeoutWhenExhausted) {
		this.maxTimeoutWhenExhausted = maxTimeoutWhenExhausted;
	}

	public int getMaxSchemaAgreementWaitSeconds() {
		return maxSchemaAgreementWaitSeconds;
	}

	public void setMaxSchemaAgreementWaitSeconds(
			int maxSchemaAgreementWaitSeconds) {
		this.maxSchemaAgreementWaitSeconds = maxSchemaAgreementWaitSeconds;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	private int socketTimeout = 60000;
	private int connectTimeout = 10000;

	public String getKeyspace() {
		return keyspace;
	}

	public void setKeyspace(String keyspace) {
		this.keyspace = keyspace;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostname) {
		this.hostName = hostname;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	} 
}
