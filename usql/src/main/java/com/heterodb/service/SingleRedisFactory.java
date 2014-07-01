package com.heterodb.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.heterodb.common.Configuration;
import com.heterodb.common.DBConfiguration;

public class SingleRedisFactory {

	private static JedisPool pool;
	
	/*
	 * <para>redis_sync</para>
	 * <value>localhost:6379</para>
	 */
	static{
		
		String machine = DBConfiguration.get("redis_sync", "localhost:6379");
		String ip = machine.split(":")[0];
		int port = Integer.valueOf(machine.split(":")[1]);
		pool = new JedisPool(ip, port);
	}
	
	public static Jedis getInstance() {
		
		 return pool.getResource();
	}
}
