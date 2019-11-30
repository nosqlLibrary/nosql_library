package com.library.www.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.library.www.po.User;
import com.library.www.po.UserType;
import com.library.www.service.UserService;

public class EditUserMsgJPanel extends JPanel implements ActionListener {

	/**
	 * �û��Լ��޸��Լ����û���Ϣ
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton submitbtn = new JButton("�ύ");
	private Font font=new Font("����",Font.BOLD,18);
	private JLabel idLabel,nameLabel,sexLabel,departmentLabel,telLabel,emailLabel,oldpwdLabel,newpwdLabel,repwdLabel,typeLablel;
	private JTextField idText = new JTextField();
	private JTextField nameText = new JTextField();
	private JTextField departmentText = new JTextField();
	private JTextField telText = new JTextField();
	private JTextField emailText = new JTextField();
	private JPasswordField oldpwdText = new JPasswordField();
	private JPasswordField newpwdText = new JPasswordField();
	private JPasswordField repwdText = new JPasswordField();
	private JRadioButton jrbMale,jrbFemale;	
	@SuppressWarnings("rawtypes")
	private JComboBox condition = new JComboBox();
	
	private User user;//��¼�û�Ҫ�޸ĵ��û���Ϣ
	private User olduser;//��¼��ǰ����洫�������û���Ϣ  �����Ϣ�޸�ʧ�ܿ��Խ��û�ֵ�ٸ�����ԭ������Ϣ
	private UserService us = new UserService();
	private ArrayList<UserType> utypes = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public EditUserMsgJPanel(User user) {
		
		setSize(1015, 600);
		setLayout(null);
		
		this.user = user;
		this.olduser = user;
		
		idLabel = new JLabel("�û���ţ�");
		idLabel.setBounds(340, 50, 100,25);
		idLabel.setFont(font);
		add(idLabel);
		
		idText.setBounds(430, 50, 150, 25);
		idText.setOpaque(false);
		idText.setText(user.getUserid());
		idText.setEditable(false);
		add(idText);
		
		//������������������
		nameLabel = new JLabel("������");		
		nameLabel.setBounds(370,90, 65, 25);
		nameLabel.setFont(font);
		add(nameLabel);
			
		nameText.setBounds(430, 90, 150, 25);
		nameText.setOpaque(false);
		nameText.setText(user.getUsername());
		add(nameText);
		
		//�����Ա�����������
		sexLabel = new JLabel("�Ա�");
		sexLabel.setFont(font);
		sexLabel.setBounds(370, 130, 65, 25);
		add(sexLabel);
		
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
        jrbMale.setBounds(430, 130, 50, 25);
        jrbFemale.setBounds(500,130, 50, 25);
        buttonGroup.add(jrbFemale);
        //�����������͸��
        jrbMale.setOpaque(false);
        jrbFemale.setOpaque(false);
        add(jrbMale);
        add(jrbFemale);
        
        //�����û�����������
		typeLablel = new JLabel("�û����ͣ�");
		typeLablel.setBounds(340, 170,100, 25);
		typeLablel.setFont(font);
		add(typeLablel);
		
		condition.setBounds(430, 170, 150, 25);
		condition.setFont(font);
		//�����������ɸѡ����
		condition.addItem("---��ѡ��---");
		
		try {
			 utypes = us.getAllUserTypes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	
    	for (UserType utype : utypes) {
			condition.addItem(utype.getTypeName());
			if(user.getType() == utype.getTypeId()) {
				condition.setSelectedItem(utype.getTypeName());
			}
		}
		
		
		add(condition);
        
        //��������ѧԺ����������
		departmentLabel = new JLabel("����ѧԺ��");
		departmentLabel.setBounds(340, 210, 100, 25);
		departmentLabel.setFont(font);
		add(departmentLabel);
		
		departmentText.setOpaque(false);
		departmentText.setBounds(430, 210, 150, 25);
		departmentText.setText(user.getDepartment());
		add(departmentText);
		
		//�����ֻ���������������
		telLabel = new JLabel("�ֻ����룺");
		telLabel.setBounds(340, 250, 100, 25);
		telLabel.setFont(font);
		add(telLabel);
		
		telText.setBounds(430, 250, 150, 25);
		telText.setOpaque(false);
		telText.setText(user.getTel()+"");
		add(telText);
		
		//������������������
		oldpwdLabel = new JLabel("�����룺");
		oldpwdLabel.setBounds(360, 290, 80, 25);
		oldpwdLabel.setFont(font);
		add(oldpwdLabel);
		
		oldpwdText.setBounds(430, 290, 150, 25);
		oldpwdText.setOpaque(false);
		add(oldpwdText);
		
		//������������������
		newpwdLabel = new JLabel("�����룺");
		newpwdLabel.setBounds(340, 330, 80, 25);
		newpwdLabel.setFont(font);
		add(newpwdLabel);
		
		newpwdText.setBounds(430, 330, 150, 25);
		newpwdText.setOpaque(false);
		add(newpwdText);
		
		//������������������
		repwdLabel = new JLabel("�ظ����룺");
		repwdLabel.setBounds(340, 370,100, 25);
		repwdLabel.setFont(font);
		add(repwdLabel);
		
		repwdText.setBounds(430, 370, 150, 25);
		repwdText.setOpaque(false);
		add(repwdText);
		
		//������������������
		emailLabel = new JLabel("���䣺");
		emailLabel.setBounds(370, 410,100, 25);
		emailLabel.setFont(font);
		add(emailLabel);
		
		emailText.setOpaque(false);
		emailText.setBounds(430, 410, 150, 25);
		emailText.setText(user.getEmail());
		add(emailText);
		
		
		submitbtn = new JButton("ȷ��");
        submitbtn.setBounds(400, 450, 65, 25);
        submitbtn.setContentAreaFilled(false);
        submitbtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
        submitbtn.setFont(font);
        submitbtn.addActionListener(this);
        submitbtn.setFocusPainted(false);
        add(submitbtn);
        
        
		setVisible(true);
	}

	 protected void paintComponent(Graphics g) {  

		 Image bg;  
            try {  
                bg = (Image) new ImageIcon(this.getClass().getResource("/Images/bopbg.jpg")).getImage(); 
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
     } 
	 
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==submitbtn) {
			//��ȡ����ľ����룬�鿴�Ƿ����û���ǰ����һ��
			String oldpwd = new String(oldpwdText.getPassword());
			if(oldpwd.equals("")) {
				JOptionPane.showMessageDialog(null, "������������ٽ��в�����","��ʾ",JOptionPane.ERROR_MESSAGE);
    			return;
			}else if(!oldpwd.equals(this.user.getPassword())){
				JOptionPane.showMessageDialog(null, "�����������������������룡","��ʾ",JOptionPane.ERROR_MESSAGE);
    			return;
			}
			
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
            
            int type = this.utypes.get(typeIndex-1).getTypeId();
            
    		//��ȡ����
            String department = departmentText.getText();
    		if(department.equals("")) {
    			JOptionPane.showMessageDialog(null, "����ѧԺ����Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
    		}
    		
    		String pattern = "^1[3-9]\\d{9}$";//������ʽ
    		//��ȡ�绰����
    		if(telText.getText().equals("")) {
    			JOptionPane.showMessageDialog(null, "�ֻ����벻��Ϊ��","��ʾ",JOptionPane.ERROR_MESSAGE);
    			return;
    		}else if(!Pattern.matches(pattern, telText.getText())) {
    			JOptionPane.showMessageDialog(null, "�ֻ���ʽ����ȷ��","��ʾ",JOptionPane.ERROR_MESSAGE);
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
    		
    		//��ȡ�û�������������� �鿴��������������Ƿ�һ��
    		//��ȡ����
    		String password = new String(newpwdText.getPassword());
    		String repassword = new String(repwdText.getPassword());
    		
    		
    		if(!password.equals(repassword)) {
    			JOptionPane.showMessageDialog(null, "������Ӧ�����ظ�����һ�£�","��ʾ",JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		
    		if(password.equals("")) {
    			JOptionPane.showMessageDialog(null, "����δ�޸ģ��Ծ������ύ��Ϣ��","��ʾ",JOptionPane.WARNING_MESSAGE);
    			//���û������������  ���þ������ύ
    			password = this.user.getPassword();
    			
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
    		
    		
    		this.user.setUsername(username);
    		this.user.setDepartment(department);
    		this.user.setTel(tel);
    		this.user.setType(type);
    		
    		this.user.setPassword(password);
    		this.user.setEmail(email);
    		this.user.setGender(gender);
    		
    		//System.out.println(this.user);
    		boolean result = false;
    		try {
				result = us.updateUserMsg(this.user);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    		
    		if(result) {
    			JOptionPane.showMessageDialog(null, "�޸��û���Ϣ�ɹ���","��ʾ",JOptionPane.PLAIN_MESSAGE);
    		}else {
    			this.user = this.olduser;
    			JOptionPane.showMessageDialog(null, "�޸��û���Ϣʧ�ܣ�","��ʾ",JOptionPane.WARNING_MESSAGE);
    		}
    		
            
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
