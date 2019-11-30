package com.library.www.util;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyButtonRender implements TableCellRenderer{
	
	private JPanel panel;
    private JButton button;
  
    public MyButtonRender(String btnName)
    {
    	
    	this.initButton(btnName);
        // ��Ӱ�ť
        this.panel = new JPanel();
        // panelʹ�þ��Զ�λ������button�Ͳ������������Ԫ��
        this.panel.setLayout(null);
        this.panel.add(this.button);
    
    }

    private void initButton(String btnName){
    	
        this.button = new JButton(btnName);
        if(btnName.equals("��ϸ��Ϣ")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/moredetail.png")) );
        	this.button.setText("");	
        }
          
        if(btnName.equals("ɾ��")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/delete.png")) );
        	this.button.setText("");
        }
        
        if(btnName.equals("�޸�")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/edit.png")) );
			this.button.setText("");
		}
        
        
        
        if(btnName.equals("���")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/filedamage.png")) );
			this.button.setText("");
		}
        
        if(btnName.equals("��ʧ")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/filelost.png")) );
        	this.button.setText("");
        }
        
        if(btnName.equals("ɾ���û�")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/delete.png")) );
        	this.button.setText("");
        }
        
        if(btnName.equals("�޸���Ϣ")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/edit.png")) );
			this.button.setText("");
		}

        // ���ð�ť�Ĵ�С��λ�á�
        this.button.setBounds(10, 0, 80, 30);
        this.button.setContentAreaFilled(false);
        this.button.setBorderPainted(false);
    }

   
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
            int column){
        // ֻΪ��ť��ֵ���ɡ�Ҳ������������������汳���ȡ�    
//        if(hasFocus) {
//        	
//        	//��ȡͼ����
//			String bid = (String) this.table.getValueAt(row, 1);
//			
//			if(btnName.equals("��ϸ��Ϣ")) {
//				
////				System.out.println("-------");
//				ArrayList result = new ArrayList<>();
//				try {
//					 result = bookService.getBookDetails(bid);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//				//bug:�����޸�ԭ��Ԫ���ѡ��״̬���ر�֮���ֻ���������
//				new BookDetailFrame(result);
//				
//			}
//			
//        }
    	
        return this.panel;
    }

}
