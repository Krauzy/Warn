package com.warn.main.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.warn.main.model.User;
import com.warn.main.util.Crypto;
import com.warn.main.util.Session;

public class DBUser {
	public static boolean insert(User u) {
		Database db = new Database();
		String SQL = "INSERT INTO User (name, lastname, cpf, date, email, password, admin)"
				+ " VALUES ('#name', '#lastname', '#cpf', '#date', '#email', '#password', #adm)";
		SQL = SQL.replace("#name", u.getName())
				.replace("#lastname", u.getLastname())
				.replace("#cpf", u.getCpf())
				.replace("#data", u.getDate().toString())
				.replace("#email", u.getEmail())
				.replace("#password", u.getPassword())
				.replace("#adm", u.isAdmin() + "");
		boolean result = db.executeNonQuery(SQL);
		db.disconnect();
		return result;
	}
	
	public static boolean update(User u) {
		Database db = new Database();
		String SQL = "UPDATE User SET name = '#name', lastname = '#lastname', cpf = '#cpf', "
				+ "date = '#date', email = '#email', password = '#password', admin = #adm "
				+ "WHERE id = " + u.getId();
		SQL = SQL.replace("#name", u.getName())
				.replace("#lastname", u.getLastname())
				.replace("#cpf", u.getCpf())
				.replace("#data", u.getDate().toString())
				.replace("#email", u.getEmail())
				.replace("#password", u.getPassword())
				.replace("#adm", u.isAdmin() + "");
		boolean result = db.executeNonQuery(SQL);
		db.disconnect();
		return result;
	}
	
	public static boolean delete(int id) {
		Database db = new Database();
		String SQL = "DELETE FROM User WHERE id = " + id;
		boolean result = db.executeNonQuery(SQL);
		db.disconnect();
		return result;
	}
	
	public static List<User> get(String filter) {
		List<User> users = new ArrayList<User>();
		String SQL = "SELECT * FROM User";
		if(!filter.isEmpty())
			SQL += " WHERE " + filter;
		Database db = new Database();
		ResultSet rs = db.executeQuery(SQL);
		try {
			while(rs.next()) {
				users.add(new User(rs.getInt("id"),
						rs.getString("name"),
						rs.getString("lastname"),
						rs.getString("cpf"),
						rs.getDate("date"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getBoolean("admin")));
			}
		}
		catch (SQLException e) {
			if(!db.getError().isEmpty())
				System.out.println(db.getError());
			e.printStackTrace();
		}
		return users;
	}
	
	public static User get(int id) {
		User user = null;
		String SQL = "SELECT * FROM User WHERE id = " + id;
		Database db = new Database();
		ResultSet rs = db.executeQuery(SQL);
		try {
			if(rs.next()) {
				user = new User(rs.getInt("id"),
						rs.getString("name"),
						rs.getString("lastname"),
						rs.getString("cpf"),
						rs.getDate("date"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getBoolean("admin"));
			}
		}
		catch (SQLException e) {
			if(!db.getError().isEmpty())
				System.out.println(db.getError());
			e.printStackTrace();
		}
		return user;
	}
	
	public static User checkLogin(String name, String pass) {
		User user = null;
		Crypto c = new Crypto(Session.KEY);
		c.encrypt(pass);
		String password = c.getEncrypted();
		String SQL = "SELECT * FROM User WHERE name = '" + name 
				+ "' AND password = '" + password + "'";
		Database db = new Database();
		ResultSet rs = db.executeQuery(SQL);
		try {
			if(rs.next()) {
				user = new User(rs.getInt("id"),
						rs.getString("name"),
						rs.getString("lastname"),
						rs.getString("cpf"),
						rs.getDate("date"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getBoolean("admin"));
			}
		}
		catch (SQLException e) {
			if(!db.getError().isEmpty())
				System.out.println(db.getError());
			e.printStackTrace();
		}
		return user;
	}
}
