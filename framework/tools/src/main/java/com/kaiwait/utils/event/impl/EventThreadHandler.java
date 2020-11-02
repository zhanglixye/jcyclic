package com.kaiwait.utils.event.impl;

import com.kaiwait.thread.executor.ExecutorServiceFactory;
import com.kaiwait.thread.task.IEventTask;
import com.kaiwait.utils.event.IEventHandler;
/**
 * 通用事件处理类
 *
 */
public class EventThreadHandler implements IEventHandler {

	@Override
	public void execute(IEventTask task) {
		ExecutorServiceFactory.getExecutService(null).execute(task);
	}

}
