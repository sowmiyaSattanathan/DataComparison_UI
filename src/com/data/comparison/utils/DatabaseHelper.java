package com.data.comparison.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DatabaseHelper {

	public String getFileName(String baseName) {
		// DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		// String dateTimeInfo = dateFormat.format(new Date());
		return baseName.concat(String.format(".xlsx"));
	}

	public XSSFSheet ExportDbTableToSheet(String table, String sql) {
		String jdbcURL = "jdbc:mysql://127.0.0.1/customerdetailsschema?autoReconnect=true&useSSL=false";
		String username = "Venkat";
		String password = "Force@123";

		// String excelFilePath = getFileName(table.concat("_Export"));
		XSSFSheet sheet = null;
		try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {

			Statement statement = connection.createStatement();

			ResultSet result = statement.executeQuery(sql);

			XSSFWorkbook workbook = new XSSFWorkbook();
			sheet = workbook.createSheet(table);

			writeHeaderLine(result, sheet);
			writeDataLines(result, workbook, sheet);
			workbook.close();

			statement.close();

		} catch (SQLException e) {
			System.out.println("Datababse error:");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("File IO error:");
			e.printStackTrace();
		}
		return sheet;
	}

	public void writeHeaderLine(ResultSet result, XSSFSheet sheet) throws SQLException {
		// write header line containing column names
		ResultSetMetaData metaData = result.getMetaData();
		int numberOfColumns = metaData.getColumnCount();

		Row headerRow = sheet.createRow(0);

		// exclude the first column which is the ID field
		for (int i = 1; i <= numberOfColumns; i++) {
			String columnName = metaData.getColumnName(i);
			Cell headerCell = headerRow.createCell(i - 1);
			headerCell.setCellValue(columnName);
		}
	}

	public void writeDataLines(ResultSet result, XSSFWorkbook workbook, XSSFSheet sheet) throws SQLException {
		ResultSetMetaData metaData = result.getMetaData();
		int numberOfColumns = metaData.getColumnCount();

		int rowCount = 1;

		while (result.next()) {
			Row row = sheet.createRow(rowCount++);

			for (int i = 1; i <= numberOfColumns; i++) {
				Object valueObject = result.getObject(i);

				Cell cell = row.createCell(i - 1);

				if (valueObject instanceof Boolean)
					cell.setCellValue((Boolean) valueObject);
				else if (valueObject instanceof Double)
					cell.setCellValue((double) valueObject);
				else if (valueObject instanceof Float)
					cell.setCellValue((float) valueObject);
				else if (valueObject instanceof String)
					cell.setCellValue((String) valueObject);
				else if (valueObject instanceof Date) {
					cell.setCellValue((Date) valueObject);
					formatDateCell(workbook, cell);
				}

			}

		}
	}

	public void formatDateCell(XSSFWorkbook workbook, Cell cell) {
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper creationHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
		cell.setCellStyle(cellStyle);
	}

}
