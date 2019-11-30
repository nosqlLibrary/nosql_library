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
        // 添加按钮
        this.panel = new JPanel();
        // panel使用绝对定位，这样button就不会充满整个单元格。
        this.panel.setLayout(null);
        this.panel.add(this.button);
    
    }

    private void initButton(String btnName){
    	
        this.button = new JButton(btnName);
        if(btnName.equals("详细信息")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/moredetail.png")) );
        	this.button.setText("");	
        }
          
        if(btnName.equals("删除")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/delete.png")) );
        	this.button.setText("");
        }
        
        if(btnName.equals("修改")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/edit.png")) );
			this.button.setText("");
		}
        
        
        
        if(btnName.equals("损毁")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/filedamage.png")) );
			this.button.setText("");
		}
        
        if(btnName.equals("丢失")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/filelost.png")) );
        	this.button.setText("");
        }
        
        if(btnName.equals("删除用户")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/delete.png")) );
        	this.button.setText("");
        }
        
        if(btnName.equals("修改信息")) {
        	this.button.setIcon(new ImageIcon(this.getClass().getResource("/Images/edit.png")) );
			this.button.setText("");
		}

        // 设置按钮的大小及位置。
        this.button.setBounds(10, 0, 80, 30);
        this.button.setContentAreaFilled(false);
        this.button.setBorderPainted(false);
    }

   
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
            int column){
        // 只为按钮赋值即可。也可以作其它操作，如绘背景等。    
//        if(hasFocus) {
//        	
//        	//获取图书编号
//			String bid = (String) this.table.getValueAt(row, 1);
//			
//			if(btnName.equals("详细信息")) {
//				
////				System.out.println("-------");
//				ArrayList result = new ArrayList<>();
//				try {
//					 result = bookService.getBookDetails(bid);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//				//bug:不能修改原单元格的选中状态，关闭之后又会重新生成
//				new BookDetailFrame(result);
//				
//			}
//			
//        }
    	
        return this.panel;
    }

}
