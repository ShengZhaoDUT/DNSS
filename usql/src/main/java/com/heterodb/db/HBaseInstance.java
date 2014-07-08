package com.heterodb.db;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.heterodb.common.DB;

public class HBaseInstance extends DB{

	private HTableInterface htable;
	private byte columnFamilyBytes[] = Bytes.toBytes("Default");
	
	public HBaseInstance(String table) {
		htable = HBaseFactory.getHBaseInstance(table);
		try {
			htable.setWriteBufferSize(1024*1024*12);
			htable.setAutoFlush(false,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		try {
			htable.flushCommits();
			htable.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int read(String database, String table, String key, Set<String> fields,
			Map<String, String> result) {
		// TODO Auto-generated method stub
		Get get = new Get(Bytes.toBytes(key));
		if(fields == null) {
			get.addFamily(columnFamilyBytes);
		}
		else {
			for(String field : fields) {
				get.addColumn(columnFamilyBytes, Bytes.toBytes(field));
			}
		}
		Result r = null;
		try {
			r = htable.get(get);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		for(Cell cell : r.rawCells()) {
			result.put(Bytes.toString(CellUtil.cloneQualifier(cell)), Bytes.toString(CellUtil.cloneValue(cell)));
		}
		return 0;
	}

	@Override
	public int update(String database, String table, String key, Map<String, String> result) {
		// TODO Auto-generated method stub
		Put put = new Put(Bytes.toBytes(key));
		for(Map.Entry<String, String> entry : result.entrySet()) {
			put.add(columnFamilyBytes, Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
		}
		try {
			htable.put(put);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	@Override
	public int insert(String database, String table, String key, Map<String, String> values) {
		// TODO Auto-generated method stub
		update(null, table, key, values);
		return 0;
	}

	@Override
	public int scan(String database, String table, String startkey, int recordcount,
			Set<String> fields, Vector<Map<String, String>> result) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String database, String table, String key) {
		// TODO Auto-generated method stub
		Delete del = new Delete(Bytes.toBytes(key));
		try {
			htable.delete(del);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
	
}
