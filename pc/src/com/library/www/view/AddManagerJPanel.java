package com.library.www.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.library.www.po.Admin;
import com.library.www.service.UserService;

public class AddManagerJPanel extends JPanel implements ActionListener {

	/**
	 * ע���¹���Ա����
	 */
	private static final long serialVersionUID = 1L;
	private JLabel namelabel = new JLabel("������");
	private JLabel pwdlabel = new JLabel("���룺");
	private JLabel repwdlabel = new JLabel("�ظ����룺");
	private JTextField nametext = new JTextField();
	private JTextField pwdtext = new JTextField();
	private JTextField repwdtext = new JTextField();
	private JButton submitbtn = new JButton("�ύ");
	
	//private Admin admin ;
	private Font font=new Font("����",Font.BOLD,18); 
	private UserService us = new UserService();
	
	public AddManagerJPanel() {
		
		setSize(1015,600);
        setLayout(null);
        
        
        namelabel.setBounds(360, 150, 400, 30);
        namelabel.setFont(font);
        
        nametext.setBounds(425, 150, 200, 25);
		nametext.setOpaque(false);
        
        pwdlabel.setBounds(360, 200, 400, 30);
        pwdlabel.setFont(font);
        
        pwdtext.setBounds(425, 200, 200, 25);
        pwdtext.setOpaque(false);
        
        repwdlabel.setBounds(330, 250, 400, 30);
        repwdlabel.setFont(font);
        
        repwdtext.setBounds(425, 250, 200, 25);
        repwdtext.setOpaque(false);
        
        submitbtn.setBounds(450, 300, 100, 30);
        submitbtn.setContentAreaFilled(false);
        submitbtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
        submitbtn.setFont(font);
        submitbtn.addActionListener(this);
        submitbtn.setFocusPainted(false);
		
		
		add(namelabel);
		add(nametext);
		add(pwdlabel);
		add(pwdtext);
		add(repwdlabel);
		add(repwdtext);
		add(submitbtn);
		
		
		setVisible(true);
	}

	//�޸����ı���ͼƬ
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
		if(e.getSource() == submitbtn) {
			String name = nametext.getText();
			if(name.equals("")) {
				JOptionPane.showMessageDialog(null, "����Ա��������Ϊ�գ�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String pwd  = pwdtext.getText();
			if(pwd.equals("")) {
				JOptionPane.showMessageDialog(null, "���벻��Ϊ�գ�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(!pwd.equals(repwdtext.getText())) {
				JOptionPane.showMessageDialog(null, "������һ�µ����룡","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String id = createData(10);
			Admin newadmin = new Admin(id,name,pwd);
			System.out.println(newadmin);
			boolean result = false;
			try {
				result = us.addNewAdmin(newadmin);
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			
			if(result) {
				JOptionPane.showMessageDialog(null, "ע��ɹ������Ϊ"+id,"��ʾ",JOptionPane.PLAIN_MESSAGE);
				nametext.setText("");
				pwdtext.setText("");
				repwdtext.setText("");
			}else {
				JOptionPane.showMessageDialog(null,"ע��ʧ�ܣ�","��ʾ",JOptionPane.ERROR_MESSAGE);
				nametext.setText("");
				pwdtext.setText("");
				repwdtext.setText("");
			}
			
		}
		
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
