package com.kaiwait.thread.executor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * 自定义线程执行类接口
 *
 */

public interface IExecutor extends Executor {
	/**
	 * 提交一个任务并等待结果返回
	 */
    <T> Future<T> submit(Callable<T> task);
    /**
     * 阻塞式提交多个任务，并等待所有任务结果返回
     */
    <T> List<Future<T>> submitAll(Collection<? extends Callable<T>> tasks);
    /**
     * 阻塞式提交多个任务,每个任务间隔指定时间提交，并等待所有任务结果返回
     */
    <T> List<Future<T>> submitAll(Collection<? extends Callable<T>> tasks, long interval);
    
}
