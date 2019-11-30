package com.library.www.service;

import java.util.ArrayList;

import com.library.www.dao.UserDao;
import com.library.www.po.Admin;
import com.library.www.po.User;
import com.library.www.po.UserType;

public class UserService {
	
	
	private UserDao udao  = new UserDao();
	

	public User userLogin(String userid, String userPassword) throws Exception {
		
		return udao.userLogin(userid, userPassword);
		
	}

	public Admin managerLogin(String userid, String userPassword) throws Exception {
		return  udao.adminLogin(userid, userPassword);
		
	}

	public boolean userRegister(User user) throws Exception {
		
		return udao.userRegister(user);
	}

	public boolean changeManagerPwd(Admin admin) throws Exception {
		
		return udao.changeManagerPwd(admin);
	}

	public User getUserMsgByID(String userid) throws Exception {
		return udao.getUserMsgByID(userid);
		
	}

	/**
	 * ע���û�ʱ���ȼ���û�״̬�Ƿ�������A��
	 * 
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public boolean logOut(User u) throws Exception {
		
		boolean result = false;
		//��ע�û�״̬�Ƿ�����
		boolean normal = false;
		User user = udao.getUserMsgByID(u.getUserid());
		char status = user.getStatus();
		if(status == 'A')
			normal =  true;
		if(normal) {
			result = udao.logOut(u);
		}
		return result;
	}

	public boolean updateUserMsg(User user) throws Exception {
		return udao.updateUserMsg(user);
		
	}

	/**
	 * ������������������ѯ�Ƿ�����û����Ӷ������������Ϣ����
	 * @param condition
	 * @return 
	 * @throws Exception
	 */
	
	public ArrayList<User> getUserByCondition(String condition,int page) throws Exception {
		ArrayList<User> users = new ArrayList<>();
		users = udao.getUserByCondition(condition,page);
		return users;
	}

	public ArrayList<User> getAllUsers(int page) throws Exception {
		
		return udao.getAllUsers(page);
	}

	public boolean deleteUserById(String userid) throws Exception {
		
		return udao.deleteUserById(userid);
	}

	public boolean addNewAdmin(Admin newadmin) throws Exception  {
		
		return udao.addNewAdmin(newadmin);
	}

	public ArrayList<UserType> getAllUserTypes() throws Exception  {
		
		return udao.getAllUserTypes();
	}

	
}
