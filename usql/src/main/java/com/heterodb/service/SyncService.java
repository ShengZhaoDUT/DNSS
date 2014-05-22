/*
 * multi-thread sync
 * before start sync service, need specific sync which db and which db can be used to write
 */
package com.heterodb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.heterodb.db.HBaseFactory;
import com.heterodb.db.MongodbFactory;
import com.heterodb.db.MongodbInstance;
import com.heterodb.memcache.Memcache;
import com.heterodb.memcache.MemcacheFactory;
import com.heterodb.memcache.RedisInstance;

public class SyncService {
	
	private static final Logger logger = LoggerFactory.getLogger(SyncService.class);
	
	private RedisInstance memcache;
	private MongodbInstance mi;
	private HBaseFactory hf;
	
	public SyncService(MemcacheFactory mf, HBaseFactory hf, MongodbFactory mof) {
		this.hf = hf;
		mc = new Memcache(mf);
		mi = new MongodbInstance(mof);
	}
	
	public void start() {
		
	}
}
