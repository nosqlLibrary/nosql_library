package com.library.www.util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.library.www.po.Book;
import com.library.www.po.User;
import com.library.www.service.BookService;
import com.library.www.service.BorrowService;
import com.library.www.service.UserService;
import com.library.www.view.BookDetailFrame;
import com.library.www.view.EditBookFrame;
import com.library.www.view.ManagerEditUserMsgFrame;
import com.library.www.view.UserDetailFrame;

public class MyButtonEditor extends DefaultCellEditor {
	private static final long serialVersionUID = -6546334664166791132L;  
	
    private String btnName;
    private JButton button;  
    private JTable table = new JTable();
    private BookService bookService = new BookService();
    private BorrowService borrowService = new BorrowService();
    private UserService userService = new UserService();
    
    
  
    public MyButtonEditor(JTable table,String btnName)  
    {  
        // DefautlCellEditor有此构造器，需要传入一个，但这个不会使用到，直接new一个即可。   
        super(new JTextField()); 
  
        // 设置点击几次激活编辑。   
        this.setClickCountToStart(1);  
  
        this.initButton();  
        this.table = table;
        this.btnName = btnName;
        
    }  
  
   

	private void initButton()  
    {  

        this.button = new JButton(this.btnName);
        this.button.setBorderPainted(false);
      
        // 为按钮添加事件     这里只能添加ActionListner事件，Mouse事件无效
        this.button.addActionListener(new ActionListener()
        {
            @SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent e)
            {
                // 触发取消编辑的事件，不会调用tableModel的setValue方法。
                MyButtonEditor.this.fireEditingCanceled();
                int selectedColumn = table.getSelectedColumn();
                int columnCount = table.getColumnCount();
                
                
                if(columnCount == 9) {
                	String eightcolumnName = table.getModel().getColumnName(8);
                    String sevencolumnName = table.getModel().getColumnName(7);
                    //获取图书编号
        			String bid = (String)table.getValueAt(table.getSelectedRow(), 1);
                    
                    if( selectedColumn == 6 && table.getModel().getColumnName(6).equals("详细信息")) {
                		
        				ArrayList result = new ArrayList<>();
        				try {
        					 
    						result = bookService.getBookDetails(bid);
        				} catch (Exception e1) {
        					e1.printStackTrace();
        				}
        				if(result.size()!=0)
        					new BookDetailFrame(result);
        				else {
        					JOptionPane.showMessageDialog(null,"图书不在数据库中！","提示",JOptionPane.ERROR_MESSAGE);
    	        			return;
        				}
        					
                	}else if(selectedColumn == 7 && sevencolumnName.equals("删除")) {
                		int n = JOptionPane.showConfirmDialog(null, "确定要删除吗?", "提示",JOptionPane.YES_NO_OPTION);
                		if(n!=0) {
                			return;
                		}
                		//看是否需要批量删除
                		int deleteNum = 0 ;//记录一下，如果Num为0，则只删除当前选中的图书
                		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
    						Boolean valueAt = (Boolean)table.getValueAt(i,0);
    						//获取图书编号
    	        			String nowbid = (String)table.getValueAt(i, 1);
    	        			
    						if(valueAt) {
    							
    							deleteNum ++;
    							int success = deleteBook(nowbid);
    							
    							if(success == 1) {
    								System.out.println("删除了"+table.getValueAt(i, 2));
    								TableModelProxy  tabelmodel = (TableModelProxy) table.getModel();  
        							tabelmodel.removeRow(i);
    							}
    							
    						}
    						
    					}
                		
                		if(deleteNum == 0) {
                			int success = deleteBook(bid);
                			if(success == 1) {
                				TableModelProxy  tabelmodel = (TableModelProxy) table.getModel();  
    							tabelmodel.removeRow(table.getSelectedRow());
                			}
                			
                		}
		        			
							
    					
                	}else if(selectedColumn == 8 && eightcolumnName.equals("修改")) {
                		
                		
                    
                    	ArrayList bookDetails = new ArrayList<>();
    					try {
    						
    						bookDetails = bookService.getBookDetails(bid);
    						Book book = (Book) bookDetails.get(1);
    	            		new EditBookFrame(book);
    					} catch (Exception e1) {
    						e1.toString();
    					}
                        
                     
                		
                	}else if(selectedColumn == 7 && sevencolumnName.equals("损毁")) {
                		int n = JOptionPane.showConfirmDialog(null, "确定该图书已损毁吗?", "提示",JOptionPane.YES_NO_OPTION);
                		if(n!=0) {
                			return;
                		}
                		int damageNum = 0 ;//记录一下，如果Num为0，则只修改当前选中的图书
                		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
    						Boolean valueAt = (Boolean)table.getValueAt(i,0);
    						//获取图书编号
    	        			String nowbid = (String)table.getValueAt(i, 1);
    	        			
    						if(valueAt) {
//    							System.out.println("损毁了"+table.getValueAt(i, 2));
    							damageNum ++;
    							damageBook(nowbid);
    							table.setValueAt("损毁",i ,3);
    						}
    						
    					}
                		
                		if(damageNum == 0) {
                			
                			damageBook(bid);
                			table.setValueAt("损毁",table.getSelectedRow() ,3);
                		}
                			
                		
                	}else if(selectedColumn == 8 && eightcolumnName.equals("丢失")) {
                		int n = JOptionPane.showConfirmDialog(null, "确定该图书已丢失吗?", "提示",JOptionPane.YES_NO_OPTION);
                		if(n!=0) {
                			return;
                		}
                		int lostNum = 0 ;//记录一下，如果Num为0，则只修改当前选中的图书
                		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
    						Boolean valueAt = (Boolean)table.getValueAt(i,0);
    						//获取图书编号
    	        			String nowbid = (String)table.getValueAt(i, 1);
    	        			
    						if(valueAt) {
//    							System.out.println("丢失了"+table.getValueAt(i, 2));
    							lostNum ++;
    							lostBook(nowbid);
    							table.setValueAt("丢失",i , 3);
    						}
    						
    					}
                		
                		if(lostNum == 0) {
                			lostBook(bid);
                			table.setValueAt("损毁",table.getSelectedRow() , 3);
                		}
                			
                		
                	}
                }
                
                
               
