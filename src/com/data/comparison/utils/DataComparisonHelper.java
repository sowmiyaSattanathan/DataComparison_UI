package com.data.comparison.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.data.comparison.dao.DataComparisonResultsDAO;
import com.data.comparison.dao.DataMappingDAO;
import com.data.comparison.dao.DataMismatchDAO;


public class DataComparisonHelper {

	private Map<String, DataMismatchDAO> dataMismatchMap;
	private Map<String, String> rowMissingHeaderMap;
	private Map<String, List<String>> rowMissingMap;
	private Integer index = 1;
	private XSSFSheet sourceSheet;
	private XSSFSheet targetSheet;
	private XSSFSheet mappingSheet;
	private List<String> rowMissingListSource;
	private List<String> rowMissingListTarget;

	/***
	 * This Method is used to Compare two sheets
	 * 
	 * @param _sheet1
	 * @param _sheet2
	 */
	public void compareTwoSheets(XSSFSheet _sheet1, XSSFSheet _sheet2,XSSFSheet _mappingSheet) {
		dataMismatchMap = new TreeMap<String, DataMismatchDAO>();
		rowMissingMap = new TreeMap<String, List<String>>();
		rowMissingHeaderMap = new TreeMap<String, String>();
		rowMissingListSource = new ArrayList<>();
		rowMissingListTarget = new ArrayList<>();
		dataMismatchMap.put(index.toString(), new DataMismatchDAO("Primary Key", "Source Column Name", "Source Column Value",
				"Target Column Name", "Target Column Value"));

		// row missing Header
		rowMissingHeaderMap.put("Status", "Primary Key Column");

		sourceSheet = _sheet1;
		targetSheet = _sheet2;
		mappingSheet= _mappingSheet;
		if(mappingSheet != null && mappingSheet.getLastRowNum() > 0) {
		
		int firstRow1 = sourceSheet.getFirstRowNum();
		int lastRow1 = sourceSheet.getLastRowNum();

		int firstRow2 = targetSheet.getFirstRowNum();
		int lastRow2 = targetSheet.getLastRowNum();
		
		   String sourcePKKey="";
           String targetPKKey="";
           
           ArrayList<DataMappingDAO> sourceIndexes = new ArrayList<>(); 
           ArrayList<DataMappingDAO> targetIndexes = new ArrayList<>(); 
           
           for(Row row1 : mappingSheet) {
          	 if(row1.getCell(2) !=null && row1.getCell(2).getCellType() == HSSFCell.CELL_TYPE_STRING && row1.getCell(2).getStringCellValue().trim().equalsIgnoreCase("Primary Key")) {
          		DataMappingDAO sourceIndex = new DataMappingDAO(findCellIndex(sourceSheet,row1.getCell(0).getStringCellValue()),row1.getCell(0).getStringCellValue());
          		 sourceIndexes.add(sourceIndex);
          		DataMappingDAO targetIndex = new DataMappingDAO(findCellIndex(targetSheet,row1.getCell(1).getStringCellValue()),row1.getCell(1).getStringCellValue());
          		 targetIndexes.add(targetIndex);
          	 }
          }
           

           for (int i = firstRow1 + 1; i <= lastRow1; i++) {
               XSSFRow row1 = sourceSheet.getRow(i);//2
               if (row1 != null) {
               	   String Row1ID = "";
               	   String sourceRowName ="";
               	   for(DataMappingDAO index : sourceIndexes) {
               		 
               		 switch (row1.getCell(index.primaryKey).getCellType()) {
                         
                         case HSSFCell.CELL_TYPE_NUMERIC:
                       	  Row1ID = Row1ID + Integer.toString((int)row1.getCell(index.primaryKey).getNumericCellValue()) + ":";
                       	  sourceRowName = sourceRowName + index.ColumnName + ":" + Integer.toString((int)row1.getCell(index.primaryKey).getNumericCellValue())+": ";
                        	 break;
                         case HSSFCell.CELL_TYPE_STRING:
                       	  Row1ID = Row1ID + row1.getCell(index.primaryKey).getStringCellValue() + ":";
                       	  sourceRowName = sourceRowName + index.ColumnName + ":" + row1.getCell(index.primaryKey).getStringCellValue()+": ";
                        	 break;
                    	 }
               	   }
                   Boolean notFound = true;
                   sourceRowName = sourceRowName.replaceAll(": $", "");
                   for (int j = firstRow2 + 1; j <= lastRow2; j++) {
                       XSSFRow row2 = targetSheet.getRow(j);
                       String Row2ID = "";
                       for(DataMappingDAO index : targetIndexes) {
                       	switch (row2.getCell(index.primaryKey).getCellType()) {
		                        case HSSFCell.CELL_TYPE_NUMERIC:
		                       	  Row2ID = Row2ID + Integer.toString((int)row2.getCell(index.primaryKey).getNumericCellValue()) + ":";
		                       	 break;
		                        case HSSFCell.CELL_TYPE_STRING:
		                        	  Row2ID = Row2ID +row2.getCell(index.primaryKey).getStringCellValue() + ":";
		                       	 break;
                       	}
                       }
                       if (Row1ID.equals(Row2ID)) {
                           notFound =false;
                           compareTwoRows(row1, row2, sourceRowName);
                           break;
                       } 
                   }
				if (notFound) {
					index++;
					rowMissingListTarget.add(sourceRowName);
				}
			}
		}
		rowMissingMap.put("Missing rows in Target", rowMissingListTarget);

		// Checking for missing rows in sheet 1
		 for (int i = firstRow2 + 1; i <= lastRow2; i++) {
             XSSFRow row2 = targetSheet.getRow(i);//2
             if (row2 != null) {
             	String sourceRowName ="";
             	String Row2ID = "";
             	for(DataMappingDAO index : targetIndexes) {
                 	switch (row2.getCell(index.primaryKey).getCellType()) {
	                        case HSSFCell.CELL_TYPE_NUMERIC:
	                       	  Row2ID = Row2ID + Integer.toString((int)row2.getCell(index.primaryKey).getNumericCellValue()) + ":";
	                       	  sourceRowName = sourceRowName + index.ColumnName + ":" + Integer.toString((int)row2.getCell(index.primaryKey).getNumericCellValue())+": ";
	                       	 break;
	                        case HSSFCell.CELL_TYPE_STRING:
	                        	  Row2ID = Row2ID +row2.getCell(index.primaryKey).getStringCellValue() + ":";
	                        	  sourceRowName = sourceRowName + index.ColumnName + ":" + row2.getCell(index.primaryKey).getStringCellValue()+": ";
	                       	 break;
                 	}
                 }
             	sourceRowName = sourceRowName.replaceAll(": $", "");
                 Boolean notFound = true;
                 for (int j = firstRow1 + 1; j <= lastRow1; j++) {
                     XSSFRow row1 = sourceSheet.getRow(j);
                    
                     String Row1ID = "";
              	   for(DataMappingDAO index : sourceIndexes) {
              		 switch (row1.getCell(index.primaryKey).getCellType()) {
                        
                        case HSSFCell.CELL_TYPE_NUMERIC:
                      	  Row1ID = Row1ID + Integer.toString((int)row1.getCell(index.primaryKey).getNumericCellValue()) + ":";
                       	 break;
                        case HSSFCell.CELL_TYPE_STRING:
                      	  Row1ID = Row1ID + row1.getCell(index.primaryKey).getStringCellValue() + ":";
                       	 break;
                   	 }
              	   }
                     if (Row1ID.equals(Row2ID)) {
                         notFound =false;
                         
                         break;
                     } 
                 }

				if (notFound) {
					index++;
					rowMissingListSource.add(sourceRowName);
				}
			}
		}

		rowMissingMap.put("Missing rows in Source", rowMissingListSource);

		if (!rowMissingMap.isEmpty())
			DataComparisonResultsDAO.setRowMissingMap(rowMissingMap);
		if (!dataMismatchMap.isEmpty())
			DataComparisonResultsDAO.setDataMismatchMap(dataMismatchMap);
		}
	}

