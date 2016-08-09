package com.taulukko.ceu.cassandra.datastax;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.taulukko.ceu.data.Errors;
import com.taulukko.ceu.data.Metrics;

public class DSMetrics implements Metrics {
	private com.datastax.driver.core.Metrics coreMetrics = null;

	public DSMetrics(com.datastax.driver.core.Metrics metrics) {
		this.coreMetrics = metrics;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metrics#getCoreMetrics()
	 */
	@Override
	public com.datastax.driver.core.Metrics getCoreMetrics() {
		return coreMetrics;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metrics#hashCode()
	 */
	@Override
	public int hashCode() {
		return coreMetrics.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metrics#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return coreMetrics.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metrics#getRegistry()
	 */
	@Override
	public MetricRegistry getRegistry() {
		return coreMetrics.getRegistry();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metrics#getRequestsTimer()
	 */
	@Override
	public Timer getRequestsTimer() {
		return coreMetrics.getRequestsTimer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metrics#getErrorMetrics()
	 */
	@Override
	public Errors getErrorMetrics() {
		return new DSErrors(coreMetrics.getErrorMetrics());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metrics#getKnownHosts()
	 */
	@Override
	public Gauge<Integer> getKnownHosts() {
		return coreMetrics.getKnownHosts();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metrics#getConnectedToHosts()
	 */
	@Override
	public Gauge<Integer> getConnectedToHosts() {
		return coreMetrics.getConnectedToHosts();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metrics#getOpenConnections()
	 */
	@Override
	public Gauge<Integer> getOpenConnections() {
		return coreMetrics.getOpenConnections();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metrics#getTrashedConnections()
	 */
	@Override
	public Gauge<Integer> getTrashedConnections() {
		return coreMetrics.getTrashedConnections();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metrics#getExecutorQueueDepth()
	 */
	@Override
	public Gauge<Integer> getExecutorQueueDepth() {
		return coreMetrics.getExecutorQueueDepth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.Metrics#getBlockingExecutorQueueDepth()
	 */
	@Override
	public Gauge<Integer> getBlockingExecutorQueueDepth() {
		return coreMetrics.getBlockingExecutorQueueDepth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metrics#toString()
	 */
	@Override
	public String toString() {
		return coreMetrics.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.Metrics#getReconnectionSchedulerQueueSize
	 * ()
	 */
	@Override
	public Gauge<Integer> getReconnectionSchedulerQueueSize() {
		return coreMetrics.getReconnectionSchedulerQueueSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.Metrics#getTaskSchedulerQueueSize()
	 */
	@Override
	public Gauge<Integer> getTaskSchedulerQueueSize() {
		return coreMetrics.getTaskSchedulerQueueSize();
	}

}
