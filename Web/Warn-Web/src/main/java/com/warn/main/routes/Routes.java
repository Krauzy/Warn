package com.warn.main.routes;

//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.warn.main.model.User;
import com.warn.main.util.Session;

@Controller
public class Routes {
	
	@GetMapping("/")
	public ModelAndView home(HttpServletResponse response) {
		//Cookie cookie = new Cookie("login", "userlogin");
		//response.addCookie(cookie);
		if(Session.login == null)
			Session.login = new User();
		ModelAndView mav = new ModelAndView("home");
		mav.addObject("logged", Session.login);
		return mav;
	}
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}
	
	@PostMapping("/login")
	public String doLogin(@RequestParam(value="lemail") String lemail, 
			@RequestParam(value="lpass") String lpass, 
			@RequestParam(value="lcheck", required = false) String lcheck) {
		System.out.println(lemail + " " + lpass + " " + lcheck);
		return "redirect:/login";
	}
	
	@GetMapping("/warning")
	public ModelAndView warning() {
		ModelAndView mav = new ModelAndView("warning");
		return mav;
	}
}
