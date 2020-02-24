package org.rfc.ui;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.poi.ss.usermodel.Row;
import org.rfc.dto.InputField;

public class PreviewDataTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String,InputField<?>> fieldMap=null;
	
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
	
	public void setPreviewDataList(List<Row> previewDataList) {
		PreviewDataTableModel model=(PreviewDataTableModel)getModel();
		model.setPreviewDataList(previewDataList);
	}
	
	public void setFieldMap(Map<String,InputField<?>> fieldMap) {
		this.fieldMap=fieldMap;
	}
	
	public Map<String,InputField<?>> getFieldMap(){
		return fieldMap;
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
