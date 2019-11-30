package com.library.www.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.library.www.po.Admin;
import com.library.www.po.User;
import com.library.www.po.UserType;
import com.library.www.util.DbUtil;

public class UserDao {
	
	private ResultSet rs = null;
	private PreparedStatement preState = null;
	private Connection con = null;
	private DbUtil db = new DbUtil();
	
	/**
	 * �û�ʵ�ֵ�¼--�����ݿ⽻��
	 * @param userid
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public User userLogin(String userid, String password) throws Exception {
		User user = new User();
		
		try {
			con = db.getCon();
			String sql = "select * from reader where r_id =? and r_password = ?";
		
			preState = con.prepareStatement(sql);
			preState.setString(1, userid);
			preState.setString(2, password);
			rs = preState.executeQuery();

			while(rs.next()) {
				//��ȡ���ݿ�õ���User��������ֶε�ֵ
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
				
				System.out.println(user.toString());
			}
			
		} catch (Exception e) {
			System.out.println("�û���¼ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			con.close();
		}
		return user;
	
		
	}
	/**
	 * ����Ա��¼ʵ��--�����ݿ���н���
	 * @param id
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public Admin adminLogin(String id,String pwd) throws Exception {
		Admin admin = new Admin();
		try {
			con = db.getCon();
			String sql = "select * from admin_table where admin_id = ? and admin_pwd =?";
			
			preState = con.prepareStatement(sql);
			preState.setString(1, id);
			preState.setString(2,pwd);
			//ִ�н����
			rs = preState.executeQuery();
			while(rs.next()) {
				//��ȡ���ݿ�õ���Admin��������ֶε�ֵ
				String adminName = rs.getString("admin_name");
				
				
				//����Admin��ֵ
				admin.setAdminName(adminName);
				admin.setId(id);
				admin.setPassword(pwd);
				
				
			}
			
		} catch (Exception e) {
			System.out.println("����Ա��¼ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			con.close();
		}
		
		return admin;
	}
	
	/**
	 * �û�ʵ��ע��----�����ݿ⽻��
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean userRegister(User user) throws Exception {
		boolean result = false;
		
		con = db.getCon();
		try {
			con.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			
			//��ע��ʱ���ʽת��
			Date date = new Date(System.currentTimeMillis());
			//����sql���
			String sql = "insert into reader values(?,?,?,?,?,?,?,?,?,?)"; 
			
			preState = con.prepareStatement(sql);
			preState.setString(1,user.getUserid());
			preState.setString(2,user.getUsername());
			preState.setInt(3, user.getType());
			preState.setString(4, Character.toString(user.getGender()));
			preState.setString(5, user.getDepartment());
			preState.setLong(6, user.getTel());
			preState.setString(7, user.getEmail());
			preState.setDate(8,date);
			preState.setString(9, Character.toString(user.getStatus()));
			preState.setString(10, user.getPassword());
			
			
			//ִ�н����
			int m = preState.executeUpdate();
			
			if(m!=0)
				result=true;

			con.commit();//�ύJDBC����
			con.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
			
		} catch (Exception e) {
			con.rollback();//�ع�JDBC����
			System.out.println("ע��ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();	
			con.close();
		}
		return result;
		
	}
	
	/**
	 * �޸����ݿ��й���Ա������
	 * @param admin
	 * @return
	 * @throws Exception
	 */
	public boolean changeManagerPwd(Admin admin) throws Exception {
		boolean result = false;
		
		con = db.getCon();
		try {
			con.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			//����sql���
			String sql = "update admin_table set admin_pwd = ? where admin_id = ?";
			
			preState = con.prepareStatement(sql);
			preState.setString(1, admin.getPassword());
			preState.setString(2, admin.getId());
			//ִ�н����
			int m = preState.executeUpdate();
			
			if(m!=0)
				result=true;
			con.commit();//�ύJDBC����
			con.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
			
		} catch (Exception e) {
			con.rollback();//�ع�JDBC����
			System.out.println("�޸�����ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			con.close();
		}
		return result;
	}
	
	/**
	 * ���ݶ��ߵ�id��ȡ�����ߵĻ�����Ϣ
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public User getUserMsgByID(String userid) throws Exception {
		User user = new User();
		
		con = db.getCon();
		try {
			//����sql���
			String sql = "select * from reader where r_id =?";
			preState = con.prepareStatement(sql);
			preState.setString(1, userid);
			//ִ�н����
			rs = preState.executeQuery();
			while(rs.next()) {
				//��ȡ���ݿ�õ���User��������ֶε�ֵ
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
				
				
			}
			
		} catch (Exception e) {
			System.out.println("����Ա��¼ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			con.close();
		}
		
		return user;
	}
	
	public boolean logOut(User u) throws Exception {
		boolean result = false;
		
		con = db.getCon();
		try {
			con.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			
			//����sql���
			String sql = "update reader set r_status = 'C' where r_id = ?" ;
			preState = con.prepareStatement(sql);
			preState.setString(1, u.getUserid());
			//ִ�н����
			int m = preState.executeUpdate();
			System.out.println(sql);
			if(m!=0)
				result=true;
			con.commit();//�ύJDBC����
			con.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
			
		} catch (Exception e) {
			con.rollback();//�ع�JDBC����
			System.out.println("�û�ע��ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();	
			con.close();
		}
		return result;
	}
	
	/**
	 * �û��Լ��޸��Լ����û���Ϣ   �����ݿ����޸�
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean updateUserMsg(User user) throws Exception {
		boolean result = false;
		
		con = db.getCon();
		try {
			con.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			
			//����sql���
			String sql = "update reader set r_na = ? ,r_type = ?,r_sex = ?,r_col = ?,r_tel =?,r_email =?"
					+ ",r_password = ?,r_status = ?  where r_id = ?";
			
			preState = con.prepareStatement(sql);
			preState.setString(1,user.getUsername());
			preState.setInt(2, user.getType());
			preState.setString(3, Character.toString(user.getGender()));
			preState.setString(4, user.getDepartment());
			preState.setLong(5, user.getTel());
			preState.setString(6, user.getEmail());
			preState.setString(7, user.getPassword());
			preState.setString(8, Character.toString(user.getStatus()));
			preState.setString(9, user.getUserid());
			
			//ִ�н����
			int m = preState.executeUpdate();
			
			if(m!=0)
				result=true;
			con.commit();//�ύJDBC����
			con.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
			
		} catch (Exception e) {
			con.rollback();//�ع�JDBC����
			System.out.println("�û��޸���Ϣʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			con.close();
		}
		return result;
		
	}
	
	/**
	 * ���ݸ����������������ݿ����ģ����ѯ�Ƿ�����û���Ϣ
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public ArrayList<User> getUserByCondition(String condition,int page) throws Exception {
		ArrayList<User> users = new ArrayList<>();
		
		try {
			con = db.getCon();
			
			//����sql���
			String sql = "SELECT * from reader,readertype where ( r_id like ? or r_na like ? or r_col like "
					+ "? or  rt_name like ?) and reader.r_type = readertype.rt_id limit ?,20";
							
			preState = con.prepareStatement(sql);
			preState.setString(1, "%" + condition + "%");
			preState.setString(2, "%" + condition + "%");
			preState.setString(3, "%" + condition + "%");
			preState.setString(4, "%" + condition + "%");
			preState.setInt(5, (page -1)*20);
			
			//ִ�н����
			rs = preState.executeQuery();
			
			while(rs.next()) {
				User u = new User();
				u.setUserid(rs.getString("r_id"));
				u.setUsername(rs.getString("r_na"));
				u.setType(rs.getInt("r_type"));
				u.setGender(rs.getString("r_sex").charAt(0));
				u.setDepartment(rs.getString("r_col"));
				u.setTel(rs.getLong("r_tel"));
				u.setEmail(rs.getString("r_email"));
				u.setRegisterDate(rs.getDate("r_date"));
				u.setStatus(rs.getString("r_status").charAt(0));
				u.setPassword(rs.getString("r_password"));
				users.add(u);
			}
			
		} catch (Exception e) {
			System.out.println("����������ȡ�û���Ϣʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			con.close();
		}
		return users;
	}
	
	/**
	 * ����Ա�����ݿ��л�ȡ�����û�����Ϣ
	 * @return
	 * @throws Exception
	 */
	public ArrayList<User> getAllUsers(int page) throws Exception {
		ArrayList<User> users = new ArrayList<>();
		
		try {
			con = db.getCon();
			
			//����sql���
			String sql = "SELECT * from reader limit ?,20";
			preState = con.prepareStatement(sql);
			preState.setInt(1, (page - 1)*20);
			//ִ�н����
			rs = preState.executeQuery();
			
			
			while(rs.next()) {
				User u = new User();
				u.setUserid(rs.getString("r_id"));
				u.setUsername(rs.getString("r_na"));
				u.setType(rs.getInt("r_type"));
				u.setGender(rs.getString("r_sex").charAt(0));
				u.setDepartment(rs.getString("r_col"));
				u.setTel(rs.getLong("r_tel"));
				u.setEmail(rs.getString("r_email"));
				u.setRegisterDate(rs.getDate("r_date"));
				u.setStatus(rs.getString("r_status").charAt(0));
				u.setPassword(rs.getString("r_password"));
				users.add(u);
			}
			
		} catch (Exception e) {
			System.out.println("��ȡ�����û���Ϣʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			con.close();
		}
		return users;
	}
	
	/**
	 * �����û����ɾ�����ݿ�����Ӧ���û���¼
	 * Ҫ��ɾ�����ı��еļ�¼����ɾ���û���ļ�¼
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public boolean deleteUserById(String userid) throws Exception {
		boolean result = false;
		
		con = db.getCon();
		
		try {
			con.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			//����sql���
			String sql2 = "delete from borrow where r_id = '" + userid +"'";
			preState = con.prepareStatement(sql2);
			preState.setString(1, userid);
			//ִ�н����
			int m = preState.executeUpdate();
			int n = 0;
			if(m!=0) {
				String sql1 = "delete from reader where r_id = ?";
				preState = con.prepareStatement(sql1);
				preState.setString(1, userid);
				n = preState.executeUpdate();
			}
			
			if(n!=0)
				result=true;
			con.commit();//�ύJDBC����
			con.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
			
		} catch (Exception e) {
			con.rollback();//�ع�JDBC����
			System.out.println("ɾ���û�ʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();	
			con.close();
		}
		return result;
	}
	
	/**
	 * �����ݿ�������¹���Ա����Ϣ
	 * @param newadmin
	 * @return
	 * @throws Exception
	 */
	public boolean addNewAdmin(Admin newadmin) throws Exception {
		boolean result = false;
		
		con = db.getCon();
		
		try {
			con.setAutoCommit(false);// ����JDBC�����Ĭ���ύ��ʽ
			
			//����sql���
			String sql = "insert into admin_table values(?,?,?)" ;
			
			preState = con.prepareStatement(sql);
			preState.setString(1, newadmin.getId());
			preState.setString(2, newadmin.getAdminName());
			preState.setString(3, newadmin.getPassword());
			//ִ�н����
			int m = preState.executeUpdate();
			
			
			if(m!=0)
				result=true;
			con.commit();//�ύJDBC����
			con.setAutoCommit(true);// �ָ�JDBC�����Ĭ���ύ��ʽ
			
		} catch (Exception e) {
			con.rollback();
			System.out.println("ע�����Աʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();	
			con.close();
		}
		return result;
	}
	
	/**
	 * ��ȡ���ݿ������еĶ�������
	 * @return
	 * @throws Exception
	 */
	public ArrayList<UserType> getAllUserTypes() throws Exception {
		ArrayList<UserType> utypes = new ArrayList<>();
		
		try {
			con = db.getCon();
			
			//����sql���
			String sql = "SELECT * from readertype";
			preState = con.prepareStatement(sql);
			//ִ�н����
			rs=preState.executeQuery();
			
			while(rs.next()) {
				UserType utype = new UserType();
				utype.setTypeId(rs.getInt("rt_id"));
				utype.setTypeName(rs.getString("rt_name"));
				utype.setCanBorrowNum(rs.getInt("rt_num"));
				utype.setDeadline(rs.getInt("rt_bdate"));
				
				utypes.add(utype);
			}
			
		} catch (Exception e) {
			System.out.println("��ȡ�����û���Ϣʧ��");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			con.close();
		}
		return utypes;
	}
	
	
	
	
}
