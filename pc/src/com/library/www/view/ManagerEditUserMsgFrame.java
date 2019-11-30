package com.library.www.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.library.www.po.User;
import com.library.www.po.UserType;
import com.library.www.service.UserService;

public class ManagerEditUserMsgFrame extends JFrame implements ActionListener {


	/**
	 * 管理员修改某个用户信息
	 */
	private static final long serialVersionUID = 1L;
	private JButton closebtn = new JButton("关闭");
	private JButton submitbtn = new JButton("提交");
	private Font font=new Font("仿宋",Font.BOLD,18);
	private JLabel nameLabel,sexLabel,departmentLabel,telLabel,emailLabel,typeLablel,statusLabel;
	private JTextField nameText = new JTextField();
	private JTextField departmentText = new JTextField();
	private JTextField telText = new JTextField();
	private JTextField emailText = new JTextField();
	
	private JRadioButton jrbMale,jrbFemale;	
	@SuppressWarnings("rawtypes")
	private JComboBox condition = new JComboBox();
	@SuppressWarnings("rawtypes")
	private JComboBox status = new JComboBox();
	@SuppressWarnings("serial")
	private JPanel panel = new JPanel(){		//设置背景图片
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
	
	
	private User user;//记录用户要修改的用户信息
	private UserService us = new UserService();
	private ArrayList<UserType> utypes = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public ManagerEditUserMsgFrame(User user) {
		super("修改"+user.getUsername()+"用户信息的界面" );		
		setSize(600, 460);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		this.user = user;
		
		panel.setOpaque(false);		//设置控件透明
		add(panel);
		panel.setLayout(null);
		//设置姓名输入相关组件
		nameLabel = new JLabel("姓名：");		
		nameLabel.setBounds(170, 20, 65, 25);
		nameLabel.setFont(font);
		panel.add(nameLabel);
			
		nameText.setBounds(230, 20, 150, 25);
		nameText.setOpaque(false);
		nameText.setText(user.getUsername());
		panel.add(nameText);
		
		//设置性别输入相关组件
		sexLabel = new JLabel("性别：");
		sexLabel.setFont(font);
		sexLabel.setBounds(170, 60, 65, 25);
		panel.add(sexLabel);
		
		if(user.getGender() == 'f') {
			jrbMale=new JRadioButton("男",true);	
	        jrbFemale=new JRadioButton("女");
		}else {
			jrbMale=new JRadioButton("男");	
	        jrbFemale=new JRadioButton("女",true);
		}
		
		
		
		jrbMale.setFont(font);
		jrbFemale.setFont(font);
        ButtonGroup buttonGroup=new ButtonGroup();
        buttonGroup.add(jrbMale);
        jrbMale.setBounds(230, 60, 50, 25);
        jrbFemale.setBounds(300, 60, 50, 25);
        buttonGroup.add(jrbFemale);
        //设置组件背景透明
        jrbMale.setOpaque(false);
        jrbFemale.setOpaque(false);
        
       
        
        panel.add(jrbMale);
        panel.add(jrbFemale);
        
        //设置用户类型相关组件
		typeLablel = new JLabel("用户类型：");
		typeLablel.setBounds(140, 100,100, 25);
		typeLablel.setFont(font);
		panel.add(typeLablel);
		
		condition.setBounds(230, 100, 150, 25);
		condition.setFont(font);
		
		try {
			 utypes = us.getAllUserTypes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//在下拉框添加筛选条件
		condition.addItem("当前："+user.getType());
		for (UserType utype : utypes) {
			condition.addItem(utype.getTypeName());
		}
		
		panel.add(condition);
		
		
        
        //设置所在学院输入相关组件
		departmentLabel = new JLabel("所在学院：");
		departmentLabel.setBounds(140, 140, 100, 25);
		departmentLabel.setFont(font);
		panel.add(departmentLabel);
		
		departmentText.setOpaque(false);
		departmentText.setBounds(230, 140, 150, 25);
		departmentText.setText(user.getDepartment());
		panel.add(departmentText);
		
		//设置手机号码输入相关组件
		telLabel = new JLabel("手机号码：");
		telLabel.setBounds(140, 180, 100, 25);
		telLabel.setFont(font);
		panel.add(telLabel);
		
		telText.setBounds(230, 180, 150, 25);
		telText.setOpaque(false);
		telText.setText(user.getTel()+"");
		panel.add(telText);
		
		
		//设置用户状态
		statusLabel = new JLabel("用户状态：");
		statusLabel.setBounds(140, 220,100, 25);
		statusLabel.setFont(font);
		panel.add(statusLabel);
		
		status.setBounds(230, 220, 150, 25);
		status.setFont(font);
		//在下拉框添加筛选条件
		status.addItem("---请选择---");
		status.addItem("正常");
		status.addItem("禁用");
		status.addItem("注销");
		if(user.getStatus()=='A') {
			status.setSelectedIndex(1);;
		}else if(user.getStatus() == 'B') {
			status.setSelectedIndex(2);
		}else if(user.getStatus() == 'C') {
			status.setSelectedIndex(3);
		}
		panel.add(status);
		
		
		
		//设置邮箱输入相关组件
		emailLabel = new JLabel("邮箱：");
		emailLabel.setBounds(170, 260,100, 25);
		emailLabel.setFont(font);
		panel.add(emailLabel);
		
		emailText.setOpaque(false);
		emailText.setBounds(230, 260, 150, 25);
		emailText.setText(user.getEmail());
		panel.add(emailText);
		
		
		submitbtn = new JButton("确定");
        submitbtn.setBounds(200, 300, 65, 25);
        submitbtn.setContentAreaFilled(false);
        submitbtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
        submitbtn.setFont(font);
        submitbtn.addActionListener(this);
        submitbtn.setFocusPainted(false);
        panel.add(submitbtn);
        
        closebtn = new JButton("取消");
        closebtn.setBounds(320, 300, 65, 25);
        closebtn.setContentAreaFilled(false);
        closebtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
        closebtn.setFont(font);
        closebtn.addActionListener(this);
        closebtn.setFocusPainted(false);
        panel.add(closebtn);
		
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


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==submitbtn) {
			
			
			//获取用户名
    		String username = nameText.getText();
    		if(username.equals("")) {
    			JOptionPane.showMessageDialog(null, "用户名不能为空","提示",JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		
    		//获取性别
    		boolean man = jrbMale.isSelected();
            char gender = 'f';
            if(!man)
            	gender = 'm';
            
            //获取用户类型  
            //获取选中的用户类型
            int typeIndex = condition.getSelectedIndex();
            int type = 0;
            if(typeIndex == 0)
            	type = user.getType();
            else
            	type = this.utypes.get(typeIndex-1).getTypeId();
            
            
    		//获取部门
            String department = departmentText.getText();
    		if(department.equals("")) {
    			JOptionPane.showMessageDialog(null, "所在学院不能为空","提示",JOptionPane.ERROR_MESSAGE);
				return;
    		}
    		
    		
    		//获取电话号码
    		if(telText.getText().equals("")) {
    			JOptionPane.showMessageDialog(null, "手机号码不能为空","提示",JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		long tel = 0 ;
    		if(isInteger(telText.getText())) {
    			if(telText.getText().length() ==11)
    				tel = Long.parseLong(telText.getText());
    			else {
    				JOptionPane.showMessageDialog(null, "手机应为11位数字","提示",JOptionPane.ERROR_MESSAGE);
        			return;
    			}
    		}else{
    			JOptionPane.showMessageDialog(null, "手机应为11位数字！","提示",JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		
    		//获取用户状态 
            //获取选中的用户状态
            int statusIndex = status.getSelectedIndex();
            char status = ' ';
            if(statusIndex==0) {
            	JOptionPane.showMessageDialog(null, "请选择用户类型","提示",JOptionPane.ERROR_MESSAGE);
    			return;
            }else if(statusIndex==1) {
            	status = 'A';
            }else if(statusIndex==2) {
            	status = 'B';
            }else if(statusIndex==3) {
            	status = 'C';
            }
    		
    		//获取邮箱
    		String email = emailText.getText();
    		if(email.equals("")) {
    			JOptionPane.showMessageDialog(null, "邮箱不能为空","提示",JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		
    		
    		this.user.setUsername(username);
    		this.user.setDepartment(department);
    		this.user.setTel(tel);
    		this.user.setType(type);
    		
    		this.user.setStatus(status);
    		this.user.setEmail(email);
    		this.user.setGender(gender);
    		
    		
    		boolean result = false;
    		try {
				result = us.updateUserMsg(this.user);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    		
    		
    		if(result) {
    			JOptionPane.showMessageDialog(null, "修改用户信息成功！","提示",JOptionPane.PLAIN_MESSAGE);
    		}else {
    			
    			JOptionPane.showMessageDialog(null, "修改用户信息失败！","提示",JOptionPane.WARNING_MESSAGE);
    		}
    		
            
		}else if(e.getSource() == closebtn) {
			dispose();	
		}
		
	}

	//判断字符串是否全为数字
	public static boolean isInteger(String str) {  
		for(int i=str.length();--i>=0;){
	        int chr=str.charAt(i);
	        if(chr<48 || chr>57)
	            return false;
	    }
	   return true; 
	}
		
	
}
