package com.itbk.controller;

import com.itbk.constant.Constant;
import com.itbk.model.*;
import com.itbk.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.*;

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

	@Autowired
	private GroupService groupService;

	@Autowired
	private StudentService studentService;

	@Autowired
	QuestionService questionService;

	@Autowired
	AnswerService answerService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String listUploadedFiles(Model model) throws IOException {
		return "/admin/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createGroup(@RequestParam("name") String name, @RequestParam("account") String account,
							  @RequestParam("password") String password, Model model) {
		if (userService.findByUserName(account) == null) {
			if(account.equals("") || name.equals("") || password.equals("")) {
				model.addAttribute("success", false);
				model.addAttribute("error_message", Constant.ErrorMessage.ERROR_EMPTY_INPUT);
				return "/admin/create";
			} else if(!account.matches(Constant.Pattern.PATTERN_USERNAME)) {
				model.addAttribute("success", false);
				model.addAttribute("error_message", Constant.ErrorMessage.ERROR_FORMAT_USERNAME);
				return "/admin/create";
			} else if(!password.matches(Constant.Pattern.PATTERN_PASS)) {
				model.addAttribute("success", false);
				model.addAttribute("error_message", Constant.ErrorMessage.ERROR_FORMAT_PASS);
				return "/admin/create";
			} else {
				Teacher teacher = new Teacher(name, account, passwordEncoder.encode(password));
				teacherService.saveTeacher(teacher);
				User user = new User();
				user.setUsername(account);
				user.setPassword(passwordEncoder.encode(password));
				HashSet<Role> roles = new HashSet<>();
				roles.add(roleService.findByName(Constant.RoleType.ROLE_TEACHER));
				user.setRoles(roles);
				userService.saveUser(user);

				model.addAttribute("success", true);
				return "/admin/create";
			}
		} else {
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_EXISTED_USERNAME);
			model.addAttribute("success", false);
			return "/admin/create";
		}
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String getInfo(Model model) throws IOException {
		String userName = getUserName();
		if(userName != null) {
			model.addAttribute("username", userName);
		} else {
			return "redirect:/login";
		}
		Object countAllTeacher = teacherService.countAllTeacher();
		Object countAllGroup = groupService.countAllGroup();
		Object countAllStudent = studentService.countAllStudent();

		if(countAllGroup != null && countAllStudent != null && countAllTeacher != null) {
			model.addAttribute("countGroup", (long)countAllGroup);
			model.addAttribute("countTeacher", (long)countAllTeacher);
			model.addAttribute("countStudent", (long)countAllStudent);
		}

		return "/admin/info";
	}

	@RequestMapping(value = "/changeinfo", method = RequestMethod.GET)
	public String getChangeInfo() {

		return "/admin/changeinfo";
	}

	@RequestMapping(value = "/changeinfo", method = RequestMethod.POST)
	public String postChangeInfo(@RequestParam("account") String account, @RequestParam("oldpass") String oldPass,
				@RequestParam("newpass") String newPass, @RequestParam("renewpass") String reNewPass, Model model) {
		User admin = userService.findByUserName(getUserName());
		if(account.equals("") || oldPass.equals("") || newPass.equals("") || reNewPass.equals("")) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_EMPTY_INPUT);
			return "/admin/changeinfo";
		}
		else if(!account.matches(Constant.Pattern.PATTERN_USERNAME)) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_FORMAT_USERNAME);
			return "/admin/changeinfo";
		}
		else if(!passwordEncoder.matches(oldPass, admin.getPassword())) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_PASS_INCORRECT);
			return "/admin/changeinfo";
		}
		else if(!newPass.matches(Constant.Pattern.PATTERN_PASS)) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_FORMAT_PASS);
			return "/admin/changeinfo";
		}
		else if(!reNewPass.equals(newPass)) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_RE_PASS_INCORRECT);
			return "/admin/changeinfo";
		} else {
			admin.setUsername(account);
			admin.setPassword(passwordEncoder.encode(newPass));

			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			Set<Role> roles = admin.getRoles();
			for (Role role : roles) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			}

			org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(), grantedAuthorities);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			userService.saveUser(admin);

			model.addAttribute("success", true);
		}

		return "login";
	}

	@RequestMapping(value = "/info_teacher", method = RequestMethod.GET)
	public String infoTeacherGet(Model model) throws IOException {
		if (getUserName() != null) {
			Object objects = teacherService.findAllTeacher();
			ArrayList<String> teachers = new ArrayList<>();
			if(objects != null) {
				for(Teacher teacher : (ArrayList<Teacher>)objects) {
					teachers.add(teacher.getName());
				}
			}
			model.addAttribute("teachers", teachers);
		}

		return "/admin/info_teacher";
	}

	@RequestMapping(value = "/info_teacher", method = RequestMethod.POST)
	public String infoTeacherPost(@RequestParam(value = "teacher", required = false) String nameTeacher, Model model) throws IOException {
		if(nameTeacher == null) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_NO_DATA);
			return "/admin/info_teacher";
		}
		Teacher teacher = teacherService.findTeacherByName(nameTeacher);
		model.addAttribute("nameTeacher", teacher.getName());
		try {
			int teacherId = teacher.getId();
			Object groups = groupService.findGroupsByTeacherId(teacherId);
			model.addAttribute("countGroup", ((ArrayList<Group>)(groups)).size());
			int countAllStudent = 0;
			for(Group group : ((ArrayList<Group>)(groups))) {
				countAllStudent += (int)studentService.countStudentByGroupId(group.getId());
			}
			model.addAttribute("countStudent", countAllStudent);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Object objects = teacherService.findAllTeacher();
		ArrayList<String> teachers = new ArrayList<>();
		if(objects != null) {
			for(Teacher teacher1 : (ArrayList<Teacher>)objects) {
				teachers.add(teacher1.getName());
			}
		}
		model.addAttribute("teachers", teachers);

		model.addAttribute("success", true);
		return "/admin/info_teacher";
	}

	@RequestMapping(value = "/edit_pass_teacher", method = RequestMethod.GET)
	public String editPasswordTeacherGet(Model model) throws IOException {
		if (getUserName() != null) {
			Object objects = teacherService.findAllTeacher();
			ArrayList<String> teachers = new ArrayList<>();
			if(objects != null) {
				for(Teacher teacher : (ArrayList<Teacher>)objects) {
					teachers.add(teacher.getName());
				}
			}
			model.addAttribute("teachers", teachers);
		}

		return "/admin/edit_pass_teacher";
	}

	@RequestMapping(value = "/edit_pass_teacher", method = RequestMethod.POST)
	public String editPasswordTeacherPost(@RequestParam(value = "teacher", required = false) String nameTeacher, @RequestParam("password") String password, Model model) throws IOException {
		if(nameTeacher == null) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_NO_DATA);
			return "/admin/edit_pass_teacher";
		} else if(password.equals("")) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_EMPTY_INPUT);
			return "/admin/edit_pass_teacher";
		} else if(!password.matches(Constant.Pattern.PATTERN_PASS)) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_FORMAT_PASS);
			return "/admin/edit_pass_teacher";
		}
		Teacher teacher = teacherService.findTeacherByName(nameTeacher);
		userService.updatePassword(passwordEncoder.encode(password), userService.findByUserName(teacher.getAccount()).getId());
		teacherService.updatePassword(passwordEncoder.encode(password), teacher.getAccount());

		Object objects = teacherService.findAllTeacher();
		ArrayList<String> teachers = new ArrayList<>();
		if(objects != null) {
			for(Teacher teacher1 : (ArrayList<Teacher>)objects) {
				teachers.add(teacher1.getName());
			}
		}
		model.addAttribute("teachers", teachers);

		model.addAttribute("success", true);
		return "/admin/edit_pass_teacher";
	}

	@RequestMapping(value = "/delete_logic", method = RequestMethod.GET)
	public String deletelogicTeacherGet(Model model) throws IOException {
		if (getUserName() != null) {
			Object objects = teacherService.findAllTeacher();
			ArrayList<String> teachers = new ArrayList<>();
			if(objects != null) {
				for(Teacher teacher : (ArrayList<Teacher>)objects) {
					teachers.add(teacher.getName());
				}
			}
			model.addAttribute("teachers", teachers);
		}

		return "/admin/delete_logic";
	}

	@RequestMapping(value = "/delete_logic", method = RequestMethod.POST)
	public String deletelogicTeacherPost(@RequestParam(value = "teacher", required = false) String nameTeacher, Model model) throws IOException {
		if(nameTeacher == null) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_NO_DATA);
			return "/admin/delete_logic";
		}
		userService.updateEnabled(false, userService.findByUserName(teacherService.findTeacherByName(nameTeacher).getAccount()).getId());

		Object objects = teacherService.findAllTeacher();
		ArrayList<String> teachers = new ArrayList<>();
		if(objects != null) {
			for(Teacher teacher : (ArrayList<Teacher>)objects) {
				teachers.add(teacher.getName());
			}
		}
		model.addAttribute("teachers", teachers);
		model.addAttribute("success", true);
		return "/admin/delete_logic";
	}

	@RequestMapping(value = "/delete_physic", method = RequestMethod.GET)
	public String deletePhysicTeacherGet(Model model) throws IOException {
		if (getUserName() != null) {
			Object objects = teacherService.findAllTeacher();
			ArrayList<String> teachers = new ArrayList<>();
			if(objects != null) {
				for(Teacher teacher : (ArrayList<Teacher>)objects) {
					teachers.add(teacher.getName());
				}
			}
			model.addAttribute("teachers", teachers);
		}

		return "/admin/delete_physic";
	}

	@RequestMapping(value = "/delete_physic", method = RequestMethod.POST)
	public String deletePhysicTeacherPost(@RequestParam(value = "teacher", required = false) String nameTeacher, Model model) throws IOException {
		if(nameTeacher == null) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_NO_DATA);
			return "/admin/delete_physic";
		}
		Teacher teacher = teacherService.findTeacherByName(nameTeacher);
		ArrayList<Group> groups = (ArrayList<Group>)groupService.findGroupsByTeacherId(teacher.getId());
		for(Group group : groups) {
			ArrayList<Question> questions = (ArrayList<Question>)questionService.findAllQuestionByGroupId(group.getId());
			questionService.deleteAllQuestionByGroupId(group.getId());
			for(Question question : questions) {
				answerService.deleteAllAnswerByQuestionId(question.getId());
				questionService.deleteQuestionById(question.getId());
			}

			ArrayList<Student> students = studentService.findAllByGroupId(group.getId());
			for(Student student : students) {
				userService.deleteUserById(userService.findByUserName(student.getIdB()).getId());
			}
			studentService.deleteAllStudentByGroupId(group.getId());
		}
		groupService.deleteAllGroupByTeacherId(teacher.getId());
		teacherService.deleteTeacherById(teacher.getId());
		userService.deleteUserById(userService.findByUserName(teacher.getAccount()).getId());

		Object objects = teacherService.findAllTeacher();
		ArrayList<String> teachers = new ArrayList<>();
		if(objects != null) {
			for(Teacher teacherNew : (ArrayList<Teacher>)objects) {
				teachers.add(teacherNew.getName());
			}
		}
		model.addAttribute("teachers", teachers);
		model.addAttribute("success", true);
		return "/admin/delete_physic";
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