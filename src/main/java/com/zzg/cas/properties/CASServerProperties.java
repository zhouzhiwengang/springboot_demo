package com.zzg.cas.properties;

public class CASServerProperties {
	
	private String prefix;
	
	private String login;

	private String logout;
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogout() {
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}

	public CASServerProperties() {
		super();
	}
	
	
}
