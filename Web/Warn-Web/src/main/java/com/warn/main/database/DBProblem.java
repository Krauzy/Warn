package com.warn.main.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.warn.main.model.Problem;

public class DBProblem {
	public static boolean insert(Problem prob) {
		Database db = new Database();
		String SQL = "INSERT INTO Problem (name, description) VALUE ('#name', '#desc')";
		SQL = SQL.replace("#name", prob.getName())
				.replace("#desc", prob.getDescription());
		boolean result = db.executeNonQuery(SQL);
		db.disconnect();
		return result;
	}
	
	public static boolean update(Problem prob) {
		Database db = new Database();
		String SQL = "UPDATE Problem SET name = '#name', description = '#desc' WHERE id = " + prob.getId();
		SQL = SQL.replace("#name", prob.getName())
				.replace("#desc", prob.getDescription());
		boolean result = db.executeNonQuery(SQL);
		db.disconnect();
		return result;
	}
	
	public static boolean delete(int id) {
		Database db = new Database();
		String SQL = "DELETE FROM Problem WHERE id = " + id;
		boolean result = db.executeNonQuery(SQL);
		db.disconnect();
		return result;
	}
	
	public static List<Problem> get(String filter) {
		List<Problem> problems = new ArrayList<Problem>();
		String SQL = "SELECT * FROM Problem";
		if(!filter.isEmpty())
			SQL += " WHERE " + filter;
		Database db = new Database();
		ResultSet rs = db.executeQuery(SQL);
		try {
			while(rs.next()) {
				problems.add(new Problem(rs.getInt("id"), 
						rs.getString("name"), 
						rs.getString("description")));
				
			}
		} 
		catch (SQLException e) {
			if(!db.getError().isEmpty())
				System.out.println(db.getError());
			e.printStackTrace();
		}
		return problems;
	}
	
	public static Problem get(int id) {
		Problem problems = null;
		String SQL = "SELECT * FROM Organ WHERE id = " + id;
		Database db = new Database();
		ResultSet rs = db.executeQuery(SQL);
		try {
			if(rs.next()) {
				problems = new Problem(rs.getInt("id"), 
						rs.getString("name"), 
						rs.getString("description"));
			}
		} 
		catch (SQLException e) {
			if(!db.getError().isEmpty())
				System.out.println(db.getError());
			e.printStackTrace();
		}
		return problems;
	}
}
