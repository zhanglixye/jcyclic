package com.kaiwait.utils.future.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.kaiwait.thread.executor.ExecutorServiceFactory;
import com.kaiwait.thread.task.IFutureTask;
import com.kaiwait.utils.future.IFutureTaskHandler;
/**
 * 可以获取任务的返回结果的接口处理类默认实现
 *
 */
public class FutureTaskHandler<V> implements IFutureTaskHandler<V> {
	/**
	 * 提交一个任务，然后等待并阻塞直到结果返回或指定的时间超时
	 */
	@Override
	public V submit(IFutureTask<V> task,long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException{
		return ExecutorServiceFactory.getExecutService(null).submit(task).get(timeout, unit);
	}

	/**
	 * 提交一个任务，然后等待并阻塞直到结果返回或指定的时间超时</br>
	 * 如果超时将中断执行task的子线程,task中的阻塞方法需捕获InterruptedException
	 * 
	 */
	@Override
	public V submitOnTimeoutCancel(IFutureTask<V> task, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		final Future<V> submit = ExecutorServiceFactory.getExecutService(null).submit(task);
		try {
			return submit.get(timeout, unit);
		} catch (TimeoutException e) {
			submit.cancel(true);
			throw e;
		}
	}
	/**
	 * 阻塞式提交多个任务，并等待所有任务结果返回
	 */
	@Override
	public List<V> submitAll(Collection<? extends Callable<V>> tasks) {
		return submitAll(tasks, 0);
	}
	/**
	 * 阻塞式提交多个任务，每个任务间隔指定时间提交,并等待所有任务结果返回
	 */
	@Override
	public List<V> submitAll(Collection<? extends Callable<V>> tasks,
			long interval) {
		List<Future<V>> futures = ExecutorServiceFactory.getExecutService(null).submitAll(tasks, interval);
		if(futures == null) return null;
		
		List<V> result = new ArrayList<V>(futures.size());
		
		for (Future<V> f : futures) {
            try {
            	result.add(f.get());
            } catch (Throwable ignore) {
            	ignore.printStackTrace();
            	result.add(null);
            }
          } 
		
		return result;
	}
}
