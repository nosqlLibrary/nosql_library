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
	 * ͼ���쳣�Ǽǽ���
	 * 1.��Ҫ�Ƕ�ͼ��״̬���� �޸�      �ڿ�(A)�����(B), ���(C), ��ʧ(D)��
	 * 2.�ܽ���������һͼ������쳣�Ǽǣ�ͼ����١�ͼ�鶪ʧ��
	 * 	�����û�id���鱾������Ϣ���û����ṩ�ģ��������������õ��鱾�ľ�����Ϣ�����鱾����Ϣ�����޸ġ�
	 * 	����������ĳ���û�ͬʱ��ʧ�˶౾ͼ�顣
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton searchbtn;
	private JButton nextbtn = new JButton("��һҳ");
	private JButton prebtn = new JButton("��һҳ");
	private JLabel tiplabel = new JLabel();
	private JTextField searchField = new JTextField();
	private Font font=new Font("����",Font.BOLD,18); 
	
	private JTable table;
	private Vector<Object> datas = new Vector<>();//��ű������
	
	private static int curentPageIndex;        //��ǰҳ��                  
    private int count;//��¼һ�������ݿ��ѯ������������
	//private Admin admin;
	private BookService bookService = new BookService();
	private BorrowService borrowService = new BorrowService();
	//private UserService uservice = new UserService();
	
	public ErrorRegisterJPanel() {
		
		
		setSize(1015,600);
		setLayout(null);
		
		//��ʼ����񼰱�ͷ
        table=new JTable();
	    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    String[] columnNames = new String[]{"ȫѡ","ͼ����","����","ͼ��״̬","�û����","�û���","��ϸ��Ϣ","���","��ʧ"};
	    
	    TableModelProxy tableModel = new TableModelProxy(columnNames,datas);
		
	    //Ӧ�ñ��ģ��
	    this.table.setModel(tableModel);  
	    this.table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
	    
	    //��6��7��8����Ӱ�ť
	    this.table.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor(this.table,"��ϸ��Ϣ"));    	  
	    this.table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender("��ϸ��Ϣ")); 
	    
	    this.table.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor(this.table,"���"));  
	    this.table.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender("���"));
	    
	    this.table.getColumnModel().getColumn(8).setCellEditor(new MyButtonEditor(this.table,"��ʧ"));  
	    this.table.getColumnModel().getColumn(8).setCellRenderer(new MyButtonRender("��ʧ"));
	    //�ڵ�0����Ӷ�Ӧ�Ĵ�����
	    this.table.getColumnModel().getColumn(0).setCellEditor(new MyJCheckBoxEditor());  
	    this.table.getColumnModel().getColumn(0).setCellRenderer(new MyJCheckBoxRenderer());
	    

	    
        this.table.setRowSelectionAllowed(false);// ��ֹ����ѡ���ܡ���Ȼ�ڵ����ťʱ�������ж��ᱻѡ�С�Ҳ����ͨ��������ʽ��ʵ�֡�   
	    table.setRowHeight(30);
	    
	    //��������С���������ӵ��������
	    JScrollPane jsp = new JScrollPane(table);
	    jsp.setViewportView(table);
	    jsp.setBounds(0, 110, 1000, 490);
	    jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		searchbtn = new JButton("��ѯ",new ImageIcon(this.getClass().getResource("/Images/search.png")) );
		
		
		searchbtn.setFont(font); 
		searchbtn.setBounds(619, 30, 120, 29);
		searchbtn.setBackground(new Color(255,255,210));
		searchbtn.addActionListener(this);
		searchbtn.setFocusPainted(false);
		
		
		//���ò�ѯ������
	    searchField.setBounds(240,30, 380, 30); 
	    searchField.setText("����/ISBN/ͼ����/�û���/�û����"); 
	    searchField.setForeground(Color.GRAY);
	    
	    //��Ӽ�������ʾ��ʾ����
	    searchField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				//��ȡ����ʱ�������ʾ����
				String temp = searchField.getText();
				if(temp.equals("����/ISBN/ͼ����/�û���/�û����")) {
					searchField.setText("");
					searchField.setForeground(Color.BLACK);
				}

				
			}

			@Override
			public void focusLost(FocusEvent e) {
				//ʧȥ����ʱ��û���������ݣ���ʾ��ʾ����
				String temp = searchField.getText();
				if(temp.equals("")) {
					searchField.setForeground(Color.GRAY);
					searchField.setText("����/ISBN/ͼ����/�û���/�û����");
				}

			}
	    	
	    });
	    
	    
	    nextbtn.setBounds(100, 65, 80, 30);
		nextbtn.setFont(font); 
		nextbtn.setContentAreaFilled(false);
		nextbtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���
		nextbtn.addActionListener(this);
		nextbtn.setFocusPainted(false);
		add(nextbtn);
		
		prebtn.setBounds(10, 65, 80, 30);
		prebtn.setFont(font); 
		prebtn.setContentAreaFilled(false);
		prebtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���
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
			
			//������ʼ��
			count  = 0;
			curentPageIndex = 1;
			
			
			if(!text.equals("����/ISBN/ͼ����/�û���/�û����")){
				getDataByText(text,1);
			}else {
				getData(1);
			}
			
		   
			this.table.updateUI();
			
			
		}else if(e.getSource() == nextbtn) {
			
			String text = searchField.getText();
			if(!text.equals("����/ISBN/ͼ����/�û���/�û����")){
				getDataByText(text,curentPageIndex + 1);
			}else {
				getData(curentPageIndex + 1);
			}
			
            System.out.println("��ǰҳ:"+curentPageIndex);
            this.table.updateUI();
			
		}else if(e.getSource() == prebtn) {
			
			if (curentPageIndex > 1) {
				
				String text = searchField.getText();
				if(!text.equals("����/ISBN/ͼ����/�û���/�û����")){
					getDataByText(text,curentPageIndex - 1);
				}else {
					getData(curentPageIndex - 1);
				}
               
                System.out.println("��ǰҳ:"+curentPageIndex);
               
				this.table.updateUI();
            }else {
				JOptionPane.showMessageDialog(null, "�Ѿ��ǵ�һҳ");
			}
         
		}
		
	}

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
			
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void getDataByText(String text,int page) {
		/**
		 * ������������п������û�����Ϣ��Ҳ�������鱾����Ϣ
		 * 1.����ͼ����Ϣ�Ҹ�ͼ��û�б����Ĺ��������ݿ��л�ȡ����������ͼ����Ϣ
		 * 2.�������ͼ����Ϣ��ͼ���������Ĺ��������ݿ��л�ȡ����Ӧ�Ľ��ļ�¼��ͼ����Ϣ
		 * ---�ϱ�����������Ǳ�������ͼ�鼯�Ϻͽ��ļ�¼���ϣ����ͼ����һ�£���Ӧ��ʾ���û���Ϣ������ֻ��ʾͼ����Ϣ��
		 * 3.�����������û���Ϣ����ôͼ�鼯��Ϊ�գ����ļ�¼���Ͽ���������
		 * ---�������ļ�¼����������
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
			JOptionPane.showMessageDialog(null, "�����������ݣ�");
			return;
		}
		
		curentPageIndex = page;
		this.datas.clear();
		count = 0;
		
		//��������û���Ϣ  ��ͼ�鼯����������������
		if(uniqueBooks.size() == 0) {
			
			borrowUbook = booksByCondition.get(2);
			borrowBook = booksByCondition.get(3);
			for (int i = 0; i < borrowMsg.size(); i++) {
				Borrow bo = borrowMsg.get(i);
				User user = userByCondition.get(i);
				UniqueBook ubook = borrowUbook.get(i);
				Book book = borrowBook.get(i);
				
				//��ͼ��״̬�޸�Ϊ�ַ�����ʽ
				String bstatus = "";
				char st = ubook.getStatus();
				//�ڿ⣨A)�����(B), ���(C), ��ʧ(D)
				if(st == 'A') {
					bstatus = "�ڿ�";
				}else if(st == 'B') {
					bstatus = "���";
				}else if(st == 'C') {
					bstatus = "���";
				}else if(st == 'D') {
					bstatus = "��ʧ";
				}
				
				if(bo.getBid().equals(ubook.getBid()) && bo.getBackdate()==null) {
					//ͼ�����������Ϣ�ı����ͬ   ����û�й黹����ʱ��Ҫ����û���Ϣ
					count++;
					Object[] data =  {new Boolean(false),ubook.getBid(),book.getBname(),bstatus,bo.getUserid(),user.getUsername(),"","",""};    //������
					this.datas.add(data);
					
					
				}else if(bo.getBid().equals(ubook.getBid()) && bo.getBackdate()!=null){
					//ͼ�����������Ϣ�ı����ͬ     ���й黹����ʱ����������û���Ϣ
					count++;
					Object[] data =  {new Boolean(false),ubook.getBid(),book.getBname(),bstatus,"","","","",""};    //������
					this.datas.add(data);
					
				}
				
			}
			
		}else {//�������ͼ����Ϣ �������Ͽ��ܶ�������
			for (UniqueBook uniqueBook : uniqueBooks) {
				for (Book bookDetail : books) {
					if(uniqueBook.getIsbn().equals(bookDetail.getIsbn())){ 
						//��ͼ��״̬�޸�Ϊ�ַ�����ʽ
						String bstatus = "";
						char st = uniqueBook.getStatus();
						//�ڿ⣨A)�����(B), ���(C), ��ʧ(D)
						if(st == 'A') {
							bstatus = "�ڿ�";
						}else if(st == 'B') {
							bstatus = "���";
						}else if(st == 'C') {
							bstatus = "���";
						}else if(st == 'D') {
							bstatus = "��ʧ";
						}
						
						int isBorrow = 0;//����Ƿ�ͼ����ϢҲ�ڽ��ļ�¼��
						//������ļ�¼��Ϊ��
						for (int i = 0; i < borrowMsg.size(); i++) {
							Borrow bo = borrowMsg.get(i);
							User u = userByCondition.get(i);
							if(bo.getBid().equals(uniqueBook.getBid()) && isBorrow==0 && bo.getBackdate()==null) {
								//ͼ�����������Ϣ�ı����ͬ   ����û�й黹����ʱ��Ҫ����û���Ϣ
								count++;
								Object[] data =  {new Boolean(false),uniqueBook.getBid(),bookDetail.getBname(),bstatus,bo.getUserid(),u.getUsername(),"","",""};    //������
								this.datas.add(data);
								
								isBorrow = 1;
							}else if(bo.getBid().equals(uniqueBook.getBid()) && isBorrow==0 && bo.getBackdate()!=null){
								//ͼ�����������Ϣ�ı����ͬ     ���й黹����ʱ����������û���Ϣ
								count++;
								Object[] data =  {new Boolean(false),uniqueBook.getBid(),bookDetail.getBname(),bstatus,"","","","",""};    //������
								this.datas.add(data);
								isBorrow = 1;
							}
						}
						
						if(isBorrow == 0) {
							count++;
							Object[] data =  {new Boolean(false),uniqueBook.getBid(),bookDetail.getBname(),bstatus,"","","","",""};    //������
							this.datas.add(data);
							
						}
							
					}
				}
				
			}
		
		
		}
		
		
		this.table.updateUI();
		tiplabel.setText("�ܹ�������" + count + "����¼");
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
			JOptionPane.showMessageDialog(null, "û�л�ȡ�����ݣ�");
			return;
		}else if(singleBooks.size() == 0) {
			JOptionPane.showMessageDialog(null, "�Ѿ������һҳ��");
			return;
		}
		
		//������ʼ��
		count  = 0;
		curentPageIndex = page;
		this.datas.clear();
				
		for (UniqueBook ubook : singleBooks) {
			for (Book book : books) {
				if(book.getIsbn().equals(ubook.getIsbn())) {
					//��ͼ��״̬�޸�Ϊ�ַ�����ʽ
					String bstatus = "";
					char st = ubook.getStatus();
					//�ڿ⣨A)�����(B), ���(C), ��ʧ(D)
					if(st == 'A') {
						bstatus = "�ڿ�";
					}else if(st == 'B') {
						bstatus = "���";
					}else if(st == 'C') {
						bstatus = "���";
					}else if(st == 'D') {
						bstatus = "��ʧ";
					}
					
					int j = 0;//���ͼ���Ƿ��˽��
					for (int i = 0; i < borrows.size(); i++) {
						Borrow bo = borrows.get(i);
						User user = users.get(i);
						
					
						if(bo.getBid().equals(ubook.getBid()) && bo.getBackdate()==null && j == 0) {
							//ͼ�����������Ϣ�ı����ͬ   ����û�й黹����ʱ��Ҫ����û���Ϣ
							count++;
							Object[] data =  {new Boolean(false),ubook.getBid(),book.getBname(),bstatus,bo.getUserid(),user.getUsername(),"","",""};    //������
							this.datas.add(data);
							j = 1;
						}else if(bo.getBid().equals(ubook.getBid()) && bo.getBackdate()!=null && j == 0){
							//ͼ�����������Ϣ�ı����ͬ     ���й黹����ʱ����������û���Ϣ
							count++;
							Object[] data =  {new Boolean(false),ubook.getBid(),book.getBname(),bstatus,"","","","",""};    //������
							this.datas.add(data);
							j = 1;
						}
					}
					
					if(j == 0) {
						count++;
						Object[] data =  {new Boolean(false),ubook.getBid(),book.getBname(),bstatus,"","","","",""};    //������
						this.datas.add(data);
						
					}
						
					
				}
			}
		}
		
				
		
		this.table.updateUI();
		tiplabel.setText("�ܹ�������" + count + "����¼");
		tiplabel.setVisible(true);
		
	}


}
