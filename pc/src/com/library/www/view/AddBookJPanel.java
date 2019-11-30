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
	 * ����Ա������ͼ��Ľ��棺
	 * 1.����ɨ��ǹ�����ͼ���isbn����mongodb���ҵ���ȡ�õ���Ϣ������Աȷ������֮�������ݿ���������Ϣ
	 * 	-����������ϼܣ�mongodb�л�û����Ϣ������ͨ��url���ʵõ���Ϣ
	 * 2.����Ա�ֶ�������Ϣ
	 * 3.���mysql���ݿ�����ͼ�飬ֻ�����ӿ�棬�Լ������µ�uniquebook��Ϣ
	 */
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	private JButton submitbtn,mongobtn,addNumsubmitbtn,morebtn;
	private Font font=new Font("����",Font.BOLD,18); 
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
	private final int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;		//���ô�ֱ������
	private final int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;	//����ˮƽ������
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
		//�޸����ı���ͼƬ
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
		//�޸����ı���ͼƬ
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
        tabbedPane.addTab("�ֶ�����������Ϣ",null,leftpanel,"Manually enter");
        tabbedPane.addTab("������ݿ�����ͼ��",null,rightpanel,"Add stock");
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
		btn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
		btn.addActionListener(this);
		btn.setFocusPainted(false);
	}


	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initright() {
		
		
		addNumsubmitbtn = new JButton("�ύ");
		morebtn = new JButton("ͼ����ϸ��Ϣ");
		
		
		addNumsubmitbtn.setBounds(350, 200, 100, 30);
		initbutton(addNumsubmitbtn);
		
		morebtn.setIcon(new ImageIcon("src/Images/moredetail.png"));
		morebtn.setBounds(710, 100,160, 25);
		morebtn.addActionListener(this);
		initbutton(morebtn);
		
		
		selectisbnLabel = new JLabel("ISBN��");
		selectisbnLabel.setBounds(150, 100, 100, 25);
		selectisbnLabel.setFont(font);
		
		
		//���ݿ�������ͼ���������
		selectIsbn.setBounds(240, 100,450, 25);
		selectIsbn.setFont(font);
		//�����������ɸѡ����
		selectIsbn.addItem("---��ѡ��---");
		//��ȡ���еľ���ͼ����Ϣ
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
		
		
		addNumLabel = new JLabel("����ͼ��������");
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
		submitbtn = new JButton("�ύ");
		
		mongobtn = new JButton("����Mongodb����");
		
		submitbtn.setBounds(230, 430, 100, 30);
		initbutton(submitbtn);
		mongobtn.setBounds(400, 430, 170, 30);
		initbutton(mongobtn);
		
		isbnLabel = new JLabel("ISBN:");
		isbnLabel.setBounds(100, 30, 110, 25);
		isbnLabel.setFont(font);
		leftpanel.add(isbnLabel);
		
		bookNameLabel = new JLabel("ͼ�����ƣ�");
		bookNameLabel.setBounds(100, 70, 110, 25);
		bookNameLabel.setFont(font);
		leftpanel.add(bookNameLabel);
		
		typeLabel = new JLabel("ͼ�����");
		typeLabel.setBounds(100,110, 110, 25);
		typeLabel.setFont(font);
		leftpanel.add(typeLabel);
		
		authorLabel = new JLabel("���ߣ�");
		authorLabel.setBounds(100, 150, 110, 25);
		authorLabel.setFont(font);
		leftpanel.add(authorLabel);
		
		pressLabel = new JLabel("�����磺");
		pressLabel.setBounds(100, 190, 110, 25);
		pressLabel.setFont(font);
		leftpanel.add(pressLabel);
		
		publishDateLabel = new JLabel("�����꣺");
		publishDateLabel.setBounds(100,230, 110, 25);
		publishDateLabel.setFont(font);
		leftpanel.add(publishDateLabel);
		
		priceLabel = new JLabel("���ۣ�");
		priceLabel.setBounds(100, 270, 110, 25);
		priceLabel.setFont(font);
		leftpanel.add(priceLabel);
		
		pageNumLabel = new JLabel("ҳ�룺");
		pageNumLabel.setBounds(100,310, 110, 25);
		pageNumLabel.setFont(font);
		leftpanel.add(pageNumLabel);
		
		bookNumLabel = new JLabel("�鼮������");
		bookNumLabel.setBounds(100, 350, 110, 25);
		bookNumLabel.setFont(font);
		leftpanel.add(bookNumLabel);
		
		introLabel = new JLabel("���ݼ�飺");
		introLabel.setBounds(650, 30, 110, 25);
		introLabel.setFont(font);
		leftpanel.add(introLabel);
		
		isbnText.setBounds(210, 30, 400, 25);
		isbnText.setOpaque(false);
		isbnText.setRequestFocusEnabled(true);
		
		
		bookNameText.setBounds(210, 70, 400, 25);
		bookNameText.setOpaque(false);
		
		//���������
		condition.setBounds(210, 110, 400, 25);
		Font conditionFont=new Font("����",Font.BOLD,15);
		condition.setFont(conditionFont);
		//�����������ɸѡ����
		condition.addItem("---��ѡ��---");
		//��ȡ���е�ͼ�����
		
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
			
			
			//�ж��������Ϣ�Ƿ�����
			//�ж�ISBN���ʽ
			String isbn = isbnText.getText();
			if(isbn.equals("")) {
				JOptionPane.showMessageDialog(null,"�������ISBN�����ύ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(isbn.length()!=13 || !isInteger(isbn)) {
				JOptionPane.showMessageDialog(null,"�������ISBN���ʽ����,ISBN��Ӧ��13λ�������","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//��ȡͼ����
			String bName = bookNameText.getText();
			if(bName.equals("")) {
				JOptionPane.showMessageDialog(null,"�������ͼ���������ύ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//��ȡͼ�����
			int typeIndex = condition.getSelectedIndex();
            if(typeIndex==0) {
            	JOptionPane.showMessageDialog(null, "��ѡ��ͼ�����","��ʾ",JOptionPane.ERROR_MESSAGE);
    			return;
            }
            
         
            char type = this.bookTypeNames.get(typeIndex-1).split(":")[0].charAt(0);
            
			//��ȡ����
			String author = authorText.getText();
			if(author.equals("")) {
				author = null;
			}
	
			//��ȡ������
			String press = presslText.getText();
			if(press.equals("")) {
				press = null;
			}
			
			//��ȡ������
			String publishDate = publishDateText.getText();
			if(publishDate.equals("")) {
				publishDate = null;
			}
			
			//��ȡ����
			String price  = priceText.getText();
			if(price.equals("")) {
				price = null;
			}

			//��ȡҳ��
			String pageNum = pageNumText.getText();
			if(!isInteger(pageNum)) {
				JOptionPane.showMessageDialog(null,"������������","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(pageNum.equals("")) {
				pageNum = null;
			}
			
			//����ǰʱ����Ϊ �Ǽ�����
			Date registerDate  = new Date();
			
			//��ȡ����¼����鼮��Ŀ
			if(bookNumText.getText().equals("")) {
				JOptionPane.showMessageDialog(null,"�����뱾��Ҫ¼����鼮��Ŀ��","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!isInteger(bookNumText.getText())) {
				JOptionPane.showMessageDialog(null,"������������","��ʾ",JOptionPane.ERROR_MESSAGE);
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
				//��¼��ÿ�����Id
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
				JOptionPane.showMessageDialog(null,"���"+ bName +"�ɹ���ͼ���ŷֱ�Ϊ:"+bids ,"��ʾ",JOptionPane.PLAIN_MESSAGE);
				
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
				JOptionPane.showMessageDialog(null,"����������ͼ����Ŀ��","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}else if(!isInteger(text)) {
				JOptionPane.showMessageDialog(null,"������������","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			int addNum = Integer.parseInt(text);
			int isbnIndex = selectIsbn.getSelectedIndex();
			if(isbnIndex == 0) {
				JOptionPane.showMessageDialog(null,"��ѡ���Ӧ��isbn�룡","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			Book book = books.get(isbnIndex-1);
			
			String bids = "";
			boolean result = false;
			boolean result2 = false;
			for(int i = 0;i < addNum;i++) {
				UniqueBook ubook = new UniqueBook();
				String bid = createData(6);
				//��¼��ÿ�����Id
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
				JOptionPane.showMessageDialog(null,"���"+ book.getBname() +"�ɹ���ͼ���ŷֱ�Ϊ:"+bids ,"��ʾ",JOptionPane.PLAIN_MESSAGE);
				selectIsbn.setSelectedIndex(0);
				addNumText.setText("");
			}
			
		}else if(e.getSource() == morebtn) {
			e.setSource(null);
			int isbnIndex = selectIsbn.getSelectedIndex();
			if(isbnIndex == 0) {
				JOptionPane.showMessageDialog(null,"��ѡ���Ӧ��isbn�룡","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Book book = books.get(isbnIndex-1);
			UniqueBook ubook = ubooks.get(isbnIndex - 1); 
			ArrayList result = new ArrayList<>();
			
			result.add(ubook);
			result.add(book);
			
			
			new BookDetailFrame(result);
			
		}else if(e.getSource() == mongobtn) {
			//�ж��������Ϣ�Ƿ�����
			//�ж�ISBN���ʽ
			String isbn = isbnText.getText();
			
			if(isbn.equals("")) {
				JOptionPane.showMessageDialog(null,"�������ISBN��������mongodb","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(isbn.length()!=13 || !isInteger(isbn)) {
				JOptionPane.showMessageDialog(null,"�������ISBN���ʽ����,ISBN��Ӧ��13λ�������","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			MongoUtil util = new MongoUtil();
			
			try {

				MongoCollection<Document> connect = util.getConnect();
				com.library.mongodbutil.Book findBookByIsbn = util.findBookByIsbn(connect, isbn);
			
				//System.out.println(findBookByIsbn.getIsbn()+" "+findBookByIsbn.getB_na());
				if(findBookByIsbn.getIsbn()!=null)
					System.out.println("��mongodb��ȡ���ˣ�"+findBookByIsbn.getB_na());
				else {
					
					int getfromNet = JOptionPane.showConfirmDialog(null,"���ݿ���ʱû�и�ͼ�����Ϣ���Ƿ����ڴ������ϻ�ȡ��","��ʾ",JOptionPane.YES_NO_OPTION);
					if(getfromNet==0) {
						//���÷�������������ȡ��Ϣ
						Book bookFromNet = GetNewBook.getBookFromNet(isbn);
						//����ȡ������Ϣ��ӵ��������
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
					
				
				//����ȡ������Ϣ��ӵ��������
				bookNameText.setText(findBookByIsbn.getB_na());
				authorText.setText(findBookByIsbn.getB_aut());
				presslText.setText(findBookByIsbn.getPress());
				publishDateText.setText(findBookByIsbn.getPdate());
				pageNumText.setText(findBookByIsbn.getB_pnum());
				priceText.setText(findBookByIsbn.getPrice());
				introarea.setText(findBookByIsbn.getB_intro());
				
			}catch (MongoTimeoutException e1) {
				System.out.println("�������磡");
			}
		}
		
	}
	

	
	//�ж��ַ����Ƿ�ȫΪ����
	public static boolean isInteger(String str) {  
		for(int i=str.length();--i>=0;){
	        int chr=str.charAt(i);
	        if(chr<48 || chr>57)
	            return false;
	    }
	   return true; 
	}
	
	//����ָ���������ɴ����ֵ������
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
