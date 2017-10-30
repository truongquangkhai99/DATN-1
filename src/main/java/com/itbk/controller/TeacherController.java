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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Controller
@RequestMapping(value = {"/teacher"})
public class TeacherController {

	@Autowired
	private GroupStudentService groupStudentService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	QuestionService questionService;

	@Autowired
	AnswerService answerService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createGroupGet(Model model) throws IOException {
		return "/teacher/create";
	}

	@SuppressWarnings({ "deprecation", "incomplete-switch" })
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createGroupPost(@RequestParam("file") MultipartFile file, @RequestParam("groupid") String id,
							  @RequestParam("teacher") String teacher, Model model) {
		if (!file.isEmpty()) {
			try {
				Workbook workbook = new HSSFWorkbook(file.getInputStream());
				Sheet sheet = workbook.getSheetAt(0);
				String string = "###";

				for (Row row : sheet) {
					if(row.getRowNum() == 0) continue;
					GroupStudent groupStudent = new GroupStudent();
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
								groupStudent.setId(string);
								break;
							case 2:
								groupStudent.setName(string);
								break;
							case 3:
								groupStudent.setDateOfBirth(string);
								break;
							case 4:
								groupStudent.setClassStudent(string);
								break;
						}
					}
					groupStudent.setTeacher(teacher);
					groupStudent.setGroup(id);
					groupStudentService.save(groupStudent);
					User user = new User(groupStudent.getId(), groupStudent.getDateOfBirth(), true);
					userService.saveUser(user);
					UserRole userRole = new UserRole(user, "ROLE_STUDENT");
					userRoleService.saveUserRole(userRole);
				}
				workbook.close();
				model.addAttribute("success", true);
				return "/teacher/create";
			} catch (Exception e) {
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
		ArrayList<String> arrayGroupStudent = groupStudentService.findAllGroupId();
		model.addAttribute("groups", arrayGroupStudent);

		return "/teacher/test";
	}

	@SuppressWarnings({ "deprecation", "incomplete-switch" })
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String createExaminationPost(@RequestParam("file") MultipartFile file,
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
					for(int j = 0; j < arrayResult.length; j++) {
						if(j == 0) {
							questionService.saveQuestion(new Question(arrayResult[j].toString(), group));
						} else {
							if(arrayResult[j].charAt(0) == '=') {
								answerService.saveAnswer(new Answer(questionService.findLastest().getId(), arrayResult[j].substring(1),true));
							} else {
								answerService.saveAnswer(new Answer(questionService.findLastest().getId(), arrayResult[j].substring(1),false));
							}
						}
					}
				}

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
		ArrayList<String> arrayGroupStudent = groupStudentService.findAllGroupId();
		model.addAttribute("groups", arrayGroupStudent);

		return "/teacher/preview";
	}

	@RequestMapping(value = "/preview", method = RequestMethod.POST)
	public String previewExaminationPost(@RequestParam("groupid") String group, Model model) throws IOException {
		List<Question> list = questionService.getExaminationByGroupId(group);
		Map<String, List<Answer>> map = new HashMap<>();
		ArrayList<Examination> examinations = new ArrayList<>();
		for (Question a : list) {
			map.put(a.getName(), a.getAnswers());
		}

		for (Map.Entry<String, List<Answer>> entry : map.entrySet()) {
			Examination examination = new Examination();
			examination.setQuestion(entry.getKey());
			ArrayList<String> stringArrayList = new ArrayList<>();
			for ( Answer value:  entry.getValue()) {
				stringArrayList.add(value.getAnswer());
			}
			examination.setAnswer(stringArrayList);
			examinations.add(examination);
		}

		model.addAttribute("examinations", examinations);
		model.addAttribute("groups", group);

		ArrayList<Question> questions = (ArrayList<Question>) questionService.findAll();
		model.addAttribute("questions", questions);

		return "/teacher/preview";
	}
}