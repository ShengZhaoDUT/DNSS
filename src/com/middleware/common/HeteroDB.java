package com.middleware.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.jruby.ext.zlib.RubyZlib.NeedDict;

import com.db.common.Task;
import com.db.common.TaskQueue;
import com.db.common.item;
import com.db.factory.HBaseInstance;
import com.db.factory.MongoInstance;
import com.db.mongoDB.MongoDBReader;
import com.mongodb.Mongo;

public class HeteroDB {
	//private MongoDBObject mongoDBObject;
	//private HbaseObject hbaseDBObject;
	private Mongo mongo = null;
	private String logName = "oplog";
	
	public HeteroDB() throws IOException {
		mongo = MongoInstance.getMongoInstance();
	}
	
	// 插入成功后要求返回时间戳，这个是hbase时间，便于同步，然后作为oplog插入的rowkey
	public void insert(String dbName, String table, List<String> rowSet, List<List<item>> content) {
		List<Put> result = new ArrayList<Put>();
		List<Put> oplog = new ArrayList<Put>();
		List<item> value = null;
		long timestamp = 0;
		int i = 0;
		for(String row : rowSet) {
			value = content.get(i);
			Put element = new Put(Bytes.toBytes(row));
			for(item it : value) {
				element.add(Bytes.toBytes(it.cf), Bytes.toBytes(it.c), Bytes.toBytes(it.v));
			}
			result.add(element);
			i++;
		}
		item tmp = content.get(i-1).get(0);
		String rowtmp = rowSet.get(i-1);
		try {
			// create table
			HTable htable = (HTable)HBaseInstance.getHBaseInstance(table);
			htable.put(result);
			//long timestamp = HConstants.LATEST_TIMESTAMP;
			// get timestamp, it's server's clock
			Get get = new Get(Bytes.toBytes(rowtmp));
			get.addColumn(Bytes.toBytes(tmp.cf), Bytes.toBytes(tmp.c));
			Result result_foo = htable.get(get);
			timestamp = result_foo.rawCells()[0].getTimestamp();
			System.out.println(timestamp);
			String prefix = String.valueOf(timestamp);
			i = 0;
			for(String row : rowSet) {
				value = content.get(i);
				Put log = new Put(Bytes.toBytes(prefix + String.valueOf(value.hashCode())));
				for(item it : value) {
					log.add(Bytes.toBytes("oplog"), Bytes.toBytes("insert_" + table + "_" + row + "_" + it.c), Bytes.toBytes(it.v));
				}
				oplog.add(log);
				i++;
			}
			htable.close();
			//htable = new HTable(conf, Bytes.toBytes("oplog"));
			htable = (HTable)HBaseInstance.getHBaseInstance(logName);
			htable.put(oplog);
			htable.close();
			Task task = new Task(dbName, table, "insert", rowSet, content, String.valueOf(timestamp));
			TaskQueue.queue.add(task);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert(String dbName, String table, String row, List<item> content) {
		//List<Put> result = new ArrayList<Put>();
		List<Put> oplog = new ArrayList<Put>();
		Put put = new Put(Bytes.toBytes(row));
		for(item it : content) {
			put.add(Bytes.toBytes(it.cf), Bytes.toBytes(it.c), Bytes.toBytes(it.v));
			//result.add(put);
		}
		try {
			//HTable htable = new HTable(conf, Bytes.toBytes(table));
			HTable htable = (HTable)HBaseInstance.getHBaseInstance(table);
			htable.put(put);
			Get get = new Get(Bytes.toBytes(row));
			item tmp = content.get(0);
			get.addColumn(Bytes.toBytes(tmp.cf), Bytes.toBytes(tmp.c));
			Result result_foo = htable.get(get);
			long timestamp = result_foo.rawCells()[0].getTimestamp();
			String prefix = String.valueOf(timestamp);
			htable.close();
			//htable = new HTable(conf, Bytes.toBytes("oplog"));
			htable = (HTable)HBaseInstance.getHBaseInstance(logName);
			Put log = new Put(Bytes.toBytes(prefix + String.valueOf(row.hashCode())));
			//log.add(Bytes.toBytes("insert"), Bytes.toBytes("op"), Bytes.toBytes("i"));
			//log.add(Bytes.toBytes("insert"), Bytes.toBytes("key"), Bytes.toBytes(row));
			//log.add(Bytes.toBytes("insert"), Bytes.toBytes("table"), Bytes.toBytes(table));
			for(item it : content) {
				log.add(Bytes.toBytes(logName), Bytes.toBytes("insert_" + table + "_" + row + "_" + it.c), Bytes.toBytes(it.v));
			}
			oplog.add(log);
			htable.put(oplog);
			htable.close();
			List<String> rowSet = new ArrayList<String>();
			rowSet.add(row);
			List<List<item>> contentSet = new ArrayList<List<item>>();
			contentSet.add(content);
			Task task = new Task(dbName, table, "insert", rowSet, contentSet, String.valueOf(timestamp));
			TaskQueue.queue.add(task);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void insert(String dbName, String table, String row, item content) {
		Put put = new Put(Bytes.toBytes(row));
		put.add(Bytes.toBytes(content.cf), Bytes.toBytes(content.c), Bytes.toBytes(content.v));
		try {
			//HTable htable = new HTable(conf, Bytes.toBytes(table));
			HTable htable = (HTable)HBaseInstance.getHBaseInstance(table);
			htable.put(put);
			Get get = new Get(Bytes.toBytes(row));
			get.addColumn(Bytes.toBytes(content.cf), Bytes.toBytes(content.c));
			Result result_foo = htable.get(get);
			long timestamp = result_foo.rawCells()[0].getTimestamp();
			String prefix = String.valueOf(timestamp);
			Put log = new Put(Bytes.toBytes(prefix + String.valueOf(row.hashCode())));
			//log.add(Bytes.toBytes("insert"), Bytes.toBytes("op"), Bytes.toBytes("i"));
			//log.add(Bytes.toBytes("insert"), Bytes.toBytes(content.c), Bytes.toBytes(content.v));
			//log.add(Bytes.toBytes("insert"), Bytes.toBytes("key"), Bytes.toBytes(row));
			//log.add(Bytes.toBytes("insert"), Bytes.toBytes("table"), Bytes.toBytes(table));
			log.add(Bytes.toBytes(logName), Bytes.toBytes("insert_" + table + "_" + row + "_" + content.c), Bytes.toBytes(content.v));
			htable.close();
			htable = (HTable)HBaseInstance.getHBaseInstance(logName);
			htable.put(log);
			htable.close();
			List<String> rowSet = new ArrayList<String>();
			rowSet.add(row);
			List<item> con = new ArrayList<item>();
			con.add(content);
			List<List<item>> contentSet = new ArrayList<List<item>>();
			contentSet.add(con);
			Task task = new Task(dbName, table, "insert", rowSet, contentSet, String.valueOf(timestamp));
			TaskQueue.queue.add(task);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void update(String dbName, String table, List<String> rowSet, List<List> content) {
		List<Put> result = new ArrayList<Put>();
		List<Put> oplog = new ArrayList<Put>();
		int i = 0;
		for(String rowString : rowSet) {
			List<item> value = content.get(i);
			Put put = new Put(Bytes.toBytes(rowString));
			for(item it : value) {
				put.add(Bytes.toBytes(it.cf), Bytes.toBytes(it.c), Bytes.toBytes(it.v));
			}
			result.add(put);
			i++;
		}
		try {
			HTable hTable = (HTable)HBaseInstance.getHBaseInstance(table);
			hTable.put(result);
			Get get = new Get(Bytes.toBytes(rowSet.get(0)));
			item tmp = (item)content.get(0).get(0);
			get.addColumn(Bytes.toBytes(tmp.cf), Bytes.toBytes(tmp.c));
			Result result_foo = hTable.get(get);
			long timestamp = result_foo.rawCells()[0].getTimestamp();
			String prefix = String.valueOf(timestamp);
			System.out.println(timestamp + " Get!");
			hTable.close();
			i = 0;
			for(String row : rowSet) {
				
				List<item> value = content.get(i);
				Put log = new Put(Bytes.toBytes(prefix + String.valueOf(value.hashCode())));
				for(item it : value) {
					log.add(Bytes.toBytes("oplog"), Bytes.toBytes("update_" + table + "_" + row + "_" + it.c), Bytes.toBytes(it.v));
				}
				oplog.add(log);
				i++;
			}
			hTable = (HTable)HBaseInstance.getHBaseInstance(logName);
			hTable.put(oplog);
			hTable.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(String dbName, String table, String row, item content) {
		Put result = new Put(Bytes.toBytes(row));
		result.add(Bytes.toBytes(content.cf), Bytes.toBytes(content.c), Bytes.toBytes(content.v));
		HTable hTable;
		try {
			hTable = (HTable)HBaseInstance.getHBaseInstance(table);
			hTable.put(result);
			Get get = new Get(Bytes.toBytes(row));
			Result result_foo = hTable.get(get);
			long timestamp = result_foo.rawCells()[0].getTimestamp();
			String prefix = String.valueOf(timestamp);
			Put oplog = new Put(Bytes.toBytes(prefix + String.valueOf(content.hashCode())));
			oplog.add(Bytes.toBytes("oplog"), Bytes.toBytes("update_" + table + "_" + row + "_" + content.c), Bytes.toBytes(content.v));
			hTable.close();
			hTable = (HTable)HBaseInstance.getHBaseInstance(logName);
			hTable.put(oplog);
			hTable.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void delete(String dbName, String table, String key) {
		
	}
	public void read(String dbName, String table, String key, Set<String> fields, Map<String, String>result) {
		MongoDBReader mongoReader = new MongoDBReader(mongo);
		mongoReader.read(dbName, table, key, fields, result);
	}
	/*public void read(String dbName, String table, String start, int range, Set<Column> fields, Map<Column,String> result) {
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
