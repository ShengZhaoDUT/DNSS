/*
 * multi-thread sync
 * before start sync service, need specific sync which db and which db can be used to write
 */
package com.heterodb.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Threads;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import com.heterodb.db.HBaseFactory;
import com.heterodb.db.MongodbFactory;

/**
 * 
 * @author zs
 * Description: 
 */
public class SyncService {
	
	private static final Logger logger = LoggerFactory.getLogger(SyncService.class);
	
	private SingleRedisFactory srf;
	private MongodbFactory mof;
	private HBaseFactory hf;
	private final int threshold = 100;
	
	public SyncService(HBaseFactory hf, MongodbFactory mof, SingleRedisFactory srf) {
		
		this.srf = srf;
		this.mof = mof;
		this.hf = hf;
	}
	
	public void syncStart(int database) {
		
		//SingleRedis jedisInstance = srf.getInstance();
		Jedis jedis = srf.getInstance();
		Iterator iterator = getKeys(jedis, database);
		//Pipeline pipeline = jedis.pipelined();
		Set<String> keys = new HashSet<String>();
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
				new Thread(syncThreads).start();
				jedis = srf.getInstance();
				keys = new HashSet<String>();
				//pipeline = jedis.pipelined();
			}
			if(!iterator.hasNext()) {
				i = 0;
				sync syncThreads = new sync(jedis, keys);
				new Thread(syncThreads).start();
			}
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
			Map<String, List<Put>> hContent = new HashMap<String, List<Put>>();
			Map<String, List<DBObject>> mContent = new HashMap<String, List<DBObject>>();
			while(iterator.hasNext()) {
				Map<String, String> keyval = (Map<String, String>)iterator.next();
				if(keyval.containsKey("table")) {
					tableName = keyval.get("table");
					
				}
			}
		}
	}
}
