package com.db.hbase;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.db.common.WriteImplement;

public class HbaseWriter implements WriteImplement{

	private Configuration conf;
	
	public HbaseWriter(HbaseObject h)
	{
		conf = (Configuration) h.getConf();
	}
	
	public boolean write(String tableName,String dbColl, Object mylist) {
		// TODO Auto-generated method stub
		//here dbName represents Tablename
		HTable mytable;
		try {
			mytable = new HTable(conf, tableName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		Map<Column, String> result = (Map<Column, String>)mylist;
		for(Column c:result.keySet())
		{
			
		
			String rowname = c.rowname;
			Put p = new Put(Bytes.toBytes(rowname));
			p.add(Bytes.toBytes(c.columnFamily),Bytes.toBytes(c.column),Bytes.toBytes(result.get(c)));

			try {
				mytable.put(p);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

}
