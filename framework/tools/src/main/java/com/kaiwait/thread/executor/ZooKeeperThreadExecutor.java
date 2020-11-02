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
 * 给ZooKeeper专用的线程连接池管理, 目前限制仅在包类可以被访问
 *
 */
class ZooKeeperThreadExecutor implements IExecutor {
	
    /**
     * max number of threads
     */
    protected static int maxThreads = 25;

    /**
     * min number of threads
     */
    protected static int minSpareThreads = 5;

    /**
     * idle time in milliseconds
     */
    protected static int maxIdleTime = 60000;
	
    /**
     * max number of threads
     */
    protected static int maxQueues = 200;
    
    //使用大型队列和小型池可以最大限度地降低 CPU 使用率
	private static ExecutorService excutorService = null;
	
	protected ZooKeeperThreadExecutor(){
		init();
	}
	
	private void init(){
		//这里使用DiscardOldestPolicy 丢弃最旧的任务策略，因为ZooKeeper 会不断尝试连接服务，线程永远不会自动退出 
		excutorService =  new ThreadPoolExecutor(minSpareThreads, maxThreads, maxIdleTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(maxQueues)
					, new ThreadPoolExecutor.DiscardOldestPolicy());

	}
	
	@Override
	public void execute(Runnable command) {
		excutorService.execute(command);

	}
	/**
	 * 阻塞式提交一个任务并等待结果返回
	 */
	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return excutorService.submit(task);
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
