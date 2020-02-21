package org.rfc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rfc.dto.Material;
import org.rfc.dto.Material3;
import org.rfc.dto.PlantData3;
import org.rfc.dto.PlantData;

public class MaterialDataModel extends AbstractModel {
	
	public static final String P_MATERIALS="p_materials";
	public static final String P_PLANT_DATA_LIST="p_plant_data_list";
	public static final String P_MATERIAL_COUNT="p_material_count";
	public static final String P_PLANT_DATA_COUNT="p_plant_data_count";
	
	private List<Material> materials=null;
	private List<PlantData> plantDataList=null;
	private int materialCount=0;
	private int plantDataCount=0;
	
	public MaterialDataModel() {
		super();
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
		this.firePropertyChange(P_MATERIALS, null, this.materials);
		this.setMaterialCount(this.materials.size());
		this.createPlantDataList(this.materials);
	}

	public List<PlantData> getPlantDataList() {
		return plantDataList;
	}

	public void setPlantDataList(List<PlantData> plantDataList) {
		this.plantDataList = plantDataList;
		this.firePropertyChange(P_PLANT_DATA_LIST, null, this.plantDataList);
		this.setPlantDataCount(this.plantDataList.size());
	}
	
	public int getMaterialCount() {
		return materialCount;
	}

	public void setMaterialCount(int materialCount) {
		this.materialCount = materialCount;
		this.firePropertyChange(P_MATERIAL_COUNT, null, this.materialCount);
	}

	public int getPlantDataCount() {
		return plantDataCount;
	}

	public void setPlantDataCount(int plantDataCount) {
		this.plantDataCount = plantDataCount;
		this.firePropertyChange(P_PLANT_DATA_COUNT, null, this.plantDataCount);
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
