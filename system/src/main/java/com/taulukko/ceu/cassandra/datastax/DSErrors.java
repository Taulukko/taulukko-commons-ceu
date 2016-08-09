package com.taulukko.ceu.cassandra.datastax;

import com.codahale.metrics.Counter;
import com.taulukko.ceu.data.Errors;

public class DSErrors implements Errors {
	private com.datastax.driver.core.Metrics.Errors coreErrors = null;

	public int hashCode() {
		return coreErrors.hashCode();
	}

	public boolean equals(Object obj) {
		return coreErrors.equals(obj);
	}

	public String toString() {
		return coreErrors.toString();
	}

	public Counter getConnectionErrors() {
		return coreErrors.getConnectionErrors();
	}

	public Counter getWriteTimeouts() {
		return coreErrors.getWriteTimeouts();
	}

	public Counter getReadTimeouts() {
		return coreErrors.getReadTimeouts();
	}

	public Counter getUnavailables() {
		return coreErrors.getUnavailables();
	}

	public Counter getClientTimeouts() {
		return coreErrors.getClientTimeouts();
	}

	public Counter getOthers() {
		return coreErrors.getOthers();
	}

	public Counter getRetries() {
		return coreErrors.getRetries();
	}

	public Counter getRetriesOnReadTimeout() {
		return coreErrors.getRetriesOnReadTimeout();
	}

	public Counter getRetriesOnWriteTimeout() {
		return coreErrors.getRetriesOnWriteTimeout();
	}

	public Counter getRetriesOnUnavailable() {
		return coreErrors.getRetriesOnUnavailable();
	}

	public Counter getRetriesOnClientTimeout() {
		return coreErrors.getRetriesOnClientTimeout();
	}

	public Counter getRetriesOnConnectionError() {
		return coreErrors.getRetriesOnConnectionError();
	}

	public Counter getRetriesOnOtherErrors() {
		return coreErrors.getRetriesOnOtherErrors();
	}

	public Counter getIgnores() {
		return coreErrors.getIgnores();
	}

	public Counter getIgnoresOnReadTimeout() {
		return coreErrors.getIgnoresOnReadTimeout();
	}

	public Counter getIgnoresOnWriteTimeout() {
		return coreErrors.getIgnoresOnWriteTimeout();
	}

	public Counter getIgnoresOnUnavailable() {
		return coreErrors.getIgnoresOnUnavailable();
	}

	public Counter getIgnoresOnClientTimeout() {
		return coreErrors.getIgnoresOnClientTimeout();
	}

	public Counter getIgnoresOnConnectionError() {
		return coreErrors.getIgnoresOnConnectionError();
	}

	public Counter getIgnoresOnOtherErrors() {
		return coreErrors.getIgnoresOnOtherErrors();
	}

	public Counter getSpeculativeExecutions() {
		return coreErrors.getSpeculativeExecutions();
	}

	public DSErrors(com.datastax.driver.core.Metrics.Errors errors) {
		this.coreErrors = errors;
	}

	public com.datastax.driver.core.Metrics.Errors getCoreErrors() {
		return coreErrors;
	}

}
