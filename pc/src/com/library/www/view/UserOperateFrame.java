package com.library.www.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.library.www.po.User;
import com.library.www.service.BorrowService;
import com.library.www.service.UserService;

@SuppressWarnings("serial")
public class UserOperateFrame extends JFrame {

	/**
	 * 用户登录之后的操作界面
	 */
	private UserBookOperateJPanel userBookOperateJPanel;
	private UserBorrowMsgJPanel userBorrowMsgJPanel;
	private EditUserMsgJPanel editUserMsgJPanel;
	
	private Font font=new Font("仿宋",Font.BOLD,14); 
	private UserService us = new UserService();
	private BorrowService bs = new BorrowService();
	private User u = new User();
	private JMenuBar mb;//定义菜单栏
	private JMenu function, exit, help,defaultPage;//定义主菜单
	private JMenuItem search, watchmsg, edituser, logout,exitItem,defaultItem;//定义菜单选项
	private JPanel menupanel = new JPanel();
	private JPanel bottompanel = new JPanel() {
			
			private static final long serialVersionUID = 1L;
			//修改右边面板的背景图片
			protected void paintComponent(Graphics g) {  
	            Image bg;  
	            try {  
	                bg = (Image) new ImageIcon(this.getClass().getResource("/Images/background.jpg")).getImage(); 
	                g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
		
	};
	
	private Container contentPane ;
	
    public UserOperateFrame(User user) {
        super("用户操作界面："+user.getUsername() + "，您好");
        
        setSize(1015,620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.u = user;
        
        contentPane = getContentPane();
        contentPane.setLayout(null);
        //将面板放置到容器中
        contentPane.add(menupanel);//菜单面板
        contentPane.add(bottompanel);//初始化界面
        //添加主面板
        menupanel.setLayout(null);
        menupanel.setBounds(0, 0, 1015, 20);
        bottompanel.setBounds(0,20,1015,600);
        userBookOperateJPanel = new UserBookOperateJPanel(this.u);
        userBookOperateJPanel.setVisible(false);
        
        userBorrowMsgJPanel = new UserBorrowMsgJPanel(this.u);
        userBorrowMsgJPanel.setVisible(false);
        
        editUserMsgJPanel = new EditUserMsgJPanel(this.u);
        editUserMsgJPanel.setVisible(false);
        
        search = new JMenuItem("查询图书"); 
        watchmsg = new JMenuItem("查看借阅信息");
        edituser = new JMenuItem("修改用户信息");
        logout = new JMenuItem("用户注销");
        exitItem = new JMenuItem("退出系统");
        defaultItem = new JMenuItem("回到首页");
        
        search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				userBookOperateJPanel.setVisible(true);
				userBorrowMsgJPanel.setVisible(false);
				bottompanel.setVisible(false);
				editUserMsgJPanel.setVisible(false);
				userBookOperateJPanel.setBounds(0,20,1015,600);
				contentPane.add(userBookOperateJPanel);
				
			}
		});
        
        watchmsg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				userBorrowMsgJPanel.setVisible(true);
				bottompanel.setVisible(false);
				userBookOperateJPanel.setVisible(false);
				editUserMsgJPanel.setVisible(false);
				
				userBorrowMsgJPanel.setBounds(0,20, 1015, 600);
				contentPane.add(userBorrowMsgJPanel);
			}
		});
        
        edituser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editUserMsgJPanel.setVisible(true);
				userBorrowMsgJPanel.setVisible(false);
				bottompanel.setVisible(false);
				userBookOperateJPanel.setVisible(false);
				userBorrowMsgJPanel.setBounds(0, 20, 1015, 600);
				contentPane.add(editUserMsgJPanel);
				
			}
		});
        
        defaultItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				userBookOperateJPanel.setVisible(false);
				userBorrowMsgJPanel.setVisible(false);
				bottompanel.setVisible(true);
				userBookOperateJPanel.setVisible(false);
				
			}
		});
        
        logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
        		int n = JOptionPane.showConfirmDialog(null, "确定要注销该用户吗?", "提示",JOptionPane.YES_NO_OPTION);
        		if(n==0) {
        			//检查用户是否已归还所有图书  默认还有图书未归还
        			boolean result = false;
        			try {
        				result = bs.checkIsAllBack(u);
        				
        				if(result) {
        					boolean logOut = us.logOut(u);
        					//符合条件才允许注销
    	        			if(logOut) {
    	        				//注销成功
    	        				JOptionPane.showMessageDialog(null, "注销成功！", "提示", JOptionPane.PLAIN_MESSAGE);
    	        				dispose();//将当前页面关闭
    			        		new LoginFrame();//跳转界面
    	        			}else {
    	        				//注销失败
    	        				JOptionPane.showMessageDialog(null, "注销失败！", "提示", JOptionPane.ERROR_MESSAGE);
    	        			}
        				}else {
        					//提示要先归还所有图书
        					JOptionPane.showMessageDialog(null, "请先归还尚未归还的图书！", "提示", JOptionPane.ERROR_MESSAGE);
        				}
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}
        			
        		}
			}
		});
        
        exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
        
        mb = new JMenuBar();
        function = new JMenu("系统功能");
        exit = new JMenu("退出系统");
        help = new JMenu("帮助");
        defaultPage = new JMenu("首页");
        
        search.setFont(font);
        watchmsg.setFont(font);
        edituser.setFont(font);
        logout.setFont(font);
        exitItem.setFont(font);
        defaultItem.setFont(font);
        
        function.add(search);
        function.add(watchmsg);
        function.add(edituser);
        function.add(logout);
        exit.add(exitItem);
        defaultPage.add(defaultItem);
        
        
        mb.add(defaultPage);
        mb.add(function);
        mb.add(exit);
        mb.add(help);
        function.setFont(font);
        exit.setFont(font);
        help.setFont(font);
        defaultPage.setFont(font);
        
        mb.setBackground(new Color(175,238,238));
        //添加框线
        mb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mb.setBounds(0, 0, 1014, 20);
        menupanel.add(mb);
