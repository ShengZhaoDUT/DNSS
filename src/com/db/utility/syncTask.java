package com.db.utility;

import java.util.ArrayList;
import java.util.List;

import com.db.common.Task;
import com.db.common.item;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class syncTask implements Runnable{
	private Task task;
	private Mongo mongo;
	public syncTask(Task task, Mongo mongo) {
		this.task = task;
		this.mongo = mongo;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DB db = mongo.getDB(task.dbName);
		DBCollection dbCollection = db.getCollection(task.table);
		List<DBObject> putList = new ArrayList<DBObject>();
		if(task.op.equals("insert")) {
			int i = 0;
			for(String row : task.rowKey) {
				List<item> value = task.value.get(i);
				DBObject dbObject = new BasicDBObject();
				dbObject.put("_id", row);
				for(item it : value) {
					dbObject.put(it.c, it.v);
				}
				putList.add(dbObject);
				i++;
			}
			dbCollection.insert(putList);
		}
		else if(task.op.equals("update")){
			int i = 0;
			for(String row : task.rowKey) {
				List<item> value = task.value.get(i);
				DBObject query = new BasicDBObject();
				DBObject update = new BasicDBObject();
				query.put("_id", row);
				for(item it : value) {
					update.put(it.c, it.v);
				}
				dbCollection.update(query, update);
				i++;
			}
		}
	}
}
