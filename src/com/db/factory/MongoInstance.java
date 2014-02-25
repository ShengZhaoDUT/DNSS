package com.db.factory;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.mongodb.MongoOptions;

public class MongoInstance {
	
	private static Mongo mongo = null;
	
	public static void set(String hostName, int port, int poolSize, int queueSize) {
		try {
			mongo = new Mongo(hostName, port);
			MongoOptions opt = mongo.getMongoOptions();
	        opt.connectionsPerHost = poolSize;
	        opt.threadsAllowedToBlockForConnectionMultiplier = queueSize;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Mongo getMongoInstance() {
		return mongo;
	}
}
