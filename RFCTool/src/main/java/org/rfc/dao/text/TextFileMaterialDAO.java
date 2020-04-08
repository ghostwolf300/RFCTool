package org.rfc.dao.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.rfc.dao.MaterialDAO;
import org.rfc.dto.FieldValue;
import org.rfc.dto.InputField;
import org.rfc.dto.Material;
import org.rfc.dto.PlantData;
import org.rfc.dto.ValuationData;
import org.rfc.function.AddAcctCostData;
import org.rfc.function.AddPlantData;
import org.rfc.function.AddPurchMRPData;
import org.rfc.function.ChangePlantData;
import org.rfc.function.SaveMaterialReplica;

public class TextFileMaterialDAO extends TextFileDAO implements MaterialDAO<Material> {
	
	private final NumberFormat format=NumberFormat.getInstance(Locale.FRANCE);
	
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
		
		int rowCount=0;
		try(BufferedReader reader=getReader()){
			materials=new ArrayList<Material>();
			while((line=reader.readLine())!=null) {
				fieldValues=getFieldValues(line);
				nextMaterialId=fieldValues[0];
				if(currentMaterialId==null || !currentMaterialId.equals(nextMaterialId)) {
					m=createMaterial(fieldValues);
					materials.add(m);
					currentMaterialId=nextMaterialId;
				}
				pd=createPlantDataShort(fieldValues);
				pd.setMaterial(m);
				m.addPlantData(pd.getPlant(), pd);
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
		
		int rowCount=0;
		try(BufferedReader reader=getReader()){
			materials=new ArrayList<Material>();
			while((line=reader.readLine())!=null) {
				fieldValues=getFieldValues(line);
				nextMaterialId=fieldValues[0];
				if(currentMaterialId==null || !currentMaterialId.equals(nextMaterialId)) {
					m=createMaterial(fieldValues);
					materials.add(m);
					currentMaterialId=nextMaterialId;
				}
				pd=createPlantDataExperimental(fieldValues);
				pd.setMaterial(m);
				m.addPlantData(pd.getPlant(), pd);
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
		
		int rowCount=0;
		try(BufferedReader reader=getReader()){
			materials=new ArrayList<Material>();
			while((line=reader.readLine())!=null) {
				fieldValues=getFieldValues(line);
				nextMaterialId=fieldValues[0];
				if(currentMaterialId==null || !currentMaterialId.equals(nextMaterialId)) {
					m=createMaterial(fieldValues);
					materials.add(m);
					currentMaterialId=nextMaterialId;
				}
				pd=createPurchMRPData(fieldValues);
				pd.setMaterial(m);
				m.addPlantData(pd.getPlant(), pd);
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
		ValuationData vd=null;
		
		int rowCount=0;
		try(BufferedReader reader=getReader()){
			materials=new ArrayList<Material>();
			while((line=reader.readLine())!=null) {
				fieldValues=getFieldValues(line);
				nextMaterialId=fieldValues[0];
				if(currentMaterialId==null || !currentMaterialId.equals(nextMaterialId)) {
					m=createMaterial(fieldValues);
					materials.add(m);
					currentMaterialId=nextMaterialId;
				}
				pd=createAcctCostData(fieldValues);
				pd.setMaterial(m);
				m.addPlantData(pd.getPlant(), pd);
				vd=new ValuationData();
				vd.setValuationArea(pd.getPlant());
				m.addValuationData(vd.getValuationArea(), vd);
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
	public List<Material> getAddClassificationDataList() {
		List<Material> materials=null;
		String line="";
		String[] fieldValues=null;
		Material m=null;
		
		int rowCount=0;
		try(BufferedReader reader=getReader()){
			materials=new ArrayList<Material>();
			while((line=reader.readLine())!=null) {
				fieldValues=getFieldValues(line);
				m=createJDMaterial(fieldValues);
				materials.add(m);
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
	public List<Material> getCreateJDMaterialList() {
		List<Material> materials=null;
		String line="";
		String[] fieldValues=null;
		Material m=null;
		
		int rowCount=0;
		try(BufferedReader reader=getReader()){
			materials=new ArrayList<Material>();
			while((line=reader.readLine())!=null) {
				fieldValues=getFieldValues(line);
				m=createMaterial(fieldValues);
				materials.add(m);
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
	
	private Material createMaterial(String[] fields) {
		Material m=null;
		if(fields!=null) {
			m=new Material();
			m.setMaterialId(fields[0]);
		}
		return m;
	}
	
	private Material createJDMaterial(String[] fields) {
		Material m=null;
		if(fields!=null) {
			m=new Material();
			m.setMaterialId(fields[0]);
			m.setOldMaterialNumber(fields[1]);
			m.setType(fields[2]);
			m.setDescription(fields[3]);
			m.setGroup(fields[4]);
			m.setBaseUom(fields[5]);
			try {
				m.setGrossWeight(format.parse(fields[6]).doubleValue());
				m.setNetWeight(format.parse(fields[6]).doubleValue());
				m.setLength(format.parse(fields[7]).doubleValue());
				m.setWidth(format.parse(fields[8]).doubleValue());
				m.setHeight(format.parse(fields[9]).doubleValue());
			} 
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m.setCnCode(fields[10]);
			
		}
		return m;
	}
	
	private PlantData createPlantDataShort(String[] fields) {
		PlantData pd=null;
		if(fields!=null) {
			pd=new PlantData();
			pd.setPlant(fields[1]);
			//pd.setDoNotCost((fields[2].equals("X") ? true : false));
		}
		return pd;
	}
	
	private PlantData createPlantDataExperimental(String[] fields) {
		PlantData pd=null;
		if(fields!=null) {
			pd=new PlantData();
			pd.setPlant(fields[1]);
			pd.setProfitCenter(fields[2]);
		}
		return pd;
	}
	
	private PlantData createAcctCostData(String[] fields) {
		PlantData pd=null;
		if(fields!=null) {
			pd=new PlantData();
			pd.setPlant(fields[1]);
			
		}
		return pd;
	}
	
	private PlantData createPurchMRPData(String[] fields) {
		PlantData pd=null;
		if(fields!=null) {
			pd=new PlantData();
			pd.setPlant(fields[1]);
		}
		return pd;
	}

	
	

}
