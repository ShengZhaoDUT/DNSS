/*
 * Create RedisPool, it can return jedis instance from a pool. 
 */

package com.heterodb.memcache;

import java.util.ArrayList;
import java.util.List;

import com.heterodb.common.DBConfiguration;

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
	
	private static ShardedJedisPool shardedJedisPool;
	private static ShardedJedis jedis;
	//private List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
	
	static{
		
		int maxTotal = DBConfiguration.getInt("Redis_Total", 25);
		int maxIdle = DBConfiguration.getInt("Redis_Idle", 5);
		int maxMillis = DBConfiguration.getInt("Redis_WaitMillis", 1000 * 60);
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxMillis);
		config.setTestOnBorrow(true);
		shardedJedisPool = new ShardedJedisPool(config, getMachineList());
	}
	
	private static List<JedisShardInfo> getMachineList() {
		List<JedisShardInfo> machineList = new ArrayList<JedisShardInfo>();
		String value = DBConfiguration.get("redis_shard", "localhost:6379");
		String[] machines = value.split(";");
		for(String machine : machines) {
			String ip = machine.split(":")[0];
			int port = Integer.valueOf(machine.split(":")[1]);
			JedisShardInfo jsInfo = new JedisShardInfo(ip, port);
			machineList.add(jsInfo);
		}
		return machineList;
	}
	
	public static ShardedJedis getClient() {
		return shardedJedisPool.getResource();
	}
	
	public static void close() {
		jedis.disconnect();
	}
}
