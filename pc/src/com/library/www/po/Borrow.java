package com.library.www.po;

import java.util.Date;

public class Borrow {
    private String id;

    private String userid;

    private String bid;

    private Date takedate;

    private Date backdate;
    
    private Date deadline;

	public Borrow() {
		
	}

	public Borrow(String id, String userid, String bid, Date takedate,  Date deadline,Date backdate) {
		super();
		this.id = id;
		this.userid = userid;
		this.bid = bid;
		this.takedate = takedate;
		this.backdate = backdate;
		this.deadline = deadline;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public Date getTakedate() {
		return takedate;
	}

	public void setTakedate(Date takedate) {
		this.takedate = takedate;
	}

	public Date getBackdate() {
		return backdate;
	}

	public void setBackdate(Date backdate) {
		this.backdate = backdate;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		return "Borrow [id=" + id + ", userid=" + userid + ", bid=" + bid + ", takedate=" + takedate + ", backdate="
				+ backdate + ", deadline=" + deadline + "]";
	}

	
    
}