package com.db.mongoDB;
import com.db.common.ReadImplement;
import com.mongodb.Mongo;


public class MongoDBReader implements ReadImplement{
	
	private Mongo mongo = null;
	
	public MongoDBReader(MongoDBObject MObject) {
		mongo = MObject.getMongo();
	}

	@Override
	public boolean read(String tableName, String dbName, Object o) {
		// TODO Auto-generated method stub
		
		return false;
	}

}
