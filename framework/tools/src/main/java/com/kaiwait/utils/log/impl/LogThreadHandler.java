package com.kaiwait.utils.log.impl;

import com.kaiwait.thread.executor.ExecutorServiceFactory;
import com.kaiwait.thread.task.ILogTask;
import com.kaiwait.utils.log.ILogHandler;

/**
 * 日志线程处理类
 *
 */
public class LogThreadHandler implements ILogHandler {

	@Override
	public void execute(ILogTask task) {
		ExecutorServiceFactory.getExecutService(null).execute(task);

	}

}
