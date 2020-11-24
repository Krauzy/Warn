package com.warn.main.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.warn.main.model.Organ;

public class DBOrgan {
	
	public static boolean insert(Organ organ) {
		Database db = Database.getInstance();
		String SQL = "INSERT INTO Organ (name, description) VALUES ('#name', '#desc')";
		SQL = SQL.replace("#name", organ.getName())
				.replace("#desc", organ.getDescription());
		boolean result = db.executeNonQuery(SQL);
		return result;
	}
	
	public static boolean update(Organ organ) {
		Database db = Database.getInstance();
		String SQL = "UPDATE Organ SET name = '#name', description = '#desc' WHERE id = " + organ.getId();
		SQL = SQL.replace("#name", organ.getName())
				.replace("#desc", organ.getDescription());
		boolean result = db.executeNonQuery(SQL);
		return result;
	}
	
	public static boolean delete(int id) {
		Database db = Database.getInstance();
		String SQL = "DELETE FROM Organ WHERE id = " + id;
		boolean result = db.executeNonQuery(SQL);
		return result;
	}
	
	public static List<Organ> get(String filter) {
		List<Organ> organs = new ArrayList<Organ>();
		String SQL = "SELECT * FROM Organ";
		if(!filter.isEmpty())
			SQL += " WHERE " + filter;
		SQL += " ORDER BY id";
		Database db = Database.getInstance();
		ResultSet rs = db.executeQuery(SQL);
		try {
			while(rs.next()) {
				organs.add(new Organ(rs.getInt("id"), 
						rs.getString("name"), 
						rs.getString("description")));
				
			}
		} 
		catch (SQLException e) {
			if(!db.getError().isEmpty())
				System.out.println(db.getError());
			e.printStackTrace();
		}
		return organs;
	}
	
	public static Organ get(int id) {
		Organ organ = null;
		String SQL = "SELECT * FROM Organ WHERE id = " + id;
		Database db = Database.getInstance();
		ResultSet rs = db.executeQuery(SQL);
		try {
			if(rs.next()) {
				organ = new Organ(rs.getInt("id"), 
						rs.getString("name"), 
						rs.getString("description"));
			}
		} 
		catch (SQLException e) {
			if(!db.getError().isEmpty())
				System.out.println(db.getError());
			e.printStackTrace();
		}
		return organ;
	}
}
