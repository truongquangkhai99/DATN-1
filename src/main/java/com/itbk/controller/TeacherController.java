package com.itbk.controller;

import com.itbk.model.GroupStudent;
import com.itbk.service.GroupStudentService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
@RequestMapping(value = {"/teacher"})
public class TeacherController {

	@Autowired
	private GroupStudentService groupStudentService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String listUploadedFiles(Model model) throws IOException {
		return "/teacher/create";
	}

	@SuppressWarnings({ "deprecation", "incomplete-switch" })
	@RequestMapping(value = "/group", method = RequestMethod.POST)
	public String createGroup(@RequestParam("file") MultipartFile file, @RequestParam("groupid") String id,
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
}