package com.middleware.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.middleware.common.HeteroDB;

public class test {
	private static String dbName = "test";
	private static String table = "user";
	public static void main(String args[]) {
		HeteroDB db = new HeteroDB();
		if(db.init()) {
			Map<String, String> content = new HashMap<String, String>();
			content.put("_id", "ShengZhao");
			content.put("Name", "zs");
			content.put("age", "22");
			db.write(dbName, table, content);
			content.clear();
			String key = "ShengZhao";
			Set<String> fields = new HashSet<String>();
			fields.add("Name");
			fields.add("age");
			db.read(dbName, table, key, fields, content);
			if(content.size() != 0)
				for(Map.Entry<String, String> entry : content.entrySet()) {
					System.out.println("key:" + entry.getKey() + ", value:" + entry.getValue());
				}
		}
	}
}
