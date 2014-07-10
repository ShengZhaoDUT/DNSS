package com.heterodb.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DBConfiguration {
	
	public static Map<String, String> configuration;
	
	static {
		configuration = new HashMap<String, String>();
		load();
	}
	
	public static void load1() {
		
		configuration.put("hbase-hostname", "192.168.6.10");
		configuration.put("hbase-port", "2181");
		configuration.put("mongo-hostname", "192.168.6.10");
		configuration.put("mongo-port", "20000");
		configuration.put("connection-multiplier", "5");
		configuration.put("connection_host", "10");
		configuration.put("Redis_Total", "25");
		configuration.put("Redis_Idle", "5");
		configuration.put("Redis_WaitMillis", "60000");
		configuration.put("redis_shard", "192.168.7.7:6379;192.168.7.101:6379;"
				+ "192.168.7.102:6379;192.168.7.103:6379;192.168.7.103:6379;"
				+ "192.168.7.104:6379;192.168.7.105:6379");
		configuration.put("redis_sync", "localhost:6379");
	}
	
	private static void load() {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse("middware-site.xml");
			Element element = doc.getDocumentElement();
			System.out.println(element.getTagName());
			/*NodeList nodeList = doc.getElementsByTagName("property");
			System.out.println(nodeList.getLength());
			for(int i = 0; i < nodeList.getLength(); i++) {
				Node fatherNode = nodeList.item(i);
				System.out.println(fatherNode.getNodeName());
				NamedNodeMap attributes = fatherNode.getAttributes();
				
				NodeList childNodes = fatherNode.getChildNodes();
				
				System.out.println(childNodes.getLength());
				
				for(int j = 0; j < childNodes.getLength(); j++) {
					Node childNode = childNodes.item(j);
					if(childNode instanceof Element) {
						System.out.println(childNode.getNodeName());
						System.out.println(childNode.getFirstChild().getNodeValue());
					}
					else {
						System.out.println("wrong");
					}
				}
			}*/
			NodeList nameList = doc.getElementsByTagName("name");
			NodeList valueList = doc.getElementsByTagName("value");
			for(int i = 0; i < nameList.getLength(); i++) {
				configuration.put(nameList.item(i).getFirstChild().getNodeValue(), valueList.item(i).getFirstChild().getNodeValue());
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void set(String key, String value) {
		configuration.put(key, value);
	}
	
	public static String get(String key, String defaultvalue) {
		if(!configuration.containsKey(key)) 
			return defaultvalue;
		else 
			return configuration.get(key);
	}
	
	public static void setInt(String key, int value) {
		configuration.put(key, String.valueOf(value));
	}
	
	public static int getInt(String key, int defaultvalue) {
		if(!configuration.containsKey(key)) 
			return defaultvalue;
		else 
			return Integer.parseInt(configuration.get(key));
	}
	
	public static void setLong(String key, long value) {
		configuration.put(key, String.valueOf(value));
	}
	
	public static long getLong(String key, long defaultvalue) {
		if(!configuration.containsKey(key))
			return defaultvalue;
		else
			return Long.parseLong(configuration.get(key));
	}
	
	public static void main(String[] args) {
		
	}
}
