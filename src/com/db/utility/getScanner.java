package com.db.utility;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

import com.db.hbase.HbaseConfiguration;
import com.db.hbase.HbaseObject;

// sync oplog to mongodb
public class getScanner {
	private HbaseObject hbaseDBObject;
	private Configuration conf;
	ResultScanner scanner;
	public getScanner(HbaseConfiguration hbaseConf) {
		try {
			hbaseDBObject = new HbaseObject(hbaseConf);
			conf = hbaseDBObject.getConf();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean getOplog() {
		try {
			HTable htable = new HTable(conf, Bytes.toBytes("oplog"));
			Scan scan = new Scan();
			Filter filter = new RowFilter(CompareOp.GREATER,  
				      new BinaryComparator(Bytes.toBytes(LastUpdateTime.getUpdateTime())));
			System.out.println(LastUpdateTime.getUpdateTime());
			scan.setFilter(filter);  
		    scanner = htable.getScanner(scan);
		    return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
	}
	public ResultScanner getResult() {
		getOplog();
		return scanner;
	}
}
