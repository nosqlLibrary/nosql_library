package com.library.www.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.library.www.po.User;
import com.library.www.po.UserType;
import com.library.www.service.UserService;

/**
 * 注册界面
 * @author 11247
 *
 */

@SuppressWarnings("serial")
public class RegisterFrame extends JFrame {
		//设置界面的相关组件	
		private JPanel panel;	
		private JTextField nameText,departmentText,telText,emailText;		
		private JPasswordField repassText;
		private JPasswordField passText;		
		private JButton sureBtn,cancelBtn;	
		@SuppressWarnings("rawtypes")
		private JComboBox condition = new JComboBox();
		private JLabel nameLabel,genderLabel,typeLabel,departmentLabel,telLabel,passLabel,repassLabel,emailLabel;	
		private JRadioButton jrbMale,jrbFemale;	
		private static UserService us = new UserService();
		private Font font=new Font("仿宋",Font.BOLD,18); 
		private static ArrayList<UserType> utypes = new ArrayList<>();
		static {
			
			try {
				 utypes = us.getAllUserTypes();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		//初始化界面
		@SuppressWarnings({ "unchecked" })
		public RegisterFrame() {
			super("用户注册");		
			setSize(600, 450);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocation(650,250);
			
			
			panel = new JPanel(){		//设置背景图片
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
			panel.setOpaque(false);		//设置控件透明
			add(panel);
			panel.setLayout(null);
			//设置姓名输入相关组件
			nameLabel = new JLabel("姓名：");		
			nameLabel.setBounds(170, 20, 65, 25);
			nameLabel.setFont(font);
			panel.add(nameLabel);
			nameText = new JTextField();		
			nameText.setBounds(230, 20, 150, 25);
			nameText.setOpaque(false);
			panel.add(nameText);
			
			//设置性别输入相关组件
			genderLabel = new JLabel("性别：");
			genderLabel.setFont(font);
			genderLabel.setBounds(170, 60, 65, 25);
			panel.add(genderLabel);
			jrbMale=new JRadioButton("男",true);	//设置性别中男女选择按钮，并在未选择时默认为男
			jrbMale.setFont(font);
	        jrbFemale=new JRadioButton("女");
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
			typeLabel = new JLabel("用户类型：");
			typeLabel.setBounds(140, 100,100, 25);
			typeLabel.setFont(font);
			panel.add(typeLabel);
			
			condition.setBounds(230, 100, 150, 25);
			condition.setFont(font);
			//在下拉框添加筛选条件
			condition.addItem("---请选择---");
			for (UserType utype : utypes) {
				condition.addItem(utype.getTypeName());
			}
			
			panel.add(condition);
			
			//设置所在学院输入相关组件
			departmentLabel = new JLabel("所在学院：");
			departmentLabel.setBounds(140, 140, 100, 25);
			departmentLabel.setFont(font);
			panel.add(departmentLabel);
			departmentText = new JTextField();
			departmentText.setOpaque(false);
			departmentText.setBounds(230, 140, 150, 25);
			panel.add(departmentText);
			
			//设置手机号码输入相关组件
			telLabel = new JLabel("手机号码：");
			telLabel.setBounds(140, 180, 100, 25);
			telLabel.setFont(font);
			panel.add(telLabel);
			telText = new JTextField();
			telText.setBounds(230, 180, 150, 25);
			telText.setOpaque(false);
			panel.add(telText);
			
			//设置密码输入相关组件
			passLabel = new JLabel("密码：");
			passLabel.setBounds(170, 220, 65, 25);
			passLabel.setFont(font);
			panel.add(passLabel);
			passText = new JPasswordField();	//设置密码域
			passText.setBounds(230, 220, 150, 25);
			passText.setOpaque(false);
			panel.add(passText);
			
			//设置密码输入相关组件
			repassLabel = new JLabel("确认密码：");
			repassLabel.setBounds(140, 260, 100, 25);
			repassLabel.setFont(font);
			panel.add(repassLabel);
			repassText = new JPasswordField();	//设置密码域
			repassText.setBounds(230, 260, 150, 25);
			repassText.setOpaque(false);
			panel.add(repassText);
			
			//设置邮箱输入相关组件
			emailLabel = new JLabel("邮箱：");
			emailLabel.setBounds(170, 300,100, 25);
			emailLabel.setFont(font);
			panel.add(emailLabel);
			emailText = new JTextField();
			emailText.setOpaque(false);
			emailText.setBounds(230, 300, 150, 25);
			panel.add(emailText);
			
			
			sureBtn = new JButton("确定");
	        sureBtn.setBounds(200, 340, 65, 25);
	        sureBtn.setContentAreaFilled(false);
	        sureBtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
	        sureBtn.setFont(font);
	        sureBtn.setFocusPainted(false);
	        panel.add(sureBtn);
	        ActionListener btl2 = new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent arg0) {
	        		
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
	                if(typeIndex==0) {
	                	JOptionPane.showMessageDialog(null, "请选择用户类型","提示",JOptionPane.ERROR_MESSAGE);
	        			return;
	                }
	                
	                
	        		//获取部门
	                String department = departmentText.getText();
	        		if(department.equals("")) {
	        			JOptionPane.showMessageDialog(null, "所在学院不能为空","提示",JOptionPane.ERROR_MESSAGE);
						return;
	        		}
	        		
	        		
	        		//获取电话号码
	        		String pattern = "^1[3-9]\\d{9}$";//正则表达式
	        		if(telText.getText().equals("")) {
	        			JOptionPane.showMessageDialog(null, "手机号码不能为空","提示",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}else if(!Pattern.matches(pattern, telText.getText())) {
	        			JOptionPane.showMessageDialog(null, "手机格式不正确！","提示",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}
	        		
	        		long tel = Long.parseLong(telText.getText());
	        		
	        		//获取密码
	        		String password = new String(passText.getPassword());
	        		String repwd = new String(repassText.getPassword());
	        		if(password.equals("")) {
	        			JOptionPane.showMessageDialog(null, "密码不能为空","提示",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}else if(repwd.equals("")) {
	        			JOptionPane.showMessageDialog(null, "确认密码不能为空","提示",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}else if(!repwd.equals(password)) {
	        			JOptionPane.showMessageDialog(null, "两次输入的密码不一致！","提示",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}
	        		
	        		
	        		pattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
	        		
	        		//获取邮箱
	        		String email = emailText.getText();
	        		if(email.equals("")) {
	        			JOptionPane.showMessageDialog(null, "邮箱不能为空","提示",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}else if(!Pattern.matches(pattern, email)) {
	        			JOptionPane.showMessageDialog(null, "邮箱格式不正确！","提示",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}
	        		
	        		
	        		//随机生成用户ID
	        		String userid = createData(20);
	        		
	        		//获取注册时间
	                Date date = new Date();// new Date()为获取当前系统时间
	                
	        		
	        		//构建user实体对象
	        		User user = new User(userid, username, password, gender, typeIndex, department, tel, email, date, 'A');
	        		
	        		
	        		
	        		try {
						if(us.userRegister(user))
							JOptionPane.showMessageDialog(null, "注册成功，您的用户ID为"+userid,"提示",JOptionPane.PLAIN_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "注册失败","提示",JOptionPane.ERROR_MESSAGE);
					} catch (Exception e) {
						e.printStackTrace();
					}
	        		new LoginFrame();
	    			dispose();
	        	}
	        };
	        sureBtn.addActionListener(btl2);
	        
	        cancelBtn = new JButton("取消");
	        cancelBtn.setBounds(320, 340, 65, 25);
	        cancelBtn.setContentAreaFilled(false);
	        cancelBtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
	        cancelBtn.setFont(font);
	        cancelBtn.setFocusPainted(false);
	        panel.add(cancelBtn);
	        ActionListener btl1 = new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent arg0) {
	        		int result = JOptionPane.showConfirmDialog(null, "你确定要退出注册吗？", "注册提示",JOptionPane.OK_CANCEL_OPTION);
	        		if(result == 0) {
	        			new LoginFrame();
	        			dispose();
	        		}
	        	}
	        };
			cancelBtn.addActionListener(btl1);
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
		
		//判断字符串是否全为数字
		public static boolean isInteger(String str) {  
			for(int i=str.length();--i>=0;){
		        int chr=str.charAt(i);
		        if(chr<48 || chr>57)
		            return false;
		    }
		   return true; 
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
}
