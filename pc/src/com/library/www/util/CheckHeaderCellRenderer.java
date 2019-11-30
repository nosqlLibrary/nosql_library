package com.library.www.util;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.library.www.po.User;
import com.library.www.service.BookService;
import com.library.www.service.BorrowService;
import com.library.www.service.UserService;

public class CheckHeaderCellRenderer implements TableCellRenderer {

	private TableModelProxy tableModel;
    private JTableHeader tableHeader;
    private JCheckBox selectBox;
    private JButton selectButton,lostButton;
    private BookService bookService = new BookService();
    private UserService userService = new UserService();
    private BorrowService borrowService = new BorrowService();
   
    
    
    private String sevencolumnName,eightcolumnName;
    private Font font=new Font("仿宋",Font.BOLD,16); 
    private int columnCount ;

    public CheckHeaderCellRenderer(final JTable table) {
        this.tableModel =  (TableModelProxy) table.getModel();
        this.tableHeader = table.getTableHeader();
        //获取列的数量
        this.columnCount = table.getColumnCount();
        
        init(table);
      
    }

	private void init(final JTable table) {
		//如果是图书表
        if(this.columnCount == 9) {
        	
        	selectBox = new JCheckBox(tableModel.getColumnName(0));
            selectBox.setSelected(false);
            
        	//分别获取后边两列的表头内容，用于后边判断是否要把列头变成按钮，实现批量操作
            sevencolumnName = tableModel.getColumnName(7);
            eightcolumnName = tableModel.getColumnName(8);
             
            selectButton = new JButton(sevencolumnName);
            if(sevencolumnName.equals("删除")) {
            	selectButton.setIcon(new ImageIcon(this.getClass().getResource("/Images/delete.png")) );             
            }else if(sevencolumnName.equals("损毁")) {
            	selectButton.setIcon(new ImageIcon(this.getClass().getResource("/Images/filedamage.png")) ); 
            }
            selectButton.setContentAreaFilled(false); 
            
            lostButton = new JButton(eightcolumnName);
            if(eightcolumnName.equals("丢失")) {
            	lostButton.setIcon(new ImageIcon(this.getClass().getResource("/Images/filelost.png")) );
            }
            lostButton.setContentAreaFilled(false);
            
            
            tableHeader.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() > 0) {
                    	
                        // 获得选中列
                        int selectColumn = tableHeader.columnAtPoint(e.getPoint());
                        if (selectColumn == 0 ) {
                            boolean value = !selectBox.isSelected();
                            selectBox.setSelected(value);
                            tableModel.selectAllOrNull(value);
                            tableHeader.repaint();
                            
//                            System.out.println("全选");
                            
                        }
                        if(selectColumn == 7 ) {
                        	if(sevencolumnName.equals("删除")) {
                        		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
            						//获取图书编号
            	        			String nowbid = (String)table.getValueAt(i, 1);
            	        			deleteBook(nowbid);
            	        			TableModelProxy  tabelmodel = (TableModelProxy) table.getModel();  
        							tabelmodel.removeRow(i);
                            	}
                        	}else if(sevencolumnName.equals("损毁")) {
                        		//获取图书编号
                        		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
            						//获取图书编号
            	        			String nowbid = (String)table.getValueAt(i, 1);
            	        			damageBook(nowbid);
            	        			table.setValueAt("损毁",i ,3);
                            	}
                        	}
                        }
                        
