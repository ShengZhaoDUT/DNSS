package com.db.mongoDB;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import com.db.common.ScanImplement;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoDBScanner implements ScanImplement{
	
	private Mongo mongo;
	private DB db;
	private DBCollection dbCollection;
	public MongoDBScanner(MongoDBObject o) {
		mongo = o.getMongo();
	}
	/*public boolean scan(String dbName, String table, String startkey, int recordcount,
            Set<String> fields, Object result) {
        try {
            db = mongo.getDB(dbName);
            db.requestStart();
            DBCollection collection = db.getCollection(table);
            // { "_id":{"$gte":startKey, "$lte":{"appId":key+"\uFFFF"}} }
            DBObject scanRange = new BasicDBObject().append("$gte", startkey);
            DBObject q = new BasicDBObject().append("_id", scanRange);
            DBCursor cursor = collection.find(q).limit(recordcount);
            while (cursor.hasNext()) {
                // toMap() returns a Map, but result.add() expects a
                // Map<String,String>. Hence, the suppress warnings.
                HashMap<String, ByteIterator> resultMap = new HashMap<String, ByteIterator>();

                DBObject obj = cursor.next();
                fillMap(resultMap, obj);

                result.add(resultMap);
            }

            return 0;
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
    */
	@Override
	public boolean scan(String dbName, String table, String startkey,
			int recordcount, Set<String> fields, Object result) {
		// TODO Auto-generated method stub
		return false;
	}
}
