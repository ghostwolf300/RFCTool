package org.rfc.dao.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.rfc.dao.PODAO;
import org.rfc.dto.PurchaseOrder;

public class TextFilePurchaseOrderDAO extends TextFileDAO implements PODAO<PurchaseOrder> {
	
	public TextFilePurchaseOrderDAO() {
		super();
	}
	
	public TextFilePurchaseOrderDAO(File dbFile) {
		super(dbFile);
	}
	
	public TextFilePurchaseOrderDAO(File dbFile,char delimiter,String encoding) {
		super(dbFile,delimiter,encoding);
	}
	
	
	@Override
	public List<PurchaseOrder> getPONumbers() {
		List<PurchaseOrder> poList=null;
		PurchaseOrder po=null;
		String line="";
		String[] fieldValues=null;
		try(BufferedReader reader=getReader()){
			poList=new ArrayList<PurchaseOrder>();
			while((line=reader.readLine())!=null) {
				fieldValues=getFieldValues(line);
				po=new PurchaseOrder(fieldValues[0]);
				poList.add(po);
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
		return poList;
	}

	@Override
	public void savePOs(List<PurchaseOrder> poList) {
		// TODO Auto-generated method stub
		
	}

}
