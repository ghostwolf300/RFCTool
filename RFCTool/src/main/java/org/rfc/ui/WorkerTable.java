package org.rfc.ui;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class WorkerTable extends JTable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String HEADERS[]= {"Id","Function","Testrun","Workload","Processed","Progress","Success","Warning","Error","Status","Log","Control"};

	public WorkerTable() {
		super(new WorkerTableModel());
		configureHeader();
	}
	
	private void configureHeader() {
		
		JTableHeader header=this.getTableHeader();
		TableColumnModel columns=header.getColumnModel();
		TableColumn col=null;
		for(int i=0;i<HEADERS.length;i++) {
			col=columns.getColumn(i);
			col.setHeaderValue(HEADERS[i]);
			if(i==2) {
				col.setCellRenderer(this.getDefaultRenderer(Boolean.class));
			}
			else if(i==5) {
				col.setCellRenderer(new ProgressCellRenderer());
			}
			else if(i==11) {
				col.setCellRenderer(new ControlCellRenderer());
				col.setCellEditor(new ControlCellEditor());
			}
			
		}	
	}
	
	public boolean isCellEditable(int row,int col) {
		if(col==WorkerTableModel.COL_CONTROL) {
			return true;
		}
		else {
			return false;
		}
	}

	
	
}
