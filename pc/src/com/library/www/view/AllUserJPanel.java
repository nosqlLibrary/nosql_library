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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.library.www.po.User;
import com.library.www.po.UserType;
import com.library.www.service.UserService;
import com.library.www.util.CheckHeaderCellRenderer;
import com.library.www.util.MyButtonEditor;
import com.library.www.util.MyButtonRender;
import com.library.www.util.MyJCheckBoxEditor;
import com.library.www.util.MyJCheckBoxRenderer;
import com.library.www.util.TableModelProxy;

public class AllUserJPanel extends JPanel implements ActionListener {

	/**
	 * ����Ա�鿴�����û�����
	 * 1.�鿴�����û���״̬�������Ϣ
	 * 2.�޸��û��Ļ�������
	 */
	private static final long serialVersionUID = 1L;
	private Vector<Object> datas = new Vector<>();//��ű������
	private static int curentPageIndex;        //��ǰҳ��                  
    private int count;//��¼һ�������ݿ��ѯ������������
	
	private JTable table;
	private JTextField searchField = new JTextField();
	private JLabel tiplabel = new JLabel();
	
	private JButton searchbtn = new JButton("��ѯ",new ImageIcon(this.getClass().getResource("/Images/search.png")) );
	private JButton nextbtn = new JButton("��һҳ");
	private JButton prebtn = new JButton("��һҳ");
	private Font font=new Font("����",Font.BOLD,18); 
	
	private UserService us = new UserService();
	
