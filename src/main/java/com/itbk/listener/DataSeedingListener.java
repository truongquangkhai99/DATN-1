package com.itbk.listener;

import com.itbk.constant.Constant;
import com.itbk.model.Role;
import com.itbk.model.User;
import com.itbk.service.RoleService;
import com.itbk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// Roles
		if (roleService.findByName(Constant.RoleType.ROLE_ADMIN) == null) {
			roleService.saveRole(new Role(Constant.RoleType.ROLE_ADMIN));
		}

		if (roleService.findByName(Constant.RoleType.ROLE_TEACHER) == null) {
			roleService.saveRole(new Role(Constant.RoleType.ROLE_TEACHER));
		}

		if (roleService.findByName(Constant.RoleType.ROLE_STUDENT) == null) {
			roleService.saveRole(new Role(Constant.RoleType.ROLE_STUDENT));
		}

		// Admin account
		if (userService.findByUserName("admin") == null) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("admin"));
			HashSet<Role> roles = new HashSet<>();
			roles.add(roleService.findByName(Constant.RoleType.ROLE_ADMIN));
			admin.setRoles(roles);
			userService.saveUser(admin);
		}
	}
}