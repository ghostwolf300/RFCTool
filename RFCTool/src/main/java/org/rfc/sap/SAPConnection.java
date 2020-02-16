package org.rfc.sap;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.rt.JCoMiddleware.Connection;


public class SAPConnection {
	
	
	public boolean testConnection() {
		
		JCoDestination destination=null;
		String destinationName=null;
		Properties connProp=null;
		
		destinationName="TETCLNT280";
		
		connProp=new Properties();
		connProp.setProperty(DestinationDataProvider.JCO_MSHOST, "tet");
		connProp.setProperty(DestinationDataProvider.JCO_MSSERV, "3601");
		connProp.setProperty(DestinationDataProvider.JCO_GROUP, "TET");
		connProp.setProperty(DestinationDataProvider.JCO_CLIENT, "280");
		connProp.setProperty(DestinationDataProvider.JCO_SAPROUTER, "/H/tet");
		connProp.setProperty(DestinationDataProvider.JCO_USER,   "TKNSUSIVI");
		connProp.setProperty(DestinationDataProvider.JCO_PASSWD, "F4llt1me");
		connProp.setProperty(DestinationDataProvider.JCO_LANG,   "en");
		createDestinationDataFile(destinationName,connProp);
		
		destinationName="TEPCLNT280";
		
		connProp=new Properties();
		connProp.setProperty(DestinationDataProvider.JCO_MSHOST, "tep");
		connProp.setProperty(DestinationDataProvider.JCO_MSSERV, "3601");
		connProp.setProperty(DestinationDataProvider.JCO_GROUP, "TEP");
		connProp.setProperty(DestinationDataProvider.JCO_CLIENT, "280");
		connProp.setProperty(DestinationDataProvider.JCO_SAPROUTER, "/H/tep");
		connProp.setProperty(DestinationDataProvider.JCO_USER,   "TKNSUSIVI");
		connProp.setProperty(DestinationDataProvider.JCO_PASSWD, "F4llt1me");
		connProp.setProperty(DestinationDataProvider.JCO_LANG,   "en");
		createDestinationDataFile(destinationName,connProp);
		
		destinationName="TETCLNT280";
		
		try {
			destination=JCoDestinationManager.getDestination(destinationName);
			destination.ping();
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			JCoFunctionTemplate functionTemplate=destination.getRepository().getFunctionTemplate("BAPI_MATERIAL_SAVEREPLICA");
			JCoFunction function=functionTemplate.getFunction();
			JCoParameterList tables=function.getTableParameterList();
			JCoTable table=tables.getTable("HEADDATA");
			System.out.println(table.getFieldCount());	
			
			
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void createDestinationDataFile(String destinationName,Properties connProp) {
		 File destCfg = new File(destinationName+".jcoDestination");
	     try{
	    	 FileOutputStream fos = new FileOutputStream(destCfg, false);
	         connProp.store(fos, "for tests only !");
	         fos.close();
	     }
	     catch (Exception e){
	    	 throw new RuntimeException("Unable to create the destination files", e);
	     }
	}
	
	public void testConnection2() {
		JCoDestination destination=null;
		try {
			destination=JCoDestinationManager.getDestination("TETCLNT280");
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(destination.getDestinationName());
		System.out.println(destination.getApplicationServerHost());
		System.out.println(destination.getClient());
		System.out.println(destination.getMessageServerHost());
		System.out.println(destination.getMessageServerService());
		
		try {
			destination.ping();
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
