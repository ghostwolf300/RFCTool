package org.rfc.function;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rfc.dto.Material;
import org.rfc.dto.PlantData;
import org.rfc.dto.ValuationData;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoStructure;

public class AddAcctCostData extends SaveMaterialReplica {
	
	public static final String FUNCTION_NAME="AddAcctCostData";
	
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
		
		Map<String,JCoStructure> structureMap=getMaterialPlantData(material.getMaterialId(), "0700");
		copyPlantData(structureMap,material);
		
		tHEADDATA.appendRow();
		tHEADDATA.setValue("FUNCTION","INS");
		tHEADDATA.setValue("MATERIAL",material.getMaterialId());
		
		Set<String> plants=material.getPlantDataMap().keySet();
		
		for(String plant : plants) {
			PlantData pd=material.getPlantDataMap().get(plant);
			
			tPLANTDATA.appendRow();
			tPLANTDATA.setValue("FUNCTION", "UPD");
			tPLANTDATA.setValue("MATERIAL", material.getMaterialId());
			tPLANTDATA.setValue("PLANT", pd.getPlant());
			tPLANTDATA.setValue("NO_COSTING", "X");
			tPLANTDATA.setValue("ACCOUNT_VIEW","X");
			tPLANTDATA.setValue("COST_VIEW","X");

			tPLANTDATAX.appendRow();
			tPLANTDATAX.setValue("FUNCTION", "UPD");
			tPLANTDATAX.setValue("MATERIAL", pd.getMaterialId());
			tPLANTDATAX.setValue("PLANT", pd.getPlant());
			tPLANTDATAX.setValue("NO_COSTING", "X");
		}
		
		Set<String> valAreas=material.getValuationDataMap().keySet();
		
		for(String valArea : valAreas) {
			ValuationData vd=material.getValuationDataMap().get(valArea);
			//---Accounting & Costing
			tVALUATIONDATA.appendRow();
			tVALUATIONDATA.setValue("FUNCTION", "INS");
			tVALUATIONDATA.setValue("MATERIAL", material.getMaterialId());
			tVALUATIONDATA.setValue("VAL_AREA", vd.getValuationArea());
			tVALUATIONDATA.setValue("PRICE_CTRL", vd.getPriceControl());
			tVALUATIONDATA.setValue("VAL_CLASS", vd.getValuationClass());
			tVALUATIONDATA.setValue("MOVING_PR", vd.getMovingAveragePrice());
			tVALUATIONDATA.setValue("STD_PRICE", vd.getStandardPrice());
			tVALUATIONDATA.setValue("PRICE_UNIT", vd.getPriceUnit());
			tVALUATIONDATA.setValue("QTY_STRUCT", (vd.isCostWithQtyStructure()==true ? "X" : " "));
			tVALUATIONDATA.setValue("ORIG_MAT", (vd.isMaterialRelatedOrigin()==true ? "X" : " "));
			tVALUATIONDATA.setValue("ACCOUNT_VIEW", "X");
			tVALUATIONDATA.setValue("COST_VIEW", "X");
			
			tVALUATIONDATAX.appendRow();
			tVALUATIONDATAX.setValue("FUNCTION", "INS");
			tVALUATIONDATAX.setValue("MATERIAL", material.getMaterialId());
			tVALUATIONDATAX.setValue("VAL_AREA", vd.getValuationArea());
			tVALUATIONDATAX.setValue("PRICE_CTRL", "X");
			tVALUATIONDATAX.setValue("VAL_CLASS", "X");
			tVALUATIONDATAX.setValue("MOVING_PR","X");
			tVALUATIONDATAX.setValue("STD_PRICE", "X");
			tVALUATIONDATAX.setValue("PRICE_UNIT","X");
			tVALUATIONDATAX.setValue("QTY_STRUCT", "X");
			tVALUATIONDATAX.setValue("ORIG_MAT", "X");
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
	
}
