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
	 * �����ݿ��л�ȡ���е�������
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public ArrayList<Book> getAllBooks() throws Exception{
		ArrayList<Book> books = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//����sql���
			String sql = "select * from book_info ";
			preState = conn.prepareStatement(sql);
			
			//ִ�н����
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
			System.out.println("��ȡͼ����ϸ��Ϣʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
			
		}
		return books;
	}
	/**
	 * ����ҳ�������鱾������
	 * ��ȡ�����ĳһ���飺ÿ��ͼ��ͼ��ݿ����ܲ�Ψһ����ʱ�ж��bid��Ӧͬһ��isbn��
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public ArrayList<UniqueBook> getSomeSingleBook(int page) throws Exception{
		ArrayList<UniqueBook> uBooks = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//����sql���
			String sql = "select  * from book limit ?,20";
			preState = conn.prepareStatement(sql);
			preState.setInt(1, (page-1)*20 );
			//ִ�н����
			rs=preState.executeQuery();
			while(rs.next()) {
				UniqueBook uniqueBook = new UniqueBook();
				String isbn = rs.getString("isbn");
				String bid = rs.getString("b_id");
				char status = rs.getString("b_status").charAt(0);
				//��ȡ�����鱾����Ϣ
				uniqueBook.setIsbn(isbn);
				uniqueBook.setBid(bid);
				uniqueBook.setStatus(status);
				
				uBooks.add(uniqueBook);
				
			}
			
		} catch (Exception e) {
			System.out.println("��ȡͼ��ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		
		
		return uBooks;
	}
	
	
	
	/**
	 * �����û��������������ȡͼ����Ϣ
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
			
			//����sql���   ����/����/ISBN/ͼ����/������
			String sql = "SELECT * from book_info,book where (book.isbn like ? or b_na like ? or b_aut like ?"
					+ " or press like ? or b_id like ?) and book.isbn = book_info.isbn limit ?,10";
			preState = conn.prepareStatement(sql);
			preState.setString(1,"%" + condition + "%");
			preState.setString(2,"%" + condition + "%");
			preState.setString(3,"%" + condition + "%");
			preState.setString(4,"%" + condition + "%");
			preState.setString(5,"%" + condition + "%");
			preState.setInt(6, (page - 1) *20);
			//ִ�н����
			rs=preState.executeQuery();
			while(rs.next()) {
				UniqueBook uniqueBook = new UniqueBook();
				
				String isbn = rs.getString("isbn");
				String bid = rs.getString("b_id");
				char status = rs.getString("b_status").charAt(0);
				//��ȡ����ĳһ����
				uniqueBook.setIsbn(isbn);
				uniqueBook.setBid(bid);
				uniqueBook.setStatus(status);
				
				uBooks.add(uniqueBook);
				
				//��ȡĳ�������ϸ��Ϣ
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
			System.out.println("����������ȡͼ��ʧ��");
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
	 * ��ȡ���ݿ������е�ͼ�����  ----��"��ţ������"�ĸ�ʽ����ڶ�̬�����з���
	 * @return
	 * @throws Exception
	 */
	public ArrayList<String> getBookTypeNames() throws Exception{
		ArrayList<String> typeNames = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//����sql���
			String sql = "select * from booktype";
			preState = conn.prepareStatement(sql);
			//ִ�н����
			rs = preState.executeQuery();
			while(rs.next()) {
				
				String type = rs.getString("bt_name");
				char id = rs.getString("bt_id").charAt(0);
				//ƴ���ַ���
				typeNames.add(id+":"+type);
				
			}
			
		} catch (Exception e) {
			System.out.println("��ȡͼ�����ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		
		return typeNames;
	}
	
	/**
	 * ���ĳ��ͼ��ľ�����Ϣ--���뵽���ݿ���
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public boolean addNewBook(Book book) throws Exception {
		boolean result = false;
		conn = db.getCon();
		
		try {
			conn.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			
			Date date = new Date(System.currentTimeMillis());
			//����sql���
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
			//ִ�н����
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//�ύJDBC����
			conn.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
		} catch (Exception e) {
			conn.rollback();//�ع�JDBC����
			System.out.println("����ͼ��ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
	}

	/**
	 * ��Ӿ����ĳһ���� -- ���뵽���ݿ���
	 * @param uniquebook
	 * @return
	 * @throws Exception
	 */
	public boolean addUniqueBook(UniqueBook uniquebook) throws Exception {
		boolean result = false;
		conn = db.getCon();
		
		try {
			conn.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			
			//����sql���
			String sql = "insert into book values(?,?,?)";
			preState = conn.prepareStatement(sql);
			preState.setString(1, uniquebook.getBid());
			preState.setString(2, uniquebook.getIsbn());
			preState.setString(3, Character.toString(uniquebook.getStatus()));
			//ִ�н����
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//�ύJDBC����
			conn.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
		} catch (Exception e) {
			conn.rollback();//�ع�JDBC����
			System.out.println("����uniquebookʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
		
	}

	/**
	 * ��ȡĳ����ľ�����Ϣ---�����ݿ���
	 * @param bid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getBookDetails(String bid) throws Exception {
		ArrayList arr = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//����sql���
			String sql = "select * from book,book_info where b_id = ? and book.isbn = book_info.isbn";
			
			preState = conn.prepareStatement(sql);
			preState.setString(1, bid);
			//ִ�н����
			rs=preState.executeQuery();
			while(rs.next()) {
				UniqueBook uniqueBook = new UniqueBook();
				
				String isbn = rs.getString("isbn");
				
				char status = rs.getString("b_status").charAt(0);
				//��ȡ����ĳһ����
				uniqueBook.setIsbn(isbn);
				uniqueBook.setBid(bid);
				uniqueBook.setStatus(status);
				
				arr.add(uniqueBook);
				
				//��ȡĳ�������ϸ��Ϣ
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
			System.out.println("��ȡͼ����ϸ��Ϣʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return arr;
	}

	/**
	 * ����ͼ���ţ�ɾ�����ݿ��еĸñ�ͼ�飬���޸Ķ�Ӧ����ϸ��Ϣ��Ŀ���ֶ�
	 * 
	 * @param bid
	 * @return
	 * @throws Exception
	 */

	public boolean deleteUniqueBook(String bid) throws Exception {
		boolean result = false;
		conn = db.getCon();
		
		try {
			conn.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
		
			
			String sql2 = "select b_num,isbn from book_info where isbn = (select isbn from book where b_id = ?)";
			preState = conn.prepareStatement(sql2);
			preState.setString(1, bid);
			int bnum = 0;//��¼�����Ϣ
			String isbn ="";//��¼isbn
			rs=preState.executeQuery();
			while(rs.next()) {
				bnum = rs.getInt("b_num");
				isbn = rs.getString("isbn");
			}
			
			
			//����sql���
			String sql = "delete from book where b_id = ?";
			preState = conn.prepareStatement(sql);
			preState.setString(1, bid);
			//ִ�н����
			int re = preState.executeUpdate();
			
			if(re!=0) {
				bnum--;//��ǰ����һ
			}
			
			String sql3 = "update book_info set b_num= ? where isbn = ? ";
			preState = conn.prepareStatement(sql3);
			preState.setInt(1, bnum);
			preState.setString(2, isbn);
			
			int re2 = preState.executeUpdate();
			
			if(re != 0 && re2 != 0) {
				result = true;
			}

			conn.commit();//�ύJDBC����
			conn.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ	
			
		} catch (Exception e) {
			conn.rollback();//�ع�JDBC����
			System.out.println("ɾ��ͼ��ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
		
	}

	/**
	 * ɾ��ĳ��ͼ��ľ�����Ϣ  ----�����ݿ⽻�� 
	 * @param isbn
	 * @return
	 * @throws Exception
	 */
	public boolean deletBook(String isbn) throws Exception {
		boolean result = false;
		conn = db.getCon();
		
		try {
			conn.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			
			//����sql���
			String sql = "delete from book_info where isbn = ? ";
			preState = conn.prepareStatement(sql);
			preState.setString(1, isbn);
			//ִ�н����
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//�ύJDBC����
			conn.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
		} catch (Exception e) {
			conn.rollback();//�ع�JDBC����
			System.out.println("ɾ��ͼ�������Ϣʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
		
	}

	/**
	 * �û��������ͼ�飬�����ݿ��������ͼ��ľ���ͼ����Ϣ���޸ĸ�ͼ��Ŀ������
	 * @param book ����Service���޸�book�е�numֵ֮�󴫵�dao����
	 * @return
	 * @throws Exception
	 */
	public boolean changeExistBookNum (Book book) throws Exception {
		boolean result = false;
		conn = db.getCon();
		
		try {
			conn.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			
			//����sql���
			String sql = "update book_info set b_num = ? where isbn = ? ";
			preState = conn.prepareStatement(sql);
			preState.setInt(1, book.getNum());
			preState.setString(2, book.getIsbn());
			//ִ�н����
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//�ύJDBC����
			conn.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
		} catch (Exception e) {
			conn.rollback();//�ع�JDBC����
			System.out.println("�޸�ͼ����ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
	}

	/**
	 * �޸��û�����ͼ����Ϣ ---���ݿ⽻��
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public boolean editBook(Book book) throws Exception {
		
		boolean result = false;
		conn = db.getCon();
		try {
			conn.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			
			//����sql���
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
			//ִ�н����
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//�ύJDBC����
			conn.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
		} catch (Exception e) {
			conn.rollback();//�ع�JDBC����
			System.out.println("�޸�ͼ����Ϣʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
	}

	/**
	 *  �޸�ͼ���״̬  �����ݿ���
	 * @param bid
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public boolean changeBookStatus(String bid,char status) throws Exception {
		boolean result = false;
		conn = db.getCon();
		try {
			conn.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			
			//����sql���
			String sql = "update book set b_status = ? where b_id = ?";
			preState = conn.prepareStatement(sql);
			preState.setString(1, Character.toString(status));
			preState.setString(2, bid);
			//ִ�н����
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//�ύJDBC����
			conn.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
		} catch (Exception e) {
			conn.rollback();//�ع�JDBC����
			System.out.println("�޸�ͼ��״̬ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return result;
		
	}

	/**
	 * ����ͼ�����ͱ�ŵõ����͵ľ�������
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public BookType getBookTypeNameById(char id) throws Exception {
		
		BookType bookType = new BookType();
		
		try {
			conn = db.getCon();
			
			//����sql���
			String sql = "select * from booktype where bt_id = ? ";
			preState = conn.prepareStatement(sql);
			preState.setString(1, Character.toString(id));
			//ִ�н����
			rs = preState.executeQuery();
			while(rs.next()) {
				char btid = rs.getString("bt_id").charAt(0);
				String typename = rs.getString("bt_name");
				
				bookType.setBtId(btid);
				bookType.setTypename(typename);
			}
			
		} catch (Exception e) {
			System.out.println("�޸�ͼ��״̬ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return bookType;
	}
	
	/**
	 * ��ȡ���ݿ������е�ͼ��
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
			
			//����sql���   ����/����/ISBN/ͼ����/������
			String sql = "SELECT * from book_info,book where book.isbn = book_info.isbn ";
			preState = conn.prepareStatement(sql);
			
			//ִ�н����
			rs=preState.executeQuery();
			while(rs.next()) {
				UniqueBook uniqueBook = new UniqueBook();
				
				String isbn = rs.getString("isbn");
				String bid = rs.getString("b_id");
				char status = rs.getString("b_status").charAt(0);
				//��ȡ����ĳһ����
				uniqueBook.setIsbn(isbn);
				uniqueBook.setBid(bid);
				uniqueBook.setStatus(status);
				
				uBooks.add(uniqueBook);
				
				//��ȡĳ�������ϸ��Ϣ
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
			System.out.println("����������ȡͼ��ʧ��");
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
