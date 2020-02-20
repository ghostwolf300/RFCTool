package org.rfc.dao.excel;

import java.io.File;
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
	
	public ExcelMaterialDAO(File dbFile) {
		super(dbFile);
	}
	
	public List<Material> getChangePlantDataList() {
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
				plantData=createPlantDataShort(row,material);
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
	
	private PlantData createPlantDataShort(Row row,Material material) {
		
		PlantData pd=new PlantData();
		
		pd.setMaterial(material);
		pd.setPlant(row.getCell(1).getStringCellValue());
		//pd.setProfitCenter(row.getCell(2).getStringCellValue());
		pd.setPlannedDeliveryTime((int) row.getCell(3).getNumericCellValue());
		//pd.setGrProcessingTime((int) row.getCell(4).getNumericCellValue());
		//pd.setSpecialProcurement(row.getCell(5).getStringCellValue());
		
		return pd;
	}
	
	private PlantData createPlantDataLong(Row row,Material material) {
		PlantData pd=new PlantData();
		pd.setMaterial(material);
		pd.setPlant(row.getCell(1).getStringCellValue());
		pd.setProfitCenter(row.getCell(2).getStringCellValue());
		pd.setLoadingGroup("Z700");
		pd.setGrProcessingTime(0);
		pd.setMrpType("PD");
		pd.setReorderPoint(0);
		pd.setLotSizingProcedure("EX");
		pd.setMinLotSize(0);
		pd.setProcurementType("F");
		pd.setSpecialProcurement("40");
		pd.setIssueStorageLocation("0700");
		pd.setStorageLocationForEP("0700");
		pd.setPeriodIndicator("M");
		pd.setAvailabilityCheck("ZT");
		pd.setIndividualAndCollectiveReq("2");
		pd.setPlannedDeliveryTime(2);
		pd.setPriceUnit(1);
		pd.setCostWithQtyStructure(true);
		pd.setMaterialRelatedOrigin(true);
		pd.setStorageLocation("0700");
		return pd;
	}

	@Override
	public List<Material> getAddPlantDataList() {
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
				plantData=createPlantDataLong(row,material);
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

}
