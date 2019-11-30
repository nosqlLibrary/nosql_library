package com.library.www.util;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;

public class MyJCheckBoxEditor extends AbstractCellEditor implements TableCellEditor{

	/**
	 * �Զ������и�ѡ���Editor
	 */
	private static final long serialVersionUID = 1L;
 
	protected JCheckBox checkBox;

	public MyJCheckBoxEditor() {
		
		checkBox = new JCheckBox();
	    checkBox.setHorizontalAlignment(SwingConstants.CENTER);
	    
        
	}
	
	@Override
	public Object getCellEditorValue() {
	    return Boolean.valueOf(this.checkBox.isSelected());
	}

	 
 	@Override  
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)  
    {  
        
 		this.checkBox.setSelected(((Boolean) value).booleanValue());
// 		if((Boolean) value) {
// 			System.out.println("���У�����");
// 		}
// 		if(table.getSelectedColumn() == 0 && table.getSelectedRow() ==row ) {
// 			System.out.println("���У�����");
// 		}
 	    return this.checkBox;
         
    }  
	  


}
