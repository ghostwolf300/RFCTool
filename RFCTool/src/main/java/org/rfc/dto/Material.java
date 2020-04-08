package org.rfc.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Material extends AbstractDataObject {
	
	private String materialId=null;
	private String type=null;
	private String industrySector=null;
	private String oldMaterialNumber=null;
	private String baseUom=null;
	private String group=null;
	private String description=null;
	private String generalItemCategoryGroup=null;
	private String crossPlantMaterialStatus=null;
	private String weightUnit=null;
	private String cnCode=null;
	private double grossWeight;
	private double netWeight;
	private double length;
	private double width;
	private double height;
	private Map<String,String> descriptionMap=null;
	private Map<String,PlantData> plantDataMap=null;
	private Map<String,ValuationData> valuationDataMap=null;
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

	public String getDescription() {
		return description;
	}

	public String getGeneralItemCategoryGroup() {
		return generalItemCategoryGroup;
	}

	public void setGeneralItemCategoryGroup(String generalItemCategoryGroup) {
		this.generalItemCategoryGroup = generalItemCategoryGroup;
	}

	public String getCrossPlantMaterialStatus() {
		return crossPlantMaterialStatus;
	}

	public void setCrossPlantMaterialStatus(String crossPlantMaterialStatus) {
		this.crossPlantMaterialStatus = crossPlantMaterialStatus;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(double netWeight) {
		this.netWeight = netWeight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIndustrySector() {
		return industrySector;
	}

	public void setIndustrySector(String industrySector) {
		this.industrySector = industrySector;
	}


	public String getOldMaterialNumber() {
		return oldMaterialNumber;
	}

	public void setOldMaterialNumber(String oldMaterialNumber) {
		this.oldMaterialNumber = oldMaterialNumber;
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

	public Map<String, ValuationData> getValuationDataMap() {
		return valuationDataMap;
	}

	public void setValuationDataMap(Map<String, ValuationData> valuationDataMap) {
		this.valuationDataMap = valuationDataMap;
	}
	
	public void addValuationData(String valArea,ValuationData valuationData) {
		if(valuationDataMap==null) {
			valuationDataMap=new HashMap<String,ValuationData>();
		}
		valuationDataMap.put(valArea, valuationData);
	}
	
	public int getValuationDataCount() {
		if(valuationDataMap==null) {
			return 0;
		}
		else {
			return valuationDataMap.keySet().size();
		}
	}

	public List<ReturnMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ReturnMessage> messages) {
		this.messages = messages;
	}
	
	
	
}
