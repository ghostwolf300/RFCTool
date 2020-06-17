package org.rfc.dao.xml;

import java.io.File;
import java.util.List;

import org.rfc.dao.JDInvoiceDAO;
import org.rfc.dto.Invoice;
import org.rfc.dto.JDInvoice;
import org.rfc.dto.JDInvoiceAmount;
import org.rfc.dto.JDInvoiceItem;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLJDInvoiceDAO extends XMLDAO implements JDInvoiceDAO {
	
	public XMLJDInvoiceDAO() {
		super();
	}
	
	public XMLJDInvoiceDAO(File dbFile) {
		super(dbFile);
	}
	
	@Override
	public JDInvoice getInvoice() {
		JDInvoice invoice=new JDInvoice();
		
		Element eCommercialInvoice=(Element)doc.getElementsByTagName("commercialInvoice").item(0);
		Element eInvoiceNo=(Element)eCommercialInvoice.getElementsByTagName("invoiceNo").item(0);
		invoice.setInvoiceNo(eInvoiceNo.getTextContent().replaceAll("\\s",""));
		System.out.println(invoice.getInvoiceNo());
		NodeList amountNodes=eCommercialInvoice.getElementsByTagName("invoiceAmountDetails");
		JDInvoiceAmount amount=null;
		
		for(int a=0;a<amountNodes.getLength();a++) {
			Element eInvoiceAmountDetails=(Element) amountNodes.item(a);
			String txt=eInvoiceAmountDetails.getElementsByTagName("invoiceText").item(0).getTextContent();
			double amt=0.0;
			try {
				amt=Double.valueOf(eInvoiceAmountDetails.getElementsByTagName("invoiceValue").item(0).getTextContent());
			}
			catch(NumberFormatException nfe) {
				txt=txt+" - NFE";
			}
			amount=new JDInvoiceAmount(invoice.getInvoiceNo(),txt,amt);
			invoice.addInvoiceAmount(amount);
			
		}
		
		NodeList itemNodes=eCommercialInvoice.getElementsByTagName("invoiceItem");
		JDInvoiceItem item=null;
		long itemNo=1;
		for(int i=0;i<itemNodes.getLength();i++) {
			Element eInvoiceItem=(Element) itemNodes.item(i);
			String orderNo=eInvoiceItem.getElementsByTagName("totalsForOrder").item(0).getTextContent();
			NodeList lineNodes=eInvoiceItem.getElementsByTagName("invoiceLineItem");
			for(int l=0;l<lineNodes.getLength();l++) {
				Element eInvoiceLineItem=(Element)lineNodes.item(l);
				item=new JDInvoiceItem();
				item.setInvoiceNo(invoice.getInvoiceNo());
				item.setOrderNo(orderNo);
				item.setItemNo(itemNo);
				item.setPartNo(eInvoiceLineItem.getElementsByTagName("partNo").item(0).getTextContent());
				item.setDescription(eInvoiceLineItem.getElementsByTagName("description").item(0).getTextContent());
				try {
					item.setShippedQty(Double.valueOf(eInvoiceLineItem.getElementsByTagName("shipQuantity").item(0).getTextContent()));
					item.setOrderQty(Double.valueOf(eInvoiceLineItem.getElementsByTagName("orderQuantity").item(0).getTextContent()));
					item.setUnitPrice(Double.valueOf(eInvoiceLineItem.getElementsByTagName("unitPrice").item(0).getTextContent()));
					item.setExtendedPrice(Double.valueOf(eInvoiceLineItem.getElementsByTagName("extendedPrice").item(0).getTextContent()));
				}
				catch(NumberFormatException nfe) {
					System.out.println(invoice.getInvoiceNo()+"\tNumberFormatException");
				}
				item.setVat(eInvoiceLineItem.getElementsByTagName("vat").item(0).getTextContent());
				invoice.addInvoiceItem(item);
				itemNo++;
			}
		}
		
		return invoice;
	}

	@Override
	public void saveInvoiceHeader(List<JDInvoice> invoices) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveInvoiceItems(List<JDInvoiceItem> items) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveInvoiceAmounts(List<JDInvoiceAmount> amounts) {
		// TODO Auto-generated method stub
		
	}

}
