package com.heterodb.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.heterodb.common.Configuration;
import com.heterodb.common.DB;
import com.heterodb.db.MongodbInstance;
import com.heterodb.memcache.RedisInstance;

public class Heterodb extends DB {
	
	private static final Logger logger = LoggerFactory.getLogger(Heterodb.class);
	
	RedisInstance rsi;
	MongodbInstance mis;
	
	public Heterodb(Configuration conf) {
		
		init(conf);
	}

	@Override
	public void init(Configuration conf) {
		// TODO Auto-generated method stub
		
		rsi = new RedisInstance();
		mis = new MongodbInstance();
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		rsi.cleanup();
		mis.cleanup();
	}

	/**
	 * read from mongodb and redis, merge the result 
	 */
	
	@Override
	public int read(String database, String table, String key,
			Set<String> fields, Map<String, String> result) {
		// TODO Auto-generated method stub
		
		HashMap<String, String> cache = new HashMap<String, String>();
		mis.read(database, table, key, fields, result);
		rsi.read(database, table, key, fields, cache);
		if(!cache.isEmpty()) {
			for(Map.Entry<String, String> entry : cache.entrySet()) {
				if(result.containsKey(entry.getKey())) {
					result.put(entry.getKey(), entry.getValue());
				}
			}
		}
		else {
			logger.debug("read redis cache is null");
		}
		return 0;
	}

	@Override
	public int update(String database, String table, String key,
			Map<String, String> result) {
		// TODO Auto-generated method stub
		
		return rsi.update(database, table, key, result);
	}

	@Override
	public int insert(String database, String table, String key,
			Map<String, String> values) {
		// TODO Auto-generated method stub
		
		return rsi.insert(database, table, key, values);
	}

	@Override
	public int scan(String database, String table, String startkey,
			int recordcount, Set<String> fields,
			Vector<Map<String, String>> result) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String database, String table, String key) {
		// TODO Auto-generated method stub
		return 0;
	}
}
