package com.warn.main.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.warn.main.model.Warning;

public class DBWarning {
	public static boolean insert(Warning w) {
		Database db = Database.getInstance();
		String SQL = "INSERT INTO Warning (title, description, level, date, "
				+ "street, district, number, city, state, user, organ, type) "
				+ "VALUES ('#title', '#desc', #level, '#date', '#street', "
				+ "'#district', #number, '#city', '#state', #user, #organ, #type)";
		SQL = SQL.replace("#title", w.getTitle())
				.replace("#desc", w.getDescription())
				.replace("#level", w.getLevel() + "")
				.replace("#date", w.getDate().getDayOfMonth() + "-" + w.getDate().getMonthValue() + "-" + w.getDate().getYear())
				.replace("#street", w.getStreet())
				.replace("#district", w.getDescription())
				.replace("#number", w.getNumber() + "")
				.replace("#city", w.getCity())
				.replace("#state", w.getState())
				.replace("#user", w.getUser().getId() + "")
				.replace("#organ", w.getOrgan().getId() + "")
				.replace("#type", w.getType().getId() + "");
		boolean result = db.executeNonQuery(SQL);
		return result;
	}
	
	public static boolean update(Warning w) {
		Database db = Database.getInstance();
		String SQL = "UPDATE Warning SET title = '#title', description = '#desc', level = #level, date = '#date', "
				+ "street = '#street', district = '#district', number = #number, city = '#city', state = '#state', "
				+ "\"user\" = #user, organ = #organ, type = #type WHERE id = " + w.getId();
		SQL = SQL.replace("#title", w.getTitle())
				.replace("#desc", w.getDescription())
				.replace("#level", w.getLevel() + "")
				.replace("#date", w.getDate().getYear() + "-" + w.getDate().getMonthValue() + "-" + w.getDate().getDayOfMonth())
				.replace("#street", w.getStreet())
				.replace("#district", w.getDescription())
				.replace("#number", w.getNumber() + "")
				.replace("#city", w.getCity())
				.replace("#state", w.getState())
				.replace("#user", w.getUser().getId() + "")
				.replace("#organ", w.getOrgan().getId() + "")
				.replace("#type", w.getType().getId() + "");
		System.out.println(SQL);
		boolean result = db.executeNonQuery(SQL);
		return result;
	}
	
	public static boolean delete(int id) {
		Database db = Database.getInstance();
		String SQL = "DELETE FROM Warning WHERE id = " + id;
		boolean result = db.executeNonQuery(SQL);
		return result;
	}
	
	public static Warning get(int id) {
		Warning warn = null;
		Database db = Database.getInstance();
		String SQL = "SELECT * FROM Warning WHERE id = " + id;
		ResultSet rs = db.executeQuery(SQL);
		try {
			if(rs != null && rs.next()) {
				warn = new Warning(rs.getInt("id"),
						rs.getString("title"),
						rs.getString("description"),
						rs.getInt("level"),
						DBUser.get(rs.getInt("user")),
						DBOrgan.get(rs.getInt("organ")),
						DBProblem.get(rs.getInt("type")),
						rs.getString("street"),
						rs.getString("district"),
						rs.getInt("number"),
						rs.getString("cep"),
						rs.getString("city"),
						rs.getString("state"),
						rs.getDate("date").toLocalDate());
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return warn;
	}
	
	public static List<Warning> get(String filter) {
		List<Warning> warns = new ArrayList<Warning>();
		Database db = Database.getInstance();
		String SQL = "SELECT * FROM Warning";
		if(!filter.isEmpty())
			SQL += " WHERE " + filter;
		SQL += " ORDER BY id";
		System.out.println(SQL);
		ResultSet rs = db.executeQuery(SQL);
		try {
			if(rs != null && rs.next()) {
				warns.add(new Warning(rs.getInt("id"),
						rs.getString("title"),
						rs.getString("description"),
						rs.getInt("level"),
						DBUser.get(rs.getInt("user")),
						DBOrgan.get(rs.getInt("organ")),
						DBProblem.get(rs.getInt("type")),
						rs.getString("street"),
						rs.getString("district"),
						rs.getInt("number"),
						rs.getString("cep"),
						rs.getString("city"),
						rs.getString("state"),
						rs.getDate("date").toLocalDate()));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return warns;
	}
}
