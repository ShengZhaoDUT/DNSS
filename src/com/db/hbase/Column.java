package com.db.hbase;
//This is a data structure
public class Column {
	public String columnFamily;
	public String column;
	public String rowname;
	public Column(String r, String cf, String c)
	{
		rowname = r;
		columnFamily = cf;
		column = c;
	}
}
