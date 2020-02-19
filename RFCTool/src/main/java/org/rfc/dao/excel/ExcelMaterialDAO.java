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
	
	public List<Material> getPlantDataList() {
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
				plantData=createPlantData(row,material);
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
	
	private PlantData createPlantData(Row row,Material material) {
		PlantData pd=new PlantData();
		pd.setMaterial(material);
		pd.setPlant(row.getCell(1).getStringCellValue());
		pd.setPlannedDeliveryTime((int) row.getCell(2).getNumericCellValue());
		
		return pd;
	}
	
	private PlantData createOpenPlantData(Row row,Material material) {
		PlantData pd=new PlantData();
		pd.setMaterial(material);
		pd.setPlant(row.getCell(2).getStringCellValue());
		pd.setLoadingGroup("Z700");
		pd.setPurchasingGroup(row.getCell(3).getStringCellValue());
		pd.setGrProcessingTime(0);
		pd.setMrpType("PD");
		pd.setReorderPoint(0);
		pd.setMrpController(pd.getPurchasingGroup());
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
		pd.setPriceControl(row.getCell(4).getStringCellValue());
		pd.setMovingAveragePrice(row.getCell(5).getNumericCellValue());
		pd.setStandardPrice(row.getCell(6).getNumericCellValue());
		pd.setValuationClass("Z004");
		pd.setPriceUnit(1);
		pd.setDoNotCost((pd.getMaterial().getType().equals("ZT07") ? true : false));
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
					material.setType(row.getCell(1).getStringCellValue());
					material.setIndustrySector("M");
					materials.add(material);
					currentMaterialID=nextMaterialID;
				}
				plantData=createOpenPlantData(row,material);
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
