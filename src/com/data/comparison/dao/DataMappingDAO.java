package com.data.comparison.dao;

public class DataMappingDAO {
	public int primaryKey;
	public String ColumnName;
	
	public DataMappingDAO(int primaryKey, String ColumnName) {
		super();
		// TODO Auto-generated constructor stub
		this.primaryKey = primaryKey;
		this.ColumnName = ColumnName;
		
	}

	public int getPrimaryKey() {
		return primaryKey;
	}
	
	public void setPrimaryKey(int primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	
	public String getColumnName() {
		return ColumnName;
	}
	
	public void setColumnName(String columnName) {
		ColumnName = columnName;
	}

}
