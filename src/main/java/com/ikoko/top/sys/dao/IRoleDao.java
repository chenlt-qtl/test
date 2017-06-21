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
     * 根据用户ID获取角色信息
     * @param userId
     * @return
     */
     public List<Role> getRolesByUser(@Param("userId") String userId);

}
