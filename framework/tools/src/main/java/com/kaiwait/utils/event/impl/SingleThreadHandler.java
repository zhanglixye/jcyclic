package com.kaiwait.utils.event.impl;

import com.kaiwait.thread.executor.ExecutorServiceFactory;
import com.kaiwait.thread.task.ISingleThreadTask;
import com.kaiwait.utils.event.ISingleThreadHandler;

public class SingleThreadHandler implements ISingleThreadHandler {

	@Override
	public void execute(ISingleThreadTask task) {
		ExecutorServiceFactory.getExecutService(ExecutorServiceFactory.SIGNLE_SERVICE_TYPE).execute(task);

	}

}
