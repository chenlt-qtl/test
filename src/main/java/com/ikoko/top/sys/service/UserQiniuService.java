package com.ikoko.top.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikoko.top.common.service.CrudService;
import com.ikoko.top.sys.dao.UserQiniuDao;
import com.ikoko.top.sys.entity.UserQiniu;

/**
 * 用户七牛配置 Service层
 * 
 * @author MyCode
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class UserQiniuService extends CrudService<UserQiniuDao, UserQiniu> {

	/**
	 * 根据用户获取七牛配置
	 * 
	 * @param userId
	 * @return
	 */
	public UserQiniu getByUser(String userId) {
		return dao.getByUser(userId);
	}
}
