package com.db.hbase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import com.db.common.ReadImplement;

public class HbaseReader{

	private Configuration conf;
	//private Result result;
	public HbaseReader(HbaseObject h){
		conf = h.getConf();
	}
	
	public boolean read(String dbName, String table, String key,
			Set<Column> fields, Object result) {
		// TODO How to read it?
		HTable mytable = null;
		try {
			mytable = new HTable(conf, table);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		Get g =new Get(Bytes.toBytes(key));
		for (Column column : fields){
			g.addColumn(Bytes.toBytes(column.columnFamily), Bytes.toBytes(column.column));
		}
		try {
			Result r = mytable.get(g);//Result object
			//need to translate into Hashmap<column string>
		for (Column column : fields){
			byte[] b = r.getValue(Bytes.toBytes(column.columnFamily),Bytes.toBytes(column.column));
			((HashMap<Column,String>) result).put(column,b.toString());
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
		return true;
	}

}
