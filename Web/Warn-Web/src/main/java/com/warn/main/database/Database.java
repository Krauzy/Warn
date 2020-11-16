package com.warn.main.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private Connection connect;
	private String error = "";
	/*private static Database instance;
	
	public static Database getInstance() {
		if(instance == null)
			instance = new Database();
		return instance;
	}*/
	
	public Database(String driver, String local, String base, String user, String pass) {
		try {
			Class.forName(driver);
			String URL = local + base;
			connect = DriverManager.getConnection(URL, user, pass);
		}
		catch(ClassNotFoundException cnfe) {
			error = "Failed to read JDBC Driver: " + cnfe.getMessage();
		}
		catch(SQLException sqle) {
			error = "Failed to connect in database: " + sqle.getMessage();
		}
		catch(Exception e) {
			error = "Error: " + e.getMessage();
		}
	}
	
	public Database() {
		this("org.postgresql.Driver", "jdbc:postgresql://tuffi.db.elephantsql.com/", 
				"npxpkvlj", "npxpkvlj", "rooD1TiIqniM-0UV3Tj5hdFoOMJ6Rtsi");
	}
	
	public String getError() {
        return error;
    }

    public boolean getState() {
        if (connect == null)
            return false;
        else
            return true;
    }
    
    public boolean executeNonQuery(String SQL) {
    	try {
            Statement statement = connect.createStatement();
            int result = statement.executeUpdate(SQL);
            statement.close();
            if (result >= 1)
                return true;
        } 
    	catch (SQLException sqle) {
            error = "Error: " + sqle.toString();
        }
        return false;
    }
    
    public ResultSet executeQuery(String SQL) {
    	ResultSet rs = null;
        try {
            Statement statement = connect.createStatement();
            rs = statement.executeQuery(SQL);
            statement.close();
        } 
        catch (SQLException sqle) {
            error = "Error: " + sqle.toString();
        }
        return rs;
    }
    
    public boolean disconnect() {
    	try {
    		connect.close();
    		return true;
    	}
    	catch(SQLException sqle) {
    		sqle.printStackTrace();
    		return false;
    	}
    }
    
    public boolean doRollback() {
    	try {
    		connect.rollback();
    		return true;
    	}
    	catch(SQLException sqle) {
    		sqle.printStackTrace();
    		return false;
    	}
    }
    
    public boolean doCommit() {
    	try {
    		connect.commit();
    		return true;
    	}
    	catch(SQLException sqle) {
    		sqle.printStackTrace();
    		return false;
    	}
    }
}
