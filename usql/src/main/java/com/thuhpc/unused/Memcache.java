package com.thuhpc.unused;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import redis.clients.jedis.Jedis;

import com.heterodb.common.Configuration;
import com.heterodb.common.DB;

public class Memcache extends DB{
	
	// it is a redis cache
	
	private Jedis jedis;
	
	public Memcache(MemcacheFactory memcacheFactory) {
		jedis = memcacheFactory.getInstance();
	}
	
	public void init() {}
	
	public void cleanup() {
		jedis.disconnect();
	}
	
	@Override
	public int read(String database, String table, String key, Set<String> fields,
			Map<String, String> result) {
		// TODO Auto-generated method stub
		if(fields == null) {
			result.putAll(jedis.hgetAll(key));
		}
		else {
			String[] fieldArray = fields.toArray(new String[fields.size()]);
			List<String> values = jedis.hmget(key, fieldArray);
			Iterator<String> fieldIterator = fields.iterator();
			Iterator<String> valueIterator = values.iterator();
			while(fieldIterator.hasNext()) {
				result.put(fieldIterator.next(), valueIterator.next());
			}
		}
		return result.isEmpty() ? 1 : 0;
	}

	@Override
	public int update(String database, String table, String key,
			Map<String, String> result) {
		// TODO Auto-generated method stub
		return jedis.hmset(key, result).equals("OK") ? 0 : 1;
	}

	@Override
	public int insert(String database, String table, String key,
			Map<String, String> values) {
		// TODO Auto-generated method stub
		if((jedis.hmset(key, values)).equals("OK")) {
			return 0;
		}
		return 1;
	}

	@Override
	public int delete(String database, String table, String key) {
		// TODO Auto-generated method stub
		return jedis.del(key) == 0 ? 1 : 0;
	}

	@Override
	public int scan(String database, String table, String startkey, int recordcount,
			Set<String> fields, Vector<Map<String, String>> result) {
		// TODO Auto-generated method stub
		
		return 0;
	}
}
