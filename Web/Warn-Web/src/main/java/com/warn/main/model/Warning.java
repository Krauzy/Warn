package com.warn.main.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Date;

import javax.imageio.ImageIO;

public class Warning {
	private int id;
	private String title;
	private String description;
	private int level;
	private User user;
	private Organ organ;
	private Problem type;
	private BufferedImage photo;
	private Date date;
	private String street;
	private String district;
	private int number;
	private String cep;
	private String city;
	private String state;

	public Warning(int id, String title, String description, int level, User user, Organ organ, Problem type,
			String street, String district, int number, String cep, String city, String state) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.level = level;
		this.user = user;
		this.organ = organ;
		this.type = type;
		this.street = street;
		this.district = district;
		this.number = number;
		this.cep = cep;
		this.city = city;
		this.state = state;
		this.date = Date.from(Instant.now());
		this.photo = null;
	}
	
	public Warning() {
		this(0, "", "", 0, null, null, null, "", "", 0, "", "", "");
	}

	public int getId() {
		return id;		
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Organ getOrgan() {
		return organ;
	}
	
	public void setOrgan(Organ organ) {
		this.organ = organ;
	}
	
	public Problem getType() {
		return type;
	}
	
	public void setType(Problem type) {
		this.type = type;
	}
	
	public Image getPhoto() {
		return photo;
	}
	
	public void setPhoto(BufferedImage photo) {
		this.photo = photo;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	public void setPhotoFromFile(String filename) {
		try {
			this.photo = ImageIO.read(new File(filename));
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setPhotoFromArrayBytes(byte[] bytes) {
		try {
			InputStream in = new ByteArrayInputStream(bytes);
			this.photo = ImageIO.read(in);			
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public byte[] getPhotoArrayBytes() {
		byte[] bytes = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(this.photo, "jpg", out);
			out.flush();
			bytes = out.toByteArray();
			out.close();
			
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return bytes;
	}
}
