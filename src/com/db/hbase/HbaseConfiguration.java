package com.db.hbase;

import com.db.common.Configuration;


public class HbaseConfiguration extends Configuration{

	@Override
	public String getDBMasterHost() {
		// TODO Auto-generated method stub
		return "cu05";//zookeeper
	}

	@Override
	public int getDBMasterPort() {
		// TODO Auto-generated method stub
		return 2181;
	}
	

	


}
