package com.taulukko.cassandra;

import java.util.Date;
import java.util.Map;

public class MainBean {
	private String name = null;
	private String email = null;
	private Map<String, Integer> friendsByName;
	private Map<String, Integer> friendsbyname;
	private Date lastAccess = null;

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

	public Map<String, Integer> getFriendsByName() {
		return friendsByName;
	}

	public void setFriendsByName(Map<String, Integer> friendsbyname) {
		this.friendsByName = friendsbyname;
	}

	// to insensetive tests propous
	public Map<String, Integer> getFriendsbyname() {
		return friendsbyname;
	}

	public void setFriendsbyname(Map<String, Integer> friendsbyname) {
		this.friendsbyname = friendsbyname;
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
