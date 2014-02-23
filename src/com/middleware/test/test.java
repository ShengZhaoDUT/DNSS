package com.middleware.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.db.common.Configuration;
import com.db.common.item;
import com.db.configure.DataBase;
import com.db.hbase.Column;
import com.db.hbase.HbaseConfiguration;
import com.db.mongoDB.MongoDBConfiguration;
import com.db.utility.getScanner;
import com.db.utility.pool;
import com.middleware.common.HeteroDB;

public class test {
	private static String dbName = "test";
	private static String table = "user";
	public static void main(String args[]) throws IOException {
		/*HeteroDB db = new HeteroDB();
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
		}*/
		DataBase.setMongoHost("166.111.131.99");
		DataBase.setMongoPort(8188);
		DataBase.setdbName("test");
		DataBase.setHBaseHost("localhost");
		DataBase.setHBasePort(2181);
		MongoDBConfiguration mongoConf = new MongoDBConfiguration();
		HbaseConfiguration hbaseConf = new HbaseConfiguration();
		HeteroDB db = new HeteroDB(mongoConf, hbaseConf);
		List<item> content = new ArrayList<item>();
		item item1 = new item("details", "name", "111");
		item item2 = new item("details", "email", "111");
		item item3 = new item("details", "age", "111");
		Map<String, List<item>> map = new HashMap<String, List<item>>();
		content.add(item1);
		content.add(item2);
		content.add(item3);
		map.put("1", content);
		db.insert("dbName", "test", map);
		getScanner getscanner = new getScanner(hbaseConf);
		pool syncPool = new pool(mongoConf);
		syncPool.syncService();
		//Column c = new Column("article", "title", )
	}
}