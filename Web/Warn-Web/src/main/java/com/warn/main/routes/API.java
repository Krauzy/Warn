package com.warn.main.routes;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.warn.main.database.DBOrgan;
import com.warn.main.database.DBProblem;
import com.warn.main.database.DBUser;
import com.warn.main.database.DBWarning;
import com.warn.main.model.Organ;
import com.warn.main.model.Problem;
import com.warn.main.model.User;
import com.warn.main.model.Warning;
import com.warn.main.util.Session;

@RestController
@RequestMapping(value = "/API")
public class API {
	
	@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ObjectNode loginAPI(@RequestParam(value="email") String email, 
			@RequestParam(value="password") String pass) {
		User u = DBUser.checkLogin(email, pass);
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		if(u != null) {
			node.put("message", "OK");
			node.put("KEY", Session.KEY);
			node.put("ID", u.getId());
		}
		else node.put("message", "ERROR");
		
		return node;
	}
	
	@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public ObjectNode getUserAPI(@RequestParam(value = "id") String id, 
			@RequestParam(value = "token", required = false) String token) {
		User user = DBUser.get(Integer.parseInt(id));
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		if(user == null || token == null || !token.equals(Session.KEY))
			node.put("message", "ERROR");
		else {
			node.put("id", user.getId());
			node.put("name", user.getName());
			node.put("lastname", user.getLastname());
			node.put("cpf", user.getCpf());
			node.put("date", user.getDate().getYear() + "-" + user.getDate().getMonthValue() + "-" + user.getDate().getDayOfMonth());
			node.put("email", user.getEmail());
			node.put("password", user.getPassword());
			node.put("admin", user.isAdmin());
		}
		return node;
	}
	
	@RequestMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ObjectNode registerAPI(@RequestParam(value = "name") String name,
			@RequestParam(value = "lastname") String lastname, 
			@RequestParam(value = "cpf") String cpf,
			@RequestParam(value = "date") String date,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String pass,
			@RequestParam(value = "admin") String admin) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		LocalDate local = LocalDate.parse(date);
		boolean adm = admin.equals("true");
		User user = new User(name, lastname, cpf, local, email, pass, adm);
		if(DBUser.insert(user)) {
			user = DBUser.checkLogin(email, pass);
			node.put("message", "OK");
			node.put("KEY", Session.KEY);
			node.put("ID", user.getId());
		}
		else node.put("message", "ERROR");
		
		return node;
	}
	
	@RequestMapping(value = "/warning", produces = MediaType.APPLICATION_JSON_VALUE)
	public ObjectNode doWarningAPI(@RequestParam(value = "title") String title,
			@RequestParam(value = "description") String desc,
			@RequestParam(value = "level") String level,
			@RequestParam(value = "street") String street,
			@RequestParam(value = "district") String dist,
			@RequestParam(value = "number") String number,
			@RequestParam(value = "cep", required = false) String cep,
			@RequestParam(value = "city") String city,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "user") String userid,
			@RequestParam(value = "organ") String organid,
			@RequestParam(value = "type") String typeid,
			@RequestParam(value = "token") String token) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		User user = DBUser.get(Integer.parseInt(userid));
		Organ organ = DBOrgan.get(Integer.parseInt(organid));
		Problem type = DBProblem.get(Integer.parseInt(typeid));
		if(cep == null)
			cep = "";
		Warning war = new Warning(title, desc, Integer.parseInt(level), user, organ, type, 
				street, dist, Integer.parseInt(number), cep, city, state);
		if(token == null || !token.equals(Session.KEY) || !DBWarning.insert(war))
			node.put("message", "ERROR");
		else
			node.put("message", "OK");
		
		return node;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/organs/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ObjectNode getAllOrgans(@RequestParam(value = "token", required = false) String token) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		if(token == null || !token.equals(Session.KEY))
			node.put("message", "ERROR");
		else {
			List<Organ> organs = DBOrgan.get("");
			int i = 1;
			for(Organ o : organs) {
				ObjectNode obj = mapper.createObjectNode();
				obj.put("id", o.getId());
				obj.put("name", o.getName());
				obj.put("description", o.getDescription());
				node.put("" + i, obj);
				i++;
			}
		}
		return node;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/types/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ObjectNode getAllProblems(@RequestParam(value = "token", required = false) String token) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		if(token == null || !token.equals(Session.KEY))
			node.put("message", "ERROR");
		else {
			List<Problem> types = DBProblem.get("");
			int i = 1;
			for(Problem t  : types) {
				ObjectNode type = mapper.createObjectNode();
				type.put("id", t.getId());
				type.put("name", t.getName());
				type.put("description", t.getDescription());
				node.put("" + i, type);
				i++;
			}
		}
		return node;
	}
}
