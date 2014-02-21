package com.middleware.common;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.db.common.item;
import com.db.configure.DataBase;
import com.db.hbase.HbaseConfiguration;
import com.db.hbase.HbaseObject;
import com.db.mongoDB.MongoDBConfiguration;
import com.db.mongoDB.MongoDBObject;

public class HeteroDB {
	private MongoDBObject mongoDBObject;
	private HbaseObject hbaseDBObject;
	private Configuration conf;
	
	public HeteroDB(MongoDBConfiguration mongConf, HbaseConfiguration hbaseConf) {
		try {
			mongoDBObject = new MongoDBObject(mongConf);
			hbaseDBObject = new HbaseObject(hbaseConf);
			conf = hbaseDBObject.getConf();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(MasterNotRunningException e) {
			e.printStackTrace();
		} catch(ZooKeeperConnectionException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		mongoDBObject.close();
	}
	// 插入成功后要求返回时间戳，这个是hbase时间，便于同步，然后作为oplog插入的rowkey
	public void insert(String dbName, String table, List<item> content) {
		List<Put> result = new ArrayList<Put>();
		List<Put> oplog = new ArrayList<Put>();
		for(item it : content) {
			Put element = new Put(Bytes.toBytes(it.r));
			element.add(Bytes.toBytes(it.cf), Bytes.toBytes(it.c), Bytes.toBytes(it.v));
			result.add(element);
			// create table
			Put log = new Put();
		}
	}
	
	public void insert(String dbName, String table, item content) {
		
	}
	
	public void update(String dbName, String table, List<item> content) {
		
	}
	
	public void update(String dbName, String table, item content) {
		
	}
	/*public void read(String dbName, String table, String key, Set<String> fields, Map<String, String>result) {
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
	}*/
}
