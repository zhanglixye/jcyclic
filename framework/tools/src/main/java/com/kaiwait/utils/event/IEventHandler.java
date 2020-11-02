package com.kaiwait.utils.event;

import com.kaiwait.thread.task.IEventTask;
/**
 * 通用事件处理类
 *
 */
public interface IEventHandler {
	public void execute(IEventTask task);
}
