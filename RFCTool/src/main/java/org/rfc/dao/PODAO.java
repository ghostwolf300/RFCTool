package org.rfc.dao;

import java.util.List;

public interface PODAO<PurchaseOrder> {
	
	public List<PurchaseOrder> getPONumbers();
	public void savePOs(List<PurchaseOrder> poList);
	
}
