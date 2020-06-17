package org.rfc.dao.text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.rfc.dao.JDInvoiceDAO;
import org.rfc.dto.JDInvoice;
import org.rfc.dto.JDInvoiceAmount;
import org.rfc.dto.JDInvoiceItem;

public class TextFileJDInvoiceDAO extends TextFileDAO implements JDInvoiceDAO {
	
	public TextFileJDInvoiceDAO() {
		super();
	}
	
	public TextFileJDInvoiceDAO(File dbFile) {
		super(dbFile);
	}
	
	public TextFileJDInvoiceDAO(File dbFile,char delimiter,String encoding) {
		super(dbFile,delimiter,encoding);
	}
	
	@Override
	public JDInvoice getInvoice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveInvoiceHeader(List<JDInvoice> invoices) {
		FileWriter csvWriter=null;
		try {
			csvWriter=new FileWriter(dbFile);
			for(JDInvoice inv : invoices) {
				csvWriter.append(inv.getInvoiceNo());
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
	public void saveInvoiceItems(List<JDInvoiceItem> items) {
		FileWriter csvWriter=null;
		try {
			csvWriter=new FileWriter(dbFile);
			for(JDInvoiceItem item : items) {
				csvWriter.append(item.getInvoiceNo());
				csvWriter.append(delimiter);
				csvWriter.append(String.valueOf(item.getItemNo()));
				csvWriter.append(delimiter);
				csvWriter.append(item.getOrderNo());
				csvWriter.append(delimiter);
				csvWriter.append(item.getPartNo());
				csvWriter.append(delimiter);
				csvWriter.append(String.valueOf(item.getShippedQty()));
				csvWriter.append(delimiter);
				csvWriter.append(String.valueOf(item.getOrderQty()));
				csvWriter.append(delimiter);
				csvWriter.append(String.valueOf(item.getUnitPrice()));
				csvWriter.append(delimiter);
				csvWriter.append(String.valueOf(item.getExtendedPrice()));
				csvWriter.append(delimiter);
				csvWriter.append(item.getVat());
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
	public void saveInvoiceAmounts(List<JDInvoiceAmount> amounts) {
		FileWriter csvWriter=null;
		try {
			csvWriter=new FileWriter(dbFile);
			for(JDInvoiceAmount amount : amounts) {
				csvWriter.append(amount.getInvoiceNo());
				csvWriter.append(delimiter);
				csvWriter.append(amount.getText());
				csvWriter.append(delimiter);
				csvWriter.append(String.valueOf(amount.getAmount()));
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
