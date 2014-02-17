package com.db.mongoDB;

import com.db.common.Configuration;
import com.db.configure.DataBase;

public class MongoDBConfiguration extends Configuration{
	
	public String getDBMasterHost() {
		return DataBase.mongoDBMasterHost; 
	}
	public int getDBMasterPort() {
		return DataBase.mongoDBMasterPort;
	}
	public String getDBName() {
		return DataBase.dbName;
	}
}