	public AllUserJPanel() {
		
		setSize(1015,600);
        setLayout(null);
		//��ʼ����񼰱�ͷ
        table=new JTable();
	    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    String[] columnNames = new String[]{"ȫѡ","�û����","�û���","�û�����","�û�״̬","��ϸ��Ϣ","ɾ���û�","�޸���Ϣ"};
	    
	    
	    //���ò�ѯ������
	    searchField.setBounds(270, 440 , 380, 30); 
	    searchField.setText("�û���/�û����/ѧԺ/�û�����"); 
	    searchField.setForeground(Color.GRAY);
	    
	    //��Ӽ�������ʾ��ʾ����
	    searchField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				//��ȡ����ʱ�������ʾ����
				String temp = searchField.getText();
				if(temp.equals("�û���/�û����/ѧԺ/�û�����")) {
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
					searchField.setText("�û���/�û����/ѧԺ/�û�����");
				}

			}
	    	
	    });
	    
	    add(searchField);
	    
	    //��Ӳ�ѯ��ť
	    searchbtn.setBounds(649, 440,110, 29);
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
		
		
		TableModelProxy tableModel = new TableModelProxy(columnNames,datas);
		//Ӧ�ñ��ģ��
	    this.table.setModel(tableModel);  
	    this.table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
	    
	    //��5��6,7����Ӱ�ť
	    this.table.getColumnModel().getColumn(5).setCellEditor(new MyButtonEditor(this.table,"��ϸ��Ϣ"));    	  
	    this.table.getColumnModel().getColumn(5).setCellRenderer(new MyButtonRender("��ϸ��Ϣ")); 
	    
	    this.table.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor(this.table,"ɾ���û�"));  
	    this.table.getColumnModel().getColumn(6).setCellRenderer(new MyButtonRender("ɾ���û�"));
	    
	    this.table.getColumnModel().getColumn(7).setCellEditor(new MyButtonEditor(this.table,"�޸���Ϣ"));  
	    this.table.getColumnModel().getColumn(7).setCellRenderer(new MyButtonRender("�޸���Ϣ"));
	    
	    //�ڵ�0����Ӷ�Ӧ�Ĵ�����
	    this.table.getColumnModel().getColumn(0).setCellEditor(new MyJCheckBoxEditor());  
	    this.table.getColumnModel().getColumn(0).setCellRenderer(new MyJCheckBoxRenderer());
	    
	    getData(1);
	    
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
	
	private void getData(int page) {
		
		ArrayList<User> users = new ArrayList<>();
		try {
			users = us.getAllUsers(page);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		if(users.size() == 0) {
			JOptionPane.showMessageDialog(null, "�Ѿ������һҳ");
			return;
		}
		
		
		count = users.size();
		this.datas.clear();
		curentPageIndex = page;
		
		if(users.size()!=0) {
			for (User user : users) {
				ArrayList<UserType> utypes = new ArrayList<>();
				try {
					 utypes = us.getAllUserTypes();
				} catch (Exception e) {
					e.printStackTrace();
				}
		    	int type = user.getType();
		    	String typestr = "";
		    	for (UserType utype : utypes) {
					if(type == utype.getTypeId()) {
						typestr = utype.getTypeName();
					}
				}
		    	
				char status = user.getStatus();
				String stastr ="";
				
				//�û�״̬  Ĭ�ϣ�������A)������(B)��ע��(C)
				if(status == 'A') {
					stastr ="����";
				}else if(status == 'B') {
					stastr ="����";
				}else if(status == 'C') {
					stastr ="ע��";
				}
		    	
		    	
				Object[] data =  {new Boolean(false),user.getUserid(),user.getUsername(),typestr,stastr,"","",""};//������
				this.datas.add(data);
				
			}
		}
		
		this.table.updateUI();
		tiplabel.setText("�ܹ�������" + count + "����¼");
		tiplabel.setVisible(true);
		
	}


	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==searchbtn) {
			this.datas.clear();
			
			//������ʼ��
			count  = 0;
			curentPageIndex = 1;
			
			String text = searchField.getText();
			
			if(!text.equals("�û���/�û����/ѧԺ/�û�����")){
				getDataByText(text, 1);
			
			}else {
				getData(1);
			   
			}
			
		}else if(e.getSource() == nextbtn) {
			
			String text = searchField.getText();
			if(!text.equals("�û���/�û����/ѧԺ/�û�����")){
				getDataByText(text,curentPageIndex + 1);
			}else {
				getData(curentPageIndex + 1);
			}
			
            System.out.println("��ǰҳ:"+curentPageIndex);
            this.table.updateUI();
 			
		}else if(e.getSource() == prebtn) {

			if (curentPageIndex > 1) {
				
				String text = searchField.getText();
				if(!text.equals("�û���/�û����/ѧԺ/�û�����")){
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



	private void getDataByText(String text, int page) {
		ArrayList<User> users = new ArrayList<>();
		try {
			users = us.getUserByCondition(text,page);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		if(users.size() == 0) {
			JOptionPane.showMessageDialog(null, "�Ѿ������һҳ");
			return;
		}
		count = users.size();
		this.datas.clear();
		curentPageIndex = page;
		
		//{"�û����","�û���","�û�����","�û�״̬","��ϸ��Ϣ","ɾ���û�","�޸���Ϣ"}
		for (User user : users) {
			ArrayList<UserType> utypes = new ArrayList<>();
			try {
				 utypes = us.getAllUserTypes();
			} catch (Exception e4) {
				e4.printStackTrace();
			}
	    	int type = user.getType();
	    	String typestr = "";
	    	for (UserType utype : utypes) {
				if(type == utype.getTypeId()) {
					typestr = utype.getTypeName();
				}
			}
	    	
			char status = user.getStatus();
			String stastr ="";
			
			
			//�û�״̬  Ĭ�ϣ�������A)������(B)��ע��(C)
			if(status == 'A') {
				stastr ="����";
			}else if(status == 'B') {
				stastr ="����";
			}else if(status == 'C') {
				stastr ="ע��";
			}
			
			
			Object[] data =  {new Boolean(false),user.getUserid(),user.getUsername(),typestr,stastr,"","",""};    //������
			this.datas.add(data);
			
		}
		
		this.table.updateUI();
		tiplabel.setText("�ܹ�������" + count + "����¼");
		tiplabel.setVisible(true);
		
	}

}
