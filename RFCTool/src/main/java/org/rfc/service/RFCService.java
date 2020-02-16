package org.rfc.service;

import java.io.File;
import java.util.List;

import org.rfc.dao.DAOFactory;
import org.rfc.dao.MaterialDAO;
import org.rfc.dao.excel.ExcelDAOFactory;
import org.rfc.dto.Material;
import org.rfc.model.MaterialDataModel;

public class RFCService {
	
	private MaterialDataModel materialDataModel=null;
	private MaterialDAO<Material> materialDao=null;
	
	public RFCService() {
		super();
		materialDataModel=new MaterialDataModel();
		
	}

	public MaterialDataModel getMaterialDataModel() {
		return materialDataModel;
	}

	public void setMaterialDataModel(MaterialDataModel materialDataModel) {
		this.materialDataModel = materialDataModel;
	}
	
	public void loadPlantDataFile(File file) {
		DAOFactory factory=new ExcelDAOFactory(file);
		materialDao=factory.getMaterialDAO();
		List<Material> materials=materialDao.getPlantDataList();
		materialDataModel.setMaterials(materials);
	}
}
