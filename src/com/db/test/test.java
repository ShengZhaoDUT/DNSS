package com.db.test;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.db.common.Configuration;
import com.db.mongoDB.MongoDBConfiguration;
import com.db.mongoDB.MongoDBObject;
import com.db.mongoDB.MongoDBWriter;


public class test {
	
	public static void main(String args[]) {
		Configuration configuration = new MongoDBConfiguration();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Name", "ShengZhao");
		map.put("age", "22");
		List<Map<?, ?>> list = new ArrayList<Map<?,?>>();
		list.add(map);
		try {
			MongoDBObject mDbObject = new MongoDBObject(configuration);
			MongoDBWriter mongoDBWriter = new MongoDBWriter(mDbObject);
			mongoDBWriter.write("user", "test", list);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}