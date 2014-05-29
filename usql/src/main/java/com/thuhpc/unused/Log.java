package com.thuhpc.unused;

import java.io.Serializable;
import java.util.Map;

public class Log implements Serializable{
	
	private long ts;
	private String op;
	private Map<String, String> message;
	
	public Log(long ts, String op, Map<String, String> message) {
		this.ts = ts;
		this.op = op;
		this.message = message;
	}
	
	public long getTimestamp() {
		if(ts != 0)
			return ts;
		else 
			return 0;
	}
	
	public String getOperator() {
		if(op != null)
			return op;
		else 
			return null;
	}
	
	public Map<String, String> getMessage() {
		if(message != null)
			return message;
		else 
			return null;
	}
}
