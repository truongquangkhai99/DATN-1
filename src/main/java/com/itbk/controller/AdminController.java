package com.itbk.controller;

import com.itbk.model.User;
import com.itbk.model.UserRole;
import com.itbk.service.UserRoleService;
import com.itbk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping(value = {"/admin"})
public class AdminController {

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String listUploadedFiles(Model model) throws IOException {
		return "/admin/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createGroup(@RequestParam("name") String name, @RequestParam("id") String id,
				@RequestParam("account") String account, @RequestParam("password") String password, Model model) {

		User user = new User(account, password, true);
		userService.saveUser(user);
		UserRole userRole = new UserRole(user, "ROLE_TEACHER");
		userRoleService.saveUserRole(userRole);

		model.addAttribute("success", true);
		return "/admin/create";
	}
}