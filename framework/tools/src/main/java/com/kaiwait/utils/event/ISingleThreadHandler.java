package com.kaiwait.utils.event;

import com.kaiwait.thread.task.ISingleThreadTask;

/**
 * 仅单一线程执行任务的处理类
 *
 */
public interface ISingleThreadHandler {
	public void execute(ISingleThreadTask task);
}
