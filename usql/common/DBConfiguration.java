package com.heterodb.common;

import java.util.HashMap;
import java.util.Map;

public class DBConfiguration {
	
	public static Map<String, String> configuration;
	
	static {
		configuration = new HashMap<String, String>();
		load();
	}
	
	public static void load() {
		
		configuration.put("hbase-hostname", "166.111.69.77");
		configuration.put("hbase-port", "2181");
		configuration.put("mongo-hostname", "166.111.69.77");
		configuration.put("mongo-port", "27017");
		configuration.put("connection-multiplier", "5");
		configuration.put("connection_host", "10");
		configuration.put("Redis_Total", "25");
		configuration.put("Redis_Idle", "5");
		configuration.put("Redis_WaitMillis", "60000");
		configuration.put("redis_shard", "192.168.7.7:6379;192.168.7.101:6379;"
				+ "192.168.7.102:6379;192.168.7.103:6379;192.168.7.103:6379;"
				+ "192.168.7.104:6379;192.168.7.105:6379");
		configuration.put("redis_sync", "localhost:6379");
	}

	public static void set(String key, String value) {
		configuration.put(key, value);
	}
	
	public static String get(String key, String defaultvalue) {
		if(!configuration.containsKey(key)) 
			return defaultvalue;
		else 
			return configuration.get(key);
	}
	
	public static void setInt(String key, int value) {
		configuration.put(key, String.valueOf(value));
	}
	
	public static int getInt(String key, int defaultvalue) {
		if(!configuration.containsKey(key)) 
			return defaultvalue;
		else 
			return Integer.parseInt(configuration.get(key));
	}
	
	public static void setLong(String key, long value) {
		configuration.put(key, String.valueOf(value));
	}
	
	public static long getLong(String key, long defaultvalue) {
		if(!configuration.containsKey(key))
			return defaultvalue;
		else
			return Long.parseLong(configuration.get(key));
	}
}
