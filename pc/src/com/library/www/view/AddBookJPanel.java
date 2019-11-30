package com.library.www.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.bson.Document;

import com.library.mongodbutil.MongoUtil;
import com.library.www.po.Book;
import com.library.www.po.UniqueBook;
import com.library.www.service.BookService;
import com.library.www.util.GetNewBook;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoCollection;

public class AddBookJPanel extends JPanel implements ActionListener {

	/**
	 * 管理员增加新图书的界面：
	 * 1.根据扫码枪来获得图书的isbn，在mongodb中找到爬取好的信息，管理员确认无误之后，在数据库插入基本信息
	 * 	-如果是新书上架，mongodb中还没有信息，可以通过url访问得到信息
	 * 2.管理员手动输入信息
	 * 3.添加mysql数据库已有图书，只是增加库存，以及产生新的uniquebook信息
	 */
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	private JButton submitbtn,mongobtn,addNumsubmitbtn,morebtn;
	private Font font=new Font("仿宋",Font.BOLD,18); 
	private JLabel isbnLabel,bookNameLabel,typeLabel,authorLabel,pressLabel,publishDateLabel,
				   priceLabel,pageNumLabel,bookNumLabel,addNumLabel,selectisbnLabel,introLabel;
	private JTextField isbnText = new JTextField();
	private JTextField bookNameText = new JTextField();
	private JTextField authorText = new JTextField();
	private JTextField presslText = new JTextField();
	private JTextField publishDateText = new JTextField();
	private JTextField pageNumText = new JTextField();
	private JTextField bookNumText = new JTextField();
	private JTextField priceText = new JTextField();
	private JTextField addNumText = new JTextField();
	private JTextArea introarea = new JTextArea(5,20);
	private final int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;		//设置垂直滚动条
	private final int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;	//设置水平滚动条
	private JScrollPane jsp=new JScrollPane(introarea,v,h);
	
	private Book book = new Book();
	private ArrayList<UniqueBook> ubooks;
	private ArrayList<String> bookTypeNames;
	private ArrayList<Book> books;
	@SuppressWarnings("rawtypes")
	private JComboBox condition = new JComboBox();
	@SuppressWarnings("rawtypes")
	private JComboBox selectIsbn = new JComboBox();
	private JTabbedPane tabbedPane=new JTabbedPane();
    
