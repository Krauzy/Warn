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
		Database db = Database.getInstance();
		String SQL = "INSERT INTO public.\"user\" (name, lastname, cpf, date, email, password, admin)"
				+ " VALUES ('#name', '#lastname', '#cpf', '#date', '#email', '#password', '#adm')";
		SQL = SQL.replace("#name", u.getName())
				.replace("#lastname", u.getLastname())
				.replace("#cpf", u.getCpf())
				.replace("#date", u.getDate().getYear() + "-" + u.getDate().getMonthValue() + "-" + u.getDate().getDayOfMonth())
				.replace("#email", u.getEmail())
				.replace("#password", u.getPassword())
				.replace("#adm", u.isAdmin() + "");
		boolean result = db.executeNonQuery(SQL);
		return result;
	}
	
	public static boolean update(User u) {
		Database db = Database.getInstance();
		String SQL = "UPDATE public.\"user\" SET name = '#name', lastname = '#lastname', cpf = '#cpf', "
				+ "date = '#date', email = '#email', password = '#password', admin = '#adm' "
				+ "WHERE id = " + u.getId();
		SQL = SQL.replace("#name", u.getName())
				.replace("#lastname", u.getLastname())
				.replace("#cpf", u.getCpf())
				.replace("#date", u.getDate().toString())
				.replace("#email", u.getEmail())
				.replace("#password", u.getPassword())
				.replace("#adm", u.isAdmin() + "");
		boolean result = db.executeNonQuery(SQL);
		return result;
	}
	
	public static boolean delete(int id) {
		Database db = Database.getInstance();
		String SQL = "DELETE FROM public.\"user\" WHERE id = " + id;
		boolean result = db.executeNonQuery(SQL);
		return result;
	}
	
	public static List<User> get(String filter) {
		List<User> users = new ArrayList<User>();
		String SQL = "SELECT * FROM public.\"user\"";
		if(!filter.isEmpty())
			SQL += " WHERE " + filter;
		SQL += " ORDER BY id";
		System.out.println(SQL);
		Database db = Database.getInstance();
		ResultSet rs = db.executeQuery(SQL);
		try {
			while(rs.next()) {
				users.add(new User(rs.getInt("id"),
						rs.getString("name"),
						rs.getString("lastname"),
						rs.getString("cpf"),
						rs.getDate("date").toLocalDate(),
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
		String SQL = "SELECT * FROM public.\"user\" WHERE id = " + id;
		Database db = Database.getInstance();
		ResultSet rs = db.executeQuery(SQL);
		try {
			if(rs.next()) {
				user = new User(rs.getInt("id"),
						rs.getString("name"),
						rs.getString("lastname"),
						rs.getString("cpf"),
						rs.getDate("date").toLocalDate(),
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
	
	public static User checkLogin(String email, String pass) {
		User user = null;
		String SQL = "SELECT * FROM public.\"user\" WHERE email = '" + email 
				+ "' AND password = '" + pass + "'";
		Database db = Database.getInstance();
		ResultSet rs = db.executeQuery(SQL);
		try {
			if(rs != null && rs.next()) {
				user = new User(rs.getInt("id"),
						rs.getString("name"),
						rs.getString("lastname"),
						rs.getString("cpf"),
						rs.getDate("date").toLocalDate(),
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
