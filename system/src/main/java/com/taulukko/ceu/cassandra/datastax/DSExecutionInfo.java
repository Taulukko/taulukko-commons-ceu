package com.taulukko.ceu.cassandra.datastax;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.taulukko.ceu.data.ExecutionInfo;
import com.taulukko.ceu.data.Host;

public class DSExecutionInfo implements ExecutionInfo {
	private com.datastax.driver.core.ExecutionInfo coreExecutionInfo = null;

	/* (non-Javadoc)
	 * @see com.taulukko.ceu.cassandra.datastax.ExecutionInfo#hashCode()
	 */
	@Override
	public int hashCode() {
		return coreExecutionInfo.hashCode();
	}

	/* (non-Javadoc)
	 * @see com.taulukko.ceu.cassandra.datastax.ExecutionInfo#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return coreExecutionInfo.equals(obj);
	}

	/* (non-Javadoc)
	 * @see com.taulukko.ceu.cassandra.datastax.ExecutionInfo#getTriedHosts()
	 */
	@Override
	public List<Host> getTriedHosts() {
		return coreExecutionInfo.getTriedHosts().stream()
				.map(h -> new DSHost(h)).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see com.taulukko.ceu.cassandra.datastax.ExecutionInfo#getQueriedHost()
	 */
	@Override
	public Host getQueriedHost() {
		return new DSHost(coreExecutionInfo.getQueriedHost());
	}

	/* (non-Javadoc)
	 * @see com.taulukko.ceu.cassandra.datastax.ExecutionInfo#getPagingStateUnsafe()
	 */
	@Override
	public byte[] getPagingStateUnsafe() {
		return coreExecutionInfo.getPagingStateUnsafe();
	}

	/* (non-Javadoc)
	 * @see com.taulukko.ceu.cassandra.datastax.ExecutionInfo#isSchemaInAgreement()
	 */
	@Override
	public boolean isSchemaInAgreement() {
		return coreExecutionInfo.isSchemaInAgreement();
	}

	/* (non-Javadoc)
	 * @see com.taulukko.ceu.cassandra.datastax.ExecutionInfo#toString()
	 */
	@Override
	public String toString() {
		return coreExecutionInfo.toString();
	}

	/* (non-Javadoc)
	 * @see com.taulukko.ceu.cassandra.datastax.ExecutionInfo#getWarnings()
	 */
	@Override
	public List<String> getWarnings() {
		return coreExecutionInfo.getWarnings();
	}

	/* (non-Javadoc)
	 * @see com.taulukko.ceu.cassandra.datastax.ExecutionInfo#getIncomingPayload()
	 */
	@Override
	public Map<String, ByteBuffer> getIncomingPayload() {
		return coreExecutionInfo.getIncomingPayload();
	}

	public DSExecutionInfo(com.datastax.driver.core.ExecutionInfo executionInfo) {
		this.coreExecutionInfo = executionInfo;
	}

}
