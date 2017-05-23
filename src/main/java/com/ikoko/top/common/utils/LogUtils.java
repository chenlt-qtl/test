package com.ikoko.top.common.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.ikoko.top.sys.entity.SLog;
import com.ikoko.top.sys.entity.User;
import com.ikoko.top.task.TaskLog;

/**
 * 日志工具
 * 
 * @author cc
 * 
 */
public class LogUtils {

	/**
	 * 保存日志
	 * @param request 请求
	 * @param user 用户
	 * @param timeConsuming 耗时
	 */
	public static void saveSLog(HttpServletRequest request, User user,String timeConsuming) {
		saveSLog(request, user, null,timeConsuming);
	}


	/**
	 * 保存日志
	 * @param request 请求
	 * @param user 用户
	 * @param ex 错误
	 * @param timeConsuming 耗时
	 */
	public static void saveSLog(HttpServletRequest request, User user,
			Exception ex,String timeConsuming) {
		SLog log = new SLog();
		log.setMenu(getMenuPath());
		log.setRemoteAddr(JStringUtils.getRemoteAddr(request));
		log.setUserAgent(request.getHeader("user-agent"));
		log.setRequestUri(request.getRequestURI());
		log.setParams(request.getParameterMap());
		log.setMethod(request.getMethod());
		if(user!=null){log.setCreateBy(user.getId());}
		log.setCreateDate(new Date());
		log.setTimeConsuming(timeConsuming);
		if (ex != null)
			log.setException(ex.getMessage());
		TaskLog.getSingleton().add(log);
	}

	/**
	 * 获取菜单名称
	 */
	public static String getMenuPath(){

		return "";
	}
}
