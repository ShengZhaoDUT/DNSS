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
import com.db.factory.HBaseInstance;
import com.db.factory.MongoInstance;
import com.db.hbase.Column;
import com.db.hbase.HbaseConfiguration;
import com.db.mongoDB.MongoDBConfiguration;
import com.db.utility.LastUpdateTime;
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
		/*DataBase.setMongoHost("166.111.131.99");
		DataBase.setMongoPort(8188);
		DataBase.setdbName("test");
		DataBase.setHBaseHost("localhost");
		DataBase.setHBasePort(2181);
		MongoDBConfiguration mongoConf = new MongoDBConfiguration();
		HbaseConfiguration hbaseConf = new HbaseConfiguration();*/
		HBaseInstance.setAndInit("localhost", 2181);
		MongoInstance.setAndInit("166.111.131.99", 8188, 5, 5);
		HeteroDB db = new HeteroDB();
		List<item> content = new ArrayList<item>();
		//item item1 = new item("details", "name", "111");
		//item item2 = new item("details", "email", "111");
		//item item3 = new item("details", "age", "111");
		List<String> key = new ArrayList<String>();
		List<List> value = new ArrayList<List>();
		//content.add(item1);
		//content.add(item2);
		//content.add(item3);
		//map.put("1", content);
		//db.insert("dbName", "test", map);
		
		// test1: insert(String dbName, String table, Map<String, List<item>> content)
		/*item item1 = new item("details", "name", "zhaosheng1");
		item item2 = new item("details", "email", "zszhaoshengzs1@163.com");
		item item3 = new item("details", "age", "123");
		
		content.add(item1);
		content.add(item2);
		content.add(item3);
		
		Map<String, List<item>> map = new HashMap<String, List<item>>();
		map.put("1", content);
		
		db.insert(dbName, table, map);
		*/
		// test2: insert(String dbName, String table, String row, List<item> content)
		
		/*item item1 = new item("details", "name", "zhaosheng1");
		item item2 = new item("details", "email", "zszhaoshengzs1@163.com");
		item item3 = new item("details", "age", "123");
		
		content.add(item1);
		content.add(item2);
		content.add(item3);
		
		db.insert(dbName, table, "1", content);*/
		
		// test3: insert(String dbName, String table, String row, item content)
		/*item item1 = new item("details", "name", "zhaosheng1");
		
		db.insert(dbName, table, "1", item1);*/
		
		// test4: update(String dbName, String table, List<String> rowSet, List<List> content)
		item item1 = new item("details", "name", "zhaosheng1");
		item item2 = new item("details", "email", "zszhaoshengzs1@163.com");
		item item3 = new item("details", "age", "123");
		
		content.add(item1);
		content.add(item2);
		content.add(item3);
		
		db.insert(dbName, table, "1", content);
		
		item1 = new item("details", "name", "jianghua");
		item2 = new item("details", "address", "qinghe");
		content.add(item1);
		content.add(item2);
		key.add("1");
		value.add(content);
		db.update(dbName, table, key, value);
		
		//key.add("1");
		//value.add(content);
		
		//db.update("dbName", "test", key, value);
		
		LastUpdateTime.setUpdateTime("1393162794329");
		pool syncPool = new pool();
		syncPool.syncService();
		//Column c = new Column("article", "title", )
	}
}