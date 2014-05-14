package com.thuhpc.usql;

import java.util.HashMap;
import java.util.Map;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/*
 *
 */
public class memcache {
	
	protected static MemCachedClient mcc = new MemCachedClient();
	
	private memcache() {
		String[] servers = {"166.111.69.77:12111"};
		Integer[] weights = {3};
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);
		pool.setWeights(weights);
		pool.setInitConn(5);
		pool.setMinConn(5);
		pool.setMaxConn(200);
		pool.setMaxIdle(1000*30*30);
		pool.setMaintSleep(30);
		pool.setNagle(false);
		pool.setSocketTO(30);
		pool.setSocketConnectTO(0);
		pool.initialize();
		
		Map<String, String> test = new HashMap<String, String>();
		test.put("name", "zhaosheng");
		mcc.set("test1", test);
		
		Map<String, String> result = (Map)mcc.get("test1");
		for(Map.Entry<String, String> entry : result.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
 	}
	
    public static void main(String[] args) {
    	memcache mem = new memcache();
    }
}