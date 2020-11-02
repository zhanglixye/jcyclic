package com.kaiwait.utils.log;

import com.kaiwait.thread.task.ILogTask;

/**
 * 日志处理接口
 *
 */
public interface ILogHandler {
	
	public void execute(ILogTask task);

}
