package com.thuhpc.unused;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LogtoDisk {
	
	private static Object object;
	private static FileOutputStream fos;
	private static ObjectOutputStream oos;
	
	public static void init() throws IOException {
		fos = new FileOutputStream(new File("memcache_log.log"));
		oos = new ObjectOutputStream(fos);
	}
	
	public static void write(Log log) throws IOException {
		synchronized (object) {
			oos.writeObject(log);
			oos.flush();
		}
	}
}