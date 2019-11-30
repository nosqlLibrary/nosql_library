package com.library.www.po;

public class UniqueBook {
	
	private String bid;
	
	private String isbn;
	
	private char status;

	public UniqueBook() {
		
	}

	public UniqueBook(String bid, String isbn, char status) {
		super();
		this.bid = bid;
		this.isbn = isbn;
		this.status = status;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UniqueBook [bid=" + bid + ", isbn=" + isbn + ", status=" + status + "]";
	}
	
	
	
	
}
