package com.db.mongoDB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.db.common.WriteImplement;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;


public class MongoDBWriter implements WriteImplement{

	private Mongo mongo;
	private DBCollection dbCollection;
	private DB db;
	private static WriteConcern writeConcern = WriteConcern.FSYNC_SAFE;
	
	public MongoDBWriter(MongoDBObject mongoDB) {
		mongo = mongoDB.getMongo();
	}

	@SuppressWarnings("unchecked")
	public boolean write(String dbName, String dbColl, Object mylist) {
		db = mongo.getDB(dbName);
		dbCollection = db.getCollection(dbColl);
		List<DBObject> list = new ArrayList<DBObject>();
		//Map<String, String> map = new HashMap<String, String>();
		//map.put("_id", key);
		List<Map<?, ?>> result = (List<Map<?,?>>)mylist;
		//result.add(map);
		//BasicDBObject basic = new BasicDBObject("_id", key);
		//list.add(basic);
		for(Map<?, ?> element : result) {
			BasicDBObject basic = new BasicDBObject(element);
			list.add(basic);
		}
		WriteResult wr = dbCollection.insert(list, writeConcern);
		return wr.getError() == null ? true : false;
	}
}