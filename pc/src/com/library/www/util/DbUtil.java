package com.library.www.util;
import java.sql.*;


public class DbUtil{
	
	//定义连接数据库的属性
	private String dbUrl="jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT";
	private String dbUserName ="root";
	private String dbPassword = "12345";
	private String jdbcName="com.mysql.cj.jdbc.Driver";
	
	//获取connection
	public Connection getCon() throws Exception{
		Connection con=null;
	    try {
			Class.forName(jdbcName);
			//System.out.println(dbUserName+dbPassword);
			con = (Connection)DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
			//System.out.println("数据库连接成功");
			
		}  catch (SQLException e) {
        	System.out.println("数据库连接失败");
            e.printStackTrace();
        }
	    return con;
	}
	
	//关闭connection
	public void closeCon (java.sql.Connection con)throws Exception {
		if(con!=null){
			con.close();
		}
	}
   

}

