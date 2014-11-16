package br.com.evon.cassandra;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public class AccountTestBean extends MainBean {

	

	private Integer age = null;

	private Set<BigInteger> cmps;

	public Set<BigInteger> getCmps() {
		return cmps;
	}

	public void setCmps(Set<BigInteger> cmps) {
		this.cmps = cmps;
	}
	private List<String> tags;
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}


}
