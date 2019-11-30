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
	 * ����Ա�޸�ĳ���û���Ϣ
	 */
	private static final long serialVersionUID = 1L;
	private JButton closebtn = new JButton("�ر�");
	private JButton submitbtn = new JButton("�ύ");
	private Font font=new Font("����",Font.BOLD,18);
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
	private JPanel panel = new JPanel(){		//���ñ���ͼƬ
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
	
	
	private User user;//��¼�û�Ҫ�޸ĵ��û���Ϣ
	private UserService us = new UserService();
	private ArrayList<UserType> utypes = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public ManagerEditUserMsgFrame(User user) {
		super("�޸�"+user.getUsername()+"�û���Ϣ�Ľ���" );		
		setSize(600, 460);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		this.user = user;
		
		panel.setOpaque(false);		//���ÿؼ�͸��
		add(panel);
		panel.setLayout(null);
		//������������������
		nameLabel = new JLabel("������");		
		nameLabel.setBounds(170, 20, 65, 25);
		nameLabel.setFont(font);
		panel.add(nameLabel);
			
		nameText.setBounds(230, 20, 150, 25);
		nameText.setOpaque(false);
		nameText.setText(user.getUsername());
		panel.add(nameText);
		
		//�����Ա�����������
		sexLabel = new JLabel("�Ա�");
		sexLabel.setFont(font);
		sexLabel.setBounds(170, 60, 65, 25);
		panel.add(sexLabel);
		
		if(user.getGender() == 'f') {
			jrbMale=new JRadioButton("��",true);	
	        jrbFemale=new JRadioButton("Ů");
		}else {
			jrbMale=new JRadioButton("��");	
	        jrbFemale=new JRadioButton("Ů",true);
		}
		
		
		
		jrbMale.setFont(font);
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
		typeLablel = new JLabel("�û����ͣ�");
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
		
		//�����������ɸѡ����
		condition.addItem("��ǰ��"+user.getType());
		for (UserType utype : utypes) {
			condition.addItem(utype.getTypeName());
		}
		
		panel.add(condition);
		
		
        
        //��������ѧԺ����������
		departmentLabel = new JLabel("����ѧԺ��");
		departmentLabel.setBounds(140, 140, 100, 25);
		departmentLabel.setFont(font);
		panel.add(departmentLabel);
		
		departmentText.setOpaque(false);
		departmentText.setBounds(230, 140, 150, 25);
		departmentText.setText(user.getDepartment());
		panel.add(departmentText);
		
		//�����ֻ���������������
		telLabel = new JLabel("�ֻ����룺");
		telLabel.setBounds(140, 180, 100, 25);
		telLabel.setFont(font);
		panel.add(telLabel);
		
		telText.setBounds(230, 180, 150, 25);
		telText.setOpaque(false);
		telText.setText(user.getTel()+"");
		panel.add(telText);
		
		
		//�����û�״̬
		statusLabel = new JLabel("�û�״̬��");
		statusLabel.setBounds(140, 220,100, 25);
		statusLabel.setFont(font);
		panel.add(statusLabel);
		
		status.setBounds(230, 220, 150, 25);
		status.setFont(font);
		//�����������ɸѡ����
		status.addItem("---��ѡ��---");
		status.addItem("����");
		status.addItem("����");
		status.addItem("ע��");
		if(user.getStatus()=='A') {
			status.setSelectedIndex(1);;
		}else if(user.getStatus() == 'B') {
			status.setSelectedIndex(2);
		}else if(user.getStatus() == 'C') {
			status.setSelectedIndex(3);
		}
		panel.add(status);
		
		
		
		//������������������
		emailLabel = new JLabel("���䣺");
		emailLabel.setBounds(170, 260,100, 25);
		emailLabel.setFont(font);
		panel.add(emailLabel);
		
		emailText.setOpaque(false);
		emailText.setBounds(230, 260, 150, 25);
		emailText.setText(user.getEmail());
		panel.add(emailText);
		
		
		submitbtn = new JButton("ȷ��");
        submitbtn.setBounds(200, 300, 65, 25);
        submitbtn.setContentAreaFilled(false);
        submitbtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
        submitbtn.setFont(font);
        submitbtn.addActionListener(this);
        submitbtn.setFocusPainted(false);
        panel.add(submitbtn);
        
        closebtn = new JButton("ȡ��");
        closebtn.setBounds(320, 300, 65, 25);
        closebtn.setContentAreaFilled(false);
        closebtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
        closebtn.setFont(font);
        closebtn.addActionListener(this);
        closebtn.setFocusPainted(false);
        panel.add(closebtn);
		
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


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==submitbtn) {
			
			
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
            int type = 0;
            if(typeIndex == 0)
            	type = user.getType();
            else
            	type = this.utypes.get(typeIndex-1).getTypeId();
            
            
    		//��ȡ����
            String department = departmentText.getText();
    		if(department.equals("")) {
    			JOptionPane.showMessageDialog(null, "����ѧԺ����Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
    		}
    		
    		
    		//��ȡ�绰����
    		if(telText.getText().equals("")) {
    			JOptionPane.showMessageDialog(null, "�ֻ����벻��Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		long tel = 0 ;
    		if(isInteger(telText.getText())) {
    			if(telText.getText().length() ==11)
    				tel = Long.parseLong(telText.getText());
    			else {
    				JOptionPane.showMessageDialog(null, "�ֻ�ӦΪ11λ����","��ʾ",JOptionPane.ERROR_MESSAGE);
        			return;
    			}
    		}else{
    			JOptionPane.showMessageDialog(null, "�ֻ�ӦΪ11λ���֣�","��ʾ",JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		
    		//��ȡ�û�״̬ 
            //��ȡѡ�е��û�״̬
            int statusIndex = status.getSelectedIndex();
            char status = ' ';
            if(statusIndex==0) {
            	JOptionPane.showMessageDialog(null, "��ѡ���û�����","��ʾ",JOptionPane.ERROR_MESSAGE);
    			return;
            }else if(statusIndex==1) {
            	status = 'A';
            }else if(statusIndex==2) {
            	status = 'B';
            }else if(statusIndex==3) {
            	status = 'C';
            }
    		
    		//��ȡ����
    		String email = emailText.getText();
    		if(email.equals("")) {
    			JOptionPane.showMessageDialog(null, "���䲻��Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
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
    			JOptionPane.showMessageDialog(null, "�޸��û���Ϣ�ɹ���","��ʾ",JOptionPane.PLAIN_MESSAGE);
    		}else {
    			
    			JOptionPane.showMessageDialog(null, "�޸��û���Ϣʧ�ܣ�","��ʾ",JOptionPane.WARNING_MESSAGE);
    		}
    		
            
		}else if(e.getSource() == closebtn) {
			dispose();	
		}
		
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
		
	
}
