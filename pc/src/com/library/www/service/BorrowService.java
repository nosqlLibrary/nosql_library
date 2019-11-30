package com.library.www.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.library.www.dao.BookDao;
import com.library.www.dao.BorrowDao;
import com.library.www.po.Book;
import com.library.www.po.Borrow;
import com.library.www.po.UniqueBook;
import com.library.www.po.User;
import com.library.www.po.UserType;

public class BorrowService {
	private BorrowDao bd = new BorrowDao();
	private BookDao bdao  = new BookDao();
	private UserService us = new UserService();
	private BookService bs = new BookService();
	/**
	 * 实现图书借阅功能
	 * 1.添加借阅信息
	 * 2.将图书的状态设置为 借出（B）
	 * @param bid
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean takeOut(String bid, User user) throws Exception {
		
		
		Borrow borrow = new Borrow();
		borrow.setBid(bid);
		borrow.setUserid(user.getUserid());
		Date nowdate = new Date();
		borrow.setTakedate(nowdate);
		
		int type = user.getType();//获取用户的类型，以此来决定应当归还的日期时间
		
		//记录借书期限，天数
		int days = 0;
		long daytime = 0L;
		//记录归还日期的毫秒值
		long time = 0L;
		
		ArrayList<UserType> utypes = new ArrayList<>();
		try {
			 utypes = us.getAllUserTypes();
		} catch (Exception e4) {
			e4.printStackTrace();
		}
    	
    	
    	for (UserType utype : utypes) {
			if(type == utype.getTypeId()) {
				days = utype.getDeadline();
			}
		}
		
		
		daytime = days*24*3600*1000L;
		time = nowdate.getTime() + daytime;
//		System.out.println(daytime);
		Date deadline = new Date(time);
		borrow.setDeadline(deadline);
		borrow.setId(createData(6));
		
		//将图书状态改变
		boolean re = bdao.changeBookStatus(bid,'B');
		int num = 0;//记录该种用户最多可以借的书籍数目
		ArrayList<UserType> allUserTypes = us.getAllUserTypes();
		for (UserType userType : allUserTypes) {
			if(user.getType() == userType.getTypeId()) {
				num = userType.getCanBorrowNum();
			}
		}
		//当前已借图书一定要少于可借数量
		if(bd.getUserBorrowsNum(user.getUserid())< num) {
			return bd.takeOut(borrow) && re ;
		}
		
		return false;
	}
	
	
	/**
	 * 实现图书归还功能
	 *   更新借阅信息中的归还日期
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean takeIn(String bid) throws Exception {

		//将图书状态改变
		
		ArrayList<Borrow> borrowsByID = bd.getBorrowsByID(bid);
		if(borrowsByID.size()!=0) {
			for (Borrow borrow : borrowsByID) {
				if(borrow.getBackdate()==null && bs.checkBookStatus(bid, 'B')) {
					return bd.takeIn(borrow.getId()) && bdao.changeBookStatus(bid,'A');
				}
					
			}
		}
		
		return false;
	}
	
	//根据指定长度生成纯数字的随机数
	public static String createData(int length) {
	    StringBuilder sb=new StringBuilder();
	    Random rand=new Random();
	    for(int i=0;i<length;i++)
	    {
	        sb.append(rand.nextInt(10));
	    }
	    String data=sb.toString();
	    return data;
	}

	/**
	 * 获取某一页的借阅记录
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getAllBorrow(int page) throws Exception {
		return bd.getAllBorrow(page);
		
	}
	/**
	 * 获取所有的借阅记录
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getAllBorrows() throws Exception {
		return bd.getAllBorrows();
		
	}

	/**
	 * 查询某个用户的所有借阅信息（输入页数）
	 * @param u
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getUserBorrowMsg(User u,int page) throws Exception {
		
		ArrayList<ArrayList> allBorrows = bd.getUserBorrowMsg(u,page);
		ArrayList<ArrayList> uallBorrows = new ArrayList<>();
		ArrayList<Borrow> uborrows = new ArrayList<>();
		ArrayList<UniqueBook> ubooks = new ArrayList<>();
		ArrayList<Book> books = new ArrayList<>();
		
		for (int i = 0; i < allBorrows.get(0).size(); i++) {
			
			uborrows.add((Borrow) allBorrows.get(0).get(i));
			ubooks.add((UniqueBook) allBorrows.get(1).get(i));
			books.add((Book) allBorrows.get(2).get(i));
			
		}
		
		uallBorrows.add(uborrows);
		uallBorrows.add(ubooks);
		uallBorrows.add(books);
		
		return uallBorrows;
		
	}


	public boolean deleteBorrowsByBid(String bid) throws Exception {
		return bd.deleteBorrowsByBid(bid);
		
	}


	public boolean isExistBookBorrowMsg(String bid) throws Exception {
		
		return bd.isExistBookBorrowMsg(bid);
	}


	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getBorrowsByCondition(String condition,int page) throws Exception {
		
		return bd.getBorrowsByCondition(condition,page);
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getAllBorrowsByCondition(String condition) throws Exception {
		
		return bd.getAllBorrowsByCondition(condition);
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getUserBorrowsByCondition(String text,User user,int page) throws Exception {
		return  bd.getUserBorrowsByCondition(text,user,page);
		
	}


	public Borrow getNowBorrowByID(String bid) throws Exception {
		ArrayList<Borrow> borrows = bd.getBorrowsByID(bid);
		boolean status = bs.checkBookStatus(bid, 'B');
		if(status)
			for (Borrow borrow : borrows) {
				if(borrow.getBackdate() == null ) {
					return borrow;
				}
			}
		
		return null;
	}
	
	public boolean checkIsAllBack(User u) throws Exception {
		
		boolean result = false;
		ArrayList<Date> allBackDate = bd.getAllBackDate(u);
		if(allBackDate.size()!=0) {
			int backcount = 0;//已归还图书数量
			for (Date date : allBackDate) {
				//System.out.println(borrow.getBackdate());
				if(date != null) {
					backcount++;
				}
			}
			
			if(backcount == allBackDate.size()) {
				result = true;//该用户借过的所有书都已归还
			}
		}else {
			result = true;//该用户还未借过书籍
		}
		
		return result;
	}

}
