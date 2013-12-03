package com.db.test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.db.common.Configuration;
import com.db.mongoDB.MongoDBConfiguration;
import com.db.mongoDB.MongoDBDeleter;
import com.db.mongoDB.MongoDBObject;
import com.db.mongoDB.MongoDBReader;
import com.db.mongoDB.MongoDBUpdater;
import com.db.mongoDB.MongoDBWriter;


public class test {
	
	public static void main(String args[]) {
		Configuration configuration = new MongoDBConfiguration();
		Map<String, String> map = new HashMap<String, String>();
		map.put("_id", "ShengZhao");
		map.put("Name", "ShengZhao");
		map.put("age", "22");
		List<Map<?, ?>> list = new ArrayList<Map<?,?>>();
		list.add(map);
		MongoDBObject mDbObject;
		try {
			mDbObject = new MongoDBObject(configuration);
			try {
				MongoDBWriter mongoDBWriter = new MongoDBWriter(mDbObject);
				mongoDBWriter.write("test", "user", list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				MongoDBReader mongoDBReader = new MongoDBReader(mDbObject);
				Set<String> set = new HashSet<String>();
				Map<String, String> result = new HashMap<String, String>();
				set.add("Name");
				//set.add("age");
				mongoDBReader.read("test", "user", "ShengZhao", set, result);
				if(result.size() == 0) {
					System.out.println("no return");
				}
				for(Map.Entry<String, String> entry : result.entrySet()) {
					System.out.println("key:" + entry.getKey() + ",value:" + entry.getValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				map.clear();
				map.put("Name", "JiangHua");
				MongoDBUpdater mongoDBUpdater = new MongoDBUpdater(mDbObject);
				if(mongoDBUpdater.update("test", "user", "ShengZhao", map)) {
					System.out.println("Update Successful");
				}
				else {
					System.out.println("Update Failed");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				MongoDBDeleter mongoDBDeleter = new MongoDBDeleter(mDbObject);
				if(mongoDBDeleter.delete("test", "user", "ShengZhao")) {
					System.out.println("Delete Successful");
				}
				else {
					System.out.println("Delete Failed");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}	
}