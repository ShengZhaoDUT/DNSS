package com.heterodb.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.heterodb.common.DBConfiguration;

public class SingleRedisFactory {

	private static JedisPool pool;
	
	/*
	 * <para>redis_sync</para>
	 * <value>localhost:6379</para>
	 */
	static{
		
		int maxTotal = DBConfiguration.getInt("Redis_Total", 25);
		int maxIdle = DBConfiguration.getInt("Redis_Idle", 5);
		int maxMillis = DBConfiguration.getInt("Redis_WaitMillis", 1000 * 60);
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxMillis);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		String machine = DBConfiguration.get("redis_sync", "localhost:6379");
		String ip = machine.split(":")[0];
		int port = Integer.valueOf(machine.split(":")[1]);
		pool = new JedisPool(config, ip, port);
	}
	
	public static Jedis getInstance() {
		
		 return pool.getResource();
	}
	
	public static void close(Jedis jedis) {
		pool.returnResource(jedis);
	}
}
