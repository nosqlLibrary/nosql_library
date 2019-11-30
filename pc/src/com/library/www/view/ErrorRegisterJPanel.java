package com.library.www.view;

import java.awt.Color;
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

import com.library.www.po.Book;
import com.library.www.po.Borrow;
import com.library.www.po.UniqueBook;
import com.library.www.po.User;
import com.library.www.service.BookService;
import com.library.www.service.BorrowService;
import com.library.www.util.CheckHeaderCellRenderer;
import com.library.www.util.MyButtonEditor;
import com.library.www.util.MyButtonRender;
import com.library.www.util.MyJCheckBoxEditor;
import com.library.www.util.MyJCheckBoxRenderer;
import com.library.www.util.TableModelProxy;

public class ErrorRegisterJPanel extends JPanel implements ActionListener {

	/**
	 * 图书异常登记界面
	 * 1.主要是对图书状态进行 修改      在库(A)，借出(B), 损毁(C), 丢失(D)，
	 * 2.能进行批量或单一图书进行异常登记（图书损毁、图书丢失）
	 * 	根据用户id及书本其他信息（用户能提供的，比如书名）来得到书本的具体信息，对书本的信息进行修改。
	 * 	批量针对如果某个用户同时丢失了多本图书。
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton searchbtn;
	private JButton nextbtn = new JButton("下一页");
	private JButton prebtn = new JButton("上一页");
	private JLabel tiplabel = new JLabel();
	private JTextField searchField = new JTextField();
	private Font font=new Font("仿宋",Font.BOLD,18); 
	
	private JTable table;
	private Vector<Object> datas = new Vector<>();//存放表格数据
	
	private static int curentPageIndex;        //当前页码                  
    private int count;//记录一共从数据库查询到多少条数据
	//private Admin admin;
	private BookService bookService = new BookService();
	private BorrowService borrowService = new BorrowService();
	//private UserService uservice = new UserService();
	
	public ErrorRegisterJPanel() {
		
		
		setSize(1015,600);
		setLayout(null);
		
		//初始化表格及表头
        table=new JTable();
	    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    String[] columnNames = new String[]{"全选","图书编号","书名","图书状态","用户编号","用户名","详细信息","损毁","丢失"};
	    
	    TableModelProxy tableModel = new TableModelProxy(columnNames,datas);
		
	    //应用表格模型
	    this.table.setModel(tableModel);  
	    this.table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
	    
	    //在6，7，8列添加按钮
	    this.table.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor(this.table,"详细信息"));    	  
	    this.table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender("详细信息")); 
	    
	    this.table.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor(this.table,"损毁"));  
	    this.table.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender("损毁"));
	    
	    this.table.getColumnModel().getColumn(8).setCellEditor(new MyButtonEditor(this.table,"丢失"));  
	    this.table.getColumnModel().getColumn(8).setCellRenderer(new MyButtonRender("丢失"));
	    //在第0列添加对应的处理器
	    this.table.getColumnModel().getColumn(0).setCellEditor(new MyJCheckBoxEditor());  
	    this.table.getColumnModel().getColumn(0).setCellRenderer(new MyJCheckBoxRenderer());
	    

	    
        this.table.setRowSelectionAllowed(false);// 禁止表格的选择功能。不然在点击按钮时表格的整行都会被选中。也可以通过其它方式来实现。   
	    table.setRowHeight(30);
	    
	    //设置面板大小并把面板添加到大面板中
	    JScrollPane jsp = new JScrollPane(table);
	    jsp.setViewportView(table);
	    jsp.setBounds(0, 110, 1000, 490);
	    jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		searchbtn = new JButton("查询",new ImageIcon(this.getClass().getResource("/Images/search.png")) );
		
		
		searchbtn.setFont(font); 
		searchbtn.setBounds(619, 30, 120, 29);
		searchbtn.setBackground(new Color(255,255,210));
		searchbtn.addActionListener(this);
		searchbtn.setFocusPainted(false);
		
		
		//设置查询条件框
	    searchField.setBounds(240,30, 380, 30); 
	    searchField.setText("书名/ISBN/图书编号/用户名/用户编号"); 
	    searchField.setForeground(Color.GRAY);
	    
	    //添加监听，显示提示文字
	    searchField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				//获取焦点时，清空提示内容
				String temp = searchField.getText();
				if(temp.equals("书名/ISBN/图书编号/用户名/用户编号")) {
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
					searchField.setText("书名/ISBN/图书编号/用户名/用户编号");
				}

			}
	    	
	    });
	    
	    
	    nextbtn.setBounds(100, 65, 80, 30);
		nextbtn.setFont(font); 
		nextbtn.setContentAreaFilled(false);
		nextbtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件
		nextbtn.addActionListener(this);
		nextbtn.setFocusPainted(false);
		add(nextbtn);
		
		prebtn.setBounds(10, 65, 80, 30);
		prebtn.setFont(font); 
		prebtn.setContentAreaFilled(false);
		prebtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件
		prebtn.addActionListener(this);
		prebtn.setFocusPainted(false);
		add(prebtn);
		
		tiplabel.setFont(font);
		tiplabel.setOpaque(false);
		tiplabel.setBounds(800, 65, 180, 30);
		tiplabel.setVisible(false);
		
		add(tiplabel);
		add(searchField);
		add(searchbtn);
		add(jsp);
		
		
		setVisible(true);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == searchbtn ) {
			this.datas.clear();
			String text = searchField.getText();
			
			//变量初始化
			count  = 0;
			curentPageIndex = 1;
			
			
			if(!text.equals("书名/ISBN/图书编号/用户名/用户编号")){
				getDataByText(text,1);
			}else {
				getData(1);
			}
			
		   
			this.table.updateUI();
			
			
		}else if(e.getSource() == nextbtn) {
			
			String text = searchField.getText();
			if(!text.equals("书名/ISBN/图书编号/用户名/用户编号")){
				getDataByText(text,curentPageIndex + 1);
			}else {
				getData(curentPageIndex + 1);
			}
			
            System.out.println("当前页:"+curentPageIndex);
            this.table.updateUI();
			
		}else if(e.getSource() == prebtn) {
			
			if (curentPageIndex > 1) {
				
				String text = searchField.getText();
				if(!text.equals("书名/ISBN/图书编号/用户名/用户编号")){
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
			
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void getDataByText(String text,int page) {
		/**
		 * 搜索框输入的有可能是用户的信息，也可能是书本的信息
		 * 1.输入图书信息且该图书没有被借阅过：从数据库中获取符合条件的图书信息
		 * 2.输入的是图书信息且图书曾被借阅过：从数据库中获取到相应的借阅记录与图书信息
		 * ---上边两种情况都是遍历所有图书集合和借阅记录集合，如果图书编号一致，则应显示出用户信息，否则，只显示图书信息。
		 * 3.如果输入的是用户信息，那么图书集合为空，借阅记录集合可能有内容
		 * ---遍历借阅记录表，插入内容
		 */
		
