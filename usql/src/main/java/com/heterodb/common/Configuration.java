package com.heterodb.common;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
	
	private Map<String, String> configuration;
	
	public Configuration() {
		configuration =  new HashMap<String, String>();
	}
	
	public Configuration getConf() {
		return this;
	}
	
	public void set(String key, String value) {
		configuration.put(key, value);
	}
	
	public String get(String key, String defaultvalue) {
		if(!configuration.containsKey(key)) 
			return defaultvalue;
		else 
			return configuration.get(key);
	}
	
	public void setInt(String key, int value) {
		configuration.put(key, String.valueOf(value));
	}
	
	public int getInt(String key, int defaultvalue) {
		if(!configuration.containsKey(key)) 
			return defaultvalue;
		else 
			return Integer.parseInt(configuration.get(key));
	}
	
	public void setLong(String key, long value) {
		configuration.put(key, String.valueOf(value));
	}
	
	public long getLong(String key, long defaultvalue) {
		if(!configuration.containsKey(key))
			return defaultvalue;
		else
			return Long.parseLong(configuration.get(key));
	}
}
