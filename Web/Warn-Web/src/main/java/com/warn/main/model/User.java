package com.warn.main.model;

import java.time.Instant;
import java.util.Date;

import com.warn.main.util.Crypto;
import com.warn.main.util.Session;

public class User {
	private int id;
	private String name;
	private String lastname;
	private String cpf;
	private Date date;
	private String email;
	private String password;
	private boolean admin;
	
	public User(int id, String name, String lastname, String cpf, Date date, String email, String password, boolean admin) {
		//super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.cpf = cpf;
		this.date = date;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}
	
	public User(String name, String lastname, String cpf, Date date, String email, String password, boolean admin) {
		//super();
		this.id = 0;
		this.name = name;
		this.lastname = lastname;
		this.cpf = cpf;
		this.date = date;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}
	
	public User(int id, String name, String lastname, String cpf, Date date, String email, String password) {
		//super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.cpf = cpf;
		this.date = date;
		this.email = email;
		this.password = password;
		this.admin = false;
	}
	
	public User(String name, String lastname, String cpf, Date date, String email, String password) {
		//super();
		this.id = 0;
		this.name = name;
		this.lastname = lastname;
		this.cpf = cpf;
		this.date = date;
		this.email = email;
		this.password = password;
		this.admin = false;
	}
	
	public User() {
		this("", "", "", Date.from(Instant.now()), "", "");
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public void encrypt() {
		Crypto crypt = new Crypto(Session.KEY);
		crypt.encrypt(this.password);
		this.password = crypt.getEncrypted();
	}
	
	public void decrypt() {
		Crypto crypt = new Crypto(Session.KEY);
		crypt.decrypt(this.password);
		this.password = crypt.getDecrypted();
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
