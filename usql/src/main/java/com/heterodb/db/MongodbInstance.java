package com.heterodb.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.heterodb.common.Configuration;
import com.heterodb.common.DB;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

public class MongodbInstance extends DB{
	
	private Mongo mongo;
	
	//protected static final AtomicInteger InitCount = new AtomicInteger(0);
	
	public MongodbInstance() {
		mongo = MongodbFactory.getMongoInstance();
	}

	@Override
	public void init(Configuration conf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int read(String database, String table, String key, Set<String> fields,
			Map<String, String> result) {
		// TODO Auto-generated method stub
		com.mongodb.DB db = null;
		db = mongo.getDB(database);
		DBCollection dbCollection = db.getCollection(table);
		DBObject query = new BasicDBObject().append("_id", key);
		DBObject fieldsToReturn = new BasicDBObject();
		DBObject queryResult = null;
		if(fields != null) {
			for(String field : fields) {
				fieldsToReturn.put(field, 1);
			}
			queryResult = dbCollection.findOne(query, fieldsToReturn);
		}
		else {
			queryResult = dbCollection.findOne(query);
		}
		if(queryResult != null) {
			result.putAll(queryResult.toMap());
		}
		return queryResult == null ? 1 : 0;
	}

	@Override
	public int update(String database, String table, String key, Map<String, String> result) {
		// TODO Auto-generated method stub
		com.mongodb.DB db = null;
		db = mongo.getDB(database);
		DBCollection dbCollection = db.getCollection(table);
		DBObject source = new BasicDBObject().append("_id", key);
		DBObject target = new BasicDBObject().append("_id", key);
		for(Map.Entry<String, String> entry : result.entrySet()) {
			target.put(entry.getKey(), entry.getValue());
		}
		WriteResult res = dbCollection.update(source, target, false, false);
		return res.getN() == 1 ? 0 : 1;
	}

	@Override
	public int insert(String database, String table, String key, Map<String, String> values) {
		// TODO Auto-generated method stub
		com.mongodb.DB db = null;
		db = mongo.getDB(database);
		DBCollection dbCollection = db.getCollection(table);
		DBObject dbObject = new BasicDBObject().append("_id", key);
		for(Map.Entry<String, String> entry : values.entrySet()) {
			dbObject.put(entry.getKey(), entry.getValue());
		}
		WriteResult result = dbCollection.insert(dbObject);
		return result.getError() == null ? 0 : 1;
	}

	@Override
	public int scan(String database, String table, String startkey, int recordcount,
			Set<String> fields, Vector<Map<String, String>> result) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String database, String table, String key) {
		// TODO Auto-generated method stub
		com.mongodb.DB db = null;
		db = mongo.getDB(database);
		DBCollection dbCollection = db.getCollection(table);
		DBObject query = new BasicDBObject().append("_id", key);
		WriteResult res = dbCollection.remove(query);
		return res.getN() == 1 ? 0 : 1;
	}
}
