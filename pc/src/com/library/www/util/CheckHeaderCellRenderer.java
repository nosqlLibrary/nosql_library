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
    private Font font=new Font("����",Font.BOLD,16); 
    private int columnCount ;

    public CheckHeaderCellRenderer(final JTable table) {
        this.tableModel =  (TableModelProxy) table.getModel();
        this.tableHeader = table.getTableHeader();
        //��ȡ�е�����
        this.columnCount = table.getColumnCount();
        
        init(table);
      
    }

	private void init(final JTable table) {
		//�����ͼ���
        if(this.columnCount == 9) {
        	
        	selectBox = new JCheckBox(tableModel.getColumnName(0));
            selectBox.setSelected(false);
            
        	//�ֱ��ȡ������еı�ͷ���ݣ����ں���ж��Ƿ�Ҫ����ͷ��ɰ�ť��ʵ����������
            sevencolumnName = tableModel.getColumnName(7);
            eightcolumnName = tableModel.getColumnName(8);
             
            selectButton = new JButton(sevencolumnName);
            if(sevencolumnName.equals("ɾ��")) {
            	selectButton.setIcon(new ImageIcon(this.getClass().getResource("/Images/delete.png")) );             
            }else if(sevencolumnName.equals("���")) {
            	selectButton.setIcon(new ImageIcon(this.getClass().getResource("/Images/filedamage.png")) ); 
            }
            selectButton.setContentAreaFilled(false); 
            
            lostButton = new JButton(eightcolumnName);
            if(eightcolumnName.equals("��ʧ")) {
            	lostButton.setIcon(new ImageIcon(this.getClass().getResource("/Images/filelost.png")) );
            }
            lostButton.setContentAreaFilled(false);
            
            
            tableHeader.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() > 0) {
                    	
                        // ���ѡ����
                        int selectColumn = tableHeader.columnAtPoint(e.getPoint());
                        if (selectColumn == 0 ) {
                            boolean value = !selectBox.isSelected();
                            selectBox.setSelected(value);
                            tableModel.selectAllOrNull(value);
                            tableHeader.repaint();
                            
//                            System.out.println("ȫѡ");
                            
                        }
                        if(selectColumn == 7 ) {
                        	if(sevencolumnName.equals("ɾ��")) {
                        		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
            						//��ȡͼ����
            	        			String nowbid = (String)table.getValueAt(i, 1);
            	        			deleteBook(nowbid);
            	        			TableModelProxy  tabelmodel = (TableModelProxy) table.getModel();  
        							tabelmodel.removeRow(i);
                            	}
                        	}else if(sevencolumnName.equals("���")) {
                        		//��ȡͼ����
                        		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
            						//��ȡͼ����
            	        			String nowbid = (String)table.getValueAt(i, 1);
            	        			damageBook(nowbid);
            	        			table.setValueAt("���",i ,3);
                            	}
                        	}
                        }
                        
                        if(selectColumn == 8) {
                        	if(eightcolumnName.equals("��ʧ")) {
                        		//��ȡͼ����
                        		for(int i = 0 ; i < table.getRowCount() ;i++ ) {
            						//��ȡͼ����
            	        			String nowbid = (String)table.getValueAt(i, 1);
            	        			lostBook(nowbid);
            	        			table.setValueAt("��ʧ",i , 3);
                            	}
                        	}
                        }
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
    			private void deleteBook(String bid) {
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
    					return;
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
    					return;
    				}
    				
    				
    				try {
    					result = bookService.deleteBook(bid);
    						
    				} catch (Exception e1) {
    					e1.printStackTrace();
    				}
    				
    				if(result) {
    					JOptionPane.showMessageDialog(null,"���Ϊ"+bid+"��ͼ����ɾ����","��ʾ",JOptionPane.PLAIN_MESSAGE);
    				}else {
    					JOptionPane.showMessageDialog(null,"���Ϊ"+bid+"��ͼ��ɾ��ʧ�ܣ�","��ʾ",JOptionPane.ERROR_MESSAGE);
    				}
    				
    				table.updateUI();
    			}
            });
        }else if(columnCount == 8) {
        	//����ǹ���Ա��ѯ�����û���Ϣ�ı�
        	selectBox = new JCheckBox(tableModel.getColumnName(0));
            selectBox.setSelected(false);
        	
        	sevencolumnName = tableModel.getColumnName(6);
        	selectButton = new JButton(sevencolumnName);
        	
        	
            if(sevencolumnName.equals("ɾ���û�")) {
            	selectButton.setIcon(new ImageIcon(this.getClass().getResource("/Images/delete.png")) );             
            }
            selectButton.setContentAreaFilled(false); 
            
            
            tableHeader.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() > 0) {
                    	
                        // ���ѡ����
                        int selectColumn = tableHeader.columnAtPoint(e.getPoint());
                        
                        if (selectColumn == 0 ) {
                            boolean value = !selectBox.isSelected();
                            selectBox.setSelected(value);
                            tableModel.selectAllOrNull(value);
                            tableHeader.repaint();
                            
                        }
                        
                        if(selectColumn == 6 ) {

                        	for(int i = 0 ; i < table.getRowCount() ;i++ ) {
        						
        						//��ȡ�û����
        	        			String nowuserid = (String)table.getValueAt(i, 1);
        	        			deleteUser(nowuserid);
        						
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

    			
            });
        }
	}

    

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        
        String valueStr = (String) value;
        JLabel label = new JLabel(valueStr);
        JComponent component = null;
        
        label.setHorizontalAlignment(SwingConstants.CENTER); // ��ͷ��ǩ����
        if(this.columnCount == 9) {
        	
            selectBox.setHorizontalAlignment(SwingConstants.CENTER);// ��ͷ��ǩ����
            selectBox.setBorderPainted(true);
            
    		selectButton.setHorizontalAlignment(SwingConstants.CENTER);// ��ͷ��ǩ����
    		selectButton.setBorderPainted(true);
    		selectButton.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
    		selectButton.setFont(font);
    		
    		if(column == 0)
            	component = selectBox;
            else if(column==7 )
            	component = selectButton;
            else if(column==8 && eightcolumnName.equals("��ʧ"))
            	component = lostButton;
            else 
            	component = label;
        }else if(this.columnCount == 8){
        	selectBox.setHorizontalAlignment(SwingConstants.CENTER);// ��ͷ��ǩ����
            selectBox.setBorderPainted(true);
            
        	selectButton.setHorizontalAlignment(SwingConstants.CENTER);// ��ͷ��ǩ����
    		selectButton.setBorderPainted(true);
    		selectButton.setBorder(BorderFactory.createRaisedBevelBorder());//����ͻ��button���  
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
