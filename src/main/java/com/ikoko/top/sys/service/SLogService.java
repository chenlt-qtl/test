package com.ikoko.top.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikoko.top.common.service.CrudService;
import com.ikoko.top.sys.dao.SLogDao;
import com.ikoko.top.sys.entity.SLog;

/**
 * 日志记录 Service层
 * 
 * @author MyCode
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class SLogService extends CrudService<SLogDao, SLog> {

	/**
	 * 批量添加记录
	 * 
	 * @param logs
	 * @return
	 */
	@Transactional(readOnly = false)
	public int saveBatch(List<SLog> logs) {
		return dao.saveBatch(logs);
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@Transactional(readOnly = false)
	public int deleteAll(String[] ids){
		return dao.deleteAll(ids);
	}

}
