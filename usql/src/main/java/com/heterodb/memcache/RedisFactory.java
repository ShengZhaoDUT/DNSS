/*
 * Create RedisPool, it can return jedis instance from a pool. 
 */

package com.heterodb.memcache;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 
 * @author zs
 * @version 1.0
 * Description: create Factory, set parameters of RedisPool.
 * This Pool use consistent hash algorithm. Return ShardedJedis
 * We need give a List represent machine list to construct RedisFactory.
 * 
 */
public class RedisFactory {
	
	private ShardedJedisPool shardedJedisPool;
	private ShardedJedis jedis;
	//private List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
	
	private JedisPoolConfig poolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(25);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000 * 60);
		config.setTestOnBorrow(true);
		return config;
	}
	
	public RedisFactory() {
		JedisPoolConfig config = poolConfig();
		List<JedisShardInfo> machineList = new ArrayList<JedisShardInfo>();
		machineList.add(new JedisShardInfo("localhost", "6379"));
		shardedJedisPool = new ShardedJedisPool(config, machineList);
	}
	
	public RedisFactory(List<JedisShardInfo> machineList) {
		JedisPoolConfig config = poolConfig();
		shardedJedisPool = new ShardedJedisPool(config, machineList);
	}
	
	public ShardedJedis getClient() {
		return shardedJedisPool.getResource();
	}
	
	public void close() {
		jedis.disconnect();
	}
}
