package com.library.www.po;

public class BookType {
	
	private char btId;
	
	private String typename;

	
	
	public BookType() {
		
	}


	public BookType(char btId, String typename) {
		super();
		this.btId = btId;
		this.typename = typename;
	}


	public char getBtId() {
		return btId;
	}


	public void setBtId(char btId) {
		this.btId = btId;
	}


	public String getTypename() {
		return typename;
	}


	public void setTypename(String typename) {
		this.typename = typename;
	}


	@Override
	public String toString() {
		return "BookType [btId=" + btId + ", typename=" + typename + "]";
	}
	
	
	
}
