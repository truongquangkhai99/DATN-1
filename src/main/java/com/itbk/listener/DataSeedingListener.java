package com.itbk.listener;

import com.itbk.constant.Constant;
import com.itbk.model.Teacher;
import com.itbk.model.User;
import com.itbk.model.UserRole;
import com.itbk.service.TeacherService;
import com.itbk.service.UserRoleService;
import com.itbk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	TeacherService teacherService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		ArrayList<String> arrayList = userRoleService.findByRoleName(Constant.RoleType.ROLE_ADMIN);
		if (arrayList == null || arrayList.isEmpty()) {
			User user = new User("admin", "admin", true);
			userService.saveUser(user);
			UserRole userRole = new UserRole(user, Constant.RoleType.ROLE_ADMIN);
			userRoleService.saveUserRole(userRole);
		}

		if (arrayList == null || arrayList.isEmpty()) {
			User user = new User("vinh", "vinh", true);
			userService.saveUser(user);

			UserRole userRole = new UserRole(user, Constant.RoleType.ROLE_TEACHER);
			userRoleService.saveUserRole(userRole);

			Teacher teacher = new Teacher("Đỗ Đình Vĩnh", "vinh", "vinh");
			teacherService.saveTeacher(teacher);
		}
	}
}