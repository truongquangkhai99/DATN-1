package com.itbk.controller;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
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
		System.out.println("chay toi day 1");
		return "/teacher/create";
	}

	@RequestMapping(value = "/group", method = RequestMethod.POST)
	public String createGroup(@RequestParam("file") MultipartFile file, Model model) throws IOException {
		System.out.println("chay toi day 2");
		String name = file.getOriginalFilename();
		if (!file.isEmpty()) {
			InputStream fileInputStream = file.getInputStream();
			Workbook workbook = new HSSFWorkbook(fileInputStream);
			Sheet sheet = workbook.getSheetAt(0);

			Map<Integer, List<String>> data = new HashMap<>();
			int i = 0;
			for (Row row : sheet) {
				data.put(i, new ArrayList<String>());
				for (Cell cell : row) {
					if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
						String result = cell.getStringCellValue();
						;
						System.out.println("vinhpro: " + result);
					}

//					data.get(i).add(cell.toString());
				}
				i++;
			}

			/*for (Integer key : data.keySet()) {
				System.out.println("key = " + key + " | value = " + data.get(key));
			}*/

			return "/teacher/create";
		} else {
			return "/teacher/create";
		}

	}
}