	// Compare Two Rows column
	public void compareTwoRows(XSSFRow row1, XSSFRow row2, String rowID) {

		String sourceColumn="";
        String targetColumn="";
           
          for(Row row3 : mappingSheet){
         
           	
           	//Check only for Compare and Empty Rule Column. Ignores Ignore and Primary Key rule.
            if((row3.getCell(2) ==null) || !row3.getCell(2).getStringCellValue().trim().equalsIgnoreCase("Ignore") && !row3.getCell(2).getStringCellValue().trim().equalsIgnoreCase("PrimaryKey")) {
                sourceColumn = row3.getCell(0).getStringCellValue();
                targetColumn = row3.getCell(1).getStringCellValue();
            
                int colIndex1 = findCellIndex(sourceSheet,sourceColumn);
                int colIndex2 = findCellIndex(targetSheet,targetColumn);
                XSSFCell cell1 = row1.getCell(colIndex1);
                XSSFCell cell2 = row2.getCell(colIndex2);
               
                if ((cell1 != null) && (cell2 != null)) {
                    compareTwoCells(cell1, cell2, rowID, colIndex1,colIndex2);
                }
                else if (cell1 == null && (cell2 != null)) {
               	  switch (cell2.getCellType()) {               	 
               	case HSSFCell.CELL_TYPE_NUMERIC:
					
						index++;
						dataMismatchMap.put(index.toString(),
								new DataMismatchDAO(rowID, sourceSheet.getRow(0).getCell(colIndex1).getStringCellValue(),"",
										targetSheet.getRow(0).getCell(colIndex2).getStringCellValue(),
										String.valueOf(cell2.getNumericCellValue())));					                 
                    
                    break;
               	  case HSSFCell.CELL_TYPE_STRING:
               		  index++;                  
						dataMismatchMap.put(index.toString(), new DataMismatchDAO(rowID,
								sourceSheet.getRow(0).getCell(colIndex1).getStringCellValue(),"",
								targetSheet.getRow(0).getCell(colIndex2).getStringCellValue(), cell2.getStringCellValue()));
					  
               		  break;               	  
               	  }
                }
                else if (cell2 == null && (cell1 != null)) {
               	 switch (cell1.getCellType()) {
               	case HSSFCell.CELL_TYPE_NUMERIC:
					
					index++;
					dataMismatchMap.put(index.toString(),
							new DataMismatchDAO(rowID, sourceSheet.getRow(0).getCell(colIndex1).getStringCellValue(),
									String.valueOf(cell1.getNumericCellValue()),
									targetSheet.getRow(0).getCell(colIndex2).getStringCellValue(),""));
                    break;
               	case HSSFCell.CELL_TYPE_STRING:
             		  index++;                  
						dataMismatchMap.put(index.toString(), new DataMismatchDAO(rowID,
								sourceSheet.getRow(0).getCell(colIndex1).getStringCellValue(),cell2.getStringCellValue(),
								targetSheet.getRow(0).getCell(colIndex2).getStringCellValue(), ""));
					  
              		 break;              	 
                }
            }
           }
          }
	}

