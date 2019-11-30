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
        // DefautlCellEditor�д˹���������Ҫ����һ�������������ʹ�õ���ֱ��newһ�����ɡ�   
        super(new JTextField()); 
  
        // ���õ�����μ���༭��   
        this.setClickCountToStart(1);  
  
        this.initButton();  
        this.table = table;
        this.btnName = btnName;
        
    }  
  
   

	private void initButton()  
    {  

        this.button = new JButton(this.btnName);
        this.button.setBorderPainted(false);
      
        // Ϊ��ť����¼�     ����ֻ�����ActionListner�¼���Mouse�¼���Ч
        this.button.addActionListener(new ActionListener()
        {
            @SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent e)
            {
                // ����ȡ���༭���¼����������tableModel��setValue������
                MyButtonEditor.this.fireEditingCanceled();
                int selectedColumn = table.getSelectedColumn();
                int columnCount = table.getColumnCount();
                
                
                if(columnCount == 9) {
                	String eightcolumnName = table.getModel().getColumnName(8);
                    String sevencolumnName = table.getModel().getColumnName(7);
                    //��ȡͼ����
        			String bid = (String)table.getValueAt(table.getSelectedRow(), 1);
                    
                    if( selectedColumn == 6 && table.getModel().getColumnName(6).equals("��ϸ��Ϣ")) {
                		
        				ArrayList result = new ArrayList<>();
        				try {
        					 
    						result = bookService.getBookDetails(bid);
        				} catch (Exception e1) {
        					e1.printStackTrace();
        				}
        				if(result.size()!=0)
        					new BookDetailFrame(result);
        				else {
        					JOptionPane.showMessageDialog(null,"ͼ�鲻�����ݿ��У�","��ʾ",JOptionPane.ERROR_MESSAGE);
    	        			return;
        				}
        					
                	}else if(selectedColumn == 7 && sevencolumnName.equals("ɾ��")) {
                		int n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ����?", "��ʾ",JOptionPane.YES_NO_OPTION);
                		if(n!=0) {
                			return;
                		}
                		//���Ƿ���Ҫ����ɾ��
                		int deleteNum = 0 ;//��¼һ�£����NumΪ0����ֻɾ����ǰѡ�е�ͼ��
                		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
    						Boolean valueAt = (Boolean)table.getValueAt(i,0);
    						//��ȡͼ����
    	        			String nowbid = (String)table.getValueAt(i, 1);
    	        			
    						if(valueAt) {
    							
    							deleteNum ++;
    							int success = deleteBook(nowbid);
    							
    							if(success == 1) {
    								System.out.println("ɾ����"+table.getValueAt(i, 2));
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
		        			
							
    					
                	}else if(selectedColumn == 8 && eightcolumnName.equals("�޸�")) {
                		
                		
                    
                    	ArrayList bookDetails = new ArrayList<>();
    					try {
    						
    						bookDetails = bookService.getBookDetails(bid);
    						Book book = (Book) bookDetails.get(1);
    	            		new EditBookFrame(book);
    					} catch (Exception e1) {
    						e1.toString();
    					}
                        
                     
                		
                	}else if(selectedColumn == 7 && sevencolumnName.equals("���")) {
                		int n = JOptionPane.showConfirmDialog(null, "ȷ����ͼ���������?", "��ʾ",JOptionPane.YES_NO_OPTION);
                		if(n!=0) {
                			return;
                		}
                		int damageNum = 0 ;//��¼һ�£����NumΪ0����ֻ�޸ĵ�ǰѡ�е�ͼ��
                		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
    						Boolean valueAt = (Boolean)table.getValueAt(i,0);
    						//��ȡͼ����
    	        			String nowbid = (String)table.getValueAt(i, 1);
    	        			
    						if(valueAt) {
//    							System.out.println("�����"+table.getValueAt(i, 2));
    							damageNum ++;
    							damageBook(nowbid);
    							table.setValueAt("���",i ,3);
    						}
    						
    					}
                		
                		if(damageNum == 0) {
                			
                			damageBook(bid);
                			table.setValueAt("���",table.getSelectedRow() ,3);
                		}
                			
                		
                	}else if(selectedColumn == 8 && eightcolumnName.equals("��ʧ")) {
                		int n = JOptionPane.showConfirmDialog(null, "ȷ����ͼ���Ѷ�ʧ��?", "��ʾ",JOptionPane.YES_NO_OPTION);
                		if(n!=0) {
                			return;
                		}
                		int lostNum = 0 ;//��¼һ�£����NumΪ0����ֻ�޸ĵ�ǰѡ�е�ͼ��
                		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
    						Boolean valueAt = (Boolean)table.getValueAt(i,0);
    						//��ȡͼ����
    	        			String nowbid = (String)table.getValueAt(i, 1);
    	        			
    						if(valueAt) {
//    							System.out.println("��ʧ��"+table.getValueAt(i, 2));
    							lostNum ++;
    							lostBook(nowbid);
    							table.setValueAt("��ʧ",i , 3);
    						}
    						
    					}
                		
                		if(lostNum == 0) {
                			lostBook(bid);
                			table.setValueAt("���",table.getSelectedRow() , 3);
                		}
                			
                		
                	}
                }
                
                
               
                if(columnCount == 8) {
                	
                	
                	if( selectedColumn == 6 && table.getModel().getColumnName(6).equals("��ϸ��Ϣ")) {
                		//��ȡͼ����
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
                	if( selectedColumn == 7 && table.getModel().getColumnName(7).equals("�޸���Ϣ")) {
                		
            			User user = new User();
            			try {
    						user = userService.getUserMsgByID(userid);
    						new ManagerEditUserMsgFrame(user);
    					} catch (Exception e1) {
    						
    						e1.printStackTrace();
    					}
            			
            			
                	}else if( selectedColumn == 5 && table.getModel().getColumnName(5).equals("��ϸ��Ϣ")) {
                		
                		
            			User user = new User();
            			try {
    						user = userService.getUserMsgByID(userid);
    						
    					} catch (Exception e1) {
    						
    						e1.printStackTrace();
    					}
            			if(user.getUserid()!=null)
            				new UserDetailFrame(user);
            			
            			
                	}else if( selectedColumn == 6 && table.getModel().getColumnName(6).equals("ɾ���û�")) {
                		int n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ����?", "��ʾ",JOptionPane.YES_NO_OPTION);
                		if(n!=0) {
                			return;
                		}
                		int deleteNum = 0 ;//��¼һ�£����NumΪ0����ֻ�޸ĵ�ǰѡ�е�ͼ��
                		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
    						Boolean valueAt = (Boolean)table.getValueAt(i,0);
    						//��ȡ�û����
    	        			String nowuserid = (String)table.getValueAt(i, 1);
    	        			
    						if(valueAt) {
//    							System.out.println("ɾ���û�"+table.getValueAt(i, 2));
    							deleteNum ++;
    							deleteUser(nowuserid);
    						}
    						
    					}
                		
                		if(deleteNum == 0) 
	                		deleteUser(userid);
                	}
                }
                
                if(columnCount == 7) {
                	if( selectedColumn == 6 && table.getModel().getColumnName(6).equals("��ϸ��Ϣ")) {
                		//��ȡͼ����
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
        					JOptionPane.showMessageDialog(null,"ͼ�鲻�����ݿ��У�","��ʾ",JOptionPane.ERROR_MESSAGE);
    	        			return;
        				}
            		
                	}
                }
                
                
            }

			
			private void deleteUser(String userid) {
				//���ж��û��Ƿ���ͼ��û�й黹
				User u;
				
				boolean re = false;
				try {
					u = userService.getUserMsgByID(userid);
					re = borrowService.checkIsAllBack(u);
				} catch (Exception e2) {
					
					e2.printStackTrace();
				}
				
				
				if(!re) {
					//��ʾҪ�ȹ黹����ͼ��
					JOptionPane.showMessageDialog(null, "�����ø��û��黹��δ�黹��ͼ�飡", "��ʾ", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				boolean result = false;
				try {
					result = userService.deleteUserById(userid);
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
				
				if(result) {
					JOptionPane.showMessageDialog(null,"���Ϊ"+userid+"���û���¼��ɾ����","��ʾ",JOptionPane.PLAIN_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null,"���Ϊ"+userid+"���û���¼ɾ��ʧ�ܣ�","��ʾ",JOptionPane.PLAIN_MESSAGE);
				}
			}

			private void lostBook(String bid) {
				//��ʧͼ��Ǽ�
				boolean result = false;
				try {
					result = bookService.bookLostRegister(bid);
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
				
				if(result) {
					JOptionPane.showMessageDialog(null,"���Ϊ"+bid+"��ͼ���ѵǼ�Ϊ��ʧ״̬��","��ʾ",JOptionPane.PLAIN_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null,"���Ϊ"+bid+"��ͼ��Ǽ�ʧ�ܣ�","��ʾ",JOptionPane.ERROR_MESSAGE);
				}
			}

			private void damageBook(String bid) {
				//���ͼ��Ǽ�
				boolean result = false;
				try {
					result = bookService.bookDamageRegister(bid);
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
				
				if(result) {
					JOptionPane.showMessageDialog(null,"���Ϊ"+bid+"��ͼ���ѵǼ�Ϊ���״̬��","��ʾ",JOptionPane.PLAIN_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null,"���Ϊ"+bid+"��ͼ��Ǽ�ʧ�ܣ�","��ʾ",JOptionPane.ERROR_MESSAGE);
				}
				
				
			}

			
			
            //����ͼ���Ž���ɾ������ɾ��
			private int deleteBook(String bid) {
				//ɾ��
				//�жϸ����Ƿ��ڿ�   ����ڿ� ���Ȱ����н�����Ϣɾ������ɾ�����飻������ڣ�����ʾ���ڿ�
				boolean result = false;
				try {
					result  = bookService.checkBookStatus(bid, 'A');
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				if(!result) {
					JOptionPane.showMessageDialog(null,"���Ϊ"+bid+"��ͼ�鲻�ڿ⣡","��ʾ",JOptionPane.PLAIN_MESSAGE);
					return 0;
				}
				
				try {
					result = borrowService.isExistBookBorrowMsg(bid);
					if(result)	//������ڽ��ļ�¼�ڽ���ɾ����¼����
						result  = borrowService.deleteBorrowsByBid(bid);
					else 
						result = true;//��������ڽ��ļ�¼�����������ʧ��Ӧ����Ϊtrue
					
				} catch (Exception e2) {
					
					e2.printStackTrace();
				}
				
				if(!result) {
					JOptionPane.showMessageDialog(null,"���Ϊ"+bid+"��ͼ��ɾ��ʧ�ܣ�","��ʾ",JOptionPane.PLAIN_MESSAGE);
					return 0;
				}
				
				
				try {
					result = bookService.deleteBook(bid);
						
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				if(result) {
					JOptionPane.showMessageDialog(null,"���Ϊ"+bid+"��ͼ����ɾ����","��ʾ",JOptionPane.PLAIN_MESSAGE);
					return 1;
				}else {
					JOptionPane.showMessageDialog(null,"���Ϊ"+bid+"��ͼ��ɾ��ʧ�ܣ�","��ʾ",JOptionPane.ERROR_MESSAGE);
					return 0;
				}
				
				
			}
        });

  
    }  
  
   
  
  
    /** 
     	* ������д����ı༭������ֱ�ӷ���һ��Button����,�����������Ԫ��
     */  
    @Override  
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)  
    {  
        
        return this.button;  
    }  
  
    /** 
     	* ��д�༭��Ԫ��ʱ��ȡ��ֵ���������д��������ܻ�Ϊ��ť���ô����ֵ�� 
     */  
    @Override  
    public Object getCellEditorValue()  
    {  
        return this.button.getText();  
    }  
  
} 



