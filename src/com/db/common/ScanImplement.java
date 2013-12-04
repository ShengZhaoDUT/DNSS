package com.db.common;

import java.util.Set;

public interface ScanImplement {
	boolean scan(String dbName, String table, String startkey, int recordcount, Set<String> fields, Object result);
}
