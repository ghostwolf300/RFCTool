package org.rfc.dao.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AccessDAO {
	
	public static final String JDBC="jdbc:ucanaccess://";
	
	protected String dbPath=null;
	protected Connection connection=null;
	
	public AccessDAO() {
		super();
	}
	
	public AccessDAO(String dbPath) {
		super();
		this.dbPath=dbPath;
	}

	public String getDbPath() {
		return dbPath;
	}

	public void setDbPath(String dbPath) {
		this.dbPath = dbPath;
	}
	
	protected void openConnection() throws SQLException {
		String dbUrl=JDBC+dbPath;
		connection=DriverManager.getConnection(dbUrl);
	}
	
	protected void closeConnection() throws SQLException {
		if(connection!=null) {
			connection.close();
		}
	}
	
}