	// Compare Two Cells column
	public void compareTwoCells(XSSFCell cell1, XSSFCell cell2, String rowID, int sourceColID,int targetColID) {

		int type1 = cell1.getCellType();
		int type2 = cell2.getCellType();
		if (type1 == type2) {
			if (cell1.getCellStyle().equals(cell2.getCellStyle())) {
				// Compare cells based on its type
				switch (cell1.getCellType()) {

				case HSSFCell.CELL_TYPE_NUMERIC:
					if (cell1.getNumericCellValue() != cell2.getNumericCellValue()) {
						index++;
						dataMismatchMap.put(index.toString(),
								new DataMismatchDAO(rowID, sourceSheet.getRow(0).getCell(sourceColID).getStringCellValue(),
										String.valueOf(cell1.getNumericCellValue()),
										targetSheet.getRow(0).getCell(targetColID).getStringCellValue(),
										String.valueOf(cell2.getNumericCellValue())));
					}

					break;
				case HSSFCell.CELL_TYPE_STRING:
					if (!cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
						index++;
						dataMismatchMap.put(index.toString(), new DataMismatchDAO(rowID,
								sourceSheet.getRow(0).getCell(sourceColID).getStringCellValue(), cell1.getStringCellValue(),
								targetSheet.getRow(0).getCell(targetColID).getStringCellValue(), cell2.getStringCellValue()));
					}

					break;

				case HSSFCell.CELL_TYPE_BOOLEAN:
					if (cell1.getBooleanCellValue() != cell2.getBooleanCellValue()) {
						index++;
						dataMismatchMap.put(index.toString(),
								new DataMismatchDAO(rowID,
										String.valueOf(sourceSheet.getRow(0).getCell(sourceColID).getBooleanCellValue()),
										String.valueOf(cell1.getBooleanCellValue()),
										String.valueOf(targetSheet.getRow(0).getCell(targetColID).getBooleanCellValue()),
										String.valueOf(cell2.getBooleanCellValue())));
					}
					break;

				default:
					if (!cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
						index++;
						dataMismatchMap.put(index.toString(), new DataMismatchDAO(rowID,
								sourceSheet.getRow(0).getCell(sourceColID).getStringCellValue(), cell1.getStringCellValue(),
								targetSheet.getRow(0).getCell(targetColID).getStringCellValue(), cell2.getStringCellValue()));
					}
					break;
				}
			}
		}
	}
	
