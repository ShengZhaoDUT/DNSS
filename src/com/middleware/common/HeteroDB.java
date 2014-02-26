package com.middleware.common;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.thrift2.generated.THBaseService.Processor.put;
import org.apache.hadoop.hbase.util.Bytes;

import com.db.common.item;
import com.db.configure.DataBase;
import com.db.hbase.HbaseConfiguration;
import com.db.hbase.HbaseObject;
import com.db.mongoDB.MongoDBConfiguration;
import com.db.mongoDB.MongoDBObject;
import com.db.mongoDB.MongoDBReader;

public class HeteroDB {
	private MongoDBObject mongoDBObject;
	private HbaseObject hbaseDBObject;
	private Configuration conf;
	
	public HeteroDB(MongoDBConfiguration mongConf, HbaseConfiguration hbaseConf) throws IOException {
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
	public void insert(String dbName, String table, Map<String, List<item>> content) {
		List<Put> result = new ArrayList<Put>();
		List<Put> oplog = new ArrayList<Put>();
		List<item> value = null;
		String row = null;
		for(Map.Entry<String, List<item>> mapEle : content.entrySet()) {
			row = mapEle.getKey();
			value = mapEle.getValue();
			Put element = new Put(Bytes.toBytes(row));
			for(item it : value) {
				element.add(Bytes.toBytes(it.cf), Bytes.toBytes(it.c), Bytes.toBytes(it.v));
			}
			result.add(element);
		}
		item tmp = content.get(row).get(0);
		try {
			// create table
			HTable htable = new HTable(conf, Bytes.toBytes(table));
			htable.put(result);
			//long timestamp = HConstants.LATEST_TIMESTAMP;
			// get timestamp, it's server's clock
			Get get = new Get(Bytes.toBytes(row));
			get.addColumn(Bytes.toBytes(tmp.cf), Bytes.toBytes(tmp.c));
			Result result_foo = htable.get(get);
			long timestamp = result_foo.rawCells()[0].getTimestamp();
			System.out.println(timestamp);
			String prefix = String.valueOf(timestamp);
			for(Map.Entry<String, List<item>> mapEle : content.entrySet()) {
				row = mapEle.getKey();
				value = mapEle.getValue();
				Put log = new Put(Bytes.toBytes(prefix + String.valueOf(row.hashCode())));
				//log.add(Bytes.toBytes("insert"), Bytes.toBytes("op"), Bytes.toBytes("i"));
				//log.add(Bytes.toBytes("insert"), Bytes.toBytes("table"), Bytes.toBytes(table));
				for(item it : value) {
					log.add(Bytes.toBytes("oplog"), Bytes.toBytes("insert_" + table + "_" + row + "_" + it.c), Bytes.toBytes(it.v));
				}
				oplog.add(log);
			}
			htable.close();
			htable = new HTable(conf, Bytes.toBytes("oplog"));
			htable.put(oplog);
			htable.close();
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
			HTable htable = new HTable(conf, Bytes.toBytes(table));
			htable.put(put);
			Get get = new Get(Bytes.toBytes(row));
			item tmp = content.get(0);
			get.addColumn(Bytes.toBytes(tmp.cf), Bytes.toBytes(tmp.c));
			Result result_foo = htable.get(get);
			long timestamp = result_foo.rawCells()[0].getTimestamp();
			String prefix = String.valueOf(timestamp);
			htable.close();
			htable = new HTable(conf, Bytes.toBytes("oplog"));
			Put log = new Put(Bytes.toBytes(prefix + String.valueOf(row.hashCode())));
			//log.add(Bytes.toBytes("insert"), Bytes.toBytes("op"), Bytes.toBytes("i"));
			//log.add(Bytes.toBytes("insert"), Bytes.toBytes("key"), Bytes.toBytes(row));
			//log.add(Bytes.toBytes("insert"), Bytes.toBytes("table"), Bytes.toBytes(table));
			for(item it : content) {
				log.add(Bytes.toBytes("oplog"), Bytes.toBytes("insert_" + table + "_" + row + "_" + it.c), Bytes.toBytes(it.v));
			}
			oplog.add(log);
			htable.close();
			htable = new HTable(conf, Bytes.toBytes("oplog"));
			htable.put(oplog);
			htable.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void insert(String dbName, String table, String row, item content) {
		Put put = new Put(Bytes.toBytes(row));
		put.add(Bytes.toBytes(content.cf), Bytes.toBytes(content.c), Bytes.toBytes(content.v));
		try {
			HTable htable = new HTable(conf, Bytes.toBytes(table));
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
			log.add(Bytes.toBytes("insert_" + table + "_" + row), Bytes.toBytes(content.c), Bytes.toBytes(content.v));
			htable.close();
			htable = new HTable(conf, Bytes.toBytes("oplog"));
			htable.put(log);
			htable.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void update(String dbName, String table, List<String> rowSet, List<List> content) {
		List<Put> result = new ArrayList<Put>();
		List<Put> oplog = new ArrayList<Put>();
		for(String rowString : rowSet) {
			int i = 0;
			List<item> value = content.get(i);
			Put put = new Put(Bytes.toBytes(rowString));
			for(item it : value) {
				put.add(Bytes.toBytes(it.cf), Bytes.toBytes(it.c), Bytes.toBytes(it.v));
			}
			result.add(put);
			i++;
		}
		try {
			HTable hTable = new HTable(conf, table);
			hTable.put(result);
			Get get = new Get(Bytes.toBytes(rowSet.get(0)));
			Result result_foo = hTable.get(get);
			long timestamp = result_foo.rawCells()[0].getTimestamp();
			String prefix = String.valueOf(timestamp);
			hTable.close();
			for(String row : rowSet) {
				int i = 0;
				List<item> value = content.get(i);
				Put log = new Put(Bytes.toBytes(prefix + String.valueOf(value.hashCode())));
				for(item it : value) {
					log.add(Bytes.toBytes("oplog"), Bytes.toBytes("update_" + table + "_" + row + "_" + it.c), Bytes.toBytes(it.v));
				}
				oplog.add(log);
				hTable.put(log);
				hTable.close();
			}
			hTable = new HTable(conf, "oplog");
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
			hTable = new HTable(conf, table);
			hTable.put(result);
			Get get = new Get(Bytes.toBytes(row));
			Result result_foo = hTable.get(get);
			long timestamp = result_foo.rawCells()[0].getTimestamp();
			String prefix = String.valueOf(timestamp);
			Put oplog = new Put(Bytes.toBytes(prefix + String.valueOf(content.hashCode())));
			oplog.add(Bytes.toBytes("oplog"), Bytes.toBytes("update_" + table + "_" + row + "_" + content.c), Bytes.toBytes(content.v));
			hTable.close();
			hTable = new HTable(conf, "oplog");
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
		MongoDBReader mongoReader = new MongoDBReader(mongoDBObject);
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
