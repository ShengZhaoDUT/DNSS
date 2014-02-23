package com.db.utility;

public class LastUpdateTime {
	private static long time;
	// sync to mem
	public static void setUpdateTime(long last) {
		time = last;
	}
	public static long getUpdateTime() {
		return time;
	}
	// sync to file
}
