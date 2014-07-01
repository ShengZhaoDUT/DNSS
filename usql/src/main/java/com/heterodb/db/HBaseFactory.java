package com.heterodb.db;

import java.io.IOException;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;

import com.heterodb.common.DBConfiguration;

public class HBaseFactory {
	
	private static HConnection hConnection = null;
	private static HBaseAdmin hBaseAdmin = null;
	
	static{
		String hostname = DBConfiguration.get("hbase-hostname", "localhost");
		int port = DBConfiguration.getInt("hbase-port", 2181);
		org.apache.hadoop.conf.Configuration hbaseConf = HBaseConfiguration.create();
		hbaseConf.set("hbase.zookeeper.quorum", hostname);
		hbaseConf.set("hbase.zookeeper.property.clientPort", port + "");
		try {
			hConnection = HConnectionManager.createConnection(hbaseConf);
			hBaseAdmin = new HBaseAdmin(hbaseConf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static HBaseAdmin getHBaseAdmin() {
		return hBaseAdmin;
	}
	
	public static HTableInterface getHBaseInstance(String tableName) {
		try {
			return hConnection.getTable(tableName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
