package com.itbk.controller;

import com.itbk.constant.Constant;
import com.itbk.model.Role;
import com.itbk.model.Teacher;
import com.itbk.model.User;
import com.itbk.service.RoleService;
import com.itbk.service.TeacherService;
import com.itbk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashSet;

@Controller
@RequestMapping(value = {"/admin"})
public class AdminController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private TeacherService teacherService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String listUploadedFiles(Model model) throws IOException {
		return "/admin/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createGroup(@RequestParam("name") String name, @RequestParam("account") String account,
							  @RequestParam("password") String password, Model model) {
		Teacher teacher = new Teacher(name, account, password);
		teacherService.saveTeacher(teacher);
		if (userService.findByUserName("account") == null) {
			User user = new User();
			user.setUsername(account);
			user.setPassword(passwordEncoder.encode(password));
			HashSet<Role> roles = new HashSet<>();
			roles.add(roleService.findByName(Constant.RoleType.ROLE_TEACHER));
			user.setRoles(roles);
			userService.saveUser(user);
		}

		model.addAttribute("success", true);
		return "/admin/create";
	}
}