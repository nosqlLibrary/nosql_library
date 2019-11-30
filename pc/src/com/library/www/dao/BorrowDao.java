package com.library.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.library.www.po.Book;
import com.library.www.po.Borrow;
import com.library.www.po.UniqueBook;
import com.library.www.po.User;
import com.library.www.util.DbUtil;

public class BorrowDao {
	
	private PreparedStatement preState = null;
	private ResultSet rs = null;
	private Connection conn = null;
	private DbUtil db = new DbUtil();
    
	/**
	 * �����ݿ���������Ϣ
	 * @param borrow
	 * @return
	 * @throws Exception
	 */
	public boolean takeOut(Borrow borrow) throws Exception {
		boolean result = false;
		conn = db.getCon();
		try {
			
			conn.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			
			//�޸����ڸ�ʽ
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String nowdate = sdf.format(borrow.getTakedate());
	        String deadline = sdf.format(borrow.getDeadline());
			
			//����sql���
			String sql = "insert into borrow values(?,?,?,?,?,null)";
			preState = conn.prepareStatement(sql);
			preState.setString(1, borrow.getId());
			preState.setString(2, borrow.getBid());
			preState.setString(3, borrow.getUserid());
			preState.setString(4, nowdate);
			preState.setString(5, deadline);
			
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
	 * �û����飬���½�����Ϣ�Ĺ黹����
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean takeIn(String id) throws Exception {
		boolean result = false;
		conn = db.getCon();
		
		try {
			conn.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			//�޸����ڸ�ʽ
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String nowdate = sdf.format(new Date());
	        
			
			//����sql���
			String sql = "update borrow set return_date = ? where id= ? ";
			preState = conn.prepareStatement(sql);
			preState.setString(1, nowdate);
			preState.setString(2, id);
			//ִ�н����
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//�ύJDBC����
			conn.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
		} catch (Exception e) {
			conn.rollback();//�ع�JDBC����
			System.out.println("����ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		return result;
	}

	/**
	 * �����鼮��ź��û���ż�����ݿ��Ƿ��������
	 * �����û��Ƿ�������
	 * @param bid
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Borrow> checkIsBorrow(String bid, String userid) throws Exception {
		ArrayList<Borrow> borrows = new ArrayList<>();
		conn = db.getCon();
		
		try {
			//����sql���
			String sql = "select * from borrow where b_id = ? and r_id= ?";
			preState = conn.prepareStatement(sql);
			preState.setString(1, bid);
			preState.setString(2, userid);
			//ִ�н����
			rs=preState.executeQuery();
			
			while(rs.next()) {
				Borrow borrow = new Borrow();
				//��ȡ�����ݿ��л�ȡ��������
				String id = rs.getString("id");
				String bookid = rs.getString("b_id");
				String uid = rs.getString("r_id");
				
				Date borrowDate = rs.getDate("borrow_date");
				Date deadline = rs.getDate("deadtime");
				Date returnDate = rs.getDate("return_date");
				
				borrow.setId(id);
				borrow.setBid(bookid);
				borrow.setUserid(uid);
				borrow.setTakedate(borrowDate);
				borrow.setDeadline(deadline);
				borrow.setBackdate(returnDate);
				
				borrows.add(borrow);
			}
			
		} catch (Exception e) {
			System.out.println("��ȡ��ʷ��¼ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		return borrows;
	}

	/**
	 * ��ȡ���ݿ����еĽ�����Ϣ��ĳһҳ��
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({  "rawtypes"})
	public ArrayList<ArrayList> getAllBorrow(int page) throws Exception {
		ArrayList<ArrayList> borrowsDetail = new ArrayList<>();
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<UniqueBook> ubooks = new ArrayList<>();
		ArrayList<User> users = new ArrayList<>();
		ArrayList<Borrow> borrows = new ArrayList<>();
		try {
			conn = db.getCon();
			
			//����sql���
			String sql = "select * from borrow,reader,book,book_info where borrow.b_id = book.b_id and "
						+ "borrow.r_id = reader.r_id  and book.isbn = book_info.isbn limit ?,20";
			preState = conn.prepareStatement(sql);
			preState.setInt(1, (page - 1)*20);
			//ִ�н����
			rs=preState.executeQuery();
			
			while(rs.next()) {
				
				Borrow borrow = new Borrow(rs.getString("id"),rs.getString("r_id"),rs.getString("b_id"),
						rs.getDate("borrow_date"),rs.getDate("deadtime"),rs.getDate("return_date"));
				borrows.add(borrow);
				
				UniqueBook ubook = new UniqueBook(rs.getString("b_id"),rs.getString("isbn"),
									rs.getString("b_status").charAt(0));
				ubooks.add(ubook);
				
				//��ȡĳ�������ϸ��Ϣ
				Book book = new Book(rs.getString("isbn"),rs.getString("b_na"),rs.getString("press"),
							rs.getString("b_aut"),rs.getString("pdate"),rs.getString("price"),
							rs.getString("b_type").charAt(0),rs.getInt("b_pnum"),rs.getDate("b_date")
							,rs.getInt("b_num"),rs.getString("b_intro"));
				books.add(book);
				
				//��ȡ���ݿ�õ���User��������ֶε�ֵ
				User user = new User();
				
				String uid = rs.getString("r_id");
				String pwd = rs.getString("r_password");
				String username = rs.getString("r_na");
				Date registerDate = rs.getDate("r_date");
				int type = rs.getInt("r_type");
				char gender = rs.getString("r_sex").charAt(0);
				String department = rs.getString("r_col");
				String email = rs.getString("r_email");
				long tel = rs.getLong("r_tel");
				char status = rs.getString("r_status").charAt(0);
				
				//����user��ֵ
				user.setUserid(uid);
				user.setPassword(pwd);
				user.setUsername(username);
				user.setRegisterDate(registerDate);
				user.setType(type);
				user.setGender(gender);
				user.setEmail(email);
				user.setDepartment(department);
				user.setTel(tel);
				user.setStatus(status);
				
				users.add(user);
				
				
			}
			
		} catch (Exception e) {
			System.out.println("��ȡ���ݿ�Ľ�����Ϣʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		
		borrowsDetail.add(borrows);
		borrowsDetail.add(users);
		borrowsDetail.add(ubooks);
		borrowsDetail.add(books);
		return borrowsDetail;
	}
	
	/**
	 * ��ȡ���ݿ����еĽ�����Ϣ
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({  "rawtypes"})
	public ArrayList<ArrayList> getAllBorrows() throws Exception {
		ArrayList<ArrayList> borrowsDetail = new ArrayList<>();
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<UniqueBook> ubooks = new ArrayList<>();
		ArrayList<User> users = new ArrayList<>();
		ArrayList<Borrow> borrows = new ArrayList<>();
		try {
			conn = db.getCon();
			
			//����sql���
			String sql = "select * from borrow,reader,book,book_info where borrow.b_id = book.b_id and "
						+ "borrow.r_id = reader.r_id  and book.isbn = book_info.isbn ";
			preState = conn.prepareStatement(sql);
			
			//ִ�н����
			rs=preState.executeQuery();
			
			while(rs.next()) {
				
				Borrow borrow = new Borrow(rs.getString("id"),rs.getString("r_id"),rs.getString("b_id"),
							rs.getDate("borrow_date"),rs.getDate("deadtime"),rs.getDate("return_date"));
				borrows.add(borrow);
				
				UniqueBook ubook = new UniqueBook(rs.getString("b_id"),rs.getString("isbn"),
									rs.getString("b_status").charAt(0));
				ubooks.add(ubook);
				
				//��ȡĳ�������ϸ��Ϣ
				Book book = new Book(rs.getString("isbn"),rs.getString("b_na"),rs.getString("press"),
							rs.getString("b_aut"),rs.getString("pdate"),rs.getString("price"),
							rs.getString("b_type").charAt(0),rs.getInt("b_pnum"),rs.getDate("b_date")
							,rs.getInt("b_num"),rs.getString("b_intro"));
				books.add(book);
				
				//��ȡ���ݿ�õ���User��������ֶε�ֵ
				User user = new User();
				
				String uid = rs.getString("r_id");
				String pwd = rs.getString("r_password");
				String username = rs.getString("r_na");
				Date registerDate = rs.getDate("r_date");
				int type = rs.getInt("r_type");
				char gender = rs.getString("r_sex").charAt(0);
				String department = rs.getString("r_col");
				String email = rs.getString("r_email");
				long tel = rs.getLong("r_tel");
				char status = rs.getString("r_status").charAt(0);
				
				//����user��ֵ
				user.setUserid(uid);
				user.setPassword(pwd);
				user.setUsername(username);
				user.setRegisterDate(registerDate);
				user.setType(type);
				user.setGender(gender);
				user.setEmail(email);
				user.setDepartment(department);
				user.setTel(tel);
				user.setStatus(status);
				
				users.add(user);
				
				
			}
			
		} catch (Exception e) {
			System.out.println("��ȡ���ݿ�Ľ�����Ϣʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		
		borrowsDetail.add(borrows);
		borrowsDetail.add(users);
		borrowsDetail.add(ubooks);
		borrowsDetail.add(books);
		return borrowsDetail;
	}

	
	/**
	 * �����ݿ���ɾ��ָ��ͼ���Ŷ�Ӧ�Ľ��ļ�¼
	 * @param bid
	 * @return
	 * @throws Exception
	 */
	public boolean deleteBorrowsByBid(String bid) throws Exception {
		
		boolean result = false;
		conn = db.getCon();
		try {
			conn.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			
			//����sql���
			String sql = "delete from borrow where b_id = ?";
			preState = conn.prepareStatement(sql);
			preState.setString(1, bid);
			//ִ�н����
			int re = preState.executeUpdate();
			if(re != 0)
				result = true;
			conn.commit();//�ύJDBC����
			conn.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
		} catch (Exception e) {
			conn.rollback();//�ع�JDBC����
			System.out.println("ɾ��ָ��ͼ���Ŷ�Ӧ�Ľ��ļ�¼ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		return result;
	}

	public boolean isExistBookBorrowMsg(String bid) throws Exception {
		boolean result = false;
		conn = db.getCon();
		try {
			conn.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			
			//����sql���
			String sql = "select * from borrow where b_id = ?";
			preState = conn.prepareStatement(sql);
			preState.setString(1, bid);
			
			//ִ�н����
			rs=preState.executeQuery();
			int  rowcount = 0;
			while(rs.next()) {
				rowcount++;
			}
			
			if(rowcount!=0)
				result = true;
			conn.commit();//�ύJDBC����
			conn.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
		} catch (Exception e) {
			conn.rollback();//�ع�JDBC����
			System.out.println("ɾ��ָ��ͼ���Ŷ�Ӧ�Ľ��ļ�¼ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		return result;
		
	}
	/**
	 * �����û�����Ĳ�ѯ������ѯ������Ϣ��ĳҳ��
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getBorrowsByCondition(String condition,int page) throws Exception {
		ArrayList<ArrayList> borrowsDetail = new ArrayList<>();
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<UniqueBook> ubooks = new ArrayList<>();
		ArrayList<User> users = new ArrayList<>();
		ArrayList<Borrow> borrows = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//����sql���
			//����/���߱��/ISBN/ͼ����
			String sql = "SELECT * from borrow,book_info,book,reader where (book_info.b_na like ? or "
					+ "book_info.isbn like ? or book.b_id like ? or borrow.r_id like ? or reader.r_na like "
					+ "?) and book_info.isbn = book.isbn and book.b_id = borrow.b_id and reader.r_id = "
					+ "borrow.r_id limit ?,20";
			
			preState = conn.prepareStatement(sql);
			preState.setString(1, "%" + condition + "%");
			preState.setString(2,"%" + condition + "%");
			preState.setString(3, "%" + condition + "%");
			preState.setString(4, "%" + condition + "%");
			preState.setString(5, "%" + condition + "%");
			preState.setInt(6, (page-1)*20);
			
			//ִ�н����
			rs=preState.executeQuery();
			while(rs.next()) {

				Borrow borrow = new Borrow(rs.getString("id"),rs.getString("b_id"),
								rs.getString("r_id"),rs.getDate("borrow_date"),
								rs.getDate("deadtime"),rs.getDate("return_date"));
				borrows.add(borrow);
				
				UniqueBook ubook = new UniqueBook(rs.getString("b_id"),rs.getString("isbn"),
									rs.getString("b_status").charAt(0));
				ubooks.add(ubook);
				
				//��ȡĳ�������ϸ��Ϣ
				Book book = new Book(rs.getString("isbn"),rs.getString("b_na"),rs.getString("press"),
							rs.getString("b_aut"),rs.getString("pdate"),rs.getString("price"),
							rs.getString("b_type").charAt(0),rs.getInt("b_pnum"),rs.getDate("b_date")
							,rs.getInt("b_num"),rs.getString("b_intro"));
				books.add(book);
				
				//��ȡ���ݿ�õ���User��������ֶε�ֵ
				User user = new User();
				
				String uid = rs.getString("r_id");
				String pwd = rs.getString("r_password");
				String username = rs.getString("r_na");
				Date registerDate = rs.getDate("r_date");
				int type = rs.getInt("r_type");
				char gender = rs.getString("r_sex").charAt(0);
				String department = rs.getString("r_col");
				String email = rs.getString("r_email");
				long tel = rs.getLong("r_tel");
				char status = rs.getString("r_status").charAt(0);
				
				//����user��ֵ
				user.setUserid(uid);
				user.setPassword(pwd);
				user.setUsername(username);
				user.setRegisterDate(registerDate);
				user.setType(type);
				user.setGender(gender);
				user.setEmail(email);
				user.setDepartment(department);
				user.setTel(tel);
				user.setStatus(status);
				
				users.add(user);
			}
			
		} catch (Exception e) {
			System.out.println("����������ѯ���ļ�¼ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		borrowsDetail.add(borrows);
		borrowsDetail.add(users);
		borrowsDetail.add(ubooks);
		borrowsDetail.add(books);
		return borrowsDetail;
	}

	/**
	 *  �����ݿ��и���������ѯĳ�û����ļ�¼��ĳ��ҳ����
	 * @param condition
	 * @param u
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getUserBorrowsByCondition(String condition,User u ,int page) throws Exception {
		ArrayList<ArrayList> borrowsDetail = new ArrayList<>();
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<UniqueBook> ubooks = new ArrayList<>();
		
		ArrayList<Borrow> borrows = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//����sql���
			//����/���߱��/ISBN/ͼ����
			String sql = "SELECT * from borrow,book_info,book,reader where (book_info.b_na like ? or "
					+ "book_info.isbn like ? or book.b_id like ? or borrow.r_id like ?) and "
					+ "book_info.isbn = book.isbn and book.b_id = borrow.b_id and reader.r_id = borrow.r_id"
					+ " and reader.r_id  = ? limit ?,20";
			
			preState = conn.prepareStatement(sql);
			preState.setString(1, "%" + condition + "%");
			preState.setString(2,"%" + condition + "%");
			preState.setString(3, "%" + condition + "%");
			preState.setString(4, "%" + condition + "%");
			preState.setString(5, u.getUserid());
			preState.setInt(6, (page - 1)* 20);
			
			//ִ�н����
			rs=preState.executeQuery();
			while(rs.next()) {

				Borrow borrow = new Borrow(rs.getString("id"),rs.getString("b_id"),
								rs.getString("r_id"),rs.getDate("borrow_date"),
								rs.getDate("deadtime"),rs.getDate("return_date"));
				borrows.add(borrow);
				
				UniqueBook ubook = new UniqueBook(rs.getString("b_id"),rs.getString("isbn"),
									rs.getString("b_status").charAt(0));
				ubooks.add(ubook);
				
				//��ȡĳ�������ϸ��Ϣ
				Book book = new Book(rs.getString("isbn"),rs.getString("b_na"),rs.getString("press"),
							rs.getString("b_aut"),rs.getString("pdate"),rs.getString("price"),
							rs.getString("b_type").charAt(0),rs.getInt("b_pnum"),rs.getDate("b_date")
							,rs.getInt("b_num"),rs.getString("b_intro"));
				books.add(book);
				
			}
			
		} catch (Exception e) {
			System.out.println("����������ѯĳ�û����ļ�¼ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		borrowsDetail.add(borrows);
		
		borrowsDetail.add(ubooks);
		borrowsDetail.add(books);
		return borrowsDetail;
	}

	
	
	/**
	 * ����Bid��ȡ�����ݿ������ж�Ӧ�Ľ��ļ�¼
	 * @param bid
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Borrow> getBorrowsByID(String bid) throws Exception {
		ArrayList<Borrow> borrows = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//����sql���
			//����/���߱��/ISBN/ͼ����
			String sql = "SELECT * from borrow where b_id = ?";
			
			preState = conn.prepareStatement(sql);
			preState.setString(1, bid);
			
			
			//ִ�н����
			rs=preState.executeQuery();
			while(rs.next()) {
				Borrow bo = new Borrow();
				
				String id = rs.getString("id");
				String thisbid = rs.getString("b_id");
				String userid = rs.getString("r_id");
				Date takedate = rs.getDate("borrow_date");
				Date deadline = rs.getDate("deadtime");
				Date returnDate = rs.getDate("return_date");
				bo.setBid(thisbid);
				bo.setId(id);
				bo.setUserid(userid);
				bo.setTakedate(takedate);
				bo.setDeadline(deadline);
				bo.setBackdate(returnDate);
				
				borrows.add(bo);
			}
			
		} catch (Exception e) {
			System.out.println("����������ѯ���ļ�¼ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		
		return borrows;
	}

	/**
	 * �����ݿ��л�ȡ�û������н�����Ϣ��ĳ��ҳ����
	 * @param u
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getUserBorrowMsg(User u, int page) throws Exception {
		ArrayList<ArrayList> borrowsDetail = new ArrayList<>();
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<UniqueBook> ubooks = new ArrayList<>();
		ArrayList<Borrow> borrows = new ArrayList<>();
		try {
			conn = db.getCon();
			
			//����sql���
			String sql = "select * from borrow,reader,book,book_info where borrow.b_id = book.b_id and "
						+ "borrow.r_id = reader.r_id  and book.isbn = book_info.isbn and reader.r_id = ? "
						+ " limit ?,20";
			preState = conn.prepareStatement(sql);
			preState.setString(1, u.getUserid());
			preState.setInt(2, (page-1)*20);
			//ִ�н����
			rs=preState.executeQuery();
			
			while(rs.next()) {
				
				Borrow borrow = new Borrow(rs.getString("id"),rs.getString("b_id"),
								rs.getString("r_id"),rs.getDate("borrow_date"),
								rs.getDate("deadtime"),rs.getDate("return_date"));
				borrows.add(borrow);
				
				UniqueBook ubook = new UniqueBook(rs.getString("b_id"),rs.getString("isbn"),
									rs.getString("b_status").charAt(0));
				ubooks.add(ubook);
				
				//��ȡĳ�������ϸ��Ϣ
				Book book = new Book(rs.getString("isbn"),rs.getString("b_na"),rs.getString("press"),
							rs.getString("b_aut"),rs.getString("pdate"),rs.getString("price"),
							rs.getString("b_type").charAt(0),rs.getInt("b_pnum"),rs.getDate("b_date")
							,rs.getInt("b_num"),rs.getString("b_intro"));
				books.add(book);
			}
			
		} catch (Exception e) {
			System.out.println("��ȡ���ݿ�Ľ�����Ϣʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		
		borrowsDetail.add(borrows);
		borrowsDetail.add(ubooks);
		borrowsDetail.add(books);
		return borrowsDetail;
	}
	
	public int getUserBorrowsNum(String userid) throws Exception {
		int num = 0 ;
		try {
			conn = db.getCon();
			
			//����sql���
			String sql = "select count(*) as sum from borrow where r_id = ?";
			preState = conn.prepareStatement(sql);
			preState.setString(1, userid);
			//ִ�н����
			rs=preState.executeQuery();
			
			while(rs.next()) {
				num = rs.getInt("sum");
			}
			
		} catch (Exception e) {
			System.out.println("��ȡ�û���������ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		return num;
	}
	
	/**
	 * �����ݿ��л�ȡĳ�û����н��ļ�¼�Ĺ黹����
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Date> getAllBackDate(User user) throws Exception {
		
		ArrayList<Date> dates = new ArrayList<>();
		try {
			conn = db.getCon();
			
			//����sql���
			String sql = "select return_date from borrow where r_id = ? ";
			preState = conn.prepareStatement(sql);
			preState.setString(1,user.getUserid());
			//ִ�н����
			rs = preState.executeQuery();
			while(rs.next()) {
				
				Date backDate = rs.getDate("return_date");
				
				dates.add(backDate);
			}
			
		} catch (Exception e) {
			System.out.println("��ȡ���й黹����ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			conn.close();
		}
		
		return dates;
		
	}

	/**
	 * ����������ȡ���з��ϵĽ��ļ�¼��û��ҳ������
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getAllBorrowsByCondition(String condition) throws Exception {
		ArrayList<ArrayList> borrowsDetail = new ArrayList<>();
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<UniqueBook> ubooks = new ArrayList<>();
		ArrayList<User> users = new ArrayList<>();
		ArrayList<Borrow> borrows = new ArrayList<>();
		
		try {
			conn = db.getCon();
			
			//����sql���
			//����/���߱��/ISBN/ͼ����
			String sql = "SELECT * from borrow,book_info,book,reader where (book_info.b_na like ? or "
					+ "book_info.isbn like ? or book.b_id like ? or borrow.r_id like ? or reader.r_na like "
					+ "?) and book_info.isbn = book.isbn and book.b_id = borrow.b_id and reader.r_id = "
					+ "borrow.r_id ";
			
			preState = conn.prepareStatement(sql);
			preState.setString(1, "%" + condition + "%");
			preState.setString(2,"%" + condition + "%");
			preState.setString(3, "%" + condition + "%");
			preState.setString(4, "%" + condition + "%");
			preState.setString(5, "%" + condition + "%");
			
			
			//ִ�н����
			rs=preState.executeQuery();
			while(rs.next()) {

				Borrow borrow = new Borrow(rs.getString("id"),rs.getString("b_id"),
								rs.getString("r_id"),rs.getDate("borrow_date"),
								rs.getDate("deadtime"),rs.getDate("return_date"));
				borrows.add(borrow);
				
				UniqueBook ubook = new UniqueBook(rs.getString("b_id"),rs.getString("isbn"),
									rs.getString("b_status").charAt(0));
				ubooks.add(ubook);
				
				//��ȡĳ�������ϸ��Ϣ
				Book book = new Book(rs.getString("isbn"),rs.getString("b_na"),rs.getString("press"),
							rs.getString("b_aut"),rs.getString("pdate"),rs.getString("price"),
							rs.getString("b_type").charAt(0),rs.getInt("b_pnum"),rs.getDate("b_date")
							,rs.getInt("b_num"),rs.getString("b_intro"));
				books.add(book);
				
				//��ȡ���ݿ�õ���User��������ֶε�ֵ
				User user = new User();
				
				String uid = rs.getString("r_id");
				String pwd = rs.getString("r_password");
				String username = rs.getString("r_na");
				Date registerDate = rs.getDate("r_date");
				int type = rs.getInt("r_type");
				char gender = rs.getString("r_sex").charAt(0);
				String department = rs.getString("r_col");
				String email = rs.getString("r_email");
				long tel = rs.getLong("r_tel");
				char status = rs.getString("r_status").charAt(0);
				
				//����user��ֵ
				user.setUserid(uid);
				user.setPassword(pwd);
				user.setUsername(username);
				user.setRegisterDate(registerDate);
				user.setType(type);
				user.setGender(gender);
				user.setEmail(email);
				user.setDepartment(department);
				user.setTel(tel);
				user.setStatus(status);
				
				users.add(user);
			}
			
		} catch (Exception e) {
			System.out.println("����������ѯ���ļ�¼ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			conn.close();
		}
		borrowsDetail.add(borrows);
		borrowsDetail.add(users);
		borrowsDetail.add(ubooks);
		borrowsDetail.add(books);
		return borrowsDetail;
	}

	
}
