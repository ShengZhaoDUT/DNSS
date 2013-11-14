import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class test {
	
	public static void main(String args[]) {
		Configuration configuration = new MongoDBConfiguration();
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Name", "ShengZhao");
		map.put("age", "22");
		List<Map<?, ?>> list = new ArrayList<Map<?,?>>();
		list.add(map);
		try {
			MongoDBObject mDbObject = new MongoDBObject(configuration, "user", "test");
			MongoDBWriter mongoDBWriter = new MongoDBWriter(mDbObject, list);
			mongoDBWriter.write();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}