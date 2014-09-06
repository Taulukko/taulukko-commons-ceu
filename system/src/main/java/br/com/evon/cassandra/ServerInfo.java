package br.com.evon.cassandra;

public class ServerInfo {

	private String clusterName;
	private String keyspace;
	private Boolean primary = false;
	private String hostName;
	private String IP;
	private Integer port;
	private Integer maxConsPerHost = 2;
	private Integer maxTimeoutWhenExhausted = 120000;
	private Integer connectTimeout = 10000;
	private Integer socketTimeout = 60000;

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public void setMaxTimeoutWhenExhausted(Integer maxTimeoutWhenExhausted) {
		this.maxTimeoutWhenExhausted = maxTimeoutWhenExhausted;
	}

	public Integer getMaxConsPerHost() {
		return maxConsPerHost;
	}

	public void setMaxConsPerHost(Integer maxConsPerHost) {
		this.maxConsPerHost = maxConsPerHost;
	}

	// não utilizado no arquivo de config
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getPrimary() {
		return primary;
	}

	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getKeyspace() {
		return keyspace;
	}

	public void setKeyspace(String keyspace) {
		this.keyspace = keyspace;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public int getMaxTimeoutWhenExhausted() {

		return maxTimeoutWhenExhausted;
	}

}
