package com.ikoko.top.sys.service;

import com.ikoko.top.common.service.CrudService;
import com.ikoko.top.common.utils.CacheUtils;
import com.ikoko.top.sys.dao.ConfigDao;
import com.ikoko.top.sys.entity.Config;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 公共配置表 Service层
* @author iutils.cn
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class ConfigService extends CrudService<ConfigDao, Config> {

    /**
     * 获取配置信息
     * @param sysName
     * @param moduleName
     * @param configName
     * @return
     */
    public Config getConfigInfo(String sysName,String moduleName,String configName){
        Config config = null;
        try{
            config = (Config) CacheUtils.get(sysName + moduleName + configName);
            if(config==null){
                Config param = new Config();
                param.setSysName(sysName);
                param.setModuleName(moduleName);
                param.setConfigName(configName);
                config = dao.getConfigInfo(param);
                CacheUtils.put(sysName + moduleName + configName, config);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return config;
    }

}
