package com.data.comparison.services;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.data.comparison.constants.DataComparisonConstants;
import com.data.comparison.utils.CSVFileHelper;
import com.data.comparison.utils.DataComparisonHelper;
import com.data.comparison.utils.DatabaseHelper;
import com.data.comparison.utils.ExcelFileHelper;
import com.data.comparison.utils.SendReportToMail;



public class DataComparisonService {

	DataComparisonHelper dataCompare = new DataComparisonHelper();

	SendReportToMail sendMail = new SendReportToMail();

	DatabaseHelper exporter = new DatabaseHelper();

	/*
	 * public void readsourcefromdb() throws IOException{ DatabaseHelper exporter =
	 * new DatabaseHelper(); XSSFSheet sheet1 = exporter.ExportDbTableToSheet(
	 * "customerdeatails","select * from customerdeatails"); XSSFSheet sheet2 =
	 * exporter.ExportDbTableToSheet("orderdetails","select * from orderdetails");
	 * dataCompare.compareTwoSheets(sheet1,sheet2); dataCompare.writeOutputFile(
	 * "F:\\DataComparison_Update_Testng\\DataComparisonGenericCode\\Excel\\DBOutput.xlsx"
	 * ); }
	 */

	public XSSFSheet returnAsSheet(String selectionMethod, String file, String query, String api) throws IOException {
		XSSFSheet sheet = null;
		switch (selectionMethod) {
		case "file":
			System.out.println("*****");
			sheet = ExcelFileHelper.ConvertExcelToSheet(file);
			break;
		case "database":
			System.out.println("DB*****DB");
			// This has to be checked - Check whether table name is needed
			sheet = exporter.ExportDbTableToSheet("customerDetails", query);
			break;
		case "salesforce":
			// Have to be developed
			break;
		}

		return sheet;
	}

	public void compareData(XSSFSheet sourceSheet, XSSFSheet targetSheet, XSSFSheet mappingSheet) throws IOException {
		dataCompare.compareTwoSheets(sourceSheet, targetSheet,mappingSheet);
		// This has to be checked - Create File Name with Timestamp
		dataCompare.writeOutputFile(DataComparisonConstants.OUTPUT_REPORT_FILE);
	}

	public void sendMailWithAttachment(String recepientMail) {
		sendMail.SendMailByAttachment(recepientMail);
	}

	public void readsoucefromcsv() throws IOException {
		XSSFSheet sheet1 = CSVFileHelper.ConvertCSVToSheet("F:\\DataComparison_Update_Testng\\DataComparisonGenericCode\\Excel\\SourceFileCSV.csv");
		XSSFSheet sheet2 = CSVFileHelper.ConvertCSVToSheet("F:\\DataComparison_Update_Testng\\DataComparisonGenericCode\\Excel\\TragetFileCSV.csv");
		XSSFSheet mappingSheet = ExcelFileHelper.ConvertExcelToSheet("E:\\my workspace\\testdb\\DataComparison\\Excel\\CSVMappingRule.xlsx");
		dataCompare.compareTwoSheets(sheet1,sheet2,mappingSheet);
		dataCompare.writeOutputFile("F:\\DataComparison_Update_Testng\\DataComparisonGenericCode\\Excel\\CSVOutput.xlsx");
	}	
		
}
