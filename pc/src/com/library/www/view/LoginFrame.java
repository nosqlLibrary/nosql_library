package com.library.www.view;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.library.www.po.Admin;
import com.library.www.po.User;
import com.library.www.service.UserService;

/**
 * 登录窗体
 * @author 11247
 *
 */
@SuppressWarnings("serial")
public class LoginFrame extends JFrame implements ActionListener {
		
		private static JPanel panel;
		private JLabel tipLabel,useridLabel,passwordLabel,genderLabel;
		private JTextField useridText;
		private JButton loginBtn,registerBtn;
		private JPasswordField passwordText;
		private JRadioButton userjrb,managerjrb;	
		private UserService userService = new UserService();
		
		
		public  LoginFrame(){
			
			super("图书管理系统");
			setSize(600, 410);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			
			panel = new JPanel() {
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
			
			add(panel);
			panel.setLayout(null);
			
			Font font=new Font("仿宋",Font.BOLD,20); 
			tipLabel = new JLabel("欢迎使用，请登录");
			tipLabel.setBounds(220,50,200,30);
			tipLabel.setFont(font);
			panel.add(tipLabel);
			
			useridLabel = new JLabel("用户ID:");
	        useridLabel.setBounds(140, 100, 80, 30);
	        useridLabel.setFont(font);
	        panel.add(useridLabel);
	        useridText = new JTextField(20);
	        useridText.setBounds(220,100,165,25);
	        useridText.setOpaque(false);
	        panel.add(useridText);
	        
	        
	        passwordLabel = new JLabel("密码:");
	        passwordLabel.setBounds(160, 140, 80, 30);
	        passwordLabel.setFont(font);
	        panel.add(passwordLabel);
	        passwordText = new JPasswordField(20);
	        passwordText.setBounds(220,140,165,25);
	        passwordText.setOpaque(false);
	        panel.add(passwordText);
	        
	        //选择以用户或管理员的身份进行登录
			genderLabel = new JLabel("请选择：");
			genderLabel.setFont(font);
			genderLabel.setBounds(140, 180, 100, 25);
			panel.add(genderLabel);
			
			userjrb=new JRadioButton("用户",true);	
			userjrb.setFont(font);
	        managerjrb=new JRadioButton("管理员");
	        managerjrb.setFont(font);
	        
	        ButtonGroup buttonGroup=new ButtonGroup();
	        buttonGroup.add(userjrb);
	        buttonGroup.add(managerjrb);
	        
	        userjrb.setBounds(215, 180, 80, 25);
	        managerjrb.setBounds(300, 180, 100, 25);
	        
	        //设置组件背景透明
	        userjrb.setOpaque(false);
	        managerjrb.setOpaque(false);
	        panel.add(userjrb);
	        panel.add(managerjrb);
	        
	        loginBtn = new JButton("登录");
	        loginBtn.setContentAreaFilled(false);
	        loginBtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
	        loginBtn.setFocusPainted(false);
	        
	        loginBtn.setFont(font);
	        loginBtn.setBounds(220, 220, 65, 25);
	        
	        panel.add(loginBtn);
	        loginBtn.addActionListener(this);
	        
	        registerBtn = new JButton("注册");
	        registerBtn.setBounds(320, 220, 65, 25);
	        registerBtn.setContentAreaFilled(false);
	        registerBtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
	        registerBtn.setFont(font);
	        registerBtn.setFocusPainted(false);
	        panel.add(registerBtn);
	        
	        registerBtn.addActionListener(this);
	        //设置logo
	        Image logo;
			try {
				logo = (Image) new ImageIcon(this.getClass().getResource("/Images/logo.png")).getImage(); 
				//logo = ImageIO.read(new File("src/images/logo.png"));
				setIconImage(logo);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			
			
	        setVisible(true);
	        
	        
		}
		
		
        public static void main(String[] args) {
        	
               new LoginFrame();
        	
		}


		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==loginBtn) {
				String userName = useridText.getText();
        		char [] password = passwordText.getPassword();
        		String userPassword = String.valueOf(password);//将char数值转化为String
        		
        		if(userName.equals("")) {
        			JOptionPane.showMessageDialog(null,"用户ID不能为空","提示",JOptionPane.ERROR_MESSAGE);
        			return;
        		}else if(userPassword.equals("")){
        			JOptionPane.showMessageDialog(null,"密码不能为空","提示",JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		//用户Id和密码都不为空之后再调用服务类 	        		
        		User userLogin = null;
        		Admin adminLogin = null;
        		
        		//获取身份
        		boolean isUser = userjrb.isSelected();
                if(isUser) {
                	//以用户身份登录
                	try {
						userLogin = userService.userLogin(userName,userPassword);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
                }else {
                	//以管理员的身份登录
                	try {
						adminLogin = userService.managerLogin(userName,userPassword);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
                }
                
                
				if(isUser) {
					//判断是否获取到数据库的内容
	        		if(userLogin.getUsername()!=null){
	        			//System.out.println(userLogin.toString());
	        			if(userLogin.getStatus()=='B') {
	        				JOptionPane.showMessageDialog(null,"您输入的用户目前处于禁用状态","提示",JOptionPane.ERROR_MESSAGE);
						}if(userLogin.getStatus()=='C') {
	        				JOptionPane.showMessageDialog(null,"您输入的用户已注销","提示",JOptionPane.ERROR_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(null, "登录成功","提示",2); 
							new UserOperateFrame(userLogin); 
		        			dispose();
						}
	        		}else {
	        			 JOptionPane.showMessageDialog(null,"您输入的用户Id、密码或身份错误","提示",JOptionPane.ERROR_MESSAGE);
	        		}
				}else {
					//判断是否获取到数据库的内容
	        		if(adminLogin.getAdminName()!=null){
	        				JOptionPane.showMessageDialog(null, "登录成功","提示",2); 
		        			new ManagerOperateFrame(adminLogin); 
		        			dispose();
					}else {
						JOptionPane.showMessageDialog(null,"您输入的用户Id、密码或身份错误","提示",JOptionPane.ERROR_MESSAGE);
					}
				}
				
    		
			}else if(e.getSource() == registerBtn) {
				new RegisterFrame();
        		dispose();
			}
			
		}
}

    
	
