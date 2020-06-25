package com.data.comparison.controller;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.struts2.util.ServletContextAware;

import com.data.comparison.constants.DataComparisonConstants;
import com.data.comparison.dao.DataComparisonResultsDAO;
import com.data.comparison.services.DataComparisonService;
import com.data.comparison.utils.ExcelFileHelper;
import com.data.comparison.utils.SendReportToMail;
import com.opensymphony.xwork2.ActionSupport;



public class DataComparisonController extends ActionSupport implements ServletContextAware {

	private static final long serialVersionUID = 1l;

	private static Logger logger = LogManager.getLogger(SendReportToMail.class);

	DataComparisonService service = new DataComparisonService();

	private ServletContext context;

	private String sourceSelection;
	private String targetSelection;

	private String sourceFile;
	private String targetFile;

	private String sourceAPI;
	private String targetAPI;

	private String sourceQuery;
	private String targetQuery;

	private String recepientEmail;

	public String compareData() throws Exception {

		logger.info("Source Selection : " + sourceSelection);
		logger.info("Target Selection : " + targetSelection);

		System.out.println("Source Selection : " + sourceSelection);
		System.out.println("Target Selection : " + targetSelection);

		XSSFSheet sourceSheet = service.returnAsSheet(sourceSelection, sourceFile, sourceQuery, sourceAPI);
		XSSFSheet targetSheet = service.returnAsSheet(targetSelection, targetFile, targetQuery, targetAPI);
		XSSFSheet mappingSheet = ExcelFileHelper.ConvertExcelToSheet("E:\\my workspace\\testdb\\DataComparison\\Excel\\MappingPSV.xlsx");
		service.compareData(sourceSheet, targetSheet,mappingSheet);

		String[] splitSourceFile = sourceFile.split("\\\\");
		String sourceFileName = splitSourceFile[splitSourceFile.length - 1];
		String[] splitTargetFile = targetFile.split("\\\\");
		String targetFileName = splitTargetFile[splitTargetFile.length - 1];

		context.setAttribute("sourceFileName", sourceFileName);
		context.setAttribute("targetFileName", targetFileName);
		context.setAttribute("outputFile", DataComparisonConstants.OUTPUT_REPORT_FILE);

		context.setAttribute("rowMissingMap", DataComparisonResultsDAO.getRowMissingMap());
		context.setAttribute("dataMismatchMap", DataComparisonResultsDAO.getDataMismatchMap());

		return ("success");
	}

	public String sendReportToMail() throws Exception {

		logger.info("Recepient : " + recepientEmail);
		service.sendMailWithAttachment(recepientEmail);
		context.setAttribute("isMailSent", true);
		return ("success");
	}

	public String getSourceSelection() {
		return sourceSelection;
	}

	public void setSourceSelection(String sourceSelection) {
		this.sourceSelection = sourceSelection;
	}

	public String getTargetSelection() {
		return targetSelection;
	}

	public void setTargetSelection(String targetSelection) {
		this.targetSelection = targetSelection;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public String getTargetFile() {
		return targetFile;
	}

	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}

	public String getSourceAPI() {
		return sourceAPI;
	}

	public void setSourceAPI(String sourceAPI) {
		this.sourceAPI = sourceAPI;
	}

	public String getTargetAPI() {
		return targetAPI;
	}

	public void setTargetAPI(String targetAPI) {
		this.targetAPI = targetAPI;
	}

	public String getSourceQuery() {
		return sourceQuery;
	}

	public void setSourceQuery(String sourceQuery) {
		this.sourceQuery = sourceQuery;
	}

	public String getTargetQuery() {
		return targetQuery;
	}

	public void setTargetQuery(String targetQuery) {
		this.targetQuery = targetQuery;
	}

	public String getRecepientEmail() {
		return recepientEmail;
	}

	public void setRecepientEmail(String recepientEmail) {
		this.recepientEmail = recepientEmail;
	}

	public void setServletContext(ServletContext context) {
		this.context = context;
	}

}
