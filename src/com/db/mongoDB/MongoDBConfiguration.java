package com.db.mongoDB;

import com.db.common.Configuration;

public class MongoDBConfiguration extends Configuration{
	private String mongoDBMasterHost = "166.111.131.134";
	private int mongoDBMasterPort = 8188;
	private String dbName = "test";
	
	public String getDBMasterHost() {
		return mongoDBMasterHost; 
	}
	public int getDBMasterPort() {
		return mongoDBMasterPort;
	}
	public String getDBName() {
		return dbName;
	}
	public void setDBMasterHost(String mongoDBMasterHost) {
		this.mongoDBMasterHost = mongoDBMasterHost;
	}
	public void setDBMasterPort(int mongoDBMasterPort) {
		this.mongoDBMasterPort = mongoDBMasterPort;
	}
	public void setdbName(String dbName) {
		this.dbName = dbName;
	}
}
