package org.rfc.dao;

import java.util.List;

public interface MaterialDAO<Material> {
	
	public List<Material> getChangePlantDataList();
	public List<Material> getAddPlantDataList();
}
