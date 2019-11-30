package com.library.www.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.library.www.po.Book;
import com.library.www.po.BookType;
import com.library.www.po.UniqueBook;
import com.library.www.service.BookService;

public class BookDetailFrame extends JFrame {


	/**
	 * 显示某本图书的具体信息的窗口
	 */
	private static final long serialVersionUID = 1L;
	
	private Font font=new Font("仿宋",Font.BOLD,18); 
	
	private JTextArea textArea = new JTextArea(5,20);
	
	private final int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;		//设置垂直滚动条
	private final int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;	//设置水平滚动条
	private JScrollPane jsp=new JScrollPane(textArea,v,h);
	private BookService bs = new BookService();
	
	@SuppressWarnings("serial")
	private JPanel panel = new JPanel() {
		
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
	
	@SuppressWarnings("rawtypes")
	public BookDetailFrame(ArrayList result) {
		super("图书详细信息界面");
		Book book = (Book) result.get(1);
		UniqueBook ubook = (UniqueBook) result.get(0);
		setSize(600,500);
		setLocationRelativeTo(null);    
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		add(panel);
		panel.setLayout(null);
		
		jsp.setBounds(40, 40,500,380 );
		
		String content = "图书编号："+ubook.getBid() + "\r\n";
		content += "ISBN:" + book.getIsbn() + "\r\n";
		content += "图书名称："+book.getBname()+ "\r\n";
		BookType type  = new BookType();
		try {
			type = bs.getBookTypeNameById(book.getBtype());
		} catch (Exception e2) {
			
			e2.printStackTrace();
		}
		
		content += "图书类别：["+book.getBtype()+ "]"+ type.getTypename() +"\r\n";
		content += "作者："+book.getBauthor()+ "\r\n";
		content +="出版社："+book.getBproduction()+ "\r\n";
		content += "出版年："+book.getPublishDate() + "\r\n";
		content += "定价："+book.getPrice() + "\r\n";
		content += "页码："+book.getPageNum() + "\r\n";
		content += "书籍数量："+book.getNum() + "\r\n";
		
		
		char status = ubook.getStatus();
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
		content += "图书状态：["+ubook.getStatus() +"]"+str+ "\r\n";
		String introduction = "";
		//如果简介不存在就直接显示无
		introduction = book.getIntroduction() ==null? "无":book.getIntroduction() ;
		content += "内容简介：" + introduction;
		textArea.setText(content);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFont(font);
		textArea.setCaretPosition(0);
		
		//jsp.setOpaque(false);
		panel.add(jsp);
		
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

}
