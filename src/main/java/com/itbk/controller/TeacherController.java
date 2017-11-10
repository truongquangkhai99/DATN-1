package com.itbk.controller;

import com.itbk.dto.Examination;
import com.itbk.model.*;
import com.itbk.service.*;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
			XWPFDocument document = null;
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = (FileInputStream)(file.getInputStream());
				document = new XWPFDocument(fileInputStream);
				XWPFWordExtractor extractor = new XWPFWordExtractor(document);
				String content = extractor.getText();
				String [] arrayQuestion = content.split("###");
				int numberOfQuestion = arrayQuestion.length;
				for(int i = 1; i < numberOfQuestion; i++) {
					String arrayResult[] = arrayQuestion[i].split("XXX");
					int countRightQuestion = 0;
					for(int k = 0; k < arrayResult.length; k++) {
						if(arrayResult[k].charAt(0) == '=') {
							countRightQuestion++;
						}
					}
					for(int j = 0; j < arrayResult.length; j++) {
						if(j == 0) {
							if(countRightQuestion >= 2) {
								questionService.saveQuestion(new Question(arrayResult[j].toString(), group, false));
							} else {
								questionService.saveQuestion(new Question(arrayResult[j].toString(), group, true));
							}
						} else {
							if(arrayResult[j].charAt(0) == '=') {
								answerService.saveAnswer(new Answer(questionService.findLastest().getId(), arrayResult[j].substring(1),true));
							} else {
								answerService.saveAnswer(new Answer(questionService.findLastest().getId(), arrayResult[j].substring(1),false));
							}
						}
					}
				}
				//set timer for student

				studentService.updateTimerForGroupId(Long.parseLong(timer) * 60, groupService.findGroupByGroupName(group).getId());


				model.addAttribute("success", true);
				return "/teacher/test";
			} catch (Exception e) {
				model.addAttribute("success", false);
				System.out.println("vinh err: " + e.getMessage());
				return "/teacher/test";
			} finally {
				try {
					if (document != null) {
						document.close();
					}
					if (fileInputStream != null) {
						fileInputStream.close();
					}
				} catch (Exception ex) {
				}
			}
		} else {
			model.addAttribute("success", false);
			return "/teacher/test";
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
		List<Question> list = questionService.getExaminationByGroupName(group);
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

	public String getUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName = null;
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		}

		return userName;
	}
}