                        if(selectColumn == 8) {
                        	if(eightcolumnName.equals("丢失")) {
                        		//获取图书编号
                        		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
            						//获取图书编号
            	        			String nowbid = (String)table.getValueAt(i, 1);
            	        			lostBook(nowbid);
            	        			table.setValueAt("丢失",i , 3);
                            	}
                        	}
                        }
                    }
                }
                
                private void lostBook(String bid) {
    				//丢失图书登记
    				boolean result = false;
    				try {
    					result = bookService.bookLostRegister(bid);
    				} catch (Exception e1) {
    					
    					e1.printStackTrace();
    				}
    				
    				if(result) {
    					JOptionPane.showMessageDialog(null,"编号为"+bid+"的图书已登记为丢失状态！","提示",JOptionPane.PLAIN_MESSAGE);
    				}else {
    					JOptionPane.showMessageDialog(null,"编号为"+bid+"的图书登记失败！","提示",JOptionPane.ERROR_MESSAGE);
    				}
    			}

    			private void damageBook(String bid) {
    				//损毁图书登记
    				boolean result = false;
    				try {
    					result = bookService.bookDamageRegister(bid);
    				} catch (Exception e1) {
    					
    					e1.printStackTrace();
    				}
    				
    				if(result) {
    					JOptionPane.showMessageDialog(null,"编号为"+bid+"的图书已登记为损毁状态！","提示",JOptionPane.PLAIN_MESSAGE);
    				}else {
    					JOptionPane.showMessageDialog(null,"编号为"+bid+"的图书登记失败！","提示",JOptionPane.ERROR_MESSAGE);
    				}
    			}
                
                
                //传入图书编号进行删除操作删除
    			private void deleteBook(String bid) {
    				//删除
    				//判断该书是否在库   如果在库 ，先把所有借阅信息删除，再删除该书；如果不在，则提示不在库
    				boolean result = false;
    				try {
    					result  = bookService.checkBookStatus(bid, 'A');
    				} catch (Exception e2) {
    					e2.printStackTrace();
    				}
    				
    				if(!result) {
    					JOptionPane.showMessageDialog(null,"编号为"+bid+"的图书不在库！","提示",JOptionPane.PLAIN_MESSAGE);
    					return;
    				}
    				
    				try {
    					result = borrowService.isExistBookBorrowMsg(bid);
    					if(result)	//如果存在借阅记录在进行删除记录操作
    						result  = borrowService.deleteBorrowsByBid(bid);
    					else 
    						result = true;//如果不存在借阅记录，不代表操作失误，应当置为true
    					
    				} catch (Exception e2) {
    					
    					e2.printStackTrace();
    				}
    				
    				if(!result) {
    					JOptionPane.showMessageDialog(null,"编号为"+bid+"的图书删除失败！","提示",JOptionPane.PLAIN_MESSAGE);
    					return;
    				}
    				
    				
    				try {
    					result = bookService.deleteBook(bid);
    						
    				} catch (Exception e1) {
    					e1.printStackTrace();
    				}
    				
    				if(result) {
    					JOptionPane.showMessageDialog(null,"编号为"+bid+"的图书已删除！","提示",JOptionPane.PLAIN_MESSAGE);
    				}else {
    					JOptionPane.showMessageDialog(null,"编号为"+bid+"的图书删除失败！","提示",JOptionPane.ERROR_MESSAGE);
    				}
    				
    				table.updateUI();
    			}
            });
        }else if(columnCount == 8) {
        	//如果是管理员查询所有用户信息的表
        	selectBox = new JCheckBox(tableModel.getColumnName(0));
            selectBox.setSelected(false);
        	
        	sevencolumnName = tableModel.getColumnName(6);
        	selectButton = new JButton(sevencolumnName);
        	
        	
            if(sevencolumnName.equals("删除用户")) {
            	selectButton.setIcon(new ImageIcon(this.getClass().getResource("/Images/delete.png")) );             
            }
            selectButton.setContentAreaFilled(false); 
            
            
            tableHeader.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() > 0) {
                    	
                        // 获得选中列
                        int selectColumn = tableHeader.columnAtPoint(e.getPoint());
                        
                        if (selectColumn == 0 ) {
                            boolean value = !selectBox.isSelected();
                            selectBox.setSelected(value);
                            tableModel.selectAllOrNull(value);
                            tableHeader.repaint();
                            
                        }
                        
                        if(selectColumn == 6 ) {

                        	for(int i = 0 ; i < table.getRowCount() ;i++ ) {
        						
        						//获取用户编号
        	        			String nowuserid = (String)table.getValueAt(i, 1);
        	        			deleteUser(nowuserid);
        						
        					}
                        	
                        }
                        
                       
                    }
                }
                
                    
                  
                 
				
				private void deleteUser(String userid) {
					//先判断用户是否还有图书没有归还
					User u;
					
					boolean re = false;
					try {
						u = userService.getUserMsgByID(userid);
						re = borrowService.checkIsAllBack(u);
					} catch (Exception e2) {
						
						e2.printStackTrace();
					}
					
					
					if(!re) {
						//提示要先归还所有图书
						JOptionPane.showMessageDialog(null, "请先让该用户归还尚未归还的图书！", "提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					boolean result = false;
					try {
						result = userService.deleteUserById(userid);
					} catch (Exception e1) {
						
						e1.printStackTrace();
					}
					
					if(result) {
						JOptionPane.showMessageDialog(null,"编号为"+userid+"的用户记录已删除！","提示",JOptionPane.PLAIN_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null,"编号为"+userid+"的用户记录删除失败！","提示",JOptionPane.PLAIN_MESSAGE);
					}
					
				}

    			
            });
        }
	}

    

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        
        String valueStr = (String) value;
        JLabel label = new JLabel(valueStr);
        JComponent component = null;
        
        label.setHorizontalAlignment(SwingConstants.CENTER); // 表头标签剧中
        if(this.columnCount == 9) {
        	
            selectBox.setHorizontalAlignment(SwingConstants.CENTER);// 表头标签剧中
            selectBox.setBorderPainted(true);
            
    		selectButton.setHorizontalAlignment(SwingConstants.CENTER);// 表头标签剧中
    		selectButton.setBorderPainted(true);
    		selectButton.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
    		selectButton.setFont(font);
    		
    		if(column == 0)
            	component = selectBox;
            else if(column==7 )
            	component = selectButton;
            else if(column==8 && eightcolumnName.equals("丢失"))
            	component = lostButton;
            else 
            	component = label;
        }else if(this.columnCount == 8){
        	selectBox.setHorizontalAlignment(SwingConstants.CENTER);// 表头标签剧中
            selectBox.setBorderPainted(true);
            
        	selectButton.setHorizontalAlignment(SwingConstants.CENTER);// 表头标签剧中
    		selectButton.setBorderPainted(true);
    		selectButton.setBorder(BorderFactory.createRaisedBevelBorder());//设置突出button组件  
    		selectButton.setFont(font);
    		if(column==5 && column ==6 && column ==7) {
    			component = selectButton;
    		}else if(column==0) {
    			component = selectBox;
    		}else {
    			component = label;
    		}
        }else {
        	component = label;
        }
        
        

        component.setForeground(tableHeader.getForeground());
        component.setBackground(tableHeader.getBackground());
        component.setFont(tableHeader.getFont());
        component.setBorder(UIManager.getBorder("TableHeader.cellBorder"));

        return component;
    }




}
