package com.library.www.service;

import java.util.ArrayList;

import com.library.www.dao.BookDao;
import com.library.www.po.Book;
import com.library.www.po.BookType;
import com.library.www.po.UniqueBook;

public class BookService {
	private BookDao bd = new BookDao();
	
	
	public ArrayList<Book> getAllBooks() throws Exception{
		
		ArrayList<Book> allBooks = bd.getAllBooks();
		
		return allBooks;
		
	}
	
	public ArrayList<UniqueBook> getSomeSingleBooks(int page) throws Exception{
		
		
		ArrayList<UniqueBook> uniqueBooks = bd.getSomeSingleBook(page);
		
		return uniqueBooks;
		
	}
	
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getBooksByCondition(String condition ,int page) throws Exception {
		return bd.selectBookByCondition(condition,page);
	}
	
	public ArrayList<String> getBookTypeNames() throws Exception{
		return bd.getBookTypeNames();
	}
	
	
	public boolean addNewBook(Book book) throws Exception {
		return bd.addNewBook(book);
	}
	
	public boolean addUniqueBook(UniqueBook uniquebook) throws Exception {
		return bd.addUniqueBook(uniquebook);
	}

	
	@SuppressWarnings("rawtypes")
	public ArrayList getBookDetails(String bid) throws Exception {
		
		return bd.getBookDetails(bid);
	}

	@SuppressWarnings("rawtypes")
	public boolean deleteBook(String bid) throws Exception {
		ArrayList bookDetails = bd.getBookDetails(bid);
		Book book = (Book) bookDetails.get(1);
		boolean result = true;
		//判断该本图书是不是该本图书最后一本书
		if(book.getNum()==1) {
			result = bd.deletBook(book.getIsbn());
		}
		
		return bd.deleteUniqueBook(bid) && result;
		
	}

	public boolean changeExistBookNum(Book book, int addNum) throws Exception{
		book.setNum(book.getNum()+addNum);
		return bd.changeExistBookNum(book);
		
	}

	public boolean editBook(Book book) throws Exception {
		
		return bd.editBook(book);
	}

	@SuppressWarnings("rawtypes")
	public boolean checkBookStatus(String bid,char status) throws Exception {
		// 判断图书状态是否与要检查的状态一致
		ArrayList bookDetails = bd.getBookDetails(bid);
		if(bookDetails.size()!=0) {
			UniqueBook book = (UniqueBook)bookDetails.get(0);
			if(book.getStatus() == status)
				return true;
		}
			
		return false;
	}

	/**
	 * 图书损毁登记
	 * @param bid
	 * @return
	 * @throws Exception
	 */
	public boolean bookDamageRegister(String bid) throws Exception {
		boolean result = false;
//		boolean flag = checkBookStatus(bid, 'B');//先检查是否不在库，不在库时会影响到是否有借阅信息还没有归还日期
		result = bd.changeBookStatus(bid, 'C');
		
		return result;
		
	}

	/**
	 * 图书丢失登记
	 * @param bid
	 * @return
	 * @throws Exception
	 */
	public boolean bookLostRegister(String bid) throws Exception {
		
		boolean result = false;
		result = bd.changeBookStatus(bid, 'D');
		return result;
	}

	public BookType getBookTypeNameById(char btid) throws Exception {
		
		return bd.getBookTypeNameById(btid);
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getAllDetailBooks() throws Exception {
		
		return bd.getAllDetailBooks();
	}
	
	
	
}
