package org.rfc.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.rfc.dto.Material;
import org.rfc.dto.PlantData;

public class PlantDataTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int COL_MATERIAL=0;
	public static final int COL_PLANT=1;
	public static final int COL_PLAN_DEL_TIME=2;
	
	private List<PlantData> plantDataList=null;
	
	public int getColumnCount() {
		return 3;
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
					return plantData.getMaterialId();
				case COL_PLANT:
					return plantData.getPlant();
				case COL_PLAN_DEL_TIME:
					return plantData.getPlannedDeliveryTime();
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
