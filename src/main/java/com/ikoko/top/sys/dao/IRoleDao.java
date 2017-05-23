package com.ikoko.top.sys.dao;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.common.annotation.MyBatisDao;
import com.ikoko.top.sys.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限Dao接口
 * 
 * @author cc
 */
@MyBatisDao
public interface IRoleDao extends ICrudDao<Role> {

    /**
     * 获取权限资源ID
     * @param roleIds
     * @return
     */
    public List<Role> getRoles(@Param("roleIds") String[] roleIds);

}
