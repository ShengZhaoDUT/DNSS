package com.db.hbase;
//This is a data structure
public class Column {
	public String columnFamily;
	public String column;
	public Column(String cf, String c)
	{
		columnFamily = cf;
		column = c;
	}
}
