package com.middleware.common;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;

import com.db.common.Configuration;
import com.db.hbase.Column;
import com.db.hbase.HbaseConfiguration;
import com.db.hbase.HbaseObject;
import com.db.hbase.HbaseReader;
import com.db.hbase.HbaseScanner;
import com.db.hbase.HbaseWriter;
import com.db.mongoDB.MongoDBConfiguration;
import com.db.mongoDB.MongoDBObject;
import com.db.mongoDB.MongoDBReader;
import com.db.mongoDB.MongoDBWriter;

public class HeteroDB {
	private MongoDBObject mongoDBObject;
	private HbaseObject hbaseDBObject;
	
	public boolean init() {
		Configuration mongoConf = new MongoDBConfiguration();
		try {
			mongoDBObject = new MongoDBObject(mongoConf);
			hbaseDBObject = new HbaseObject(new HbaseConfiguration());
			return true;
			} catch (MasterNotRunningException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (ZooKeeperConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
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
	public void read(String dbName, String table, String start, int range, Set<Column> fields, Map<Column,String> result) {
		//HBase Read(Scan)
		HbaseScanner hbasescanner = new HbaseScanner(hbaseDBObject);
		hbasescanner.scan(null, table, start, 0, fields, result);
	}
	public void write(String dbName, String table, Map<?, String> content) {
		//HBase Write
		HbaseWriter hbaseWriter = new HbaseWriter(hbaseDBObject);
		hbaseWriter.write(table, null, content);
		//Mongo Write
		MongoDBWriter mongoWriter = new MongoDBWriter(mongoDBObject);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add((Map<String, String>) content);
		mongoWriter.write(dbName, table, list);
	}
}
