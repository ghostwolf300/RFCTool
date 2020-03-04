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
		tHEADDATA.setValue("MATERIAL",material.getMaterialId());
		
		Set<String> plants=material.getPlantDataMap().keySet();
		
		for(String plant : plants) {
			PlantData plantData=material.getPlantDataMap().get(plant);
			
			tPLANTDATA.appendRow();
			tPLANTDATA.setValue("FUNCTION", "UPD");
			tPLANTDATA.setValue("MATERIAL", material.getMaterialId());
			tPLANTDATA.setValue("PLANT", plantData.getPlant());
			tPLANTDATA.setValue("NO_COSTING", (plantData.isDoNotCost()==true ? "X" : " "));
			
			tPLANTDATAX.appendRow();
			tPLANTDATAX.setValue("FUNCTION", "UPD");
			tPLANTDATAX.setValue("MATERIAL", material.getMaterialId());
			tPLANTDATAX.setValue("PLANT", plantData.getPlant());
			tPLANTDATAX.setValue("NO_COSTING", "X");
			
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

	
}
