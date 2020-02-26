package org.rfc.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.poi.ss.usermodel.Row;

public class PreviewDataTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Row> previewDataList=null;
	
	public PreviewDataTableModel() {
		super();
	}
	
	public List<Row> getPreviewDataList() {
		return previewDataList;
	}

	public void setPreviewDataList(List<Row> previewDataList) {
		//for(Row r : previewDataList) {
		//	System.out.println(r.getCell(0).getStringCellValue());
		//}
		//System.out.println("Model received: "+previewDataList.size());
		this.previewDataList = previewDataList;
		this.fireTableStructureChanged();
	}

	@Override
	public int getColumnCount() {
		if(previewDataList==null) {
			return 0;
		}
		else {
			return calculateColumns();
		}
	}

	@Override
	public int getRowCount() {
		if(previewDataList==null) {
			return 0;
		}
		else {
			return previewDataList.size();
		}
	}

	@Override
	public Object getValueAt(int r, int c) {
		if(previewDataList==null) {
			return null;
		}
		else {
			Row row=previewDataList.get(r);
			return row.getCell(c);
		}
	}
	
	private int calculateColumns() {
		int currentColumns=0;
		int maxColumns=0;
		for(Row row : previewDataList) {
			currentColumns=row.getLastCellNum();
			if(currentColumns>maxColumns) {
				maxColumns=currentColumns;
			}
		}
		return maxColumns;
	}

}