	@SuppressWarnings("serial")
	private JPanel leftpanel = new JPanel() {
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
	
	@SuppressWarnings("serial")
	private JPanel rightpanel = new JPanel() {
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
	

	
	public AddBookJPanel() {
		
		
		setSize(1015,600);
		setLayout(null);
		
        leftpanel.setLayout(null);
        rightpanel.setLayout(null);
        tabbedPane.addTab("手动输入所有信息",null,leftpanel,"Manually enter");
        tabbedPane.addTab("添加数据库已有图书",null,rightpanel,"Add stock");
        tabbedPane.setMnemonicAt(0,KeyEvent.VK_1);
        tabbedPane.setBounds(0,0,1015, 600);
        
        add(tabbedPane);
        initleft();
        initright();
		
		
		setVisible(true);
		
	}



	public void initbutton(JButton btn) {
		btn.setFont(font);
		btn.setContentAreaFilled(false);
		btn.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
		btn.addActionListener(this);
		btn.setFocusPainted(false);
	}


	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initright() {
		
		
		addNumsubmitbtn = new JButton("提交");
		morebtn = new JButton("图书详细信息");
		
		
		addNumsubmitbtn.setBounds(350, 200, 100, 30);
		initbutton(addNumsubmitbtn);
		
		morebtn.setIcon(new ImageIcon("src/Images/moredetail.png"));
		morebtn.setBounds(710, 100,160, 25);
		morebtn.addActionListener(this);
		initbutton(morebtn);
		
		
		selectisbnLabel = new JLabel("ISBN：");
		selectisbnLabel.setBounds(150, 100, 100, 25);
		selectisbnLabel.setFont(font);
		
		
		//数据库中所有图书的下拉框
		selectIsbn.setBounds(240, 100,450, 25);
		selectIsbn.setFont(font);
		//在下拉框添加筛选条件
		selectIsbn.addItem("---请选择---");
		//获取所有的具体图书信息
		try {
			ArrayList<ArrayList> allbooks = bookService.getAllDetailBooks();
			books = allbooks.get(1);
			ubooks = allbooks.get(0);
			for (Book book : books) {
				selectIsbn.addItem(book.getIsbn());
			}
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		
		addNumLabel = new JLabel("增加图书数量：");
		addNumLabel.setBounds(90, 140, 150, 25);
		addNumLabel.setFont(font);
		
		
		addNumText.setBounds(240, 140, 450, 25);
		addNumText.setOpaque(false);
		
		rightpanel.add(addNumLabel);
		rightpanel.add(addNumText);
		rightpanel.add(addNumsubmitbtn);
		rightpanel.add(selectIsbn);
		rightpanel.add(selectisbnLabel);
		rightpanel.add(morebtn);
		
	}



	@SuppressWarnings("unchecked")
	private void initleft() {
		submitbtn = new JButton("提交");
		
		mongobtn = new JButton("连接Mongodb输入");
		
		submitbtn.setBounds(230, 430, 100, 30);
		initbutton(submitbtn);
		mongobtn.setBounds(400, 430, 170, 30);
		initbutton(mongobtn);
		
		isbnLabel = new JLabel("ISBN:");
		isbnLabel.setBounds(100, 30, 110, 25);
		isbnLabel.setFont(font);
		leftpanel.add(isbnLabel);
		
		bookNameLabel = new JLabel("图书名称：");
		bookNameLabel.setBounds(100, 70, 110, 25);
		bookNameLabel.setFont(font);
		leftpanel.add(bookNameLabel);
		
		typeLabel = new JLabel("图书类别：");
		typeLabel.setBounds(100,110, 110, 25);
		typeLabel.setFont(font);
		leftpanel.add(typeLabel);
		
		authorLabel = new JLabel("作者：");
		authorLabel.setBounds(100, 150, 110, 25);
		authorLabel.setFont(font);
		leftpanel.add(authorLabel);
		
		pressLabel = new JLabel("出版社：");
		pressLabel.setBounds(100, 190, 110, 25);
		pressLabel.setFont(font);
		leftpanel.add(pressLabel);
		
		publishDateLabel = new JLabel("出版年：");
		publishDateLabel.setBounds(100,230, 110, 25);
		publishDateLabel.setFont(font);
		leftpanel.add(publishDateLabel);
		
		priceLabel = new JLabel("定价：");
		priceLabel.setBounds(100, 270, 110, 25);
		priceLabel.setFont(font);
		leftpanel.add(priceLabel);
		
		pageNumLabel = new JLabel("页码：");
		pageNumLabel.setBounds(100,310, 110, 25);
		pageNumLabel.setFont(font);
		leftpanel.add(pageNumLabel);
		
		bookNumLabel = new JLabel("书籍数量：");
		bookNumLabel.setBounds(100, 350, 110, 25);
		bookNumLabel.setFont(font);
		leftpanel.add(bookNumLabel);
		
		introLabel = new JLabel("内容简介：");
		introLabel.setBounds(650, 30, 110, 25);
		introLabel.setFont(font);
		leftpanel.add(introLabel);
		
		isbnText.setBounds(210, 30, 400, 25);
		isbnText.setOpaque(false);
		isbnText.setRequestFocusEnabled(true);
		
		
		bookNameText.setBounds(210, 70, 400, 25);
		bookNameText.setOpaque(false);
		
		//类别下拉框
		condition.setBounds(210, 110, 400, 25);
		Font conditionFont=new Font("仿宋",Font.BOLD,15);
		condition.setFont(conditionFont);
		//在下拉框添加筛选条件
		condition.addItem("---请选择---");
		//获取所有的图书类别
		
		try {
			bookTypeNames = bookService.getBookTypeNames();
			for (String string : bookTypeNames) {
				condition.addItem(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		leftpanel.add(condition);
		
		authorText.setBounds(210, 150, 400, 25);
		authorText.setOpaque(false);
		
		presslText.setBounds(210, 190, 400, 25);
		presslText.setOpaque(false);
		
		publishDateText.setBounds(210, 230, 400, 25);
		publishDateText.setOpaque(false);
		
		priceText.setBounds(210, 270, 400, 25);
		priceText.setOpaque(false);

		pageNumText.setBounds(210, 310, 400, 25);
		pageNumText.setOpaque(false);
		
		bookNumText.setBounds(210, 350, 400, 25);
		bookNumText.setOpaque(false);
		
		introarea.setLineWrap(true);
		jsp.setBounds(650, 70, 300, 300);
		//jsp.setOpaque(false);
		
		leftpanel.add(priceText);
		leftpanel.add(authorText);
		leftpanel.add(bookNameText);
		leftpanel.add(bookNumText);
		leftpanel.add(isbnText);
		leftpanel.add(pageNumText);
		leftpanel.add(presslText);
		leftpanel.add(publishDateText);
		leftpanel.add(jsp);
		leftpanel.add(submitbtn);
		leftpanel.add(mongobtn);
		
		
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitbtn){
			
			
			//判断输入的信息是否有误
			//判断ISBN码格式
			String isbn = isbnText.getText();
			if(isbn.equals("")) {
				JOptionPane.showMessageDialog(null,"请输入的ISBN码再提交","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(isbn.length()!=13 || !isInteger(isbn)) {
				JOptionPane.showMessageDialog(null,"您输入的ISBN码格式错误,ISBN码应由13位数字组成","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//获取图书名
			String bName = bookNameText.getText();
			if(bName.equals("")) {
				JOptionPane.showMessageDialog(null,"请输入的图书名称再提交","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//获取图书类别
			int typeIndex = condition.getSelectedIndex();
            if(typeIndex==0) {
            	JOptionPane.showMessageDialog(null, "请选择图书类别","提示",JOptionPane.ERROR_MESSAGE);
    			return;
            }
            
         
            char type = this.bookTypeNames.get(typeIndex-1).split(":")[0].charAt(0);
            
			//获取作者
			String author = authorText.getText();
			if(author.equals("")) {
				author = null;
			}
	
			//获取出版社
			String press = presslText.getText();
			if(press.equals("")) {
				press = null;
			}
			
			//获取出版年
			String publishDate = publishDateText.getText();
			if(publishDate.equals("")) {
				publishDate = null;
			}
			
			//获取定价
			String price  = priceText.getText();
			if(price.equals("")) {
				price = null;
			}

			//获取页码
			String pageNum = pageNumText.getText();
			if(!isInteger(pageNum)) {
				JOptionPane.showMessageDialog(null,"请输入整数！","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(pageNum.equals("")) {
				pageNum = null;
			}
			
			//将当前时间设为 登记日期
			Date registerDate  = new Date();
			
			//获取本次录入的书籍数目
			if(bookNumText.getText().equals("")) {
				JOptionPane.showMessageDialog(null,"请输入本次要录入的书籍数目！","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!isInteger(bookNumText.getText())) {
				JOptionPane.showMessageDialog(null,"请输入整数！","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int bookNum = Integer.parseInt(bookNumText.getText());
			
			String text = introarea.getText();
			if(text.equals("")) {
				text = null;
			}
			
			book.setIsbn(isbn);
			book.setBauthor(author);
			book.setBname(bName);
			book.setBproduction(press);
			book.setBtype(type);
			book.setNum(bookNum);
			if(pageNum!=null)
				book.setPageNum(Integer.parseInt(pageNum));
			book.setPrice(price);
			book.setPublishDate(publishDate);
			book.setRegisterDate(registerDate);
			book.setIntroduction(text);
			
			boolean result1 = false;
			boolean result2 = false;
			
			try {
				result1 = bookService.addNewBook(book);
			} catch (Exception e2) {
				
				e2.printStackTrace();
			}
			
			String bids  = "";
			
			for(int i = 0;i < bookNum ;i++) {
				UniqueBook ubook = new UniqueBook();
				String bid = createData(6);
				//记录下每本书的Id
				if(i != bookNum-1)	
					bids += bid+",";
				else bids += bid;
				ubook.setBid(bid);
				ubook.setIsbn(isbn);
				ubook.setStatus('A');
				try {
					result2 = bookService.addUniqueBook(ubook);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			if(result1 && result2) {
				JOptionPane.showMessageDialog(null,"添加"+ bName +"成功，图书编号分别为:"+bids ,"提示",JOptionPane.PLAIN_MESSAGE);
				
				condition.setSelectedIndex(0);
				isbnText.setText("");
				bookNameText.setText("");
				authorText.setText("");
				presslText.setText("");
				publishDateText.setText("");
				pageNumText.setText("");
				bookNumText.setText("");
				priceText.setText("");
				introarea.setText("");
				
			}
			
			
		}else if(e.getSource() ==addNumsubmitbtn) {
			String text = addNumText.getText();
			if(text.equals("")) {
				JOptionPane.showMessageDialog(null,"请输入新增图书数目！","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}else if(!isInteger(text)) {
				JOptionPane.showMessageDialog(null,"请输入整数！","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			int addNum = Integer.parseInt(text);
			int isbnIndex = selectIsbn.getSelectedIndex();
			if(isbnIndex == 0) {
				JOptionPane.showMessageDialog(null,"请选择对应的isbn码！","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			Book book = books.get(isbnIndex-1);
			
			String bids = "";
			boolean result = false;
			boolean result2 = false;
			for(int i = 0;i < addNum;i++) {
				UniqueBook ubook = new UniqueBook();
				String bid = createData(6);
				//记录下每本书的Id
				if(i != addNum-1)	
					bids += bid+",";
				else bids += bid;
				ubook.setBid(bid);
				ubook.setIsbn(book.getIsbn());
				ubook.setStatus('A');
				try {
					result2 = bookService.addUniqueBook(ubook);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
			try {
				result = bookService.changeExistBookNum(book, addNum);
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
				
			if(result && result2) {
				JOptionPane.showMessageDialog(null,"添加"+ book.getBname() +"成功，图书编号分别为:"+bids ,"提示",JOptionPane.PLAIN_MESSAGE);
				selectIsbn.setSelectedIndex(0);
				addNumText.setText("");
			}
			
		}else if(e.getSource() == morebtn) {
			e.setSource(null);
			int isbnIndex = selectIsbn.getSelectedIndex();
			if(isbnIndex == 0) {
				JOptionPane.showMessageDialog(null,"请选择对应的isbn码！","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Book book = books.get(isbnIndex-1);
			UniqueBook ubook = ubooks.get(isbnIndex - 1); 
			ArrayList result = new ArrayList<>();
			
			result.add(ubook);
			result.add(book);
			
			
			new BookDetailFrame(result);
			
		}else if(e.getSource() == mongobtn) {
			//判断输入的信息是否有误
			//判断ISBN码格式
			String isbn = isbnText.getText();
			
			if(isbn.equals("")) {
				JOptionPane.showMessageDialog(null,"请输入的ISBN码再连接mongodb","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(isbn.length()!=13 || !isInteger(isbn)) {
				JOptionPane.showMessageDialog(null,"您输入的ISBN码格式错误,ISBN码应由13位数字组成","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			MongoUtil util = new MongoUtil();
			
			try {

				MongoCollection<Document> connect = util.getConnect();
				com.library.mongodbutil.Book findBookByIsbn = util.findBookByIsbn(connect, isbn);
			
				//System.out.println(findBookByIsbn.getIsbn()+" "+findBookByIsbn.getB_na());
				if(findBookByIsbn.getIsbn()!=null)
					System.out.println("从mongodb获取到了："+findBookByIsbn.getB_na());
				else {
					
					int getfromNet = JOptionPane.showConfirmDialog(null,"数据库暂时没有该图书的信息，是否现在从网络上获取？","提示",JOptionPane.YES_NO_OPTION);
					if(getfromNet==0) {
						//调用方法，从网上爬取信息
						Book bookFromNet = GetNewBook.getBookFromNet(isbn);
						//将获取到的信息添加到输入框中
						bookNameText.setText(bookFromNet.getBname());
						authorText.setText(bookFromNet.getBauthor());
						presslText.setText(bookFromNet.getBproduction());
						publishDateText.setText(bookFromNet.getPublishDate());
						pageNumText.setText(bookFromNet.getPageNum()+"");
						priceText.setText(bookFromNet.getPrice());
						introarea.setText(bookFromNet.getIntroduction());
						
						return;
					}else {
						isbnText.setText("");
						bookNameText.setText("");
						authorText.setText("");
						presslText.setText("");
						publishDateText.setText("");
						pageNumText.setText("");
						bookNumText.setText("");
						priceText.setText("");
						introarea.setText("");
						return;
					}
					
				}
					
				
				//将获取到的信息添加到输入框中
				bookNameText.setText(findBookByIsbn.getB_na());
				authorText.setText(findBookByIsbn.getB_aut());
				presslText.setText(findBookByIsbn.getPress());
				publishDateText.setText(findBookByIsbn.getPdate());
				pageNumText.setText(findBookByIsbn.getB_pnum());
				priceText.setText(findBookByIsbn.getPrice());
				introarea.setText(findBookByIsbn.getB_intro());
				
			}catch (MongoTimeoutException e1) {
				System.out.println("请检查网络！");
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
