package com.db.common;

public class item {
	public String cf;
	public String c;
	public String v;
	public item(String columnFamily, String column, String value) {
		cf = columnFamily;
		c = column;
		v = value;
	}
}
