package mongoDB;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;


public class MongoDBWriter implements WriteImplement{

	private Mongo mongo;
	List<Map<?, ?>> arrayList;
	private DBCollection dbCollection;
	
	public MongoDBWriter(MongoDBObject mObject, List<Map<?, ?>> mylist) {
		mongo = mObject.getMongo();
		dbCollection = mObject.getDbCollection();
		this.arrayList = mylist;
	}
	@Override
	public boolean write() {
		// TODO Auto-generated method stub
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