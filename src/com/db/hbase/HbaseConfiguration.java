package com.db.hbase;

import com.db.common.Configuration;
import com.db.configure.DataBase;


public class HbaseConfiguration extends Configuration{

	@Override
	public String getDBMasterHost() {
		// TODO Auto-generated method stub
		return DataBase.getHbaseHost();//zookeeper.quorum
	}

	@Override
	public int getDBMasterPort() {
		// TODO Auto-generated method stub
		return DataBase.getHbasePort();
	}
	public String getZookeeper() {
		return DataBase.getZookeeper();
	}

	@Override
	public String getDBName() {
		// TODO Auto-generated method stub
		return DataBase.getdbName();
	}
}
