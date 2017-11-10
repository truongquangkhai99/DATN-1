package com.itbk.service;

import com.itbk.constant.Constant;
import com.itbk.model.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by PC on 11/9/2017.
 */

@Component
public class HandleFileExelService {

	@Autowired
	private StudentService studentService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	GroupService groupService;

	@Autowired
	TeacherService teacherService;

	public boolean readFileExel (MultipartFile file, Teacher teacher, Group group, boolean isExistedGroup) {
		try {
			Workbook workbook = new HSSFWorkbook(file.getInputStream());
			Sheet sheet = null;
			if(isExistedGroup) {
				sheet = workbook.getSheetAt(0);
				readBySheet(sheet, group, isExistedGroup);
			} else {
				int numberOfSheet = workbook.getNumberOfSheets();
				for(int i = 0; i < numberOfSheet; i++) {
					System.out.println("chay toi day i = " + i);
					sheet = workbook.getSheetAt(i);
					Group groupNew = new Group();
					groupNew.setTeacher(teacher);
					if(i < numberOfSheet - 1) {
						groupNew.setName("N" + sheet.getSheetName());
						groupService.saveGroup(groupNew);
						readBySheet(sheet, groupNew, isExistedGroup);
					}
				}
			}

			workbook.close();
			return true;
		} catch (Exception e) {
			System.out.println("loi tai day: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public void readBySheet(Sheet sheet, Group group, boolean isExistedGroup) {
		String string = "#####";
		String nameTeacher = "";

		for (Row row : sheet) {
			if (row.getRowNum() == 0) continue; //skip first row
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
					case 5:
						if (row.getRowNum() == 1) {
							nameTeacher = string;
						}
						break;
				}
				student.setTeacher(nameTeacher);
				if (isExistedGroup) { // read only a sheet case
					student.setGroup(group);
				} else { // read all sheets case
					student.setGroup(group);
				}
			}

			studentService.save(student);
			User user = new User(student.getIdB(), student.getDateOfBirth(), true);
			userService.saveUser(user);
			UserRole userRole = new UserRole(user, Constant.RoleType.ROLE_STUDENT);
			userRoleService.saveUserRole(userRole);
		}
	}

	public void setInfoToStudent(Row row, String nameTeacher) {

	}

}
