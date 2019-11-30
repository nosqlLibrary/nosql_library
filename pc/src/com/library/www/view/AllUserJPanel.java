package com.library.www.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.library.www.po.User;
import com.library.www.po.UserType;
import com.library.www.service.UserService;
import com.library.www.util.CheckHeaderCellRenderer;
import com.library.www.util.MyButtonEditor;
import com.library.www.util.MyButtonRender;
import com.library.www.util.MyJCheckBoxEditor;
import com.library.www.util.MyJCheckBoxRenderer;
import com.library.www.util.TableModelProxy;

public class AllUserJPanel extends JPanel implements ActionListener {

	/**
	 * 管理员查看所有用户界面
	 * 1.查看各个用户的状态与基本信息
	 * 2.修改用户的基本资料
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Object> datas = new Vector<>();//存放表格数据
	private static int curentPageIndex;        //当前页码                  
    private int count;//记录一共从数据库查询到多少条数据
	
	private JTable table;
	private JTextField searchField = new JTextField();
	private JLabel tiplabel = new JLabel();
	
	private JButton searchbtn = new JButton("查询",new ImageIcon(this.getClass().getResource("/Images/search.png")) );
	private JButton nextbtn = new JButton("下一页");
	private JButton prebtn = new JButton("上一页");
	private Font font=new Font("仿宋",Font.BOLD,18); 
	
	private UserService us = new UserService();
	
	public AllUserJPanel() {
		
		setSize(1015,600);
        setLayout(null);
		//初始化表格及表头
        table=new JTable();
	    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    String[] columnNames = new String[]{"全选","用户编号","用户名","用户类型","用户状态","详细信息","删除用户","修改信息"};
	    
	    
	    //设置查询条件框
	    searchField.setBounds(270, 440 , 380, 30); 
	    searchField.setText("用户名/用户编号/学院/用户类型"); 
	    searchField.setForeground(Color.GRAY);
	    
	    //添加监听，显示提示文字
	    searchField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				//获取焦点时，清空提示内容
				String temp = searchField.getText();
				if(temp.equals("用户名/用户编号/学院/用户类型")) {
					searchField.setText("");
					searchField.setForeground(Color.BLACK);
				}

				
			}

			@Override
			public void focusLost(FocusEvent e) {
				//失去焦点时，没有输入内容，显示提示内容
				String temp = searchField.getText();
				if(temp.equals("")) {
					searchField.setForeground(Color.GRAY);
					searchField.setText("用户名/用户编号/学院/用户类型");
				}

			}
	    	
	    });
	    
	    add(searchField);
	    
	    //添加查询按钮
	    searchbtn.setBounds(649, 440,110, 29);
	    searchbtn.setFont(font); 
	    searchbtn.setBackground(new Color(255,255,210));
		searchbtn.addActionListener(this);
		searchbtn.setFocusPainted(false);
		
		
		nextbtn.setBounds(140, 405, 80, 30);
		nextbtn.setFont(font); 
		nextbtn.setContentAreaFilled(false);
		nextbtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件
		nextbtn.addActionListener(this);
		nextbtn.setFocusPainted(false);
		add(nextbtn);
		
		prebtn.setBounds(50, 405, 80, 30);
		prebtn.setFont(font); 
		prebtn.setContentAreaFilled(false);
		prebtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件
		prebtn.addActionListener(this);
		prebtn.setFocusPainted(false);
		add(prebtn);
		
		tiplabel.setFont(font);
		tiplabel.setOpaque(false);
		tiplabel.setBounds(800, 400, 180, 30);
		tiplabel.setVisible(false);
		add(tiplabel);
		
		
		TableModelProxy tableModel = new TableModelProxy(columnNames,datas);
		//应用表格模型
	    this.table.setModel(tableModel);  
	    this.table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
	    
	    //在5，6,7列添加按钮
	    this.table.getColumnModel().getColumn(5).setCellEditor(new MyButtonEditor(this.table,"详细信息"));    	  
	    this.table.getColumnModel().getColumn(5).setCellRenderer(new MyButtonRender("详细信息")); 
	    
	    this.table.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor(this.table,"删除用户"));  
	    this.table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender("删除用户"));
	    
	    this.table.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor(this.table,"修改信息"));  
	    this.table.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender("修改信息"));
	    
	    //在第0列添加对应的处理器
	    this.table.getColumnModel().getColumn(0).setCellEditor(new MyJCheckBoxEditor());  
	    this.table.getColumnModel().getColumn(0).setCellRenderer(new MyJCheckBoxRenderer());
	    
	    getData(1);
	    
        this.table.setRowSelectionAllowed(false);// 禁止表格的选择功能。不然在点击按钮时表格的整行都会被选中。也可以通过其它方式来实现。   
	    table.setRowHeight(30);
	    
		//设置面板大小并把面板添加到大面板中
	    JScrollPane jsp = new JScrollPane(table);
	    jsp.setPreferredSize(new Dimension(1000, 400));
	    jsp.setViewportView(table);
	    jsp.setBounds(0, 0, 1000, 400);
	    jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	    add(jsp,0);
		//刷新表格
		this.table.updateUI();
	    add(searchbtn);
	    
		
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
	
	private void getData(int page) {
		
		ArrayList<User> users = new ArrayList<>();
		try {
			users = us.getAllUsers(page);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		if(users.size() == 0) {
			JOptionPane.showMessageDialog(null, "已经是最后一页");
			return;
		}
		
		
		count = users.size();
		this.datas.clear();
		curentPageIndex = page;
		
		if(users.size()!=0) {
			for (User user : users) {
				ArrayList<UserType> utypes = new ArrayList<>();
				try {
					 utypes = us.getAllUserTypes();
				} catch (Exception e) {
					e.printStackTrace();
				}
		    	int type = user.getType();
		    	String typestr = "";
		    	for (UserType utype : utypes) {
					if(type == utype.getTypeId()) {
						typestr = utype.getTypeName();
					}
				}
		    	
				char status = user.getStatus();
				String stastr ="";
				
				//用户状态  默认：正常（A)，禁用(B)，注销(C)
				if(status == 'A') {
					stastr ="正常";
				}else if(status == 'B') {
					stastr ="禁用";
				}else if(status == 'C') {
					stastr ="注销";
				}
		    	
		    	
				Object[] data =  {new Boolean(false),user.getUserid(),user.getUsername(),typestr,stastr,"","",""};//增加列
				this.datas.add(data);
				
			}
		}
		
		this.table.updateUI();
		tiplabel.setText("总共搜索到" + count + "条记录");
		tiplabel.setVisible(true);
		
	}


	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==searchbtn) {
			this.datas.clear();
			
			//变量初始化
			count  = 0;
			curentPageIndex = 1;
			
			String text = searchField.getText();
			
			if(!text.equals("用户名/用户编号/学院/用户类型")){
				getDataByText(text, 1);
			
			}else {
				getData(1);
			   
			}
			
		}else if(e.getSource() == nextbtn) {
			
			String text = searchField.getText();
			if(!text.equals("用户名/用户编号/学院/用户类型")){
				getDataByText(text,curentPageIndex + 1);
			}else {
				getData(curentPageIndex + 1);
			}
			
            System.out.println("当前页:"+curentPageIndex);
            this.table.updateUI();
 			
		}else if(e.getSource() == prebtn) {

			if (curentPageIndex > 1) {
				
				String text = searchField.getText();
				if(!text.equals("用户名/用户编号/学院/用户类型")){
					getDataByText(text,curentPageIndex - 1);
				}else {
					getData(curentPageIndex - 1);
				}
               
                System.out.println("当前页:"+curentPageIndex);
               
				this.table.updateUI();
            }else {
				JOptionPane.showMessageDialog(null, "已经是第一页");
			}
         
		}
		
		
	}



	private void getDataByText(String text, int page) {
		ArrayList<User> users = new ArrayList<>();
		try {
			users = us.getUserByCondition(text,page);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		if(users.size() == 0) {
			JOptionPane.showMessageDialog(null, "已经是最后一页");
			return;
		}
		count = users.size();
		this.datas.clear();
		curentPageIndex = page;
		
		//{"用户编号","用户名","用户类型","用户状态","详细信息","删除用户","修改信息"}
		for (User user : users) {
			ArrayList<UserType> utypes = new ArrayList<>();
			try {
				 utypes = us.getAllUserTypes();
			} catch (Exception e4) {
				e4.printStackTrace();
			}
	    	int type = user.getType();
	    	String typestr = "";
	    	for (UserType utype : utypes) {
				if(type == utype.getTypeId()) {
					typestr = utype.getTypeName();
				}
			}
	    	
			char status = user.getStatus();
			String stastr ="";
			
			
			//用户状态  默认：正常（A)，禁用(B)，注销(C)
			if(status == 'A') {
				stastr ="正常";
			}else if(status == 'B') {
				stastr ="禁用";
			}else if(status == 'C') {
				stastr ="注销";
			}
			
			
			Object[] data =  {new Boolean(false),user.getUserid(),user.getUsername(),typestr,stastr,"","",""};    //增加列
			this.datas.add(data);
			
		}
		
		this.table.updateUI();
		tiplabel.setText("总共搜索到" + count + "条记录");
		tiplabel.setVisible(true);
		
	}

}
