package com.db.configure;

public class DataBase {
	private static String mongoHost;
	private static int mongoPort;
	private static String hbaseHost;
	private static int hbasePort;
	private static String zookeepString;
	private static String dbName;
	
	public DataBase(String mongoHost, int mongoPort, String hbaseHost, 
			int hbasePort, String Zookeep, String dbName) {
		DataBase.mongoHost = mongoHost;
		DataBase.mongoPort = mongoPort;
		DataBase.hbaseHost = hbaseHost;
		DataBase.hbasePort = hbasePort;
		DataBase.zookeepString = Zookeep;
		DataBase.dbName = dbName;
	}
	public static void setMongoHost(String host) {
		mongoHost = host;
	}
	public static void setMongoPort(int port) {
		mongoPort = port;
	}
	public static void setHBaseHost(String host) {
		hbaseHost = host;
	}
	public static void setHBasePort(int port) {
		hbasePort = port;
	}
	public static void setZookeeper(String str) {
		zookeepString = str;
	}
	public static void setdbName(String db) {
		dbName = db;
	}
	public static String getMongoHost() {
		return mongoHost;
	}
	public static int getMongoPort() {
		return mongoPort;
	}
	public static String getHbaseHost() {
		return hbaseHost;
	}
	public static int getHbasePort() {
		return hbasePort;
	}
	public static String getZookeeper() {
		return zookeepString;
	}
	public static String getdbName() {
		return dbName;
	}
}
