package com.db.common;

public class item {
	public String r; 
	public String cf;
	public String c;
	public String v;
	public item(String row, String columnFamily, String column, String value) {
		r = row;
		cf = columnFamily;
		c = column;
		v = value;
	}
}
