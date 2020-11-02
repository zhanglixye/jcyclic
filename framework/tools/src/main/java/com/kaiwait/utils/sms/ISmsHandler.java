package com.kaiwait.utils.sms;

import com.kaiwait.thread.task.ISmsTask;

/**
 * SMS 处理接口
 *
 */
public interface ISmsHandler {
	public void execute(ISmsTask task);
}
