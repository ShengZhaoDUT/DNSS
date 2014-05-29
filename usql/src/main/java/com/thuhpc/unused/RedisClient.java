package com.thuhpc.unused;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisClient {
	
	private ShardedJedisPool shardedJedisPool;
	private ShardedJedis jedis;
	private List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
	
	private JedisPoolConfig poolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(25);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000 * 60);
		config.setTestOnBorrow(true);
		return config;
	}
	
	public RedisClient() {
		JedisPoolConfig config = poolConfig();
		shards.add(new JedisShardInfo("localhost", 6379));
		shardedJedisPool = new ShardedJedisPool(config, shards);
	}
	
	public RedisClient(Map<String, Integer> machineList) {
		JedisPoolConfig config = poolConfig();
		for(Map.Entry<String, Integer> entry : machineList.entrySet()) {
			JedisShardInfo jsi = new JedisShardInfo(entry.getKey(), entry.getValue());
			shards.add(jsi);
		}
		shardedJedisPool = new ShardedJedisPool(config, shards);
	}
	
	public ShardedJedis getClient() {
		return shardedJedisPool.getResource();
	}
	
	public void close() {
		jedis.disconnect();
	}
}
