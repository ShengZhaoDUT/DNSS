package com.heterodb.common;

public interface Configuration {
	
	public void set(String key, String value);
	
	public String get(String key, String defaultvalue);
	
	public void setInt(String key, int value);
	
	public int getInt(String key, int defaultvalue);
	
	public void setLong(String key, long value);
	
	public long getLong(String key, long defaultvalue);
	
}
