package org.rfc.ui;

import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class PreviewDataTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PreviewDataTable() {
		super(new PreviewDataTableModel());
		initialize();
	}
	
	private void initialize() {
		PreviewDataTableModel model=(PreviewDataTableModel) getModel();
		model.addTableModelListener(this);
	}
	
	private void createHeaderRenderers() {
		JTableHeader header=this.getTableHeader();
		if(header!=null) {
			TableColumnModel columnModel=header.getColumnModel();
			Enumeration<TableColumn> colEnum=columnModel.getColumns();
			while(colEnum.hasMoreElements()) {
				TableColumn column=colEnum.nextElement();
				column.setHeaderRenderer(new PreviewDataHeaderRenderer());
			}
		}
	}
	
	public void tableChanged(TableModelEvent e) {
		super.tableChanged(e);
		createHeaderRenderers();
		System.out.println("TableModelEvent "+e.getType()+" "+e.getClass()+" "+e.getSource());
		if(e.getType()==TableModelEvent.INSERT) {
			System.out.println("Event: Insert");
			if(e.getColumn()==TableModelEvent.ALL_COLUMNS) {
				System.out.println("All columns changed");
			}
		}
		
	}

}
