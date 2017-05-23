package com.ikoko.top.sys.dao;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.common.annotation.MyBatisDao;
import com.ikoko.top.sys.entity.Organization;

/**
 * 组织机构Dao
 * 
 * @author cc
 */
@MyBatisDao
public interface IOrganizationDao extends ICrudDao<Organization> {

    /**
     * 查询是否存在下级节点
     * @param organization
     * @return
     */
    public int findNext(Organization organization);

}
