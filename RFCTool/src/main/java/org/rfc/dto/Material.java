package org.rfc.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Material extends DataObject {
	
	private FieldValue<String> materialId=null;
	private FieldValue<String> type=null;
	private FieldValue<String> industrySector=null;
	private FieldValue<String> baseUom=null;
	private FieldValue<String> group=null;
	private Map<String,String> descriptionMap=null;
	private Map<String,PlantData> plantDataMap=null;
	private List<ReturnMessage> messages=null;
	
	
	public Material() {
		super();
	}

	public FieldValue<String> getMaterialId() {
		return materialId;
	}

	public void setMaterialId(FieldValue<String> materialId) {
		this.materialId = materialId;
	}

	public FieldValue<String> getType() {
		return type;
	}

	public void setType(FieldValue<String> type) {
		this.type = type;
	}

	public FieldValue<String> getIndustrySector() {
		return industrySector;
	}

	public void setIndustrySector(FieldValue<String> industrySector) {
		this.industrySector = industrySector;
	}

	public FieldValue<String> getBaseUom() {
		return baseUom;
	}

	public void setBaseUom(FieldValue<String> baseUom) {
		this.baseUom = baseUom;
	}

	public FieldValue<String> getGroup() {
		return group;
	}

	public void setGroup(FieldValue<String> group) {
		this.group = group;
	}



	public Map<String, String> getDescriptionMap() {
		return descriptionMap;
	}

	public void setDescriptionMap(Map<String, String> descriptionMap) {
		this.descriptionMap = descriptionMap;
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
