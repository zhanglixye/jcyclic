package com.kaiwait.utils.future.impl;

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
 * 可以获取任务的返回结果的接口处理ZooKeeper 特有实现
 *
 */
public class ZooKeeperFutureTaskHandler<V> implements IFutureTaskHandler<V> {
	/**
	 * 提交一个任务，然后等待并阻塞直到结果返回或指定的时间超时
	 */
	@Override
	public V submit(IFutureTask<V> task,long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException{
		return ExecutorServiceFactory.getExecutService(ExecutorServiceFactory.ZOOKEEPER_SERVICE_TYPE).submit(task).get(timeout, unit);
	}

	/**
	 * 提交一个任务，然后等待并阻塞直到结果返回或指定的时间超时</br>
	 * 如果超时将中断执行task的子线程,task中的阻塞方法需捕获InterruptedException
	 * 
	 */
	@Override
	public V submitOnTimeoutCancel(IFutureTask<V> task, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		final Future<V> submit = ExecutorServiceFactory.getExecutService(ExecutorServiceFactory.ZOOKEEPER_SERVICE_TYPE).submit(task);
		try {
			return submit.get(timeout, unit);
		} catch (TimeoutException e) {
			submit.cancel(true);
			throw e;
		}
	}
	/**
	 */
	@Override
	public List<V> submitAll(Collection<? extends Callable<V>> tasks) {
		return null;
	}
	/**
	 */
	@Override
	public List<V> submitAll(Collection<? extends Callable<V>> tasks,
			long interval) {
		return null;
	}
}
