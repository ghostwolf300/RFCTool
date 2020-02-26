package org.rfc.function;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rfc.dto.FieldValue;
import org.rfc.dto.InputField;
import org.rfc.dto.Material;
import org.rfc.dto.PlantData;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;


public class ChangePlantData extends SaveMaterialReplica {
	
	public static final String FUNCTION_NAME="ChangePlantData";
	
	public static final Map<String,InputField<?>> FIELD_MAP=initInputFieldMap();
	
	private static Map<String,InputField<?>> initInputFieldMap(){
		Map<String,InputField<?>> map=new HashMap<String,InputField<?>>();
		
		InputField<String> materialId=new InputField<String>("MATERIAL",null,"MaterialId",true);
		InputField<String> plant=new InputField<String>("PLANT",null,"Plant",true);
		InputField<Boolean> doNotCost=new InputField<Boolean>("NO_COSTING",null,"DoNotCost");
		//InputField<Integer> plannedDeliveryTime=new InputField<Integer>("PLND_DELRY",null,"PlannedDeliveryTime");
		
		materialId.setMappedColumn(0);
		plant.setMappedColumn(1);
		doNotCost.setMappedColumn(2);
		//plannedDeliveryTime.setMappedColumn(3);
		
		map.put("MATERIAL",materialId);
		map.put("PLANT",plant);
		map.put("NO_COSTING", doNotCost);
		//map.put("PLND_DELRY",plannedDeliveryTime);
		return Collections.unmodifiableMap(map);
	}
	
	
	public ChangePlantData() {
		super();
	}
	
	public ChangePlantData(int id) {
		super(id);
	}
	
	public ChangePlantData(int id,JCoDestination destination) {
		super(id,destination);
	}
	
	public ChangePlantData(int id,List<Material> materials,JCoDestination destination) {
		super(id,materials,destination);
	}
	
	public ChangePlantData(int id,List<Material> materials,JCoDestination destination,boolean testRun) {
		super(id,materials,destination,testRun);
	}
	
	protected void executeFunction(Material material) throws JCoException{
		
		tHEADDATA.appendRow();
		tHEADDATA.setValue("FUNCTION","UPD");
		tHEADDATA.setValue("MATERIAL",material.getMaterialId().getValue());
		
		//Set<String> fieldNameSet=MATERIAL_FIELD_MAP.keySet();
		Set<String> plants=material.getPlantDataMap().keySet();
		
		for(String plant : plants) {
			PlantData plantData=material.getPlantDataMap().get(plant);
			
			tPLANTDATA.appendRow();
			tPLANTDATA.setValue("FUNCTION", "UPD");
			tPLANTDATA.setValue("MATERIAL", material.getMaterialId().getValue());
			tPLANTDATA.setValue("PLANT", plantData.getPlant().getValue());
			tPLANTDATA.setValue("NO_COSTING", (plantData.isDoNotCost().getValue()==true ? "X" : " "));
			
			tPLANTDATAX.appendRow();
			tPLANTDATAX.setValue("FUNCTION", "UPD");
			tPLANTDATAX.setValue("MATERIAL", material.getMaterialId().getValue());
			tPLANTDATAX.setValue("PLANT", plantData.getPlant().getValue());
			tPLANTDATAX.setValue("NO_COSTING", "X");
				
//			for(String fieldName : fieldNameSet) {
//				InputField<?> inputField=MATERIAL_FIELD_MAP.get(fieldName);
//				FieldValue<?> fval=plantData.getFieldValue(inputField.getPropertyName());
//				tPLANTDATA.setValue(inputField.getRfcName(), (fval.getValue()==true ? "X" : " "));
//			}
			
		}
		
		execute(material);
		
		tHEADDATA.clear();
		tPLANTDATA.clear();
		tPLANTDATAX.clear();
		tRETURNMESSAGES.clear();
	
	}
	
	@Override
	public String getFunctionName() {
		return FUNCTION_NAME;
	}


	public static Map<String, InputField<?>> getFieldMap() {
		return FIELD_MAP;
	}

	
}
