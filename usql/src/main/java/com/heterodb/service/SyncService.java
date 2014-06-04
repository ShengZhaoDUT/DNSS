/*
 * multi-thread sync
 * before start sync service, need specific sync which db and which db can be used to write
 */
package com.heterodb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import com.heterodb.db.HBaseFactory;
import com.heterodb.db.MongodbFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

/**
 * 
 * @author zs
 * Description: 
 */
public class SyncService {
	
	private static final Logger logger = LoggerFactory.getLogger(SyncService.class);
	
	
	private final int threshold = 100;
	private final String cf = "default";
	private final String mongoDBName = "db";
	private final int threadNum = 50;
	
	public SyncService() {}
	
	public void syncStart(int database) {
		
		//SingleRedis jedisInstance = srf.getInstance();
		Jedis jedis = SingleRedisFactory.getInstance();
		Iterator iterator = getKeys(jedis, database);
		//Pipeline pipeline = jedis.pipelined();
		Set<String> keys = new HashSet<String>();
		ExecutorService executor = Executors.newFixedThreadPool(50);
		int i = 0;
		while(iterator.hasNext()) {
			String key = (String)iterator.next();
			//pipeline.hgetAll(key);
			keys.add(key);
			i++;
			if(i == threshold) {
				i = 0;
				//sync syncThreads = new sync(pipeline);
				sync syncThreads = new sync(jedis, keys);
				executor.execute(syncThreads);
				jedis = SingleRedisFactory.getInstance();
				keys = new HashSet<String>();
				//pipeline = jedis.pipelined();
			}
			if(!iterator.hasNext()) {
				i = 0;
				sync syncThreads = new sync(jedis, keys);
				executor.execute(syncThreads);
			}
		}
		executor.shutdown();
		try {
			while(!executor.awaitTermination(2, TimeUnit.SECONDS));
			logger.debug("waiting...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Iterator getKeys(Jedis jedis, int database) {
		
		if(database != 0) {
			jedis.select(database);
		}
		Iterator it = jedis.keys("*").iterator();
		return it;
	}
	
	class sync implements Runnable {
		
		private Jedis threadJedis;
		private Set<String> threadKeys;
		
		sync(Jedis jedis, Set<String> keys) {
			
			threadJedis = jedis;
			threadKeys = keys;
		}
		
		public void run() {
			String tableName = "";
			logger.debug("new thread: " + Thread.currentThread().getId());
			Pipeline pipeline = threadJedis.pipelined();
			for(String key : threadKeys) {
				pipeline.hgetAll(key);
			}
			Response<List<Object>> response = pipeline.exec();
			List<Object> result = response.get();
			Iterator<Object> iterator = result.iterator();
			Iterator<String> itKey = threadKeys.iterator();
			threadJedis.disconnect();
			Map<String, List<Put>> hContent = new HashMap<String, List<Put>>();
			Map<String, List<DBObject>> mContent = new HashMap<String, List<DBObject>>();
			while(iterator.hasNext()) {
				Map<String, String> keyval = (Map<String, String>)iterator.next();
				String key = itKey.next();
				if(keyval.containsKey("table")) {
					tableName = keyval.get("table");
					Put put = new Put(Bytes.toBytes(key));
					DBObject dbObject = new BasicDBObject();
					dbObject.put("_id", key);
					for(Map.Entry<String, String> entry : keyval.entrySet()) {
						put.add(Bytes.toBytes(cf), Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
						dbObject.put(entry.getKey(), entry.getValue());
					}
					if(hContent.containsKey(tableName)) {
						hContent.get(tableName).add(put);
						mContent.get(tableName).add(dbObject);
					}
					else {
						List<Put> puts = new ArrayList<Put>();
						List<DBObject> dbObjects = new ArrayList<DBObject>();
						puts.add(put);
						dbObjects.add(dbObject);
						hContent.put(tableName, puts);
						mContent.put(tableName, dbObjects);
					}
				}
			}
			for(Map.Entry<String, List<Put>> entry : hContent.entrySet()) {
				// sync to hbase
				HTableInterface htable = HBaseFactory.getHBaseInstance(entry.getKey());
				try {
					htable.put(entry.getValue());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						htable.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			for(Map.Entry<String, List<DBObject>> entry : mContent.entrySet()) {
				// sync to mongodb
				Mongo mongo = MongodbFactory.getMongoInstance();
				DB db = mongo.getDB(mongoDBName);
				DBCollection dbCollection = db.getCollection(entry.getKey());
				WriteResult wr = dbCollection.insert(entry.getValue());
				logger.debug("mongodb write result: " + wr.getN());
			}
		}
	}
}
