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

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

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
	public pool(MongoDBConfiguration conf) {
		try {
			// create mongo instance
			mongo = new Mongo(conf.getDBMasterHost(), conf.getDBMasterPort());
			MongoOptions opt = mongo.getMongoOptions();
            opt.connectionsPerHost = poolSize;
            opt.threadsAllowedToBlockForConnectionMultiplier = queueSize;
            // create Java thread pool
            syncPool = Executors.newFixedThreadPool(poolSize * queueSize);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map = new HashMap<String, Map>();
	}
	public Map sync(ResultScanner scanner) throws IOException {
		for(Result r = scanner.next(); r != null; r = scanner.next()) {
			
			for(Cell cell : r.listCells()) {
				//String op = new String (CellUtil.cloneFamily(cell));
				String key = new String(CellUtil.cloneQualifier(cell));
				String value = new String(CellUtil.cloneValue(cell));
				/*if(key.equals("op")) {
					
				}*/
				String[] body = key.split("_");
				if(!map.containsKey(body[1])) {
					List<DBObject> insert = new ArrayList<DBObject>();
					List<DBObject> update = new ArrayList<DBObject>();
					List<DBObject> delete = new ArrayList<DBObject>();
					Map<String, List<DBObject>> dbOP = new HashMap<String, List<DBObject>>();
					dbOP.put(body[2], insert);
					dbOP.put(body[2], update);
					dbOP.put(body[2], delete);
					Map<String, Map> map1 = new HashMap<String, Map>();
					map1.put(body[0], dbOP);
					DBObject dbo = new BasicDBObject();
					dbo.put(body[3], value);
					((ArrayList) map1.get(body[0]).get(body[2])).add(dbo);
					map.put(body[1], map1);
				}
				else if(!map.get(body[1]).containsKey(body[0])) {
					List<DBObject> insert = new ArrayList<DBObject>();
					List<DBObject> update = new ArrayList<DBObject>();
					List<DBObject> delete = new ArrayList<DBObject>();
					Map<String, List<DBObject>> dbOP = new HashMap<String, List<DBObject>>();
					dbOP.put(body[2], insert);
					dbOP.put(body[2], update);
					dbOP.put(body[2], delete);
					DBObject dbo = new BasicDBObject();
					dbo.put(body[3], value);
					dbOP.get(body[2]).add(dbo);
					map.get(body[1]).put(body[0], dbOP);
				}
				else {
					DBObject dbo = new BasicDBObject();
					dbo.put(body[3], value);
					((List)((Map) map.get(body[1]).get(body[0])).get(body[2])).add(dbo);
				}
			}
		}
		scanner.close();
		return map;
	}
	public void syncService() {
		while(true) {
			if(map.isEmpty()) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				for(Entry<String, Map> element : map.entrySet()) {
					final String dbName = element.getKey();
					Map<String, Map> value = element.getValue();
					for(Entry<String, Map> item : value.entrySet()) {
						String op = item.getKey();
						Map<String, List> log = item.getValue();
						for(Entry<String, List> finalitem : log.entrySet()) {
							String row = finalitem.getKey();
							final List<DBObject> finalvalue = finalitem.getValue();
							DBObject dbo = new BasicDBObject();
							dbo.put("_id", row);
							syncPool.execute(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									DB db = mongo.getDB("default");
									DBCollection dbCollection = db.getCollection(dbName);
									dbCollection.insert(finalvalue);
								}
								
							});
						}
					}
				}
			}
			map.clear();
		}
	}
}
