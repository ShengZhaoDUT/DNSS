package com.db.hbase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import com.db.common.ScanImplement;

public class HbaseScanner{

	private Configuration conf;
	//private Result result;
	public HbaseScanner(HbaseObject h){
		conf = h.getConf();
	}
	public boolean scan(String dbName, String table, String startkey,
			int recordcount, Set<Column> fields, Object result) {
		// TODO Auto-generated method stub
		HTable htable = null;
		try {
			htable = new HTable(conf,table);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		byte[] startRow = Bytes.toBytes(startkey);
		byte[] stopRow = Bytes.toBytes(startkey);
		stopRow[stopRow.length-1]++;
		Scan s = new Scan(startRow,stopRow);
		for(Column i : fields)
		{
			s.addColumn(Bytes.toBytes(i.columnFamily), Bytes.toBytes(i.column));
		}
		try {
			ResultScanner rs = htable.getScanner(s);
			
			//put the scanner result into a list<map<column,string>>
			for(Result r : rs)
			{
				Map<Column, String> temp = new HashMap<Column,String>();
				for(Column column : fields)
				{
					byte[] b = r.getValue(Bytes.toBytes(column.columnFamily), Bytes.toBytes(column.column));
					
					((HashMap<Column, String>) temp).put(column, b.toString());
				}
				((List<HashMap<Column,String>>)result).add((HashMap<Column, String>) temp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
