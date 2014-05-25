package com.heterodb.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.JedisShardInfo;

import com.heterodb.memcache.RedisFactory;
import com.heterodb.memcache.RedisInstance;

public class RedisTest {
	
	public static void main(String[] args) {
		String ip = "166.111.69.77";
		//int port = 7001;
		/*for(int port = 7000; port <= 7004; port++) {
			Redis redisInstance = new Redis(ip, port);
			Map<String, String> test = new HashMap<String, String>();
			test.put("name", "ShengZhao");
			test.put("age", "23");
			int result = redisInstance.insert("default", "table", "zs", test);
			System.out.println(port);
			System.out.println(result);
		}*/
		
		/*int port = 6379;
		Redis redisInstance = new Redis(ip, port);
		Map<String, String> test = new HashMap<String, String>();
		test.put("name", "ShengZhao");
		test.put("age", "23");
		int result = redisInstance.insert("default", "table", "zs", test);
		System.out.println(port);
		System.out.println(result);*/
		List<JedisShardInfo> machineList = new ArrayList<JedisShardInfo>();
		for(int port = 7000; port <= 7004; port++) {
			JedisShardInfo jsi = new JedisShardInfo(ip, port);
			machineList.add(jsi);
		}
		JedisShardInfo jsi = new JedisShardInfo(ip, 6379);
		machineList.add(jsi);

		RedisFactory redisFactory = new RedisFactory(machineList);
		RedisInstance redisInstance = new RedisInstance(redisFactory);
		for(int i = 0; i < 10000; i++) {
			Map<String, String> keyval = new HashMap<String, String>();
			keyval.put("value", String.valueOf(i));
			redisInstance.insert(null, null, String.valueOf(i), keyval);
		}
		System.out.println("Done");
		for(int i = 0; i < 10000; i++) {
			JedisShardInfo s = redisInstance.getClient().getShardInfo(String.valueOf(i));
			System.out.println(i + " " + s.getHost() + " " + s.getPort());
		}
	}
}
