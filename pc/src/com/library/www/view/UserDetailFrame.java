package com.library.www.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import com.library.www.po.User;
import com.library.www.po.UserType;
import com.library.www.service.UserService;

public class UserDetailFrame extends JFrame {

	/**
	 * 查看某个用户的具体信息
	 */
	private static final long serialVersionUID = 1L;
	//private JButton closebtn = new JButton("关闭");
	private Font font=new Font("仿宋",Font.BOLD,18); 
	
	private JTextArea textArea = new JTextArea(5,20);
	
	private final int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;		//设置垂直滚动条
	private final int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;	//设置水平滚动条
	private JScrollPane jsp=new JScrollPane(textArea,v,h);
	private UserService us = new UserService();
	
	private JPanel panel = new JPanel() {
		
			private static final long serialVersionUID = 1L;
			//修改面板的背景图片
			protected void paintComponent(Graphics g) {  
	            Image bg;  
	            try {  
	                bg = (Image) new ImageIcon(this.getClass().getResource("/Images/bopbg.jpg")).getImage(); 
	                g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
		
	};

	public UserDetailFrame(User user) {
		super("用户"+user.getUsername()+"的详细信息界面");
		
		setSize(500,500); 
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		add(panel);
		panel.setLayout(null);
		
		jsp.setBounds(50, 60,400,300 );
		String content = "用户编号：" + user.getUserid() + "\r\n";
		content += "用户名:" + user.getUsername() + "\r\n";
		
		
		char gender = user.getGender();
		char status = user.getStatus();
		
		ArrayList<UserType> utypes = new ArrayList<>();
		try {
			 utypes = us.getAllUserTypes();
		} catch (Exception e4) {
			e4.printStackTrace();
		}
    	int type = user.getType();
    	String typestr = "";
    	for (UserType utype : utypes) {
			if(type == utype.getTypeId()) {
				typestr = utype.getTypeName();
			}
		}
		
		content += "用户类型:"+ typestr +"\r\n";
		
		
		if(gender == 'f') {
			content += "性别:男\r\n";
		}else if(gender == 'm') {
			content += "性别:女\r\n";
		}
		
		if(status == 'A') {
			content += "用户状态：正常\r\n";
		}else if(status == 'B') {
			content += "用户状态：禁用\r\n";
		}else if(status == 'C') {
			content += "用户状态：注销\r\n";
		}
		
		content += "所在部门:" + user.getDepartment() + "\r\n";
		content += "手机号码:" + user.getTel() + "\r\n";
		content += "邮箱:" + user.getEmail() + "\r\n";
		content += "登记日期:" + user.getRegisterDate()+ "\r\n";
		content += "密码:" + user.getPassword()+ "\r\n";
		
		textArea.setText(content);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFont(font);
		
		
		jsp.setOpaque(false);
		panel.add(jsp);
		
		
		//设置logo
        Image logo;
		try {
			logo = (Image) new ImageIcon(this.getClass().getResource("/Images/logo.png")).getImage(); 
			setIconImage(logo);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		setVisible(true);
		
	}

}
