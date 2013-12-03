package com.middleware.common;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.db.common.Configuration;
import com.db.mongoDB.MongoDBConfiguration;
import com.db.mongoDB.MongoDBObject;
import com.db.mongoDB.MongoDBReader;
import com.db.mongoDB.MongoDBWriter;

public class HeteroDB {
	private MongoDBObject mongoDBObject;
	//private HbaseDBObject hbaseDBObject;
	public boolean init() {
		Configuration mongoConf = new MongoDBConfiguration();
		try {
			mongoDBObject = new MongoDBObject(mongoConf);
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public void close() {
		mongoDBObject.close();
	}
	public void read(String dbName, String table, String key, Set<String> fields, Map<String, String>result) {
		MongoDBReader mongoReader = new MongoDBReader(mongoDBObject);
		mongoReader.read(dbName, table, key, fields, result);
	}
	public void read(String dbName, String table, String start, int range, Set<String> fields) {
		//HBase Read
	}
	public void write(String dbName, String table, Map<String, String> content) {
		//HBase Write
		MongoDBWriter mongoWriter = new MongoDBWriter(mongoDBObject);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(content);
		mongoWriter.write(dbName, table, list);
	}
}
