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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.library.www.po.Admin;
import com.library.www.service.UserService;

@SuppressWarnings("serial")
public class ManagerOperateFrame extends JFrame {

	/**
	 * 管理员登录之后的操作界面
	 */
	
	private BookOperateJPanel bookOperateJPanel;
	private Container contentPane;
	private ErrorRegisterJPanel errorRegisterJPanel;
	private AddManagerJPanel addManagerJPanel;
	private AddBookJPanel addBookJPanel;
	private BorrowDetailJPanel borrowDetailJPanel;
	private AllUserJPanel allUserJPanel;
	private BorrowOperateJPanel borrowOperateJPanel;
	private JPanel menuPanel = new JPanel();
	private Font font=new Font("仿宋",Font.BOLD,14); 
	private UserService us = new UserService();
	
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
	
	
	private JMenuBar mb;//定义菜单栏
	private JMenu function, exit, help,defaultPage,account;//定义主菜单
	private JMenuItem search, addBook, errorlogin,watchborrow,alluser,
					register,editpwd, takeop,exitItem,defaultItem;//定义菜单选项
	
    public ManagerOperateFrame(Admin admin) {
        super("管理员操作界面："+admin.getAdminName() + "，您好");
        setSize(1015,620);
        setLocationRelativeTo(null);      
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        contentPane = getContentPane();
        contentPane.setLayout(null);
        
        //将面板放置到容器中
        contentPane.add(menuPanel);//菜单面板
        contentPane.add(bottompanel);//初始化界面
        //添加主面板
        menuPanel.setLayout(null);
        menuPanel.setBounds(0, 0, 1015, 20);
        bottompanel.setBounds(0,20,1015,600);
        
        bookOperateJPanel = new BookOperateJPanel();
        bookOperateJPanel.setVisible(false);
        bookOperateJPanel.setBounds(0, 20,1015, 600);
        contentPane.add(bookOperateJPanel);
        
        errorRegisterJPanel = new ErrorRegisterJPanel();
        errorRegisterJPanel.setVisible(false);
        errorRegisterJPanel.setBounds(0, 20,1015, 600);
        contentPane.add(errorRegisterJPanel);
        
        addManagerJPanel = new AddManagerJPanel();
        addManagerJPanel.setVisible(false);
        addManagerJPanel.setBounds(0, 20,1015, 600);
        contentPane.add(addManagerJPanel);
        
        addBookJPanel = new AddBookJPanel();
        addBookJPanel.setVisible(false);
        addBookJPanel.setBounds(0, 20,1015, 600);
        contentPane.add(addBookJPanel);
        
        borrowDetailJPanel = new BorrowDetailJPanel();
        borrowDetailJPanel.setVisible(false);
        borrowDetailJPanel.setBounds(0, 20,1015, 600);
        contentPane.add(borrowDetailJPanel);
        
        allUserJPanel = new AllUserJPanel();
        allUserJPanel.setVisible(false);
        allUserJPanel.setBounds(0, 20,1015, 600);
        contentPane.add(allUserJPanel);
        
        borrowOperateJPanel = new BorrowOperateJPanel();
        borrowOperateJPanel.setVisible(false);
        borrowOperateJPanel.setBounds(0, 20,1015, 600);
        contentPane.add(borrowOperateJPanel);
        
        search = new JMenuItem("查询图书"); 
        addBook = new JMenuItem("增添新图书");
        errorlogin = new JMenuItem("图书异常登记");
        watchborrow = new JMenuItem("查看借阅信息");
        alluser = new JMenuItem("查看所有用户");
		register = new JMenuItem("注册新管理员");
		editpwd = new JMenuItem("修改密码");
		takeop = new JMenuItem("用户借还操作");
        exitItem = new JMenuItem("退出系统");
        defaultItem = new JMenuItem("回到首页");
        
        search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 bookOperateJPanel.setVisible(true);
				 bottompanel.setVisible(false);
				 errorRegisterJPanel.setVisible(false);
				 addManagerJPanel.setVisible(false);
				 addBookJPanel.setVisible(false);
				 borrowDetailJPanel.setVisible(false);
				 allUserJPanel.setVisible(false);
				 borrowOperateJPanel.setVisible(false);
			}
		});
        
        errorlogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				errorRegisterJPanel.setVisible(true);
				bookOperateJPanel.setVisible(false);
				bottompanel.setVisible(false);
				addManagerJPanel.setVisible(false);
				addBookJPanel.setVisible(false);
				borrowDetailJPanel.setVisible(false);
				allUserJPanel.setVisible(false);
				borrowOperateJPanel.setVisible(false);
			}
		});
        
        register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addManagerJPanel.setVisible(true);
				errorRegisterJPanel.setVisible(false);
				bookOperateJPanel.setVisible(false);
				bottompanel.setVisible(false);
				addBookJPanel.setVisible(false);
				borrowDetailJPanel.setVisible(false);
				allUserJPanel.setVisible(false);
				borrowOperateJPanel.setVisible(false);
			}
		});
        
        defaultItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bookOperateJPanel.setVisible(false);
				bottompanel.setVisible(true);
				errorRegisterJPanel.setVisible(false);
				addManagerJPanel.setVisible(false);
				addBookJPanel.setVisible(false);
				borrowDetailJPanel.setVisible(false);
				allUserJPanel.setVisible(false);
				borrowOperateJPanel.setVisible(false);
			}
		});
        

        addBook.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bookOperateJPanel.setVisible(false);
				bottompanel.setVisible(false);
				errorRegisterJPanel.setVisible(false);
				addManagerJPanel.setVisible(false);
				addBookJPanel.setVisible(true);
				borrowDetailJPanel.setVisible(false);
				allUserJPanel.setVisible(false);
				borrowOperateJPanel.setVisible(false);
			}
		});
        
        watchborrow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				bookOperateJPanel.setVisible(false);
				bottompanel.setVisible(false);
				errorRegisterJPanel.setVisible(false);
				addManagerJPanel.setVisible(false);
				addBookJPanel.setVisible(false);
				borrowDetailJPanel.setVisible(true);
				allUserJPanel.setVisible(false);
				borrowOperateJPanel.setVisible(false);
			}
		});
        
        
        alluser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bookOperateJPanel.setVisible(false);
				bottompanel.setVisible(false);
				errorRegisterJPanel.setVisible(false);
				addManagerJPanel.setVisible(false);
				addBookJPanel.setVisible(false);
				borrowDetailJPanel.setVisible(false);
				allUserJPanel.setVisible(true);
				borrowOperateJPanel.setVisible(false);
			}
		});
        
        takeop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				bookOperateJPanel.setVisible(false);
				bottompanel.setVisible(false);
				errorRegisterJPanel.setVisible(false);
				addManagerJPanel.setVisible(false);
				addBookJPanel.setVisible(false);
				borrowDetailJPanel.setVisible(false);
				allUserJPanel.setVisible(false);
				borrowOperateJPanel.setVisible(true);
				
			}
		});
        
        editpwd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel manPanel = new JPanel();
				JPasswordField oldpwdField = new JPasswordField(10);
				
				JPasswordField newpwdField = new JPasswordField(10);
				JPasswordField repwdField = new JPasswordField(10);
			    manPanel.add(new JLabel("旧密码:"));
			    manPanel.add(oldpwdField);
			    
			    manPanel.add(new JLabel("新密码:"));
			    manPanel.add(newpwdField);
			    //manPanel.add(Box.createHorizontalStrut(15)); // a spacer
			    manPanel.add(new JLabel("重复密码:"));
			    manPanel.add(repwdField);
	
			    int result = JOptionPane.showConfirmDialog(null, manPanel,
			        "请输入相关信息：", JOptionPane.OK_CANCEL_OPTION);
			    String pwd = "";
			    if (result == JOptionPane.OK_OPTION) {
			      pwd = new String(newpwdField.getPassword());
			      if(!new String(oldpwdField.getPassword()).equals(admin.getPassword())) {
			    	  JOptionPane.showMessageDialog(null,"输入的旧密码不正确！","提示",JOptionPane.WARNING_MESSAGE);
		    		  return;
			      }else if(!pwd.equals(new String(repwdField.getPassword()))) {
			    	  JOptionPane.showMessageDialog(null,"两次输入的密码不一致！","提示",JOptionPane.WARNING_MESSAGE);
		    		  return;
			      }
			      
			    }else {
			    	return;
			    }
	
	    		admin.setPassword(pwd);
	    		boolean re = false;
	    		try {
					re = us.changeManagerPwd(admin);
				} catch (Exception e3) {
					
					e3.printStackTrace();
				}
	    		
	    		if(re) {
	    			JOptionPane.showMessageDialog(null,"修改成功 ","提示",JOptionPane.PLAIN_MESSAGE);
	    		}else {
	    			JOptionPane.showMessageDialog(null,"修改失败 ","提示",JOptionPane.ERROR_MESSAGE);
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
        account  = new JMenu("账号管理");
        
        search.setFont(font);
        addBook.setFont(font);
        errorlogin.setFont(font);
        watchborrow.setFont(font);
        alluser.setFont(font);
		register.setFont(font);
		editpwd.setFont(font);
		takeop.setFont(font);
		
        exitItem.setFont(font);
        defaultItem.setFont(font);
        
        function.add(search);
        function.add(addBook);
        function.add(errorlogin);
        function.add(watchborrow);
        function.add(alluser);
        function.add(takeop);
        exit.add(exitItem);
        defaultPage.add(defaultItem);
        account.add(editpwd);
        account.add(register);
        
        mb.add(defaultPage);
        mb.add(function);
        mb.add(account);
        mb.add(exit);
        mb.add(help);
        
        function.setFont(font);
        exit.setFont(font);
        help.setFont(font);
        defaultPage.setFont(font);
        account.setFont(font);
        
        mb.setBackground(new Color(175,238,238));
        //添加框线
        mb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mb.setBounds(0, 0, 1014, 20);
        menuPanel.add(mb);
 	
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


//	public void initbtn(JButton button) {
//		button.setContentAreaFilled(false);
//		button.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
//		button.setFont(font);
//		button.setFocusPainted(false);
//		button.addActionListener(this);
//		this.panel.add(button);
//	}


//	@Override
//	public void actionPerformed(ActionEvent e) {
//		if(e.getSource() == searchbtn) {
//			dispose();//将当前页面关闭
//			new BookOperateFrame(this.admin);//跳转界面  	
//		}else if(e.getSource() == addBookbtn) {
//			dispose();//将当前页面关闭
//    		new AddBookFrame(admin);//跳转界面
//		}else if(e.getSource() == changeMsgbtn) {
//			JPanel manPanel = new JPanel();
//			JPasswordField oldpwdField = new JPasswordField(10);
//			
//			JPasswordField newpwdField = new JPasswordField(10);
//			JPasswordField repwdField = new JPasswordField(10);
//		    manPanel.add(new JLabel("旧密码:"));
//		    manPanel.add(oldpwdField);
//		    
//		    manPanel.add(new JLabel("新密码:"));
//		    manPanel.add(newpwdField);
//		    //manPanel.add(Box.createHorizontalStrut(15)); // a spacer
//		    manPanel.add(new JLabel("重复密码:"));
//		    manPanel.add(repwdField);
//
//		    int result = JOptionPane.showConfirmDialog(null, manPanel,
//		        "请输入相关信息：", JOptionPane.OK_CANCEL_OPTION);
//		    String pwd = "";
//		    if (result == JOptionPane.OK_OPTION) {
//		      pwd = new String(newpwdField.getPassword());
//		      if(!new String(oldpwdField.getPassword()).equals(admin.getPassword())) {
//		    	  JOptionPane.showMessageDialog(null,"输入的旧密码不正确！","提示",JOptionPane.WARNING_MESSAGE);
//	    		  return;
//		      }else if(!pwd.equals(new String(repwdField.getPassword()))) {
//		    	  JOptionPane.showMessageDialog(null,"两次输入的密码不一致！","提示",JOptionPane.WARNING_MESSAGE);
//	    		  return;
//		      }
//		      
//		    }else {
//		    	return;
//		    }
//
//    		admin.setPassword(pwd);
//    		boolean re = false;
//    		try {
//				re = us.changeManagerPwd(admin);
//			} catch (Exception e3) {
//				
//				e3.printStackTrace();
//			}
//    		
//    		if(re) {
//    			JOptionPane.showMessageDialog(null,"修改成功 ","提示",JOptionPane.PLAIN_MESSAGE);
//    		}else {
//    			JOptionPane.showMessageDialog(null,"修改失败 ","提示",JOptionPane.ERROR_MESSAGE);
//    		}
//			
//		}else if(e.getSource() == errorRegisterbtn) {
//			
//			dispose();//将当前页面关闭
//    		new ErrorRegisterFrame(admin);//跳转界面
//    		
//		}else if(e.getSource() == alluserbtn) {
//			
//			dispose();//将当前页面关闭
//    		new AllUserFrame(admin);//跳转界面
//    		
//		}else if(e.getSource() == logoutbtn) {
//			
//			int n = JOptionPane.showConfirmDialog(null, "确定要退出登录吗?", "提示",JOptionPane.YES_NO_OPTION);
//    		if(n==0) {
//    			dispose();//将当前页面关闭
//        		new LoginFrame();//跳转界面
//    		}
//    		
//		}else if(e.getSource() == meassagebtn) {
//			
//			dispose();//将当前页面关闭
//    		new BorrowDetailFrame(admin);//跳转界面
//			
//		}else if(e.getSource() == addManagerbtn) {
//			
//			dispose();//将当前页面关闭
//    		new AddManagerFrame(admin);//跳转界面
//    		
//		}else if(e.getSource() == borrowbtn) {
//			dispose();
//			new BorrowOperate(admin);
//		}
//		
//	}


}