		ArrayList<ArrayList> booksByCondition = new ArrayList<>();
		ArrayList<ArrayList> borrowsByCondition = new ArrayList<>();
		ArrayList<User> userByCondition = new ArrayList<>();
		ArrayList<Borrow> borrowMsg = new ArrayList<>();
		ArrayList<Book> borrowBook = new ArrayList<>();
		ArrayList<UniqueBook> borrowUbook = new ArrayList<>();
		try {
			borrowsByCondition =  borrowService.getAllBorrowsByCondition(text);
			booksByCondition = bookService.getBooksByCondition(text,page);
			
			borrowMsg = borrowsByCondition.get(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<Book> books = booksByCondition.get(1);
		ArrayList<UniqueBook> uniqueBooks = booksByCondition.get(0);
		userByCondition = borrowsByCondition.get(1);
		if(uniqueBooks.size() == 0 &&  borrowMsg.size() == 0) {
			JOptionPane.showMessageDialog(null, "搜索不到内容！");
			return;
		}
		
		curentPageIndex = page;
		this.datas.clear();
		count = 0;
		
		//输入的是用户信息  即图书集合中搜索不到内容
		if(uniqueBooks.size() == 0) {
			
			borrowUbook = booksByCondition.get(2);
			borrowBook = booksByCondition.get(3);
			for (int i = 0; i < borrowMsg.size(); i++) {
				Borrow bo = borrowMsg.get(i);
				User user = userByCondition.get(i);
				UniqueBook ubook = borrowUbook.get(i);
				Book book = borrowBook.get(i);
				
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
				
				if(bo.getBid().equals(ubook.getBid()) && bo.getBackdate()==null) {
					//图书编号与借阅信息的编号相同   并且没有归还日期时，要添加用户信息
					count++;
					Object[] data =  {new Boolean(false),ubook.getBid(),book.getBname(),bstatus,bo.getUserid(),user.getUsername(),"","",""};    //增加列
					this.datas.add(data);
					
					
				}else if(bo.getBid().equals(ubook.getBid()) && bo.getBackdate()!=null){
					//图书编号与借阅信息的编号相同     但有归还日期时，不用添加用户信息
					count++;
					Object[] data =  {new Boolean(false),ubook.getBid(),book.getBname(),bstatus,"","","","",""};    //增加列
					this.datas.add(data);
					
				}
				
			}
			
		}else {//输入的是图书信息 两个集合可能都有内容
			for (UniqueBook uniqueBook : uniqueBooks) {
				for (Book bookDetail : books) {
					if(uniqueBook.getIsbn().equals(bookDetail.getIsbn())){ 
						//将图书状态修改为字符串格式
						String bstatus = "";
						char st = uniqueBook.getStatus();
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
						
						int isBorrow = 0;//标记是否图书信息也在借阅记录中
						//如果借阅记录不为空
						for (int i = 0; i < borrowMsg.size(); i++) {
							Borrow bo = borrowMsg.get(i);
							User u = userByCondition.get(i);
							if(bo.getBid().equals(uniqueBook.getBid()) && isBorrow==0 && bo.getBackdate()==null) {
								//图书编号与借阅信息的编号相同   并且没有归还日期时，要添加用户信息
								count++;
								Object[] data =  {new Boolean(false),uniqueBook.getBid(),bookDetail.getBname(),bstatus,bo.getUserid(),u.getUsername(),"","",""};    //增加列
								this.datas.add(data);
								
								isBorrow = 1;
							}else if(bo.getBid().equals(uniqueBook.getBid()) && isBorrow==0 && bo.getBackdate()!=null){
								//图书编号与借阅信息的编号相同     但有归还日期时，不用添加用户信息
								count++;
								Object[] data =  {new Boolean(false),uniqueBook.getBid(),bookDetail.getBname(),bstatus,"","","","",""};    //增加列
								this.datas.add(data);
								isBorrow = 1;
							}
						}
						
						if(isBorrow == 0) {
							count++;
							Object[] data =  {new Boolean(false),uniqueBook.getBid(),bookDetail.getBname(),bstatus,"","","","",""};    //增加列
							this.datas.add(data);
							
						}
							
					}
				}
				
			}
		
		
		}
		
		
		this.table.updateUI();
		tiplabel.setText("总共搜索到" + count + "条记录");
		tiplabel.setVisible(true);
	}

	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes"})
	public void getData(int page) {
		
		
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<UniqueBook> singleBooks = new ArrayList<>();
		ArrayList<ArrayList> allBorrows = new ArrayList<>();
		
		ArrayList<User> users = new ArrayList<>();
		//ArrayList<Book> borrowBook = new ArrayList<>();
		//ArrayList<UniqueBook> borrowUbook = new ArrayList<>();
		ArrayList<Borrow> borrows = new ArrayList<>();
		
		try {
			books = bookService.getAllBooks();
			singleBooks = bookService.getSomeSingleBooks(page);
			allBorrows = borrowService.getAllBorrows();
			borrows = allBorrows.get(0);
			users = allBorrows.get(1);
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		if(singleBooks.size() == 0 && allBorrows.size()==0) {
			JOptionPane.showMessageDialog(null, "没有获取到内容！");
			return;
		}else if(singleBooks.size() == 0) {
			JOptionPane.showMessageDialog(null, "已经是最后一页！");
			return;
		}
		
		//变量初始化
		count  = 0;
		curentPageIndex = page;
		this.datas.clear();
				
		for (UniqueBook ubook : singleBooks) {
			for (Book book : books) {
				if(book.getIsbn().equals(ubook.getIsbn())) {
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
					
					int j = 0;//标记图书是否被人借过
					for (int i = 0; i < borrows.size(); i++) {
						Borrow bo = borrows.get(i);
						User user = users.get(i);
						
					
						if(bo.getBid().equals(ubook.getBid()) && bo.getBackdate()==null && j == 0) {
							//图书编号与借阅信息的编号相同   并且没有归还日期时，要添加用户信息
							count++;
							Object[] data =  {new Boolean(false),ubook.getBid(),book.getBname(),bstatus,bo.getUserid(),user.getUsername(),"","",""};    //增加列
							this.datas.add(data);
							j = 1;
						}else if(bo.getBid().equals(ubook.getBid()) && bo.getBackdate()!=null && j == 0){
							//图书编号与借阅信息的编号相同     但有归还日期时，不用添加用户信息
							count++;
							Object[] data =  {new Boolean(false),ubook.getBid(),book.getBname(),bstatus,"","","","",""};    //增加列
							this.datas.add(data);
							j = 1;
						}
					}
					
					if(j == 0) {
						count++;
						Object[] data =  {new Boolean(false),ubook.getBid(),book.getBname(),bstatus,"","","","",""};    //增加列
						this.datas.add(data);
						
					}
						
					
				}
			}
		}
		
				
		
		this.table.updateUI();
		tiplabel.setText("总共搜索到" + count + "条记录");
		tiplabel.setVisible(true);
		
	}


}
