package com.ikoko.top.sys.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikoko.top.common.service.CrudService;
import com.ikoko.top.sys.dao.IRoleDao;
import com.ikoko.top.sys.entity.Role;

/**
 * 权限服务
 * 
 * @author cc
 */
@Service
@Transactional(readOnly = true)
public class RoleService extends CrudService<IRoleDao, Role> {

	@Autowired
	ResourceService resourceService;

	public Set<String> findRoles(String userId) {
		Set<String> roles = new HashSet<String>();
		List<Role> roleList = dao.getRolesByUser(userId);
		for (Role role : roleList) {
			roles.add(role.getRole());
		}
		return roles;
	}
	
    public String findRolesStr(String userId) {
        String rolesStr = "";
        List<Role> roleList = dao.getRolesByUser(userId);
        for (Role role : roleList) {
            if(rolesStr.length()!=0){
                rolesStr += ",";
            }
            rolesStr += role.getId();
        }
        return rolesStr;
    }

	public Set<String> findPermissions(String userId) {
		Set<String> resourceIds = new HashSet<String>();
		List<Role> roles = dao.getRolesByUser(userId);
		for (int i=0;i<roles.size();i++) {
			Role role = roles.get(i);
			if (role != null) {
				resourceIds.addAll(role.getResourceIds());
			}
		}
		if(resourceIds.size()>0){
		    return resourceService.findPermissions(resourceIds);
		}else{
		    return new HashSet<String>();
		}
	}
}
