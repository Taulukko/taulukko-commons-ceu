package com.taulukko.ceu.data;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

public interface ExecutionInfo {

	public abstract int hashCode();

	public abstract boolean equals(Object obj);

	public abstract List<Host> getTriedHosts();

	public abstract Host getQueriedHost();

	public abstract byte[] getPagingStateUnsafe();

	public abstract boolean isSchemaInAgreement();

	public abstract String toString();

	public abstract List<String> getWarnings();

	public abstract Map<String, ByteBuffer> getIncomingPayload();

}