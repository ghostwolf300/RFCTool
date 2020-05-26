package org.rfc.dao.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.rfc.dao.PODAO;
import org.rfc.dto.POItem;
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
	public List<PurchaseOrder> getPOsIncludeItems() {
		List<PurchaseOrder> poList=null;
		PurchaseOrder po=null;
		POItem poItem=null;
		String currentPONumber="";
		String newPONumber="";
		
		String line="";
		String[] fieldValues=null;
		try(BufferedReader reader=getReader()){
			poList=new ArrayList<PurchaseOrder>();
			while((line=reader.readLine())!=null) {
				fieldValues=getFieldValues(line);
				newPONumber=fieldValues[0];
				if(!newPONumber.equals(currentPONumber)) {
					po=new PurchaseOrder(newPONumber);
					poList.add(po);
					currentPONumber=newPONumber;
				}
				poItem=new POItem();
				poItem.setPoNumber(po.getPoNumber());
				poItem.setItemNumber(Integer.parseInt(fieldValues[1]));
				po.addItem(poItem);
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
		FileWriter csvWriter=null;
		try {
			csvWriter=new FileWriter(dbFile);
			
			for(PurchaseOrder po : poList) {
				csvWriter.append(po.getPoNumber());
				csvWriter.append(getDelimiter());
				csvWriter.append(po.getDocumentType());
				csvWriter.append(getDelimiter());
				csvWriter.append(po.getCreatedOn().toString());
				csvWriter.append(getDelimiter());
				csvWriter.append(po.getCreatedBy());
				csvWriter.append(getDelimiter());
				csvWriter.append(String.valueOf(po.getItemInterval()));
				csvWriter.append(getDelimiter());
				csvWriter.append(String.valueOf(po.getLastItem()));
				csvWriter.append(getDelimiter());
				csvWriter.append(po.getVendor());
				csvWriter.append(getDelimiter());
				csvWriter.append(po.getPaymentTerm());
				csvWriter.append(getDelimiter());
				csvWriter.append(po.getPurchOrg());
				csvWriter.append(getDelimiter());
				csvWriter.append(po.getPurchGroup());
				csvWriter.append(getDelimiter());
				csvWriter.append(po.getCurrency());
				csvWriter.append(getDelimiter());
				csvWriter.append(po.getDocumentDate().toString());
				csvWriter.append("\n");
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				csvWriter.close();
			}
			catch(NullPointerException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void savePOItems(List<POItem> poItemList) {
		FileWriter csvWriter=null;
		try {
			csvWriter=new FileWriter(dbFile);
			
			for(POItem item : poItemList) {
				csvWriter.append(item.getPoNumber());
				csvWriter.append(getDelimiter());
				csvWriter.append(String.valueOf(item.getItemNumber()));
				csvWriter.append(getDelimiter());
				csvWriter.append(item.getDeleteIndicator());
				csvWriter.append(getDelimiter());
				csvWriter.append((item.getChangedOn()==null ? "" : item.getChangedOn().toString()));
				csvWriter.append(getDelimiter());
				csvWriter.append(item.getShortText());
				csvWriter.append(getDelimiter());
				csvWriter.append(item.getMaterial());
				csvWriter.append(getDelimiter());
				csvWriter.append(item.getPurchMaterial());
				csvWriter.append(getDelimiter());
				csvWriter.append(item.getInfoRecord());
				csvWriter.append(getDelimiter());
				csvWriter.append(item.getVendorMaterial());
				csvWriter.append(getDelimiter());
				csvWriter.append(String.valueOf(item.getQuantity()));
				csvWriter.append(getDelimiter());
				csvWriter.append(item.getUnit());
				csvWriter.append(getDelimiter());
				csvWriter.append(item.getOrderPriceUnit());
				csvWriter.append(getDelimiter());
				csvWriter.append(String.valueOf(item.getNetPrice()));
				csvWriter.append(getDelimiter());
				csvWriter.append(item.getTaxCode());
				csvWriter.append(getDelimiter());
				csvWriter.append(item.getCustomer());
				csvWriter.append("\n");
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				csvWriter.close();
			}
			catch(NullPointerException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
