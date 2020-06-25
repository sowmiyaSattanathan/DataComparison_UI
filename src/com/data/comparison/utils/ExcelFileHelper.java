package com.data.comparison.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileHelper {

	public static XSSFSheet ConvertExcelToSheet(String excellFilePath) throws IOException {

		FileInputStream fileStream = new FileInputStream(excellFilePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		return sheet;
	}

}
