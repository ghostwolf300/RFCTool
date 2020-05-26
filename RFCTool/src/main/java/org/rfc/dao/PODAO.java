package org.rfc.dao;

import java.util.List;

import org.rfc.dto.POItem;

public interface PODAO<PurchaseOrder> {
	
	public List<PurchaseOrder> getPONumbers();
	public List<PurchaseOrder> getPOsIncludeItems();
	public void savePOs(List<PurchaseOrder> poList);
	public void savePOItems(List<POItem> poItemList);
	
}
