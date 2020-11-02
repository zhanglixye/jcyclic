package com.kaiwait.utils.sms.impl;

import com.kaiwait.thread.executor.ExecutorServiceFactory;
import com.kaiwait.thread.task.ISmsTask;
import com.kaiwait.utils.sms.ISmsHandler;

/**
 * SMS 线程处理类
 *
 */
public class SmsThreadHandler implements ISmsHandler {

	@Override
	public void execute(ISmsTask task) {
		ExecutorServiceFactory.getExecutService(null).execute(task);
		
	}

}
