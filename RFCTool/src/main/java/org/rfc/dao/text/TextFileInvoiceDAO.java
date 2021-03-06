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

import org.rfc.dao.InvoiceDAO;
import org.rfc.dto.Invoice;
import org.rfc.dto.InvoiceItem;

public class TextFileInvoiceDAO extends TextFileDAO implements InvoiceDAO<Invoice> {
	
	private final NumberFormat format=NumberFormat.getInstance(Locale.FRANCE);
	
	public TextFileInvoiceDAO() {
		super();
	}
	
	public TextFileInvoiceDAO(File dbFile) {
		super(dbFile);
	}
	
	public TextFileInvoiceDAO(File dbFile,char delimiter,String encoding) {
		super(dbFile,delimiter,encoding);
	}
	
	
	@Override
	public List<Invoice> getInvoices() {
		List<Invoice> invoices=null;
		String currentInvoice=null;
		String nextInvoice=null;
		String line="";
		String[] fieldValues=null;
		Invoice invoice=null;
		InvoiceItem item=null;
		
		int rowCount=0;
		long itemNumber=1;
		try(BufferedReader reader=getReader()){
			invoices=new ArrayList<Invoice>();
			while((line=reader.readLine())!=null) {
				fieldValues=getFieldValues(line);
				nextInvoice=fieldValues[1];
				if(currentInvoice==null || !currentInvoice.equals(nextInvoice)) {
					invoice=new Invoice(nextInvoice);
					try {
						invoice.setTotalMerchandise(format.parse(fieldValues[2]).doubleValue());
						invoice.setSurcharge(format.parse(fieldValues[3]).doubleValue());
						invoice.setUnplannedDeliveryCost(format.parse(fieldValues[3]).doubleValue());
						invoice.setVatAmount(format.parse(fieldValues[6]).doubleValue());
						invoice.setGrossAmount(format.parse(fieldValues[5]).doubleValue());
					} 
					catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					invoices.add(invoice);
					currentInvoice=nextInvoice;
					itemNumber=1;
				}
				item=createInvoiceItem(fieldValues);
				item.setInvItemNumber(itemNumber);
				invoice.addItem(item);
				rowCount++;
				itemNumber++;
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
		return invoices;
	}
	
	private InvoiceItem createInvoiceItem(String[] fieldValues) {
		InvoiceItem item=new InvoiceItem();
		
		item.setInvoiceDoc(fieldValues[1]);
		//item.setInvItemNumber(Long.valueOf(fieldValues[7]));
		item.setPoNumber(fieldValues[13]);
		item.setPoItemNumber(Long.valueOf(fieldValues[14]));
		
		try {
			item.setInvItemAmount(format.parse(fieldValues[12]).doubleValue());
			item.setInvItemQty(format.parse(fieldValues[9]).doubleValue());
		} 
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String poUnit=fieldValues[15];
		if(poUnit.equals("PC")) {
			item.setPoUnit("ST");
		}
		else {
			item.setPoUnit(poUnit);
		}
		return item;
	}

}
