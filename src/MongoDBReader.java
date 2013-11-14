import com.mongodb.Mongo;


public class MongoDBReader implements ReadImplement{
	
	private Mongo mongo = null;
	
	public MongoDBReader(MongoDBObject MObject) {
		mongo = MObject.getMongo();
	}
	
	@Override
	public boolean read() {
		// TODO Auto-generated method stub
		
		return false;
	}
}
