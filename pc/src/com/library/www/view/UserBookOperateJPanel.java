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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.library.www.po.Book;
import com.library.www.po.UniqueBook;
import com.library.www.po.User;
import com.library.www.service.BookService;
import com.library.www.util.CheckHeaderCellRenderer;
import com.library.www.util.MyButtonEditor;
import com.library.www.util.MyButtonRender;
import com.library.www.util.TableModelProxy;

public class UserBookOperateJPanel extends JPanel implements ActionListener {
	/**
	 * 用户图书操作界面
	 * 	主要是查看当前图书馆有什么书
	 */
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	private Vector<Object> datas = new Vector<>();//存放表格数据
	
	
	private static int curentPageIndex;        //当前页码                  
    private int count;//记录一共从数据库查询到多少条数据
	
	protected void paintComponent(Graphics g) {  
        Image bg;  
        try {  
            bg = (Image) new ImageIcon(this.getClass().getResource("/Images/bopbg.jpg")).getImage(); 
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    };
    
	
	private JTable table;
	private JTextField searchField = new JTextField();
	@SuppressWarnings("rawtypes")
	private JComboBox condition = new JComboBox();
	private JButton searchbtn = new JButton("查询",new ImageIcon(this.getClass().getResource("/Images/search.png")) );
	private Font font=new Font("仿宋",Font.BOLD,18); 
	private JButton nextbtn = new JButton("下一页");
	private JButton prebtn = new JButton("上一页");
	//private JButton returnbtn = new JButton("返回");
	
	private ArrayList<String> bookTypeNames;
	private JLabel typeLabel = new JLabel("筛选图书类别：");
	private JLabel tiplabel = new JLabel();
	
	@SuppressWarnings("unchecked")
	public UserBookOperateJPanel(User u) {
		
		setSize(1015,600);
        setLayout(null);
        
        
		//初始化表格及表头
        table=new JTable();
	    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    String[] columnNames = new String[]{"编号","书名","图书状态","作者","出版社","ISBN","详细信息"};
	    //设置查询条件框
	    searchField.setBounds(250, 450 , 400, 30); 
	    searchField.setText("书名/作者/ISBN/图书编号/出版社"); 
	    searchField.setForeground(Color.GRAY);
	    
	    //添加监听，显示提示文字
	    searchField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				//获取焦点时，清空提示内容
				String temp = searchField.getText();
				if(temp.equals("书名/作者/ISBN/图书编号/出版社")) {
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
					searchField.setText("书名/作者/ISBN/图书编号/出版社");
				}
			}
	    	
	    });
	    
	    add(searchField);
	    
	    //添加查询按钮
	    searchbtn.setBounds(648, 450, 110, 29);
	    searchbtn.setFont(font); 
	    searchbtn.setBackground(new Color(255,255,210));
		searchbtn.addActionListener(this);
		searchbtn.setFocusPainted(false);
		
		nextbtn.setBounds(140, 425, 80, 30);
		nextbtn.setFont(font); 
		nextbtn.setContentAreaFilled(false);
		nextbtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件
		nextbtn.addActionListener(this);
		nextbtn.setFocusPainted(false);
		add(nextbtn);
		
		prebtn.setBounds(50, 425, 80, 30);
		prebtn.setFont(font); 
		prebtn.setContentAreaFilled(false);
		prebtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件
		prebtn.addActionListener(this);
		prebtn.setFocusPainted(false);
		add(prebtn);
		
		typeLabel = new JLabel("图书类别：");
		typeLabel.setBounds(250,500, 110, 25);
		typeLabel.setFont(font);
		add(typeLabel);
		
		//类别下拉框
		condition.setBounds(350, 500, 400, 25);
		Font conditionFont=new Font("仿宋",Font.BOLD,15);
		condition.setFont(conditionFont);
		//在下拉框添加筛选条件
		condition.addItem("全部");
		//获取所有的图书类别
		try {
			bookTypeNames = bookService.getBookTypeNames();
			for (String string : bookTypeNames) {
				condition.addItem(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		add(condition);
		
		tiplabel.setFont(font);
		tiplabel.setOpaque(false);
		tiplabel.setBounds(800, 420, 180, 30);
		tiplabel.setVisible(false);
		add(tiplabel);
		
		TableModelProxy tableModel = new TableModelProxy(columnNames,datas);
		this.table.setRowHeight(50);
		 
	    //应用表格模型
	    this.table.setModel(tableModel);  
	    this.table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
  
	    //在6列添加按钮
	    this.table.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor(this.table,"详细信息"));    	  
	    this.table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender("详细信息")); 
	    
	    
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
	    
//	    //设置logo
//        Image logo;
//		try {
//			logo = (Image) new ImageIcon(this.getClass().getResource("/Images/logo.png")).getImage(); 
//			setIconImage(logo);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		} 
//		
	    setVisible(true);
	}

	
	private void getData(int page) {
		
		
		ArrayList<Book> bookDetails = new ArrayList<>();
	    ArrayList<UniqueBook> singleBooks = new ArrayList<>();
		try {
			bookDetails = bookService.getAllBooks();
			singleBooks = bookService.getSomeSingleBooks(page);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		
		if(singleBooks.size() == 0) {
			JOptionPane.showMessageDialog(null, "已经是最后一页！");
			return;
		}
		
		curentPageIndex = page;
		this.datas.clear();
		count = 0;
		
		//获取图书类别
		char type = ' ';
		int typeIndex = condition.getSelectedIndex();
        if(typeIndex!=0) {
        	type = this.bookTypeNames.get(typeIndex-1).split(":")[0].charAt(0);
        }
		
	    
	    
	    for (UniqueBook uniqueBook : singleBooks) {
			for (Book bookDetail : bookDetails) {
				
				if(type != ' ' && type != bookDetail.getBtype()) {
					continue;
				}
				
				if(uniqueBook.getIsbn().equals(bookDetail.getIsbn())) {
					count++;
					char status = uniqueBook.getStatus();
					String str = "";
					if(status == 'A') {
						str = "在库";
					}else if(status == 'B') {
						str = "借出";
					}else if(status == 'C') {
						str = "损毁";
					}else if(status == 'D') {
						str = "丢失";
					}
					
					Object[] data =  {uniqueBook.getBid(),bookDetail.getBname(),str,bookDetail.getBauthor(),bookDetail.getBproduction(),bookDetail.getIsbn(),""};    //增加列
					this.datas.add(data);
					
				}
				
			}
		}
	   
	   
		this.table.updateUI();
		tiplabel.setText("总共搜索到" + count + "条记录");
		tiplabel.setVisible(true);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == searchbtn) {
			this.datas.clear();
			String text = searchField.getText();
			//变量初始化
			count  = 0;
			curentPageIndex = 1;
			
			if(!text.equals("书名/作者/ISBN/图书编号/出版社")){
				getDataByText(text, 1);
			}else {
				getData(1);
			}
			
			this.table.updateUI();
			
		
		}else if(e.getSource() == nextbtn) {
			
			String text = searchField.getText();
			
			if(!text.equals("书名/作者/ISBN/图书编号/出版社")){
				getDataByText(text,curentPageIndex + 1);
			}else {
				getData(curentPageIndex + 1);
			}
			
            System.out.println("当前页:"+curentPageIndex);
            this.table.updateUI();

 			
		}else if(e.getSource() == prebtn) {
			
			
			if (curentPageIndex > 1) {
				
				String text = searchField.getText();
				if(!text.equals("书名/作者/ISBN/图书编号/出版社")){
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void getDataByText(String text, int page) {

		ArrayList<ArrayList> booksByCondition = new ArrayList<>();
		try {
			booksByCondition = bookService.getBooksByCondition(text,page);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		ArrayList<Book> books = booksByCondition.get(1);
		ArrayList<UniqueBook> uniqueBooks = booksByCondition.get(0);
		
		if(uniqueBooks.size() == 0) {
			JOptionPane.showMessageDialog(null,"已经是最后一页！");
			return;
		}
		
		curentPageIndex = page;
		this.datas.clear();
		count = 0;
		
		//获取图书类别
		char type = ' ';
		int typeIndex = condition.getSelectedIndex();
        if(typeIndex!=0) {
        	type = this.bookTypeNames.get(typeIndex-1).split(":")[0].charAt(0);
        }
        
       
        
		for (UniqueBook uniqueBook : uniqueBooks) {
			for (Book bookDetail : books) {
				
				if(type != ' ' && type != bookDetail.getBtype()) {
					continue;
				}
				
				if(uniqueBook.getIsbn().equals(bookDetail.getIsbn())) {
					count++;
					char status = uniqueBook.getStatus();
					String str = "";
					if(status == 'A') {
						str = "在库";
					}else if(status == 'B') {
						str = "借出";
					}else if(status == 'C') {
						str = "损毁";
					}else if(status == 'D') {
						str = "丢失";
					}
					
					
					Object[] data =  {uniqueBook.getBid(),bookDetail.getBname(),str,bookDetail.getBauthor(),bookDetail.getBproduction(),bookDetail.getIsbn(),""};    //增加列
					this.datas.add(data);
					break;
				}
				
			}
		}
		
	   
		this.table.updateUI();
		tiplabel.setText("总共搜索到" + count + "条记录");
		tiplabel.setVisible(true);
		
	}

}
