
/**
* @(#)IRoleUserDao.java 2017年6月21日
*
* Copyright 2000-2017 by ChinanetCenter Corporation.
*
* All rights reserved.
*
* This software is the confidential and proprietary information of
* ChinanetCenter Corporation ("Confidential Information"). You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with ChinanetCenter.
*
*/

package com.ikoko.top.sys.dao;

import java.util.List;

import com.ikoko.top.common.ICrudDao;
import com.ikoko.top.common.annotation.MyBatisDao;
import com.ikoko.top.sys.entity.RoleUser;

/**
 * 描述:
 * @author chenlt
 */
@MyBatisDao
public interface IRoleUserDao extends ICrudDao<RoleUser> {

    public List<RoleUser> getByUser(String userId);
}
