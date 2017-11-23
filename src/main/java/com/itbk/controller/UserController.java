package com.itbk.controller;

import com.itbk.service.StudentService;
import com.itbk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

	@Autowired
	StudentService studentService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	// Controller for Home page
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String homePage(Model model) {
		return "home";
	}

	// Controller for Admin
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model) {
		return "redirect:/admin/info";
	}

	// Controller for teacher
	@RequestMapping(value = "/teacher", method = RequestMethod.GET)
	public String teacherPage(Model model) {
		return "redirect:/teacher/info";
	}

	// Controller for student
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	public String studentPage(Model model) {
		String userName = getUserName();
		if(userName != null ) {
			Object isTested = studentService.findIsTestedByUsername(getUserName());
			if((Object)isTested != null) {
				model.addAttribute("isTested", (boolean)isTested);
			}
		}

		return "/student/student";
	}

	// Controller for the login
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {

		return "login";
	}

	// Controller for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesssDeniedPage(Model model) {
		// check if user is logining
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);
			model.addAttribute("username", userDetail.getUsername());
		}

		return "403";
	}

	public String getUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = null;
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		}

		return userName;
	}
}