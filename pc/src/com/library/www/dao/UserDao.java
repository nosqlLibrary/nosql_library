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
	 * 用户实现登录--与数据库交互
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
				//获取数据库得到的User对象各个字段的值
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
				
				//设置user的值
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
			System.out.println("用户登录失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			con.close();
		}
		return user;
	
		
	}
	/**
	 * 管理员登录实现--与数据库进行交互
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
			//执行结果集
			rs = preState.executeQuery();
			while(rs.next()) {
				//获取数据库得到的Admin对象各个字段的值
				String adminName = rs.getString("admin_name");
				
				
				//设置Admin的值
				admin.setAdminName(adminName);
				admin.setId(id);
				admin.setPassword(pwd);
				
				
			}
			
		} catch (Exception e) {
			System.out.println("管理员登录失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			con.close();
		}
		
		return admin;
	}
	
	/**
	 * 用户实现注册----与数据库交互
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean userRegister(User user) throws Exception {
		boolean result = false;
		
		con = db.getCon();
		try {
			con.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			
			//把注册时间格式转换
			Date date = new Date(System.currentTimeMillis());
			//定义sql语句
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
			
			
			//执行结果集
			int m = preState.executeUpdate();
			
			if(m!=0)
				result=true;

			con.commit();//提交JDBC事务
			con.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
			
		} catch (Exception e) {
			con.rollback();//回滚JDBC事务
			System.out.println("注册失败");
			e.printStackTrace();
			
		}finally {
			preState.close();	
			con.close();
		}
		return result;
		
	}
	
	/**
	 * 修改数据库中管理员的密码
	 * @param admin
	 * @return
	 * @throws Exception
	 */
	public boolean changeManagerPwd(Admin admin) throws Exception {
		boolean result = false;
		
		con = db.getCon();
		try {
			con.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			//定义sql语句
			String sql = "update admin_table set admin_pwd = ? where admin_id = ?";
			
			preState = con.prepareStatement(sql);
			preState.setString(1, admin.getPassword());
			preState.setString(2, admin.getId());
			//执行结果集
			int m = preState.executeUpdate();
			
			if(m!=0)
				result=true;
			con.commit();//提交JDBC事务
			con.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
			
		} catch (Exception e) {
			con.rollback();//回滚JDBC事务
			System.out.println("修改密码失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			con.close();
		}
		return result;
	}
	
	/**
	 * 根据读者的id获取到读者的基本信息
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public User getUserMsgByID(String userid) throws Exception {
		User user = new User();
		
		con = db.getCon();
		try {
			//定义sql语句
			String sql = "select * from reader where r_id =?";
			preState = con.prepareStatement(sql);
			preState.setString(1, userid);
			//执行结果集
			rs = preState.executeQuery();
			while(rs.next()) {
				//获取数据库得到的User对象各个字段的值
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
				
				//设置user的值
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
			System.out.println("管理员登录失败");
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
			con.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			
			//定义sql语句
			String sql = "update reader set r_status = 'C' where r_id = ?" ;
			preState = con.prepareStatement(sql);
			preState.setString(1, u.getUserid());
			//执行结果集
			int m = preState.executeUpdate();
			System.out.println(sql);
			if(m!=0)
				result=true;
			con.commit();//提交JDBC事务
			con.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
			
		} catch (Exception e) {
			con.rollback();//回滚JDBC事务
			System.out.println("用户注销失败");
			e.printStackTrace();
			
		}finally {
			preState.close();	
			con.close();
		}
		return result;
	}
	
	/**
	 * 用户自己修改自己的用户信息   从数据库中修改
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean updateUserMsg(User user) throws Exception {
		boolean result = false;
		
		con = db.getCon();
		try {
			con.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			
			//定义sql语句
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
			
			//执行结果集
			int m = preState.executeUpdate();
			
			if(m!=0)
				result=true;
			con.commit();//提交JDBC事务
			con.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
			
		} catch (Exception e) {
			con.rollback();//回滚JDBC事务
			System.out.println("用户修改信息失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			con.close();
		}
		return result;
		
	}
	
	/**
	 * 根据给出的条件，在数据库进行模糊查询是否存在用户信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public ArrayList<User> getUserByCondition(String condition,int page) throws Exception {
		ArrayList<User> users = new ArrayList<>();
		
		try {
			con = db.getCon();
			
			//定义sql语句
			String sql = "SELECT * from reader,readertype where ( r_id like ? or r_na like ? or r_col like "
					+ "? or  rt_name like ?) and reader.r_type = readertype.rt_id limit ?,20";
							
			preState = con.prepareStatement(sql);
			preState.setString(1, "%" + condition + "%");
			preState.setString(2, "%" + condition + "%");
			preState.setString(3, "%" + condition + "%");
			preState.setString(4, "%" + condition + "%");
			preState.setInt(5, (page -1)*20);
			
			//执行结果集
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
			System.out.println("根据条件获取用户信息失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			con.close();
		}
		return users;
	}
	
	/**
	 * 管理员从数据库中获取所有用户的信息
	 * @return
	 * @throws Exception
	 */
	public ArrayList<User> getAllUsers(int page) throws Exception {
		ArrayList<User> users = new ArrayList<>();
		
		try {
			con = db.getCon();
			
			//定义sql语句
			String sql = "SELECT * from reader limit ?,20";
			preState = con.prepareStatement(sql);
			preState.setInt(1, (page - 1)*20);
			//执行结果集
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
			System.out.println("获取所有用户信息失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			con.close();
		}
		return users;
	}
	
	/**
	 * 根据用户编号删除数据库中相应的用户记录
	 * 要先删除借阅表中的记录，再删除用户表的记录
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public boolean deleteUserById(String userid) throws Exception {
		boolean result = false;
		
		con = db.getCon();
		
		try {
			con.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			//定义sql语句
			String sql2 = "delete from borrow where r_id = '" + userid +"'";
			preState = con.prepareStatement(sql2);
			preState.setString(1, userid);
			//执行结果集
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
			con.commit();//提交JDBC事务
			con.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
			
		} catch (Exception e) {
			con.rollback();//回滚JDBC事务
			System.out.println("删除用户失败");
			e.printStackTrace();
			
		}finally {
			preState.close();	
			con.close();
		}
		return result;
	}
	
	/**
	 * 在数据库中添加新管理员的信息
	 * @param newadmin
	 * @return
	 * @throws Exception
	 */
	public boolean addNewAdmin(Admin newadmin) throws Exception {
		boolean result = false;
		
		con = db.getCon();
		
		try {
			con.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			
			//定义sql语句
			String sql = "insert into admin_table values(?,?,?)" ;
			
			preState = con.prepareStatement(sql);
			preState.setString(1, newadmin.getId());
			preState.setString(2, newadmin.getAdminName());
			preState.setString(3, newadmin.getPassword());
			//执行结果集
			int m = preState.executeUpdate();
			
			
			if(m!=0)
				result=true;
			con.commit();//提交JDBC事务
			con.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
			
		} catch (Exception e) {
			con.rollback();
			System.out.println("注册管理员失败");
			e.printStackTrace();
			
		}finally {
			preState.close();	
			con.close();
		}
		return result;
	}
	
	/**
	 * 获取数据库中所有的读者类型
	 * @return
	 * @throws Exception
	 */
	public ArrayList<UserType> getAllUserTypes() throws Exception {
		ArrayList<UserType> utypes = new ArrayList<>();
		
		try {
			con = db.getCon();
			
			//定义sql语句
			String sql = "SELECT * from readertype";
			preState = con.prepareStatement(sql);
			//执行结果集
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
			System.out.println("获取所有用户信息失败");
			e.printStackTrace();
			
		}finally {
			preState.close();
			rs.close();
			con.close();
		}
		return utypes;
	}
	
	
	
	
}
