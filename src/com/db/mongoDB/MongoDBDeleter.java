package com.db.mongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public class MongoDBDeleter {
	
	private WriteConcern writeConcern = WriteConcern.FSYNC_SAFE;
	private Mongo mongo;
	private DB db;
	private DBCollection dbCollection;
	
	public MongoDBDeleter(MongoDBObject o) {
		mongo = o.getMongo();
		// TODO Auto-generated constructor stub
	}
	public boolean delete(String dbName, String table, String key) {
		
        try {
            db = mongo.getDB(dbName);
            db.requestStart();
            dbCollection = db.getCollection(table);
            DBObject q = new BasicDBObject().append("_id", key);
            WriteResult res = dbCollection.remove(q, writeConcern);
            return res.getN() == 1 ? true : false;
        }
        catch (Exception e) {
            System.err.println(e.toString());
            return false;
        }
        finally {
            if (db != null) {
                db.requestDone();
            }
        }
    }

}