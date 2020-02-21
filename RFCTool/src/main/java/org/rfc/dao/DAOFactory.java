package org.rfc.dao;

import org.rfc.dto.Material;
import org.rfc.dto.Material3;
import org.rfc.dto.ReturnMessage;

public interface DAOFactory {
	
	public MaterialDAO<Material> getMaterialDAO();
	public ReturnMessageDAO<ReturnMessage> getReturnMessageDAO();
	
}
