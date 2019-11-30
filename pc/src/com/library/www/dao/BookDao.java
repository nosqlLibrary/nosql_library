package com.library.www.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.library.www.po.Book;
import com.library.www.po.BookType;
import com.library.www.po.UniqueBook;
import com.library.www.util.DbUtil;

public class BookDao {
	
	
	private ResultSet rs = null;
	private Connection conn = null;
	private static DbUtil db = new DbUtil();
	private PreparedStatement preState = null;
    
	/**
	 * 从数据库中获取所有的书数据
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public ArrayList<Book> getAllBooks() throws Exception{
		ArrayList<Book> books = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//定义sql语句
			String sql = "select * from book_info ";
			preState = conn.prepareStatement(sql);
			
			//执行结果集
			rs=preState.executeQuery();
			while(rs.next()) {
				Book book = new Book();
				String isbn = rs.getString("isbn");
				String bookName = rs.getString("b_na");
				char type = rs.getString("b_type").charAt(0);
				String author = rs.getString("b_aut");
				String bproduction = rs.getString("press");
				String publishDate = rs.getString("pdate");
				String price = rs.getString("price");
				String introduction = rs.getString("b_intro");
				int num = rs.getInt("b_num");
				int pageNum = rs.getInt("b_pnum");
				Date registerDate = rs.getDate("b_date");
				
				book.setIsbn(isbn);
				book.setBname(bookName);
				book.setBtype(type);
				book.setBauthor(author);
				book.setBproduction(bproduction);
				book.setPublishDate(publishDate);
				book.setPrice(price);
				book.setNum(num);
				book.setPageNum(pageNum);
				book.setRegisterDate(registerDate);
				book.setIntroduction(introduction);
				
				books.add(book);
				
			}
			
		} catch (Exception e) {
			System.out.println("获取图书详细信息失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
			
		}
		return books;
	}
	/**
	 * 根据页数返回书本的内容
	 * 获取具体的某一本书：每本图书图书馆库存可能不唯一，这时有多个bid对应同一个isbn码
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public ArrayList<UniqueBook> getSomeSingleBook(int page) throws Exception{
		ArrayList<UniqueBook> uBooks = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//定义sql语句
			String sql = "select  * from book limit ?,20";
			preState = conn.prepareStatement(sql);
			preState.setInt(1, (page-1)*20 );
			//执行结果集
			rs=preState.executeQuery();
			while(rs.next()) {
				UniqueBook uniqueBook = new UniqueBook();
				String isbn = rs.getString("isbn");
				String bid = rs.getString("b_id");
				char status = rs.getString("b_status").charAt(0);
				//获取具体书本的信息
				uniqueBook.setIsbn(isbn);
				uniqueBook.setBid(bid);
				uniqueBook.setStatus(status);
				
				uBooks.add(uniqueBook);
				
			}
			
		} catch (Exception e) {
			System.out.println("获取图书失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		
		
		return uBooks;
	}
	
	
	
	/**
	 * 根据用户输入的条件来获取图书信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> selectBookByCondition(String condition,int page) throws Exception{
		
		ArrayList<ArrayList> conditionBooks  = new ArrayList<>();
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<UniqueBook> uBooks = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//定义sql语句   书名/作者/ISBN/图书编号/出版社
			String sql = "SELECT * from book_info,book where (book.isbn like ? or b_na like ? or b_aut like ?"
					+ " or press like ? or b_id like ?) and book.isbn = book_info.isbn limit ?,10";
			preState = conn.prepareStatement(sql);
			preState.setString(1,"%" + condition + "%");
			preState.setString(2,"%" + condition + "%");
			preState.setString(3,"%" + condition + "%");
			preState.setString(4,"%" + condition + "%");
			preState.setString(5,"%" + condition + "%");
			preState.setInt(6, (page - 1) *20);
			//执行结果集
			rs=preState.executeQuery();
			while(rs.next()) {
				UniqueBook uniqueBook = new UniqueBook();
				
				String isbn = rs.getString("isbn");
				String bid = rs.getString("b_id");
				char status = rs.getString("b_status").charAt(0);
				//获取具体某一本书
				uniqueBook.setIsbn(isbn);
				uniqueBook.setBid(bid);
				uniqueBook.setStatus(status);
				
				uBooks.add(uniqueBook);
				
				//获取某本书的详细信息
				Book book = new Book();
				String bookName = rs.getString("b_na");
				char type = rs.getString("b_type").charAt(0);
				String author = rs.getString("b_aut");
				String bproduction = rs.getString("press");
				String publishDate = rs.getString("pdate");
				String price = rs.getString("price");
				int num = rs.getInt("b_num");
				int pageNum = rs.getInt("b_pnum");
				String introduction = rs.getString("b_intro");
				Date registerDate = rs.getDate("b_date");
				
				book.setIsbn(isbn);
				book.setBname(bookName);
				book.setBtype(type);
				book.setBauthor(author);
				book.setBproduction(bproduction);
				book.setPublishDate(publishDate);
				book.setPrice(price);
				book.setNum(num);
				book.setPageNum(pageNum);
				book.setRegisterDate(registerDate);
				book.setIntroduction(introduction);
				
				books.add(book);
				
			}
			
		} catch (Exception e) {
			System.out.println("根据条件获取图书失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		
		conditionBooks.add(uBooks);
		conditionBooks.add(books);
		
		return conditionBooks;
		
	}
	
	/**
	 * 获取数据库中所有的图书类别  ----以"编号：类别名"的格式存放在动态数组中返回
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getBookTypeNames() throws Exception{
		ArrayList<String> typeNames = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//定义sql语句
			String sql = "select * from booktype";
			preState = conn.prepareStatement(sql);
			//执行结果集
			rs = preState.executeQuery();
			while(rs.next()) {
				
				String type = rs.getString("bt_name");
				char id = rs.getString("bt_id").charAt(0);
				//拼接字符串
				typeNames.add(id+":"+type);
				
			}
			
		} catch (Exception e) {
			System.out.println("获取图书类别失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		
		return typeNames;
	}
	
	/**
	 * 添加某本图书的具体信息--插入到数据库中
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public boolean addNewBook(Book book) throws Exception {
		boolean result = false;
		conn = db.getCon();
		
		try {
			conn.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			
			Date date = new Date(System.currentTimeMillis());
			//定义sql语句
			String sql = "insert into book_info values (?,?,?,?,?,?,?,?,?,?,?)";
			preState = conn.prepareStatement(sql);
			preState.setString(1, book.getIsbn());
			preState.setString(2, book.getBname());
			preState.setString(3, Character.toString(book.getBtype()));
			preState.setString(4, book.getBauthor());
			
			preState.setString(5, book.getBproduction());
			preState.setString(6, book.getPublishDate());
			preState.setString(7, book.getPrice());
			preState.setInt(8, book.getPageNum());
			preState.setDate(9, date);
			preState.setInt(10, book.getNum());
			preState.setString(11, book.getIntroduction());
			//System.out.println(preState);
			//执行结果集
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//提交JDBC事务
			conn.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
		} catch (Exception e) {
			conn.rollback();//回滚JDBC事务
			System.out.println("插入图书失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
	}

	/**
	 * 添加具体的某一本书 -- 插入到数据库中
	 * @param uniquebook
	 * @return
	 * @throws Exception
	 */
	public boolean addUniqueBook(UniqueBook uniquebook) throws Exception {
		boolean result = false;
		conn = db.getCon();
		
		try {
			conn.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			
			//定义sql语句
			String sql = "insert into book values(?,?,?)";
			preState = conn.prepareStatement(sql);
			preState.setString(1, uniquebook.getBid());
			preState.setString(2, uniquebook.getIsbn());
			preState.setString(3, Character.toString(uniquebook.getStatus()));
			//执行结果集
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//提交JDBC事务
			conn.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
		} catch (Exception e) {
			conn.rollback();//回滚JDBC事务
			System.out.println("插入uniquebook失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
		
	}

	/**
	 * 获取某本书的具体信息---从数据库中
	 * @param bid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getBookDetails(String bid) throws Exception {
		ArrayList arr = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//定义sql语句
			String sql = "select * from book,book_info where b_id = ? and book.isbn = book_info.isbn";
			
			preState = conn.prepareStatement(sql);
			preState.setString(1, bid);
			//执行结果集
			rs=preState.executeQuery();
			while(rs.next()) {
				UniqueBook uniqueBook = new UniqueBook();
				
				String isbn = rs.getString("isbn");
				
				char status = rs.getString("b_status").charAt(0);
				//获取具体某一本书
				uniqueBook.setIsbn(isbn);
				uniqueBook.setBid(bid);
				uniqueBook.setStatus(status);
				
				arr.add(uniqueBook);
				
				//获取某本书的详细信息
				Book book = new Book();
				String bookName = rs.getString("b_na");
				char type = rs.getString("b_type").charAt(0);
				String author = rs.getString("b_aut");
				String bproduction = rs.getString("press");
				String publishDate = rs.getString("pdate");
				String price = rs.getString("price");
				int num = rs.getInt("b_num");
				int pageNum = rs.getInt("b_pnum");
				Date registerDate = rs.getDate("b_date");
				String introduction = rs.getString("b_intro");
				
				book.setIsbn(isbn);
				book.setBname(bookName);
				book.setBtype(type);
				book.setBauthor(author);
				book.setBproduction(bproduction);
				book.setPublishDate(publishDate);
				book.setPrice(price);
				book.setNum(num);
				book.setPageNum(pageNum);
				book.setRegisterDate(registerDate);
				book.setIntroduction(introduction);
				
				arr.add(book);
				
			}
			
		} catch (Exception e) {
			System.out.println("获取图书详细信息失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return arr;
	}

	/**
	 * 给定图书编号，删除数据库中的该本图书，并修改对应的详细信息表的库存字段
	 * 
	 * @param bid
	 * @return
	 * @throws Exception
	 */

	public boolean deleteUniqueBook(String bid) throws Exception {
		boolean result = false;
		conn = db.getCon();
		
		try {
			conn.setAutoCommit(false);// 更改JDBC事务的默认提交方式
		
			
			String sql2 = "select b_num,isbn from book_info where isbn = (select isbn from book where b_id = ?)";
			preState = conn.prepareStatement(sql2);
			preState.setString(1, bid);
			int bnum = 0;//记录库存信息
			String isbn ="";//记录isbn
			rs=preState.executeQuery();
			while(rs.next()) {
				bnum = rs.getInt("b_num");
				isbn = rs.getString("isbn");
			}
			
			
			//定义sql语句
			String sql = "delete from book where b_id = ?";
			preState = conn.prepareStatement(sql);
			preState.setString(1, bid);
			//执行结果集
			int re = preState.executeUpdate();
			
			if(re!=0) {
				bnum--;//当前库存减一
			}
			
			String sql3 = "update book_info set b_num= ? where isbn = ? ";
			preState = conn.prepareStatement(sql3);
			preState.setInt(1, bnum);
			preState.setString(2, isbn);
			
			int re2 = preState.executeUpdate();
			
			if(re != 0 && re2 != 0) {
				result = true;
			}

			conn.commit();//提交JDBC事务
			conn.setAutoCommit(true);// 恢复JDBC事务的默认提交方式	
			
		} catch (Exception e) {
			conn.rollback();//回滚JDBC事务
			System.out.println("删除图书失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
		
	}

	/**
	 * 删除某本图书的具体信息  ----与数据库交互 
	 * @param isbn
	 * @return
	 * @throws Exception
	 */
	public boolean deletBook(String isbn) throws Exception {
		boolean result = false;
		conn = db.getCon();
		
		try {
			conn.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			
			//定义sql语句
			String sql = "delete from book_info where isbn = ? ";
			preState = conn.prepareStatement(sql);
			preState.setString(1, isbn);
			//执行结果集
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//提交JDBC事务
			conn.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
		} catch (Exception e) {
			conn.rollback();//回滚JDBC事务
			System.out.println("删除图书具体信息失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
		
	}

	/**
	 * 用户添加已有图书，向数据库添加已有图书的具体图书信息后，修改该图书的库存数量
	 * @param book ：由Service类修改book中的num值之后传到dao类中
	 * @return
	 * @throws Exception
	 */
	public boolean changeExistBookNum (Book book) throws Exception {
		boolean result = false;
		conn = db.getCon();
		
		try {
			conn.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			
			//定义sql语句
			String sql = "update book_info set b_num = ? where isbn = ? ";
			preState = conn.prepareStatement(sql);
			preState.setInt(1, book.getNum());
			preState.setString(2, book.getIsbn());
			//执行结果集
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//提交JDBC事务
			conn.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
		} catch (Exception e) {
			conn.rollback();//回滚JDBC事务
			System.out.println("修改图书库存失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
	}

	/**
	 * 修改用户输入图书信息 ---数据库交互
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public boolean editBook(Book book) throws Exception {
		
		boolean result = false;
		conn = db.getCon();
		try {
			conn.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			
			//定义sql语句
			String sql = "update book_info set b_na =? , b_type= ? , b_aut= ?, press=?, pdate=?"
					+ ", price= ?, b_pnum=?,b_intro = ?  where isbn = ?";
			preState = conn.prepareStatement(sql);
			preState.setString(1, book.getBname());
			preState.setString(2,Character.toString(book.getBtype()));
			preState.setString(3, book.getBauthor());
			preState.setString(4, book.getBproduction());
			preState.setString(5,book.getPublishDate());
			preState.setString(6, book.getPrice());
			preState.setInt(7, book.getPageNum());
			preState.setString(8, book.getIntroduction());
			preState.setString(9, book.getIsbn());
			//执行结果集
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//提交JDBC事务
			conn.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
		} catch (Exception e) {
			conn.rollback();//回滚JDBC事务
			System.out.println("修改图书信息失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
	}

	/**
	 *  修改图书的状态  从数据库中
	 * @param bid
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public boolean changeBookStatus(String bid,char status) throws Exception {
		boolean result = false;
		conn = db.getCon();
		try {
			conn.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			
			//定义sql语句
			String sql = "update book set b_status = ? where b_id = ?";
			preState = conn.prepareStatement(sql);
			preState.setString(1, Character.toString(status));
			preState.setString(2, bid);
			//执行结果集
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//提交JDBC事务
			conn.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
		} catch (Exception e) {
			conn.rollback();//回滚JDBC事务
			System.out.println("修改图书状态失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
		
	}

	/**
	 * 根据图书类型编号得到类型的具体内容
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BookType getBookTypeNameById(char id) throws Exception {
		
		BookType bookType = new BookType();
		
		try {
			conn = db.getCon();
			
			//定义sql语句
			String sql = "select * from booktype where bt_id = ? ";
			preState = conn.prepareStatement(sql);
			preState.setString(1, Character.toString(id));
			//执行结果集
			rs = preState.executeQuery();
			while(rs.next()) {
				char btid = rs.getString("bt_id").charAt(0);
				String typename = rs.getString("bt_name");
				
				bookType.setBtId(btid);
				bookType.setTypename(typename);
			}
			
		} catch (Exception e) {
			System.out.println("修改图书状态失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return bookType;
	}
	
	/**
	 * 获取数据库中所有的图书
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getAllDetailBooks() throws Exception {
		ArrayList<ArrayList> allBooks  = new ArrayList<>();
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<UniqueBook> uBooks = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//定义sql语句   书名/作者/ISBN/图书编号/出版社
			String sql = "SELECT * from book_info,book where book.isbn = book_info.isbn ";
			preState = conn.prepareStatement(sql);
			
			//执行结果集
			rs=preState.executeQuery();
			while(rs.next()) {
				UniqueBook uniqueBook = new UniqueBook();
				
				String isbn = rs.getString("isbn");
				String bid = rs.getString("b_id");
				char status = rs.getString("b_status").charAt(0);
				//获取具体某一本书
				uniqueBook.setIsbn(isbn);
				uniqueBook.setBid(bid);
				uniqueBook.setStatus(status);
				
				uBooks.add(uniqueBook);
				
				//获取某本书的详细信息
				Book book = new Book();
				String bookName = rs.getString("b_na");
				char type = rs.getString("b_type").charAt(0);
				String author = rs.getString("b_aut");
				String bproduction = rs.getString("press");
				String publishDate = rs.getString("pdate");
				String price = rs.getString("price");
				int num = rs.getInt("b_num");
				int pageNum = rs.getInt("b_pnum");
				String introduction = rs.getString("b_intro");
				Date registerDate = rs.getDate("b_date");
				
				book.setIsbn(isbn);
				book.setBname(bookName);
				book.setBtype(type);
				book.setBauthor(author);
				book.setBproduction(bproduction);
				book.setPublishDate(publishDate);
				book.setPrice(price);
				book.setNum(num);
				book.setPageNum(pageNum);
				book.setRegisterDate(registerDate);
				book.setIntroduction(introduction);
				
				books.add(book);
				
			}
			
		} catch (Exception e) {
			System.out.println("根据条件获取图书失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		
		allBooks.add(uBooks);
		allBooks.add(books);
		
		return allBooks;
		
	}


}
