
/**
* @(#)RoleUserService.java 2017年6月21日
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

package com.ikoko.top.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikoko.top.common.service.CrudService;
import com.ikoko.top.sys.dao.IRoleUserDao;
import com.ikoko.top.sys.entity.RoleUser;
import com.ikoko.top.sys.entity.User;

/**
 * 描述:
 * @author chenlt
 */
@Service
@Transactional(readOnly = true)
public class RoleUserService extends CrudService<IRoleUserDao, RoleUser> {

    /**
     * 保存角色与用户对应数据
     * @param user
     */
    public void saveRoleUser(User user){
        String userId = user.getId();
        List<RoleUser> roleUsers = dao.getByUser(userId);
        Map<String,RoleUser> exist = new HashMap<String,RoleUser>();
        
        String rolesStr = user.getRolesStr();
        if(StringUtils.isNotBlank(rolesStr)){
            for(RoleUser roleUser:roleUsers){
                exist.put(roleUser.getRoleId(), roleUser);
            }
            
            for(String roleId:rolesStr.split(",")){
                if(StringUtils.isNotBlank(roleId)){
                    if(!exist.containsKey(roleId)){//不存在的话新增数据
                        RoleUser roleUser = new RoleUser();
                        roleUser.setUserId(userId);
                        roleUser.setRoleId(roleId);
                        save(roleUser);
                    }else{//存在的话从exist里删除
                        exist.remove(roleId);
                    }
                }
            }
        }
        
        for(RoleUser roleUser:exist.values()){//删除数据
            delete(roleUser);
        }
        
    }
}
