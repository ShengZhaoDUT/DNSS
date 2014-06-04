package com.heterodb.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import redis.clients.jedis.Jedis;

import com.heterodb.common.Configuration;
import com.heterodb.common.DB;

public class Redis extends DB{

	private Jedis jedis; 
	
	public Redis(String ip, int port) {
		jedis = new Jedis(ip, port);
	}
	
	@Override
	public void init(Configuration conf) {
		// TODO Auto-generated method stub
		jedis = new Jedis("166.111.69.77", 7000);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		jedis.disconnect();
	}

	@Override
	public int read(String database, String table, String key,
			Set<String> fields, Map<String, String> result) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String database, String table, String key,
			Map<String, String> result) {
		// TODO Auto-generated method stub
		return 0;
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
