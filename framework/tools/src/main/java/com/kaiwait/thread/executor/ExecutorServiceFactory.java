package com.kaiwait.thread.executor;

import java.util.Hashtable;

import com.kaiwait.utils.common.StringUtil;

public class ExecutorServiceFactory {

	private static Hashtable<String, IExecutor> executors = new Hashtable<String, IExecutor>();
	
	public static final String DEFAULT_SERVICE_TYPE = "DEFAULT";
	
	public static final String SIGNLE_SERVICE_TYPE = "SINGLE";
	
	public static final String ZOOKEEPER_SERVICE_TYPE = "ZOOKEEPER";
	
	public static final String LOCK_SERVICE_TYPE = "LOCK";
	
	static{
		executors.put(DEFAULT_SERVICE_TYPE, new StandardThreadExecutor());
		
		executors.put(SIGNLE_SERVICE_TYPE, new NewSingleThreadExecutor());
		
		executors.put(ZOOKEEPER_SERVICE_TYPE, new ZooKeeperThreadExecutor());
		
		executors.put(LOCK_SERVICE_TYPE, new DistributedLockThreadExecutor());
	}
	
	/**
	 * 获取特定的线程执行服务
	 */
	public static IExecutor getExecutService(String serviceType) {
		
		if(StringUtil.isEmpty(serviceType) || !executors.containsKey(serviceType)) {
			serviceType = DEFAULT_SERVICE_TYPE;
		
		} 
		return executors.get(serviceType);

	}

}
