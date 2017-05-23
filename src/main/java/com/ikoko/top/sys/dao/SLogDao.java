package com.ikoko.top.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.common.annotation.MyBatisDao;
import com.ikoko.top.sys.entity.SLog;

/**
 * 日志记录 DAO接口
 * 
 * @author MyCode
 * @version 1.0
 */
@MyBatisDao
public interface SLogDao extends ICrudDao<SLog> {

	/**
	 * 批量添加记录
	 * 
	 * @param logs
	 * @return
	 */
	public int saveBatch(@Param("logs") List<SLog> logs);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public int deleteAll(@Param("ids") String[] ids);

}
