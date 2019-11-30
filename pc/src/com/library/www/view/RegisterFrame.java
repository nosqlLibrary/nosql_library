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
 * ע�����
 * @author 11247
 *
 */

@SuppressWarnings("serial")
public class RegisterFrame extends JFrame {
		//���ý����������	
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
		private Font font=new Font("����",Font.BOLD,18); 
		private static ArrayList<UserType> utypes = new ArrayList<>();
		static {
			
			try {
				 utypes = us.getAllUserTypes();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		//��ʼ������
		@SuppressWarnings({ "unchecked" })
		public RegisterFrame() {
			super("�û�ע��");		
			setSize(600, 450);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocation(650,250);
			
			
			panel = new JPanel(){		//���ñ���ͼƬ
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
			panel.setOpaque(false);		//���ÿؼ�͸��
			add(panel);
			panel.setLayout(null);
			//������������������
			nameLabel = new JLabel("������");		
			nameLabel.setBounds(170, 20, 65, 25);
			nameLabel.setFont(font);
			panel.add(nameLabel);
			nameText = new JTextField();		
			nameText.setBounds(230, 20, 150, 25);
			nameText.setOpaque(false);
			panel.add(nameText);
			
			//�����Ա�����������
			genderLabel = new JLabel("�Ա�");
			genderLabel.setFont(font);
			genderLabel.setBounds(170, 60, 65, 25);
			panel.add(genderLabel);
			jrbMale=new JRadioButton("��",true);	//�����Ա�����Ůѡ��ť������δѡ��ʱĬ��Ϊ��
			jrbMale.setFont(font);
	        jrbFemale=new JRadioButton("Ů");
	        jrbFemale.setFont(font);
	        ButtonGroup buttonGroup=new ButtonGroup();
	        buttonGroup.add(jrbMale);
	        jrbMale.setBounds(230, 60, 50, 25);
	        jrbFemale.setBounds(300, 60, 50, 25);
	        buttonGroup.add(jrbFemale);
	        //�����������͸��
	        jrbMale.setOpaque(false);
	        jrbFemale.setOpaque(false);
	        panel.add(jrbMale);
	        panel.add(jrbFemale);
	        
			
			//�����û�����������
			typeLabel = new JLabel("�û����ͣ�");
			typeLabel.setBounds(140, 100,100, 25);
			typeLabel.setFont(font);
			panel.add(typeLabel);
			
			condition.setBounds(230, 100, 150, 25);
			condition.setFont(font);
			//�����������ɸѡ����
			condition.addItem("---��ѡ��---");
			for (UserType utype : utypes) {
				condition.addItem(utype.getTypeName());
			}
			
			panel.add(condition);
			
			//��������ѧԺ����������
			departmentLabel = new JLabel("����ѧԺ��");
			departmentLabel.setBounds(140, 140, 100, 25);
			departmentLabel.setFont(font);
			panel.add(departmentLabel);
			departmentText = new JTextField();
			departmentText.setOpaque(false);
			departmentText.setBounds(230, 140, 150, 25);
			panel.add(departmentText);
			
			//�����ֻ���������������
			telLabel = new JLabel("�ֻ����룺");
			telLabel.setBounds(140, 180, 100, 25);
			telLabel.setFont(font);
			panel.add(telLabel);
			telText = new JTextField();
			telText.setBounds(230, 180, 150, 25);
			telText.setOpaque(false);
			panel.add(telText);
			
			//������������������
			passLabel = new JLabel("���룺");
			passLabel.setBounds(170, 220, 65, 25);
			passLabel.setFont(font);
			panel.add(passLabel);
			passText = new JPasswordField();	//����������
			passText.setBounds(230, 220, 150, 25);
			passText.setOpaque(false);
			panel.add(passText);
			
			//������������������
			repassLabel = new JLabel("ȷ�����룺");
			repassLabel.setBounds(140, 260, 100, 25);
			repassLabel.setFont(font);
			panel.add(repassLabel);
			repassText = new JPasswordField();	//����������
			repassText.setBounds(230, 260, 150, 25);
			repassText.setOpaque(false);
			panel.add(repassText);
			
			//������������������
			emailLabel = new JLabel("���䣺");
			emailLabel.setBounds(170, 300,100, 25);
			emailLabel.setFont(font);
			panel.add(emailLabel);
			emailText = new JTextField();
			emailText.setOpaque(false);
			emailText.setBounds(230, 300, 150, 25);
			panel.add(emailText);
			
			
			sureBtn = new JButton("ȷ��");
	        sureBtn.setBounds(200, 340, 65, 25);
	        sureBtn.setContentAreaFilled(false);
	        sureBtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
	        sureBtn.setFont(font);
	        sureBtn.setFocusPainted(false);
	        panel.add(sureBtn);
	        ActionListener btl2 = new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent arg0) {
	        		
	        		//��ȡ�û���
	        		String username = nameText.getText();
	        		if(username.equals("")) {
	        			JOptionPane.showMessageDialog(null, "�û�������Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}
	        		
	        		//��ȡ�Ա�
	        		boolean man = jrbMale.isSelected();
	                char gender = 'f';
	                if(!man)
	                	gender = 'm';
	                
	        		
	        		//��ȡ�û�����  
	                //��ȡѡ�е��û�����
	                int typeIndex = condition.getSelectedIndex();
	                if(typeIndex==0) {
	                	JOptionPane.showMessageDialog(null, "��ѡ���û�����","��ʾ",JOptionPane.ERROR_MESSAGE);
	        			return;
	                }
	                
	                
	        		//��ȡ����
	                String department = departmentText.getText();
	        		if(department.equals("")) {
	        			JOptionPane.showMessageDialog(null, "����ѧԺ����Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
						return;
	        		}
	        		
	        		
	        		//��ȡ�绰����
	        		String pattern = "^1[3-9]\\d{9}$";//������ʽ
	        		if(telText.getText().equals("")) {
	        			JOptionPane.showMessageDialog(null, "�ֻ����벻��Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}else if(!Pattern.matches(pattern, telText.getText())) {
	        			JOptionPane.showMessageDialog(null, "�ֻ���ʽ����ȷ��","��ʾ",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}
	        		
	        		long tel = Long.parseLong(telText.getText());
	        		
	        		//��ȡ����
	        		String password = new String(passText.getPassword());
	        		String repwd = new String(repassText.getPassword());
	        		if(password.equals("")) {
	        			JOptionPane.showMessageDialog(null, "���벻��Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}else if(repwd.equals("")) {
	        			JOptionPane.showMessageDialog(null, "ȷ�����벻��Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}else if(!repwd.equals(password)) {
	        			JOptionPane.showMessageDialog(null, "������������벻һ�£�","��ʾ",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}
	        		
	        		
	        		pattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
	        		
	        		//��ȡ����
	        		String email = emailText.getText();
	        		if(email.equals("")) {
	        			JOptionPane.showMessageDialog(null, "���䲻��Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}else if(!Pattern.matches(pattern, email)) {
	        			JOptionPane.showMessageDialog(null, "�����ʽ����ȷ��","��ʾ",JOptionPane.ERROR_MESSAGE);
	        			return;
	        		}
	        		
	        		
	        		//��������û�ID
	        		String userid = createData(20);
	        		
	        		//��ȡע��ʱ��
	                Date date = new Date();// new Date()Ϊ��ȡ��ǰϵͳʱ��
	                
	        		
	        		//����userʵ�����
	        		User user = new User(userid, username, password, gender, typeIndex, department, tel, email, date, 'A');
	        		
	        		
	        		
	        		try {
						if(us.userRegister(user))
							JOptionPane.showMessageDialog(null, "ע��ɹ��������û�IDΪ"+userid,"��ʾ",JOptionPane.PLAIN_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "ע��ʧ��","��ʾ",JOptionPane.ERROR_MESSAGE);
					} catch (Exception e) {
						e.printStackTrace();
					}
	        		new LoginFrame();
	    			dispose();
	        	}
	        };
	        sureBtn.addActionListener(btl2);
	        
	        cancelBtn = new JButton("ȡ��");
	        cancelBtn.setBounds(320, 340, 65, 25);
	        cancelBtn.setContentAreaFilled(false);
	        cancelBtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
	        cancelBtn.setFont(font);
	        cancelBtn.setFocusPainted(false);
	        panel.add(cancelBtn);
	        ActionListener btl1 = new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent arg0) {
	        		int result = JOptionPane.showConfirmDialog(null, "��ȷ��Ҫ�˳�ע����", "ע����ʾ",JOptionPane.OK_CANCEL_OPTION);
	        		if(result == 0) {
	        			new LoginFrame();
	        			dispose();
	        		}
	        	}
	        };
			cancelBtn.addActionListener(btl1);
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
		
		//�ж��ַ����Ƿ�ȫΪ����
		public static boolean isInteger(String str) {  
			for(int i=str.length();--i>=0;){
		        int chr=str.charAt(i);
		        if(chr<48 || chr>57)
		            return false;
		    }
		   return true; 
		}
		
		//����ָ���������ɴ����ֵ������
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
