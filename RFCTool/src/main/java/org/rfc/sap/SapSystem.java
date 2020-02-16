package org.rfc.sap;

import java.util.Properties;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;

public class SapSystem {
	
	private String name;
	private String msHost;
	private String msServ;
	private String group;
	private String client;
	private String router;
	private String user;
	private String password;
	private String language;
	private JCoDestination destination;
	
	public SapSystem() {
		
	}
	
	public SapSystem(String name,JCoDestination destination) {
		this.name=name;
		this.destination=destination;
		this.msHost=destination.getMessageServerHost();
		this.msServ=destination.getMessageServerService();
		this.group=destination.getLogonGroup();
		this.client=destination.getClient();
		this.router=destination.getSAPRouterString();
		this.user=destination.getUser();
		this.password="";
		this.language=destination.getLanguage();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsHost() {
		return msHost;
	}

	public void setMsHost(String msHost) {
		this.msHost = msHost;
	}

	public String getMsServ() {
		return msServ;
	}

	public void setMsServ(String msServ) {
		this.msServ = msServ;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getRouter() {
		return router;
	}

	public void setRouter(String router) {
		this.router = router;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public JCoDestination getDestination() {
		return destination;
	}

	public void setDestination(JCoDestination destination) {
		this.destination = destination;
	}
	
	public boolean ping() {
		boolean success=true;
		try {
			destination.ping();
		} 
		catch (JCoException e) {
			success=false;
			e.printStackTrace();
		}
		return success;
	}
	
}
