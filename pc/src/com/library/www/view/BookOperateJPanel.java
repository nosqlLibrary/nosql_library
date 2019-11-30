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
	 * ��ѯͼ����棺
	 * 1.��ѯ�������Խ���ɸѡ
	 * 2.����Աʵ�������򵥱�ɾ��ͼ�飬�Լ��޸ĵ���ͼ����Ϣ���鿴ͳ����Ϣ
	 */
	private static final long serialVersionUID = 1L;
	private BookService bookService = new BookService();
	private Vector<Object> datas = new Vector<>();
	private static int curentPageIndex;        //��ǰҳ��                  
    private int count;//��¼һ�������ݿ��ѯ������������
    
	@SuppressWarnings({ "rawtypes", "unused" })
	private JComboBox selectType = new JComboBox();//ɸѡͼ������
	
	
	private JTable table;
	@SuppressWarnings("rawtypes")
	private JComboBox condition = new JComboBox();
	private JTextField searchField = new JTextField();
	private JButton nextbtn = new JButton("��һҳ");
	private JButton prebtn = new JButton("��һҳ");
	
	private JButton searchbtn = new JButton("��ѯ",new ImageIcon(this.getClass().getResource("/Images/search.png")) );
	private Font font=new Font("����",Font.BOLD,18); 
	
	private JLabel typeLabel = new JLabel("ɸѡͼ�����");
	private JLabel tiplabel = new JLabel();
	private ArrayList<String> bookTypeNames;

	
	@SuppressWarnings("unchecked")
	public BookOperateJPanel() {
		
        setSize(1015,600);
        setLayout(null);
		//��ʼ����񼰱�ͷ
        table=new JTable();
	    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    String[] columnNames = new String[]{"ȫѡ","���","����","ͼ��״̬","����","������","��ϸ��Ϣ","ɾ��","�޸�"};
	    
	  
	    
	    //���ò�ѯ������
	    searchField.setBounds(250, 430 , 390, 30); 
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
	    searchbtn.setBounds(639, 430, 110, 29);
	    searchbtn.setFont(font); 
	    searchbtn.setBackground(new Color(255,255,210));
		searchbtn.addActionListener(this);
		searchbtn.setFocusPainted(false);
		
		
		
		nextbtn.setBounds(140, 405, 80, 30);
		nextbtn.setFont(font); 
		nextbtn.setContentAreaFilled(false);
		nextbtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���
		nextbtn.addActionListener(this);
		nextbtn.setFocusPainted(false);
		add(nextbtn);
		
		prebtn.setBounds(50, 405, 80, 30);
		prebtn.setFont(font); 
		prebtn.setContentAreaFilled(false);
		prebtn.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���
		prebtn.addActionListener(this);
		prebtn.setFocusPainted(false);
		add(prebtn);
		
		typeLabel = new JLabel("ͼ�����");
		typeLabel.setBounds(250,480, 110, 25);
		typeLabel.setFont(font);
		add(typeLabel);
		
		//���������
		condition.setBounds(350, 480, 400, 25);
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
		tiplabel.setBounds(800, 400, 180, 30);
		tiplabel.setVisible(false);
		add(tiplabel);
		
		TableModelProxy tableModel = new TableModelProxy(columnNames,datas);
		
		 
	    //Ӧ�ñ��ģ��
	    this.table.setModel(tableModel);  
	    this.table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
  
	    //��6��7��8����Ӱ�ť
	    this.table.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor(this.table,"��ϸ��Ϣ"));    	  
	    this.table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender("��ϸ��Ϣ")); 
	    
	    this.table.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor(this.table,"ɾ��"));  
	    this.table.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender("ɾ��"));
	    
	    this.table.getColumnModel().getColumn(8).setCellEditor(new MyButtonEditor(this.table,"�޸�"));  
	    this.table.getColumnModel().getColumn(8).setCellRenderer(new MyButtonRender("�޸�"));
	    //�ڵ�0����Ӷ�Ӧ�Ĵ�����
	    this.table.getColumnModel().getColumn(0).setCellEditor(new MyJCheckBoxEditor());  
	    this.table.getColumnModel().getColumn(0).setCellRenderer(new MyJCheckBoxRenderer());
	    
//	    initdata();
	    this.table.getModel().addTableModelListener(this.table);
        this.table.setRowSelectionAllowed(false);// ��ֹ����ѡ���ܡ���Ȼ�ڵ����ťʱ�������ж��ᱻѡ�С�Ҳ����ͨ��������ʽ��ʵ�֡�   
	    table.setRowHeight(30);
	    //table.setFont(font);
	    
	    
	    //��������С���������ӵ��������
	    JScrollPane jsp = new JScrollPane(table);
	    //jsp.setPreferredSize(new Dimension(1000, 400));
	    jsp.setViewportView(table);
	    
	    jsp.setBounds(0, 0, 1000, 400);
	    jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	    add(jsp,0);
		//ˢ�±��
		this.table.updateUI();
	    add(searchbtn);
	    
		
		
	    setVisible(true);
	    
	    
		
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == searchbtn) {
			String text = searchField.getText();
			this.datas.clear();
			try {
				if(!text.equals("����/����/ISBN/ͼ����/������")){
					getDataByText(1,text);
				}else {
					getData(1);
				}
			}catch (Exception e3) {
				e3.printStackTrace();
			}
		}else if(e.getSource() == nextbtn) {
			String text = searchField.getText();
			if(!text.equals("����/����/ISBN/ͼ����/������")){
				getDataByText(curentPageIndex + 1 ,text);
			}else {
				getData(curentPageIndex + 1);
			}
			
           	System.out.println("��ǰҳ:"+curentPageIndex);
           	this.table.updateUI();
			
 			
		}else if(e.getSource() == prebtn) {
			if (curentPageIndex > 1) {
				String text = searchField.getText();
				if(!text.equals("����/����/ISBN/ͼ����/������")){
					getDataByText(curentPageIndex -1 ,text);
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
			JOptionPane.showMessageDialog(null, "�Ѿ������һҳ��");
			return;
		}
		//������ʼ��
		count  = 0;
		curentPageIndex = page;
		this.datas.clear();
		
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
					
					Object[] data =  {new Boolean(false),uniqueBook.getBid(),bookDetail.getBname(),str,bookDetail.getBauthor(),bookDetail.getBproduction(),"","",""};    //������
					this.datas.add(data);
					break;
				}
				
			}
		}

   
		this.table.updateUI();
		tiplabel.setText("�ܹ�������" + count + "����¼");
		tiplabel.setVisible(true);
	}
	
	//��ʼ������е�����
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
	    	JOptionPane.showMessageDialog(null, "�Ѿ������һҳ��");
	    	return;
	    }
	    
	    //������ʼ��
	    count  = 0;
	    curentPageIndex = page;
	    this.datas.clear();
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
				//System.out.println("heihei");
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
					
					Object[] data =  {new Boolean(false),uniqueBook.getBid(),bookDetail.getBname(),str,bookDetail.getBauthor(),bookDetail.getBproduction(),"","",""};    //������
					
					this.datas.add(data);
					
					
				}
				
			}
		}
	    
	    
	    tiplabel.setText("�ܹ�������" + count + "����¼");
		tiplabel.setVisible(true);

	    this.table.updateUI();
	}

}
