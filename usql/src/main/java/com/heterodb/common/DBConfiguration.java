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
		
	}
	
	public static void reload() {
		
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
