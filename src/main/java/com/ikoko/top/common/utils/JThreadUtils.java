package com.ikoko.top.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程处理工具
 * 
 * @author cc
 */
public class JThreadUtils extends BaseUtils{

	/**
	 * 单线程执行器
	 */
	public static ExecutorService singleExecutor = Executors.newSingleThreadExecutor();

	/**
	 * 动态多线程执行器
	 */
	public static ExecutorService executor = Executors.newCachedThreadPool();

	/**
	 * sleep等待,单位为毫秒 忽略InterruptedException.
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
