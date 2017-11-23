package com.itbk.controller;

import com.itbk.constant.Constant;
import com.itbk.dto.Examination;
import com.itbk.model.*;
import com.itbk.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping(value = {"/teacher"})
public class TeacherController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private HandleFileExelService handleFileExelService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private HandleFileWordService handleFileWordService;

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String infoTeacherGet(HttpServletRequest request, Model model) throws IOException {
		String userName = getUserName();
		if(userName != null) {
			model.addAttribute("username", userName);
		} else {
			return "redirect:/login";
		}
		ArrayList<Group> groups = (ArrayList<Group>)groupService.findGroupsByTeacherId(teacherService.findTeacherByUsername(userName).getId());
		int numberOfStudent = 0;
		for(Group group : groups) {
			numberOfStudent += (long)studentService.countStudentByGroupId(group.getId());
		}

		model.addAttribute("countGroup", groups.size());
		model.addAttribute("countStudent", numberOfStudent);

		return "/teacher/info";
	}

	@RequestMapping(value = "/changeinfo", method = RequestMethod.GET)
	public String getChangeInfo() {

		return "/teacher/changeinfo";
	}

	@RequestMapping(value = "/changeinfo", method = RequestMethod.POST)
	public String postChangeInfo(@RequestParam("account") String account, @RequestParam("oldpass") String oldPass,
								 @RequestParam("newpass") String newPass, @RequestParam("renewpass") String reNewPass, Model model) {
		User teacher = userService.findByUserName(getUserName());
		if(account.equals("") || oldPass.equals("") || newPass.equals("") || reNewPass.equals("")) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_EMPTY_INPUT);
			return "/teacher/changeinfo";
		}
		else if(!account.matches(Constant.Pattern.PATTERN_USERNAME)) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_FORMAT_USERNAME);
			return "/teacher/changeinfo";
		}
		else if(!passwordEncoder.matches(oldPass, teacher.getPassword())) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_PASS_INCORRECT);
			return "/teacher/changeinfo";
		}
		else if(!newPass.matches(Constant.Pattern.PATTERN_PASS)) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_FORMAT_PASS);
			return "/teacher/changeinfo";
		}
		else if(!reNewPass.equals(newPass)) {
			model.addAttribute("success", false);
			model.addAttribute("error_message", Constant.ErrorMessage.ERROR_RE_PASS_INCORRECT);
			return "/teacher/changeinfo";
		} else {
			teacher.setUsername(account);
			teacher.setPassword(passwordEncoder.encode(newPass));

			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			Set<Role> roles = teacher.getRoles();
			for (Role role : roles) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			}

			org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(teacher.getUsername(), teacher.getPassword(), grantedAuthorities);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			userService.saveUser(teacher);

			model.addAttribute("success", true);
			return "login";
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createGroupGet(HttpServletRequest request, Model model) throws IOException {

		return "/teacher/create";
	}

	@SuppressWarnings({ "deprecation", "incomplete-switch" })
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createGroupPost(@RequestParam("file") MultipartFile file, @RequestParam("group") String group, Model model) {
		if (!file.isEmpty()) {
			boolean resultReadFileExel = false;
			Teacher teacher = teacherService.findTeacherByUsername(getUserName());
			if(group != null) {
				Group groupObj = new Group(group);
				groupObj.setTeacher(teacher);
				groupService.saveGroup(groupObj);
				resultReadFileExel = handleFileExelService.readFileExel(file, teacher, groupObj, true);
			}
			if(resultReadFileExel) {
				model.addAttribute("success", true);
				return "/teacher/create";

			} else {
				model.addAttribute("success", false);
				return "/teacher/create";
			}
		} else {
			model.addAttribute("success", false);
			return "/teacher/create";
		}
	}

	// createAlGroup start
	@RequestMapping(value = "/create_all", method = RequestMethod.GET)
	public String createAllGroupGet(HttpServletRequest request, Model model) throws IOException {

		return "/teacher/create_all";
	}

	@SuppressWarnings({ "deprecation", "incomplete-switch" })
	@RequestMapping(value = "/create_all", method = RequestMethod.POST)
	public String createAllGroupPost(@RequestParam("file") MultipartFile file, @RequestParam("group") String group, Model model) {
		if (!file.isEmpty()) {
			boolean resultReadFileExel = false;
			Teacher teacher = teacherService.findTeacherByUsername(getUserName());
			if(group == null || group.equals("")) {
				resultReadFileExel = handleFileExelService.readFileExel(file, teacher, null, false);
			}
			if(resultReadFileExel) {
				model.addAttribute("success", true);
				return "/teacher/create_all";
			} else {
				model.addAttribute("success", false);
				return "/teacher/create_all";
			}
		} else {
			model.addAttribute("success", false);
			return "/teacher/create_all";
		}
	}
	// createAlGroup finish

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String createExaminationGet(Model model) throws IOException {
		if(getUserName() != null) {
			Teacher teacher = teacherService.findGroupIdByUsername(getUserName());
			ArrayList<String> groups = new ArrayList<>();
			for(Group group : teacher.getGroups()) {
				groups.add(group.getName());
			}

			model.addAttribute("groups", groups);
		}

		return "/teacher/test";
	}

	@SuppressWarnings({ "deprecation", "incomplete-switch" })
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String createExaminationPost(@RequestParam("file") MultipartFile file, @RequestParam("timer") String timer,
				@RequestParam("group") String group, Model model) {
		if (!file.isEmpty()) {
			boolean resultReadFileWord= false;
			if(group != null) {
				resultReadFileWord = handleFileWordService.readFileWord(file, teacherService.findTeacherByUsername(getUserName()), groupService.findGroupByGroupName(group), timer);
			}
			if(resultReadFileWord) {
				model.addAttribute("success", true);
				return "/teacher/test";
			} else {
				model.addAttribute("success", false);
				return "/teacher/test";
			}

		} else {
			model.addAttribute("success", false);
			return "/teacher/test";
		}
	}

	@RequestMapping(value = "/test_all", method = RequestMethod.GET)
	public String createExaminationAllGet(Model model) throws IOException {

		return "/teacher/test_all";
	}

	@SuppressWarnings({ "deprecation", "incomplete-switch" })
	@RequestMapping(value = "/test_all", method = RequestMethod.POST)
	public String createExaminationAllPost(@RequestParam("file") MultipartFile file, @RequestParam("timer") String timer,
										@RequestParam("group") String group, Model model) {
		if (!file.isEmpty()) {
			boolean resultReadFileWord= false;
			if(group == null || group.equals("")) {
				resultReadFileWord = handleFileWordService.readFileWord(file, teacherService.findTeacherByUsername(getUserName()), null, timer);
			}
			if(resultReadFileWord) {
				model.addAttribute("success", true);
				return "/teacher/test_all";
			} else {
				model.addAttribute("success", false);
				return "/teacher/test_all";
			}
		} else {
			model.addAttribute("success", false);
			return "/teacher/test_all";
		}
	}



	@RequestMapping(value = "/preview", method = RequestMethod.GET)
	public String previewExaminationGet(Model model) throws IOException {
		if (getUserName() != null) {
			Teacher teacher = teacherService.findTeacherByUsername(getUserName());
			ArrayList<String> groups = new ArrayList<>();
			for(Group group : teacher.getGroups()) {
				groups.add(group.getName());
			}
			model.addAttribute("groups", groups);
		}

		return "/teacher/preview";
	}

	@RequestMapping(value = "/preview", method = RequestMethod.POST)
	public String previewExaminationPost(@RequestParam("group") String group, Model model) throws IOException {
		List<Question> list = questionService.getExaminationByGroupId(groupService.findGroupByGroupName(group).getId());
		Map<Question, List<Answer>> map = new HashMap<>();
		ArrayList<Examination> examinations = new ArrayList<>();
		int count = 0;
		for (Question a : list) {
			Examination examination = new Examination();
			examination.setQuestion("Câu " + (++count) + ": " + a.getName());
			examination.setAnswers(a.getAnswers());
			examination.setRadio(a.isRadio());
			examinations.add(examination);
		}

		model.addAttribute("examinations", examinations);
		model.addAttribute("groups", group);

		return "/teacher/preview";
	}


	@RequestMapping(value = "/output", method = RequestMethod.GET)
	public String outputGet(Model model) throws IOException {
		if (getUserName() != null) {
			Teacher teacher = teacherService.findTeacherByUsername(getUserName());
			ArrayList<String> groups = new ArrayList<>();
			for(Group group : teacher.getGroups()) {
				groups.add(group.getName());
			}
			model.addAttribute("groups", groups);
		}

		return "/teacher/output";
	}

	@RequestMapping(value = "/output", method = RequestMethod.POST)
	public ModelAndView outputPost(@RequestParam("fileName") String fileName, @RequestParam("group") String group, HttpServletResponse response) throws IOException {
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
		ArrayList<Object> params = new ArrayList<>();
		params.add(groupService.findGroupByGroupName(group).getId());
		params.add(fileName);

		return new ModelAndView("excelPOIView", "params", params);
	}

	@RequestMapping(value = "/output_all", method = RequestMethod.GET)
	public String outputAllGet(Model model) throws IOException {

		return "/teacher/output_all";
	}

	@RequestMapping(value = "/output_all", method = RequestMethod.POST)
	public ModelAndView outputAllPost(@RequestParam("fileName") String fileName, @RequestParam("group") String group, HttpServletResponse response) throws IOException {
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
		ArrayList<Object> params = new ArrayList<>();
		params.add(null);
		params.add(fileName);

		return new ModelAndView("excelPOIView", "params", params);
	}


	@RequestMapping(value = "/output_test", method = RequestMethod.GET)
	public String outputTestGet(Model model) throws IOException {
		if (getUserName() != null) {
			Teacher teacher = teacherService.findTeacherByUsername(getUserName());
			ArrayList<String> groups = new ArrayList<>();
			for(Group group : teacher.getGroups()) {
				groups.add(group.getName());
			}
			model.addAttribute("groups", groups);
		}

		return "/teacher/output_test";
	}

	@RequestMapping(value = "/output_test", method = RequestMethod.POST)
	public ModelAndView outputTestPost(@RequestParam("fileName") String fileName, @RequestParam("group") String group, @RequestParam("original") String original, HttpServletResponse response) throws IOException {
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
		ArrayList<Object> params = new ArrayList<>();
		params.add(groupService.findGroupByGroupName(group).getId());
		params.add(fileName);
		params.add(original);

		return new ModelAndView("excelPOIView", "params", params);
	}

	@RequestMapping(value = "/output_all_test", method = RequestMethod.GET)
	public String outputAllTestGet(Model model) throws IOException {

		return "/teacher/output_all_test";
	}

	@RequestMapping(value = "/output_all_test", method = RequestMethod.POST)
	public ModelAndView outputAllTestPost(@RequestParam("fileName") String fileName, @RequestParam("group") String group, @RequestParam("original") String original, HttpServletResponse response) throws IOException {
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
		ArrayList<Object> params = new ArrayList<>();
		params.add(null);
		params.add(fileName);
		params.add(original);

		return new ModelAndView("excelPOIView", "params", params);
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