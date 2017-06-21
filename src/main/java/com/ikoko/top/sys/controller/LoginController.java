package com.ikoko.top.sys.controller;

import javax.servlet.http.HttpServletRequest;

import com.ikoko.top.common.utils.UserUtils;
import com.ikoko.top.sys.entity.User;
import com.ikoko.top.sys.service.PasswordHelper;
import com.ikoko.top.sys.service.UserService;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ikoko.top.common.BaseController;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 登录控制器
 * 
 * @author cc
 */
@Controller
@RequestMapping(value = "${adminPath}")
public class LoginController extends BaseController {

	@Autowired
	UserService userService;

	@Autowired
	private PasswordHelper passwordHelper;

	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model) {

        //获取用户登录信息 验证已登录，跳转到管理页
        String userName = UserUtils.getLoginUserName();
        if(userName!=null){
            return "redirect:" + adminPath;
        }
		String exceptionClassName = (String) request
				.getAttribute("shiroLoginFailure");
		model.addAttribute("username", request.getParameter("username"));
		String error = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (IncorrectCredentialsException.class.getName().equals(
				exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (ExcessiveAttemptsException.class.getName().equals(
				exceptionClassName)) {
			error = "错误次数操作超过限制";
		} else if (LockedAccountException.class.getName().equals(
				exceptionClassName)) {
			error = "帐号被锁定";
		} else if (exceptionClassName != null) {
			error = "未知的错误";
		}
		model.addAttribute("msg", error);
		if (request.getParameter("forceLogout") != null) {
			error = "您已经被管理员强制退出，请重新登录";
			model.addAttribute("msg", error);
		}
		return "login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(){
		return "register";
	}

	/**
	 * 用户注册
	 * @param username 用户名
	 * @param password 密码
	 * @param password 确认密码
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(String username,String password,String rpassword,Model model){
		String url = "register";
		//判断是否密码重复
		if(!password.equals(rpassword)){
			addMessage(model,"两次密码不一致");
		}else{
			User user = userService.getUserByUserName(username);
			if(user==null){
				user = new User();
				user.setUsername(username);
				user.setPassword(password);
				user.setOrganizationId("1");
				user.setRolesStr("3,");
				passwordHelper.encryptPassword(user);
				userService.saveUserAndRole(user);
				addMessage(model,"注册成功");
				url = "login";
			}else{
				addMessage(model,"账号已存在");
			}
		}
		return url;
	}

}
