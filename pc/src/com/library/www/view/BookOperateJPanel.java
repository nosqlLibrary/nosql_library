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

import com.library.www.po.Admin;
import com.library.www.po.Book;
import com.library.www.po.UniqueBook;
import com.library.www.service.BookService;
import com.library.www.util.CheckHeaderCellRenderer;
import com.library.www.util.MyButtonEditor;
import com.library.www.util.MyButtonRender;
import com.library.www.util.MyJCheckBoxEditor;
import com.library.www.util.MyJCheckBoxRenderer;
import com.library.www.util.TableModelProxy;

public class BookOperateJPanel extends JPanel implements ActionListener {

	/**
	 * 查询图书界面：
	 * 1.查询条件可以进行筛选
	 * 2.管理员实现批量或单本删除图书，以及修改单本图书信息。查看统计信息
	 */
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	private Vector<Object> datas = new Vector<>();
	private static int curentPageIndex;        //当前页码                  
    private int count;//记录一共从数据库查询到多少条数据
    
	@SuppressWarnings({ "rawtypes", "unused" })
	private JComboBox selectType = new JComboBox();//筛选图书类型
	
	
	private JTable table;
	@SuppressWarnings("rawtypes")
	private JComboBox condition = new JComboBox();
	private JTextField searchField = new JTextField();
	private JButton nextbtn = new JButton("下一页");
	private JButton prebtn = new JButton("上一页");
	
	private JButton searchbtn = new JButton("查询",new ImageIcon(this.getClass().getResource("/Images/search.png")) );
	private Font font=new Font("仿宋",Font.BOLD,18); 
	
	private JLabel typeLabel = new JLabel("筛选图书类别：");
	private JLabel tiplabel = new JLabel();
	private ArrayList<String> bookTypeNames;

	
	@SuppressWarnings("unchecked")
	public BookOperateJPanel() {
		
        setSize(1015,600);
        setLayout(null);
		//初始化表格及表头
        table=new JTable();
	    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    String[] columnNames = new String[]{"全选","编号","书名","图书状态","作者","出版社","详细信息","删除","修改"};
	    
	  
	    
	    //设置查询条件框
	    searchField.setBounds(250, 430 , 390, 30); 
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
	    searchbtn.setBounds(639, 430, 110, 29);
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
		
		typeLabel = new JLabel("图书类别：");
		typeLabel.setBounds(250,480, 110, 25);
		typeLabel.setFont(font);
		add(typeLabel);
		
		//类别下拉框
		condition.setBounds(350, 480, 400, 25);
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
		tiplabel.setBounds(800, 400, 180, 30);
		tiplabel.setVisible(false);
		add(tiplabel);
		
		TableModelProxy tableModel = new TableModelProxy(columnNames,datas);
		
		 
	    //应用表格模型
	    this.table.setModel(tableModel);  
	    this.table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
  
	    //在6，7，8列添加按钮
	    this.table.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor(this.table,"详细信息"));    	  
	    this.table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender("详细信息")); 
	    
	    this.table.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor(this.table,"删除"));  
	    this.table.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender("删除"));
	    
	    this.table.getColumnModel().getColumn(8).setCellEditor(new MyButtonEditor(this.table,"修改"));  
	    this.table.getColumnModel().getColumn(8).setCellRenderer(new MyButtonRender("修改"));
	    //在第0列添加对应的处理器
	    this.table.getColumnModel().getColumn(0).setCellEditor(new MyJCheckBoxEditor());  
	    this.table.getColumnModel().getColumn(0).setCellRenderer(new MyJCheckBoxRenderer());
	    
//	    initdata();
	    this.table.getModel().addTableModelListener(this.table);
        this.table.setRowSelectionAllowed(false);// 禁止表格的选择功能。不然在点击按钮时表格的整行都会被选中。也可以通过其它方式来实现。   
	    table.setRowHeight(30);
	    //table.setFont(font);
	    
	    
	    //设置面板大小并把面板添加到大面板中
	    JScrollPane jsp = new JScrollPane(table);
	    //jsp.setPreferredSize(new Dimension(1000, 400));
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == searchbtn) {
			String text = searchField.getText();
			this.datas.clear();
			try {
				if(!text.equals("书名/作者/ISBN/图书编号/出版社")){
					getDataByText(1,text);
				}else {
					getData(1);
				}
			}catch (Exception e3) {
				e3.printStackTrace();
			}
		}else if(e.getSource() == nextbtn) {
			String text = searchField.getText();
			if(!text.equals("书名/作者/ISBN/图书编号/出版社")){
				getDataByText(curentPageIndex + 1 ,text);
			}else {
				getData(curentPageIndex + 1);
			}
			
           	System.out.println("当前页:"+curentPageIndex);
           	this.table.updateUI();
			
 			
		}else if(e.getSource() == prebtn) {
			if (curentPageIndex > 1) {
				String text = searchField.getText();
				if(!text.equals("书名/作者/ISBN/图书编号/出版社")){
					getDataByText(curentPageIndex -1 ,text);
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




	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void getDataByText(int page,String text){
		ArrayList<ArrayList> booksByCondition = new ArrayList<>();
		try {
			booksByCondition = bookService.getBooksByCondition(text,page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<Book> books = booksByCondition.get(1);
		ArrayList<UniqueBook> uniqueBooks = booksByCondition.get(0);
		
		if(uniqueBooks.size() == 0) {
			JOptionPane.showMessageDialog(null, "已经是最后一页！");
			return;
		}
		//变量初始化
		count  = 0;
		curentPageIndex = page;
		this.datas.clear();
		
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
					
					Object[] data =  {new Boolean(false),uniqueBook.getBid(),bookDetail.getBname(),str,bookDetail.getBauthor(),bookDetail.getBproduction(),"","",""};    //增加列
					this.datas.add(data);
					break;
				}
				
			}
		}

   
		this.table.updateUI();
		tiplabel.setText("总共搜索到" + count + "条记录");
		tiplabel.setVisible(true);
	}
	
	//初始化表格中的数据
	@SuppressWarnings("deprecation")
	public void getData(int page) {
		
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
	    
	    //变量初始化
	    count  = 0;
	    curentPageIndex = page;
	    this.datas.clear();
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
				//System.out.println("heihei");
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
					
					Object[] data =  {new Boolean(false),uniqueBook.getBid(),bookDetail.getBname(),str,bookDetail.getBauthor(),bookDetail.getBproduction(),"","",""};    //增加列
					
					this.datas.add(data);
					
					
				}
				
			}
		}
	    
	    
	    tiplabel.setText("总共搜索到" + count + "条记录");
		tiplabel.setVisible(true);

	    this.table.updateUI();
	}

}
