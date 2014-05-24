/*
 * multi-thread sync
 * before start sync service, need specific sync which db and which db can be used to write
 */
package com.heterodb.service;

import java.util.Iterator;
import java.util.Set;

import org.apache.hadoop.hbase.util.Threads;
import org.glassfish.grizzly.threadpool.SyncThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.heterodb.db.HBaseFactory;
import com.heterodb.db.MongodbFactory;
import com.heterodb.db.MongodbInstance;
import com.heterodb.memcache.Memcache;
import com.heterodb.memcache.MemcacheFactory;
import com.heterodb.memcache.RedisInstance;

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
		Pipeline pipeline = jedis.pipelined();
		int i = 0;
		while(iterator.hasNext()) {
			String key = (String)iterator.next();
			pipeline.hgetAll(key);
			i++;
			if(i == threshold) {
				i = 0;
				//sync syncThreads = new sync(pipeline);
				sync syncThreads = new sync(pipeline);
				new Thread(syncThreads).start();
				Jedis syncJedis = srf.getInstance();
				pipeline = syncJedis.pipelined();
			}
		}
	}
	
	public boolean syncOnce(String key) {
		
	}
	
	public Iterator getKeys(Jedis jedis, int database) {
		
		if(database != 0) {
			jedis.select(database);
		}
		Iterator it = jedis.keys("*").iterator();
		return it;
	}
	
	class sync implements Runnable {
		
		sync(Pipeline pipeline) {
			
		}
		
		public void run() {
			logger.debug("new thread");
		}
	}
}
