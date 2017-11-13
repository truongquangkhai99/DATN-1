package com.itbk.viewexcel;

import com.itbk.model.Group;
import com.itbk.model.Student;
import com.itbk.service.GroupService;
import com.itbk.service.StudentService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by PC on 11/3/2017.
 */
public class ExcelPOIView extends AbstractView {

	@Autowired
	StudentService studentService;

	@Autowired
	GroupService groupService;

	private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<Object> params = (ArrayList<Object>)model.get("params");
		setContentType(CONTENT_TYPE_XLSX);
		Workbook workbook = new XSSFWorkbook();
		try {
			CellStyle headerStyle = workbook.createCellStyle();

			if(params.get(0) != null) {
				if(params.size() == 3) {
					Sheet sheet = getSheet(workbook, headerStyle, (String)params.get(1));

					Row header = sheet.createRow(0);

					createRowHeaderTest(header, headerStyle);

					CellStyle style = workbook.createCellStyle();
					style.setWrapText(true);
					createRowInfoTest(sheet, style, (int)params.get(0));

				} else {
					Sheet sheet = getSheet(workbook, headerStyle, (String)params.get(1));

					Row header = sheet.createRow(0);

					createRowHeader(header, headerStyle);

					CellStyle style = workbook.createCellStyle();
					style.setWrapText(true);
					createRowInfo(sheet, style, (int)params.get(0));
				}
			} else {
				ArrayList<Group> groups = groupService.findAllGroup();
				for(Group groupOne : groups) {
					if(params.size() == 3) {
						Sheet sheet = getSheet(workbook, headerStyle, groupOne.getName());

						Row header = sheet.createRow(0);

						createRowHeaderTest(header, headerStyle);

						CellStyle style = workbook.createCellStyle();
						style.setWrapText(true);
						createRowInfoTest(sheet, style, groupOne.getId());
					} else {
						Sheet sheet = getSheet(workbook, headerStyle, groupOne.getName());

						Row header = sheet.createRow(0);

						createRowHeader(header, headerStyle);

						CellStyle style = workbook.createCellStyle();
						style.setWrapText(true);

						createRowInfo(sheet, style, groupOne.getId());
					}
				}
			}

			response.setContentType(CONTENT_TYPE_XLSX);

			ServletOutputStream out = response.getOutputStream();
			out.flush();
			workbook.write(out);
			out.flush();
		} catch (Exception e) {e.printStackTrace();}
		finally {
			workbook.close();
		}
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	public void createRowInfo(Sheet sheet, CellStyle style, int groupId) {
		ArrayList<Student> students = studentService.findAllByGroupId(groupId);
		int size = students.size();
		for(int i = 0; i < size; i++) {
			Row row = sheet.createRow((i+1));
			for(int j = 0; j < 7; j++) {
				Cell cell = row.createCell(j);
				cell.setCellStyle(style);
				insertData(j, cell, i, students);
			}
		}
	}

	public void createRowInfoTest(Sheet sheet, CellStyle style, int groupId) {
		ArrayList<Student> students = studentService.findAllByGroupId(groupId);
		int size = students.size();
		for(int i = 0; i < size; i++) {
			Row row = sheet.createRow((i+1));
			for(int j = 0; j < 11; j++) {
				Cell cell = row.createCell(j);
				cell.setCellStyle(style);
				insertDataTest(j, cell, i, students);
			}
		}
	}

	public void insertData(int i, Cell cell, int count, ArrayList<Student> students) {
		switch (i) {
			case 0:
				cell.setCellValue(++count);
				break;
			case 1:
				cell.setCellValue(students.get(count).getIdB());
				break;
			case 2:
				cell.setCellValue(students.get(count).getName());
				break;
			case 3:
				cell.setCellValue(students.get(count).getDateOfBirth());
				break;
			case 4:
				cell.setCellValue(students.get(count).getClassStd());
				break;
			case 5:
				cell.setCellValue(students.get(count).getTeacher());
				break;
			case 6:
				cell.setCellValue(students.get(count).getScore());
				break;
		}
	}

	public void insertDataTest(int i, Cell cell, int count, ArrayList<Student> students) {
		switch (i) {
			case 0: // Số TT
				cell.setCellValue(++count);
				break;
			case 1: // Mã SV
				cell.setCellValue(students.get(count).getIdB());
				break;
			case 2: // Họ và tên
				cell.setCellValue(students.get(count).getName());
				break;
			case 3: // Lớp
				cell.setCellValue(students.get(count).getClassStd());
				break;
			case 4: // Điểm giữa kỳ
			case 5: // Điểm bài tập
				break;
			case 6: // Điểm thi cuối kỳ
				cell.setCellValue(students.get(count).getScore());
				break;
			case 7: // Điểm học phần
			case 8: // Điểm chữ
			case 9: // Điểm thang 4
			case 10: // Ghi chú
				break;
		}
	}

	public Sheet getSheet(Workbook workbook, CellStyle headerStyle, String fileName) {
		Sheet sheet = workbook.createSheet(fileName);
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 4000);

		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontName("Calibri");
		font.setBold(true);

		headerStyle.setFont(font);

		return sheet;
	}

	public void createRowHeader(Row header, CellStyle headerStyle) {
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("TT");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(1);
		headerCell.setCellValue("Số thẻ");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(2);
		headerCell.setCellValue("Họ tên");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(3);
		headerCell.setCellValue("Ngày sinh");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(4);
		headerCell.setCellValue("Lớp SH");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(5);
		headerCell.setCellValue("GV");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(6);
		headerCell.setCellValue("Điểm");
		headerCell.setCellStyle(headerStyle);
	}

	public void createRowHeaderTest(Row header, CellStyle headerStyle) {
		Cell headerCell = header.createCell(0);
		headerCell.setCellValue("TT");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(1);
		headerCell.setCellValue("MÃ SỐ SV");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(2);
		headerCell.setCellValue("HỌ VÀ TÊN");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(3);
		headerCell.setCellValue("LỚP");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(4);
		headerCell.setCellValue("GIỮA KỲ");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(5);
		headerCell.setCellValue("BÀI TẬP");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(6);
		headerCell.setCellValue("THI");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(7);
		headerCell.setCellValue("ĐIỂM H.PHẦN");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(8);
		headerCell.setCellValue("ĐIỂM CHỮ");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(9);
		headerCell.setCellValue("ĐIỂM T4");
		headerCell.setCellStyle(headerStyle);

		headerCell = header.createCell(10);
		headerCell.setCellValue("GHI CHÚ");
		headerCell.setCellStyle(headerStyle);
	}
}
