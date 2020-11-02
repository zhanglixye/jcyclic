package com.kaiwait.thread.task;

import java.util.concurrent.Callable;
/**
 * 可以获取任务返回结果的接口类
 *
 */
public interface IFutureTask<V> extends Callable<V> {

}
