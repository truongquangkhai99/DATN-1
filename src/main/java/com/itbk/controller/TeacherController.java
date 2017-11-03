package com.itbk.controller;

import com.itbk.dto.Examination;
import com.itbk.model.*;
import com.itbk.service.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping(value = {"/teacher"})
public class TeacherController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	QuestionService questionService;

	@Autowired
	AnswerService answerService;

	@Autowired
	TeacherService teacherService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createGroupGet(Model model) throws IOException {
		return "/teacher/create";
	}

	@SuppressWarnings({ "deprecation", "incomplete-switch" })
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createGroupPost(@RequestParam("file") MultipartFile file, @RequestParam("groupid") String id, Model model) {
		if (!file.isEmpty()) {
			try {
				Workbook workbook = new HSSFWorkbook(file.getInputStream());
				Sheet sheet = workbook.getSheetAt(0);
				String string = "###";

				for (Row row : sheet) {
					if(row.getRowNum() == 0) continue;
					Student student = new Student();
					for (Cell cell : row) {
						switch (cell.getCellTypeEnum()) {
							case STRING:
								string = cell.toString();
								break;
							case NUMERIC:
								if (DateUtil.isCellDateFormatted(cell)) {
									string = cell.toString();
								} else {
									string = NumberToTextConverter.toText(cell.getNumericCellValue());
								}
								break;
						}
						switch (cell.getColumnIndex()) {
							case 1:
								student.setIdB(string);
								break;
							case 2:
								student.setName(string);
								break;
							case 3:
								student.setDateOfBirth(string);
								break;
							case 4:
								student.setClassStd(string);
								break;
						}
					}
					student.setTeacher(getNameTeacher());
					student.setGroup(id);
					studentService.save(student);
					User user = new User(student.getIdB(), student.getDateOfBirth(), true);
					userService.saveUser(user);
					UserRole userRole = new UserRole(user, "ROLE_STUDENT");
					userRoleService.saveUserRole(userRole);
				}
				workbook.close();
				model.addAttribute("success", true);
				return "/teacher/create";
			} catch (Exception e) {
				System.out.println("loi tai day: " + e.getMessage());
				model.addAttribute("success", false);
				return "/teacher/create";
			}

		} else {
			model.addAttribute("success", false);
			return "/teacher/create";
		}
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String createExaminationGet(Model model) throws IOException {
		if(getNameTeacher() != null) {
			ArrayList<String> groups = studentService.findGroupByNameTeacher(getNameTeacher());
			model.addAttribute("groups", groups);
		}

		return "/teacher/test";
	}

	@SuppressWarnings({ "deprecation", "incomplete-switch" })
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String createExaminationPost(@RequestParam("file") MultipartFile file, @RequestParam("timer") String timer,
				@RequestParam("groupid") String group, Model model) {
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
				studentService.updateTimerForGroup(Long.parseLong(timer) * 60, group);

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
		if (getNameTeacher() != null) {
			ArrayList<String> arrayGroupStudent = studentService.findGroupByNameTeacher(getNameTeacher());
			model.addAttribute("groups", arrayGroupStudent);
		}

		return "/teacher/preview";
	}

	@RequestMapping(value = "/preview", method = RequestMethod.POST)
	public String previewExaminationPost(@RequestParam("groupid") String group, Model model) throws IOException {
		List<Question> list = questionService.getExaminationByGroupId(group);
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
		if (getNameTeacher() != null) {
			ArrayList<String> arrayGroupStudent = studentService.findGroupByNameTeacher(getNameTeacher());
			model.addAttribute("groups", arrayGroupStudent);
		}

		return "/teacher/output";
	}

	@RequestMapping(value = "/output", method = RequestMethod.POST)
	public ModelAndView outputPost(@RequestParam("fileName") String fileName, @RequestParam("groupid") String groupid, HttpServletResponse response) throws IOException {
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
		ArrayList<String> params = new ArrayList<>();
		params.add(groupid);
		params.add(fileName);

		return new ModelAndView("excelPOIView", "params", params);
	}


	public String getNameTeacher() {
		if(getUserName() != null) {
			Teacher teacher = teacherService.findTeacherByUsername(getUserName());
			return teacher.getName();
		}
		return  null;
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