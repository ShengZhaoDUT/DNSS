package com.db.hbase;

import java.util.Set;

import org.apache.hadoop.hbase.client.HBaseAdmin;

import com.db.common.ReadImplement;

public class HbaseReader implements ReadImplement {

	private HBaseAdmin admin;
	
	public HbaseReader(HbaseObject h){
		admin = h.getAdmin();
	}
	
	@Override
	public boolean read(String dbName, String table, String key,
			Set<String> fields, Object result) {
		// TODO How to read it?
		
		return false;
	}

}
