package lus.bookrequest;

import javax.swing.table.AbstractTableModel;

public class RequestTableModel extends AbstractTableModel{
	String[] columnName;
	Object data[][];
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnName.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return data[row][col];
	}
	
	@Override
	public String getColumnName(int col) {
		// TODO Auto-generated method stub
		return columnName[col];
	}
}
