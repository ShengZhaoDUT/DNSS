package com.heterodb.db;

import java.io.IOException;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;

public class HBaseFactory {
	
	private HConnection hConnection = null;
	private HBaseAdmin hBaseAdmin = null;
	
	public HBaseFactory(com.heterodb.common.Configuration conf) {
		// TODO Auto-generated constructor stub
		String hostname = conf.get("hbase-hostname", "localhost");
		int port = conf.getInt("hbase-port", 2181);
		org.apache.hadoop.conf.Configuration hbaseConf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", hostname);
		conf.set("hbase.zookeeper.property.clientPort", port + "");
		try {
			hConnection = HConnectionManager.createConnection(hbaseConf);
			hBaseAdmin = new HBaseAdmin(hbaseConf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public HBaseAdmin getHBaseAdmin() {
		return hBaseAdmin;
	}
	
	public HTableInterface getHBaseInstance(String tableName) {
		try {
			return hConnection.getTable(tableName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
