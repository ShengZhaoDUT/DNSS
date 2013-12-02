package com.db.mongoDB;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.db.common.UpdateImplement;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoDBUpdater implements UpdateImplement{
	
	private Mongo mongo;
	private DB db;
	private DBCollection dbCollection;
	
	public MongoDBUpdater(MongoDBObject o) {
		mongo = o.getMongo();
	}
	
	public boolean update(String dbName, String table, String key, Object o) {
        com.mongodb.DB db = null;
        try {
            db = mongo.getDB(dbName);

            db.requestStart();

            DBCollection collection = db.getCollection(table);
            DBObject q = new BasicDBObject().append("_id", key);
            DBObject u = new BasicDBObject();
            DBObject fieldsToSet = new BasicDBObject();
            HashMap<?, ?> values = (HashMap<?, ?>) o;
            Iterator<String> keys = (Iterator<String>) values.keySet().iterator();
            while (keys.hasNext()) {
                String tmpKey = keys.next();
                fieldsToSet.put(tmpKey, values.get(tmpKey));
            }
            u.put("$set", fieldsToSet);
            WriteResult res = collection.update(q, u, false, false,
                    writeConcern);
            return res.getN() == 1 ? 0 : 1;
        }
        catch (Exception e) {
            System.err.println(e.toString());
            return 1;
        }
        finally {
            if (db != null) {
                db.requestDone();
            }
        }
    }
}
