package com.warn.main.routes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.warn.main.database.DBOrgan;
import com.warn.main.database.DBProblem;
import com.warn.main.database.DBUser;
import com.warn.main.database.DBWarning;
import com.warn.main.model.Organ;
import com.warn.main.model.Problem;
import com.warn.main.model.User;
import com.warn.main.model.Warning;
import com.warn.main.util.Session;

@Controller
public class Routes {
	
	@GetMapping("/")
	public String home() {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public Object login(HttpServletRequest request) {
		String email = "";
		String pass = "";
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie c : cookies) {
				if(c.getName().equals("warnemail"))
					email = c.getValue();
				if(c.getName().equals("warnpass"))
					pass = c.getValue();
			}
		}
		Session.login = DBUser.checkLogin(email, pass);
		if(Session.login != null) {
			Session.login_error = false;
			return "redirect:/dashboard/organs";
		}
		Session.login_error = false;
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("error", Session.login_error);
		return mav;
	}
	
	@PostMapping("/login")
	public String postLogin(HttpServletResponse response, @RequestParam(value = "email") String email, 
			@RequestParam(value = "pass") String pass,
			@RequestParam(value = "remember", required = false) String check) {
		User user = DBUser.checkLogin(email, pass);
		if(user != null) {
			Session.login = user;
			if(check != null) {
				Cookie cokemail = new Cookie("warnemail", email);
				response.addCookie(cokemail);
				Cookie cokpass = new Cookie("warnpass", pass);
				response.addCookie(cokpass);
			}			
			return "redirect:/dashboard/organs";
		}
		else {
			Session.login = null;
			Session.login_error = true;
			return "redirect:/login";
		}
	}
	
	@GetMapping("/dashboard")
	public String redirDash() {
		return "redirect:/dashboard/organs";
	}
	
	@GetMapping("/dashboard/organs")
	public Object loadOrganTemplate() {
		if(Session.login == null)
			return "redirect:/login";
		List<Organ> organs = DBOrgan.get(Session.organ_filter);
		ModelAndView mav = new ModelAndView("organs");
		
		mav.addObject("username", Session.login.getName());
		mav.addObject("organs", organs);
		mav.addObject("error", Session.organ_error);
		return mav;
	}
	
	@PostMapping("/dashboard/organs/new")
	public String addOrgan(@RequestParam(value = "orgname") String name, @RequestParam(value = "orgdesc") String desc) {
		Organ organ = new Organ(name, desc);
		Session.organ_error = !DBOrgan.insert(organ);
		return "redirect:/dashboard/organs";		
	}
	
	@PostMapping("/dashboard/organs/update")
	public String updateOrgan(@RequestParam(value = "uorgname") String name, 
			@RequestParam(value = "uorgdesc") String desc,
			@RequestParam(value = "uid") String id) {
		int index = Integer.parseInt(id);
		Organ o = DBOrgan.get(index);
		o.setName(name);
		o.setDescription(desc);
		Session.organ_error = !DBOrgan.update(o);
		return "redirect:/dashboard/organs";
	}
	
	@PostMapping("/dashboard/organs/delete")
	public String deleteOrgan(@RequestParam(value = "delid") String id) {
		int index = Integer.parseInt(id);
		Session.organ_error = !DBOrgan.delete(index);
		return "redirect:/dashboard/organs";
	}
	
	@PostMapping("/dashboard/organs/search")
	public String getOrgans(@RequestParam(value = "search", required = false) String filter) {
		if(filter != null) {
			Session.organ_filter = "name LIKE '%" + filter + "%' OR description LIKE '%" +  filter + "%'";
		}
		else
			Session.organ_filter = "";
		return "redirect:/dashboard/organs";
	}
	
	//
	
	@GetMapping("/dashboard/problems")
	public Object loadProblemTemplate() {
		if(Session.login == null)
			return "redirect:/login";
		else {
			List<Problem> problems = DBProblem.get(Session.problem_filter);
			ModelAndView mav = new ModelAndView("problems");
			mav.addObject("problems", problems);
			mav.addObject("error", Session.problem_error);
			mav.addObject("username", Session.login.getName());
			return mav;
		}
	}
	
	@PostMapping("/dashboard/problems/new")
	public String addProblem(@RequestParam(value = "probname") String name, @RequestParam(value = "probdesc") String desc) {
		Problem p = new Problem(name, desc);
		Session.problem_error = !DBProblem.insert(p);
		return "redirect:/dashboard/problems";
	}
	
	@PostMapping("/dashboard/problems/update")
	public String updateProblem(@RequestParam(value = "uprobname") String name, 
			@RequestParam(value = "uprobdesc") String desc,
			@RequestParam(value = "uid") String id) {
		int index = Integer.parseInt(id);
		Problem p = DBProblem.get(index);
		p.setName(name);
		p.setDescription(desc);
		Session.problem_error = !DBProblem.update(p);
		return "redirect:/dashboard/problems";
	}
	
	@PostMapping("/dashboard/problems/delete")
	public String deleteProblem(@RequestParam(value = "delid") String id) {
		int index = Integer.parseInt(id);
		Session.problem_error = !DBProblem.delete(index);
		return "redirect:/dashboard/problems";
	}
	
	@PostMapping("/dashboard/problems/search")
	public String getProblems(@RequestParam(value = "search", required = false) String filter) {
		if(filter != null) {
			Session.problem_filter = "name LIKE '%" + filter + "%' OR description LIKE '%" +  filter + "%'";
		}
		else
			Session.problem_filter = "";
		return "redirect:/dashboard/problems";
	}
	
	//
	
	@GetMapping("/dashboard/users")
	public Object loadUsersTemplate() {
		if(Session.login == null)
			return "redirect:/login";
		else {
			List<User> users = DBUser.get(Session.user_filter);
			ModelAndView mav = new ModelAndView("users");
			mav.addObject("error", Session.user_error);
			mav.addObject("username", Session.login.getName());
			mav.addObject("users", users);
			return mav;
		}
	}
	
	@PostMapping("/dashboard/users/new")
	public String addUser(@RequestParam(value = "username") String name,
			@RequestParam(value = "userlastname") String lastname,
			@RequestParam(value = "usercpf") String cpf,
			@RequestParam(value = "userdate") String date,
			@RequestParam(value = "usermail") String email,
			@RequestParam(value = "userpass") String pass,
			@RequestParam(value = "userretrypass") String retry,
			@RequestParam(value = "useradmin", required = false) String admin) {
		if(!retry.equals(pass)) {
			Session.user_error = true;
		}
		else {
			boolean adm = (admin != null);
			User user = new User(name, lastname, cpf, LocalDate.parse(date), email, pass);
			Session.user_error = !DBUser.insert(user);
		}		
		return "redirect:/dashboard/users";
	}
	
	@PostMapping("/dashboard/users/update")
	public String updateUser(@RequestParam(value = "uusername") String name,
			@RequestParam(value = "uuserlastname") String lastname,
			@RequestParam(value = "uusercpf") String cpf,
			@RequestParam(value = "uuserdate") String date,
			@RequestParam(value = "uusermail") String email,
			@RequestParam(value = "uuserpass") String pass,
			@RequestParam(value = "uuserretrypass") String retry,
			@RequestParam(value = "uid") String id,
			@RequestParam(value = "uuseradmin", required = false) String admin) {
		if(!retry.equals(pass)) {
			Session.user_error = true;
		}
		else {
			int index = Integer.parseInt(id);
			User user = DBUser.get(index);
			user.setName(name);
			user.setLastname(lastname);
			user.setCpf(cpf);
			user.setDate(LocalDate.parse(date));
			user.setEmail(email);
			user.setPassword(pass);
			user.setAdmin(admin != null);
			Session.user_error = !DBUser.update(user);
		}		
		return "redirect:/dashboard/users";
	}
	
	@PostMapping("/dashboard/users/delete")
	public String deleteUser(@RequestParam(value = "delid") String id) {
		int index = Integer.parseInt(id);
		Session.user_error = !DBUser.delete(index);
		return "redirect:/dashboard/users";
	}
	
	@PostMapping("/dashboard/users/search")
	public String getUsers(@RequestParam(value = "search", required = false) String filter) {
		if(filter != null) {
			Session.user_filter = "name LIKE '%" + filter + "%' "
					+ "OR lastname LIKE '%" +  filter + "%' "
					+ "OR cpf LIKE '%" +  filter + "%' "
					+ "OR email LIKE '%" +  filter + "%'";
		}
		else
			Session.user_filter = "";
		return "redirect:/dashboard/users";
	}
	
	@GetMapping("/dashboard/users/search")
	public String getUsersWithGET(@RequestParam(value = "search", required = false) String filter) {
		if(filter != null) {
			Session.user_filter = "name LIKE '%" + filter + "%' "
					+ "OR lastname LIKE '%" +  filter + "%' "
					+ "OR cpf LIKE '%" +  filter + "%' "
					+ "OR email LIKE '%" +  filter + "%'";
		}
		else
			Session.user_filter = "";
		return "redirect:/dashboard/users";
	}
	
	//
	
	@GetMapping("/dashboard/warnings")
	public Object loadWarningTemplate(@RequestParam(value="time", required = false) String time) {
		if(Session.login == null)
			return "redirect:/login";
		else {
			List<Organ> organs = DBOrgan.get("");
			List<User> users = DBUser.get("");
			List<Problem> problems = DBProblem.get("");
			String str = "";
			String flagtime = "all";
			LocalDate local = LocalDate.now();
			if(time != null && time.equals("today")) {
				String today = local.getYear() + "-" + local.getMonthValue() + "-" + local.getDayOfMonth();
				str = "date = '" + today + "'";
				flagtime = time;
			}
			if(time != null && time.equals("week")) {
				while(local.getDayOfWeek() != DayOfWeek.SUNDAY)
					local = local.minusDays(1);
					
				String week = local.getYear() + "-" + local.getMonthValue() + "-" + local.getDayOfMonth();
				str = "date >= '" + week + "'";
				flagtime = time;
			}
			if(time != null && time.equals("mounth")) {
				while(local.getDayOfMonth() != 1)
					local = local.minusDays(1);
				String mounth = local.getYear() + "-" + local.getMonthValue() + "-" + local.getDayOfMonth();
				str = "date >= '" + mounth + "'";
				flagtime = time;
			}
			if(!Session.warning_filter.isEmpty())
				str = " " + str;
			List<Warning> warnings = DBWarning.get(Session.warning_filter + str);
			ModelAndView mav = new ModelAndView("warning");
			mav.addObject("users", users);
			mav.addObject("organs", organs);
			mav.addObject("problems", problems);
			mav.addObject("warnings", warnings);
			mav.addObject("error", Session.warning_error);
			mav.addObject("username", Session.login.getName());
			mav.addObject("flag", flagtime);
			return mav;
		}
	}
	
	@PostMapping("/dashboard/warnings/update")
	public String updateWarning(@RequestParam(value="wtitle") String title, 
			@RequestParam(value="wdesc") String desc,
			@RequestParam(value="wcep", required = false) String cep,
			@RequestParam(value="wstate") String state,
			@RequestParam(value="wcity") String city,
			@RequestParam(value="wdist") String district,
			@RequestParam(value="wstreet") String street,
			@RequestParam(value="wnumber") String number,
			@RequestParam(value="worgan") String organ,
			@RequestParam(value="wprob") String type,
			@RequestParam(value="wuser") String user,
			@RequestParam(value="wdate") String date,
			@RequestParam(value="wlevel") String level,
			@RequestParam(value="uid") String id) {
		LocalDate dt = LocalDate.parse(date);
		int index = Integer.parseInt(id);
		Organ o = DBOrgan.get("name = '" + organ + "'").get(0);
		Problem typ = DBProblem.get("name = '" + type + "'").get(0);
		User u = DBUser.get("name = '" + user + "'").get(0);
		Warning w = DBWarning.get(index);
		w.setTitle(title);
		w.setDescription(desc);
		if(cep != null)
			w.setCep(cep);
		w.setState(state);
		w.setCity(city);
		w.setStreet(street);
		w.setDistrict(district);
		w.setNumber(Integer.parseInt(number));
		w.setOrgan(o);
		w.setType(typ);
		w.setUser(u);
		w.setDate(dt);
		w.setLevel(Integer.parseInt(level));
		Session.warning_error = !DBWarning.update(w);
		return "redirect:/dashboard/warnings";
	}
	
	@PostMapping("/dashboard/warnings/delete")
	public String deleteWarning(@RequestParam(value="delid") String id) {
		int index = Integer.parseInt(id);
		Session.warning_error = !DBWarning.delete(index);
		return "dashboard/warnings";
	}
}
