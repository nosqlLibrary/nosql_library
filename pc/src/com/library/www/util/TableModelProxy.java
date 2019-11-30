package com.library.www.util;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class TableModelProxy extends AbstractTableModel {

	private static final long serialVersionUID = -3295581072864170310L;
    private String[] columnNames;
    private Vector<Object> datas = new Vector<>(); 
    
    public TableModelProxy(String[] columnNames, Vector<Object> datas ) {
        
        this.columnNames = columnNames;
        this.datas = datas;
    }
  
    public void removeRow(int row) {
    	datas.removeElementAt(row);
        fireTableRowsDeleted(row, row);
    }
    
    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }


    public void setData(Vector<Object> datas ) {
        this.datas = datas;
    }

    @Override
    public String getColumnName(int column) {
        
        return columnNames[column];
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
       // System.out.println(columnIndex);
        return getValueAt(0, columnIndex).getClass();
    }
    
   

    @Override
    public boolean isCellEditable(int row, int column) {
        
    	// 带有按钮列的功能这里必须要返回true不然按钮点击时不会触发编辑效果，也就不会触发事件。
    	if(columnNames.length == 9 ||columnNames.length == 8) {
    		if (column == 8|| column ==7 || column ==6 ){  
                return true;  
            }else if(column == 0) {
            	return getValueAt(row, column).getClass() == Boolean.class;
            }else if(columnNames[5].equals("详细信息")) {
            	return true; 
            }else if(columnNames[6].equals("删除用户")) {
            	return true; 
            }else{  
                return false;  
            }  
    	}else if(columnNames.length == 7 && columnNames[6].equals("详细信息")){
    		return true;
    	}else {
    		return false;
    	}
        
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
        Object[] object = (Object[]) datas.get(rowIndex);
        object[columnIndex] = aValue;
        
//        fireTableCellUpdated(rowIndex, columnIndex);
    }


    @Override
    public int getRowCount() {
       
         return datas.size();
        
    }

    @Override
    public int getColumnCount() {
        
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Object[] object = (Object[]) datas.get(rowIndex);
        return object[columnIndex];
    }

    public void selectAllOrNull(boolean value){
        // Select All. The first column
        for(int index = 0; index < getRowCount(); index ++){
            this.setValueAt(value, index, 0);
        }
    }

    public void refresh(Vector<Object> datas){
        this.datas = datas;
        this.fireTableDataChanged();
    }

}
