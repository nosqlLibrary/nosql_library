package com.library.www.po;

import java.util.Date;

/**
 * @author 11247
 *
 */
/**
 * @author 11247
 *
 */
public class Book {
   

	private String isbn;
	
    private String bname;
    
    private String bproduction;
    
    private String bauthor;
    
    private String publishDate; 
    
    private String price;
    
    private char btype; 
    
    private int pageNum;
    
    private Date registerDate;
    
    private int num ; 
    
    private String introduction;
    
    
    public Book() {
		super();
	}

	

	
	

	public Book(String isbn, String bname, String bproduction, String bauthor, String publishDate, String price,
			char btype, int pageNum, Date registerDate, int num, String introduction) {
		super();
		this.isbn = isbn;
		this.bname = bname;
		this.bproduction = bproduction;
		this.bauthor = bauthor;
		this.publishDate = publishDate;
		this.price = price;
		this.btype = btype;
		this.pageNum = pageNum;
		this.registerDate = registerDate;
		this.num = num;
		this.introduction = introduction;
	}


	



	public String getIntroduction() {
		return introduction;
	}






	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}






	public int getNum() {
		return num;
	}



	public void setNum(int num) {
		this.num = num;
	}

	

	public int getPageNum() {
		return pageNum;
	}



	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}



	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getBproduction() {
		return bproduction;
	}

	public void setBproduction(String bproduction) {
		this.bproduction = bproduction;
	}

	public String getBauthor() {
		return bauthor;
	}

	public void setBauthor(String bauthor) {
		this.bauthor = bauthor;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public char getBtype() {
		return btype;
	}

	public void setBtype(char btype) {
		this.btype = btype;
	}

	

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}


	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", bname=" + bname + ", bproduction=" + bproduction + ", bauthor=" + bauthor
				+ ", publishDate=" + publishDate + ", price=" + price + ", btype=" + btype + ", pageNum=" + pageNum
				+ ", registerDate=" + registerDate + ", num=" + num + ", introduction=" + introduction + "]";
	}



}