package org.rfc.ui;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class LogTable extends JTable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String HEADERS[]= {"Worker ID","Material3","Number","Type","Message","Row","V1","V2","V3","V4"};
	
	public LogTable() {
		super(new LogTableModel());
		configureHeader();
	}
	
	private void configureHeader() {
		
		JTableHeader header=this.getTableHeader();
		TableColumnModel columns=header.getColumnModel();
		TableColumn col=null;
		for(int i=0;i<HEADERS.length;i++) {
			col=columns.getColumn(i);
			col.setHeaderValue(HEADERS[i]);
		}	
	}
	
	
	
}
