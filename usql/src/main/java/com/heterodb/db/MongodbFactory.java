package com.heterodb.db;

import java.net.UnknownHostException;

import com.heterodb.common.DBConfiguration;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class MongodbFactory {
	
	private static Mongo mongo;
	
	static {
		String hostname = DBConfiguration.get("mongo-hostname", "localhost");
		int port = DBConfiguration.getInt("mongo-port", 27017);
		int threads = DBConfiguration.getInt("connection-multiplier", 5);
		int connections = DBConfiguration.getInt("connection_host", 10);
		try {
			MongoOptions options = new MongoOptions();
			options.setThreadsAllowedToBlockForConnectionMultiplier(threads);
			options.setConnectionsPerHost(connections);
			mongo = new Mongo(new ServerAddress(hostname, port), options);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Mongo getMongoInstance() {
		return mongo;
	}
	
	public static void shutdown() {
		mongo.close();
	}
}