package com.ikoko.top.sys.dao;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.common.annotation.MyBatisDao;
import com.ikoko.top.sys.entity.ScheduleJob;

/**
 * 任务调度 DAO接口
 * 
 * @author MyCode
 * @version 1.0
 */
@MyBatisDao
public interface ScheduleJobDao extends ICrudDao<ScheduleJob> {

	/**
	 * 更改任务状态
	 * 
	 * @param scheduleJob
	 * @return
	 */
	public int changeJobStatus(ScheduleJob scheduleJob);

}
