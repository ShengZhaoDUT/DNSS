package com.db.mongoDB;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.db.common.ReadImplement;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;


public class MongoDBReader implements ReadImplement{
	
	private Mongo mongo = null;
	private DBCollection dbCollection;
	private DB db;
	protected static final Integer INCLUDE = Integer.valueOf(1);
	
	public MongoDBReader(MongoDBObject MObject) {
		mongo = MObject.getMongo();
	}
	
	@SuppressWarnings("unchecked")
	public boolean read(String dbName, String table, String key, Set<String> fields,
            Object result) {
        try {
            db = mongo.getDB(dbName);
            db.requestStart();

            dbCollection = db.getCollection(table);
            DBObject q = new BasicDBObject().append("_id", key);
            DBObject fieldsToReturn = new BasicDBObject();

            DBObject queryResult = null;
            if (fields != null) {
                Iterator<String> iter = fields.iterator();
                while (iter.hasNext()) {
                    fieldsToReturn.put(iter.next(), INCLUDE);
                }
                queryResult = dbCollection.findOne(q, fieldsToReturn);
            }
            else {
                queryResult = dbCollection.findOne(q);
            }

            if (queryResult != null) {
                ((HashMap<?, ?>)result).putAll(queryResult.toMap());
            }
            return queryResult != null ? true : false;
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