//        //里边可以查看统计信息、借还书籍
//		searchbtn = new JButton("查询图书");
//		searchbtn.setBounds(120, 90, 140, 30);
//		searchbtn.setContentAreaFilled(false);
//		searchbtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
//		searchbtn.setFont(font);
//		searchbtn.setFocusPainted(false);
//		
//		ActionListener actionlistener1 = new ActionListener() {		
//	        	@Override
//	        	public void actionPerformed(ActionEvent arg0) {
//	        		dispose();//将当前页面关闭
//	        		new UserBookOperateFrame(user);//跳转界面
//	        	}
//		};
//	
//		searchbtn.addActionListener(actionlistener1);
//		
//		messagebtn = new JButton("查看借阅信息");
//		messagebtn.setBounds(120, 150, 140, 30);
//		messagebtn.setContentAreaFilled(false);
//		messagebtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
//		messagebtn.setFont(font);
//		messagebtn.setFocusPainted(false);
//		
//		ActionListener actionlistener2 = new ActionListener() {		
//	        	@Override
//	        	public void actionPerformed(ActionEvent arg0) {
//	        		dispose();//将当前页面关闭
//	        		new UserBorrowMsgFrame(user);//跳转界面
//	        	}
//		};
//	
//		messagebtn.addActionListener(actionlistener2);
//		
//		changeUserMsgbtn = new JButton("修改用户信息");
//		changeUserMsgbtn.setBounds(120, 210, 140, 30);
//		changeUserMsgbtn.setContentAreaFilled(false);
//		changeUserMsgbtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
//		changeUserMsgbtn.setFont(font);
//		changeUserMsgbtn.setFocusPainted(false);
//		
//		ActionListener actionlistener3 = new ActionListener() {		
//	        	@Override
//	        	public void actionPerformed(ActionEvent arg0) {
//	        		dispose();//将当前页面关闭
//	        		new EditUserMsgFrame(user);//跳转界面
//	        	}
//		};
//	
//		changeUserMsgbtn.addActionListener(actionlistener3);
//		
//		logOutbtn = new JButton("用户注销");
//		logOutbtn.setBounds(120, 270, 140, 30);
//		logOutbtn.setContentAreaFilled(false);
//		logOutbtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
//		logOutbtn.setFont(font);
//		logOutbtn.setFocusPainted(false);
//		
//		ActionListener actionlistener4 = new ActionListener() {		
//	        	
//				@Override
//	        	public void actionPerformed(ActionEvent arg0) {
//	        		int n = JOptionPane.showConfirmDialog(null, "确定要注销该用户吗?", "提示",JOptionPane.YES_NO_OPTION);
//	        		if(n==0) {
//	        			//检查用户是否已归还所有图书  默认还有图书未归还
//	        			boolean result = false;
//	        			try {
//	        				result = bs.checkIsAllBack(u);
//	        				
//	        				if(result) {
//	        					boolean logOut = us.logOut(u);
//	        					//符合条件才允许注销
//	    	        			if(logOut) {
//	    	        				//注销成功
//	    	        				JOptionPane.showMessageDialog(null, "注销成功！", "提示", JOptionPane.PLAIN_MESSAGE);
//	    	        				dispose();//将当前页面关闭
//	    			        		new LoginFrame();//跳转界面
//	    	        			}else {
//	    	        				//注销失败
//	    	        				JOptionPane.showMessageDialog(null, "注销失败！", "提示", JOptionPane.ERROR_MESSAGE);
//	    	        			}
//	        				}else {
//	        					//提示要先归还所有图书
//	        					JOptionPane.showMessageDialog(null, "请先归还尚未归还的图书！", "提示", JOptionPane.ERROR_MESSAGE);
//	        				}
//							
//						} catch (Exception e) {
//							
//							e.printStackTrace();
//						}
//	        			
//	        			
//	        		}
//	        		
//	        	}
//		};
//	
//		logOutbtn.addActionListener(actionlistener4);
//		
//		exitbtn = new JButton("退出登录");
//		exitbtn.setBounds(120, 330, 140, 30);
//		exitbtn.setContentAreaFilled(false);
//		exitbtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
//		exitbtn.setFont(font);
//		exitbtn.setFocusPainted(false);
//		
//		ActionListener actionlistener5 = new ActionListener() {		
//	        	@Override
//	        	public void actionPerformed(ActionEvent arg0) {
//	        		int n = JOptionPane.showConfirmDialog(null, "确定要退出登录吗?", "提示",JOptionPane.YES_NO_OPTION);
//	        		if(n==0) {
//	        			dispose();//将当前页面关闭
//		        		new LoginFrame();//跳转界面
//	        		}
//	        		
//	        	}
//		};
//	
//		exitbtn.addActionListener(actionlistener5);
//		
//		//创建标签
//		tipLabel =  new JLabel("请选择一个功能进行操作：");
//		tipLabel.setFont(font);
//        tipLabel.setBounds(80, 30, 400, 30);
//        
//        panel.add(searchbtn);
//        panel.add(messagebtn);
//        panel.add(changeUserMsgbtn);
//        panel.add(tipLabel);
//        panel.add(logOutbtn);
//        panel.add(exitbtn);
//        
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
