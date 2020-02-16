package org.rfc.dao.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.rfc.dao.MaterialDAO;
import org.rfc.dto.Material;
import org.rfc.dto.PlantData;

public class ExcelMaterialDAO extends ExcelDAO implements MaterialDAO<Material> {
	
	public ExcelMaterialDAO() {
		super();
	}
	
	public ExcelMaterialDAO(String dbPath) {
		super(dbPath);
	}
	
	public List<Material> getPlannedDeliveryTimeUpdateList() {
		List<Material> materials=new ArrayList<Material>();
		Material material=null;
		PlantData plantData=null;
		String currentMaterialID=null;
		String nextMaterialID=null;
		try {
			this.openConnection();
			Sheet sheet=this.workbook.getSheetAt(0);
			Iterator<Row> rowIter=sheet.iterator();
			//skip header row
			rowIter.next();
			while(rowIter.hasNext()) {
				Row row=rowIter.next();
				nextMaterialID=row.getCell(0).getStringCellValue();
				if(currentMaterialID==null || !currentMaterialID.equals(nextMaterialID)) {
					material=new Material();
					material.setMaterialId(nextMaterialID);
					materials.add(material);
					currentMaterialID=nextMaterialID;
				}
				plantData=createPlantData(row);
				material.addPlantData(plantData.getPlant(), plantData);
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				this.closeConnection();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return materials;
	}
	
	private PlantData createPlantData(Row row) {
		PlantData pd=new PlantData();
		pd.setMaterialId(row.getCell(0).getStringCellValue());
		pd.setPlant(row.getCell(1).getStringCellValue());
		pd.setPlannedDeliveryTime((int) row.getCell(2).getNumericCellValue());
		return pd;
	}

}
