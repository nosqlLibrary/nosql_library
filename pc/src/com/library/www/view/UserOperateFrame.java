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
	 * �û���¼֮��Ĳ�������
	 */
	private UserBookOperateJPanel userBookOperateJPanel;
	private UserBorrowMsgJPanel userBorrowMsgJPanel;
	private EditUserMsgJPanel editUserMsgJPanel;
	
	private Font font=new Font("����",Font.BOLD,14); 
	private UserService us = new UserService();
	private BorrowService bs = new BorrowService();
	private User u = new User();
	private JMenuBar mb;//����˵���
	private JMenu function, exit, help,defaultPage;//�������˵�
	private JMenuItem search, watchmsg, edituser, logout,exitItem,defaultItem;//����˵�ѡ��
	private JPanel menupanel = new JPanel();
	private JPanel bottompanel = new JPanel() {
			
			private static final long serialVersionUID = 1L;
			//�޸��ұ����ı���ͼƬ
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
        super("�û��������棺"+user.getUsername() + "������");
        
        setSize(1015,620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.u = user;
        
        contentPane = getContentPane();
        contentPane.setLayout(null);
        //�������õ�������
        contentPane.add(menupanel);//�˵����
        contentPane.add(bottompanel);//��ʼ������
        //��������
        menupanel.setLayout(null);
        menupanel.setBounds(0, 0, 1015, 20);
        bottompanel.setBounds(0,20,1015,600);
        userBookOperateJPanel = new UserBookOperateJPanel(this.u);
        userBookOperateJPanel.setVisible(false);
        
        userBorrowMsgJPanel = new UserBorrowMsgJPanel(this.u);
        userBorrowMsgJPanel.setVisible(false);
        
        editUserMsgJPanel = new EditUserMsgJPanel(this.u);
        editUserMsgJPanel.setVisible(false);
        
        search = new JMenuItem("��ѯͼ��"); 
        watchmsg = new JMenuItem("�鿴������Ϣ");
        edituser = new JMenuItem("�޸��û���Ϣ");
        logout = new JMenuItem("�û�ע��");
        exitItem = new JMenuItem("�˳�ϵͳ");
        defaultItem = new JMenuItem("�ص���ҳ");
        
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
        		int n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫע�����û���?", "��ʾ",JOptionPane.YES_NO_OPTION);
        		if(n==0) {
        			//����û��Ƿ��ѹ黹����ͼ��  Ĭ�ϻ���ͼ��δ�黹
        			boolean result = false;
        			try {
        				result = bs.checkIsAllBack(u);
        				
        				if(result) {
        					boolean logOut = us.logOut(u);
        					//��������������ע��
    	        			if(logOut) {
    	        				//ע���ɹ�
    	        				JOptionPane.showMessageDialog(null, "ע���ɹ���", "��ʾ", JOptionPane.PLAIN_MESSAGE);
    	        				dispose();//����ǰҳ��ر�
    			        		new LoginFrame();//��ת����
    	        			}else {
    	        				//ע��ʧ��
    	        				JOptionPane.showMessageDialog(null, "ע��ʧ�ܣ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
    	        			}
        				}else {
        					//��ʾҪ�ȹ黹����ͼ��
        					JOptionPane.showMessageDialog(null, "���ȹ黹��δ�黹��ͼ�飡", "��ʾ", JOptionPane.ERROR_MESSAGE);
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
        function = new JMenu("ϵͳ����");
        exit = new JMenu("�˳�ϵͳ");
        help = new JMenu("����");
        defaultPage = new JMenu("��ҳ");
        
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
        //��ӿ���
        mb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mb.setBounds(0, 0, 1014, 20);
        menupanel.add(mb);
//        //��߿��Բ鿴ͳ����Ϣ���軹�鼮
//		searchbtn = new JButton("��ѯͼ��");
//		searchbtn.setBounds(120, 90, 140, 30);
//		searchbtn.setContentAreaFilled(false);
//		searchbtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
//		searchbtn.setFont(font);
//		searchbtn.setFocusPainted(false);
//		
//		ActionListener actionlistener1 = new ActionListener() {		
//	        	@Override
//	        	public void actionPerformed(ActionEvent arg0) {
//	        		dispose();//����ǰҳ��ر�
//	        		new UserBookOperateFrame(user);//��ת����
//	        	}
//		};
//	
//		searchbtn.addActionListener(actionlistener1);
//		
//		messagebtn = new JButton("�鿴������Ϣ");
//		messagebtn.setBounds(120, 150, 140, 30);
//		messagebtn.setContentAreaFilled(false);
//		messagebtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
//		messagebtn.setFont(font);
//		messagebtn.setFocusPainted(false);
//		
//		ActionListener actionlistener2 = new ActionListener() {		
//	        	@Override
//	        	public void actionPerformed(ActionEvent arg0) {
//	        		dispose();//����ǰҳ��ر�
//	        		new UserBorrowMsgFrame(user);//��ת����
//	        	}
//		};
//	
//		messagebtn.addActionListener(actionlistener2);
//		
//		changeUserMsgbtn = new JButton("�޸��û���Ϣ");
//		changeUserMsgbtn.setBounds(120, 210, 140, 30);
//		changeUserMsgbtn.setContentAreaFilled(false);
//		changeUserMsgbtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
//		changeUserMsgbtn.setFont(font);
//		changeUserMsgbtn.setFocusPainted(false);
//		
//		ActionListener actionlistener3 = new ActionListener() {		
//	        	@Override
//	        	public void actionPerformed(ActionEvent arg0) {
//	        		dispose();//����ǰҳ��ر�
//	        		new EditUserMsgFrame(user);//��ת����
//	        	}
//		};
//	
//		changeUserMsgbtn.addActionListener(actionlistener3);
//		
//		logOutbtn = new JButton("�û�ע��");
//		logOutbtn.setBounds(120, 270, 140, 30);
//		logOutbtn.setContentAreaFilled(false);
//		logOutbtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
//		logOutbtn.setFont(font);
//		logOutbtn.setFocusPainted(false);
//		
//		ActionListener actionlistener4 = new ActionListener() {		
//	        	
//				@Override
//	        	public void actionPerformed(ActionEvent arg0) {
//	        		int n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫע�����û���?", "��ʾ",JOptionPane.YES_NO_OPTION);
//	        		if(n==0) {
//	        			//����û��Ƿ��ѹ黹����ͼ��  Ĭ�ϻ���ͼ��δ�黹
//	        			boolean result = false;
//	        			try {
//	        				result = bs.checkIsAllBack(u);
//	        				
//	        				if(result) {
//	        					boolean logOut = us.logOut(u);
//	        					//��������������ע��
//	    	        			if(logOut) {
//	    	        				//ע���ɹ�
//	    	        				JOptionPane.showMessageDialog(null, "ע���ɹ���", "��ʾ", JOptionPane.PLAIN_MESSAGE);
//	    	        				dispose();//����ǰҳ��ر�
//	    			        		new LoginFrame();//��ת����
//	    	        			}else {
//	    	        				//ע��ʧ��
//	    	        				JOptionPane.showMessageDialog(null, "ע��ʧ�ܣ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
//	    	        			}
//	        				}else {
//	        					//��ʾҪ�ȹ黹����ͼ��
//	        					JOptionPane.showMessageDialog(null, "���ȹ黹��δ�黹��ͼ�飡", "��ʾ", JOptionPane.ERROR_MESSAGE);
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
//		exitbtn = new JButton("�˳���¼");
//		exitbtn.setBounds(120, 330, 140, 30);
//		exitbtn.setContentAreaFilled(false);
//		exitbtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
//		exitbtn.setFont(font);
//		exitbtn.setFocusPainted(false);
//		
//		ActionListener actionlistener5 = new ActionListener() {		
//	        	@Override
//	        	public void actionPerformed(ActionEvent arg0) {
//	        		int n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫ�˳���¼��?", "��ʾ",JOptionPane.YES_NO_OPTION);
//	        		if(n==0) {
//	        			dispose();//����ǰҳ��ر�
//		        		new LoginFrame();//��ת����
//	        		}
//	        		
//	        	}
//		};
//	
//		exitbtn.addActionListener(actionlistener5);
//		
//		//������ǩ
//		tipLabel =  new JLabel("��ѡ��һ�����ܽ��в�����");
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
