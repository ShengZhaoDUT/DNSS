package com.heterodb.db;

import java.net.UnknownHostException;

import com.heterodb.common.Configuration;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class MongodbFactory {
	
	private Mongo mongo;
	
	public MongodbFactory(Configuration conf) {
		String hostname = conf.get("mongo-hostname", "localhost");
		int port = conf.getInt("mongo-port", 27017);
		
		try {
			MongoOptions options = new MongoOptions();
			options.setThreadsAllowedToBlockForConnectionMultiplier(5);
			options.setConnectionsPerHost(10);
			mongo = new Mongo(new ServerAddress(hostname, port), options);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Mongo getMongoInstance() {
		return mongo;
	}
}