	  private static int findCellIndex(XSSFSheet sheet, String cellContent) {

	        for (Row row : sheet) {
	            for (Cell cell : row) {
	                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	                    if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
	                        return cell.getColumnIndex();  
	                    }
	                }
	            }
	        }               
	        return 0;
	    }

	public void writeOutputFile(String OutputFilePath) throws IOException {

		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet rowMissingSheet = workbook.createSheet("Missing Rows");
		XSSFSheet dataMismatchSheet = workbook.createSheet("Data Mismatch");

		// Create row object
		XSSFRow row;

		// Iterate over data and write to sheet
		int rowNumber = 0;

		// Writing Header for Row Missing Sheet

		for (String headerKey : rowMissingHeaderMap.keySet()) {
			row = rowMissingSheet.createRow(rowNumber++);
			int columnNumber = 0;
			Cell headerCell = row.createCell(columnNumber++);
			headerCell.setCellValue(headerKey);

			headerCell = row.createCell(columnNumber++);
			headerCell.setCellValue(rowMissingHeaderMap.get(headerKey));
		}

		for (String rowMissingKey : rowMissingMap.keySet()) {
			List<String> missingRowList = rowMissingMap.get(rowMissingKey);
			int rowCounter = 0;
			for (String missingRow : missingRowList) {
				row = rowMissingSheet.createRow(rowNumber++);
				int columnNumber = 0;
				Cell cell = row.createCell(columnNumber++);
				if (rowCounter == 0) {
					cell.setCellValue(rowMissingKey);
					rowCounter++;
				}
				cell = row.createCell(columnNumber++);
				cell.setCellValue(missingRow);

			}
		}

		// Iterate over data and write to sheet
		Set<String> keyid1 = dataMismatchMap.keySet();
		rowNumber = 0;
		
		String primaryKey = null;

		for (String key : keyid1) {
			row = dataMismatchSheet.createRow(rowNumber++);
			DataMismatchDAO dataMismatchDAO = dataMismatchMap.get(key);
				
			int cellid = 0;

			Cell cell = row.createCell(cellid++);
			if (primaryKey == null || !primaryKey.equals(dataMismatchDAO.getPrimaryKey())) {
				primaryKey = dataMismatchDAO.getPrimaryKey();
				cell.setCellValue(primaryKey);
			} 
			
			cell = row.createCell(cellid++);
			cell.setCellValue(dataMismatchDAO.getSourceColumnName());

			cell = row.createCell(cellid++);
			cell.setCellValue(dataMismatchDAO.getSourceColumnValue());

			cell = row.createCell(cellid++);
			cell.setCellValue(dataMismatchDAO.getTargetColumnName());

			cell = row.createCell(cellid++);
			cell.setCellValue(dataMismatchDAO.getTargetColumnValue());

		}

		// Write the workbook in file system
		FileOutputStream out = new FileOutputStream(new File(OutputFilePath));

		workbook.write(out);

		workbook.close();

	}
}
