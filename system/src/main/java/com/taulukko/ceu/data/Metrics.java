package com.taulukko.ceu.data;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.taulukko.ceu.CEUException;

public interface Metrics {

	public abstract com.datastax.driver.core.Metrics getCoreMetrics();

	public abstract MetricRegistry getRegistry() throws CEUException;

	public abstract Timer getRequestsTimer() throws CEUException;

	public abstract Errors getErrorMetrics() throws CEUException;

	public abstract Gauge<Integer> getKnownHosts() throws CEUException;

	public abstract Gauge<Integer> getConnectedToHosts() throws CEUException;

	public abstract Gauge<Integer> getOpenConnections() throws CEUException;

	public abstract Gauge<Integer> getTrashedConnections() throws CEUException;

	public abstract Gauge<Integer> getExecutorQueueDepth() throws CEUException;

	public abstract Gauge<Integer> getBlockingExecutorQueueDepth()
			throws CEUException;

	public abstract Gauge<Integer> getReconnectionSchedulerQueueSize()
			throws CEUException;

	public abstract Gauge<Integer> getTaskSchedulerQueueSize()
			throws CEUException;

}