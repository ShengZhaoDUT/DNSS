package com.db.hbase;

import com.db.common.Configuration;


public class HbaseConfiguration extends Configuration{

	@Override
	public String getDBMasterHost() {
		// TODO Auto-generated method stub
		return "166.111.131.166";//zookeeper.quorum
	}

	@Override
	public int getDBMasterPort() {
		// TODO Auto-generated method stub
		return 2181;
	}
	public String getZookeeper()
	{
		return "cu05";
	}

	


}
