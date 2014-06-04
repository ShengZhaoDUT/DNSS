package com.heterodb.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.heterodb.common.Configuration;
import com.heterodb.common.DB;

public class Heterodb extends DB {
	
	public Heterodb(Configuration conf) {
		
		Configuration configuration = new Configuration();
	}

	@Override
	public void init(Configuration conf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int read(String database, String table, String key,
			Set<String> fields, HashMap<String, String> result) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String database, String table, String key,
			Map<String, String> result) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(String database, String table, String key,
			Map<String, String> values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int scan(String database, String table, String startkey,
			int recordcount, Set<String> fields,
			Vector<Map<String, String>> result) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String database, String table, String key) {
		// TODO Auto-generated method stub
		return 0;
	}
}
