package com.heterodb.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.heterodb.common.Configuration;

public class SingleRedisFactory {

	private JedisPool pool;
	public SingleRedisFactory(Configuration conf) {
		String hostname = conf.get("redis-hostname", "localhost");
		int port = conf.getInt("redis-port", 6379);
		pool = new JedisPool(hostname, port);
	}
	
	public Jedis getInstance() {
		 return pool.getResource();
		 //return new SingleRedis(jedis);
	}
}
