package com.heterodb.service;

import java.util.Iterator;
import java.util.Map;

import javax.print.attribute.Size2DSyntax;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author zs
 * travel all the keys in redis
 *
 */
public class SingleRedis {
	
	private Jedis jedis;
	
	public SingleRedis() {
		jedis = new Jedis("localhost", 6379);
	}
	
	public SingleRedis(Jedis jedis) {
		this.jedis = jedis;
	}
	
	public Jedis getRedis() {
		return jedis;
	}
	
	public Iterator getKeys(int database) {
		if(database != 0) {
			jedis.select(database);
		}
		Iterator it = jedis.keys("*").iterator();
		return it;
	}
	
	public int read(String key, Map<String, String> value) {
		value = jedis.hgetAll(key);
		return value.isEmpty() ? 1 : 0;
	}
}
