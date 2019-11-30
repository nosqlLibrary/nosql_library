package com.library.www.po;

public class BorrowDetail {
	private Integer id;
	private Integer bid;
	private String bname;
	private String username;
	private Integer userid;
	private String takeDate;
	private String backDate;
	public Integer getId() {
		return id;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getTakeDate() {
		return takeDate;
	}
	public void setTakeDate(String takeDate) {
		this.takeDate = takeDate;
	}
	public String getBackDate() {
		return backDate;
	}
	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}
	@Override
	public String toString() {
		return "BorrowDetail [id=" + id + ", bid=" + bid + ", bname=" + bname + ", username=" + username + ", userid="
				+ userid + ", takeDate=" + takeDate + ", backDate=" + backDate + "]";
	}
	
	
}
