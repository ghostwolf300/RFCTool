package org.rfc.service;

import org.rfc.dao.DAOFactory;
import org.rfc.dao.MaterialDAO;
import org.rfc.dao.excel.ExcelDAOFactory;
import org.rfc.model.MaterialDataModel;

public class RFCService {
	
	private MaterialDataModel materialDataModel=null;
	private MaterialDAO materialDao=null;
	
	public RFCService() {
		super();
		materialDataModel=new MaterialDataModel();
		DAOFactory factory=new ExcelDAOFactory();
		materialDao=factory.getMaterialDAO();
	}

	public MaterialDataModel getMaterialDataModel() {
		return materialDataModel;
	}

	public void setMaterialDataModel(MaterialDataModel materialDataModel) {
		this.materialDataModel = materialDataModel;
	}
}
