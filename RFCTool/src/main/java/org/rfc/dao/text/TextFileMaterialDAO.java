package org.rfc.dao.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.rfc.dao.MaterialDAO;
import org.rfc.dto.FieldValue;
import org.rfc.dto.InputField;
import org.rfc.dto.Material;
import org.rfc.dto.PlantData;
import org.rfc.function.AddAcctCostData;
import org.rfc.function.AddPlantData;
import org.rfc.function.AddPurchMRPData;
import org.rfc.function.ChangePlantData;
import org.rfc.function.SaveMaterialReplica;

public class TextFileMaterialDAO extends TextFileDAO implements MaterialDAO<Material> {
	
	public TextFileMaterialDAO() {
		super();
	}
	
	public TextFileMaterialDAO(File dbFile) {
		super(dbFile);
	}
	
	public TextFileMaterialDAO(File dbFile,char delimiter,String encoding) {
		super(dbFile,delimiter,encoding);
	}
	
	
	@Override
	public List<Row> getPreviewDataList(int maxRows) {
		List<Row> previewDataList=null;
		String line="";
		int rowCount=0;
		try(BufferedReader reader=getReader()){
			previewDataList=new ArrayList<Row>();
			while((line=reader.readLine())!=null) {
				rowCount++;
			}
		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return previewDataList;
	}

	@Override
	public List<Material> getChangePlantDataList() {
		List<Material> materials=null;
		String nextMaterialId=null;
		String currentMaterialId=null;
		String line="";
		String[] fieldValues=null;
		Material m=null;
		PlantData pd=null;
		Map<String,InputField<?>> fieldMap=ChangePlantData.FIELD_MAP;
		
		int rowCount=0;
		try(BufferedReader reader=getReader()){
			materials=new ArrayList<Material>();
			while((line=reader.readLine())!=null) {
				fieldValues=getFieldValues(line);
				nextMaterialId=fieldValues[0];
				if(currentMaterialId==null || !currentMaterialId.equals(nextMaterialId)) {
					m=createMaterial(fieldValues,fieldMap);
					materials.add(m);
					currentMaterialId=nextMaterialId;
				}
				pd=createPlantDataShort(fieldValues,fieldMap);
				pd.setMaterial(m);
				m.addPlantData(pd.getPlant().getValue(), pd);
				rowCount++;
			}
		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return materials;
	}

	@Override
	public List<Material> getAddPlantDataList() {
		List<Material> materials=null;
		String nextMaterialId=null;
		String currentMaterialId=null;
		String line="";
		String[] fieldValues=null;
		Material m=null;
		PlantData pd=null;
		Map<String,InputField<?>> fields=AddPlantData.FIELD_MAP;
		
		int rowCount=0;
		try(BufferedReader reader=getReader()){
			materials=new ArrayList<Material>();
			while((line=reader.readLine())!=null) {
				fieldValues=getFieldValues(line);
				nextMaterialId=fieldValues[0];
				if(currentMaterialId==null || !currentMaterialId.equals(nextMaterialId)) {
					m=createMaterial(fieldValues,fields);
					materials.add(m);
					currentMaterialId=nextMaterialId;
				}
				pd=createPlantDataExperimental(fieldValues,fields);
				pd.setMaterial(m);
				m.addPlantData(pd.getPlant().getValue(), pd);
				rowCount++;
			}
		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return materials;
	}
	
	@Override
	public List<Material> getAddPurchMRPDataList() {
		List<Material> materials=null;
		String nextMaterialId=null;
		String currentMaterialId=null;
		String line="";
		String[] fieldValues=null;
		Material m=null;
		PlantData pd=null;
		Map<String,InputField<?>> fieldMap=AddPurchMRPData.FIELD_MAP;
		
		int rowCount=0;
		try(BufferedReader reader=getReader()){
			materials=new ArrayList<Material>();
			while((line=reader.readLine())!=null) {
				fieldValues=getFieldValues(line);
				nextMaterialId=fieldValues[0];
				if(currentMaterialId==null || !currentMaterialId.equals(nextMaterialId)) {
					m=createMaterial(fieldValues,fieldMap);
					materials.add(m);
					currentMaterialId=nextMaterialId;
				}
				pd=createPurchMRPData(fieldValues,fieldMap);
				pd.setMaterial(m);
				m.addPlantData(pd.getPlant().getValue(), pd);
				rowCount++;
			}
		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return materials;
	}
	
	@Override
	public List<Material> getAddAcctCostDataList() {
		List<Material> materials=null;
		String nextMaterialId=null;
		String currentMaterialId=null;
		String line="";
		String[] fieldValues=null;
		Material m=null;
		PlantData pd=null;
		Map<String,InputField<?>> fieldMap=AddAcctCostData.FIELD_MAP;
		
		int rowCount=0;
		try(BufferedReader reader=getReader()){
			materials=new ArrayList<Material>();
			while((line=reader.readLine())!=null) {
				fieldValues=getFieldValues(line);
				nextMaterialId=fieldValues[0];
				if(currentMaterialId==null || !currentMaterialId.equals(nextMaterialId)) {
					m=createMaterial(fieldValues,fieldMap);
					materials.add(m);
					currentMaterialId=nextMaterialId;
				}
				pd=createAcctCostData(fieldValues,fieldMap);
				pd.setMaterial(m);
				m.addPlantData(pd.getPlant().getValue(), pd);
				rowCount++;
			}
		} 
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return materials;
	}
	
	private Material createMaterial(String[] fields,Map<String,InputField<?>> fieldMap) {
		Material m=null;
		if(fields!=null) {
			m=new Material();
			FieldValue<String> materialId=(FieldValue<String>) fieldMap.get("MATERIAL").createFieldValue();
			materialId.setValue(fields[0]);
			m.setMaterialId(materialId);
		}
		return m;
	}
	
	private PlantData createPlantDataShort(String[] fields,Map<String,InputField<?>> fieldMap) {
		PlantData pd=null;
		if(fields!=null) {
			pd=new PlantData();
			
			FieldValue<String> plant=(FieldValue<String>) fieldMap.get("PLANT").createFieldValue();
			plant.setValue(fields[1]);
			pd.setPlant(plant);
			
			FieldValue<Boolean> doNotCost=(FieldValue<Boolean>) fieldMap.get("NO_COSTING").createFieldValue();
			doNotCost.setValue((fields[2].equals("X") ? true : false));
			pd.setDoNotCost(doNotCost);
			
		}
		return pd;
	}
	
	private PlantData createPlantDataExperimental(String[] fields,Map<String,InputField<?>> fieldMap) {
		PlantData pd=null;
		if(fields!=null) {
			pd=new PlantData();
			
			FieldValue<String> plant=(FieldValue<String>) fieldMap.get("PLANT").createFieldValue();
			plant.setValue(fields[1]);
			pd.setPlant(plant);
			
			FieldValue<String> profitCenter=(FieldValue<String>) fieldMap.get("PROFIT_CTR").createFieldValue();
			profitCenter.setValue(fields[2]);
			pd.setProfitCenter(profitCenter);
			
		}
		return pd;
	}
	
	private PlantData createAcctCostData(String[] fields,Map<String,InputField<?>> fieldMap) {
		PlantData pd=null;
		if(fields!=null) {
			pd=new PlantData();
			
			FieldValue<String> plant=(FieldValue<String>) fieldMap.get("PLANT").createFieldValue();
			plant.setValue(fields[1]);
			pd.setPlant(plant);
			
//			FieldValue<Boolean> doNotCost=(FieldValue<Boolean>) fieldMap.get("NO_COSTING").createFieldValue();
//			doNotCost.setValue((fields[2].equals("X") ? true : false));
//			pd.setDoNotCost(doNotCost);
			
		}
		return pd;
	}
	
	private PlantData createPurchMRPData(String[] fields,Map<String,InputField<?>> fieldMap) {
		PlantData pd=null;
		if(fields!=null) {
			pd=new PlantData();
			
			FieldValue<String> plant=(FieldValue<String>) fieldMap.get("PLANT").createFieldValue();
			plant.setValue(fields[1]);
			pd.setPlant(plant);
			
		}
		return pd;
	}

	

	

}
