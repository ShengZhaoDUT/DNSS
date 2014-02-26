package com.db.utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LastUpdateTime {
	private static String time = "0";
	private static String fileName = "lastUpdateTime.txt";
	// sync to mem
	public static void setUpdateTime(String last) {
		time = last;
	}
	public static String getUpdateTime() {
		return time;
	}
	public static boolean syncToFile() {
		FileWriter writer = null;
		try {
			writer = new FileWriter(fileName);
			writer.write(time);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return true;
	}
	public static void getFromFile() {
		FileReader reader = null;
		try {
			reader = new FileReader(fileName);
			char[] buffer = new char[20];
			reader.read(buffer);
			time = String.valueOf(buffer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	// sync to file
}