                if(columnCount == 8) {
                	
                	
                	if( selectedColumn == 6 && table.getModel().getColumnName(6).equals("详细信息")) {
                		//获取图书编号
            			String bid = (String)table.getValueAt(table.getSelectedRow(), 1);
        				ArrayList result = new ArrayList<>();
        				try {
        					result = bookService.getBookDetails(bid);
        				} catch (Exception e1) {
        					e1.printStackTrace();
        				}
        				
        				new BookDetailFrame(result);
            		
                	}
                	
                	
                	
                	String userid = (String)table.getValueAt(table.getSelectedRow(), 1);
                	if( selectedColumn == 7 && table.getModel().getColumnName(7).equals("修改信息")) {
                		
            			User user = new User();
            			try {
    						user = userService.getUserMsgByID(userid);
    						new ManagerEditUserMsgFrame(user);
    					} catch (Exception e1) {
    						
    						e1.printStackTrace();
    					}
            			
            			
                	}else if( selectedColumn == 5 && table.getModel().getColumnName(5).equals("详细信息")) {
                		
                		
            			User user = new User();
            			try {
    						user = userService.getUserMsgByID(userid);
    						
    					} catch (Exception e1) {
    						
    						e1.printStackTrace();
    					}
            			if(user.getUserid()!=null)
            				new UserDetailFrame(user);
            			
            			
                	}else if( selectedColumn == 6 && table.getModel().getColumnName(6).equals("删除用户")) {
                		int n = JOptionPane.showConfirmDialog(null, "确定要删除吗?", "提示",JOptionPane.YES_NO_OPTION);
                		if(n!=0) {
                			return;
                		}
                		int deleteNum = 0 ;//记录一下，如果Num为0，则只修改当前选中的图书
                		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
    						Boolean valueAt = (Boolean)table.getValueAt(i,0);
    						//获取用户编号
    	        			String nowuserid = (String)table.getValueAt(i, 1);
    	        			
    						if(valueAt) {
//    							System.out.println("删除用户"+table.getValueAt(i, 2));
    							deleteNum ++;
    							deleteUser(nowuserid);
    						}
    						
    					}
                		
                		if(deleteNum == 0) 
	                		deleteUser(userid);
                	}
                }
                
                if(columnCount == 7) {
                	if( selectedColumn == 6 && table.getModel().getColumnName(6).equals("详细信息")) {
                		//获取图书编号
            			String bid = (String)table.getValueAt(table.getSelectedRow(), 0);
        				ArrayList result = new ArrayList<>();
        				try {
        					result = bookService.getBookDetails(bid);
        				} catch (Exception e1) {
        					e1.printStackTrace();
        				}
        				
        				if(result.size()!=0)
        					new BookDetailFrame(result);
        				else {
        					JOptionPane.showMessageDialog(null,"图书不在数据库中！","提示",JOptionPane.ERROR_MESSAGE);
    	        			return;
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
			private int deleteBook(String bid) {
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
					return 0;
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
					return 0;
				}
				
				
				try {
					result = bookService.deleteBook(bid);
						
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				if(result) {
					JOptionPane.showMessageDialog(null,"编号为"+bid+"的图书已删除！","提示",JOptionPane.PLAIN_MESSAGE);
					return 1;
				}else {
					JOptionPane.showMessageDialog(null,"编号为"+bid+"的图书删除失败！","提示",JOptionPane.ERROR_MESSAGE);
					return 0;
				}
				
				
			}
        });

  
    }  
  
   
  
  
    /** 
     	* 这里重写父类的编辑方法，直接返回一个Button对象,填充满整个单元格
     */  
    @Override  
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)  
    {  
        
        return this.button;  
    }  
  
    /** 
     	* 重写编辑单元格时获取的值。如果不重写，这里可能会为按钮设置错误的值。 
     */  
    @Override  
    public Object getCellEditorValue()  
    {  
        return this.button.getText();  
    }  
  
} 



