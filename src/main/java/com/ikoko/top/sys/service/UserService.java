package com.ikoko.top.sys.service;

import java.util.*;

import com.ikoko.top.common.utils.CacheUtils;
import com.ikoko.top.common.utils.JStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ikoko.top.common.service.CrudService;
import com.ikoko.top.common.utils.UserUtils;
import com.ikoko.top.sys.dao.IUserDao;
import com.ikoko.top.sys.entity.Role;
import com.ikoko.top.sys.entity.User;

/**
 * 用户服务
 * 
 * @author cc
 */
@Service
@Transactional(readOnly = true)
public class UserService extends CrudService<IUserDao, User> {

	@Autowired
	private PasswordHelper passwordHelper;

	@Autowired
	private RoleService roleService;
	
	@Autowired
    private RoleUserService roleUserService;

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param newPassword
	 */
	@Transactional(readOnly = false)
	public void changePassword(String userId, String password,
			String newPassword) throws Exception{
		User user = dao.get(userId);
		String oldPassword = user.getPassword();
		user.setPassword(password);
		String myPassword = passwordHelper.getPassword(user);
		if (oldPassword.equals(myPassword)) {
			user.setPassword(newPassword);
			passwordHelper.encryptPassword(user);
			save(user);
		} else {
			throw new RuntimeException("原密码错误");
		}
	}

	/**
	 * 修改登录密码
	 * @param user
	 * @param newPassword
	 */
	@Transactional(readOnly = false)
	public void changePassword(User user,String newPassword) throws Exception{
		user.setPassword(newPassword);
		passwordHelper.encryptPassword(user);
		save(user);
	}

	/**
	 * 根据用户名查找其角色
	 * 
	 * @return
	 */
	public Set<String> findRoles() {
		User user = UserUtils.getLoginUser();
		if (user == null) {
			return Collections.emptySet();
		}
		return roleService.findRoles(user.getId());
	}

	/**
	 * 根据用户名查找其权限
	 * 
	 * @return
	 */
	public Set<String> findPermissions() {
		User user = UserUtils.getLoginUser();
		if (user == null) {
			return Collections.emptySet();
		}
		return roleService.findPermissions(user.getId());
	}

	/**
	 * 根据登录名获取用户
	 *
	 * @param userName
	 * @return
	 */
	public User getUserByUserName(String userName) {
		if(JStringUtils.isBlank(userName)){
			return null;
		}
		User user = null;
		try {
			user = (User)CacheUtils.get(userName);
			if(user==null){
				user = dao.getUserByUserName(userName);
				CacheUtils.put(userName,user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * 获取用户列表
	 * @param users
	 * @return
	 */
	public List<Map> getUsers(String[] users){
		return dao.getUsers(users);
	}
	
	public void saveUserAndRole(User user){
	    save(user);
	    roleUserService.saveRoleUser(user);
	}

}
