package com.itbk.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = {"/teacher"})
public class TeacherController {

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String listUploadedFiles(Model model) throws IOException {
		return "/teacher/create";
	}

	@RequestMapping(value = "/group", method = RequestMethod.POST)
	public String createGroup(@RequestParam("file") MultipartFile file, Model model) throws IOException {
		String name = file.getOriginalFilename();
		if (!file.isEmpty()) {
			InputStream fileInputStream = file.getInputStream();
			Workbook workbook = new HSSFWorkbook(fileInputStream);
			Sheet sheet = workbook.getSheetAt(0);

			Map<Integer, List<String>> data = new HashMap<>();
			int i = 0;
			String string = "###";
			for (Row row : sheet) {
				data.put(i, new ArrayList<String>());
				for (Cell cell : row) {
					switch (cell.getCellTypeEnum()) {
						case STRING:
							string = cell.toString();
							data.get(i).add(string);
							break;
						case NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								string = cell.toString();
								data.get(i).add(string);
							} else {
								string = NumberToTextConverter.toText(cell.getNumericCellValue());
								data.get(i).add(string);
							}
							break;
						default: data.get(new Integer(i)).add("###");
					}

				}
				i++;
			}
			workbook.close();

			return "/teacher/create";
		} else {
			return "/teacher/create";
		}
	}
}