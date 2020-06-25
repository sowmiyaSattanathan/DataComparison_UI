package com.data.comparison.dao;

public class DataMismatchDAO {

	private String primaryKey;
	private String sourceColumnName;
	private String sourceColumnValue;
	private String targetColumnName;
	private String targetColumnValue;

	public DataMismatchDAO(String primaryKey, String sourceColumnName, String sourceColumnValue,
			String targetColumnName, String targetColumnValue) {
		super();
		// TODO Auto-generated constructor stub
		this.primaryKey = primaryKey;
		this.sourceColumnName = sourceColumnName;
		this.sourceColumnValue = sourceColumnValue;
		this.targetColumnName = targetColumnName;
		this.targetColumnValue = targetColumnValue;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getSourceColumnName() {
		return sourceColumnName;
	}

	public void setSourceColumnName(String sourceColumnName) {
		this.sourceColumnName = sourceColumnName;
	}

	public String getSourceColumnValue() {
		return sourceColumnValue;
	}

	public void setSourceColumnValue(String sourceColumnValue) {
		this.sourceColumnValue = sourceColumnValue;
	}

	public String getTargetColumnName() {
		return targetColumnName;
	}

	public void setTargetColumnName(String targetColumnName) {
		this.targetColumnName = targetColumnName;
	}

	public String getTargetColumnValue() {
		return targetColumnValue;
	}

	public void setTargetColumnValue(String targetColumnValue) {
		this.targetColumnValue = targetColumnValue;
	}

}
