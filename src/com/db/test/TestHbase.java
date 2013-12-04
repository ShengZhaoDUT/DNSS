package com.db.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Result;

import com.db.hbase.Column;
import com.db.hbase.HbaseConfiguration;
import com.db.hbase.HbaseObject;
import com.db.hbase.HbaseReader;
import com.db.hbase.HbaseWriter;

public class TestHbase {
	public static void main(String args[]) throws MasterNotRunningException, ZooKeeperConnectionException
	{
		HbaseObject h = new HbaseObject(new HbaseConfiguration());
		HbaseWriter w = new HbaseWriter(h);
		Column birthinfo = new Column("xiaohan","info","birth");
		Map<Column, String> tobeWrite = new HashMap<Column, String>();
		tobeWrite.put(birthinfo, "0912");
		w.write("usertable",null, tobeWrite);
		//-------------//
		HbaseReader r = new HbaseReader(h);
		Set<Column> tobeRead= new HashSet<Column>();
		tobeRead.add(birthinfo);
		Result readResult = new Result();
		r.read(null, "usertable", "xiaohan", tobeRead, readResult);
		System.out.println("readResult" + new String (readResult.getRow())+ "");
		
	}
}
