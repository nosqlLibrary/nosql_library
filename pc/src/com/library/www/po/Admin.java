package com.library.www.po;

public class Admin {
	
	private String id;
	
	private String adminName;
	
	private String password;

	public Admin() {
		
	}

	public Admin(String id, String adminName, String password) {
		super();
		this.id = id;
		this.adminName = adminName;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", adminName=" + adminName + ", password=" + password + "]";
	}
	
	
}
