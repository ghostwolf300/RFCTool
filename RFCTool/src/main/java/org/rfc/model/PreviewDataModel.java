package org.rfc.model;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;

public class PreviewDataModel extends AbstractModel {
	
	public static final String P_PREVIEW_DATA="p_preview_data";
	
	private List<Row> previewDataList=null;
	
	public PreviewDataModel() {
		super();
	}

	public List<Row> getPreviewDataList() {
		return previewDataList;
	}

	public void setPreviewDataList(List<Row> previewDataList) {
		this.previewDataList = previewDataList;
		this.firePropertyChange(P_PREVIEW_DATA, null, this.previewDataList);
	}
	
}
