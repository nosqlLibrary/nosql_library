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
	 * �鿴ĳ���û��ľ�����Ϣ
	 */
	private static final long serialVersionUID = 1L;
	//private JButton closebtn = new JButton("�ر�");
	private Font font=new Font("����",Font.BOLD,18); 
	
	private JTextArea textArea = new JTextArea(5,20);
	
	private final int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;		//���ô�ֱ������
	private final int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;	//����ˮƽ������
	private JScrollPane jsp=new JScrollPane(textArea,v,h);
	private UserService us = new UserService();
	
	private JPanel panel = new JPanel() {
		
			private static final long serialVersionUID = 1L;
			//�޸����ı���ͼƬ
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
		super("�û�"+user.getUsername()+"����ϸ��Ϣ����");
		
		setSize(500,500); 
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		add(panel);
		panel.setLayout(null);
		
		jsp.setBounds(50, 60,400,300 );
		String content = "�û���ţ�" + user.getUserid() + "\r\n";
		content += "�û���:" + user.getUsername() + "\r\n";
		
		
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
		
		content += "�û�����:"+ typestr +"\r\n";
		
		
		if(gender == 'f') {
			content += "�Ա�:��\r\n";
		}else if(gender == 'm') {
			content += "�Ա�:Ů\r\n";
		}
		
		if(status == 'A') {
			content += "�û�״̬������\r\n";
		}else if(status == 'B') {
			content += "�û�״̬������\r\n";
		}else if(status == 'C') {
			content += "�û�״̬��ע��\r\n";
		}
		
		content += "���ڲ���:" + user.getDepartment() + "\r\n";
		content += "�ֻ�����:" + user.getTel() + "\r\n";
		content += "����:" + user.getEmail() + "\r\n";
		content += "�Ǽ�����:" + user.getRegisterDate()+ "\r\n";
		content += "����:" + user.getPassword()+ "\r\n";
		
		textArea.setText(content);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFont(font);
		
		
		jsp.setOpaque(false);
		panel.add(jsp);
		
		
		//����logo
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
