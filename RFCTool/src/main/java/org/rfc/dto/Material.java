package org.rfc.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Material {
	
	private String materialId=null;
	private Map<String,PlantData> plantDataMap=null;
	private List<ReturnMessage> messages=null;
	
	public Material() {
		super();
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public Map<String, PlantData> getPlantDataMap() {
		return plantDataMap;
	}

	public void setPlantDataMap(Map<String, PlantData> materialPlantDataMap) {
		this.plantDataMap = materialPlantDataMap;
	}
	
	public void addPlantData(String plant,PlantData plantData) {
		if(plantDataMap==null) {
			plantDataMap=new HashMap<String,PlantData>();
		}
		plantDataMap.put(plant, plantData);
	}
	
	public int getPlantDataCount() {
		if(plantDataMap==null) {
			return 0;
		}
		else {
			return plantDataMap.keySet().size();
		}
	}

	public List<ReturnMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ReturnMessage> messages) {
		this.messages = messages;
	}
	
	
	
}
