package com.db.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;  

public class HbaseObject {
	private HBaseAdmin admin;
	private org.apache.hadoop.conf.Configuration conf;
	public HbaseObject(HbaseConfiguration hcf) throws IOException
	{
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", hcf.getDBMasterHost());
		conf.set("hbase.zookeeper.property.clientPort", hcf.getDBMasterPort()+"");
		this.admin = new HBaseAdmin(conf);
	}
	public HBaseAdmin getAdmin()
	{
		return this.admin;
	}
	public Configuration getConf()
	{
		return this.conf;
	}
}
