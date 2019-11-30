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
import java.util.Date;
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

import com.library.www.po.Book;
import com.library.www.po.Borrow;
import com.library.www.po.UniqueBook;
import com.library.www.po.User;
import com.library.www.service.BorrowService;
import com.library.www.util.CheckHeaderCellRenderer;
import com.library.www.util.TableModelProxy;

public class UserBorrowMsgJPanel extends JPanel implements ActionListener {
	/**
	 * ĳ�û��Ľ�����Ϣ����
	 * 1.��ѯ����Ľ�����Ϣ
	 * 2.�ܰ�����/ISBN/ͼ����/����ʱ�䣨��3�����ڣ�12�����ڣ��Լ�¼��������
	 * 3.�ܹ�ɸѡ���ѻ���δ������Ϣ
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Object> datas = new Vector<>();//��ű������
	//private Vector<Object> smalldatas = new Vector<>();//��ŵ�ǰ��������
	private static int curentPageIndex;        //��ǰҳ��                  
    
    private int count;//��¼һ�������ݿ��ѯ������������
    private int selectIndex = 0;//��¼�Ƿ�Ҫ��ȡ���м�¼ �� ֻҪ�ѹ黹/δ�黹������
	
	
	private JTable table;
	private JLabel timelabel = new JLabel("ɸѡ����ʱ�䣺");
	
	private JLabel tiplabel = new JLabel();
	@SuppressWarnings("rawtypes")
	private JComboBox condition = new JComboBox();
	private JTextField searchField = new JTextField();
	private JRadioButton alljrb,backjrb,notbackjrb;
	private  ButtonGroup buttonGroup = new ButtonGroup();
	
	private JButton nextbtn = new JButton("��һҳ");
	private JButton prebtn = new JButton("��һҳ");
	private JButton searchbtn = new JButton("��ѯ",new ImageIcon(this.getClass().getResource("/Images/search.png")) );
	private Font font=new Font("����",Font.BOLD,18); 
	private BorrowService bs = new BorrowService();
	@SuppressWarnings("rawtypes")
	private ArrayList<ArrayList> borrowsDetail = new ArrayList<>();
	
	private User user;
	
	@SuppressWarnings("unchecked")
	public UserBorrowMsgJPanel(User u) {
		
        setSize(1015,600);
        
        this.user = u;
        
        setLayout(null);
		//��ʼ����񼰱�ͷ
        table=new JTable();
	    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    String[] columnNames = new String[]{"���","ͼ����","ͼ����","��ǰ״̬","����ʱ��","Ӧ������","�黹ʱ��"};
	    
	    TableModelProxy tableModel = new TableModelProxy(columnNames,datas);
		
		 
	    //Ӧ�ñ��ģ��
	    this.table.setModel(tableModel);  
	    this.table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
	   
	    this.table.setRowHeight(30);
	    this.table.setRowSelectionAllowed(false);// ��ֹ����ѡ���ܡ���Ȼ�ڵ����ťʱ�������ж��ᱻѡ�С�Ҳ����ͨ��������ʽ��ʵ�֡�   

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
	    
		//����ɸѡ��������ʾȫ��ͼ��/��ʾδ��ͼ��/��ʾ�ѻ�ͼ��
		alljrb = new JRadioButton("ȫ����¼",true);
		notbackjrb = new JRadioButton("δ����¼");
		backjrb = new JRadioButton("�ѻ���¼");
		
		alljrb.setBounds(250, 465, 120, 30);
		alljrb.setFont(font);
		alljrb.setOpaque(false);
        
		notbackjrb.setBounds(400, 465, 120, 30);
		notbackjrb.setFont(font);
		notbackjrb.setOpaque(false);
		
		backjrb.setBounds(550, 465, 120, 30);
		backjrb.setFont(font);
		backjrb.setOpaque(false);
		
		buttonGroup.add(alljrb);
		buttonGroup.add(notbackjrb);
		buttonGroup.add(backjrb);
		
		add(alljrb);
		add(notbackjrb);
		add(backjrb);
		
	    
	    //���ò�ѯ������
	    searchField.setBounds(250, 430 , 400, 30); 
	    searchField.setText("����/ISBN/ͼ����"); 
	    searchField.setForeground(Color.GRAY);
	    
	    
	    //��Ӽ�������ʾ��ʾ����
	    searchField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				//��ȡ����ʱ�������ʾ����
				String temp = searchField.getText();
				if(temp.equals("����/ISBN/ͼ����")) {
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
					searchField.setText("����/ISBN/ͼ����");
				}

			}
	    	
	    });
	    
	    
	    add(searchField);
	    
	    //��Ӳ�ѯ��ť
	    searchbtn.setBounds(649, 430, 110, 29);
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
		
		tiplabel.setFont(font);
		tiplabel.setOpaque(false);
		tiplabel.setBounds(800, 400, 180, 30);
		tiplabel.setVisible(false);
		add(tiplabel);
		
		
		timelabel.setBounds(250, 508, 150, 30);
        timelabel.setFont(font);
        
        //ɸѡ����������
  		condition.setBounds(378, 510, 360, 25);
  		condition.setFont(font);
  		//�����������ɸѡ����
  		condition.addItem("ȫ��");
  		condition.addItem("���һ��");
  		condition.addItem("���һ��");
  		condition.addItem("���һ����");
  		condition.addItem("���������");
  		condition.addItem("�������");
  		condition.addItem("���һ��");
		
        add(timelabel);
        add(condition);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == searchbtn) {
			
    		if(alljrb.isSelected()) {
    			selectIndex = 1;//ȫ����¼
    		}else if(notbackjrb.isSelected()) {
    			selectIndex = 2;//ֻҪδ�黹�ļ�¼
    		}else {
    			selectIndex = 3;//ֻҪ�黹�˵ļ�¼
    		}
    		
			String text = searchField.getText();
			this.datas.clear();
			
			//������ʼ��
			count  = 0;
			curentPageIndex = 1;
			
			
			if(text.equals("����/ISBN/ͼ����")){
				getData(1);
			
			}else {
				getDataByText(text,1);
			}
		
			this.table.updateUI();
			tiplabel.setText("�ܹ�������" + count + "����¼");
			tiplabel.setVisible(true);
		}else if(e.getSource() == nextbtn) {
			String text = searchField.getText();
			if(!text.equals("����/ISBN/ͼ����")){
				getDataByText(text,curentPageIndex + 1);
			}else {
				getData(curentPageIndex + 1);
			}
			
            System.out.println("��ǰҳ:"+curentPageIndex);
            this.table.updateUI();
			
 			
		}else if(e.getSource() == prebtn) {
			if (curentPageIndex > 1) {
				
				String text = searchField.getText();
				if(!text.equals("����/ISBN/ͼ����")){
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

	public void getDataByText(String text,int page) {
		//����������������в�ѯ
		//���������������borrows���¸�ֵ
		borrowsDetail.clear();
		try {
			borrowsDetail = bs.getUserBorrowsByCondition(text,user,page);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<UniqueBook> ubooks = new ArrayList<>();
		
		ArrayList<Borrow> borrows = new ArrayList<>();
		
		borrows = borrowsDetail.get(0);
		ubooks = borrowsDetail.get(1);
		books = borrowsDetail.get(2);
		
		if(page == 1 && borrows.size() == 0 ) {
			JOptionPane.showMessageDialog(null, "�����������ݣ�");
			this.table.updateUI();
			return;
		}
		
		if(borrows.size() == 0 ) {
			JOptionPane.showMessageDialog(null, "�Ѿ������һҳ");
			return;
		}else {
			//������ʼ��
			count  = 0;
			curentPageIndex = page;
			this.datas.clear();
			
			
    		
		
			if(condition.getSelectedItem() == "���һ��" ) {
				selectData(1);
			}else if(condition.getSelectedItem() == "���һ��" ) {
				selectData(7);
			}else if(condition.getSelectedItem() == "���һ����" ) {
				selectData(30);
			}else if(condition.getSelectedItem() == "���������" ) {
				selectData(90);
			}else if(condition.getSelectedItem() == "�������" ) {
				selectData(180);
			}else if(condition.getSelectedItem() == "���һ��" ) {
				selectData(365);
			}else {
				
				
				for (int i = 0; i < borrows.size(); i++) {
					Borrow borrow = borrows.get(i);
					UniqueBook ubook = ubooks.get(i);
					Book book = books.get(i);
				
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
					count++;
					if(borrow.getBackdate() != null && (selectIndex == 1||selectIndex == 3)) {
						
						Object[] data =  {borrow.getId(),borrow.getBid(),book.getBname(),bstatus,borrow.getTakedate().toString(),borrow.getDeadline().toString(),borrow.getBackdate().toString(),};    //������
						this.datas.add(data);
					}else if(borrow.getBackdate() == null&&(selectIndex == 1||selectIndex == 2)){
						Object[] data =  {borrow.getId(),borrow.getBid(),book.getBname(),bstatus,borrow.getTakedate().toString(),borrow.getDeadline().toString(),""};    //������
						this.datas.add(data);
					}
						
					
				}
			}
		}

		this.table.updateUI();
		tiplabel.setText("�ܹ�������" + count + "����¼");
		tiplabel.setVisible(true);
	}
	
	@SuppressWarnings("unchecked")
	private void selectData(int selectdays) {
		Date nowdate = new Date();
		int days;
		long time;
		
		
		ArrayList<Book> books = new ArrayList<>();
		ArrayList<UniqueBook> ubooks = new ArrayList<>();
		
		ArrayList<Borrow> borrows = new ArrayList<>();
		
		borrows = borrowsDetail.get(0);
		
		ubooks = borrowsDetail.get(1);
		books = borrowsDetail.get(2);
		
		if(borrows.size()==0) {
			JOptionPane.showMessageDialog(null, "�Ѿ������һҳ��");
			return;
		}else {
		
			for (int i = 0; i < borrows.size(); i++) {
				
				Borrow borrow = borrows.get(i);
				time = nowdate.getTime()-borrow.getTakedate().getTime();
				days=(int) Math.floor(time/(24*3600*1000));
				
				if(days <= selectdays) {
					
					UniqueBook ubook = ubooks.get(i);
					Book book = books.get(i);
					
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
					count++;
					if(borrow.getBackdate() != null && (selectIndex == 1||selectIndex == 3)) {
						Object[] data =  {borrow.getId(),borrow.getBid(),book.getBname(),bstatus,borrow.getTakedate().toString(),borrow.getDeadline().toString(),borrow.getBackdate().toString()};    //������
						this.datas.add(data);
					}else if(borrow.getBackdate()== null && (selectIndex == 1||selectIndex == 2)){
						Object[] data =  {borrow.getId(),borrow.getBid(),book.getBname(),bstatus,borrow.getTakedate().toString(),borrow.getDeadline().toString(),""};    //������
						this.datas.add(data);
					}
					
				}
			}
		
		}
		this.table.updateUI();
		tiplabel.setText("�ܹ�������" + count + "����¼");
		tiplabel.setVisible(true);
		
	}

	
	@SuppressWarnings("unchecked")
	public void getData(int page)   {
		//��ʾȫ����Ϣ
		borrowsDetail.clear();
		try {
			borrowsDetail = this.bs.getUserBorrowMsg(user,page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<Book> books = borrowsDetail.get(2);
		ArrayList<UniqueBook> ubooks = borrowsDetail.get(1);
		ArrayList<Borrow> borrows = borrowsDetail.get(0);
		
		if(borrows.size() == 0) {
			JOptionPane.showMessageDialog(null, "�Ѿ������һҳ��");
			return;
		}
		
		//������ʼ��
		count  = 0;
		curentPageIndex = page;
		this.datas.clear();
		
		
		if(borrows.size()!=0) {
			if(condition.getSelectedItem() == "���һ��" ) {
				selectData(1);
			}else if(condition.getSelectedItem() == "���һ��" ) {
				selectData(7);
			}else if(condition.getSelectedItem() == "���һ����" ) {
				selectData(30);
			}else if(condition.getSelectedItem() == "���������" ) {
				selectData(90);
			}else if(condition.getSelectedItem() == "�������" ) {
				selectData(180);
			}else if(condition.getSelectedItem() == "���һ��" ) {
				selectData(365);
			}else {
				for (int i = 0; i < borrows.size(); i++) {
					Borrow borrow = borrows.get(i);
					
					UniqueBook ubook = ubooks.get(i);
					Book book = books.get(i);
					count = borrows.size();
						
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
						
					if(borrow.getBackdate() != null && (selectIndex == 1||selectIndex == 3)) {
						Object[] data =  {borrow.getId(),borrow.getBid(),book.getBname(),bstatus,borrow.getTakedate().toString(),borrow.getDeadline().toString(),borrow.getBackdate().toString()};    //������
						this.datas.add(data);
					}else if(borrow.getBackdate() == null && (selectIndex == 1||selectIndex == 2)){
						Object[] data =  {borrow.getId(),borrow.getBid(),book.getBname(),bstatus,borrow.getTakedate().toString(),borrow.getDeadline().toString(),""};    //������
						this.datas.add(data);
					}
					
					
				}
			}
		}
		this.table.updateUI();
		
	}
	

}
