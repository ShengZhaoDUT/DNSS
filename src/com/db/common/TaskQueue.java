package com.db.common;

import java.util.concurrent.ConcurrentLinkedDeque;

public class TaskQueue {
	public static ConcurrentLinkedDeque<Task> queue = new ConcurrentLinkedDeque<Task>();
}
