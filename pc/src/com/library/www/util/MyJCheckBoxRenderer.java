package com.library.www.util;

import java.awt.Component;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class MyJCheckBoxRenderer extends JCheckBox implements TableCellRenderer {
	
	private static final long serialVersionUID = 1L;
	Border border = new EmptyBorder(1, 2, 1, 2);
	

	public MyJCheckBoxRenderer() {
		super();
	    setOpaque(true);
	    setHorizontalAlignment(SwingConstants.CENTER);

	}

	 
	  
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value instanceof Boolean) {
	      setSelected(((Boolean) value).booleanValue());
	 
	      // setEnabled(table.isCellEditable(row, column));
	      setForeground(table.getForeground());
	      setBackground(table.getBackground());
	 
	    }
		
		
	 
	    return this;
	}
	
	public void setSelectRow(Map<String,Integer> select) {
		
	}
	
	public Map<String,Integer> getSelectRow(){
		
		return null;
		
	}

}
