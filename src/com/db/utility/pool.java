package com.db.utility;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.jruby.ext.zlib.RubyZlib.NeedDict;

import com.db.factory.MongoInstance;
import com.db.hbase.HbaseConfiguration;
import com.db.mongoDB.MongoDBConfiguration;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;

public class pool {
	
	private Mongo mongo;
	// 后台执行，线程数并不是很多，不需要开太多的线程，不要每次都重新申请线程
	private int poolSize = 5;
	private int queueSize = 5;
	private ExecutorService syncPool;
	private Map<String, Map> map;
	private getScanner getscanner;
	public pool() {
		
		// create mongo instance
		mongo = MongoInstance.getMongoInstance();
        // create Java thread pool
        syncPool = Executors.newFixedThreadPool(poolSize * queueSize);
        getscanner = new getScanner();
		map = new HashMap<String, Map>();
	}
	public String dataPre() throws IOException {	
		ResultScanner scanner = getscanner.getResult();
		Result first = scanner.next();
		String resultTime = LastUpdateTime.getUpdateTime();
		if(first == null) {
			scanner.close();
			return resultTime;
		}
		for(Result r = first; r != null; r = scanner.next()) {
			for(Cell cell : r.listCells()) {
				String key = new String(CellUtil.cloneQualifier(cell));
				String value = new String(CellUtil.cloneValue(cell));
				System.out.println(key);
				System.out.println(value);
				String[] body = key.split("_");
				if(!map.containsKey(body[1])) {
					//List<DBObject> operator = new ArrayList<DBObject>();
					DBObject dbo = new BasicDBObject();
					dbo.put(body[3], value);
					//operator.add(dbo);
					//List<DBObject> update = new ArrayList<DBObject>();
					//List<DBObject> delete = new ArrayList<DBObject>();
					Map<String, DBObject> dbOP = new HashMap<String, DBObject>();
					dbOP.put(body[2], dbo);
					//dbOP.put(body[2], update);
					//dbOP.put(body[2], delete);
					Map<String, Map> map1 = new HashMap<String, Map>();
					map1.put(body[0], dbOP);
					
					//((ArrayList) map1.get(body[0]).get(body[2])).add(dbo);
					map.put(body[1], map1);
				}
				else if(!(map.get(body[1]).containsKey(body[0]))) {
					//List<DBObject> operator = new ArrayList<DBObject>();
					DBObject dbo = new BasicDBObject();
					dbo.put(body[3], value);
					//List<DBObject> update = new ArrayList<DBObject>();
					//List<DBObject> delete = new ArrayList<DBObject>();
					Map<String, DBObject> dbOP = new HashMap<String, DBObject>();
					dbOP.put(body[2], dbo);
					//dbOP.put(body[2], update);
					//dbOP.put(body[2], delete);
					
					//dbOP.get(body[2]).add(dbo);
					map.get(body[1]).put(body[0], dbOP);
				}
				else if(!(((Map)map.get(body[1]).get(body[0])).containsKey(body[2]))) {
					//List<DBObject> operator = new ArrayList<DBObject>();
					DBObject dbo = new BasicDBObject();
					dbo.put(body[3], value);
					((Map)map.get(body[1]).get(body[0])).put(body[2], dbo);
				}
				else {
					((DBObject)((Map) map.get(body[1]).get(body[0])).get(body[2])).put(body[3], value);
				}
				resultTime = new String(CellUtil.cloneRow(cell)).substring(0, 13);
				System.out.println("resultTime " + resultTime);
			}
		}
		scanner.close();
		System.out.println("Scanner details:");
		/*for(Entry<String, Map> entry : map.entrySet()) {
			System.out.println("DBName: " + entry.getKey());
			Map<String, Map> map1 = entry.getValue();
			for(Entry<String, Map> entry1 : map1.entrySet()) {
				System.out.println("Operator: " + entry1.getKey());
				Map<String, List> map2 = entry1.getValue();
				for(Entry<String, List> entry2 : map2.entrySet()) {
					System.out.println("rowkey : " + entry2.getKey());
					List<DBObject> value = entry2.getValue();
					for(DBObject dbo : value) {
						for(String ele : dbo.keySet()) {
							System.out.println(ele);
							System.out.println(dbo.get(ele));
						}
					}
				}
			}
		}*/
		return resultTime;
	}
	public void syncService() {
		while(true) {
			String syncTime = LastUpdateTime.getUpdateTime();
			try {
				syncTime = dataPre();
				System.out.println(syncTime + " need to sync!");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(map.isEmpty()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				for(Entry<String, Map> element : map.entrySet()) {
					final String dbName = element.getKey();
					System.out.println("dbName:" + dbName);
					Map<String, Map> value = element.getValue();
					for(Entry<String, Map> item : value.entrySet()) {
						final String op = item.getKey();
						System.out.println("op:" + op);
						Map<String, DBObject> log = item.getValue();
						for(Entry<String, DBObject> finalitem : log.entrySet()) {
							String row = finalitem.getKey();
							System.out.println("row:" + row);
							final DBObject finalvalue = finalitem.getValue();
							final DBObject dbObject = new BasicDBObject();
							//DBObject dbo = new BasicDBObject();
							if(op.equals("insert"))
								finalvalue.put("_id", row);
							else if(op.equals("update")) {
								dbObject.put("_id", row);
							}
							syncPool.execute(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									DB db = mongo.getDB("default");
									DBCollection dbCollection = db.getCollection(dbName);
									if(op.equals("insert")) {
										dbCollection.insert(finalvalue);
									}
									else {
										dbCollection.update(dbObject, finalvalue);
									}
								}
								
							});
							try {
								boolean loop = true;
								do {
									loop = !syncPool.awaitTermination(2, TimeUnit.SECONDS);
								} while(loop);
							} catch(InterruptedException e) {
								e.printStackTrace();
							}
							DB db = mongo.getDB("default");
							DBCollection dbCollection = db.getCollection(dbName);
							dbCollection.insert(finalvalue);
							/*System.out.println("show details:");
							for(DBObject tmp : finalvalue) {
								//Map<String, String> map = tmp.toMap();
								for(String ele : tmp.keySet()) {
									System.out.println(ele);
									System.out.println(tmp.get(ele));
								}
							}*/
						}
					}
				}
			}
			map.clear();
			LastUpdateTime.setUpdateTime(syncTime);
			System.out.println("The data from " + syncTime + " complete!");
		}
	}
}
