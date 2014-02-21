package com.db.mongoDB;

import com.db.common.Configuration;
import com.db.configure.DataBase;

public class MongoDBConfiguration extends Configuration{
	
	public String getDBMasterHost() {
		return DataBase.getMongoHost(); 
	}
	public int getDBMasterPort() {
		return DataBase.getMongoPort();
	}
	public String getDBName() {
		return DataBase.getdbName();
	}
}
