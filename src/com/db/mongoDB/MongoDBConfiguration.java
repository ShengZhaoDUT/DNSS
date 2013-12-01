package com.db.mongoDB;

import com.db.common.Configuration;

public class MongoDBConfiguration extends Configuration{
	private String mongoDBMasterHost = "192.168.1.55";
	private int mongoDBMasterPort = 27017;
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
