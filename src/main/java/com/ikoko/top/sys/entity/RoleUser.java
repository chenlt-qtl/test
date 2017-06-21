package com.ikoko.top.sys.entity;

/**
 * 用户权限对应关系表
 * @author chenlt
 *
 */
public class RoleUser extends DataEntity<RoleUser> {
    
    private static final long serialVersionUID = 1L;
    private String roleId;
    private String userId;
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

}