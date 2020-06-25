package com.data.comparison.dao;

import java.util.List;
import java.util.Map;

public class DataComparisonResultsDAO {

	public static Map<String, List<String>> rowMissingMap;
	public static Map<String, DataMismatchDAO> dataMismatchMap;

	public static Map<String, List<String>> getRowMissingMap() {
		return rowMissingMap;
	}

	public static void setRowMissingMap(Map<String, List<String>> rowMissingMap) {
		DataComparisonResultsDAO.rowMissingMap = rowMissingMap;
	}

	public static Map<String, DataMismatchDAO> getDataMismatchMap() {
		return dataMismatchMap;
	}

	public static void setDataMismatchMap(Map<String, DataMismatchDAO> dataMismatchMap) {
		DataComparisonResultsDAO.dataMismatchMap = dataMismatchMap;
	}

}
