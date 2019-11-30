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
	 * ʵ��ͼ����Ĺ���
	 * 1.��ӽ�����Ϣ
	 * 2.��ͼ���״̬����Ϊ �����B��
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
		
		int type = user.getType();//��ȡ�û������ͣ��Դ�������Ӧ���黹������ʱ��
		
		//��¼�������ޣ�����
		int days = 0;
		long daytime = 0L;
		//��¼�黹���ڵĺ���ֵ
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
		
		//��ͼ��״̬�ı�
		boolean re = bdao.changeBookStatus(bid,'B');
		int num = 0;//��¼�����û������Խ���鼮��Ŀ
		ArrayList<UserType> allUserTypes = us.getAllUserTypes();
		for (UserType userType : allUserTypes) {
			if(user.getType() == userType.getTypeId()) {
				num = userType.getCanBorrowNum();
			}
		}
		//��ǰ�ѽ�ͼ��һ��Ҫ���ڿɽ�����
		if(bd.getUserBorrowsNum(user.getUserid())< num) {
			return bd.takeOut(borrow) && re ;
		}
		
		return false;
	}
	
	
	/**
	 * ʵ��ͼ��黹����
	 *   ���½�����Ϣ�еĹ黹����
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean takeIn(String bid) throws Exception {

		//��ͼ��״̬�ı�
		
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
	
	//����ָ���������ɴ����ֵ������
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
	 * ��ȡĳһҳ�Ľ��ļ�¼
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getAllBorrow(int page) throws Exception {
		return bd.getAllBorrow(page);
		
	}
	/**
	 * ��ȡ���еĽ��ļ�¼
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList> getAllBorrows() throws Exception {
		return bd.getAllBorrows();
		
	}

	/**
	 * ��ѯĳ���û������н�����Ϣ������ҳ����
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
			int backcount = 0;//�ѹ黹ͼ������
			for (Date date : allBackDate) {
				//System.out.println(borrow.getBackdate());
				if(date != null) {
					backcount++;
				}
			}
			
			if(backcount == allBackDate.size()) {
				result = true;//���û�����������鶼�ѹ黹
			}
		}else {
			result = true;//���û���δ����鼮
		}
		
		return result;
	}

}
