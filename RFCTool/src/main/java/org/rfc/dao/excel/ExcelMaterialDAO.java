package org.rfc.dao.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.rfc.dao.MaterialDAO;
import org.rfc.dto.FieldValue;
import org.rfc.dto.InputField;
import org.rfc.dto.Material;
import org.rfc.dto.Material3;
import org.rfc.dto.PlantData3;
import org.rfc.dto.PreviewRow;
import org.rfc.dto.PlantData;
import org.rfc.function.ChangePlantData;

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
	
	@Override
	public List<Row> getPreviewDataList(int maxRows) {
		
		List<Row> previewDataList=null;
		int rowCount=0;
		
		try {
			openConnection();
			Sheet sheet=this.workbook.getSheetAt(0);
			previewDataList=new ArrayList<Row>();
			
			Iterator<Row> rowIter=sheet.iterator();
			while(rowIter.hasNext() && rowCount<=maxRows) {
				Row row=rowIter.next();
				previewDataList.add(row);
				rowCount++;
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return previewDataList;
	}
	
	public List<Material> getChangePlantDataList() {
		List<Material> materials=new ArrayList<Material>();
		Material material=null;
		PlantData plantData=null;
		String currentMaterialID=null;
		String nextMaterialID=null;
		Map<String,InputField<?>> fldMap=ChangePlantData.FIELD_MAP;
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
					FieldValue<String> materialId=(FieldValue<String>)fldMap.get("MATERIAL").createFieldValue();
					materialId.setValue(nextMaterialID);
					material.setMaterialId(materialId);
					materials.add(material);
					currentMaterialID=nextMaterialID;
				}
				plantData=createPlantDataShort(row,material);
				material.addPlantData(plantData.getPlant().getValue(), plantData);
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (InvalidFormatException e) {
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
		FieldValue<String> plant=(FieldValue<String>)ChangePlantData.FIELD_MAP.get("PLANT").createFieldValue();
		plant.setValue(row.getCell(1).getStringCellValue());
		pd.setPlant(plant);
		FieldValue<Boolean> doNotCost=(FieldValue<Boolean>)ChangePlantData.FIELD_MAP.get("NO_COSTING").createFieldValue();
		if(row.getLastCellNum()>2) {
			String val=row.getCell(2).getStringCellValue();
			if(val==null) {
				doNotCost.setValue(false);
			}	
			else {
				doNotCost.setValue((val.equals("X") ? true : false));
			}
		}
		else {
			doNotCost.setValue(false);
		}
		pd.setDoNotCost(doNotCost);
		
		
		return pd;
	}
	
	private PlantData3 createPlantDataLong(Row row,Material3 material3) {
		PlantData3 pd=new PlantData3();
		pd.setMaterial(material3);
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
//		List<Material> materials=new ArrayList<Material>();
//		Material material=null;
//		PlantData3 plantData=null;
//		String currentMaterialID=null;
//		String nextMaterialID=null;
//		try {
//			this.openConnection();
//			Sheet sheet=this.workbook.getSheetAt(0);
//			Iterator<Row> rowIter=sheet.iterator();
//			//skip header row
//			rowIter.next();
//			while(rowIter.hasNext()) {
//				Row row=rowIter.next();
//				nextMaterialID=row.getCell(0).getStringCellValue();
//				if(currentMaterialID==null || !currentMaterialID.equals(nextMaterialID)) {
//					material=new Material();
//					material.setMaterialId(nextMaterialID);
//					materials.add(material);
//					currentMaterialID=nextMaterialID;
//				}
//				plantData=createPlantDataLong(row,material);
//				material.addPlantData(plantData.getPlant(), plantData);
//			}
//		} 
//		catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		finally {
//			try {
//				this.closeConnection();
//			} 
//			catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		return null;
	}

	

}
