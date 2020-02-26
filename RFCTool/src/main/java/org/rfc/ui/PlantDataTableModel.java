package org.rfc.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.rfc.dto.PlantData;

public class PlantDataTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int COL_MATERIAL=0;
	public static final int COL_TYPE=1;
	public static final int COL_PLANT=2;
	public static final int COL_PURCH_GROUP=3;
	public static final int COL_PRICE_CTRL=4;
	public static final int COL_MOV_AVG_PRICE=5;
	public static final int COL_STD_PRICE=6;
	public static final int COL_PLAN_DEL_TIME=7;
	
	private List<PlantData> plantDataList=null;
	
	public int getColumnCount() {
		return 8;
	}

	public int getRowCount() {
		if(plantDataList!=null) {
			if(plantDataList.size()<=500) {
				return plantDataList.size();
			}
			else {
				return 500;
			}
		}
		else {
			return 0;
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if(plantDataList==null) {
			return null;
		}
		else {
			PlantData plantData=plantDataList.get(rowIndex);
			switch(columnIndex) {
				case COL_MATERIAL :  
					return plantData.getMaterial().getMaterialId().getValue();
				case COL_TYPE :
					return plantData.getMaterial().getType();
				case COL_PLANT:
					return plantData.getPlant().getValue();
				case COL_PURCH_GROUP :
					return plantData.getPurchasingGroup();
				case COL_PRICE_CTRL :
					return plantData.getPriceControl();
				case COL_MOV_AVG_PRICE :
					return plantData.getMovingAveragePrice();
				case COL_STD_PRICE :
					return plantData.getStandardPrice();
				case COL_PLAN_DEL_TIME:
					return plantData.getPlannedDeliveryTime().getValue();
				default :
					return null;
			}
		}
	}

	public List<PlantData> getPlantDataList() {
		return plantDataList;
	}

	public void setPlantDataList(List<PlantData> plantDataList) {
		this.plantDataList = plantDataList;
		this.fireTableDataChanged();
	}


}
