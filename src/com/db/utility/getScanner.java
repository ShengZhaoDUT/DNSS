package com.db.utility;

import java.io.IOException;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

import com.db.factory.HBaseInstance;

// sync oplog to mongodb
public class getScanner {
	//private HbaseObject hbaseDBObject;
	//private Configuration conf;
	private HTable hTable = null;
	private ResultScanner scanner = null;
	private String tableName = "oplog";
	public getScanner() {
		hTable = (HTable)HBaseInstance.getHBaseInstance(tableName);
	}
	public boolean getOplog() {
		try {
			Scan scan = new Scan();
			Filter filter = new RowFilter(CompareOp.GREATER,  
				      new BinaryComparator(Bytes.toBytes(LastUpdateTime.getUpdateTime())));
			System.out.println(LastUpdateTime.getUpdateTime());
			scan.setFilter(filter);  
		    scanner = hTable.getScanner(scan);
		    // need to set time, no api to know the last line, so delay, can get the time when sync()
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
