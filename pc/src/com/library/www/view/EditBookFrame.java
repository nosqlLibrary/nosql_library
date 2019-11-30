package com.library.www.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.library.www.po.Book;
import com.library.www.service.BookService;


public class EditBookFrame extends JFrame implements ActionListener {

	/**
	 * 修改某本图书具体信息的窗口
	 */
	private static final long serialVersionUID = 1L;
	private JButton closebtn = new JButton("关闭");
	private JButton submitbtn = new JButton("提交");
	private Font font=new Font("仿宋",Font.BOLD,18);
	private JLabel isbnLabel,bookNameLabel,typeLabel,authorLabel,pressLabel,publishDateLabel,registerDateLabel,
	   priceLabel,pageNumLabel,bookNumLabel;
	private JTextField isbnText = new JTextField();
	private JTextField bookNameText = new JTextField();
	private JTextField authorText = new JTextField();
	private JTextField presslText = new JTextField();
	private JTextField publishDateText = new JTextField();
	private JTextField pageNumText = new JTextField();
	private JTextField bookNumText = new JTextField();
	private JTextField priceText = new JTextField();
	private JTextField registerText = new JTextField();
	
	@SuppressWarnings("rawtypes")
	private JComboBox condition = new JComboBox();
	private ArrayList<String> bookTypeNames = new ArrayList<>();
	private BookService bookService = new BookService();
	private Book book;
	
	private JPanel panel = new JPanel() {
		
			private static final long serialVersionUID = 1L;
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
		
	};
	
