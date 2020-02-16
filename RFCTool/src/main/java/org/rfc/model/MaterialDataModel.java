package org.rfc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rfc.dto.Material;
import org.rfc.dto.PlantData;

public class MaterialDataModel extends AbstractModel {
	
	public static final String P_MATERIALS="p_materials";
	public static final String P_PLANT_DATA_LIST="p_plant_data_list";
	
	private List<Material> materials=null;
	private List<PlantData> plantDataList=null;
	
	public MaterialDataModel() {
		super();
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
		this.firePropertyChange(P_MATERIALS, null, this.materials);
		this.createPlantDataList(this.materials);
	}

	public List<PlantData> getPlantDataList() {
		return plantDataList;
	}

	public void setPlantDataList(List<PlantData> plantDataList) {
		this.plantDataList = plantDataList;
		this.firePropertyChange(P_PLANT_DATA_LIST, null, this.plantDataList);
	}
	
	private void createPlantDataList(List<Material> materials) {
		List<PlantData> pdList=new ArrayList<PlantData>();
		for(Material m :  materials) {
			if(m.getPlantDataMap()!=null) {
				Set<String> plants=m.getPlantDataMap().keySet();
				for(String plant : plants) {
					pdList.add(m.getPlantDataMap().get(plant));
				}
			}
		}
		this.setPlantDataList(pdList);
	}
	
	
}
