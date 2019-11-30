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
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.library.www.po.Book;
import com.library.www.po.Borrow;
import com.library.www.po.UniqueBook;
import com.library.www.po.User;
import com.library.www.service.BorrowService;
import com.library.www.util.CheckHeaderCellRenderer;
import com.library.www.util.TableModelProxy;

public class UserBorrowMsgJPanel extends JPanel implements ActionListener {
	/**
	 * 某用户的借阅信息界面
	 * 1.查询自身的借阅信息
	 * 2.能按书名/ISBN/图书编号/借阅时间（如3个月内，12个月内）对记录进行搜索
	 * 3.能够筛选出已还和未还的信息
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Object> datas = new Vector<>();//存放表格数据
	//private Vector<Object> smalldatas = new Vector<>();//存放当前表格的数据
	private static int curentPageIndex;        //当前页码                  
    
    private int count;//记录一共从数据库查询到多少条数据
    private int selectIndex = 0;//记录是否要获取所有记录 或 只要已归还/未归还的数据
	
	
	private JTable table;
	private JLabel timelabel = new JLabel("筛选借阅时间：");
	
	private JLabel tiplabel = new JLabel();
	@SuppressWarnings("rawtypes")
	private JComboBox condition = new JComboBox();
	private JTextField searchField = new JTextField();
	private JRadioButton alljrb,backjrb,notbackjrb;
	private  ButtonGroup buttonGroup = new ButtonGroup();
	
	private JButton nextbtn = new JButton("下一页");
	private JButton prebtn = new JButton("上一页");
	private JButton searchbtn = new JButton("查询",new ImageIcon(this.getClass().getResource("/Images/search.png")) );
	private Font font=new Font("仿宋",Font.BOLD,18); 
	private BorrowService bs = new BorrowService();
	@SuppressWarnings("rawtypes")
	private ArrayList<ArrayList> borrowsDetail = new ArrayList<>();
	
	private User user;
	
	@SuppressWarnings("unchecked")
	public UserBorrowMsgJPanel(User u) {
		
        setSize(1015,600);
        
        this.user = u;
        
        setLayout(null);
		//初始化表格及表头
        table=new JTable();
	    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    String[] columnNames = new String[]{"编号","图书编号","图书名","当前状态","借阅时间","应还日期","归还时间"};
	    
	    TableModelProxy tableModel = new TableModelProxy(columnNames,datas);
		
		 
	    //应用表格模型
	    this.table.setModel(tableModel);  
	    this.table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
	   
	    this.table.setRowHeight(30);
	    this.table.setRowSelectionAllowed(false);// 禁止表格的选择功能。不然在点击按钮时表格的整行都会被选中。也可以通过其它方式来实现。   

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
	    
		//设置筛选条件：显示全部图书/显示未还图书/显示已还图书
		alljrb = new JRadioButton("全部记录",true);
		notbackjrb = new JRadioButton("未还记录");
		backjrb = new JRadioButton("已还记录");
		
		alljrb.setBounds(250, 465, 120, 30);
		alljrb.setFont(font);
		alljrb.setOpaque(false);
        
		notbackjrb.setBounds(400, 465, 120, 30);
		notbackjrb.setFont(font);
		notbackjrb.setOpaque(false);
		
		backjrb.setBounds(550, 465, 120, 30);
		backjrb.setFont(font);
		backjrb.setOpaque(false);
		
		buttonGroup.add(alljrb);
		buttonGroup.add(notbackjrb);
		buttonGroup.add(backjrb);
		
		add(alljrb);
		add(notbackjrb);
		add(backjrb);
		
	    
	    //设置查询条件框
	    searchField.setBounds(250, 430 , 400, 30); 
	    searchField.setText("书名/ISBN/图书编号"); 
	    searchField.setForeground(Color.GRAY);
	    
	    
	    //添加监听，显示提示文字
	    searchField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				//获取焦点时，清空提示内容
				String temp = searchField.getText();
				if(temp.equals("书名/ISBN/图书编号")) {
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
					searchField.setText("书名/ISBN/图书编号");
				}

			}
	    	
	    });
	    
	    
	    add(searchField);
	    
	    //添加查询按钮
	    searchbtn.setBounds(649, 430, 110, 29);
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
		
		
		timelabel.setBounds(250, 508, 150, 30);
        timelabel.setFont(font);
        
        //筛选条件下拉框
  		condition.setBounds(378, 510, 360, 25);
  		condition.setFont(font);
  		//在下拉框添加筛选条件
  		condition.addItem("全部");
  		condition.addItem("最近一天");
  		condition.addItem("最近一周");
  		condition.addItem("最近一个月");
  		condition.addItem("最近三个月");
  		condition.addItem("最近半年");
  		condition.addItem("最近一年");
		
        add(timelabel);
        add(condition);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == searchbtn) {
			
    		if(alljrb.isSelected()) {
    			selectIndex = 1;//全部记录
    		}else if(notbackjrb.isSelected()) {
    			selectIndex = 2;//只要未归还的记录
    		}else {
    			selectIndex = 3;//只要归还了的记录
    		}
    		
			String text = searchField.getText();
			this.datas.clear();
			
			//变量初始化
			count  = 0;
			curentPageIndex = 1;
			
			
			if(text.equals("书名/ISBN/图书编号")){
				getData(1);
			
			}else {
				getDataByText(text,1);
			}
		
			this.table.updateUI();
			tiplabel.setText("总共搜索到" + count + "条记录");
			tiplabel.setVisible(true);
		}else if(e.getSource() == nextbtn) {
			String text = searchField.getText();
			if(!text.equals("书名/ISBN/图书编号")){
				getDataByText(text,curentPageIndex + 1);
			}else {
				getData(curentPageIndex + 1);
			}
			
            System.out.println("当前页:"+curentPageIndex);
            this.table.updateUI();
			
 			
		}else if(e.getSource() == prebtn) {
			if (curentPageIndex > 1) {
				
				String text = searchField.getText();
				if(!text.equals("书名/ISBN/图书编号")){
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

	public void getDataByText(String text,int page) {
		//按照输入的条件进行查询
		//根据输入的条件给borrows重新赋值
		borrowsDetail.clear();
		try {
			borrowsDetail = bs.getUserBorrowsByCondition(text,user,page);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<UniqueBook> ubooks = new ArrayList<>();
		
		ArrayList<Borrow> borrows = new ArrayList<>();
		
		borrows = borrowsDetail.get(0);
		ubooks = borrowsDetail.get(1);
		books = borrowsDetail.get(2);
		
		if(page == 1 && borrows.size() == 0 ) {
			JOptionPane.showMessageDialog(null, "搜索不到数据！");
			this.table.updateUI();
			return;
		}
		
		if(borrows.size() == 0 ) {
			JOptionPane.showMessageDialog(null, "已经是最后一页");
			return;
		}else {
			//变量初始化
			count  = 0;
			curentPageIndex = page;
			this.datas.clear();
			
			
    		
		
			if(condition.getSelectedItem() == "最近一天" ) {
				selectData(1);
			}else if(condition.getSelectedItem() == "最近一周" ) {
				selectData(7);
			}else if(condition.getSelectedItem() == "最近一个月" ) {
				selectData(30);
			}else if(condition.getSelectedItem() == "最近三个月" ) {
				selectData(90);
			}else if(condition.getSelectedItem() == "最近半年" ) {
				selectData(180);
			}else if(condition.getSelectedItem() == "最近一年" ) {
				selectData(365);
			}else {
				
				
				for (int i = 0; i < borrows.size(); i++) {
					Borrow borrow = borrows.get(i);
					UniqueBook ubook = ubooks.get(i);
					Book book = books.get(i);
				
					//将图书状态修改为字符串格式
					String bstatus = "";
					char st = ubook.getStatus();
					//在库（A)，借出(B), 损毁(C), 丢失(D)
					if(st == 'A') {
						bstatus = "在库";
					}else if(st == 'B') {
						bstatus = "借出";
					}else if(st == 'C') {
						bstatus = "损毁";
					}else if(st == 'D') {
						bstatus = "丢失";
					}
					count++;
					if(borrow.getBackdate() != null && (selectIndex == 1||selectIndex == 3)) {
						
						Object[] data =  {borrow.getId(),borrow.getBid(),book.getBname(),bstatus,borrow.getTakedate().toString(),borrow.getDeadline().toString(),borrow.getBackdate().toString(),};    //增加列
						this.datas.add(data);
					}else if(borrow.getBackdate() == null&&(selectIndex == 1||selectIndex == 2)){
						Object[] data =  {borrow.getId(),borrow.getBid(),book.getBname(),bstatus,borrow.getTakedate().toString(),borrow.getDeadline().toString(),""};    //增加列
						this.datas.add(data);
					}
						
					
				}
			}
		}

		this.table.updateUI();
		tiplabel.setText("总共搜索到" + count + "条记录");
		tiplabel.setVisible(true);
	}
	
	@SuppressWarnings("unchecked")
	private void selectData(int selectdays) {
		Date nowdate = new Date();
		int days;
		long time;
		
		
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<UniqueBook> ubooks = new ArrayList<>();
		
		ArrayList<Borrow> borrows = new ArrayList<>();
		
		borrows = borrowsDetail.get(0);
		
		ubooks = borrowsDetail.get(1);
		books = borrowsDetail.get(2);
		
		if(borrows.size()==0) {
			JOptionPane.showMessageDialog(null, "已经是最后一页！");
			return;
		}else {
		
			for (int i = 0; i < borrows.size(); i++) {
				
				Borrow borrow = borrows.get(i);
				time = nowdate.getTime()-borrow.getTakedate().getTime();
				days=(int) Math.floor(time/(24*3600*1000));
				
				if(days <= selectdays) {
					
					UniqueBook ubook = ubooks.get(i);
					Book book = books.get(i);
					
					//将图书状态修改为字符串格式
					String bstatus = "";
					char st = ubook.getStatus();
					//在库（A)，借出(B), 损毁(C), 丢失(D)
					if(st == 'A') {
						bstatus = "在库";
					}else if(st == 'B') {
						bstatus = "借出";
					}else if(st == 'C') {
						bstatus = "损毁";
					}else if(st == 'D') {
						bstatus = "丢失";
					}
					count++;
					if(borrow.getBackdate() != null && (selectIndex == 1||selectIndex == 3)) {
						Object[] data =  {borrow.getId(),borrow.getBid(),book.getBname(),bstatus,borrow.getTakedate().toString(),borrow.getDeadline().toString(),borrow.getBackdate().toString()};    //增加列
						this.datas.add(data);
					}else if(borrow.getBackdate()== null && (selectIndex == 1||selectIndex == 2)){
						Object[] data =  {borrow.getId(),borrow.getBid(),book.getBname(),bstatus,borrow.getTakedate().toString(),borrow.getDeadline().toString(),""};    //增加列
						this.datas.add(data);
					}
					
				}
			}
		
		}
		this.table.updateUI();
		tiplabel.setText("总共搜索到" + count + "条记录");
		tiplabel.setVisible(true);
		
	}

	
	@SuppressWarnings("unchecked")
	public void getData(int page)   {
		//显示全部信息
		borrowsDetail.clear();
		try {
			borrowsDetail = this.bs.getUserBorrowMsg(user,page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<Book> books = borrowsDetail.get(2);
		ArrayList<UniqueBook> ubooks = borrowsDetail.get(1);
		ArrayList<Borrow> borrows = borrowsDetail.get(0);
		
		if(borrows.size() == 0) {
			JOptionPane.showMessageDialog(null, "已经是最后一页！");
			return;
		}
		
		//变量初始化
		count  = 0;
		curentPageIndex = page;
		this.datas.clear();
		
		
		if(borrows.size()!=0) {
			if(condition.getSelectedItem() == "最近一天" ) {
				selectData(1);
			}else if(condition.getSelectedItem() == "最近一周" ) {
				selectData(7);
			}else if(condition.getSelectedItem() == "最近一个月" ) {
				selectData(30);
			}else if(condition.getSelectedItem() == "最近三个月" ) {
				selectData(90);
			}else if(condition.getSelectedItem() == "最近半年" ) {
				selectData(180);
			}else if(condition.getSelectedItem() == "最近一年" ) {
				selectData(365);
			}else {
				for (int i = 0; i < borrows.size(); i++) {
					Borrow borrow = borrows.get(i);
					
					UniqueBook ubook = ubooks.get(i);
					Book book = books.get(i);
					count = borrows.size();
						
					//将图书状态修改为字符串格式
					String bstatus = "";
					char st = ubook.getStatus();
					//在库（A)，借出(B), 损毁(C), 丢失(D)
					if(st == 'A') {
						bstatus = "在库";
					}else if(st == 'B') {
						bstatus = "借出";
					}else if(st == 'C') {
						bstatus = "损毁";
					}else if(st == 'D') {
						bstatus = "丢失";
					}
						
					if(borrow.getBackdate() != null && (selectIndex == 1||selectIndex == 3)) {
						Object[] data =  {borrow.getId(),borrow.getBid(),book.getBname(),bstatus,borrow.getTakedate().toString(),borrow.getDeadline().toString(),borrow.getBackdate().toString()};    //增加列
						this.datas.add(data);
					}else if(borrow.getBackdate() == null && (selectIndex == 1||selectIndex == 2)){
						Object[] data =  {borrow.getId(),borrow.getBid(),book.getBname(),bstatus,borrow.getTakedate().toString(),borrow.getDeadline().toString(),""};    //增加列
						this.datas.add(data);
					}
					
					
				}
			}
		}
		this.table.updateUI();
		
	}
	

}
