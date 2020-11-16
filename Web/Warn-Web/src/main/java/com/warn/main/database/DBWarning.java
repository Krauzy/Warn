package com.warn.main.database;

import com.warn.main.model.Warning;

public class DBWarning {
	public static boolean insert(Warning w) {
		Database db = new Database();
		String SQL = "INSERT INTO Warning (title, description, level, date, "
				+ "street, district, number, city, state, user, organ, type) "
				+ "VALUES ('#title', '#desc', #level, '#date', '#street', "
				+ "'#district', #number, '#city', '#state', #user, #organ, #type)";
		SQL = SQL.replace("#title", w.getTitle())
				.replace("#desc", w.getDescription())
				.replace("#level", w.getLevel() + "")
				.replace("#data", w.getDate().toString())
				.replace("#street", w.getStreet())
				.replace("#district", w.getDescription())
				.replace("#number", w.getNumber() + "")
				.replace("#city", w.getCity())
				.replace("#state", w.getState())
				.replace("#user", w.getUser().getId() + "")
				.replace("#organ", w.getOrgan().getId() + "")
				.replace("#type", w.getType().getId() + "");
		boolean result = db.executeNonQuery(SQL);
		db.disconnect();
		return result;
	}
}
