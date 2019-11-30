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
	 * 注册新管理员界面
	 */
	private static final long serialVersionUID = 1L;
	private JLabel namelabel = new JLabel("姓名：");
	private JLabel pwdlabel = new JLabel("密码：");
	private JLabel repwdlabel = new JLabel("重复密码：");
	private JTextField nametext = new JTextField();
	private JTextField pwdtext = new JTextField();
	private JTextField repwdtext = new JTextField();
	private JButton submitbtn = new JButton("提交");
	
	//private Admin admin ;
	private Font font=new Font("仿宋",Font.BOLD,18); 
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
        submitbtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
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

	//修改面板的背景图片
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
				JOptionPane.showMessageDialog(null, "管理员姓名不能为空！","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String pwd  = pwdtext.getText();
			if(pwd.equals("")) {
				JOptionPane.showMessageDialog(null, "密码不能为空！","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(!pwd.equals(repwdtext.getText())) {
				JOptionPane.showMessageDialog(null, "请输入一致的密码！","提示",JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "注册成功！编号为"+id,"提示",JOptionPane.PLAIN_MESSAGE);
				nametext.setText("");
				pwdtext.setText("");
				repwdtext.setText("");
			}else {
				JOptionPane.showMessageDialog(null,"注册失败！","提示",JOptionPane.ERROR_MESSAGE);
				nametext.setText("");
				pwdtext.setText("");
				repwdtext.setText("");
			}
			
		}
		
	}

	//根据指定长度生成纯数字的随机数
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
