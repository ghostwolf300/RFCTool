package org.rfc.sap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;

import org.rfc.dao.DAOFactory;
import org.rfc.dao.InvoiceDAO;
import org.rfc.dao.JDInvoiceDAO;
import org.rfc.dao.MaterialDAO;
import org.rfc.dao.PODAO;
import org.rfc.dao.text.TextFileDAOFactory;
import org.rfc.dao.xml.XMLDAOFactory;
import org.rfc.dto.FieldValue;
import org.rfc.dto.InputField;
import org.rfc.dto.Invoice;
import org.rfc.dto.InvoiceItem;
import org.rfc.dto.JDInvoice;
import org.rfc.dto.JDInvoiceAmount;
import org.rfc.dto.JDInvoiceItem;
import org.rfc.dto.PlantData;
import org.rfc.dto.PurchaseOrder;
import org.rfc.dto.Material;
import org.rfc.dto.POItem;
import org.rfc.dto.UserFunction;
import org.rfc.dto.ReturnMessage;
import org.rfc.dto.Worker;
import org.rfc.function.ChangePlantData;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class RFCMain {
	
	private SAPConnection sap;
	
	public RFCMain() {
		sap=new SAPConnection();
	}
	
	public static void main(String[] args) {
		RFCMain main=new RFCMain();
		//main.testXMLReader();
		main.changeIncomingInvoice();
		//main.changeIncomingInvoiceStatus();
		//main.releaseIncomingInvoice();
		//main.postIncomingInvoice();
		//main.changePOItem();
		//main.fetchPOData();
		//main.ExecuteTest();
		//main.ExecuteThreadTest();
		//main.daoTest();
		//main.reflectionTest();

	}
	
	public void reflectionTest() {
		UserFunction<ChangePlantData> f=new UserFunction<ChangePlantData>(ChangePlantData.class,1);
		Map<String,InputField<?>> fieldMap=f.getFieldMap();
		InputField<Double> mappedField=(InputField<Double>) fieldMap.get("PLND_DELRY");
		System.out.println("PLND_DELRY mapped field: "+mappedField.getMappedColumn());
		List<Material> materials=new ArrayList<Material>();
		materials.add(new Material());
		materials.add(new Material());
		Worker w=f.createWorker(1, materials, null, true);
		
	}
	
	public void daoTest() {
		
		String dbPath="C:/Users/ville.susi/OneDrive - Wihuri/Tiedostot/Digital Development/Projects/Ostolaskusuma/BAPI/PO_ITEMS.csv";
		
		DAOFactory daoFactory=new TextFileDAOFactory(new File(dbPath));
		InvoiceDAO<Invoice> dao=daoFactory.getInvoiceDAO();
		List<Invoice> invoices=dao.getInvoices();
		
		System.out.println("Invoices size: "+invoices.size());
		
		for(Invoice invoice : invoices) {
			for(InvoiceItem item : invoice.getItems()) {
				System.out.println(item.toString());
			}
		}
		
	}
	
	private List<List<Material>> slice(List<Material> materials, int maxRows){
		List<List<Material>> slicedList=new ArrayList<List<Material>>();
		List<Material> slice=new ArrayList<Material>();
		int counter=0;
		for(Material m : materials) {
			if(counter % maxRows==0 && counter>0) {
				slicedList.add(slice);
				slice=new ArrayList<Material>();
			}
			slice.add(m);
			counter++;
		}
		if(slice.size()>0) {
			slicedList.add(slice);
		}
		return slicedList;
	}
	
	public void ExecuteThreadTest() {
		
		SapSystemFactory factory=new SapSystemFactory();
		SapSystem sap=null;
		try {
			sap = factory.getSapSystem("TETCLNT280");
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ChangePlantData f1=null;//new ChangePlantData(1,createTestMaterials1(),sap.getDestination());
		ChangePlantData f2=null;//new ChangePlantData(2,createTestMaterials2(),sap.getDestination());
		
		f1.setTestRun(false);
		f2.setTestRun(false);
		
		Thread t1=new Thread(f1);
		Thread t2=new Thread(f2);
		t1.start();
		t2.start();
		
		while(t1.isAlive() || t2.isAlive()) {
			System.out.println("F1: "+f1.getProgress());
			System.out.println("F2: "+f2.getProgress());
			System.out.println("Thread(s) running... Sleeping 5s");
			try {
				Thread.sleep(5000);
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Ready!");
		System.out.println("F1: "+f1.getProgress());
		System.out.println("F2: "+f2.getProgress());
		
		for(ReturnMessage msg : ChangePlantData.getReturnMessages()) {
			System.out.println(msg);
		}
		
		
	}
	
	public void ExecuteTest() {
		
		SapSystemFactory factory=new SapSystemFactory();
		SapSystem sap=null;
		JCoDestination destination=null;
		JCoFunctionTemplate template=null;
		JCoFunction function=null;
		JCoFunction commitFunction;
		JCoRepository repository=null;
		JCoTable tRETURN=null;
		
		try {
			sap=factory.getSapSystem("TETCLNT280");
			
			System.out.println(sap.getName());
			System.out.println(sap.getMsHost());
			System.out.println(sap.getMsServ());
			System.out.println(sap.getGroup());
			System.out.println(sap.getRouter());
			System.out.println(sap.getClient());
			System.out.println("Ping succesful: "+sap.ping());
			
			destination=sap.getDestination();
			
			JCoContext.begin(destination);
			
			repository=destination.getRepository();
			template=repository.getFunctionTemplate("BAPI_OBJCL_CREATE");
			function=template.getFunction();
			template=repository.getFunctionTemplate("BAPI_TRANSACTION_COMMIT");
			commitFunction=template.getFunction();
			
			String material="JDW0050025204";
			
			function.getImportParameterList().setValue("OBJECTKEYNEW", material);
			function.getImportParameterList().setValue("OBJECTTABLENEW","MARA");
			function.getImportParameterList().setValue("CLASSNUMNEW", "ECOMMERCE");
			function.getImportParameterList().setValue("CLASSTYPENEW", "001");
			tRETURN=function.getTableParameterList().getTable("RETURN");
			
			function.execute(destination);
			commitFunction.execute(destination);
			
			do {
				System.out.println(tRETURN.getValue("TYPE")+" "+tRETURN.getValue("ID")+" "+tRETURN.getValue("NUMBER")+" "+tRETURN.getValue("MESSAGE"));
			}
			while(tRETURN.nextRow());
			
			
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				JCoContext.end(destination);
			} 
			catch (JCoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void fetchPOData() {
		SapSystemFactory factory=new SapSystemFactory();
		SapSystem sap=null;
		JCoDestination destination=null;
		JCoFunctionTemplate template=null;
		JCoFunction function=null;
		JCoRepository repository=null;
		JCoStructure sPO_HEADER=null;
		JCoTable tPO_ITEMS=null;
		
		//List<String> poNumbers=getPONumbers("C:/Database/Ostolaskusuma/inv_po_list.csv");
		List<String> poNumbers=getPONumbers("C:/Database/Ostolaskusuma/jd_po_list.csv");
		List<String> invalidPONumbers=new ArrayList<String>();
		List<PurchaseOrder> poList=new ArrayList<PurchaseOrder>();
		
		try {
			sap=factory.getSapSystem("TEPCLNT280");
			System.out.println("Ping succesful: "+sap.ping());
			
			destination=sap.getDestination();
			
			JCoContext.begin(destination);
			
			repository=destination.getRepository();
			template=repository.getFunctionTemplate("BAPI_PO_GETDETAIL");
			function=template.getFunction();
			
			//String poNumber="4500021264";
			for(String poNumber : poNumbers) {
				function.getImportParameterList().setValue("PURCHASEORDER", poNumber);
				function.getImportParameterList().setValue("ITEMS","X");
				
				sPO_HEADER=function.getExportParameterList().getStructure("PO_HEADER");
				tPO_ITEMS=function.getTableParameterList().getTable("PO_ITEMS");
				
				System.out.println("PO: "+poNumber);
				
				function.execute(destination);
				
				PurchaseOrder po=createPO(sPO_HEADER);
				if(po!=null) {
					po.setItems(createPOItemList(tPO_ITEMS));
					poList.add(po);
				}
				else {
					invalidPONumbers.add(poNumber);
				}
				
//				System.out.println("PO number: "+po.getPoNumber()+"\tItems: "+po.getItems().size());
//				for(POItem item : po.getItems()) {
//					System.out.println("Item: "+item.getItemNumber());
//				}
			}
			
			
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				JCoContext.end(destination);
			} 
			catch (JCoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		savePOList(poList,"C:/Database/Ostolaskusuma/inv_po_out.csv");
		
		List<POItem> poItemList=new ArrayList<POItem>();
		for(PurchaseOrder po : poList) {
			poItemList.addAll(po.getItems());
		}
		
		savePOItemList(poItemList,"C:/Database/Ostolaskusuma/inv_po_item_out.csv");
		
		System.out.println("Invalid PO number:");
		for(String poNumber : invalidPONumbers) {
			System.out.println(poNumber);
		}
	}
	
	private List<String> getPONumbers(String filePath){
		List<String> poNumbers=new ArrayList<String>();
		DAOFactory factory=new TextFileDAOFactory(new File(filePath));
		PODAO<PurchaseOrder> dao=factory.getPODAO();
		List<PurchaseOrder> poList=dao.getPONumbers();
		for(PurchaseOrder po : poList) {
			poNumbers.add(po.getPoNumber());
		}
		return poNumbers;
	}
	
	private List<PurchaseOrder> getPOsIncludeItems(String filePath){
		DAOFactory factory=new TextFileDAOFactory(new File(filePath));
		PODAO<PurchaseOrder> dao=factory.getPODAO();
		List<PurchaseOrder> poList=dao.getPOsIncludeItems();
		return poList;
	}
	
	private void savePOList(List<PurchaseOrder> poList, String filePath) {
		DAOFactory factory=new TextFileDAOFactory(new File(filePath));
		PODAO<PurchaseOrder> dao=factory.getPODAO();
		dao.savePOs(poList);
	}
	
	private void savePOItemList(List<POItem> poItemList,String filePath) {
		DAOFactory factory=new TextFileDAOFactory(new File(filePath));
		PODAO<PurchaseOrder> dao=factory.getPODAO();
		dao.savePOItems(poItemList);
	}
	
	private PurchaseOrder createPO(JCoStructure hdrStruct) {
		
		PurchaseOrder po=null;
		String poNumber=null;
		poNumber=hdrStruct.getString("PO_NUMBER");
		
		if(!poNumber.isEmpty()) {
			po=new PurchaseOrder();
			po.setPoNumber(poNumber);
			po.setCreatedOn(new Date(hdrStruct.getDate("CREATED_ON").getTime()));
			po.setCreatedBy(hdrStruct.getString("CREATED_BY"));
			po.setDocumentType(hdrStruct.getString("DOC_TYPE"));
			po.setItemInterval(hdrStruct.getLong("ITEM_INTVL"));
			po.setLastItem(hdrStruct.getLong("LAST_ITEM"));
			po.setVendor(hdrStruct.getString("VENDOR"));
			po.setPaymentTerm(hdrStruct.getString("PMNTTRMS"));
			po.setPurchOrg(hdrStruct.getString("PURCH_ORG"));
			po.setPurchGroup(hdrStruct.getString("PUR_GROUP"));
			po.setCurrency(hdrStruct.getString("CURRENCY"));
			po.setDocumentDate(new Date(hdrStruct.getDate("DOC_DATE").getTime()));
		}
		return po;
	}
	
	private List<POItem> createPOItemList(JCoTable itemTable){
		POItem poItem=null;
		List<POItem> poItems=new ArrayList<POItem>();
		if(itemTable.getNumRows()>0) {
			do {
				poItem=new POItem();
				poItem.setPoNumber(itemTable.getString("PO_NUMBER"));
				poItem.setItemNumber(itemTable.getInt("PO_ITEM"));
				poItem.setDeleteIndicator(itemTable.getString("DELETE_IND"));
				try {
					poItem.setChangedOn(new Date(itemTable.getDate("CHANGED_ON").getTime()));
				}
				catch(NullPointerException npe) {
					System.out.println("PO: "+poItem.getPoNumber()+" CHANGED_ON date is null");
				}
				poItem.setShortText(itemTable.getString("SHORT_TEXT"));
				poItem.setMaterial(itemTable.getString("MATERIAL"));
				poItem.setPurchMaterial(itemTable.getString("PUR_MAT"));
				poItem.setInfoRecord(itemTable.getString("INFO_REC"));
				poItem.setVendorMaterial(itemTable.getString("VEND_MAT"));
				poItem.setQuantity(itemTable.getDouble("QUANTITY"));
				poItem.setUnit(itemTable.getString("UNIT"));
				poItem.setOrderPriceUnit(itemTable.getString("ORDERPR_UN"));
				poItem.setNetPrice(itemTable.getDouble("NET_PRICE"));
				poItem.setTaxCode(itemTable.getString("TAX_CODE"));
				poItem.setCustomer(itemTable.getString("CUSTOMER"));
				poItems.add(poItem);
			}
			while(itemTable.nextRow());
		}
		return poItems;
	}
	
	private void changeIncomingInvoice() {
		
		SapSystemFactory factory=new SapSystemFactory();
		SapSystem sap=null;
		JCoDestination destination=null;
		JCoFunctionTemplate template=null;
		JCoFunction function=null;
		JCoFunction commitFunction=null;
		JCoRepository repository=null;
		String fiscalYear="2020";
		
		String dbPath="C:/Users/ville.susi/OneDrive - Wihuri/Tiedostot/Digital Development/Projects/Ostolaskusuma/BAPI/INVOICE_LINES_TEST_2.csv";
		List<Invoice> invoices=getInvoicesWithItems(dbPath);
		
		for(Invoice inv : invoices) {
			for(InvoiceItem itm : inv.getItems()) {
				System.out.println(inv.getInvoiceDoc()+"\t"+itm.getInvItemNumber()+"\t"+itm.getInvItemQty()+"\t"+itm.getInvItemAmount());
			}
		}
		
		JCoStructure sHEADERDATA_CHANGE=null;
		JCoStructure sHEADERDATA_CHANGEX=null;
		JCoStructure sTABLE_CHANGE=null;
		JCoTable tRETURN=null;
		JCoTable tITEMDATA=null;
		
		try {
			
			//pointing to production!
			sap=factory.getSapSystem("TEPCLNT280");
			System.out.println("Ping succesful: "+sap.ping());

			destination=sap.getDestination();

			JCoContext.begin(destination);

			repository=destination.getRepository();
			template=repository.getFunctionTemplate("BAPI_INCOMINGINVOICE_CHANGE");
			function=template.getFunction();
			template=repository.getFunctionTemplate("BAPI_TRANSACTION_COMMIT");
			commitFunction=template.getFunction();

			sHEADERDATA_CHANGE=function.getImportParameterList().getStructure("HEADERDATA_CHANGE");
			sHEADERDATA_CHANGEX=function.getImportParameterList().getStructure("HEADERDATA_CHANGEX");
			sTABLE_CHANGE=function.getImportParameterList().getStructure("TABLE_CHANGE");
			tITEMDATA=function.getTableParameterList().getTable("ITEMDATA");
			tRETURN=function.getTableParameterList().getTable("RETURN");
			
			boolean approved=false;
			
			for(Invoice invoice : invoices) {
				
				function.getImportParameterList().setValue("INVOICEDOCNUMBER", invoice.getInvoiceDoc());
				function.getImportParameterList().setValue("FISCALYEAR", fiscalYear);
				
				System.out.println("TotalMerchandise:\t"+invoice.getTotalMerchandise()+"\tItems:\t"+invoice.getItemAmountSum());
				
				approved=false;
				if(invoice.canApprove()) {
					System.out.println(invoice.getInvoiceDoc()+"\tCan approve");
					function.getImportParameterList().setValue("INVOICE_DOC_STATUS", "B");
					approved=true;
				}
				else {
					System.out.println(invoice.getInvoiceDoc()+"\tCheck values!");
				}
				
				sHEADERDATA_CHANGE.setValue("DEL_COSTS", invoice.getUnplannedDeliveryCost());
				sHEADERDATA_CHANGEX.setValue("DEL_COSTS", "X");
				
				sTABLE_CHANGE.setValue("ITEMDATA", "X");
				
				List<InvoiceItem> items=invoice.getItems();
				
				for(InvoiceItem item : items) {
					tITEMDATA.appendRow();
					tITEMDATA.setValue("INVOICE_DOC_ITEM", item.getInvItemNumber());
					tITEMDATA.setValue("ITEM_AMOUNT", item.getInvItemAmount());
					tITEMDATA.setValue("QUANTITY", item.getInvItemQty());
					tITEMDATA.setValue("PO_NUMBER", item.getPoNumber());
					tITEMDATA.setValue("PO_ITEM", item.getPoItemNumber());
					tITEMDATA.setValue("PO_UNIT", item.getPoUnit());
					//If Gr based invoice=invoice linked to PO with delivery. Must have goods receipt!
					if(item.isGrBased()) {
						tITEMDATA.setValue("REF_DOC", item.getGoodsReceipt());
						tITEMDATA.setValue("REF_DOC_YEAR", "2020");
						tITEMDATA.setValue("REF_DOC_IT", item.getRefItem());
					}
				}
				
				//function.execute(destination);
				//commitFunction.execute(destination);
				
				if(!tRETURN.isEmpty()) {
					do {
						System.out.println(tRETURN.getValue("TYPE")+" "+tRETURN.getValue("ID")+" "+tRETURN.getValue("NUMBER")+" "+tRETURN.getValue("MESSAGE"));
					}
					while(tRETURN.nextRow());
				}
				else {
					System.out.println("Invoice "+invoice.getInvoiceDoc()+" : success");
					if(approved) {
						invoice.setApproved(true);
					}
				}
				
				tRETURN.clear();
				tITEMDATA.clear();
				
			}
			
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				JCoContext.end(destination);
			} 
			catch (JCoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private List<InvoiceItem> getTestInvoiceItems(){
		List<InvoiceItem> items=new ArrayList<InvoiceItem>();
		String poNumber="4500014293";
		String goodsReceipt="5000023640";
		items.add(new InvoiceItem(1,155.0,100.0,poNumber,10,"ST"));
		items.add(new InvoiceItem(2,69.5,1.0,poNumber,20,"ST",goodsReceipt,1));
		//items.add(new InvoiceItem(3,300.0,3.0,poNumber,30,"PAA",goodsReceipt,3));
		return items;
	}
	
	private List<Invoice> getInvoicesWithItems(String dbPath){
		DAOFactory factory=new TextFileDAOFactory(new File(dbPath));
		InvoiceDAO<Invoice> dao=factory.getInvoiceDAO();
		List<Invoice> invoices=dao.getInvoices();
		return invoices;
	}
	
	private void changeIncomingInvoiceStatus() {
		SapSystemFactory factory=new SapSystemFactory();
		SapSystem sap=null;
		JCoDestination destination=null;
		JCoFunctionTemplate template=null;
		JCoFunction function=null;
		JCoFunction commitFunction=null;
		JCoRepository repository=null;
		
		String fiscalYear="2020";
		String invoice="5100033261";
		
		JCoTable tRETURN=null;
		
		try {
			
			sap=factory.getSapSystem("TEPCLNT280");
			System.out.println("Ping succesful: "+sap.ping());

			destination=sap.getDestination();

			JCoContext.begin(destination);

			repository=destination.getRepository();
			template=repository.getFunctionTemplate("BAPI_INCOMINGINVOICE_CHANGE");
			function=template.getFunction();
			template=repository.getFunctionTemplate("BAPI_TRANSACTION_COMMIT");
			commitFunction=template.getFunction();

			tRETURN=function.getTableParameterList().getTable("RETURN");
				
			function.getImportParameterList().setValue("INVOICEDOCNUMBER", invoice);
			function.getImportParameterList().setValue("FISCALYEAR", fiscalYear);
			//B=parked and completed
			function.getImportParameterList().setValue("INVOICE_DOC_STATUS", "B");

			function.execute(destination);
			commitFunction.execute(destination);

			if(!tRETURN.isEmpty()) {
				do {
					System.out.println(tRETURN.getValue("TYPE")+" "+tRETURN.getValue("ID")+" "+tRETURN.getValue("NUMBER")+" "+tRETURN.getValue("MESSAGE"));
				}
				while(tRETURN.nextRow());
			}
			else {
				System.out.println("Invoice "+invoice+" status changed");
			}
			
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				JCoContext.end(destination);
			} 
			catch (JCoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void postIncomingInvoice() {
		SapSystemFactory factory=new SapSystemFactory();
		SapSystem sap=null;
		JCoDestination destination=null;
		JCoFunctionTemplate template=null;
		JCoFunction releaseFunc=null;
		JCoFunction postFunc=null;
		JCoFunction commitFunction=null;
		JCoRepository repository=null;
		
		String invoiceDoc="5100033261";
		String fiscalYear="2020";
		
		JCoTable tRETURN=null;
		
		try {
			
			sap=factory.getSapSystem("TEPCLNT280");
			System.out.println("Ping succesful: "+sap.ping());

			destination=sap.getDestination();

			JCoContext.begin(destination);

			repository=destination.getRepository();
			template=repository.getFunctionTemplate("BAPI_INCOMINGINVOICE_RELEASE");
			releaseFunc=template.getFunction();
			template=repository.getFunctionTemplate("BAPI_INCOMINGINVOICE_POST");
			postFunc=template.getFunction();
			template=repository.getFunctionTemplate("BAPI_TRANSACTION_COMMIT");
			commitFunction=template.getFunction();

			tRETURN=postFunc.getTableParameterList().getTable("RETURN");
				
			//releaseFunc.getImportParameterList().setValue("INVOICEDOCNUMBER", invoiceDoc);
			//releaseFunc.getImportParameterList().setValue("FISCALYEAR", fiscalYear);
			
			postFunc.getImportParameterList().setValue("INVOICEDOCNUMBER", invoiceDoc);
			postFunc.getImportParameterList().setValue("FISCALYEAR", fiscalYear);
			
			//releaseFunc.execute(destination);
			postFunc.execute(destination);
			commitFunction.execute(destination);
			
			if(!tRETURN.isEmpty()) {
				do {
					System.out.println(invoiceDoc+" "+tRETURN.getValue("TYPE")+" "+tRETURN.getValue("ID")+" "+tRETURN.getValue("NUMBER")+" "+tRETURN.getValue("MESSAGE"));
				}
				while(tRETURN.nextRow());
			}
			else {
				System.out.println("Invoice\t"+invoiceDoc+"\tno messages");
			}
			
			
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				JCoContext.end(destination);
			} 
			catch (JCoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void releaseIncomingInvoice() {
		SapSystemFactory factory=new SapSystemFactory();
		SapSystem sap=null;
		JCoDestination destination=null;
		JCoFunctionTemplate template=null;
		JCoFunction releaseFunc=null;
		JCoFunction commitFunction=null;
		JCoRepository repository=null;
		
		String invoiceDoc="5100033261";
		String fiscalYear="2020";
		
		JCoTable tRETURN=null;
		
		try {
			
			sap=factory.getSapSystem("TEPCLNT280");
			System.out.println("Ping succesful: "+sap.ping());

			destination=sap.getDestination();

			JCoContext.begin(destination);

			repository=destination.getRepository();
			template=repository.getFunctionTemplate("BAPI_INCOMINGINVOICE_RELEASE");
			releaseFunc=template.getFunction();
			template=repository.getFunctionTemplate("BAPI_TRANSACTION_COMMIT");
			commitFunction=template.getFunction();

			tRETURN=releaseFunc.getTableParameterList().getTable("RETURN");
				
			//releaseFunc.getImportParameterList().setValue("INVOICEDOCNUMBER", invoiceDoc);
			//releaseFunc.getImportParameterList().setValue("FISCALYEAR", fiscalYear);
			
			releaseFunc.getImportParameterList().setValue("INVOICEDOCNUMBER", invoiceDoc);
			releaseFunc.getImportParameterList().setValue("FISCALYEAR", fiscalYear);
			
			//releaseFunc.execute(destination);
			releaseFunc.execute(destination);
			commitFunction.execute(destination);
			
			if(!tRETURN.isEmpty()) {
				do {
					System.out.println(invoiceDoc+" "+tRETURN.getValue("TYPE")+" "+tRETURN.getValue("ID")+" "+tRETURN.getValue("NUMBER")+" "+tRETURN.getValue("MESSAGE"));
				}
				while(tRETURN.nextRow());
			}
			else {
				System.out.println("Invoice\t"+invoiceDoc+"\tno messages");
			}
			
			
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				JCoContext.end(destination);
			} 
			catch (JCoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void changePOItem() {
		SapSystemFactory factory=new SapSystemFactory();
		SapSystem sap=null;
		JCoDestination destination=null;
		JCoFunctionTemplate template=null;
		JCoFunction function=null;
		JCoFunction commitFunction=null;
		JCoRepository repository=null;
		
		boolean testRun=false;
		
		long progressCount=0;
		
		List<PurchaseOrder> poList=getPOsIncludeItems("C:/Database/Ostolaskusuma/jd_po_items_186005798_2.csv");
		
		JCoTable tPOITEM=null;
		JCoTable tPOITEMX=null;
		JCoTable tRETURN=null;
		
		try {
			sap=factory.getSapSystem("TEPCLNT280");
			System.out.println("Ping succesful: "+sap.ping());
			
			destination=sap.getDestination();
			
			JCoContext.begin(destination);
			
			repository=destination.getRepository();
			template=repository.getFunctionTemplate("BAPI_PO_CHANGE");
			function=template.getFunction();
			template=repository.getFunctionTemplate("BAPI_TRANSACTION_COMMIT");
			commitFunction=template.getFunction();
			
			tPOITEM=function.getTableParameterList().getTable("POITEM");
			tPOITEMX=function.getTableParameterList().getTable("POITEMX");
			tRETURN=function.getTableParameterList().getTable("RETURN");
			
			function.getImportParameterList().setValue("TESTRUN", (testRun==true ? "X" : ""));
			
			for(PurchaseOrder po : poList) {
				function.getImportParameterList().setValue("PURCHASEORDER", po.getPoNumber());
				for(POItem item : po.getItems()) {
					tPOITEM.appendRow();
					tPOITEM.setValue("PO_ITEM", item.getItemNumber());
					tPOITEM.setValue("TAX_CODE", "S1");
					
					tPOITEMX.appendRow();
					tPOITEMX.setValue("PO_ITEM", item.getItemNumber());
					tPOITEMX.setValue("TAX_CODE", "X");
					
					//System.out.println(item.getPoNumber()+"\t"+item.getItemNumber());
				}
				
				function.execute(destination);
				commitFunction.execute(destination);
				
				if(!tRETURN.isEmpty()) {
					do {
						if(tRETURN.getValue("TYPE").equals("E")) {
							System.out.println(po.getPoNumber()+" "+tRETURN.getValue("TYPE")+" "+tRETURN.getValue("ID")+" "+tRETURN.getValue("NUMBER")+" "+tRETURN.getValue("MESSAGE"));
						}
					}
					while(tRETURN.nextRow());
				}
				else {
					System.out.println("No messages PO:\t"+po.getPoNumber());
				}
				
				tPOITEM.clear();
				tPOITEMX.clear();
				
				progressCount++;
				
				if(progressCount % 10 == 0) {
					System.out.println("Progress: "+progressCount+" / " +poList.size());
				}
				
			}
			
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				JCoContext.end(destination);
			} 
			catch (JCoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private List<String> getJDInvoiceFileNames(){
		String folder="C:/Users/ville.susi/OneDrive - Wihuri/Tiedostot/Digital Development/Projects/Ostolaskusuma/OpusCapita/JD ostolaskut/attachments/MM";
		List<String> fileNames=null;
		try(Stream<Path> paths=Files.walk(Paths.get(folder))){
			fileNames=paths.filter(Files::isRegularFile)
					.map(x -> x.toString()).collect(Collectors.toList());
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//for(String fileName : fileNames) {
		//	System.out.println(fileName);
		//}
		return fileNames;
	}
	
	public void testXMLReader() {
		
		XMLDAOFactory factory=new XMLDAOFactory();
		JDInvoiceDAO dao=null;
		File dbFile=null;
		JDInvoice invoice=null;
		List<JDInvoice> invoices=new ArrayList<JDInvoice>();
		
		List<String> fileNames=getJDInvoiceFileNames();
		System.out.println(fileNames.size());
		
		for(String fileName : fileNames) {
			System.out.println(fileName);
			dbFile=new File(fileName);
			factory.setDbFile(dbFile);
			dao=factory.getJDInvoiceDAO();
			invoice=dao.getInvoice();
			invoices.add(invoice);
		}
		
		List<JDInvoiceItem> items=new ArrayList<JDInvoiceItem>();
		List<JDInvoiceAmount> amounts=new ArrayList<JDInvoiceAmount>();
		
		for(JDInvoice inv : invoices) {
			amounts.addAll(inv.getInvoiceAmounts());
			items.addAll(inv.getInvoiceItems());
		}
		
		for(JDInvoiceItem item : items) {
			System.out.println(item.getInvoiceNo()+"\t"+item.getOrderNo()+"\t"+item.getItemNo()+"\t"+item.getPartNo()+"\t"+item.getShippedQty()+"\t"+item.getUnitPrice()+"\t"+item.getExtendedPrice());
		}
		
		String linesOut="C:/Users/ville.susi/OneDrive - Wihuri/Tiedostot/Digital Development/Projects/Ostolaskusuma/OpusCapita/JD ostolaskut/InvoiceLines.csv";
		String amountOut="C:/Users/ville.susi/OneDrive - Wihuri/Tiedostot/Digital Development/Projects/Ostolaskusuma/OpusCapita/JD ostolaskut/InvoiceAmounts.csv";
		
		TextFileDAOFactory f=new TextFileDAOFactory(new File(linesOut));
		JDInvoiceDAO daoOut=f.getJDInvoiceDAO();
		daoOut.saveInvoiceItems(items);
		f.setDbFile(new File(amountOut));
		daoOut=f.getJDInvoiceDAO();
		daoOut.saveInvoiceAmounts(amounts);
		
	}

}
