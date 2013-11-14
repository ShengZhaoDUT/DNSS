
public class MongoDBConfiguration extends Configuration{
	private String mongoDBMasterHost = "192.168.1.55";
	private int mongoDBMasterPort = 27017;
	private String dbName = "test";
	
	public String getDBMasterHost() {
		return mongoDBMasterHost; 
	}
	public int getDBMasterPort() {
		return mongoDBMasterPort;
	}
	public String getDBName() {
		return dbName;
	}
}
