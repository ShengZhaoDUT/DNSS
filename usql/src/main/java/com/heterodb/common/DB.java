package com.heterodb.common;

import java.util.Map;
import java.util.Set;
import java.util.Vector;

public abstract class DB {
	
	public abstract void init();
	
	public abstract void cleanup();
	
	public abstract int read(String database, String table, String key, Set<String> fields, Map<String, String> result);
	
	public abstract int update(String database, String table, String key, Map<String, String> result);
	
	public abstract int insert(String database, String table, String key, Map<String, String> values);
	
	public abstract int scan(String database, String table, String startkey, int recordcount, Set<String> fields, Vector<Map<String,String>> result);
	
	public abstract int delete(String database, String table, String key);
}
