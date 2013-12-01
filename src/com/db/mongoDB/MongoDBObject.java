package com.db.mongoDB;
import java.net.UnknownHostException;

import com.db.common.Configuration;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;


public class MongoDBObject {
	
	private Mongo mongo = null;
	
	
	
	
	public MongoDBObject(Configuration configuration) throws UnknownHostException {
		// TODO Auto-generated constructor stub
		String host = configuration.getDBMasterHost();
		int port = configuration.getDBMasterPort();
		mongo = new Mongo(host, port);

		for(String name : mongo.getDatabaseNames()) {
			System.out.println("dbName:" + name);
		}
	}

	public Mongo getMongo() {
		return mongo;
	}
	/*
	public DBCollection getDbCollection() {
		return dbCollection;
	}
	
	public DB getDb() {
		return db;
	}
	*/
}