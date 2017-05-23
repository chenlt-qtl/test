package com.ikoko.top.sys.dao;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.common.annotation.MyBatisDao;
import com.ikoko.top.sys.entity.Config;

/**
* 公共配置表 DAO接口
* @author iutils.cn
* @version 1.0
*/
@MyBatisDao
public interface ConfigDao extends ICrudDao<Config> {

    /**
     * 获取配置信息
     * @param config
     * @return
     */
    public Config getConfigInfo(Config config);

}
