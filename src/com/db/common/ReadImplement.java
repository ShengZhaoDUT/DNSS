package com.db.common;

import java.util.Set;
/*
 * dbName: name of dataBase
 * table: name of table
 * key: the key to quary
 * field: quired field
 * result: result of the quary
 */
public interface ReadImplement {
	boolean read(String dbName, String table, String key, Set<String> fields, Object result);
}
