package com.db.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.db.common.WriteImplement;

public class HbaseWriter{

	private Configuration conf;
	
	public HbaseWriter(HbaseObject h)
	{
		conf = (Configuration) h.getConf();
	}
	
	public boolean write(String tableName, String dbColl, Object mylist) throws IOException {
		// TODO Auto-generated method stub
		//here dbName represents Tablename
		HTable mytable = new HTable(conf, tableName);
		String rowname = "row";
		Put p = new Put(Bytes.toBytes(rowname));
		p.add(Bytes.toBytes("columnfamily"),Bytes.toBytes("column"),Bytes.toBytes("value"));
		
		mytable.put(p);
		return false;
	}

}
