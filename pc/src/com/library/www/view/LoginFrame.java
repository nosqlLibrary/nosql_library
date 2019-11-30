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
 * ��¼����
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
			
			super("ͼ�����ϵͳ");
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
			
			Font font=new Font("����",Font.BOLD,20); 
			tipLabel = new JLabel("��ӭʹ�ã����¼");
			tipLabel.setBounds(220,50,200,30);
			tipLabel.setFont(font);
			panel.add(tipLabel);
			
			useridLabel = new JLabel("�û�ID:");
	        useridLabel.setBounds(140, 100, 80, 30);
	        useridLabel.setFont(font);
	        panel.add(useridLabel);
	        useridText = new JTextField(20);
	        useridText.setBounds(220,100,165,25);
	        useridText.setOpaque(false);
	        panel.add(useridText);
	        
	        
	        passwordLabel = new JLabel("����:");
	        passwordLabel.setBounds(160, 140, 80, 30);
	        passwordLabel.setFont(font);
	        panel.add(passwordLabel);
	        passwordText = new JPasswordField(20);
	        passwordText.setBounds(220,140,165,25);
	        passwordText.setOpaque(false);
	        panel.add(passwordText);
	        
	        //ѡ�����û������Ա����ݽ��е�¼
			genderLabel = new JLabel("��ѡ��");
			genderLabel.setFont(font);
			genderLabel.setBounds(140, 180, 100, 25);
			panel.add(genderLabel);
			
			userjrb=new JRadioButton("�û�",true);	
			userjrb.setFont(font);
	        managerjrb=new JRadioButton("����Ա");
	        managerjrb.setFont(font);
	        
	        ButtonGroup buttonGroup=new ButtonGroup();
	        buttonGroup.add(userjrb);
	        buttonGroup.add(managerjrb);
	        
	        userjrb.setBounds(215, 180, 80, 25);
	        managerjrb.setBounds(300, 180, 100, 25);
	        
	        //�����������͸��
	        userjrb.setOpaque(false);
	        managerjrb.setOpaque(false);
	        panel.add(userjrb);
	        panel.add(managerjrb);
	        
	        loginBtn = new JButton("��¼");
	        loginBtn.setContentAreaFilled(false);
	        loginBtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
	        loginBtn.setFocusPainted(false);
	        
	        loginBtn.setFont(font);
	        loginBtn.setBounds(220, 220, 65, 25);
	        
	        panel.add(loginBtn);
	        loginBtn.addActionListener(this);
	        
	        registerBtn = new JButton("ע��");
	        registerBtn.setBounds(320, 220, 65, 25);
	        registerBtn.setContentAreaFilled(false);
	        registerBtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
	        registerBtn.setFont(font);
	        registerBtn.setFocusPainted(false);
	        panel.add(registerBtn);
	        
	        registerBtn.addActionListener(this);
	        //����logo
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
        		String userPassword = String.valueOf(password);//��char��ֵת��ΪString
        		
        		if(userName.equals("")) {
        			JOptionPane.showMessageDialog(null,"�û�ID����Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
        			return;
        		}else if(userPassword.equals("")){
        			JOptionPane.showMessageDialog(null,"���벻��Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		//�û�Id�����붼��Ϊ��֮���ٵ��÷����� 	        		
        		User userLogin = null;
        		Admin adminLogin = null;
        		
        		//��ȡ���
        		boolean isUser = userjrb.isSelected();
                if(isUser) {
                	//���û���ݵ�¼
                	try {
						userLogin = userService.userLogin(userName,userPassword);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
                }else {
                	//�Թ���Ա����ݵ�¼
                	try {
						adminLogin = userService.managerLogin(userName,userPassword);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
                }
                
                
				if(isUser) {
					//�ж��Ƿ��ȡ�����ݿ������
	        		if(userLogin.getUsername()!=null){
	        			//System.out.println(userLogin.toString());
	        			if(userLogin.getStatus()=='B') {
	        				JOptionPane.showMessageDialog(null,"��������û�Ŀǰ���ڽ���״̬","��ʾ",JOptionPane.ERROR_MESSAGE);
						}if(userLogin.getStatus()=='C') {
	        				JOptionPane.showMessageDialog(null,"��������û���ע��","��ʾ",JOptionPane.ERROR_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(null, "��¼�ɹ�","��ʾ",2); 
							new UserOperateFrame(userLogin); 
		        			dispose();
						}
	        		}else {
	        			 JOptionPane.showMessageDialog(null,"��������û�Id���������ݴ���","��ʾ",JOptionPane.ERROR_MESSAGE);
	        		}
				}else {
					//�ж��Ƿ��ȡ�����ݿ������
	        		if(adminLogin.getAdminName()!=null){
	        				JOptionPane.showMessageDialog(null, "��¼�ɹ�","��ʾ",2); 
		        			new ManagerOperateFrame(adminLogin); 
		        			dispose();
					}else {
						JOptionPane.showMessageDialog(null,"��������û�Id���������ݴ���","��ʾ",JOptionPane.ERROR_MESSAGE);
					}
				}
				
    		
			}else if(e.getSource() == registerBtn) {
				new RegisterFrame();
        		dispose();
			}
			
		}
}

    
	
