package org.rfc.function;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rfc.dto.FieldValue;
import org.rfc.dto.InputField;
import org.rfc.dto.Material;
import org.rfc.dto.Material3;
import org.rfc.dto.PlantData;
import org.rfc.dto.PlantData3;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;


public class AddAcctCostData extends SaveMaterialReplica {
	
	public static final String FUNCTION_NAME="AddAcctCostData";
	
	public static final Map<String,InputField<?>> FIELD_MAP=initInputFieldMap();
	
	private static Map<String,InputField<?>> initInputFieldMap(){
		Map<String,InputField<?>> map=new HashMap<String,InputField<?>>();
		
		InputField<String> materialId=new InputField<String>("MATERIAL",null,"MaterialId",true);
		InputField<String> plant=new InputField<String>("PLANT",null,"Plant",true);
		InputField<Boolean> doNotCost=new InputField<Boolean>("NO_COSTING",null,"DoNotCost");
		InputField<String> priceControl=new InputField<String>("PRICE_CTRL",null,"PriceControl");
		InputField<String> valuationClass=new InputField<String>("VAL_CLASS",null,"ValuationClass");
		InputField<Double> movingAveragePrice=new InputField<Double>("MOVING_PR",null,"MovingAveragePrice");
		InputField<Double> standardPrice=new InputField<Double>("STD_PRICE",null,"StandardPrice");
		InputField<Integer> priceUnit=new InputField<Integer>("PRICE_UNIT",null,"PriceUnit");
		InputField<Boolean> costWithQtyStructure=new InputField<Boolean>("QTY_STRUCT",null,"IsCostWithQtyStructure");
		InputField<Boolean> materialRelatedOrigin=new InputField<Boolean>("ORIG_MAT",null,"IsMaterialRelatedOrigin");
		
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
		
		Map<String,JCoStructure> structureMap=super.getMaterialPlantData(material.getMaterialId().getValue(), "0700");
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
		
		processedCount++;
		progressUpdated();
	}
	
	private void copyPlantData(Map<String,JCoStructure> structureMap,Material material) {
		
		JCoStructure sCLIENTDATA=structureMap.get("CLIENTDATA");
		JCoStructure sPLANTDATA=structureMap.get("PLANTDATA");
		JCoStructure sVALUATIONDATA=structureMap.get("VALUATIONDATA");
		
		FieldValue<String> industrySector=new FieldValue<String>();
		industrySector.setValue(sCLIENTDATA.getString("IND_SECTOR"));
		material.setIndustrySector(industrySector);
		
		FieldValue<String> type=new FieldValue<String>();
		type.setValue(sCLIENTDATA.getString("MATL_TYPE"));
		material.setType(type);
		
		FieldValue<String> group=new FieldValue<String>();
		group.setValue(sCLIENTDATA.getString("MATL_GROUP"));
		material.setGroup(group);
		
		FieldValue<String> baseUom=new FieldValue<String>();
		baseUom.setValue(sCLIENTDATA.getString("BASE_UOM"));
		material.setBaseUom(baseUom);
		
		
		Set<String> plants=material.getPlantDataMap().keySet();
		for(String plant : plants) {
			PlantData pd=material.getPlantDataMap().get(plant);
			
			FieldValue<String> purchasingGroup=new FieldValue<String>();
			purchasingGroup.setValue(sPLANTDATA.getString("PUR_GROUP"));
			pd.setPurchasingGroup(purchasingGroup);
			
			FieldValue<String> mrpController=new FieldValue<String>();
			mrpController.setValue(sPLANTDATA.getString("MRP_CTRLER"));
			pd.setMrpController(mrpController);
			
			FieldValue<String> valuationClass=new FieldValue<String>();
			valuationClass.setValue(sVALUATIONDATA.getString("VAL_CLASS"));
			pd.setValuationClass(valuationClass);
			
			FieldValue<String> priceControl=new FieldValue<String>();
			priceControl.setValue(sVALUATIONDATA.getString("PRICE_CTRL"));
			pd.setPriceControl(priceControl);
			
			FieldValue<Double> movingPrice=new FieldValue<Double>();
			movingPrice.setValue(sVALUATIONDATA.getDouble("MOVING_PR"));
			pd.setMovingAveragePrice(movingPrice);
			
			FieldValue<Double> standardPrice=new FieldValue<Double>();
			standardPrice.setValue(sVALUATIONDATA.getDouble("STD_PRICE"));
			pd.setStandardPrice(standardPrice);
			
			FieldValue<Integer> priceUnit=new FieldValue<Integer>();
			priceUnit.setValue(sVALUATIONDATA.getInt("PRICE_UNIT"));
			pd.setPriceUnit(priceUnit);
			
			FieldValue<Boolean> qtyStructure=new FieldValue<Boolean>();
			qtyStructure.setValue((sVALUATIONDATA.getString("QTY_STRUCT").equals("X") ? true : false));
			pd.setCostWithQtyStructure(qtyStructure);
			
			FieldValue<Boolean> originMaterial=new FieldValue<Boolean>();
			originMaterial.setValue((sVALUATIONDATA.getString("ORIG_MAT").equals("X") ? true : false));
			pd.setMaterialRelatedOrigin(originMaterial);
			
			//FieldValue<Boolean> mbrue=new FieldValue<Boolean>();
			//mbrue.setValue((sVALUATIONDATA.getString("PRVMBEWHX").equals("X") ? true : false));
			//pd.setMbrue(mbrue);
			
			
		}
		
	}

	@Override
	public String getFunctionName() {
		return FUNCTION_NAME;
	}
	
	public static Map<String, InputField<?>> getFieldMap() {
		return FIELD_MAP;
	}


}
