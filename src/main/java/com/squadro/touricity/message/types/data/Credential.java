package com.squadro.touricity.message.types.data;

public class Credential {

	private String user_name;
	private String password;

	public Credential() {
		// nothing
	}

	public Credential(String user_name, String password) {
		this.user_name = user_name;
		this.password = password;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name() {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword() {
		this.password = password;
	}
}