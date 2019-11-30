package com.library.www.po;

import java.util.Date;

public class User {
    private String userid;

    private String username;

    private String password;

    private char gender;

    private int type;
    
    private String department;
    
    private long tel;
    
    private String email;
    
    private Date registerDate;
    
    private char status;

    
    
	public User() {
		
	}



	public User(String userid, String username, String password, char gender, int type, String department,
			long tel, String email, Date registerDate, char status) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.type = type;
		this.department = department;
		this.tel = tel;
		this.email = email;
		this.registerDate = registerDate;
		this.status = status;
	}



	public String getUserid() {
		return userid;
	}



	public void setUserid(String userid) {
		this.userid = userid;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public char getGender() {
		return gender;
	}



	public void setGender(char gender) {
		this.gender = gender;
	}



	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}



	public String getDepartment() {
		return department;
	}



	public void setDepartment(String department) {
		this.department = department;
	}



	public long getTel() {
		return tel;
	}



	public void setTel(long tel) {
		this.tel = tel;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Date getRegisterDate() {
		return registerDate;
	}



	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}



	public char getStatus() {
		return status;
	}



	public void setStatus(char status) {
		this.status = status;
	}



	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", password=" + password + ", gender=" + gender
				+ ", type=" + type + ", department=" + department + ", tel=" + tel + ", email=" + email
				+ ", registerDate=" + registerDate + ", status=" + status + "]";
	}
	
	

    
    
}