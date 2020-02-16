package org.rfc.sap;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class SapSystemFactory {
	
	public SapSystem getSapSystem(String systemName) throws JCoException {
		JCoDestination destination=JCoDestinationManager.getDestination(systemName);
		SapSystem sapSystem=new SapSystem(systemName,destination);
		return sapSystem;
	}
	
	public void saveSapSystem(SapSystem sapSystem) {
		Properties connProp=new Properties();
		connProp.setProperty(DestinationDataProvider.JCO_MSHOST, sapSystem.getMsHost());
		connProp.setProperty(DestinationDataProvider.JCO_MSSERV, sapSystem.getMsServ());
		connProp.setProperty(DestinationDataProvider.JCO_GROUP, sapSystem.getGroup());
		connProp.setProperty(DestinationDataProvider.JCO_CLIENT, sapSystem.getClient());
		connProp.setProperty(DestinationDataProvider.JCO_SAPROUTER, sapSystem.getRouter());
		connProp.setProperty(DestinationDataProvider.JCO_USER,   sapSystem.getUser());
		connProp.setProperty(DestinationDataProvider.JCO_PASSWD, sapSystem.getPassword());
		connProp.setProperty(DestinationDataProvider.JCO_LANG,   sapSystem.getLanguage());
		createDestinationDataFile(sapSystem.getName(),connProp);
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
	
}
