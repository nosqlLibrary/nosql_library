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
	 * 用户自己修改自己的用户信息
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton submitbtn = new JButton("提交");
	private Font font=new Font("仿宋",Font.BOLD,18);
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
	
	private User user;//记录用户要修改的用户信息
	private User olduser;//记录从前面界面传过来的用户信息  如果信息修改失败可以将用户值再改正到原来的信息
	private UserService us = new UserService();
	private ArrayList<UserType> utypes = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public EditUserMsgJPanel(User user) {
		
		setSize(1015, 600);
		setLayout(null);
		
		this.user = user;
		this.olduser = user;
		
		idLabel = new JLabel("用户编号：");
		idLabel.setBounds(340, 50, 100,25);
		idLabel.setFont(font);
		add(idLabel);
		
		idText.setBounds(430, 50, 150, 25);
		idText.setOpaque(false);
		idText.setText(user.getUserid());
		idText.setEditable(false);
		add(idText);
		
		//设置姓名输入相关组件
		nameLabel = new JLabel("姓名：");		
		nameLabel.setBounds(370,90, 65, 25);
		nameLabel.setFont(font);
		add(nameLabel);
			
		nameText.setBounds(430, 90, 150, 25);
		nameText.setOpaque(false);
		nameText.setText(user.getUsername());
		add(nameText);
		
		//设置性别输入相关组件
		sexLabel = new JLabel("性别：");
		sexLabel.setFont(font);
		sexLabel.setBounds(370, 130, 65, 25);
		add(sexLabel);
		
		if(user.getGender() == 'f') {
			jrbMale=new JRadioButton("男",true);	
	        jrbFemale=new JRadioButton("女");
	        
		}else {
			jrbMale=new JRadioButton("男");	
	        jrbFemale=new JRadioButton("女",true);
		}
		
		jrbMale.setFont(font);
		jrbFemale.setFont(font);
        ButtonGroup buttonGroup=new ButtonGroup();
        buttonGroup.add(jrbMale);
        jrbMale.setBounds(430, 130, 50, 25);
        jrbFemale.setBounds(500,130, 50, 25);
        buttonGroup.add(jrbFemale);
        //设置组件背景透明
        jrbMale.setOpaque(false);
        jrbFemale.setOpaque(false);
        add(jrbMale);
        add(jrbFemale);
        
        //设置用户类型相关组件
		typeLablel = new JLabel("用户类型：");
		typeLablel.setBounds(340, 170,100, 25);
		typeLablel.setFont(font);
		add(typeLablel);
		
		condition.setBounds(430, 170, 150, 25);
		condition.setFont(font);
		//在下拉框添加筛选条件
		condition.addItem("---请选择---");
		
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
        
        //设置所在学院输入相关组件
		departmentLabel = new JLabel("所在学院：");
		departmentLabel.setBounds(340, 210, 100, 25);
		departmentLabel.setFont(font);
		add(departmentLabel);
		
		departmentText.setOpaque(false);
		departmentText.setBounds(430, 210, 150, 25);
		departmentText.setText(user.getDepartment());
		add(departmentText);
		
		//设置手机号码输入相关组件
		telLabel = new JLabel("手机号码：");
		telLabel.setBounds(340, 250, 100, 25);
		telLabel.setFont(font);
		add(telLabel);
		
		telText.setBounds(430, 250, 150, 25);
		telText.setOpaque(false);
		telText.setText(user.getTel()+"");
		add(telText);
		
		//设置密码输入相关组件
		oldpwdLabel = new JLabel("旧密码：");
		oldpwdLabel.setBounds(360, 290, 80, 25);
		oldpwdLabel.setFont(font);
		add(oldpwdLabel);
		
		oldpwdText.setBounds(430, 290, 150, 25);
		oldpwdText.setOpaque(false);
		add(oldpwdText);
		
		//设置密码输入相关组件
		newpwdLabel = new JLabel("新密码：");
		newpwdLabel.setBounds(340, 330, 80, 25);
		newpwdLabel.setFont(font);
		add(newpwdLabel);
		
		newpwdText.setBounds(430, 330, 150, 25);
		newpwdText.setOpaque(false);
		add(newpwdText);
		
		//设置密码输入相关组件
		repwdLabel = new JLabel("重复密码：");
		repwdLabel.setBounds(340, 370,100, 25);
		repwdLabel.setFont(font);
		add(repwdLabel);
		
		repwdText.setBounds(430, 370, 150, 25);
		repwdText.setOpaque(false);
		add(repwdText);
		
		//设置邮箱输入相关组件
		emailLabel = new JLabel("邮箱：");
		emailLabel.setBounds(370, 410,100, 25);
		emailLabel.setFont(font);
		add(emailLabel);
		
		emailText.setOpaque(false);
		emailText.setBounds(430, 410, 150, 25);
		emailText.setText(user.getEmail());
		add(emailText);
		
		
		submitbtn = new JButton("确定");
        submitbtn.setBounds(400, 450, 65, 25);
        submitbtn.setContentAreaFilled(false);
        submitbtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
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
			//获取输入的旧密码，查看是否与用户当前密码一致
			String oldpwd = new String(oldpwdText.getPassword());
			if(oldpwd.equals("")) {
				JOptionPane.showMessageDialog(null, "请输入旧密码再进行操作！","提示",JOptionPane.ERROR_MESSAGE);
    			return;
			}else if(!oldpwd.equals(this.user.getPassword())){
				JOptionPane.showMessageDialog(null, "旧密码输入有误，请重新输入！","提示",JOptionPane.ERROR_MESSAGE);
    			return;
			}
			
			//获取用户名
    		String username = nameText.getText();
    		if(username.equals("")) {
    			JOptionPane.showMessageDialog(null, "用户名不能为空","提示",JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		
    		//获取性别
    		boolean man = jrbMale.isSelected();
            char gender = 'f';
            if(!man)
            	gender = 'm';
            
            //获取用户类型  
            //获取选中的用户类型
            int typeIndex = condition.getSelectedIndex();
            if(typeIndex==0) {
            	JOptionPane.showMessageDialog(null, "请选择用户类型","提示",JOptionPane.ERROR_MESSAGE);
    			return;
            }
            
            int type = this.utypes.get(typeIndex-1).getTypeId();
            
    		//获取部门
            String department = departmentText.getText();
    		if(department.equals("")) {
    			JOptionPane.showMessageDialog(null, "所在学院不能为空","提示",JOptionPane.ERROR_MESSAGE);
				return;
    		}
    		
    		String pattern = "^1[3-9]\\d{9}$";//正则表达式
    		//获取电话号码
    		if(telText.getText().equals("")) {
    			JOptionPane.showMessageDialog(null, "手机号码不能为空","提示",JOptionPane.ERROR_MESSAGE);
    			return;
    		}else if(!Pattern.matches(pattern, telText.getText())) {
    			JOptionPane.showMessageDialog(null, "手机格式不正确！","提示",JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		long tel = 0 ;
    		if(isInteger(telText.getText())) {
    			if(telText.getText().length() ==11)
    				tel = Long.parseLong(telText.getText());
    			else {
    				JOptionPane.showMessageDialog(null, "手机应为11位数字","提示",JOptionPane.ERROR_MESSAGE);
        			return;
    			}
    		}else{
    			JOptionPane.showMessageDialog(null, "手机应为11位数字！","提示",JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		
    		//获取用户重新输入的密码 查看与输入的新密码是否一致
    		//获取密码
    		String password = new String(newpwdText.getPassword());
    		String repassword = new String(repwdText.getPassword());
    		
    		
    		if(!password.equals(repassword)) {
    			JOptionPane.showMessageDialog(null, "新密码应该与重复密码一致！","提示",JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		
    		if(password.equals("")) {
    			JOptionPane.showMessageDialog(null, "密码未修改，以旧密码提交信息！","提示",JOptionPane.WARNING_MESSAGE);
    			//如果没有输入新密码  则用旧密码提交
    			password = this.user.getPassword();
    			
    		}
    		
    		pattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    		//获取邮箱
    		String email = emailText.getText();
    		if(email.equals("")) {
    			JOptionPane.showMessageDialog(null, "邮箱不能为空","提示",JOptionPane.ERROR_MESSAGE);
    			return;
    		}else if(!Pattern.matches(pattern, email)) {
    			JOptionPane.showMessageDialog(null, "邮箱格式不正确！","提示",JOptionPane.ERROR_MESSAGE);
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
    			JOptionPane.showMessageDialog(null, "修改用户信息成功！","提示",JOptionPane.PLAIN_MESSAGE);
    		}else {
    			this.user = this.olduser;
    			JOptionPane.showMessageDialog(null, "修改用户信息失败！","提示",JOptionPane.WARNING_MESSAGE);
    		}
    		
            
		}
		
	}

	//判断字符串是否全为数字
	public static boolean isInteger(String str) {  
		for(int i=str.length();--i>=0;){
	        int chr=str.charAt(i);
	        if(chr<48 || chr>57)
	            return false;
	    }
	   return true; 
	}

}
