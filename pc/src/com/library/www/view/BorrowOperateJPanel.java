package com.library.www.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.library.www.po.Admin;
import com.library.www.po.User;
import com.library.www.service.BookService;
import com.library.www.service.BorrowService;
import com.library.www.service.UserService;

public class BorrowOperateJPanel extends JPanel implements ActionListener {

	/**
	 * 管理员对用户的借阅和归还进行操作
	 * 	通过图书编号借阅和归还
	 */
	private static final long serialVersionUID = 1L;
	private Admin admin;
	
	private JLabel bidlabel1 = new JLabel("图书编号:");
	private JLabel bidlabel2 = new JLabel("图书编号:");
	private JLabel useridlabel = new JLabel("用户编号:");
	private JTextField bidText = new JTextField();
	private JTextField bidText2 = new JTextField();
	private JTextField userText = new JTextField();
	private JButton takebtn,backbtn;
	private Font font=new Font("仿宋",Font.BOLD,18); 
	private BookService bookService = new BookService();
	private BorrowService borrowService = new BorrowService();
	private UserService us = new UserService();
	private JTabbedPane tabbedPane=new JTabbedPane();
	private JPanel leftpanel = new JPanel() {
		
		private static final long serialVersionUID = 1L;
		//修改右边面板的背景图片
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
	
	private JPanel rightpanel = new JPanel() {
		
		private static final long serialVersionUID = 1L;
		//修改右边面板的背景图片
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
	
	public BorrowOperateJPanel() {
        setSize(1015,600);
        setLayout(null);
        
        
        leftpanel.setLayout(null);
        rightpanel.setLayout(null);
        tabbedPane.addTab("用户借阅操作",null,leftpanel,"借阅");
        tabbedPane.addTab("用户归还操作",null,rightpanel,"归还");
        tabbedPane.setMnemonicAt(0,KeyEvent.VK_1);
        tabbedPane.setBounds(0,0,1015, 600);
        
        add(tabbedPane);
        
        leftinit();//左面板初始化
        rightinit();//右面板初始化
        
        setVisible(true);
	}

	
	private void rightinit() {
		
		bidlabel1.setFont(font);
		bidlabel1.setBounds(250,180,100, 30);
		rightpanel.add(bidlabel1);
		
        bidText = new JTextField();		
        bidText.setBounds(370, 180, 310, 30);
        bidText.setOpaque(false);
        rightpanel.add(bidText);
        
        backbtn = new JButton("归还");
        backbtn.setIcon(new ImageIcon(this.getClass().getResource("/Images/take_in.png")) );
        backbtn.setBounds(480, 230, 80, 30);
        initButton(backbtn);
        rightpanel.add(backbtn);
        
        
        
	}

	private void initButton(JButton button) {
		button.setContentAreaFilled(false);
		button.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
		button.setFont(font);
		button.setFocusPainted(false);
		button.addActionListener(this);
	}

	private void leftinit() {
		
		bidlabel2.setFont(font);
		bidlabel2.setBounds(250,150,100, 30);
		leftpanel.add(bidlabel2);
		
        bidText2 = new JTextField();		
        bidText2.setBounds(370, 150, 310, 30);
        bidText2.setOpaque(false);
        leftpanel.add(bidText2);
        
        useridlabel.setFont(font);
        useridlabel.setBounds(250,200,100, 30);
		leftpanel.add(useridlabel);
        
        userText = new JTextField();		
        userText.setBounds(370, 200, 310, 30);
        userText.setOpaque(false);
        leftpanel.add(userText);
        
        takebtn = new JButton("借阅");
        takebtn.setIcon(new ImageIcon(this.getClass().getResource("/Images/take_out.png")) );
        takebtn.setBounds(480, 250,80, 30);
        initButton(takebtn);
        leftpanel.add(takebtn);
        
        
	}


//	public static void main(String[] args) {
//		new BorrowOperate(new Admin());
//	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == takebtn ) {
			String bid = bidText2.getText();
			String userid = userText.getText();
			if(bid.equals("")||userid.equals("")) {
				JOptionPane.showMessageDialog(null, "请输入相关信息！","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			User user;
			boolean result = false;
			
			try {
				boolean in = bookService.checkBookStatus(bid, 'A');
				if(!in) {
					JOptionPane.showMessageDialog(null, "图书不在库！","提示",JOptionPane.WARNING_MESSAGE);
					return;
				}
				user = us.getUserMsgByID(userid);
				if(user.getStatus() != 'A') {
					JOptionPane.showMessageDialog(null, "当前用户为禁用状态！","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
				//System.out.println(user);
				if(user.getUsername() != null)
					result = borrowService.takeOut(bid, user);
				else {
					JOptionPane.showMessageDialog(null, "请输入正确的用户编号！","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (Exception e2) {
				
				e2.printStackTrace();
			}
			
			
			if(result) {
				JOptionPane.showMessageDialog(null, "借阅成功！","提示",JOptionPane.PLAIN_MESSAGE);
				bidText2.setText("");
				userText.setText("");
				
			}else {
				JOptionPane.showMessageDialog(null, "借阅失败！","提示",JOptionPane.ERROR_MESSAGE);
				bidText2.setText("");
				userText.setText("");
			}
			
		}else if(e.getSource() == backbtn) {
			String bid = bidText.getText();
			if(bid.equals("")) {
				JOptionPane.showMessageDialog(null, "请输入图书编号！","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			boolean result = false;
			
			try {
				boolean in = bookService.checkBookStatus(bid, 'B');
				if(!in) {
					JOptionPane.showMessageDialog(null, "图书不是借书状态！","提示",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				result = borrowService.takeIn(bid);
				
			} catch (Exception e2) {
				
				e2.printStackTrace();
			}
			
			
			if(result) {
				JOptionPane.showMessageDialog(null, "归还成功！","提示",JOptionPane.PLAIN_MESSAGE);
				bidText.setText("");
				
				
			}else {
				JOptionPane.showMessageDialog(null, "归还失败！","提示",JOptionPane.ERROR_MESSAGE);
				bidText.setText("");
			}
			
			
		}
		
	}

}
