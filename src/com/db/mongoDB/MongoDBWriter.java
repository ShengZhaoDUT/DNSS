package com.db.mongoDB;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.db.common.WriteImplement;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;


public class MongoDBWriter implements WriteImplement{

	private Mongo mongo;
	List<Map<?, ?>> arrayList;
	private DBCollection dbCollection;
	private DB db;
	
	public MongoDBWriter(MongoDBObject mObject) {
		mongo = mObject.getMongo();
		
	}
	
	@SuppressWarnings("unchecked")
	public boolean write(String dbColl, String dbName, Object mylist) {
		db = mongo.getDB(dbName);
		dbCollection = db.getCollection(dbColl);
		this.arrayList = (List<Map<?, ?>>)mylist;
		List<DBObject> list = new ArrayList<DBObject>();
		for(Map<?, ?> element : arrayList) {
			BasicDBObject basic = new BasicDBObject(element);
			list.add(basic);
		}
		WriteResult wr = dbCollection.insert(list);
		System.out.println(wr.getError());
		return true;
	}
}