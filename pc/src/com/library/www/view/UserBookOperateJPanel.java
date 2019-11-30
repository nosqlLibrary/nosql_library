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
	 * �û�ͼ���������
	 * 	��Ҫ�ǲ鿴��ǰͼ�����ʲô��
	 */
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	private Vector<Object> datas = new Vector<>();//��ű������
	
	
	private static int curentPageIndex;        //��ǰҳ��                  
    private int count;//��¼һ�������ݿ��ѯ������������
	
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
	private JButton searchbtn = new JButton("��ѯ",new ImageIcon(this.getClass().getResource("/Images/search.png")) );
	private Font font=new Font("����",Font.BOLD,18); 
	private JButton nextbtn = new JButton("��һҳ");
	private JButton prebtn = new JButton("��һҳ");
	//private JButton returnbtn = new JButton("����");
	
	private ArrayList<String> bookTypeNames;
	private JLabel typeLabel = new JLabel("ɸѡͼ�����");
	private JLabel tiplabel = new JLabel();
	
	@SuppressWarnings("unchecked")
	public UserBookOperateJPanel(User u) {
		
		setSize(1015,600);
        setLayout(null);
        
        
		//��ʼ����񼰱�ͷ
        table=new JTable();
	    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    String[] columnNames = new String[]{"���","����","ͼ��״̬","����","������","ISBN","��ϸ��Ϣ"};
	    //���ò�ѯ������
	    searchField.setBounds(250, 450 , 400, 30); 
	    searchField.setText("����/����/ISBN/ͼ����/������"); 
	    searchField.setForeground(Color.GRAY);
	    
	    //��Ӽ�������ʾ��ʾ����
	    searchField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				//��ȡ����ʱ�������ʾ����
				String temp = searchField.getText();
				if(temp.equals("����/����/ISBN/ͼ����/������")) {
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
					searchField.setText("����/����/ISBN/ͼ����/������");
				}
			}
	    	
	    });
	    
	    add(searchField);
	    
	    //��Ӳ�ѯ��ť
	    searchbtn.setBounds(648, 450, 110, 29);
	    searchbtn.setFont(font); 
	    searchbtn.setBackground(new Color(255,255,210));
		searchbtn.addActionListener(this);
		searchbtn.setFocusPainted(false);
		
		nextbtn.setBounds(140, 425, 80, 30);
		nextbtn.setFont(font); 
		nextbtn.setContentAreaFilled(false);
		nextbtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���
		nextbtn.addActionListener(this);
		nextbtn.setFocusPainted(false);
		add(nextbtn);
		
		prebtn.setBounds(50, 425, 80, 30);
		prebtn.setFont(font); 
		prebtn.setContentAreaFilled(false);
		prebtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���
		prebtn.addActionListener(this);
		prebtn.setFocusPainted(false);
		add(prebtn);
		
		typeLabel = new JLabel("ͼ�����");
		typeLabel.setBounds(250,500, 110, 25);
		typeLabel.setFont(font);
		add(typeLabel);
		
		//���������
		condition.setBounds(350, 500, 400, 25);
		Font conditionFont=new Font("����",Font.BOLD,15);
		condition.setFont(conditionFont);
		//�����������ɸѡ����
		condition.addItem("ȫ��");
		//��ȡ���е�ͼ�����
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
		 
	    //Ӧ�ñ��ģ��
	    this.table.setModel(tableModel);  
	    this.table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
  
	    //��6����Ӱ�ť
	    this.table.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor(this.table,"��ϸ��Ϣ"));    	  
	    this.table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender("��ϸ��Ϣ")); 
	    
	    
        this.table.setRowSelectionAllowed(false);// ��ֹ����ѡ���ܡ���Ȼ�ڵ����ťʱ�������ж��ᱻѡ�С�Ҳ����ͨ��������ʽ��ʵ�֡�   
	    table.setRowHeight(30);
	       

	    //��������С���������ӵ��������
	    JScrollPane jsp = new JScrollPane(table);
	    jsp.setPreferredSize(new Dimension(1000, 400));
	    jsp.setViewportView(table);
	    jsp.setBounds(0, 0, 1000, 400);
	    jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	    add(jsp,0);
		//ˢ�±��
		this.table.updateUI();
	    add(searchbtn);
	    
//	    //����logo
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
			JOptionPane.showMessageDialog(null, "�Ѿ������һҳ��");
			return;
		}
		
		curentPageIndex = page;
		this.datas.clear();
		count = 0;
		
		//��ȡͼ�����
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
						str = "�ڿ�";
					}else if(status == 'B') {
						str = "���";
					}else if(status == 'C') {
						str = "���";
					}else if(status == 'D') {
						str = "��ʧ";
					}
					
					Object[] data =  {uniqueBook.getBid(),bookDetail.getBname(),str,bookDetail.getBauthor(),bookDetail.getBproduction(),bookDetail.getIsbn(),""};    //������
					this.datas.add(data);
					
				}
				
			}
		}
	   
	   
		this.table.updateUI();
		tiplabel.setText("�ܹ�������" + count + "����¼");
		tiplabel.setVisible(true);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == searchbtn) {
			this.datas.clear();
			String text = searchField.getText();
			//������ʼ��
			count  = 0;
			curentPageIndex = 1;
			
			if(!text.equals("����/����/ISBN/ͼ����/������")){
				getDataByText(text, 1);
			}else {
				getData(1);
			}
			
			this.table.updateUI();
			
		
		}else if(e.getSource() == nextbtn) {
			
			String text = searchField.getText();
			
			if(!text.equals("����/����/ISBN/ͼ����/������")){
				getDataByText(text,curentPageIndex + 1);
			}else {
				getData(curentPageIndex + 1);
			}
			
            System.out.println("��ǰҳ:"+curentPageIndex);
            this.table.updateUI();

 			
		}else if(e.getSource() == prebtn) {
			
			
			if (curentPageIndex > 1) {
				
				String text = searchField.getText();
				if(!text.equals("����/����/ISBN/ͼ����/������")){
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
			JOptionPane.showMessageDialog(null,"�Ѿ������һҳ��");
			return;
		}
		
		curentPageIndex = page;
		this.datas.clear();
		count = 0;
		
		//��ȡͼ�����
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
						str = "�ڿ�";
					}else if(status == 'B') {
						str = "���";
					}else if(status == 'C') {
						str = "���";
					}else if(status == 'D') {
						str = "��ʧ";
					}
					
					
					Object[] data =  {uniqueBook.getBid(),bookDetail.getBname(),str,bookDetail.getBauthor(),bookDetail.getBproduction(),bookDetail.getIsbn(),""};    //������
					this.datas.add(data);
					break;
				}
				
			}
		}
		
	   
		this.table.updateUI();
		tiplabel.setText("�ܹ�������" + count + "����¼");
		tiplabel.setVisible(true);
		
	}

}
