package com.kaiwait.thread.executor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 缺省的线程连接池管理, 目前限制仅在包类可以被访问
 *
 */
class StandardThreadExecutor implements IExecutor {
	
    /**
     * max number of threads
     */
    protected static int maxThreads = 200;

    /**
     * min number of threads
     */
    protected static int minSpareThreads = 25;

    /**
     * idle time in milliseconds
     */
    protected static int maxIdleTime = 60000;
	
    /**
     * max number of threads
     */
    protected static int maxQueues = 400;
    
    //使用大型队列和小型池可以最大限度地降低 CPU 使用率
	private static ExecutorService excutorService = null;
	
	protected StandardThreadExecutor(){
		init();
	}
	
	private void init(){
		excutorService =  new ThreadPoolExecutor(minSpareThreads, maxThreads, maxIdleTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(maxQueues)
					, new ThreadPoolExecutor.CallerRunsPolicy());

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
	 * 阻塞式提交多个任务，并等待所有任务结果返回
	 */
	@Override
	public <T> List<Future<T>> submitAll(Collection<? extends Callable<T>> tasks) {
//		try {
//			return excutorService.invokeAll(tasks);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
		
		return submitAll(tasks , 0);
         
	}
	
	/**
	 * 阻塞式提交多个任务,每个任务间隔指定时间提交，并等待所有任务结果返回
	 */
	@Override
	public <T> List<Future<T>> submitAll(Collection<? extends Callable<T>> tasks, long interval) {

        if (tasks == null)
            throw new NullPointerException();
        List<Future<T>> futures = new ArrayList<Future<T>>(tasks.size());
       
        for (Callable<T> t : tasks) {
        	Future<T> f = submit(t);
            futures.add(f);
            
            if(interval > 0){
            	try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }

        return futures;        
         
	}	
}
