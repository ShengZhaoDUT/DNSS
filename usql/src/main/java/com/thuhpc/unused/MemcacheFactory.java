package com.thuhpc.unused;

import com.heterodb.common.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class MemcacheFactory {
	
	private JedisPool pool;
	public MemcacheFactory(Configuration conf) {
		String hostname = conf.get("redis-hostname", "localhost");
		int port = conf.getInt("redis-port", 6379);
		pool = new JedisPool(hostname, port);
	}
	
	public Jedis getInstance() {
		return pool.getResource();
	}
}
