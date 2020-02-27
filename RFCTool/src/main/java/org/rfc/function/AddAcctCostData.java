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
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;


public class AddAcctCostData extends SaveMaterialReplica {
	
	public static final String FUNCTION_NAME="AddAcctCostData";
	
	public static final Map<String,InputField<?>> FIELD_MAP=initInputFieldMap();
	
	private static Map<String,InputField<?>> initInputFieldMap(){
		Map<String,InputField<?>> map=new HashMap<String,InputField<?>>();
		
		InputField<String> materialId=new InputField<String>("MATERIAL",null,"MaterialId",true,String.class);
		InputField<String> plant=new InputField<String>("PLANT",null,"Plant",true,String.class);
		InputField<Boolean> doNotCost=new InputField<Boolean>("NO_COSTING",null,"DoNotCost",Boolean.class);
		InputField<String> priceControl=new InputField<String>("PRICE_CTRL",null,"PriceControl",String.class);
		InputField<String> valuationClass=new InputField<String>("VAL_CLASS",null,"ValuationClass",String.class);
		InputField<Double> movingAveragePrice=new InputField<Double>("MOVING_PR",null,"MovingAveragePrice",Double.class);
		InputField<Double> standardPrice=new InputField<Double>("STD_PRICE",null,"StandardPrice",Double.class);
		InputField<Integer> priceUnit=new InputField<Integer>("PRICE_UNIT",null,"PriceUnit",Integer.class);
		InputField<Boolean> costWithQtyStructure=new InputField<Boolean>("QTY_STRUCT",null,"IsCostWithQtyStructure",Boolean.class);
		InputField<Boolean> materialRelatedOrigin=new InputField<Boolean>("ORIG_MAT",null,"IsMaterialRelatedOrigin",Boolean.class);
		
		map.put(materialId.getRfcName(),materialId);
		map.put(plant.getRfcName(),plant);
		map.put(doNotCost.getRfcName(), doNotCost);
		map.put(priceControl.getRfcName(), priceControl);
		map.put(valuationClass.getRfcName(), valuationClass);
		map.put(movingAveragePrice.getRfcName(), movingAveragePrice);
		map.put(standardPrice.getRfcName(), standardPrice);
		map.put(priceUnit.getRfcName(),priceUnit);
		map.put(costWithQtyStructure.getRfcName(), costWithQtyStructure);
		map.put(materialRelatedOrigin.getRfcName(), materialRelatedOrigin);
		
		return Collections.unmodifiableMap(map);
	}
	
	public AddAcctCostData() {
		super();
	}
	
	public AddAcctCostData(int id) {
		super(id);
	}
	
	public AddAcctCostData(int id,JCoDestination destination) {
		super(id,destination);
	}
	
	public AddAcctCostData(int id,List<Material> materials,JCoDestination destination) {
		super(id,materials,destination);
	}
	
	public AddAcctCostData(int id,List<Material> materials,JCoDestination destination,boolean testRun) {
		super(id,materials,destination,testRun);
	}
	
	protected void executeFunction(Material material) throws JCoException {
		
		Map<String,JCoStructure> structureMap=getMaterialPlantData(material.getMaterialId().getValue(), "0700");
		copyPlantData(structureMap,material);
		
		tHEADDATA.appendRow();
		tHEADDATA.setValue("FUNCTION","INS");
		tHEADDATA.setValue("MATERIAL",material.getMaterialId().getValue());
		
		Set<String> plants=material.getPlantDataMap().keySet();
		
		for(String plant : plants) {
			
			PlantData pd=material.getPlantDataMap().get(plant);
			
			tPLANTDATA.appendRow();
			tPLANTDATA.setValue("FUNCTION", "UPD");
			tPLANTDATA.setValue("MATERIAL", material.getMaterialId().getValue());
			tPLANTDATA.setValue("PLANT", pd.getPlant().getValue());
			//Default "no cost=true"
			tPLANTDATA.setValue("NO_COSTING", "X");
			tPLANTDATA.setValue("ACCOUNT_VIEW","X");
			tPLANTDATA.setValue("COST_VIEW","X");

			tPLANTDATAX.appendRow();
			tPLANTDATAX.setValue("FUNCTION", "UPD");
			tPLANTDATAX.setValue("MATERIAL", pd.getMaterialId().getValue());
			tPLANTDATAX.setValue("PLANT", pd.getPlant().getValue());
			tPLANTDATAX.setValue("NO_COSTING", "X");
			
			//---Accounting & Costing
			tVALUATIONDATA.appendRow();
			tVALUATIONDATA.setValue("FUNCTION", "INS");
			tVALUATIONDATA.setValue("MATERIAL", material.getMaterialId().getValue());
			tVALUATIONDATA.setValue("VAL_AREA", pd.getPlant().getValue());
			tVALUATIONDATA.setValue("PRICE_CTRL", pd.getPriceControl().getValue());
			tVALUATIONDATA.setValue("VAL_CLASS", pd.getValuationClass().getValue());
			tVALUATIONDATA.setValue("MOVING_PR", pd.getMovingAveragePrice().getValue());
			tVALUATIONDATA.setValue("STD_PRICE", pd.getStandardPrice().getValue());
			tVALUATIONDATA.setValue("PRICE_UNIT", pd.getPriceUnit().getValue());
			tVALUATIONDATA.setValue("QTY_STRUCT", (pd.isCostWithQtyStructure().getValue()==true ? "X" : " "));
			tVALUATIONDATA.setValue("ORIG_MAT", (pd.isMaterialRelatedOrigin().getValue()==true ? "X" : " "));
			//tVALUATIONDATA.setValue("PRVMBEWHX", (pd.getMbrue().getValue()==true ? "X" : " "));
			tVALUATIONDATA.setValue("ACCOUNT_VIEW", "X");
			tVALUATIONDATA.setValue("COST_VIEW", "X");
			
			tVALUATIONDATAX.appendRow();
			tVALUATIONDATAX.setValue("FUNCTION", "INS");
			tVALUATIONDATAX.setValue("MATERIAL", material.getMaterialId().getValue());
			tVALUATIONDATAX.setValue("VAL_AREA", pd.getPlant().getValue());
			tVALUATIONDATAX.setValue("PRICE_CTRL", "X");
			tVALUATIONDATAX.setValue("VAL_CLASS", "X");
			tVALUATIONDATAX.setValue("MOVING_PR","X");
			tVALUATIONDATAX.setValue("STD_PRICE", "X");
			tVALUATIONDATAX.setValue("PRICE_UNIT","X");
			tVALUATIONDATAX.setValue("QTY_STRUCT", "X");
			tVALUATIONDATAX.setValue("ORIG_MAT", "X");
			//tVALUATIONDATAX.setValue("PRVMBEWHX","X");
		}
		
		execute(material);
		
		tHEADDATA.clear();
		tPLANTDATA.clear();
		tPLANTDATAX.clear();
		tVALUATIONDATA.clear();
		tVALUATIONDATAX.clear();
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