	@SuppressWarnings("unchecked")
	public EditBookFrame(Book book) {
		super("编辑图书《"+book.getBname()+"》的信息界面");
		setSize(700,600);
		setLocationRelativeTo(null);    
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		this.book = book;
		add(panel);
		panel.setLayout(null);
		
		registerDateLabel = new JLabel("登记日期：");
		registerDateLabel.setBounds(100, 60, 110, 25);
		registerDateLabel.setFont(font);
		panel.add(registerDateLabel);
		
		isbnLabel = new JLabel("ISBN:");
		isbnLabel.setBounds(100, 100, 110, 25);
		isbnLabel.setFont(font);
		panel.add(isbnLabel);
		
		
		bookNameLabel = new JLabel("图书名称：");
		bookNameLabel.setBounds(100, 140, 110, 25);
		bookNameLabel.setFont(font);
		panel.add(bookNameLabel);
		
		
		typeLabel = new JLabel("图书类别：");
		typeLabel.setBounds(100,180, 110, 25);
		typeLabel.setFont(font);
		panel.add(typeLabel);
		
		
		authorLabel = new JLabel("作者：");
		authorLabel.setBounds(100, 220, 110, 25);
		authorLabel.setFont(font);
		panel.add(authorLabel);
		
		
		pressLabel = new JLabel("出版社：");
		pressLabel.setBounds(100, 260, 110, 25);
		pressLabel.setFont(font);
		panel.add(pressLabel);
		
		
		publishDateLabel = new JLabel("出版年：");
		publishDateLabel.setBounds(100,300, 110, 25);
		publishDateLabel.setFont(font);
		panel.add(publishDateLabel);
		
		
		priceLabel = new JLabel("定价：");
		priceLabel.setBounds(100, 340, 110, 25);
		priceLabel.setFont(font);
		panel.add(priceLabel);
		
		
		pageNumLabel = new JLabel("页码：");
		pageNumLabel.setBounds(100, 380, 110, 25);
		pageNumLabel.setFont(font);
		panel.add(pageNumLabel);
		
		
		bookNumLabel = new JLabel("书籍数量：");
		bookNumLabel.setBounds(100, 420, 110, 25);
		bookNumLabel.setFont(font);
		panel.add(bookNumLabel);
		
		registerText.setBounds(210, 60, 400, 25);
		registerText.setOpaque(false);
		registerText.setText(book.getRegisterDate().toString());
		
		isbnText.setBounds(210, 100, 400, 25);
		isbnText.setOpaque(false);
		isbnText.setText(book.getIsbn());
		isbnText.setEditable(false);
		
		
		bookNameText.setBounds(210, 140, 400, 25);
		bookNameText.setOpaque(false);
		bookNameText.setText(book.getBname());
		
		
		//类别下拉框
		condition.setBounds(210, 180, 400, 25);
		Font conditionFont=new Font("仿宋",Font.BOLD,15);
		condition.setFont(conditionFont);
		//在下拉框添加筛选条件
		condition.addItem("当前:"+book.getBtype());
		//获取所有的图书类别
		try {
			bookTypeNames = bookService.getBookTypeNames();
			for (String string : bookTypeNames) {
				condition.addItem(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		authorText.setBounds(210, 220, 400, 25);
		authorText.setOpaque(false);
		authorText.setText(book.getBauthor());
		
		
		presslText.setBounds(210, 260, 400, 25);
		presslText.setOpaque(false);
		presslText.setText(book.getBproduction());
		
		publishDateText.setBounds(210, 300, 400, 25);
		publishDateText.setOpaque(false);
		
		publishDateText.setText(book.getPublishDate());
		
		priceText.setBounds(210, 340, 400, 25);
		priceText.setOpaque(false);
		priceText.setText(book.getPrice());

		pageNumText.setBounds(210, 380, 400, 25);
		pageNumText.setOpaque(false);
		pageNumText.setText(""+book.getPageNum());
		
		bookNumText.setBounds(210, 420, 400, 25);
		bookNumText.setOpaque(false);
		bookNumText.setText(book.getNum()+"");
		//库存数量不可修改
		bookNumText.setEditable(false);
		
		submitbtn.setFont(font); 
		submitbtn.setBounds(130, 470, 100, 30);
		submitbtn.setContentAreaFilled(false);
		submitbtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
		submitbtn.addActionListener(this);
		submitbtn.setFocusPainted(false);
		
		closebtn.setFont(font); 
		closebtn.setBounds(320, 470, 120, 30);
		closebtn.setContentAreaFilled(false);
		closebtn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
		closebtn.addActionListener(this);
		closebtn.setFocusPainted(false);
		
		panel.add(condition);
		panel.add(priceText);
		panel.add(authorText);
		panel.add(bookNameText);
		panel.add(bookNumText);
		panel.add(isbnText);
		panel.add(pageNumText);
		panel.add(presslText);
		panel.add(publishDateText);
		panel.add(registerText);
		panel.add(submitbtn);
		panel.add(closebtn);
		
		
		//设置logo
        Image logo;
		try {
			logo = (Image) new ImageIcon(this.getClass().getResource("/Images/logo.png")).getImage(); 
			setIconImage(logo);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		setVisible(true);
		
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

//	 public static void main(String[] args) throws Exception {
//		BookService bs = new BookService();
//		Book book = (Book) bs.getBookDetails("136751").get(1);
//		System.out.println(book);
//		new EditBookFrame(book);
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitbtn) {
			//判断输入的信息是否有误
			//获取图书名
			String bName = bookNameText.getText();
			if(bName.equals("")) {
				JOptionPane.showMessageDialog(null,"请输入的图书名称再提交","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//获取图书类别
			int typeIndex = condition.getSelectedIndex();
			char type ;
            if(typeIndex==0) {
            	type = ((String)condition.getSelectedItem()).split(":")[1].charAt(0);
//            	System.out.println(((String)condition.getSelectedItem()).split(":")[1]);
            }else {
            	
            	type = this.bookTypeNames.get(typeIndex-1).split(":")[0].charAt(0);
            	
            }
            
            
			//获取作者
			String author = authorText.getText();
	
			//获取出版社
			String press = presslText.getText();

			
			//获取出版年
			String publishDate = publishDateText.getText();

			
			//获取定价
			String price  = priceText.getText();

			//获取页码
			String pageNum = pageNumText.getText();
			if(!isInteger(pageNum)) {
				JOptionPane.showMessageDialog(null,"请输入整数！","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			book.setBauthor(author);
			book.setBname(bName);
			book.setBproduction(press);
			book.setBtype(type);
			book.setPageNum(Integer.parseInt(pageNum));
			book.setPrice(price);
			book.setPublishDate(publishDate);
			
			
			boolean result = false;
			try {
				 result = bookService.editBook(book);
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			if(result) {
				JOptionPane.showMessageDialog(null,"修改《"+ book.getBname() +"》信息成功 ","提示",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"修改《"+ book.getBname() +"》信息失败" ,"提示",JOptionPane.ERROR_MESSAGE);
			}
		
		}else if(e.getSource() == closebtn){
			dispose();
		}
		
	}
	
}
