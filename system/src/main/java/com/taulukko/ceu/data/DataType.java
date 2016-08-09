package com.taulukko.ceu.data;

import java.util.List;

import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.exceptions.DriverInternalError;

public interface DataType {

	public abstract int hashCode();

	public abstract boolean equals(Object obj);

	public abstract String toString();

	public abstract Name getName();

	public abstract boolean isFrozen();

	public abstract boolean isCollection();

	public abstract List<DataType> getTypeArguments();

	public abstract String asFunctionParameterString();
	
	  /**
     * The CQL type name.
     */
    public enum Name {

        ASCII(1),
        BIGINT(2),
        BLOB(3),
        BOOLEAN(4),
        COUNTER(5),
        DECIMAL(6),
        DOUBLE(7),
        FLOAT(8),
        INET(16),
        INT(9),
        TEXT(10) {
            @Override
            public boolean isCompatibleWith(Name that) {
                return this == that || that == VARCHAR;
            }
        },
        TIMESTAMP(11),
        UUID(12),
        VARCHAR(13) {
            @Override
            public boolean isCompatibleWith(Name that) {
                return this == that || that == TEXT;
            }
        },
        VARINT(14),
        TIMEUUID(15),
        LIST(32),
        SET(34),
        MAP(33),
        CUSTOM(0),
        UDT(48, ProtocolVersion.V3),
        TUPLE(49, ProtocolVersion.V3),
        SMALLINT(19, ProtocolVersion.V4),
        TINYINT(20, ProtocolVersion.V4),
        DATE(17, ProtocolVersion.V4),
        TIME(18, ProtocolVersion.V4);

        final int protocolId;

        final ProtocolVersion minProtocolVersion;

        private static final Name[] nameToIds;

        static {
            int maxCode = -1;
            for (Name name : Name.values())
                maxCode = Math.max(maxCode, name.protocolId);
            nameToIds = new Name[maxCode + 1];
            for (Name name : Name.values()) {
                if (nameToIds[name.protocolId] != null)
                    throw new IllegalStateException("Duplicate Id");
                nameToIds[name.protocolId] = name;
            }
        }

        private Name(int protocolId) {
            this(protocolId, ProtocolVersion.V1);
        }

        private Name(int protocolId, ProtocolVersion minProtocolVersion) {
            this.protocolId = protocolId;
            this.minProtocolVersion = minProtocolVersion;
        }

        static Name fromProtocolId(int id) {
            Name name = nameToIds[id];
            if (name == null)
                throw new DriverInternalError("Unknown data type protocol id: " + id);
            return name;
        }

        /**
         * Return {@code true} if the provided Name is equal to this one,
         * or if they are aliases for each other, and {@code false} otherwise.
         *
         * @param that the Name to compare with the current one.
         * @return {@code true} if the provided Name is equal to this one,
         * or if they are aliases for each other, and {@code false} otherwise.
         */
        public boolean isCompatibleWith(Name that) {
            return this == that;
        }

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

}