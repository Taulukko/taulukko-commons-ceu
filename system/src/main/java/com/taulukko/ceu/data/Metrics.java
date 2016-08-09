package com.taulukko.ceu.data;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

public interface Metrics {

	public abstract com.datastax.driver.core.Metrics getCoreMetrics();

	public abstract int hashCode();

	public abstract boolean equals(Object obj);

	public abstract MetricRegistry getRegistry();

	public abstract Timer getRequestsTimer();

	public abstract Errors getErrorMetrics();

	public abstract Gauge<Integer> getKnownHosts();

	public abstract Gauge<Integer> getConnectedToHosts();

	public abstract Gauge<Integer> getOpenConnections();

	public abstract Gauge<Integer> getTrashedConnections();

	public abstract Gauge<Integer> getExecutorQueueDepth();

	public abstract Gauge<Integer> getBlockingExecutorQueueDepth();

	public abstract String toString();

	public abstract Gauge<Integer> getReconnectionSchedulerQueueSize();

	public abstract Gauge<Integer> getTaskSchedulerQueueSize();

}