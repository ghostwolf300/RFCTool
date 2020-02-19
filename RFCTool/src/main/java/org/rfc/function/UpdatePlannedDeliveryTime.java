package org.rfc.function;


import java.util.List;
import java.util.Set;
import org.rfc.dto.Material;
import org.rfc.dto.PlantData;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;


public class UpdatePlannedDeliveryTime extends SaveMaterialReplica {
	
	public UpdatePlannedDeliveryTime(int id,JCoDestination destination) {
		super(id,destination);
	}
	
	public UpdatePlannedDeliveryTime(int id,List<Material> materials,JCoDestination destination) {
		super(id,materials,destination);
	}
	
	public UpdatePlannedDeliveryTime(int id,List<Material> materials,JCoDestination destination,boolean testRun) {
		super(id,materials,destination,testRun);
	}
	
	protected void doWork() throws JCoException {
		super.doWork();
		executePlannedDeliveryTimeUpdate();
	}
	
	private void executePlannedDeliveryTimeUpdate() throws JCoException {
		
		for(Material material : materials) {
			
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
				tPLANTDATA.setValue("PLND_DELRY", plantData.getPlannedDeliveryTime());

				tPLANTDATAX.appendRow();
				tPLANTDATAX.setValue("FUNCTION", "UPD");
				tPLANTDATAX.setValue("MATERIAL", material.getMaterialId());
				tPLANTDATAX.setValue("PLANT", plantData.getPlant());
				tPLANTDATAX.setValue("PLND_DELRY", "X");
			}
			
			super.execute(material);
			
			tHEADDATA.clear();
			tPLANTDATA.clear();
			tPLANTDATAX.clear();
			tRETURNMESSAGES.clear();
			
			processedCount++;
			this.progressUpdated();
		}
		
	}
	
}
