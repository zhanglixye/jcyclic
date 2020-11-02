package com.kaiwait.utils.future;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.kaiwait.thread.task.IFutureTask;
/**
 * 可以获取任务的返回结果的接口处理类
 *
 */
public interface IFutureTaskHandler<V> {
	/**
	 * 提交一个任务，然后等待并阻塞直到结果返回或指定的时间超时
	 */
	public V submit(IFutureTask<V> task,long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException;
	
	/**
	 * 提交一个任务，然后等待并阻塞直到结果返回或指定的时间超时</br>
	 * 如果超时将中断执行task的子线程,task中的阻塞方法需捕获InterruptedException
	 * 
	 */
	public V submitOnTimeoutCancel(IFutureTask<V> task,long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException;
	/**
	 * 阻塞式提交多个任务，并等待所有任务结果返回
	 */
	public List<V> submitAll(Collection<? extends Callable<V>> tasks);
	
	/**
	 * 阻塞式提交多个任务，每个任务间隔指定时间提交,并等待所有任务结果返回
	 */
	public List<V> submitAll(Collection<? extends Callable<V>> tasks, long interval);
	
}
