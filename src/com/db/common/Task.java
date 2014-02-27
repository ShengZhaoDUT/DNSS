package com.db.common;

import java.util.List;

public class Task {
	public String dbName;
	public String table;
	public String op;
	public List<String> rowKey;
	public List<List<item>> value;
	public String timestamp;
	public Task(String dbNameString, String tableName, String operator, List<String> rowSet, List<List<item>> content, String time) {
		dbName = dbNameString;
		table = tableName;
		op = operator;
		rowKey = rowSet;
		value = content;
		timestamp = time;
	}
}
