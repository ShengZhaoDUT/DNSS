package com.heterodb.service;

import com.heterodb.db.HBaseFactory;
import com.heterodb.db.MongodbFactory;
import com.heterodb.db.MongodbInstance;
import com.heterodb.memcache.Memcache;
import com.heterodb.memcache.MemcacheFactory;

public class SyncService {
	
	private Memcache mc;
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
