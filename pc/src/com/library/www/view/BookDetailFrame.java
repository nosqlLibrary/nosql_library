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
	 * ��ʾĳ��ͼ��ľ�����Ϣ�Ĵ���
	 */
	private static final long serialVersionUID = 1L;
	
	private Font font=new Font("����",Font.BOLD,18); 
	
	private JTextArea textArea = new JTextArea(5,20);
	
	private final int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;		//���ô�ֱ������
	private final int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;	//����ˮƽ������
	private JScrollPane jsp=new JScrollPane(textArea,v,h);
	private BookService bs = new BookService();
	
	@SuppressWarnings("serial")
	private JPanel panel = new JPanel() {
		
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
	
	@SuppressWarnings("rawtypes")
	public BookDetailFrame(ArrayList result) {
		super("ͼ����ϸ��Ϣ����");
		Book book = (Book) result.get(1);
		UniqueBook ubook = (UniqueBook) result.get(0);
		setSize(600,500);
		setLocationRelativeTo(null);    
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		add(panel);
		panel.setLayout(null);
		
		jsp.setBounds(40, 40,500,380 );
		
		String content = "ͼ���ţ�"+ubook.getBid() + "\r\n";
		content += "ISBN:" + book.getIsbn() + "\r\n";
		content += "ͼ�����ƣ�"+book.getBname()+ "\r\n";
		BookType type  = new BookType();
		try {
			type = bs.getBookTypeNameById(book.getBtype());
		} catch (Exception e2) {
			
			e2.printStackTrace();
		}
		
		content += "ͼ�����["+book.getBtype()+ "]"+ type.getTypename() +"\r\n";
		content += "���ߣ�"+book.getBauthor()+ "\r\n";
		content +="�����磺"+book.getBproduction()+ "\r\n";
		content += "�����꣺"+book.getPublishDate() + "\r\n";
		content += "���ۣ�"+book.getPrice() + "\r\n";
		content += "ҳ�룺"+book.getPageNum() + "\r\n";
		content += "�鼮������"+book.getNum() + "\r\n";
		
		
		char status = ubook.getStatus();
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
		content += "ͼ��״̬��["+ubook.getStatus() +"]"+str+ "\r\n";
		String introduction = "";
		//�����鲻���ھ�ֱ����ʾ��
		introduction = book.getIntroduction() ==null? "��":book.getIntroduction() ;
		content += "���ݼ�飺" + introduction;
		textArea.setText(content);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setFont(font);
		textArea.setCaretPosition(0);
		
		//jsp.setOpaque(false);
		panel.add(jsp);
		
		//����logo
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
