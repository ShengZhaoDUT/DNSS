package com.db.factory;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;

public class HBaseInstance {
		
	private static HConnection hConnection = null;
	private static HBaseAdmin hBaseAdmin = null;
	
	public static void setAndInit(String hostName, int port) {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", hostName);
		conf.set("hbase.zookeeper.property.clientPort", port + "");
		try {
			hConnection = HConnectionManager.createConnection(conf);
			hBaseAdmin = new HBaseAdmin(conf);
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
