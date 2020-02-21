package org.rfc.dao;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;

public interface MaterialDAO<Material> {
	
	public List<Row> getPreviewDataList(int maxRows);
	public List<Material> getChangePlantDataList();
	public List<Material> getAddPlantDataList();
}
