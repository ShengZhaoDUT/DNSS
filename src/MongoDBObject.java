import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;


public class MongoDBObject {
	
	private Mongo mongo = null;
	private DBCollection dbCollection;
	private DB db;
	
	
	public MongoDBObject(Configuration conf, String dbColl, String dbName) throws UnknownHostException{
		String host = conf.getDBMasterHost();
		int port = conf.getDBMasterPort();
		mongo = new Mongo(host, port);
		db = mongo.getDB(dbName);
		dbCollection = db.getCollection(dbColl);
		for(String name : mongo.getDatabaseNames()) {
			System.out.println("dbName:" + name);
		}
	}
	
	public Mongo getMongo() {
		return mongo;
	}
	
	public DBCollection getDbCollection() {
		return dbCollection;
	}
	
	public DB getDb() {
		return db;
	}
	
}