 
 
package com.taulukko.cassandra;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

public class MainWrongBean {
	private String name = null;
	private String email = null;
	private Map<String, BigInteger> friendsByName;
	private Date lastAccess = null;
	private Float version = null;

	public Float getVersion() {
		return version;
	}

	public void setVersion(Float version) {
		this.version = version;
	}

	private int oldId = 0;

	private int oldid = 0;

	public int getOldid() {
		return oldid;
	}

	public void setOldid(int oldid) {
		this.oldid = oldid;
	}
	public int getOldId() {
		return oldId;
	}

	public void setOldId(int userId) {
		this.oldId = userId;
	}
	public String getName() {
		return name;
	}

	public Map<String, BigInteger> getFriendsByName() {
		return friendsByName;
	}

	public void setFriendsByName(Map<String, BigInteger> friendsbyname) {
		this.friendsByName = friendsbyname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}  