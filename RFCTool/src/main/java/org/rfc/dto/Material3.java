package org.rfc.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Material3 {
	
	private String materialId=null;
	private String type=null;
	private String industrySector=null;
	private String baseUom=null;
	private String group=null;
	private Map<String,String> descriptionMap=null;
	private Map<String,PlantData3> plantDataMap=null;
	private List<ReturnMessage> messages=null;
	
	
	public Material3() {
		super();
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getIndustrySector() {
		return industrySector;
	}

	public void setIndustrySector(String industrySector) {
		this.industrySector = industrySector;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBaseUom() {
		return baseUom;
	}

	public void setBaseUom(String baseUom) {
		this.baseUom = baseUom;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Map<String, String> getDescriptionMap() {
		return descriptionMap;
	}

	public void setDescriptionMap(Map<String, String> descriptionMap) {
		this.descriptionMap = descriptionMap;
	}

	public Map<String, PlantData3> getPlantDataMap() {
		return plantDataMap;
	}

	public void setPlantDataMap(Map<String, PlantData3> materialPlantDataMap) {
		this.plantDataMap = materialPlantDataMap;
	}
	
	public void addPlantData(String plant,PlantData3 plantData3) {
		if(plantDataMap==null) {
			plantDataMap=new HashMap<String,PlantData3>();
		}
		plantDataMap.put(plant, plantData3);
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