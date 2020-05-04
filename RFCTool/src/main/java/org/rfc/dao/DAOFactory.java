package org.rfc.dao;

import org.rfc.dto.Material;
import org.rfc.dto.PurchaseOrder;
import org.rfc.dto.ReturnMessage;

public interface DAOFactory {
	
	public MaterialDAO<Material> getMaterialDAO();
	public PODAO<PurchaseOrder> getPODAO();
	public ReturnMessageDAO<ReturnMessage> getReturnMessageDAO();
	
}
