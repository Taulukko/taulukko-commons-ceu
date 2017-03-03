/*
 *     
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.taulukko.ceu.cassandra.datastax;

import com.taulukko.ceu.data.DataType;
import com.taulukko.ceu.data.TupleType;
import com.taulukko.ceu.data.TupleValue;

/**
 * A tuple type.
 *  
 * A tuple type is a essentially a list of types.
 */
public class DSTupleType extends DSDataType<com.datastax.driver.core.TupleType>
		implements TupleType {

	public DSTupleType(com.datastax.driver.core.TupleType coreTupleType) {
		super(coreTupleType);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleType#newValue(java.lang.Object)
	 */
	@Override
	public TupleValue newValue(Object... values) {
		return new DSTupleValue(this.getCoreDataType().newValue(values));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleType#isFrozen()
	 */

	@Override
	public boolean isFrozen() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleType#hashCode()
	 */

	@Override
	public int hashCode() {
		return this.getCoreDataType().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleType#equals(java.lang.Object)
	 */

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof DSTupleType))
			return false;

		DSTupleType d = (DSTupleType) o;
		return this.getCoreDataType().equals(d.getCoreDataType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.TupleType#contains(com.taulukko.cassandra
	 * .datastax.DSTupleType)
	 */
	@Override
	public boolean contains(DSTupleType other) {

		return this.getCoreDataType().contains(other.getCoreDataType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.taulukko.cassandra.datastax.TupleType#toString()
	 */

	@Override
	public String toString() {
		return "frozen<" + asFunctionParameterString() + ">";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.taulukko.cassandra.datastax.TupleType#asFunctionParameterString()
	 */

	@Override
	public String asFunctionParameterString() {
		StringBuilder sb = new StringBuilder();
		for (DataType type : this.getTypeArguments()) {
			sb.append(sb.length() == 0 ? "tuple<" : ", ");
			sb.append(type);
		}
		return sb.append(">").toString();
	}
}
