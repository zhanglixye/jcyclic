package com.kaiwait.thread.executor;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 仅支持一个线程的线程池处理一些比较耗时但不重要的操作，如邮件发送；
 * 注意：如果队列数达到上限值，会丢弃最老的任务
 *
 */
public class NewSingleThreadExecutor implements IExecutor {
	
	private static ExecutorService excutorService = null;
    /**
     * max number of threads
     */
    protected static int maxQueues = 1000;
	
    protected NewSingleThreadExecutor(){
		init();
	}
	
	private void init(){
		excutorService =  new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(maxQueues)
					, new ThreadPoolExecutor.DiscardOldestPolicy());

	}
	

	@Override
	public void execute(Runnable command) {
		excutorService.execute(command);

	}
	/**
	 */
	@Override
	public <T> Future<T> submit(Callable<T> task) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 */
	@Override
	public <T> List<Future<T>> submitAll(Collection<? extends Callable<T>> tasks) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 */
	@Override
	public <T> List<Future<T>> submitAll(
			Collection<? extends Callable<T>> tasks, long interval) {
		// TODO Auto-generated method stub
		return null;
	}

}
