package com.db.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;  

public class HbaseObject {
	private HBaseAdmin admin;
	private org.apache.hadoop.conf.Configuration conf;
	public HbaseObject(HbaseConfiguration hcf) throws MasterNotRunningException, ZooKeeperConnectionException
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
