package com.ikoko.top.sys.dao;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.common.annotation.MyBatisDao;
import com.ikoko.top.sys.entity.Session;

/**
* session管理 DAO接口
* @author iutils.cn
* @version 1.0
*/
@MyBatisDao
public interface SessionDao extends ICrudDao<Session> {

}
